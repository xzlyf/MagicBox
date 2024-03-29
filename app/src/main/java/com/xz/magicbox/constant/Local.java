package com.xz.magicbox.constant;

import android.Manifest;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.xz.magicbox.R;

public class Local {

    //硬件信息
    public static int width;//屏幕尺寸
    public static int height;//屏幕尺寸

    //对话框标识
    public static final int TYPE_FAIL = R.drawable.ic_error;//失败
    public static final int TYPE_WARN = R.drawable.ic_warnning;//异常
    public static final int TYPE_INFO =  R.drawable.ic_tips;//消息
    public static final int TYPE_SUCCESS =  R.drawable.ic_success;//成功

    public static final int TYPE_LOADING_1 =  R.drawable.loading_1;//加载动画
    public static final int TYPE_LOADING_2 =  R.drawable.loading_2;//加载动画
    public static final int TYPE_LOADING_3 =  R.drawable.loading_3;//加载动画

    //待申请权限列表
    public static final String[] permission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
//            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    //知乎接口
    public static final String GET_DAIYL_NEWS = "https://news-at.zhihu.com/api/4/news/latest";//日报api
    public static final String GET_DAIYL_CONTENT = "https://news-at.zhihu.com/api/4/news/";//日报内容
    public static final String GET_DAIYL_DETAIL = "https://news-at.zhihu.com/api/4/story-extra/";//日报细节
    public static final String GET_CONTENT_COMMENT = "https://news-at.zhihu.com/api/4/story/";//文章评论
    public static final String GET_CONTENT_COMMENT_TAIL = "/short-comments";//文章评论_尾部

}
