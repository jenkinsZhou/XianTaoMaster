package com.tourcoo.xiantao.ui.order;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.OrderListAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.base.activity.BaseActivity;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseRefreshFragment;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.threadpool.ThreadPoolManager;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.event.BaseEvent;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.order.OrderEntity;
import com.tourcoo.xiantao.entity.pay.WeiXinPay;
import com.tourcoo.xiantao.entity.user.CashEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.comment.EvaluationActivity;
import com.tourcoo.xiantao.widget.dialog.PayDialog;
import com.trello.rxlifecycle3.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_ALL;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_COMMENT;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_PAY;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_RECIEVE;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_SEND;
import static com.tourcoo.xiantao.constant.WxConfig.APP_ID;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_PAY_FRESH_FAILED;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_PAY_FRESH_SUCCESS;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_REFRESH_COMMENT;
import static com.tourcoo.xiantao.ui.order.OrderDetailActivity.EXTRA_ORDER_ID;
import static com.tourcoo.xiantao.ui.order.OrderDetailActivity.EXTRA_PIN_TAG;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.PAY_STATUS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.PAY_STATUS_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.SDK_PAY_FLAG;
import static com.tourcoo.xiantao.ui.order.ReturnGoodsActivity.EXTRA_GOODS_LIST;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_ALI;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_BALANCE;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_WE_XIN;

/**
 * @author :JenkinsZhou
 * @description :订单列表（全部状态）
 * @company :途酷科技
 * @date 2019年04月27日18:30
 * @Email: 971613168@qq.com
 */
public class OrderListFragment extends BaseRefreshFragment<OrderEntity.OrderInfo> {
    private IWXAPI api;
    private OrderListAdapter mAdapter;
    private int orderStatus = ORDER_STATUS_ALL;
    private int currentSelectPosition;
    private int currentOrderId;
    private int mPayType;
    private OrderEntity.OrderInfo currentOrder;
    private PaymentHandler paymentHandler = new PaymentHandler(this);
    /**
     * 订单详情
     */
    public static final String EXTRA_ORDER_STATUS = "EXTRA_ORDER_STATUS";

    @Override
    public int getContentLayout() {
        return R.layout.layout__smart_refresh;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDefaultPage = 1;
        mDefaultPageSize = 10;
        if (getArguments() == null) {
            ToastUtil.show("未获取到数据");
            return;
        }
        api = WXAPIFactory.createWXAPI(mContext, null);
        orderStatus = getArguments().getInt(EXTRA_ORDER_STATUS, -1);
        TourCooLogUtil.i(TAG, TAG + "订单状态:" + orderStatus);
        EventBus.getDefault().register(this);
    }

    @Override
    public OrderListAdapter getAdapter() {
        mAdapter = new OrderListAdapter();
        return mAdapter;
    }

    @Override
    public void loadData(int page) {
        requestOrderInfo(page);
    }

    @Override
    public void loadData() {
        super.loadData();
        initItemButtonClick();
    }

