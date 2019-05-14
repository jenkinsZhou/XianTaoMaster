package com.tourcoo.xiantao.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.TimeUtils;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.util.FormatDuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//适配器
public class TuanListAdapter extends RecyclerView.Adapter<TuanListAdapter.ViewHolder> {

    private List<Goods.TuanListBean> mDatas;
    //用于退出activity,避免countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownMap;
    private Context context;

    public TuanListAdapter(Context context, List<Goods.TuanListBean> datas) {
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>();
        }
        mDatas = datas;
        countDownMap = new SparseArray<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tuan_list_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        for (int i = 0, length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
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


        final Goods.TuanListBean data = mDatas.get(position);
        holder.tvNickName.setText(data.getNickname());
        holder.tvSurplus.setText(data.getSurplus() + "kg");

        holder.btnJoinTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onJoinTuanClick(data.getId(), data.getNum(), data.getSurplus(), data.getDeadline() * 1000L);
                }
            }
        });

        GlideManager.loadImg(data.getAvatar(), holder.ivGoodsIcon, R.mipmap.img_default_avatar);

        long time = data.getDeadline() * 1000L;
        time = time - System.currentTimeMillis();
        //将前一个缓存清除
        if (holder.countDownTimer != null) {
            holder.countDownTimer.cancel();
        }
        if (time > 0) {
            holder.countDownTimer = new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    holder.tvEndTime.setText("剩余" + FormatDuration.format(new Long(millisUntilFinished).intValue()));
                }

                public void onFinish() {
                    holder.tvEndTime.setVisibility(View.INVISIBLE);
                    holder.btnJoinTuan.setText("已截止");
                    holder.btnJoinTuan.setEnabled(false);
                }
            }.start();

            countDownMap.put(holder.tvEndTime.hashCode(), holder.countDownTimer);
        } else {
            holder.tvEndTime.setVisibility(View.INVISIBLE);
            holder.btnJoinTuan.setText("已截止");
            holder.btnJoinTuan.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        if (mDatas != null && !mDatas.isEmpty()) {
            return mDatas.size();
        }
        return 0;
    }

    public void setNewData(List<Goods.TuanListBean> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNickName;
        public TextView tvSurplus;
        public TextView tvEndTime;
        public TextView btnJoinTuan;
        public CircleImageView ivGoodsIcon;
        public CountDownTimer countDownTimer;

        public ViewHolder(View itemView) {
            super(itemView);
            ivGoodsIcon = itemView.findViewById(R.id.ivAvatar);
            tvNickName = itemView.findViewById(R.id.tvNickName);
            tvSurplus = itemView.findViewById(R.id.tvSurplus);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            btnJoinTuan = itemView.findViewById(R.id.btnJoinTuan);

        }
    }

    private IOnItemClickListener listener;

    public void setIOnItemClickListener(IOnItemClickListener listener) {
        this.listener = listener;
    }

    public interface IOnItemClickListener {
        void onJoinTuanClick(int tuan_id, int num, String surplus, long time);
    }

}