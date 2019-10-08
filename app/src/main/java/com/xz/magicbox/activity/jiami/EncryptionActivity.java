package com.xz.magicbox.activity.jiami;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.qmuiteam.qmui.widget.QMUIViewPager;
import com.xz.magicbox.R;
import com.xz.magicbox.activity.jiami.fragment.Md5Fragment;
import com.xz.magicbox.activity.jiami.fragment.MorseFragment;
import com.xz.magicbox.base.BaseActivity;
import com.xz.magicbox.custom.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EncryptionActivity extends BaseActivity {

    @BindView(R.id.spanner_select)
    Spinner spannerSelect;
    @BindView(R.id.view_fun)
    NoScrollViewPager viewFun;

    private List<Fragment> fragments;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_encryption;
    }

    @Override
    protected boolean homeAsUpEnabled() {
        return true;
    }

    @Override
    protected void initData() {
        setTitle("编码/加密");
        fragments = new ArrayList<>();

        fragments.add(new Md5Fragment());
        fragments.add(new MorseFragment());

        viewFun.setAdapter(new MyAdapter(getSupportFragmentManager()));
        spannerSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewFun.setCurrentItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // adapter
    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }
    }

}
