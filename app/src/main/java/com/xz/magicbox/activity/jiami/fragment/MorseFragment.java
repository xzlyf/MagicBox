package com.xz.magicbox.activity.jiami.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xz.magicbox.R;
import com.xz.magicbox.utils.CopyUtil;
import com.xz.magicbox.utils.MorseCodeUtil;

public class MorseFragment extends Fragment {
    private Context mContext;
    private EditText fromText;
    private TextView result;
    private TextView clear;
    private TextView copy;
    private TextView paste;
    private LinearLayout layout;
    private TextView copyResult;
    private Button submit;
    private View view;
    private TextView tvTitle;
    private CopyUtil copyUtil;
    private MorseCodeUtil morseCodeUtil;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_general, null);
        initView();
        return view;
    }

    private void initView() {
        copyUtil = new CopyUtil(mContext);
        morseCodeUtil = new MorseCodeUtil();
        fromText = view.findViewById(R.id.from_text);
        submit = view.findViewById(R.id.submit);
        tvTitle = view.findViewById(R.id.tv_title);
        copy = view.findViewById(R.id.copy);
        clear = view.findViewById(R.id.clear);
        paste = view.findViewById(R.id.paste);
        result = view.findViewById(R.id.encrypyion_text);
        copyResult = view.findViewById(R.id.copy_result);
        layout = view.findViewById(R.id.morse_layout);

        tvTitle.setText("莫斯密码");

        //提交
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText(morseCodeUtil.encryp(fromText.getText().toString().trim()));
                layout.setVisibility(View.VISIBLE);
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

        //结果复制
        copyResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyUtil.copyToClicp(result.getText().toString());
            }
        });
    }
}
