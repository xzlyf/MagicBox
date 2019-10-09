package com.xz.magicbox.activity.jiami.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.xz.magicbox.R;
import com.xz.magicbox.base.BaseFragment;
import com.xz.magicbox.utils.CopyUtil;
import com.xz.magicbox.utils.RandomString;
import com.xz.magicbox.utils.ToastUtil;

public class RandomFragment extends BaseFragment{
    private Context mContext;
    private TextView tvTitle;
    private TextView clear;
    private TextView copy;
    private TextView paste;
    private TextView t1;
    private TextView t2;
    private EditText fromText;
    private Button submit;
    private CopyUtil copyUtil;
    private LinearLayout layout;
    private RadioGroup numberG;
    private RadioButton btn;

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_general;
    }

    @Override
    public void initData() {
        mContext = getContext();
        copyUtil = new CopyUtil(mContext);
        tvTitle.setText("随机数");
        t1.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        submit.setText("生成随机数");
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void findViewById(final View view) {

        tvTitle = view.findViewById(R.id.tv_title);
        clear = view.findViewById(R.id.clear);
        copy = view.findViewById(R.id.copy);
        t1 = view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        paste = view.findViewById(R.id.paste);
        fromText = view.findViewById(R.id.from_text);
        submit = view.findViewById(R.id.submit);
        layout = view.findViewById(R.id.random_layout);
        numberG = view.findViewById(R.id.number_type);
        //提交
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn = view.findViewById(numberG.getCheckedRadioButtonId());
                try {
                    fromText.setText(
                            RandomString.getRandomString(
                                    Integer.valueOf(
                                            btn.getText().toString().split("位")[0]), false));
                }catch (Exception e){
                    ToastUtil.Shows(mContext,"异常");
                }

            }
        });

        //清空
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromText.setText("");
            }
        });

        //复制
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyUtil.copyToClicp(fromText.getText().toString());
            }
        });
        //粘贴
        paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromText.setText(copyUtil.getClicp());
            }
        });
    }


}
