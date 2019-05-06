package com.tourcoo.xiantao.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.CountDownTimer;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
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

    private static final int TUAN_STATUS_FAIL = 0;
    private static final int TUAN_STATUS_RUNNING = 1;
    private static final int TUAN_STATUS_COMPLETE = 2;

    //1我发起的  2我参与的
    private int type = 1;

    public MyTuanListAdapter(Context context, List<TuanEntity.DataBean> datas) {
        this.context = context;
        if (datas == null) {
            datas = new ArrayList<>();
        }
        mDatas = datas;
        countDownMap = new SparseArray<>();

    }

    public void setNewData(List<TuanEntity.DataBean> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    public void addMoreItem(List<TuanEntity.DataBean> data) {
        this.mDatas.addAll(data);
        notifyDataSetChanged();
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_tuan_list_recycler_view_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        TuanEntity.DataBean item = mDatas.get(position);
        if (item == null) {
            return;
        }
        TuanEntity.DataBean.GoodsBean goodsBean = item.getGoods();
        if (goodsBean == null) {
            return;
        }
        GlideManager.loadImg(goodsBean.getImage(), holder.ivGoodsImage);
        holder.tvGoodsName.setText(goodsBean.getGoods_name());
        holder.tvLable.setText(goodsBean.getLabel());
        holder.tvTuanRuleName.setText(item.getTuan_rule().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
//                    ToastUtil.show("支付状态：" + mDatas.get(position).getUser_status() + "\n" +
//                            "订单状态 " + mDatas.get(position).getStatus());
                    listener.onItemClick(mDatas.get(position).getId());
                }

            }
        });

        switch (item.getStatus()) {
            case TUAN_STATUS_FAIL:
                holder.ivStatus.setImageResource(R.mipmap.ic_failure);
                holder.llStatus.setVisibility(View.GONE);
                holder.tvEndTime.setVisibility(View.GONE);
                holder.tvTuanStatus.setVisibility(View.GONE);
                holder.btnClick.setVisibility(View.GONE);
                holder.btnPay.setVisibility(View.GONE);
                break;
            case TUAN_STATUS_RUNNING:
                //进行中
                long time = item.getDeadline() * 1000L;
                time = time - System.currentTimeMillis();

                TourCooLogUtil.e(item);
                LogUtils.e(position, time);
                //已经过了截止时间 或者已经满团并支付    订单状态 ---> 已完成
                if ((Double.parseDouble(item.getTuan().getSurplus()) == 0.0 && item.getUser_status() == 1) || time <= 0) {
                    holder.ivStatus.setImageResource(R.mipmap.ic_completed);
                    holder.llStatus.setVisibility(View.GONE);
                    holder.tvEndTime.setVisibility(View.GONE);
                    holder.tvTuanStatus.setVisibility(View.GONE);
                    holder.btnClick.setVisibility(View.GONE);
                    holder.btnPay.setVisibility(View.GONE);
                } else {
                    holder.ivStatus.setImageResource(R.mipmap.ic_ongoing);
                    holder.llStatus.setVisibility(View.VISIBLE);
                    if (item.getUser_status() == 0) {
                        holder.btnPay.setVisibility(View.VISIBLE);
                        holder.btnPay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (listener != null) {
                                    listener.onPayClick(item.getTuanuser_id());
                                }
                            }
                        });
                    } else {
                        holder.btnPay.setVisibility(View.GONE);
                    }

                    holder.tvEndTime.setVisibility(View.VISIBLE);
                    holder.tvTuanStatus.setVisibility(View.VISIBLE);
                    double d = Double.parseDouble(item.getTuan().getSurplus());
                    if (d == 0) {
                        holder.btnClick.setVisibility(View.GONE);
                    } else {
                        holder.btnClick.setVisibility(View.VISIBLE);
                    }
                    holder.tvTuanStatus.setText(new SpanUtils()
                            .append("还差").setForegroundColor(TourCoolUtil.getColor(R.color.colorTextGray))
                            .append(item.getTuan().getSurplus() + "kg").setForegroundColor(TourCoolUtil.getColor(R.color.redTextCommon))
                            .append("成团").setForegroundColor(TourCoolUtil.getColor(R.color.colorTextGray))
                            .create());

                    holder.btnClick.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.onBtnClick(item.getId(), ConvertUtils.view2Bitmap(holder.ivGoodsImage));
                            }
                        }
                    });


                    //将前一个缓存清除
                    if (holder.countDownTimer != null) {
                        holder.countDownTimer.cancel();
                    }

                    holder.countDownTimer = new CountDownTimer(time, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            holder.tvEndTime.setText("剩余" + FormatDuration.format(new Long(millisUntilFinished).intValue()));
                        }

                        @Override
                        public void onFinish() {
                            holder.tvEndTime.setVisibility(View.GONE);
                        }
                    }.start();

                    countDownMap.put(holder.tvEndTime.hashCode(), holder.countDownTimer);

                }

                break;
            case TUAN_STATUS_COMPLETE:
                holder.ivStatus.setImageResource(R.mipmap.ic_completed);
                holder.llStatus.setVisibility(View.GONE);
                holder.tvEndTime.setVisibility(View.GONE);
                holder.tvTuanStatus.setVisibility(View.GONE);
                holder.btnClick.setVisibility(View.GONE);
                holder.btnPay.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (mDatas != null && !mDatas.isEmpty()) {
            return mDatas.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivStatus;
        private TextView tvTuanStatus;
        private TextView tvGoodsName;
        private TextView tvTuanRuleName;
        private TextView tvLable;
        private TextView tvEndTime;
        private TextView btnClick;
        private TextView btnPay;
        private LinearLayout llStatus;
        private RoundedImageView ivGoodsImage;
        private CountDownTimer countDownTimer;

        ViewHolder(View itemView) {
            super(itemView);
            ivStatus = itemView.findViewById(R.id.ivStatus);
            llStatus = itemView.findViewById(R.id.llStatus);
            tvTuanStatus = itemView.findViewById(R.id.tvTuanStatus);
            ivGoodsImage = itemView.findViewById(R.id.ivGoodsImage);
            tvGoodsName = itemView.findViewById(R.id.tvGoodsName);
            btnPay = itemView.findViewById(R.id.btnPay);
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
        void onItemClick(int tuan_id);

        void onBtnClick(int tuan_id, Bitmap bitmap);

        void onPayClick(int tuanuser_id);
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


