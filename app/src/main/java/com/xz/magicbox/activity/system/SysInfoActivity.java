package com.xz.magicbox.activity.system;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.SystemClock;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.widget.TextView;

import com.xz.magicbox.MyApplication;
import com.xz.magicbox.R;
import com.xz.magicbox.base.BaseActivity;
import com.xz.magicbox.utils.TimeUtil;

import java.util.Arrays;

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
        showLoading("正在加载...");
        autoLine("------------系统信息-------------");
        autoLine("主板：" + Build.BOARD);
        autoLine("系统定制商：" + Build.BRAND);
        autoLine("CPU指令集：" + Build.SUPPORTED_ABIS);
        autoLine("设备参数：" + Build.DEVICE);
        autoLine("显示屏参数：" + Build.DISPLAY);
        autoLine("唯一编号：" + Build.FINGERPRINT);
        autoLine("硬件序列号：" + Build.SERIAL);
        autoLine("修订版本列表：" + Build.ID);
        autoLine("硬件制造商：" + Build.MANUFACTURER);
        autoLine("版本：" + Build.MODEL);
        autoLine("硬件名：" + Build.HARDWARE);
        autoLine("手机产品名：" + Build.PRODUCT);
        autoLine("描述build的标签：" + Build.TAGS);
        autoLine("Builder类型：" + Build.TYPE);
        autoLine("当前开发代号：" + Build.VERSION.CODENAME);
        autoLine("源码控制版本号：" + Build.VERSION.INCREMENTAL);
        autoLine("版本字符串：" + Build.VERSION.RELEASE);
        autoLine("版本号：" + Build.VERSION.SDK_INT);
        autoLine("HOST值：" + Build.HOST);
        autoLine("User名：" + Build.USER);
        autoLine("编译时间：--" + TimeUtil.getSimMilliDate("yyyy-MM-dd HH:mm:ss", Build.TIME));
        autoLine("------------网络信息-------------");
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wm.getConnectionInfo();
        autoLine("服务集标识符:" + wifiInfo.getBSSID());
        autoLine("返回当前802.11网络的服务集标识符:" + wifiInfo.getSSID());
        autoLine("IP地址：" + wifiInfo.getIpAddress());
        autoLine("隐藏SSID:" + (wifiInfo.getHiddenSSID()));
        autoLine("链接速度：" + (wifiInfo.getLinkSpeed()) + "Mbps");
        autoLine("对请求方执行操作时标识网络：" + (wifiInfo.getNetworkId()));
        autoLine("信号强度指示符：" + (wifiInfo.getRssi()));
        autoLine("请求者与访问点进行协商的详细状态:" + (wifiInfo.getSupplicantState()));
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();
        NetworkInfo networkInfo = cm.getNetworkInfo(network);
        autoLine("粗粒度状态：" + Arrays.toString(NetworkInfo.State.values()));
        autoLine("当前细粒度状态：" + networkInfo.getDetailedState());
        autoLine("网络状态的额外信息：" + networkInfo.getExtraInfo());
        autoLine("所属的网络类型名称：" + networkInfo.getTypeName());
        autoLine("是否可以建立网络连接：" + networkInfo.isAvailable());
        autoLine("是否存在网络连接：" + networkInfo.isConnected());

        disLoading();
    }

    private void autoLine(String s) {
        outPanel.append(s + "\n");
    }

}