    private OrderEntity parseOrderEntity(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseObject(info, OrderEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }

    /**
     * 获取个人中心信息
     */
    private void requestOrderInfo(int page) {
        ApiRepository.getInstance().requestOrderInfo(orderStatus, page).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    LogUtils.i(JSON.toJSONString(entity.data));
                                    OrderEntity orderEntity = parseOrderEntity(entity.data);
                                    if (orderEntity != null) {
                                        UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), orderEntity.getData() == null ? new ArrayList<>() : orderEntity.getData(), null);
                                    } else {
                                        ToastUtil.showFailed(entity.msg);
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                mRefreshLayout.finishRefresh(false);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mRefreshLayout.finishRefresh(false);
                        mStatusManager.showErrorLayout();
                    }
                });
    }


    public static OrderListFragment newInstance(int orderStatus) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ORDER_STATUS, orderStatus);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 初始化item按钮点击事件
     */
    private void initItemButtonClick() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OrderEntity.OrderInfo orderInfo = mAdapter.getData().get(position);
                currentOrder = orderInfo;
                currentOrderId = orderInfo.getId();
                currentSelectPosition = position;
                boolean pin = orderInfo.getTuan() == 1;
                switch (view.getId()) {
                    case R.id.photoRecyclerView:
                    case R.id.llOrderInfo:
                        skipOrderDetail(orderInfo.getId(), pin);
                        break;
                    case R.id.btnOne:
//                        ToastUtil.show("1");
                        break;
                    case R.id.btnTwo:
                        setButton2Function(orderInfo);
                        break;
                    case R.id.btnThree:
                        setButton3Function(orderInfo);
                        break;
                    case R.id.btnFour:
                        loadButton4Function(orderInfo);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 跳转到订单详情
     *
     * @param orderId
     */
    private void skipOrderDetail(int orderId, boolean isPin) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        intent.setClass(mContext, OrderDetailActivity.class);
        if (isPin) {
            //表示当前是拼团订单
            intent.putExtra(EXTRA_PIN_TAG, 1);
        }
        TourCooLogUtil.i(TAG, TAG + ":" + "订单状态:" + orderStatus);
        TourCooLogUtil.i(TAG, TAG + ":" + "订单id:" + orderId);
        startActivityForResult(intent, orderStatus);
    }


    private void loadButton4Function(OrderEntity.OrderInfo orderInfo) {
        switch (orderInfo.getOrder_status()) {
            case ORDER_STATUS_WAIT_SEND:
                //先判断是否是拼团订单
                int pin = orderInfo.getTuan();
                //1表示拼团订单
                if (pin == 1) {
                    //查看详情
                    skipOrderDetail(orderInfo.getId(), true);
                } else {
                    //申请退单
                    skipReturnGoods(orderInfo);
                }
                break;
            case ORDER_STATUS_WAIT_COMMENT:
                //待评价状态 去评价
                skipEvaluation(orderInfo);
                break;
            case ORDER_STATUS_WAIT_PAY:
                //立即支付
                requestBalanceAndShowPayDialog();
                break;
            case ORDER_STATUS_WAIT_RECIEVE:
                //确认收货
                showConfirmFinishDialog();
                break;
            case ORDER_STATUS_FINISH:
                //查看物流
                skipSeeLogistics(currentOrderId);
                break;
            default:
                break;
        }
    }


    /**
     * 跳转至退货页面
     *
     * @param
     */
    private void skipReturnGoods(OrderEntity.OrderInfo orderInfo) {
        Intent intent = new Intent();
        List<Goods> goodsList = orderInfo.getGoods();
        intent.putExtra(EXTRA_GOODS_LIST, (Serializable) goodsList);
        intent.putExtra(EXTRA_ORDER_ID, orderInfo.getId());
        intent.setClass(mContext, ReturnGoodsActivity.class);
        TourCooLogUtil.i(TAG, TAG + ":" + "订单状态");
        startActivityForResult(intent, orderStatus);
    }

    /**
     * 去评价
     *
     * @param orderInfo
     */
    private void skipEvaluation(OrderEntity.OrderInfo orderInfo) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderInfo.getId());
        TourCooLogUtil.i(TAG, TAG + ":" + "订单状态");
        intent.setClass(mContext, EvaluationActivity.class);
        startActivityForResult(intent, orderStatus);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //订单详情回调
                case ORDER_STATUS_WAIT_PAY:
                    TourCooLogUtil.i(TAG, TAG + ":" + "代付款回调");
                    mRefreshLayout.autoRefresh();
                    break;
                case ORDER_STATUS_WAIT_COMMENT:
                    mRefreshLayout.autoRefresh();
                    break;
                case ORDER_STATUS_WAIT_SEND:
                    mRefreshLayout.autoRefresh();
                    break;
                case ORDER_STATUS_WAIT_RECIEVE:
                    mRefreshLayout.autoRefresh();
                    break;
                case ORDER_STATUS_BACK:
                    mRefreshLayout.autoRefresh();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 取消订单
     *
     * @param orderId
     */
    private void requestCancelOrder(int orderId) {
        TourCooLogUtil.i(TAG, TAG + "操作的订单id:" + orderId);
        ApiRepository.getInstance().requestCancelOrder(orderId).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                ToastUtil.showSuccess("订单已取消");
                                refreshWaitPayList(currentSelectPosition);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 确认取消订单
     */
    private void showCancelOrderDialog(int orderId) {
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
     * 刷新待付款列表
     */
    private void refreshWaitPayList(int position) {
        if (orderStatus == ORDER_STATUS_ALL) {
            mRefreshLayout.autoRefresh();
        }
        if (orderStatus != ORDER_STATUS_WAIT_PAY) {
            TourCooLogUtil.e(TAG, TAG + ":" + "当前不是待付款列表");
            return;
        }
        if (position < 0 || position >= mAdapter.getData().size()) {
            return;
        }
        TourCooLogUtil.i(TAG, TAG + "当前操作的位置:" + position);
        mAdapter.remove(position);
        mAdapter.refreshNotifyItemChanged(position);
        if (mAdapter.getData().isEmpty()) {
            mRefreshLayout.autoRefresh();
        }

    }


    /**
     * 第二个按钮的功能
     */
    private void setButton2Function(OrderEntity.OrderInfo orderInfo) {
        TourCooLogUtil.i(TAG, TAG + ":" + "当前状态:" + orderInfo.getOrder_status());
        switch (orderInfo.getOrder_status()) {
            case ORDER_STATUS_WAIT_PAY:
//                showCancelOrderDialog(currentOrderId);
                break;
            case ORDER_STATUS_ALL:
//                showCancelOrderDialog(currentOrderId);
                break;
//            case ORDER_STATUS_WAIT_COMMENT:
            //确认收货
//                showConfirmFinishDialog();
//                break;
            case ORDER_STATUS_WAIT_RECIEVE:
                skipReturnGoods(orderInfo);
                break;
            default:
                break;
        }
    }

    /**
     * 第三个按钮的功能
     */
    private void setButton3Function(OrderEntity.OrderInfo orderInfo) {
        switch (orderInfo.getOrder_status()) {
            case ORDER_STATUS_WAIT_PAY:
                showCancelOrderDialog(currentOrderId);
                break;
            case ORDER_STATUS_ALL:
                showCancelOrderDialog(currentOrderId);
                break;
            case ORDER_STATUS_WAIT_COMMENT:
                //查看物流
                skipSeeLogistics(currentOrderId);
                break;
            case ORDER_STATUS_WAIT_RECIEVE:
                //查看物流
                skipSeeLogistics(currentOrderId);
                break;
            default:
                break;
        }
    }

    /**
     * 支付接口
     */
    private void createPay(int orderId, int payType) {
        ApiRepository.getInstance().requestOrderPay(orderId, payType).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            TourCooLogUtil.e("回调结果:", entity);
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                ThreadPoolManager.getThreadPoolProxy().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        switch (mPayType) {
                                            case PAY_TYPE_WE_XIN:
                                                weiChatPay(entity.data.toString());
                                                break;
                                            case PAY_TYPE_ALI:
                                                aliPay(entity.data.toString());
                                                break;
                                            case PAY_TYPE_BALANCE:
                                                TourCooLogUtil.i("支付结果", entity);
                                                ToastUtil.showSuccess("支付完成");
                                                autoRefresh();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private void showPayDialog(double money, double balance) {
        PayDialog payDialog = new PayDialog(mContext, money, balance, new PayDialog.PayListener() {
            @Override
            public void pay(int payType, Dialog dialog) {
                mPayType = payType;
                createPay(currentOrderId, payType);
                dialog.dismiss();
            }
        });
        payDialog.show();
    }


    /**
     * 微信支付
     *
     * @param payInfo
     */
    private void weiChatPay(String payInfo) {
        WeiXinPay weiXinPay = parseWeiXinPay(payInfo);
        if (weiXinPay != null) {
            PayReq req = new PayReq();
            req.appId = weiXinPay.getAppid();
            req.nonceStr = weiXinPay.getNoncestr();
            req.packageValue = "Sign=WXPay";
            req.partnerId = weiXinPay.getPartnerid();
            req.timeStamp = weiXinPay.getTimestamp();
            req.sign = weiXinPay.getSign();
            TourCooLogUtil.d("请求结果", weiXinPay);
            req.prepayId = weiXinPay.getPrepayid();
            api.registerApp(APP_ID);
            api.sendReq(req);
        } else {
            ToastUtil.showFailed("解析失败");
        }
    }

    private WeiXinPay parseWeiXinPay(String data) {
        if (data == null) {
            return null;
        }
        try {
            return JSON.parseObject(data, WeiXinPay.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "value:" + e.toString());
            return null;
        }
    }


    private void aliPay(String payInfo) {
        PayTask aliPay = new PayTask(mContext);
        Map<String, String> result = aliPay.payV2(payInfo, true);
        Message msg = new Message();
        msg.what = SDK_PAY_FLAG;
        msg.obj = result;
        paymentHandler.sendMessage(msg);
    }


    @SuppressWarnings("unchecked")
    private static class PaymentHandler extends Handler {
        private WeakReference<OrderListFragment> softReference;

        public PaymentHandler(OrderListFragment activity) {
            softReference = new WeakReference<OrderListFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    Map<String, String> result = (Map<String, String>) msg.obj;
                    for (Map.Entry<String, String> stringStringEntry : result.entrySet()) {
                        if (PAY_STATUS.equalsIgnoreCase(stringStringEntry.getKey())) {
                            boolean success = PAY_STATUS_SUCCESS.equals(stringStringEntry.getValue());
                            if (success) {
                                ToastUtil.showSuccess("支付完成");
                                softReference.get().mRefreshLayout.autoRefresh();
                            } else {
                                ToastUtil.showFailed("支付失败");
                            }
                            break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 查询账户信息并显示支付
     */
    private void requestBalanceAndShowPayDialog() {
        if (currentOrder == null) {
            ToastUtil.showFailed("未获取到订单信息");
            return;
        }
        try {
            double price = Double.parseDouble(currentOrder.getPay_price());
            ApiRepository.getInstance().requestBalance().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                    subscribe(new BaseObserver<BaseEntity<CashEntity>>() {
                        @Override
                        public void onRequestNext(BaseEntity<CashEntity> entity) {
                            if (entity != null) {
                                if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                    TourCooLogUtil.i(TAG, entity.data);
                                    double cash = entity.data.getCash();
                                    showPayDialog(price, cash);
                                } else {
                                    ToastUtil.showFailed(entity.msg);
                                }
                            }
                        }
                    });
        } catch (NumberFormatException e) {
            ToastUtil.showFailed("未获取到订单信息");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onPayEvent(BaseEvent event) {
        if (event == null) {
            TourCooLogUtil.e(TAG, "直接拦截");
            return;
        }
        switch (event.id) {
            case EVENT_ACTION_PAY_FRESH_SUCCESS:
                //支付成功
                mRefreshLayout.autoRefresh();
                break;
            case EVENT_ACTION_PAY_FRESH_FAILED:
//                skipToOrderListAndFinish();
                ToastUtil.showFailed("支付失败");
                break;
            case EVENT_ACTION_REFRESH_COMMENT:
                 TourCooLogUtil.i(TAG,TAG+":"+ "刷新评价");
                mRefreshLayout.autoRefresh();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (api != null) {
            api.detach();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /**
     * 确认收货确认框
     */
    private void showConfirmFinishDialog() {
        //删除地址
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(mContext);
        builder.setTitle("确认收货").setFirstMessage("是否确认收货？")
                .setFirstMsgSize(15).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestConfirmFinish(currentOrderId);
                        dialog.dismiss();
//                        ApiRepository.getInstance().updateApp()
                    }
                });
        showConfirmDialog(builder);
    }


    /**
     * 确认收货
     */
    private void requestConfirmFinish(int orderId) {
        TourCooLogUtil.i(TAG, TAG + "订单id:" + orderId);
        ApiRepository.getInstance().requestConfirmFinish(orderId).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
//                                refreshRequest();
                                ToastUtil.showSuccess("收货完成");
                                mRefreshLayout.autoRefresh();
//                                setResult(RESULT_OK);
//                                finish();
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }

    /**
     * 确认弹窗
     *
     * @param builder
     */
    protected void showConfirmDialog(ConfirmDialog.Builder builder) {
        if (!mContext.isFinishing() && builder != null) {
            builder.create().show();
        }
    }


    private void autoRefresh() {
        paymentHandler.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.autoRefresh();
            }
        });
    }

    /**
     * 查看物流
     */
    private void skipSeeLogistics(int orderId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        intent.setClass(mContext, SeeLogisticsActivity.class);
        startActivity(intent);
    }
}
