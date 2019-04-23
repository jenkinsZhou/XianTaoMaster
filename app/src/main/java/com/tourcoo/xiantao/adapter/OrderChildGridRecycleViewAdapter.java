package com.tourcoo.xiantao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tourcoo.xiantao.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class OrderChildGridRecycleViewAdapter extends RecyclerView.Adapter<OrderChildGridRecycleViewAdapter.ViewHolder> {

    private List<Integer> data;
    private Context context;

    public OrderChildGridRecycleViewAdapter(Context context, List<Integer> data) {
        this.context = context;
        if (data == null) {
            data = new ArrayList<>();
        }
        this.data = data;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_grideview_image_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder horizontalViewHolder, int i) {
        Glide.with(context).load(data.get(i)).into(horizontalViewHolder.image);
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    /**
     * 嵌套的水平RecyclerView
     * 当条目被回收时，下次加载会重新回到之前的x轴
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }


    }

}