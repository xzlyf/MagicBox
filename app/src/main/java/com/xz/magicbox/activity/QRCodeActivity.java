package com.xz.magicbox.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.layout.QMUIButton;
import com.xz.magicbox.BuildConfig;
import com.xz.magicbox.R;
import com.xz.magicbox.base.BaseActivity;
import com.xz.magicbox.utils.ZXingQRUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRCodeActivity extends BaseActivity {
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.imgPreView)
    ImageView imgPreView;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.submit)
    Button submit;
    private int width;//屏幕尺寸
    private int height;//屏幕尺寸
    private Bitmap bitmap;
    private String path = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_qrcode;
    }

    @Override
    protected boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    protected void initData() {
        setTitle("二维码生成器");
        bitmap = ZXingQRUtils.createQRimg("魔盒，一个多功能的盒子", 512, 512);
        getScreenHW();
        //默认显示
        imgPreView.setImageBitmap(bitmap);//二维码
//        imgPreView.setImageBitmap(ZXingQRUtils.creatBarcode(this, "4461231321", 1024, 512, true));//条形码

    }

    /**
     * 获取屏幕尺寸
     */
    private void getScreenHW() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        width = outMetrics.widthPixels;
        height = outMetrics.heightPixels;
    }

    @OnClick(R.id.submit)
    public void submit() {
        if (content.getText().toString().trim().equals(""))
            return;
        bitmap = ZXingQRUtils.createQRimg(content.getText().toString(), 1024, 1024);
        imgPreView.setImageBitmap(bitmap);
    }

    @OnClick(R.id.save)
    public void save() {
        String dir = Environment.getExternalStorageDirectory() + "/MagicBox/QR_code/";
        File dirf = new File(dir);
        if (!dirf.exists()) {
            dirf.mkdirs();
        }
        path = dir + System.currentTimeMillis() + ".png";
        File file = new File(path);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();

            lToast("已保存至" + path);

            //保存图片后发送广播通知更新数据库
//            Uri uri = Uri.fromFile(file);
//            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

            String type = "application/vnd.android.package-archive";
            if (Build.VERSION.SDK_INT >= 24) {
                Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri).setType(type));
            } else {
                Uri uri = Uri.fromFile(file);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri).setType(type));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @OnClick(R.id.share)
    public void share() {
        if (path.equals("")) {
            sToast("请先保存");
            return;
        }
        Uri uri;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", new File(path));
        } else {
            uri = Uri.fromFile(new File(path));
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        Intent chooserIntent = Intent.createChooser(intent, "分享到:");
        startActivity(chooserIntent);
    }


}
