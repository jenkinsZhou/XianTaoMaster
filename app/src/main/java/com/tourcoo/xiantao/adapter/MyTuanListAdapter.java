package com.tourcoo.xiantao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.order.OrderEntity;
import com.tourcoo.xiantao.entity.tuan.TuanEntity;
import com.tourcoo.xiantao.util.FormatDuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.tourcoo.xiantao.constant.OrderConstant.FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.NOT_FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK_FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK_ING;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK_REFUSE;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_COMMENT;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_PAY;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_RECIEVE;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_SEND;


/**
 * 拼团记录适配器
 */
public class MyTuanListAdapter extends RecyclerView.Adapter<MyTuanListAdapter.ViewHolder> {

    private List<TuanEntity.DataBean> mDatas;

    //用于退出activity,避免countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownMap;
    private Context context;

    public MyTuanListAdapter(Context context, List<TuanEntity.DataBean> datas) {
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>();
        }
        mDatas = datas;
        countDownMap = new SparseArray<>();
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
    public MyTuanListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_tuan_list_recycler_view_layout, parent, false);
        return new MyTuanListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        TuanEntity.DataBean item = mDatas.get(position);

        GlideManager.loadImg(item.getGoods().getImage(), holder.ivGoodsImage);

        holder.tvGoodsName.setText(item.getGoods().getGoods_name());
        holder.tvLable.setText(item.getGoods().getLabel());
        holder.tvTuanRuleName.setText(item.getTuan_rule().getName());
        holder.tvLable.setText(item.getGoods().getLabel());

        holder.tvTuanStatus.setText(new SpanUtils()
                        .append("还差").setForegroundColor(TourCoolUtil.getColor(R.color.colorTextGray))
                        .append(item.getTuan().getSurplus()+"kg").setForegroundColor(TourCoolUtil.getColor(R.color.redTextCommon))
                        .append("成团").setForegroundColor(TourCoolUtil.getColor(R.color.colorTextGray))
                        .create());

        holder.btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onBtnClick();
                }
            }
        });

        long time = item.getDeadline() * 1000L;
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
//                    holder.btnJoinTuan.setText("已截止");
//                    holder.btnJoinTuan.setEnabled(false);
                }
            }.start();

            countDownMap.put(holder.tvEndTime.hashCode(), holder.countDownTimer);
        } else {
            holder.tvEndTime.setVisibility(View.INVISIBLE);
//            holder.btnJoinTuan.setText("已截止");
//            holder.btnJoinTuan.setEnabled(false);
        }

    }

    @Override
    public int getItemCount() {
        if (mDatas != null && !mDatas.isEmpty()) {
            return mDatas.size();
        }
        return 0;
    }

    public void setNewData(List<TuanEntity.DataBean> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    public void addMoreItem(List<TuanEntity.DataBean> data) {
        this.mDatas.addAll(data);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTuanStatus;
        public TextView tvGoodsName;
        public TextView tvTuanRuleName;
        public TextView tvLable;
        public TextView tvEndTime;
        public TextView btnClick;
        public RoundedImageView ivGoodsImage;
        public CountDownTimer countDownTimer;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTuanStatus = itemView.findViewById(R.id.tvTuanStatus);
            ivGoodsImage = itemView.findViewById(R.id.ivGoodsImage);
            tvGoodsName = itemView.findViewById(R.id.tvGoodsName);
            tvTuanRuleName = itemView.findViewById(R.id.tvTuanRuleName);
            tvLable = itemView.findViewById(R.id.tvLable);
            tvEndTime = itemView.findViewById(R.id.tvEndTime);
            btnClick = itemView.findViewById(R.id.btnClick);
        }
    }

    private IOnItemClickListener listener;

    public void setIOnItemClickListener(IOnItemClickListener listener) {
        this.listener = listener;
    }

    public interface IOnItemClickListener {
        void onBtnClick();
    }


    private void setTextGreen(TextView text, String value) {
        text.setText(value);
        text.setTextColor(TourCooUtil.getColor(R.color.greenCommon));
    }

    private void setTextRed(TextView text, String value) {
        text.setText(value);
        text.setTextColor(TourCooUtil.getColor(R.color.redTextCommon));
    }


    private void setTextGray(TextView text, String value) {
        text.setText(value);
        text.setVisibility(View.VISIBLE);
    }


    private void setViewVisible(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }


    private void hindView(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    private void showView(View view) {
        view.setVisibility(View.INVISIBLE);
    }


}


