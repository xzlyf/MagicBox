package com.xz.magicbox.activity.zhihu.contract;

import com.xz.magicbox.activity.contract.BaseView;
import com.xz.magicbox.custom.OnModelCallback;

public interface Contract {
    interface Model {
        void getDaily(OnModelCallback listener);

    }

    interface View extends BaseView {
        void showDaily();
    }

    interface Presenter {
        void getDaily();
    }
}
