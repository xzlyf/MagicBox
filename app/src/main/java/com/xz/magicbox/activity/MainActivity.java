package com.xz.magicbox.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.xz.magicbox.R;
import com.xz.magicbox.base.BaseActivity;

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
    }


    public void codeBuilder(View view) {
        startActivity(new Intent(this,QRCodeActivity.class));
    }
}
