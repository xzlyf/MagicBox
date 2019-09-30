package com.xz.magicbox.activity.system;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.TextView;

import com.xz.magicbox.MyApplication;
import com.xz.magicbox.R;
import com.xz.magicbox.base.BaseActivity;

import butterknife.BindView;

public class SysInfoActivity extends BaseActivity {

    @BindView(R.id.out_panel)
    TextView outPanel;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_system_info;
    }

    @Override
    protected boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    protected void initData() {
        autoLine("   主板：" + Build.BOARD);
        autoLine("   系统定制商：" + Build.BRAND);
        autoLine("   CPU指令集：" + Build.SUPPORTED_ABIS);
        autoLine("  设备参数" + Build.DEVICE);
        autoLine("  显示屏参数" + Build.DISPLAY);
        autoLine("  唯一编号" + Build.FINGERPRINT);
        autoLine("  硬件序列号" + Build.SERIAL);
        autoLine("  修订版本列表" + Build.ID);
        autoLine("  硬件制造商" + Build.MANUFACTURER);
        autoLine("  版本" + Build.MODEL);
        autoLine("  硬件名 " + Build.HARDWARE);
        autoLine("  手机产品名 " + Build.PRODUCT);
        autoLine("  描述build的标签 " + Build.TAGS);
        autoLine("  Builder类型 " + Build.TYPE);
        autoLine("  当前开发代号 " + Build.VERSION.CODENAME);
        autoLine("  源码控制版本号 " + Build.VERSION.INCREMENTAL);
        autoLine("  版本字符串 " + Build.VERSION.RELEASE );
        autoLine("  版本号 " + Build.VERSION.SDK_INT  );
        autoLine("  HOST值 " + Build.HOST  );
        autoLine("  User名 " + Build.USER   );
        autoLine("  编译时间 " + Build.TIME    );

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wm.getConnectionInfo();
        autoLine(wifiInfo.getMacAddress());
        autoLine(wifiInfo.getBSSID());
        autoLine(wifiInfo.getSSID());
        autoLine(String.valueOf(wifiInfo.getIpAddress()));
        autoLine(String.valueOf(wifiInfo.getFrequency()));
        autoLine(String.valueOf(wifiInfo.getHiddenSSID()));
        autoLine(String.valueOf(wifiInfo.getLinkSpeed()));
        autoLine(String.valueOf(wifiInfo.getNetworkId()));
        autoLine(String.valueOf(wifiInfo.getRssi()));
        autoLine(String.valueOf(wifiInfo.getSupplicantState()));


    }

    private void autoLine(String s) {
        outPanel.append(s + "\n");
    }

}
