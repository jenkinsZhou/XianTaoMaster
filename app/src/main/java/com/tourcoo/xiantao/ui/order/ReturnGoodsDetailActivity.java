package com.tourcoo.xiantao.ui.order;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.previewlibrary.GPreviewBuilder;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.GridImageMatchContentAdapter;
import com.tourcoo.xiantao.adapter.OrderGoodsDetailAdapter;
import com.tourcoo.xiantao.constant.WxConfig;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.threadpool.ThreadPoolManager;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.ImageEntity;
import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.event.BaseEvent;
import com.tourcoo.xiantao.entity.event.RefreshEvent;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.order.OrderDetailEntity;
import com.tourcoo.xiantao.entity.order.ReturnInfo;
import com.tourcoo.xiantao.entity.pay.WeiXinPay;
import com.tourcoo.xiantao.entity.user.CashEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.tourcoo.xiantao.ui.comment.EvaluationActivity;
import com.tourcoo.xiantao.ui.comment.LookEvaluationActivity;
import com.tourcoo.xiantao.widget.dialog.PayDialog;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import static com.tourcoo.xiantao.constant.WxConfig.APP_ID;
import static com.tourcoo.xiantao.constant.WxConfig.WEI_XIN_PAY_TAG_NORMAL;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_PAY_FRESH_FAILED;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_PAY_FRESH_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.PAY_STATUS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.PAY_STATUS_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.SDK_PAY_FLAG;
import static com.tourcoo.xiantao.ui.order.ReturnGoodsActivity.EXTRA_GOODS_LIST;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_ALI;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_BALANCE;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_WE_XIN;

/**
 * @author :JenkinsZhou
 * @description :退单详情
 * @company :途酷科技
 * @date 2019年05月07日10:14
 * @Email: 971613168@qq.com
 */
