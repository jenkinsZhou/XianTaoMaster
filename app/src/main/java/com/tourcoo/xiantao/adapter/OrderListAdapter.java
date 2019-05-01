package com.tourcoo.xiantao.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.constant.OrderConstant;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.order.OrderEntity;

import java.util.ArrayList;
import java.util.List;

import static com.tourcoo.xiantao.constant.OrderConstant.FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.NOT_FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK_FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK_ING;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK_REFUSE;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_COMMENT;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_PAY;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_RECIEVE;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_SEND;


/**
 * @author :JenkinsZhou
 * @description :订单列表适配器
 * @company :途酷科技
 * @date 2019年04月27日19:17
 * @Email: 971613168@qq.com
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderEntity.OrderInfo, BaseViewHolder> {
    public OrderListAdapter() {
        super(R.layout.item_order_recycler_view_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderEntity.OrderInfo orderInfo) {
        RecyclerView commentImageRecyclerView = helper.getView(R.id.photoRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        helper.setText(R.id.tvPrice, "￥" + orderInfo.getPay_price());
        commentImageRecyclerView.setLayoutManager(layoutManager);
        List<Goods> goodsList = orderInfo.getGoods();
        List<String> imageList = new ArrayList<>();
        if (goodsList != null) {
            for (Goods goods : goodsList) {
                imageList.add(goods.getImage());
            }
        }
        GridImageAdapter gridImageAdapter = new GridImageAdapter(imageList);
        gridImageAdapter.bindToRecyclerView(commentImageRecyclerView);
        helper.getView(R.id.tvOrderNumber);
        helper.setText(R.id.tvNum, orderInfo.getGoods().size() + "");
        helper.setText(R.id.tvOrderNumber, orderInfo.getOrder_no());
        helper.setText(R.id.tvCreateTime, DateUtil.parseDate(orderInfo.getCreatetime()) + "");
        TextView tvOrderStatus = helper.getView(R.id.tvOrderStatus);
        TextView btnOne = helper.getView(R.id.btnOne);
        TextView btnTwo = helper.getView(R.id.btnTwo);
        TextView btnThree = helper.getView(R.id.btnThree);
        TextView btnFour = helper.getView(R.id.btnFour);
        helper.addOnClickListener(R.id.llOrderInfo);
        helper.addOnClickListener(R.id.btnOne);
        helper.addOnClickListener(R.id.btnTwo);
        helper.addOnClickListener(R.id.btnThree);
        helper.addOnClickListener(R.id.btnFour);
        helper.addOnClickListener(R.id.photoRecyclerView);
//        initItemButtonClick(helper);
        //待付款状态
        if (orderInfo.getPay_status() == NOT_FINISH) {
            orderInfo.setOrder_status(ORDER_STATUS_WAIT_PAY);
            tvOrderStatus.setText("待付款");
            hindView(btnOne);
            hindView(btnTwo);
            setTextGray(btnThree, "取消订单");
            setTextGray(btnFour, "立即支付");
            TourCooLogUtil.i(TAG, TAG + "订单id:" + orderInfo.getId());
        } else {
            //判断 待发货
            switch (orderInfo.getFreight_status()) {
                case NOT_FINISH:
                    //待发货
                    orderInfo.setOrder_status(ORDER_STATUS_WAIT_SEND);
                    tvOrderStatus.setText("待发货");
                    hindView(btnOne);
                    hindView(btnTwo);
                    hindView(btnThree);
                    setTextGray(btnFour, "申请退货");
                    TourCooLogUtil.i(TAG, TAG + "订单id:" + orderInfo.getId());
                    break;
                case FINISH:
                    //已经发货 判断待收货状态
                    if (orderInfo.getReceipt_status() == NOT_FINISH) {
                        tvOrderStatus.setText("待收货");
                        orderInfo.setOrder_status(ORDER_STATUS_WAIT_RECIEVE);
                        hindView(btnOne);
                        setTextGray(btnTwo, "申请退货");
                        setTextGray(btnThree, "查看物流");
                        setTextGray(btnFour, "确认收货");
                        TourCooLogUtil.i(TAG, TAG + "订单id:" + orderInfo.getId());
                    } else {
                        //已经收货 判断 是否评论
                        if (orderInfo.getComment_status() == NOT_FINISH) {
                            //todo 待评论
                            tvOrderStatus.setText("待评价");
                            orderInfo.setOrder_status(ORDER_STATUS_WAIT_COMMENT);
                            tvOrderStatus.setTextColor(TourCooUtil.getColor(R.color.greenCommon));
                            hindView(btnOne);
                            hindView(btnTwo);
                            hindView(btnThree);
                          /*  setTextGray(btnTwo, "申请退货");
                            setTextGray(btnThree, "查看物流");*/
                            setTextGray(btnFour, "立即评价");
                            TourCooLogUtil.i(TAG, TAG + "订单id:" + orderInfo.getId());
                        } else {
                            //已经评价
                            //todo 已完成
                            tvOrderStatus.setText("已完成");
                            orderInfo.setOrder_status(ORDER_STATUS_FINISH);
                            hindView(btnOne);
                            hindView(btnTwo);
                            hindView(btnThree);
                            hindView(btnFour);
//                            setTextGray(btnFour, "立即评价");
                            TourCooLogUtil.i(TAG, TAG + "订单id:" + orderInfo.getId());
                        }
                    }
                    break;
                default:
                    break;
            }
            switch (orderInfo.getReturn_status()) {
                //todo 退货中 20
                case FINISH:
                    setTextGreen(tvOrderStatus, "退货中");
                    orderInfo.setOrder_status(ORDER_STATUS_BACK_ING);
                    hindView(btnOne);
                    hindView(btnTwo);
                    hindView(btnThree);
                    setTextGray(btnFour, "查看详情");
                    break;
                case 30:
                    //已经退货
                    tvOrderStatus.setText("已退货");
                    tvOrderStatus.setTextColor(TourCooUtil.getColor(R.color.redTextCommon));
                    setTextRed(tvOrderStatus, "已退货");
                    orderInfo.setOrder_status(ORDER_STATUS_BACK_FINISH);
                    hindView(btnOne);
                    hindView(btnTwo);
                    hindView(btnThree);
                    hindView(btnFour);
                    break;
                case 40:
                    //退货被拒绝
                    setTextRed(tvOrderStatus, "退货被拒绝");
                    orderInfo.setOrder_status(ORDER_STATUS_BACK_REFUSE);
                    hindView(btnOne);
                    hindView(btnTwo);
                    hindView(btnThree);
                    hindView(btnFour);
                    break;
                default:
                    break;
            }
        }
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


