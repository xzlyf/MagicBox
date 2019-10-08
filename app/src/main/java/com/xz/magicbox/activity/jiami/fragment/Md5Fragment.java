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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.magicbox.R;
import com.xz.magicbox.custom.MoreListView;
import com.xz.magicbox.utils.CopyUtil;
import com.xz.magicbox.utils.MD5Util;
import com.xz.magicbox.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Md5Fragment extends Fragment {
    private Context mContext;
    private EditText fromText;
    private TextView clear;
    private TextView copy;
    private TextView paste;
    private Button submit;
    private View view;
    private TextView tvTitle;
    private MoreListView result;
    private ResultAdapter mAdapter;
    private CopyUtil copyUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_general, null);
        initView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initView() {
        copyUtil = new CopyUtil(mContext);
        fromText = view.findViewById(R.id.from_text);
        submit = view.findViewById(R.id.submit);
        tvTitle = view.findViewById(R.id.tv_title);
        result = view.findViewById(R.id.result_list);
        copy = view.findViewById(R.id.copy);
        clear = view.findViewById(R.id.clear);
        paste = view.findViewById(R.id.paste);
        mAdapter = new ResultAdapter(mContext);
        result.setAdapter(mAdapter);

        tvTitle.setText("MD5加密");

        //提交
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAdapter.refresh(MD5Util.getMoreMD5(fromText.getText().toString()));

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

    class ResultAdapter extends BaseAdapter {
        private Context mContext;
        List<String> mlist;
        List<String> title;
        private int i;

        public ResultAdapter(Context context) {
            mContext = context;
            mlist = new ArrayList<>();
            title = new ArrayList<>();
            title.add("16位 小写:");
            title.add("16位 大写:");
            title.add("32位 小写:");
            title.add("32位 大写:");
        }

        public void refresh(String[] ary) {
            mlist.clear();
            Collections.addAll(mlist, ary);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            i = position;
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_md5, null);

                holder = new ViewHolder();

                holder.tvTitle = convertView.findViewById(R.id.tv_title);
                holder.tvCopy = convertView.findViewById(R.id.tv_copy);
                holder.tvContent = convertView.findViewById(R.id.tv_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvContent.setText(mlist.get(position));
            holder.tvTitle.setText(title.get(position));
            holder.tvCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copyUtil.copyToClicp(mlist.get(position));
                    ToastUtil.Shows(mContext, "已复制");
                }
            });
            return convertView;


        }


        class ViewHolder {
            TextView tvTitle;
            TextView tvCopy;
            TextView tvContent;

        }
    }
}
