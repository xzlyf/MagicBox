package com.xz.magicbox.adapter;

import android.content.Context;
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
import com.xz.magicbox.entity.Comment;
import com.xz.magicbox.entity.News;
import com.xz.magicbox.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private final Context mContext;
    private List<Comment> mlist;


    public CommentAdapter(Context context) {
        this.mContext = context;
        mlist = new ArrayList<>();
    }

    public void refresh(List<Comment> list) {
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
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.likes.setText(mlist.get(i).getLikes()+"");
        viewHolder.time.setText(TimeUtil.getSimDate("yyyy-MM-dd-HH:mm:ss",mlist.get(i).getTime()));
        viewHolder.userComment.setText(mlist.get(i).getContent());
        viewHolder.userId.setText(mlist.get(i).getAuthor());
        Glide.with(mContext).load(mlist.get(i).getAvatar()).into(viewHolder.userPhoto);
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
