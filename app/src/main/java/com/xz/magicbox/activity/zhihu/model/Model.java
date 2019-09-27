package com.xz.magicbox.activity.zhihu.model;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.xz.magicbox.activity.zhihu.contract.Contract;
import com.xz.magicbox.constant.Local;
import com.xz.magicbox.custom.OnModelCallback;
import com.xz.magicbox.custom.OnRequestListener;
import com.xz.magicbox.network.NetUtil;
import com.xz.magicbox.utils.ThreadUtil;

import java.util.TimerTask;

public class Model implements Contract.Model {
    private String TAG = "Dayily.Model";

    private NetUtil net = new NetUtil();

    /**
     * 获取日报数据
     */
    @Override
    public void getDaily(final OnModelCallback listener) {
        ThreadUtil.runInThread(new TimerTask() {
            @Override
            public void run() {

                net.get_Sync(Local.GET_DAIYL_NEWS, new OnRequestListener() {

                    @Override
                    public void onSuccess(String body, String header) {
                        Logger.w( "日报内容: " +body);

                        listener.callback(body);

                    }

                    @Override
                    public void onFailed(String message, int code) {
                        Log.d(TAG, "onFailure: "+ code);
                        listener.onFailed("连接失败："+code+"\n"+message);

                    }

                    @Override
                    public void onFailed(String connectError) {
                        Log.d(TAG, "onFailed: 连接失败（网络或url错误)");
                        listener.onFailed("onFailed: 连接失败（网络或url错误)");
                    }
                });


            }
        });


    }


}
