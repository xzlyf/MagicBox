package com.xz.magicbox.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.xz.magicbox.R;
import com.xz.magicbox.base.BaseActivity;
import com.xz.magicbox.constant.Local;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


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

            if (isExits){

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
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    //成功
                }else{
                    //失败
                    Toast.makeText(this, permissions[i]+"权限申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    public void codeBuilder(View view) {
        startActivity(new Intent(this, QRCodeActivity.class));
    }
}
