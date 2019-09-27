package com.xz.magicbox.activity.zhihu.presenter;

import com.xz.magicbox.activity.zhihu.contract.Contract;
import com.xz.magicbox.activity.zhihu.model.Model;
import com.xz.magicbox.constant.Local;
import com.xz.magicbox.custom.OnModelCallback;
import com.xz.magicbox.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Presenter implements Contract.Presenter {
    private String TAG = "Dayily.Presenter";

    //M
    private Model model;

    //V
    private Contract.View view;

    public Presenter(Contract.View view) {
        this.view = view;
        model = new Model();
    }


    @Override
    public void getDaily() {
        view.showLoading("正在获取");

        model.getDaily(new OnModelCallback() {
            @Override
            public void callback(Object o) {

                List<News> mlist = new ArrayList<>();

                try {
                    JSONObject obj = new JSONObject((String) o);

                    //解析stories数据
                    JSONArray stories = obj.getJSONArray("stories");
                    JSONObject obj2;
                    News news;
                    for (int i = 0; i < stories.length(); i++) {
                        news = new News();
                        obj2 = stories.getJSONObject(i);
                        news.setHint(obj2.getString("hint"));
                        news.setId(obj2.getInt("id") + "");
                        news.setImg(obj2.getJSONArray("images").getString(0));
                        news.setTitle(obj2.getString("title"));
                        news.setUrl(obj2.getString("url"));
                        mlist.add(news);
                    }

                    //解析top_stories数据
                    JSONArray topStories = obj.getJSONArray("top_stories");

                    for (int i = 0; i < topStories.length(); i++) {
                        news = new News();
                        obj2 = topStories.getJSONObject(i);
                        news.setHint(obj2.getString("hint"));
                        news.setId(obj2.getInt("id") + "");
                        news.setImg(obj2.getString("image"));
                        news.setTitle(obj2.getString("title"));
                        news.setUrl(obj2.getString("url"));
                        mlist.add(news);
                    }

                    view.showNews(mlist);
                } catch (JSONException e) {
                    e.printStackTrace();
                    view.sDialog("错误", "数据解析异常", Local.TYPE_FAIL);


                }


                view.disLoading();

            }

            @Override
            public void onFailed(String tips) {
                view.sDialog("错误", tips, Local.TYPE_FAIL);
                view.disLoading();
            }
        });
    }
}
