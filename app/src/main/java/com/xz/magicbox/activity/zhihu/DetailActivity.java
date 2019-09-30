package com.xz.magicbox.activity.zhihu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xz.magicbox.R;
import com.xz.magicbox.adapter.CommentAdapter;
import com.xz.magicbox.base.BaseActivity;
import com.xz.magicbox.constant.Local;
import com.xz.magicbox.entity.Comment;
import com.xz.magicbox.network.NetUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailActivity extends BaseActivity {
    @BindView(R.id.body)
    TextView body;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.likes)
    TextView likes;
    @BindView(R.id.conn)
    TextView comments;
    @BindView(R.id.comm_list)
    RecyclerView commList;
    @BindView(R.id.check_comm)
    Button checkComm;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    private CommentAdapter adapter;
    private String id;
    private final int CONTENT = 98;
    private final int TIELE = 99;
    private final int DETAIL = 100;
    private final int COMMENT = 101;
    private boolean ishowComment = true;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONTENT:
                    _showBody((Spanned) msg.obj);
                    break;
                case TIELE:
                    _showTitle((String) msg.obj);
                    break;
                case DETAIL:
                    _showDetails(msg.arg1, msg.arg2);
                    break;
                case COMMENT:
                    _showComm((List<Comment>) msg.obj);
                    break;
            }
        }
    };


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
        body.setLinkTextColor(getColor(R.color.blue));
        commList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentAdapter(this);
        commList.setAdapter(adapter);

        showLoading("正在获取文章内容");
        getNetData();

        //设置超链接可直接跳转
        body.setMovementMethod(LinkMovementMethod.getInstance());
        CharSequence text = body.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable spannable = (Spannable) body.getText();
            URLSpan[] urlSpans = spannable.getSpans(0, end, URLSpan.class);
            if (urlSpans.length == 0) {
                return;
            }

            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
            // 循环遍历并拦截 所有http://开头的链接
            for (URLSpan uri : urlSpans) {
                String url = uri.getURL();
                if (url.indexOf("http://") == 0) {
                }
            }
            body.setText(spannableStringBuilder);
        }
    }

    @Override
    public void onBackPressed() {
        if (!ishowComment){
            body.setVisibility(View.VISIBLE);
            commList.setVisibility(View.GONE);
            checkComm.setText("查看评论");
            ishowComment = true;

        }else{
            super.onBackPressed();
        }
    }

    /**
     * 查看评论按钮
     *
     * @param v
     */
    @OnClick(R.id.check_comm)
    public void showRecycler(View v) {

        if (ishowComment){

            NetUtil.get_Asyn(Local.GET_CONTENT_COMMENT + id + Local.GET_CONTENT_COMMENT_TAIL, new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    sToast("获取评论失败，请稍后重试！");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String json = response.body().string();
                    List<Comment> mlist = new ArrayList<>();
                    try {
                        JSONObject obj = new JSONObject(json);
                        JSONArray obj2 = obj.getJSONArray("comments");
                        Comment comment;
                        JSONObject obj3;
                        for (int i = 0; i < obj2.length(); i++) {
                            obj3 = obj2.getJSONObject(i);
                            comment = new Comment();
                            comment.setAuthor(obj3.getString("author"));
                            comment.setContent(obj3.getString("content"));
                            comment.setAvatar(obj3.getString("avatar"));
                            comment.setTime(obj3.getLong("time"));
                            comment.setId(obj3.getLong("id"));
                            comment.setLikes(obj3.getInt("likes"));
                            mlist.add(comment);

                        }


                        showComm(mlist);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            ishowComment = true;
            body.setVisibility(View.VISIBLE);
            commList.setVisibility(View.GONE);
            checkComm.setText("查看评论");
        }


    }


    /**
     * 获取json数据
     */
    private void getNetData() {
        //文章内容跟
        NetUtil.get_Asyn(Local.GET_DAIYL_CONTENT + id, connectCallback);
    }

    private Callback connectCallback = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            disLoading();
            sDialog("错误", call.timeout() + "\n" + e.getMessage(), Local.TYPE_FAIL);

        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            //文章细节，点赞数，评论数
            NetUtil.get_Asyn(Local.GET_DAIYL_DETAIL + id, detailCallback);

            String json = response.body().string();

            Logger.w(json);

            String htmlBody = "";
            JSONObject obj = null;
            String title = "";
            try {
                obj = new JSONObject(json);
                htmlBody = obj.getString("body");
                title = obj.getString("title");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //解析html文本和解析图片
            Spanned sp = Html.fromHtml(htmlBody, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    Log.i("magicbox_xz", "得到的图片：" + source);
                    InputStream is = null;
                    try {
                        is = (InputStream) new URL(source).getContent();
                        Drawable d = Drawable.createFromStream(is, "src");
                        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                        //getIntrinsicWidth获取的单位之dp  要转回去才得到原图的px尺寸
//                        d.setBounds(0, 0,
//                                PxAndDipUtils.dip2px(DetailActivity.this,d.getIntrinsicWidth()),
//                                PxAndDipUtils.dip2px(DetailActivity.this,d.getIntrinsicHeight()));
                        is.close();
                        return d;
                    } catch (Exception e) {
                        return null;
                    }
                }
            }, null);

            showBody(sp);
            showTitle(title);
        }
    };

    private Callback detailCallback = new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            Logger.w("获取文章细节失败:" + Local.GET_DAIYL_DETAIL + id);
            showDetails(-1, -1);

        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            String json = response.body().string();

            try {
                JSONObject obj = new JSONObject(json);

                showDetails(obj.getInt("popularity"), obj.getInt("comments"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    /**
     * 展示标题
     *
     * @param t
     */
    private void showTitle(String t) {
        Message msg = handler.obtainMessage();
        msg.what = TIELE;
        msg.obj = t;
        handler.sendMessage(msg);
    }

    /**
     * 展示文章内容
     */
    private void showBody(Spanned sp) {

        Message msg = handler.obtainMessage();
        msg.what = CONTENT;
        msg.obj = sp;
        handler.sendMessage(msg);

    }

    /**
     * 展示系列
     *
     * @param liks 点赞数
     * @param comm 总评论数
     */
    private void showDetails(int liks, int comm) {
        Message msg = handler.obtainMessage();
        msg.what = DETAIL;
        msg.arg1 = liks;
        msg.arg2 = comm;
        handler.sendMessage(msg);
    }


    /**
     * 展示 评论
     *
     * @param mlist
     */
    private void showComm(List<Comment> mlist) {
        Message msg = handler.obtainMessage();
        msg.what = COMMENT;
        msg.obj = mlist;
        handler.sendMessage(msg);
    }


    private void _showTitle(String t) {
        title.setText(t);
    }

    private void _showBody(Spanned sp) {

//        Spanned msg = Html.fromHtml(text);
//        body.setText(msg);
        disLoading();
        body.setText(sp);
    }

    private void _showDetails(int like, int comm) {
        likes.setText("点赞：" + like);
        comments.setText("评论：" + comm);
    }

    private void _showComm(List<Comment> list) {
        adapter.refresh(list);
        commList.setVisibility(View.VISIBLE);
        body.setVisibility(View.GONE);
        commList.setVisibility(View.VISIBLE);
        ishowComment = false;
        checkComm.setText("收起评论");
    }

}
