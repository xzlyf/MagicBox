package com.xz.magicbox.activity.zhihu.contract;

import com.xz.magicbox.activity.BaseView;
import com.xz.magicbox.custom.OnModelCallback;
import com.xz.magicbox.entity.News;

import java.util.List;

public interface Contract {
    interface Model {
        void getDaily(OnModelCallback listener);

    }

    interface View extends BaseView {
        void showNews(List<News> list);
    }

    interface Presenter {
        void getDaily();
    }
}
