package com.xz.magicbox.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xz.magicbox.R;
import com.xz.magicbox.entity.Func;

import java.util.ArrayList;
import java.util.List;

public class FunctionAdapter extends RecyclerView.Adapter<FunctionAdapter.ViewHolder> {
    private Context mContext;
    List<Func> mlist;

    public FunctionAdapter(Context context) {
        mContext = context;
        mlist = new ArrayList<>();

    }

    public void refresh(List list) {
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    public void cleanAll() {
        mlist.clear();
    }

    public void delete(int position) {
        mlist.remove(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_function_list, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mlist.get(i).getTitle());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,mlist.get(getLayoutPosition()).getmClass()));
                }
            });
        }
    }

}
