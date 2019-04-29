package com.tourcoo.xiantao.ui.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.OrderGoodsDetailAdapter;
import com.tourcoo.xiantao.adapter.OrderGoodsSettleAdapter;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.order.OrderDetailEntity;
import com.tourcoo.xiantao.helper.GoodsCount;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.tourcoo.xiantao.ui.goods.GoodsDetailActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

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
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.goods.HomeFragment.EXTRA_GOODS_ID;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.NOT_USE_COIN;
import static com.tourcoo.xiantao.ui.order.ReturnGoodsActivity.EXTRA_GOODS_LIST;

/**
 * @author :JenkinsZhou
 * @description :订单详情页面
 * @company :途酷科技
 * @date 2019年04月28日10:28
 * @Email: 971613168@qq.com
 */
public class OrderDetailActivity extends BaseTourCooTitleMultiViewActivity implements View.OnClickListener {
    private LinearLayout llAddressInfo;
    public static final String EXTRA_ORDER_ID = "EXTRA_ORDER_ID";
    private RecyclerView goodsOrderRecyclerView;
    private OrderDetailEntity mOrderEntity;
    /**
     * 商品订单适配器
     */
    private OrderGoodsDetailAdapter mGoodsAdapter;

    private TextView tvNickName;
    private TextView tvMobile;
    private TextView tvAddress;
    private TextView tvPayPrice;
    private TextView tvOrderNumber;
    private TextView tvCreateTime;
    private TextView tvCoin;

    private TextView tvExpressPrice;
    /**
     * 订单id
     */
    private int orderId;

    private LinearLayout llRootView;

    private TextView tvTotalPrice;

    private TextView tvRemark;

    private TextView tvGoodsTypeCount;

    private LinearLayout llRemark;
    private TextView tvPayNow;
    private TextView tvCommentNow;
    private TextView tvLookExpress;
    private TextView tvReturn;
    private TextView tvCancelOrder;
    private TextView tvConfirmReceive;

    private LinearLayout llBottomToolBar;


    @Override
    public int getContentLayout() {
        return R.layout.activity_order_details;
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("订单详情");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvCoin = findViewById(R.id.tvCoin);
        llBottomToolBar = findViewById(R.id.llBottomToolBar);
        tvCommentNow = findViewById(R.id.tvCommentNow);
        tvConfirmReceive = findViewById(R.id.tvConfirmReceive);
        tvCancelOrder = findViewById(R.id.tvCancelOrder);
        llRemark = findViewById(R.id.llRemark);
        tvPayNow = findViewById(R.id.tvPayNow);
        tvLookExpress = findViewById(R.id.tvLookExpress);
        tvReturn = findViewById(R.id.tvReturn);
        tvCommentNow = findViewById(R.id.tvCommentNow);
        tvCommentNow.setOnClickListener(this);
        tvCancelOrder.setOnClickListener(this);
        tvConfirmReceive.setOnClickListener(this);
        tvPayNow.setOnClickListener(this);
        tvReturn.setOnClickListener(this);
        goodsOrderRecyclerView = findViewById(R.id.goodsOrderRecyclerView);
        goodsOrderRecyclerView = findViewById(R.id.goodsOrderRecyclerView);
        goodsOrderRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        tvGoodsTypeCount = findViewById(R.id.tvGoodsTypeCount);
        tvRemark = findViewById(R.id.tvRemark);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvExpressPrice = findViewById(R.id.tvExpressPrice);
        llRootView = findViewById(R.id.llRootView);
        llAddressInfo = findViewById(R.id.llAddressInfo);
        tvNickName = findViewById(R.id.tvNickName);
        tvAddress = findViewById(R.id.tvAddress);
        tvPayPrice = findViewById(R.id.tvPayPrice);
        tvMobile = findViewById(R.id.tvMobile);
        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        tvCreateTime = findViewById(R.id.tvCreateTime);
        orderId = getIntent().getIntExtra(EXTRA_ORDER_ID, -1);
        TourCooLogUtil.i(TAG, TAG + "订单详情id:" + orderId);
    }

    @Override
    public void loadData() {
        super.loadData();
        initAdapter();
        requestOrderDetail();
    }

