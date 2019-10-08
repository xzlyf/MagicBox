package com.xz.magicbox.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不可左右滑动的viewpager
 */
public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 禁止滑动
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return  false;
    }


    /**
     * 禁止滑动
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 禁止动画
     * @param item
     * @param smoothScroll
     */
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, false);
    }
}
