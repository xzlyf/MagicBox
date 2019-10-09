package com.xz.magicbox.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xz.magicbox.R;

public abstract class BaseFragment extends Fragment {
    private Context context;

    //布局id
    public abstract int getLayoutResource();

    //初始化数据
    public abstract void initData();

    //在这初始化控件
    public abstract void findViewById(View view);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResource(), null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        findViewById(view);
        initData();
    }

    @Override
    public Context getContext() {
        return context;
    }


}
