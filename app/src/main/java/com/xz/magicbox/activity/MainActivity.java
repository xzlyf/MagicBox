package com.xz.magicbox.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.xz.magicbox.R;
import com.xz.magicbox.activity.jiami.EncryptionActivity;
import com.xz.magicbox.activity.qr_code.QRCodeActivity;
import com.xz.magicbox.activity.system.SysInfoActivity;
import com.xz.magicbox.activity.zhihu.DailyRe;
import com.xz.magicbox.adapter.FunctionAdapter;
import com.xz.magicbox.base.BaseActivity;
import com.xz.magicbox.constant.Local;
import com.xz.magicbox.entity.Func;
import com.xz.magicbox.utils.SpacesItemDecorationVertical;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    private FunctionAdapter adapter;

    @BindView(R.id.function_list)
    RecyclerView functionList;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean homeAsUpEnabled() {
        return false;
    }

    @Override
    protected void initData() {
        initPermission();

        initRecycler();
        getScreenHW();

    }

    private void initRecycler() {

        adapter = new FunctionAdapter(this);
        functionList.setLayoutManager(new LinearLayoutManager(this));
        functionList.setAdapter(adapter);
        functionList.addItemDecoration(new SpacesItemDecorationVertical(10));
        List<Func> list = new ArrayList<>();
        list.add(new Func("二维码生成器",QRCodeActivity.class));
        list.add(new Func("知乎日报", DailyRe.class));
        list.add(new Func("系统信息", SysInfoActivity.class));
        list.add(new Func("加密", EncryptionActivity.class));
        adapter.refresh(list);

    }

    /**
     * 权限请求
     */
    private void initPermission() {

        if (Build.VERSION.SDK_INT >= 23) {

            boolean isExits = false;
            //权限列表在Local全局类里，需要增加权限去local里增加
            for (String s : Local.permission) {
                if (ContextCompat.checkSelfPermission(this, s) != PackageManager.PERMISSION_GRANTED) {
                    isExits = true;
                }
            }

            if (isExits) {

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("温馨提示")
                        .setMessage("请允许我使用以下权限，以确保程序正确运行！")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, Local.permission, 456);
                            }
                        })
                        .setNegativeButton("不同意", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lToast("程序可能出现数据保存不成功、闪退等异常!");
                            }
                        })
                        .create();

                dialog.show();
            }

        }

    }

    /**
     * 权限结果回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 456) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //成功
                } else {
                    //失败
                    Toast.makeText(this, permissions[i] + "权限申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    /**
     * 获取屏幕尺寸
     */
    private void getScreenHW() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        Local.width = outMetrics.widthPixels;
        Local.height = outMetrics.heightPixels;
    }

}