public class ReturnGoodsDetailActivity extends BaseTourCooTitleMultiViewActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_EVALUATE = 1001;
    public static final int REQUEST_CODE_RETURN_GOODS = 1002;
    private List<String> mImageList = new ArrayList<>();
    private GridImageMatchContentAdapter gridImageAdapter;
    private IWXAPI api;
    private LinearLayout llAddressInfo;
    public static final String EXTRA_ORDER_ID = "EXTRA_ORDER_ID";
    public static final String EXTRA_PIN_TAG = "EXTRA_PIN_TAG";
    private RecyclerView goodsOrderRecyclerView;
    private OrderDetailEntity mOrderEntity;
    private PaymentHandler paymentHandler = new PaymentHandler(ReturnGoodsDetailActivity.this);
    private int mPayType;
    /**
     * 是否是拼团订单
     */
    private int pinTag;
    /**
     * 商品订单适配器
     */
    private OrderGoodsDetailAdapter mGoodsAdapter;

    private TextView tvNickName;
    private TextView tvMobile;
    private TextView tvAddress;
    private TextView tvOrderNumber;
    private TextView tvCreateTime;

    /**
     * 退货的商品图片列表
     */
    private RecyclerView rvReturnGoods;
    /**
     * 订单id
     */
    private int orderId;

    private LinearLayout llRootView;


    private TextView tvRemark;

    private TextView tvGoodsTypeCount;

    private TextView tvPayNow;
    private TextView tvCommentNow;
    private TextView tvLookExpress;
    private TextView tvReturn;
    private TextView tvCancelOrder;
    private TextView tvConfirmReceive;
    /**
     * 查看评价
     */
    private TextView tvLookComment;

    private boolean isPin;
    /**
     * 取消退单
     */
    private TextView tvCancelReturn;

    private LinearLayout llReturnGood;
    private TextView tvReturnDetail;
    private TextView tvReturnReason;

    private TextView tvReturnImage;
    /**
     * 退货状态
     */
    private TextView tvReturnStatus;


    private TextView tvReturnCoin;

    private TextView tvReturnPrice;

    /**
     * 订单处理
     */
    private TextView tvReturnResult;

    private LinearLayout llRealReturnMoney;
    private LinearLayout llRealReturnCoin;
    private TextView tvRealReturnMoney;
    private TextView tvRealReturnCoin;
    private TextView tvReply;
    private TextView tvReturnType;
    private TextView tvPin;

    @Override
    public int getContentLayout() {
        return R.layout.activity_return_detail;
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("退单详情");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(mContext, null);
        tvReply = findViewById(R.id.tvReply);
        tvPin = findViewById(R.id.tvPin);
        tvReturnType = findViewById(R.id.tvReturnType);
        tvRealReturnMoney = findViewById(R.id.tvRealReturnMoney);
        tvRealReturnCoin = findViewById(R.id.tvRealReturnCoin);
        llRealReturnMoney = findViewById(R.id.llRealReturnMoney);
        llRealReturnCoin = findViewById(R.id.llRealReturnCoin);
        tvReturnResult = findViewById(R.id.tvReturnResult);
        tvReturnStatus = findViewById(R.id.tvReturnStatus);
        tvReturnPrice = findViewById(R.id.tvReturnPrice);
        tvReturnDetail = findViewById(R.id.tvReturnDetail);
        tvReturnCoin = findViewById(R.id.tvReturnCoin);
        rvReturnGoods = findViewById(R.id.rvReturnGoods);
        tvReturnReason = findViewById(R.id.tvReturnReason);
        llReturnGood = findViewById(R.id.llReturnGood);
        tvCancelReturn = findViewById(R.id.tvCancelReturn);
        tvCommentNow = findViewById(R.id.tvCommentNow);
        tvConfirmReceive = findViewById(R.id.tvConfirmReceive);
        tvLookComment = findViewById(R.id.tvLookComment);
        tvCancelOrder = findViewById(R.id.tvCancelOrder);
        tvCancelReturn.setOnClickListener(this);
        tvPayNow = findViewById(R.id.tvPayNow);
        tvLookExpress = findViewById(R.id.tvLookExpress);
        tvReturn = findViewById(R.id.tvReturn);
        tvCommentNow = findViewById(R.id.tvCommentNow);
        tvCommentNow.setOnClickListener(this);
        tvCancelOrder.setOnClickListener(this);
        tvConfirmReceive.setOnClickListener(this);
        tvPayNow.setOnClickListener(this);
        tvReturn.setOnClickListener(this);
        tvLookExpress.setOnClickListener(this);
        tvLookComment.setOnClickListener(this);
        goodsOrderRecyclerView = findViewById(R.id.goodsOrderRecyclerView);
        goodsOrderRecyclerView = findViewById(R.id.goodsOrderRecyclerView);
        goodsOrderRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        tvGoodsTypeCount = findViewById(R.id.tvGoodsTypeCount);
        tvRemark = findViewById(R.id.tvRemark);
        llRootView = findViewById(R.id.llRootView);
        llAddressInfo = findViewById(R.id.llAddressInfo);
        tvNickName = findViewById(R.id.tvNickName);
        tvAddress = findViewById(R.id.tvAddress);
        tvMobile = findViewById(R.id.tvMobile);
        tvOrderNumber = findViewById(R.id.tvOrderNumber);
        tvCreateTime = findViewById(R.id.tvCreateTime);
        tvReturnImage = findViewById(R.id.tvReturnImage);
        orderId = getIntent().getIntExtra(EXTRA_ORDER_ID, -1);
        pinTag = getIntent().getIntExtra(EXTRA_PIN_TAG, -1);
        TourCooLogUtil.i(TAG, TAG + "订单详情id:" + orderId);
        TourCooLogUtil.i(TAG, TAG + "pinTag:" + pinTag);
        isPin = pinTag == 1;
        EventBus.getDefault().register(this);
        setViewVisible(tvPin, isPin);
    }

    @Override
    public void loadData() {
        super.loadData();
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
        mStatusLayoutManager.showLoadingLayout();
        ApiRepository.getInstance().requestOrderDetail(orderId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
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
                                        showReturnDetail(orderEntity);
                                    } else {
                                        ToastUtil.showFailed(entity.msg);
                                        mStatusLayoutManager.showErrorLayout();
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                mStatusLayoutManager.showErrorLayout();
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mStatusLayoutManager.showErrorLayout();
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
                        requestOrderDetail();
                    }
                };
            }

            @Override
            public View.OnClickListener getErrorClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestOrderDetail();
                    }
                };
            }

            @Override
            public View.OnClickListener getCustomerClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestOrderDetail();
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
                    tvReturnStatus.setTextColor(TourCooUtil.getColor(R.color.greenCommon));
                    tvReturnStatus.setText("退单中");
                    orderInfo.setOrder_status(ORDER_STATUS_BACK_ING);
                    //退单中 需要隐藏退款相关信息
                    setViewVisible(llRealReturnMoney, false);
                    setViewVisible(llRealReturnCoin, false);
                    break;
                case 30:
                    //已经退货
                    tvReturnStatus.setTextColor(TourCooUtil.getColor(R.color.redTextCommon));
                    tvReturnStatus.setText("已退单");
                    orderInfo.setOrder_status(ORDER_STATUS_BACK_FINISH);
                    //退单完成 需要显示退款相关信息
                    setViewVisible(llRealReturnMoney, true);
                    setViewVisible(llRealReturnCoin, true);
                    break;
                case 40:
                    //退货被拒绝
                    tvReturnStatus.setTextColor(TourCooUtil.getColor(R.color.redTextCommon));
                    tvReturnStatus.setText("已拒绝");
                    orderInfo.setOrder_status(ORDER_STATUS_BACK_REFUSE);
                    //退单拒绝 需要隐藏退款相关信息
                    setViewVisible(llRealReturnMoney, false);
                    setViewVisible(llRealReturnCoin, false);
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 显示退单详情
     *
     * @param orderDetailEntity
     */
    private void showReturnDetail(OrderDetailEntity orderDetailEntity) {
        mStatusLayoutManager.showSuccessLayout();
        if (orderDetailEntity.getOrder().getTuan() == 1) {
            loadAdapter(true);
        } else {
            loadAdapter(false);
        }

        OrderDetailEntity.OrderBean orderBean = orderDetailEntity.getOrder();
        showAddressInfo(orderBean.getAddress());
        //商品合计
        tvOrderNumber.setText(orderBean.getOrder_no());
        tvCreateTime.setText(DateUtil.parseDate(orderBean.getCreatetime()));
        //显示备注
        showRemark(orderBean);
        //注意 这里显示的商品数量仅为退单的商品数量 因此需要排除没有退的商品
        showReturnGoodsList(orderBean);
        loadBottomButtonFunction(orderBean);
        showReturnInfo(orderBean, orderDetailEntity.getOrder().getReturn_info());
    }

    private void setViewVisible(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
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


    private void loadAdapter(boolean isPin) {
        mGoodsAdapter = new OrderGoodsDetailAdapter(isPin);
        mGoodsAdapter.bindToRecyclerView(goodsOrderRecyclerView);
    }


    private void showRemark(OrderDetailEntity.OrderBean orderBean) {
        String remark = TourCooUtil.getNotNullValue(orderBean.getRemark());
        if (TextUtils.isEmpty(remark)) {
            tvRemark.setText("无备注信息");
        } else {
            tvRemark.setText(TourCooUtil.getNotNullValue(orderBean.getRemark()));

        }
    }


    /**
     * 根据不同状态加载底部按钮功能
     *
     * @param orderBean
     */
    private void loadBottomButtonFunction(OrderDetailEntity.OrderBean orderBean) {
        if (orderBean == null) {
            return;
        }
        TourCooLogUtil.i(TAG, TAG + "订单状态:" + orderBean.getOrder_status());
        switch (orderBean.getOrder_status()) {
            case ORDER_STATUS_WAIT_PAY:
                //待支付
                hideView(tvLookExpress);
                hideView(tvReturn);
                hideView(tvCommentNow);
                hideView(tvConfirmReceive);
                showView(tvPayNow);
                if (isPin) {
                    //拼团订单不允许取消退单
                    hideView(tvCancelOrder);
                } else {
                    showView(tvCancelOrder);
                }
                break;
            case ORDER_STATUS_WAIT_SEND:
                //待发货
                hideView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvConfirmReceive);
                hideView(tvLookExpress);
                hideView(tvReturn);
                hideView(tvCancelOrder);
                if (isPin) {
                    //拼团订单不允许退单
                    hideView(tvReturn);
                } else {
                    showView(tvReturn);
                }
                break;
            case ORDER_STATUS_WAIT_RECIEVE:
                //待收货
                hideView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvCancelOrder);
                //查看物流
                showView(tvLookExpress);
                //确认收货
                showView(tvConfirmReceive);
                if (isPin) {
                    //拼团订单不允许退单
                    hideView(tvReturn);
                } else {
                    //申请退货
                    showView(tvReturn);
                }
                break;
            case ORDER_STATUS_WAIT_COMMENT:
                //待评价
                hideView(tvPayNow);
                hideView(tvCancelOrder);
                hideView(tvReturn);
                hideView(tvConfirmReceive);
                showView(tvCommentNow);
                showView(tvLookExpress);
                break;
            case ORDER_STATUS_FINISH:
                hideView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvCancelOrder);
                hideView(tvReturn);
                hideView(tvConfirmReceive);
                showView(tvLookExpress);
                showView(tvLookComment);
                break;
            //退货中
            case ORDER_STATUS_BACK_ING:
                hideView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvCancelOrder);
                hideView(tvReturn);
                hideView(tvConfirmReceive);
                hideView(tvLookExpress);
                hideView(tvLookComment);
                showView(tvCancelReturn);
                break;
            case ORDER_STATUS_BACK_FINISH:
                hideView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvCancelOrder);
                hideView(tvReturn);
                hideView(tvConfirmReceive);
                hideView(tvLookExpress);
                hideView(tvLookComment);
                hideView(tvCancelReturn);
                break;
            case ORDER_STATUS_BACK_REFUSE:
                hideView(tvCommentNow);
                hideView(tvPayNow);
                hideView(tvCancelOrder);
                hideView(tvReturn);
                hideView(tvConfirmReceive);
                hideView(tvLookExpress);
                hideView(tvLookComment);
                hideView(tvCancelReturn);
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
            case R.id.tvPayNow:
                //立即支付
                requestBalanceAndShowPayDialog();
                break;
            //申请退单
            case R.id.tvReturn:
                skipReturnGoods(mOrderEntity.getOrder());
                break;
            //查看物流
            case R.id.tvLookExpress:
                skipSeeLogistics(mOrderEntity.getOrder().getId());
                break;
            //确认收货
            case R.id.tvConfirmReceive:
                showConfirmFinishDialog();
                break;
            case R.id.tvCommentNow:
                //立即评价
                skipEvaluation(mOrderEntity.getOrder());
                break;
            case R.id.tvLookComment:
                skipLookComment(mOrderEntity.getOrder().getId());
                break;
            case R.id.tvCancelReturn:
                showCancelReturnDialog();
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
                                ToastUtil.showSuccess("订单已取消");
                                setResult(RESULT_OK);
                                finish();
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
        paymentHandler.post(new Runnable() {
            @Override
            public void run() {
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
                                                showReturnDetail(orderEntity);
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
        });

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
        startActivityForResult(intent, REQUEST_CODE_RETURN_GOODS);
    }


    private List<Goods> parseGoodsList(OrderDetailEntity.OrderBean orderBean) {
        List<Goods> goodsList = new ArrayList<>();
        if (orderBean == null) {
            return goodsList;
        }
        Goods currentGoods;
        for (OrderDetailEntity.OrderBean.GoodsBean good : orderBean.getGoods()) {
            currentGoods = new Goods();
            currentGoods.setId(good.getId());
            currentGoods.setGoods_name(good.getGoods_name());
            currentGoods.setGoods_price(good.getGoods_price());
            currentGoods.setGoods_id(good.getGoods_id());
            currentGoods.setTotal_num(good.getTotal_num());
            currentGoods.setImage(good.getImage());
            goodsList.add(currentGoods);
        }
        return goodsList;
    }


    /**
     * 跳转至评价页面
     *
     * @param
     */
    private void skipEvaluation(OrderDetailEntity.OrderBean orderBean) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderBean.getId());
        intent.setClass(mContext, EvaluationActivity.class);
        startActivityForResult(intent, REQUEST_CODE_EVALUATE);
    }


    /**
     * 确认收货
     */
    private void requestConfirmFinish(int orderId) {
        TourCooLogUtil.i(TAG, TAG + "订单id:" + orderId);
        ApiRepository.getInstance().requestConfirmFinish(orderId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
//                                refreshRequest();
                                ToastUtil.showSuccess("收货完成");
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_EVALUATE:
                if (resultCode == RESULT_OK) {
                    TourCooLogUtil.i(TAG, TAG + ":" + "测试");
                    refreshRequest();
                    setResult(RESULT_OK);
                }
                break;
            case REQUEST_CODE_RETURN_GOODS:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK);
                    refreshRequest();
                }
                break;
            default:
                break;
        }
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
                        requestConfirmFinish(mOrderEntity.getOrder().getId());
                        dialog.dismiss();
