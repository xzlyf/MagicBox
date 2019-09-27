package com.xz.magicbox.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xz.magicbox.R;
import com.xz.magicbox.custom.OnItemClickListener;
import com.xz.magicbox.entity.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private final Context mContext;
    private List<News> mlist;
    private OnItemClickListener mListener;


    public NewsAdapter(Context context) {
        this.mContext = context;
        mlist = new ArrayList<>();
    }

    public void refresh(List<News> list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanAll() {
        mlist.clear();
        notifyDataSetChanged();
    }

    public void delete(int position) {
        mlist.remove(position);
        notifyDataSetChanged();
    }
    public void setItemOnClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_news, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mlist.get(i).getTitle());
        viewHolder.hint.setText(mlist.get(i).getHint());
        Glide.with(mContext).load(mlist.get(i).getImg()).into(viewHolder.tvImg);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView hint;
        TextView title;
        ImageView tvImg;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            hint = itemView.findViewById(R.id.hint);
            title = itemView.findViewById(R.id.tv_title);
            tvImg = itemView.findViewById(R.id.tv_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mListener.onClick(mlist.get(getLayoutPosition()).getId());
                    mListener.onClick(mlist.get(getLayoutPosition()).getUrl());
                }
            });
        }
    }
}
