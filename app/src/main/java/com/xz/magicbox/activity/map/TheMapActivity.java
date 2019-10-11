package com.xz.magicbox.activity.map;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.orhanobut.logger.Logger;
import com.xz.magicbox.R;
import com.xz.magicbox.base.BaseActivity;

public class TheMapActivity extends BaseActivity implements AMapLocationListener {


    private MapView map2d;
    private AMap aMap;
    private ActionBar bar;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private float mZoom = 15f;//当前缩放级别，默认10

    private Switch tarce;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        map2d.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map2d.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map2d.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map2d.onSaveInstanceState(outState);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        bar = getSupportActionBar();
        map2d = findViewById(R.id.map_2d);
        map2d.onCreate(getBundle());
        aMap = map2d.getMap();

        tarce = findViewById(R.id.trace);



        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(1000);//
        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }


    }

    /**
     * 事务
     */
    private void initEvent() {

        //设置一个当地图被点击时的回调方法。
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

//
//                //判断是否现需要隐藏标题和全屏
//                if (bar.isShowing()) {
//                    //隐藏标题栏
//                    bar.hide();
//                } else {
//                    //显示状态栏
//                    bar.show();
//                }


            }
        });

        //当用户长按地图时回调的接口。
        aMap.setOnMapLongClickListener(new AMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                //从地图上删除所有的Marker，Overlay，Polyline 等覆盖物。
//                aMap.clear();
                //latLng.latitude;//纬度 (垂直方向)
                //latLng.longitude;//经度 (水平方向)
                //添加一个遮挡物在长按的位置上
//                aMap.addMarker(
//                        new MarkerOptions().position(
//                                new LatLng(latLng.latitude,latLng.longitude)));


            }
        });

        //设置一个当地图上的Marker 被点击的回调方法。
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.w("xz", "onMarkerClick: " + marker.getTitle());
                return false;
            }
        });
        //设置一个可视范围变化时的回调的接口方法。
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                //存储当前所缩放级别
                mZoom = cameraPosition.zoom;
            }
        });

    }

    /**
     * 位置发生变化监听
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
            //定位成功
            Logger.d("当前坐标："+aMapLocation.getLatitude() + ":" + aMapLocation.getLongitude());
            //如果开启跟踪，但位置发生变化时视图会自动移动到当前位置
            if (tarce.isChecked()){
                //跳转到当前位置
                CameraUpdate mCameraUpdate =
                        CameraUpdateFactory.newCameraPosition(
                                new CameraPosition(
                                        new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), mZoom, 30, 0));

                aMap.animateCamera(mCameraUpdate);
            }
            //添加图钉
            aMap.addMarker(new MarkerOptions().position(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));

        } else {
            //定位失败
//            sDialog("错误码："+aMapLocation.getErrorCode(),	aMapLocation.getErrorInfo(), Local.TYPE_FAIL);
            Logger.w("定位失败：" + aMapLocation.getErrorCode(), aMapLocation.getErrorInfo());
        }


    }

}