//                        ApiRepository.getInstance().updateApp()
                    }
                });
        showConfirmDialog(builder);
    }


    /**
     * 查询账户信息并显示支付
     */
    private void requestBalanceAndShowPayDialog() {
        if (mOrderEntity == null || mOrderEntity.getOrder() == null) {
            ToastUtil.showFailed("未获取到订单信息");
            return;
        }
        ApiRepository.getInstance().requestBalance().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<CashEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<CashEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                TourCooLogUtil.i(TAG, entity.data);
                                double cash = entity.data.getCash();
                                showPayDialog(mOrderEntity.getOrder().getPay_price(), cash);
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
                createPay(payType);
                dialog.dismiss();
            }
        });
        payDialog.show();
    }


    /**
     * 支付接口
     */
    private void createPay(int payType) {
        if (mOrderEntity == null || mOrderEntity.getOrder() == null) {
            ToastUtil.showFailed("未获取到订单信息");
            return;
        }
        ApiRepository.getInstance().requestOrderPay(mOrderEntity.getOrder().getId(), payType).compose(bindUntilEvent(ActivityEvent.DESTROY)).
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
                                                weiChatPay(entity.data.toString(), WEI_XIN_PAY_TAG_NORMAL);
                                                break;
                                            case PAY_TYPE_ALI:
                                                aliPay(entity.data.toString());
                                                break;
                                            case PAY_TYPE_BALANCE:
                                                TourCooLogUtil.i("支付结果", entity);
                                                ToastUtil.showSuccess("支付完成");
                                                setResult(RESULT_OK);
                                                refreshRequest();
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


    /**
     * 微信支付
     *
     * @param payInfo
     */
    private void weiChatPay(String payInfo, int payType) {
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
            WxConfig.weiXinPayTag = payType;
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
        private WeakReference<ReturnGoodsDetailActivity> softReference;

        public PaymentHandler(ReturnGoodsDetailActivity activity) {
            softReference = new WeakReference<>(activity);
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
                                softReference.get().setResult(RESULT_OK);
                                softReference.get().refreshRequest();
//                                softReference.get().refreshStatus(TYPE_STATUS_ORDER_WAIT_EVALUATE);
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


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayEvent(BaseEvent event) {
        if (event == null) {
            TourCooLogUtil.e(TAG, "直接拦截");
            return;
        }
        switch (event.id) {
            case EVENT_ACTION_PAY_FRESH_SUCCESS:
                //支付成功 直接跳转到详情
                refreshRequest();
                setResult(RESULT_OK);
                break;
            case EVENT_ACTION_PAY_FRESH_FAILED:
//                skipToOrderListAndFinish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (api != null) {
            api.detach();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /**
     * 取消退单
     */
    private void requestCancelReturn(int orderId) {
        TourCooLogUtil.i(TAG, TAG + "订单id:" + orderId);
        ApiRepository.getInstance().requestCancelReturn(orderId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
//                                refreshRequest();
                                ToastUtil.showSuccess("已取消退单");
                                setResult(RESULT_OK);
                                EventBus.getDefault().post(new RefreshEvent());
                                finish();
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
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


    /**
     * 显示退货信息
     */
    private void showReturnInfo(OrderDetailEntity.OrderBean orderBean, ReturnInfo returnInfo) {
        if (returnInfo == null) {
            setViewVisible(llReturnGood, false);
            setViewVisible(tvReturnImage, false);
            TourCooLogUtil.e(TAG, TAG + ":" + "退货信息为空");
            return;
        }
        //退还的金币
        String returnCoin = TourCooUtil.doubleTransString(returnInfo.getCoin());
        tvReturnCoin.setText(returnCoin);
        if (returnInfo.getCoin() <= 0) {
            setViewVisible(llRealReturnCoin, false);
        } else {
            switch (orderBean.getOrder_status()) {
                case ORDER_STATUS_BACK_ING:
                    setViewVisible(llRealReturnCoin, false);
                    break;
                case ORDER_STATUS_BACK_FINISH:
                    //只有退货完成才显示退回金币
                    setViewVisible(llRealReturnCoin, true);
                    break;
                default:
                    //其他状态下 不显示退回金币
                    setViewVisible(llRealReturnCoin, false);
                    break;
            }

        }
        setViewVisible(llReturnGood, true);
        tvReturnType.setText(returnInfo.getType());
        //退货详情
        if (TextUtils.isEmpty(returnInfo.getDetail())) {
            tvReturnDetail.setText("未填写");
        } else {
            tvReturnDetail.setText(TourCooUtil.getNotNullValue(returnInfo.getDetail()));
        }

        String reason = returnInfo.getReason();
        TourCooLogUtil.i(TAG, TAG + ":" + "reason=" + reason);
        //退货原因
        tvReturnReason.setText(returnInfo.getReason());
        //订单处理
        tvReturnResult.setText(TourCooUtil.getNotNullValue(returnInfo.getStatus_text()));
        //实际返回金额
        TourCooLogUtil.i(TAG, TAG + "实际退款金额:" + returnInfo.getPrice());
        String realReturrnMoney = "¥ " + TourCooUtil.doubleTransString(returnInfo.getPrice());
        tvRealReturnMoney.setText(realReturrnMoney);
        //实际返回金币
        tvRealReturnCoin.setText(TourCooUtil.doubleTransStringZhen(returnInfo.getCoin()));
        if (returnInfo.getReply() == null || TextUtils.isEmpty(returnInfo.getReply().toString())) {
            tvReply.setText("无回复");
        } else {
            tvReply.setText(returnInfo.getReply().toString());
        }

        if (TextUtils.isEmpty(returnInfo.getImages())) {
            setViewVisible(tvReturnImage, false);
        } else {
            //显示退货图片
            gridImageAdapter = new GridImageMatchContentAdapter(mImageList);
            gridImageAdapter.bindToRecyclerView(rvReturnGoods);
            rvReturnGoods.setLayoutManager(new GridLayoutManager(mContext, 4));
            String[] images = returnInfo.getImages().split(",");
            for (String image : images) {
                mImageList.add(TourCooUtil.getUrl(image));
            }
            gridImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    onThumbnailClick(view, gridImageAdapter.getData().get(position));
                    List<ImageEntity> imageEntityList = parseImageEntityList(gridImageAdapter.getData());
                    computeBoundsBackward(rvReturnGoods, imageEntityList);
                    GPreviewBuilder.from(ReturnGoodsDetailActivity.this)
                            .setData(imageEntityList)
                            .setCurrentIndex(position)
                            .setSingleFling(true)
                            .setType(GPreviewBuilder.IndicatorType.Number)
                            .start();
                }
            });
        }
    }


    public void onThumbnailClick(View v, String imageUrl) {
// 全屏显示的方法
        /* android.R.style.Theme_Black_NoTitleBar_Fullscreen*/
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ImageView imgView = getView();
        dialog.setContentView(imgView);
        dialog.show();
        GlideManager.loadImg(imageUrl, imgView);
// 点击图片消失
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    private ImageView getView() {
        ImageView imgView = new ImageView(this);
        imgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imgView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        return imgView;
    }


    /**
     * 确认取消退货
     */
    private void showCancelReturnDialog() {
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(
                mContext);
        builder.setPositiveButtonPosition(ConfirmDialog.RIGHT);
        builder.setTitle("取消退单");
        builder.setMessageGravity(Gravity.CENTER_HORIZONTAL);
        builder.setFirstMessage("是否取消退单?").setNegativeButtonButtonBold(true);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestCancelReturn(mOrderEntity.getOrder().getId());
                dialog.dismiss();
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


    private void skipLookComment(int orderId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        intent.setClass(mContext, LookEvaluationActivity.class);
        startActivity(intent);
    }

    /**
     * 显示退单的商品列表
     */
    private void showReturnGoodsList(OrderDetailEntity.OrderBean orderBean) {
        if (orderBean == null || orderBean.getReturn_info() == null) {
            return;
        }
        ReturnInfo returnInfo = orderBean.getReturn_info();
        String[] returnGoodsArray = returnInfo.getGoods_id().split(",");
        //真正退单的商品id集合
        List<Integer> returnGoodsIds = new ArrayList<>();
        try {
            for (String id : returnGoodsArray) {
                if (TextUtils.isEmpty(id)) {
                    continue;
                }
                TourCooLogUtil.i(TAG, TAG + "转换的商品id:" + id);
                returnGoodsIds.add(Integer.parseInt(id));
            }
        } catch (NumberFormatException e) {
            TourCooLogUtil.e(TAG, TAG + "转换异常" + e.toString());
        }
        List<OrderDetailEntity.OrderBean.GoodsBean> allGoodsList = orderBean.getGoods();
        TourCooLogUtil.i(TAG, TAG + "allGoodsList长度:" + allGoodsList.size());
        List<OrderDetailEntity.OrderBean.GoodsBean> returnGoodsList = new ArrayList<>();
        for (OrderDetailEntity.OrderBean.GoodsBean goodsBean : allGoodsList) {
            for (Integer returnGoodsId : returnGoodsIds) {
                TourCooLogUtil.i(TAG, TAG + "商品id:" + goodsBean.getGoods_id());
                if (goodsBean.getId() == returnGoodsId) {
                    //如果商品id相同则说明该商品是真正退货的商品
                    returnGoodsList.add(goodsBean);
                }
            }
        }
        int num = 0;
        String realReturnMoney = "¥";
        double returnMoney = 0;
        for (OrderDetailEntity.OrderBean.GoodsBean goodsBean : returnGoodsList) {
            num += goodsBean.getTotal_num();
            returnMoney += goodsBean.getGoods_price() * goodsBean.getTotal_num();
        }
        returnMoney = Double.parseDouble(TourCooUtil.doubleTrans(returnMoney));
        realReturnMoney += TourCooUtil.doubleTransString(returnMoney);
//        String amount = "共" + num + "件商品";
        String amount = "共" + returnGoodsList.size() + "件商品";

        tvGoodsTypeCount.setText(amount);
        mGoodsAdapter.setNewData(returnGoodsList);
        //应退款金额
        tvReturnPrice.setText(realReturnMoney);
    }

    private List<ImageEntity> parseImageEntityList(List<String> imageUrlList) {
        List<ImageEntity> imageEntityList = new ArrayList<>();
        if (imageUrlList == null || imageUrlList.isEmpty()) {
            return imageEntityList;
        }
        ImageEntity imageEntity;
        for (String url : imageUrlList) {
            imageEntity = new ImageEntity();
            imageEntity.setImageUrl(url);
            imageEntityList.add(imageEntity);
        }
        return imageEntityList;
    }

    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(RecyclerView imageRecyclerView, List<ImageEntity> imageEntityList) {
        if (imageRecyclerView == null || !(imageRecyclerView.getLayoutManager() instanceof GridLayoutManager)) {
            return;
        }
        GridLayoutManager gridLayoutManager = (GridLayoutManager) imageRecyclerView.getLayoutManager();
        int firstCompletelyVisiblePos = gridLayoutManager.findFirstVisibleItemPosition();
        for (int i = firstCompletelyVisiblePos; i < imageEntityList.size(); i++) {
            View itemView = gridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = itemView.findViewById(R.id.additionalRoundedImageView);
                thumbView.getGlobalVisibleRect(bounds);
            }
            imageEntityList.get(i).setBounds(bounds);
        }
    }
}
