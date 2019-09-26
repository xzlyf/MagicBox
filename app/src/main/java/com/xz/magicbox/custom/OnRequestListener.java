package com.xz.magicbox.custom;

/**
 * 网络请求回调接口
 */
public interface OnRequestListener {
    void onSuccess(String body,String header);
    void onFailed(String message,int code );
    void onFailed(String connectError );
}
