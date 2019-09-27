package com.xz.magicbox.activity.zhihu;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;
import com.xz.magicbox.R;
import com.xz.magicbox.activity.zhihu.contract.Contract;
import com.xz.magicbox.activity.zhihu.presenter.Presenter;
import com.xz.magicbox.adapter.NewsAdapter;
import com.xz.magicbox.base.BaseActivity;
import com.xz.magicbox.custom.OnItemClickListener;
import com.xz.magicbox.entity.News;
import com.xz.magicbox.utils.SpacesItemDecorationVertical;

import java.util.List;

import butterknife.BindView;

public class DailyRe extends BaseActivity implements Contract.View {
    //P
    private Presenter presenter;
    private NewsAdapter adapter;
    private static final int SHOW_NEW = 110;


    @BindView(R.id.news_list)
    RecyclerView dailyList;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_NEW:
                    _showNews((List<News>) msg.obj);
                    break;
            }
        }
    };



    @Override
    protected int getLayoutResource() {
        return R.layout.activity_daily_re;
    }

    @Override
    protected boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    protected void initData() {
        presenter = new Presenter(this);
        initRecycler();

        //获取日报数据
        presenter.getDaily();

    }

    private void initRecycler() {

        dailyList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(this);
        dailyList.setAdapter(adapter);

        adapter.setItemOnClickListener(new OnItemClickListener() {
            @Override
            public void onClick(String id) {
                startActivity(new Intent(DailyRe.this,DetailActivity.class).putExtra("id",id));
            }
        });

    }

    /**
     * 日报数据
     */
    @Override
    public void showNews(List<News> list) {
        Message msg = handler.obtainMessage();
        msg.what = SHOW_NEW;
        msg.obj = list;
        handler.sendMessage(msg);
    }


    /**
     * UI线程
     */
    private void _showNews(List<News> list) {

        adapter.refresh(list);

    }
}
