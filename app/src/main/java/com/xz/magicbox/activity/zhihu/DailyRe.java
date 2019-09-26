package com.xz.magicbox.activity.zhihu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xz.magicbox.R;
import com.xz.magicbox.activity.zhihu.contract.Contract;
import com.xz.magicbox.activity.zhihu.presenter.Presenter;
import com.xz.magicbox.base.BaseActivity;
import com.xz.magicbox.constant.Local;

public class DailyRe extends BaseActivity implements Contract.View {

    //P
    private Presenter presenter;

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

//        presenter.getDaily();

        showLoading("sad");
    }

    /**
     * 日报数据
     */
    @Override
    public void showDaily() {

    }
}
