package com.xz.magicbox.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决嵌套ListView只显示一个item
 */
public class MoreListView extends ListView {
    public MoreListView(Context context) {
        super(context);
    }

    public MoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
