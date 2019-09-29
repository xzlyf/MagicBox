package com.xz.magicbox.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xz.magicbox.R;
import com.xz.magicbox.entity.News;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private final Context mContext;
    private List<News> mlist;


    public CommentAdapter(Context context) {
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userPhoto;
        TextView userId;
        TextView userComment;
        TextView likes;
        TextView time;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            userPhoto = itemView.findViewById(R.id.user_photo);
            userId = itemView.findViewById(R.id.user_id);
            userComment = itemView.findViewById(R.id.user_comment);
            likes = itemView.findViewById(R.id.likes);
            time = itemView.findViewById(R.id.time);
        }
    }
}
