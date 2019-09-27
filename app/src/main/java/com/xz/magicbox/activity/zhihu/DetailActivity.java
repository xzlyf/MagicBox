package com.xz.magicbox.activity.zhihu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.xz.magicbox.R;
import com.xz.magicbox.base.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class DetailActivity extends BaseActivity {
    private String id ;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_detail;
    }

    @Override
    protected boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");


        //以下测试======//待完成  html解析 20190927
        try {
            Document doc = Jsoup.connect(id).get();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
