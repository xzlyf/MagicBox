package com.xz.magicbox.activity.map;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.orhanobut.logger.Logger;
import com.xz.magicbox.R;
import com.xz.magicbox.base.BaseActivity;
import com.xz.magicbox.utils.StatuBarColorUtls;
import com.xz.magicbox.utils.TransparentBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TheMapActivity extends BaseActivity {

    private MapView map2d;
    private AMap aMap;
    private ActionBar bar;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_the_map;
    }

    @Override
    protected boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    protected void initData() {
        initView();
        initEvent();


    }

    /**
     * 初始化控件
     */
    private void initView() {
        bar = getSupportActionBar();
        map2d = findViewById(R.id.map_2d);
        map2d.onCreate(getBundle());
        aMap = map2d.getMap();
    }

    /**
     * 事务
     */
    private void initEvent() {
        //设置一个当地图被点击时的回调方法。
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {


                //判断是否现需要隐藏标题和全屏
                if (bar.isShowing()) {
                    //隐藏标题栏
                    bar.hide();
                } else {
                    //显示状态栏
                    bar.show();
                }

            }
        });

        //当用户长按地图时回调的接口。
        aMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //latLng.latitude;//纬度 (垂直方向)
                //latLng.longitude;//经度 (水平方向)

                MarkerOptions options = new MarkerOptions();
                options.position(new LatLng(latLng.latitude,latLng.longitude));
                aMap.addMarker(options);
            }
        });

    }


}
