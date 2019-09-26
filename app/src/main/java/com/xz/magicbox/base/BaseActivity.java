package com.xz.magicbox.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.xz.magicbox.MyApplication;
import com.xz.magicbox.activity.contract.BaseView;
import com.xz.magicbox.constant.Local;
import com.xz.magicbox.custom.LoadingDialog;
import com.xz.magicbox.custom.TipsDialog;
import com.xz.magicbox.utils.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private TipsDialog tipDialog;
    private LoadingDialog loadingDialog;

    abstract protected int getLayoutResource();

    abstract protected boolean homeAsUpEnabled();

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        //设置是否开启返回homeAsUp按钮
        if (homeAsUpEnabled()) {
            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setHomeButtonEnabled(true);
                bar.setDisplayHomeAsUpEnabled(true);
            }
        }
        initData();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:  //id不要写错，前面要加android
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束Activity&从集合中移除
        MyApplication.getInstance().finishActivity(this);
    }

    @Override
    public void sToast(String text) {
        ToastUtil.Shows(this, text);
    }

    @Override
    public void lToast(String text) {
        ToastUtil.Shows_LONG(this, text);
    }

    @Override
    public void showLoading(String text) {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        loadingDialog = new LoadingDialog.Builder(this)
                .setTitle("加载中...")
                .setIcon(Local.TYPE_LOADING_2)
                .setCancelOnClickListeren(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadingDialog.dismiss();
                    }
                })
                .create();
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    @Override
    public void disLoading() {
        if (tipDialog != null || tipDialog.isShowing()) {
            tipDialog.dismiss();
        }
    }

    /**
     * 显示一个对话框，并指定类型
     *
     * @param msg
     * @param type
     */
    @Override
    public void sDialog(String title, String msg, int type) {
        if (tipDialog != null) {
            tipDialog.dismiss();
        }
        tipDialog = new TipsDialog.Builder(this)
                .setTitle(title)
                .setMeg(msg)
                .setIcon(type)
                .create();
        tipDialog.show();

    }

    /**
     * 销毁对话框
     */
    @Override
    public void dDialog() {
        if (tipDialog == null) {
            return;
        }
        if (tipDialog.isShowing())
            tipDialog.dismiss();
        if (tipDialog != null) {
            tipDialog = null;
        }
    }


    /**
     * 自动隐藏软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            View view = getCurrentFocus();
            if (isShouldHideInput(view, ev)) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
            return super.dispatchTouchEvent(ev);

        }

        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);

    }

    /**
     * 判断是否应该隐藏软键盘
     *
     * @param view
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View view, MotionEvent event) {
        if (view != null && (view instanceof EditText)) {
            int[] leftTop = {0, 0};
            view.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + view.getHeight();
            int right = left + view.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域
                return false;
            } else {
                //清除输入框焦点
                view.clearFocus();
                return true;
            }
        }
        return false;
    }


}
