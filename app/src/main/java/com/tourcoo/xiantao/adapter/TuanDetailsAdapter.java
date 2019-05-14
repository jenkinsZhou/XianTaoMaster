package com.tourcoo.xiantao.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.tuan.TuanDetails;
import com.tourcoo.xiantao.util.FormatDuration;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//适配器
public class TuanDetailsAdapter extends RecyclerView.Adapter<TuanDetailsAdapter.ViewHolder> {

    private List<TuanDetails> mDatas;
    private Context context;

    public TuanDetailsAdapter(Context context, List<TuanDetails> datas) {
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>();
        }
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tuan_detail_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mDatas.size() == 1) {
            holder.itemView.setBackgroundResource(R.drawable.bg_radius_10_white);
        } else {
            if (position == 0) {
                holder.itemView.setBackgroundResource(R.drawable.bg_radius_10_white_top);
            } else if (position == mDatas.size() - 1) {
                holder.itemView.setBackgroundResource(R.drawable.bg_radius_10_white_bottom);
            }
        }


        final TuanDetails data = mDatas.get(position);
        holder.tvNickName.setText(data.getUserinfo().getNickname());
        holder.tvSurplus.setText(data.getWeigh() + "kg");
        holder.tvCreateTime.setText(data.getCreatetime_text());

        GlideManager.loadImg(data.getUserinfo().getAvatar(), holder.ivGoodsIcon, R.mipmap.img_default_avatar);

    }

    @Override
    public int getItemCount() {
        if (mDatas != null && !mDatas.isEmpty()) {
            return mDatas.size();
        }
        return 0;
    }

    public void setNewData(List<TuanDetails> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNickName;
        public TextView tvSurplus;
        public TextView tvCreateTime;
        public CircleImageView ivGoodsIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ivGoodsIcon = itemView.findViewById(R.id.ivAvatar);
            tvNickName = itemView.findViewById(R.id.tvNickName);
            tvSurplus = itemView.findViewById(R.id.tvSurplus);
            tvCreateTime = itemView.findViewById(R.id.tvCreateTime);
        }
    }

}