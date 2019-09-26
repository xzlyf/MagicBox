package com.xz.magicbox.activity.zhihu.presenter;

import android.util.Log;

import com.xz.magicbox.activity.zhihu.contract.Contract;
import com.xz.magicbox.activity.zhihu.model.Model;
import com.xz.magicbox.constant.Local;
import com.xz.magicbox.custom.OnModelCallback;
import com.xz.magicbox.network.NetUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
            public void callback(Class c) {

            }

            @Override
            public void onFailed(String tips) {
                view.disLoading();
            }
        });
    }
}