    private OrderDetailEntity parseOrderDetailEntity(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseObject(info, OrderDetailEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    /**
     * 获取订单详情
     */
    private void requestOrderDetail() {
        ApiRepository.getInstance().requestOrderDetail(orderId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    LogUtils.i(JSON.toJSONString(entity.data));
                                    OrderDetailEntity orderEntity = parseOrderDetailEntity(entity.data);
                                    if (orderEntity != null && orderEntity.getOrder() != null) {
                                        //todo 显示订单详情
                                        //转换一下订单状态
                                        parseOrderStatus(orderEntity);
                                        mOrderEntity = orderEntity;
                                        TourCooLogUtil.i(TAG, TAG + "orderEntity状态:" + orderEntity.getOrder().getOrder_status());
                                        showOrderDetail(orderEntity);
                                    } else {
                                        ToastUtil.showFailed(entity.msg);
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    @Override
    protected IMultiStatusView getMultiStatusView() {
        return new IMultiStatusView() {
            @Override
            public View getMultiStatusContentView() {
                return llRootView;
            }

            @Override
            public void setMultiStatusView(StatusLayoutManager.Builder statusView) {

            }

            @Override
            public View.OnClickListener getEmptyClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                };
            }

            @Override
            public View.OnClickListener getErrorClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                };
            }

            @Override
            public View.OnClickListener getCustomerClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                };
            }
        };
    }


    private void parseOrderStatus(OrderDetailEntity orderDetailEntity) {
        if (orderDetailEntity == null) {
            return;
        }
        OrderDetailEntity.OrderBean orderInfo = orderDetailEntity.getOrder();
        //待付款状态
        if (orderInfo.getPay_status() == NOT_FINISH) {
            orderInfo.setOrder_status(ORDER_STATUS_WAIT_PAY);
            TourCooLogUtil.i(TAG, TAG + "订单id:" + orderInfo.getId());
        } else {
            //判断 待发货
            switch (orderInfo.getFreight_status()) {
                case NOT_FINISH:
                    //待发货
                    orderInfo.setOrder_status(ORDER_STATUS_WAIT_SEND);
                    TourCooLogUtil.i(TAG, TAG + "订单id:" + orderInfo.getId());
                    break;
                case FINISH:
                    //已经发货 判断待收货状态
                    if (orderInfo.getReceipt_status() == NOT_FINISH) {
                        orderInfo.setOrder_status(ORDER_STATUS_WAIT_RECIEVE);
                        TourCooLogUtil.i(TAG, TAG + "订单id:" + orderInfo.getId());
                    } else {
                        //已经收货 判断 是否评论
                        if (orderInfo.getComment_status() == NOT_FINISH) {
                            //todo 待评论
                            orderInfo.setOrder_status(ORDER_STATUS_WAIT_COMMENT);
                            TourCooLogUtil.i(TAG, TAG + "订单id:" + orderInfo.getId());
                        } else {
                            //已经评价
                            //todo 已完成
                            orderInfo.setOrder_status(ORDER_STATUS_FINISH);
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
                    orderInfo.setOrder_status(ORDER_STATUS_BACK_ING);
                    break;
                case 30:
                    //已经退货
                    orderInfo.setOrder_status(ORDER_STATUS_BACK_FINISH);
                    break;
                case 40:
                    //退货被拒绝
                    orderInfo.setOrder_status(ORDER_STATUS_BACK_REFUSE);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 显示订单详情
     *
     * @param orderDetailEntity
     */
    private void showOrderDetail(OrderDetailEntity orderDetailEntity) {
        OrderDetailEntity.OrderBean orderBean = orderDetailEntity.getOrder();
        showAddressInfo(orderBean.getAddress());
        showCoin(orderDetailEntity);
        //显示运费
        tvExpressPrice.setText("￥ " + orderBean.getExpress_price());
        //商品合计
        tvTotalPrice.setText("￥ " + orderBean.getTotal_price());
        tvPayPrice.setText("￥ " + orderBean.getPay_price());
        tvOrderNumber.setText(orderBean.getOrder_no());
        tvCreateTime.setText(DateUtil.parseDate(orderBean.getCreatetime()));
        //显示备注
        showRemark(orderBean);
        //商品数量
        String amount = "共" + orderBean.getGoods().size() + "件商品";
        tvGoodsTypeCount.setText(amount);
        List<OrderDetailEntity.OrderBean.GoodsBean> goodsList = orderBean.getGoods();
        mGoodsAdapter.setNewData(goodsList);
        loadBottomButtonFunction(orderBean);
    }


    /**
     * 显示地址区域信息
     *
     * @param addressEntity
     */
    private void showAddressInfo(AddressEntity addressEntity) {
        if (addressEntity == null || addressEntity.getArea() == null) {
            llAddressInfo.setVisibility(View.GONE);
            return;
        }
        llAddressInfo.setVisibility(View.VISIBLE);
        tvNickName.setText(addressEntity.getName());
        tvMobile.setText(addressEntity.getPhone());
        tvAddress.setText(AccountInfoHelper.getInstance().getWholeAddressInfo(addressEntity));
    }


    /**
     * 显示抵扣金币
     *
     * @param orderDetailEntity
     */
    private void showCoin(OrderDetailEntity orderDetailEntity) {
        double coin = orderDetailEntity.getOrder().getCoin();
        if (orderDetailEntity.getOrder().getCoin_status() == NOT_USE_COIN) {
            tvCoin.setText("-￥" + 0.00);
        } else {
            //使用了金币抵扣
            tvCoin.setText("-￥" + coin);
        }
    }


    private void initAdapter() {
        mGoodsAdapter = new OrderGoodsDetailAdapter();
        mGoodsAdapter.bindToRecyclerView(goodsOrderRecyclerView);
    }


    private void showRemark(OrderDetailEntity.OrderBean orderBean) {
        String remark = TourCooUtil.getNotNullValue(orderBean.getRemark());
        if (TextUtils.isEmpty(remark)) {
            llRemark.setVisibility(View.GONE);
        } else {
            llRemark.setVisibility(View.VISIBLE);
            tvRemark.setText(TourCooUtil.getNotNullValue(orderBean.getRemark()));
        }
    }

    private void loadBottomButtonFunction(OrderDetailEntity.OrderBean orderBean) {
        if (orderBean == null) {
            return;
        }
        switch (orderBean.getOrder_status()) {
            case ORDER_STATUS_WAIT_PAY:
                //待支付
                hideView(tvLookExpress);
                hideView(tvReturn);
                hideView(tvCommentNow);
                hideView(tvConfirmReceive);
                showView(tvCancelOrder);
                showView(tvPayNow);
                break;
            case ORDER_STATUS_WAIT_SEND:
                //待发货
                hideView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvConfirmReceive);
                hideView(tvLookExpress);
                hideView(tvReturn);
                hideView(tvCancelOrder);

                showView(tvReturn);
                break;
            case ORDER_STATUS_WAIT_RECIEVE:
                //待收货
                hideView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvCancelOrder);
                showView(tvReturn);
                showView(tvLookExpress);
                showView(tvConfirmReceive);
                break;
            case ORDER_STATUS_WAIT_COMMENT:
                //待评价
                showView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvCancelOrder);
                hideView(tvReturn);
                hideView(tvLookExpress);
                hideView(tvConfirmReceive);
                break;
            case ORDER_STATUS_FINISH:
                hideView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvCancelOrder);
                hideView(tvReturn);
                hideView(tvLookExpress);
                hideView(tvConfirmReceive);
                break;
            default:
                break;
        }
    }


    private void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    private void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //取消订单
            case R.id.tvCancelOrder:
                showCancelOrderDialog();
                break;
            //立即支付
            case R.id.tvPayNow:


                break;
            //申请退货
            case R.id.tvReturn:
                skipReturnGoods(mOrderEntity.getOrder());
                break;

            //查看物流
            case R.id.tvLookExpress:

                break;

            //确认收货
            case R.id.tvConfirmReceive:

                break;
            //立即评价
            case R.id.tvCommentNow:
                break;
            default:
                break;
        }


    }


    /**
     * 确认取消订单
     */
    private void showCancelOrderDialog() {
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(
                mContext);
        builder.setPositiveButtonPosition(ConfirmDialog.RIGHT);
        builder.setTitle("取消订单");
        builder.setMessageGravity(Gravity.CENTER_HORIZONTAL);
        builder.setFirstMessage("是否取消订单?").setNegativeButtonButtonBold(true);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestCancelOrder(orderId);
            }
        });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }


    /**
     * 取消订单
     *
     * @param orderId
     */
    private void requestCancelOrder(int orderId) {
        TourCooLogUtil.i(TAG, TAG + "操作的订单id:" + orderId);
        ApiRepository.getInstance().requestCancelOrder(orderId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    refreshRequest();
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 刷新请求
     */
    private void refreshRequest() {
        requestOrderDetail();
    }

    /**
     * 跳转至退货页面
     *
     * @param
     */
    private void skipReturnGoods(OrderDetailEntity.OrderBean orderBean) {
        Intent intent = new Intent();
        List<Goods> goodsList = parseGoodsList(orderBean);
        intent.putExtra(EXTRA_GOODS_LIST, (Serializable) goodsList);
        intent.putExtra(EXTRA_ORDER_ID, orderBean.getId());
        intent.setClass(mContext, ReturnGoodsActivity.class);
        startActivity(intent);
    }


    private List<Goods> parseGoodsList(OrderDetailEntity.OrderBean orderBean) {
        List<Goods> goodsList = new ArrayList<>();
        if (orderBean == null) {
            return goodsList;
        }
        Goods currentGoods;
        for (OrderDetailEntity.OrderBean.GoodsBean good : orderBean.getGoods()) {
            currentGoods = new Goods();
            currentGoods.setGoods_name(good.getGoods_name());
            currentGoods.setGoods_price(good.getGoods_price());
            currentGoods.setGoods_id(good.getGoods_id());
            currentGoods.setTotal_num(good.getTotal_num());
            currentGoods.setImage(good.getImage());
            goodsList.add(currentGoods);
        }
        return goodsList;
    }


}


