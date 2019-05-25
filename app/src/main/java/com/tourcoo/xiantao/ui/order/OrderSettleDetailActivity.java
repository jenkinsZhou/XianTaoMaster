package com.tourcoo.xiantao.ui.order;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.OrderGoodsSettleAdapter;
import com.tourcoo.xiantao.constant.WxConfig;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.FormatUtil;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.threadpool.ThreadPoolManager;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.navigation.KeyboardHelper;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.discount.DiscountInfo;
import com.tourcoo.xiantao.entity.event.BaseEvent;
import com.tourcoo.xiantao.entity.event.RefreshEvent;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsSkuBean;
import com.tourcoo.xiantao.entity.pay.WeiXinPay;
import com.tourcoo.xiantao.entity.settle.SettleEntity;
import com.tourcoo.xiantao.entity.user.CashEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.tourcoo.xiantao.ui.account.AddressManagerActivity;
import com.tourcoo.xiantao.ui.discount.DisCountSelectListActivity;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.builder.TimePickerBuilder;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.view.TimePickerView;
import com.tourcoo.xiantao.widget.dialog.PayDialog;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.lang.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_MODE_CHANGED;
import static com.tourcoo.xiantao.constant.WxConfig.APP_ID;
import static com.tourcoo.xiantao.constant.WxConfig.WEI_XIN_PAY_TAG_NORMAL;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_ADDRESS_KEY;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_PAY_FRESH_FAILED;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_PAY_FRESH_SUCCESS;
import static com.tourcoo.xiantao.ui.account.AddressManagerActivity.EXTRA_ADDRESS_INFO;
import static com.tourcoo.xiantao.ui.account.AddressManagerActivity.EXTRA_SKIP_TAG_SETTLE;
import static com.tourcoo.xiantao.ui.account.AddressManagerActivity.REQUEST_CODE_EDIT_ADDRESS;
import static com.tourcoo.xiantao.ui.discount.DisCountSelectListActivity.EXTRA_DISCOUNT_LIST;
import static com.tourcoo.xiantao.ui.discount.DisCountSelectListActivity.EXTRA_DISCOUNT_LIST_SELECT;
import static com.tourcoo.xiantao.ui.discount.DisCountSelectListActivity.EXTRA_PRICE;
import static com.tourcoo.xiantao.ui.goods.GoodsDetailActivity.EXTRA_SETTLE;
import static com.tourcoo.xiantao.ui.home.HomeFragment.EXTRA_GOODS_ID;
import static com.tourcoo.xiantao.ui.order.MyOrderListActivity.EXTRA_CURRENT_TAB_INDEX;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_ALI;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_BALANCE;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_WE_XIN;

/**
 * @author :JenkinsZhou
 * @description :订单详情(结算)
 * @company :途酷科技
 * @date 2019年04月25日11:33
 * @Email: 971613168@qq.com
 */
public class OrderSettleDetailActivity extends BaseTourCooTitleMultiViewActivity implements View.OnClickListener {
    private RelativeLayout rlSettleRemark;
    private TextView tvSettleRemark;
    private List<DiscountInfo> mDiscountInfoList = new ArrayList<>();
    /**
     * 是否是购物车结算
     */
    private boolean isShoppingCarSettle;
    public static final String EXTRA_GOODS_COUNT = "EXTRA_GOODS_COUNT";
    public static final String EXTRA_GOODS_SKU_ID = "EXTRA_GOODS_SKU_ID";
    public static final String EXTRA_SHOPPING_CAR_SETTLE = "EXTRA_SHOPPING_CAR_SETTLE";
    public int goodsId;
    public int goodsCount;
    public String skuId;

    private String addressInfo;
    /**
     * 判断是否拼团的结算
     */
    private boolean isPin;
    private static final double MIN_PAY_MONEY = 0.01;
    public static final int SKIP_TAG_SETTLE = 1002;
    public static final int REQUEST_CODE_SELECT_DISCOUNT = 1003;
    private RelativeLayout contentView;
    private TextView tvCanUseCount;
    private ImageView ivDiscount;
    private TextView tvSelectDiscount;
    private LinearLayout llDiscountMinus;
    private TextView tvDiscountMinus;
    /**
     * 优惠券页面
     */
    private RelativeLayout rlDiscount;
    /**
     * 日期选择器
     */
    private TimePickerView pvTime;
    /**
     * 默认的结算方式
     */
    private int mSettleType;
    /**
     * 配送时间
     */
    private TextView tvDeliveryTime;
    /**
     * 结算方式
     */
    public static final String EXTRA_SETTLE_TYPE = "EXTRA_SETTLE_TYPE";

    /**
     * 拼团id
     */
    public static final String EXTRA_PIN_USER_ID = "EXTRA_PIN_USER_ID";
    /**
     * 单独购买结算方式
     */
    public static final int SETTLE_TYPE_SINGLE = 1;
    /**
     * 购物车结算方式
     */
    public static final int SETTLE_TYPE_CAR = 2;
    /**
     * 拼团购买结算方式
     */
    public static final int SETTLE_TYPE_PIN = 3;
    private IWXAPI api;
    public static final int USE_COIN = 1;
    private static final String TAG = "OrderDetailActivity";
    public static final int SDK_PAY_FLAG = 1001;
    public static final int NOT_USE_COIN = 0;
    public static final String PAY_STATUS = "resultStatus";
    public static final String PAY_STATUS_SUCCESS = "9000";
    private PaymentHandler mHandler = new PaymentHandler(OrderSettleDetailActivity.this);
    /**
     * 获取权限使用的 RequestCode
     */
    private static final int PERMISSIONS_REQUEST_CODE = 1002;
    /**
     * 结算实体
     */
    private SettleEntity mSettleEntity;
    private TextView tvNickName;
    private TextView tvAddress;
    private LinearLayout llAddressInfo;
    private TextView tvCoinAmount;

    private LinearLayout llAddressLayout;
    private TextView fillAddress;
    private RecyclerView goodsOrderRecyclerView;

    private TextView tvTotalPrice;
    /**
     * 底部金额
     */
    private TextView tvPayPrice;

    private TextView tvSettleAccounts;
    /**
     * 商品种类数量
     */
    private TextView tvGoodsTypeCount;


    /**
     * 商品订单适配器
     */
    private OrderGoodsSettleAdapter mGoodsAdapter;

    private TextView tvExpressPrice;

    private TextView tvMobile;

    private TextView tvOrderPrice;

    /**
     * 使用金币开关
     */
    private Switch switchUseCoin;

    private LinearLayout llUseCoin;

    private EditText etRemark;

    private int mPayType;

    /**
     * 账户余额
     */
    private double cash;

    private double payMoney;

    private int pinId;

    /**
     * 是否创建了订单 默认未创建
     */
    private boolean isCreateOrder = false;


    /**
     * 优惠券抵扣价
     */
    private double minusMoney;

    /**
     * 记录在不使用优惠券的情况下的价格
     */
    private double recordPrice;

    /**
     * 选择的优惠券id组
     */
    private String discountIds;

    /**
     * 是否可以修改信息
     */
    private boolean canEdit = true;

    @Override
    protected IMultiStatusView getMultiStatusView() {
        return new IMultiStatusView() {
            @Override
            public View getMultiStatusContentView() {
                return contentView;
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
                        doRequestByCondition();
                    }
                };
            }

            @Override
            public View.OnClickListener getCustomerClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doRequestByCondition();
                    }
                };
            }
        };
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_order_settle_details;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initView(Bundle savedInstanceState) {
        rlSettleRemark = findViewById(R.id.rlSettleRemark);
        tvSettleRemark = findViewById(R.id.tvSettleRemark);
        mSettleType = getIntent().getIntExtra(EXTRA_SETTLE_TYPE, -1);
        goodsCount = getIntent().getIntExtra(EXTRA_GOODS_COUNT, -1);
        goodsId = getIntent().getIntExtra(EXTRA_GOODS_ID, -1);
        skuId = getIntent().getStringExtra(EXTRA_GOODS_SKU_ID);
        addressInfo = (String) SharedPreferencesUtil.get(PREF_ADDRESS_KEY, "");
        isShoppingCarSettle = getIntent().getBooleanExtra(EXTRA_SHOPPING_CAR_SETTLE, false);
        TourCooLogUtil.i(TAG, TAG + ":" + "skuId = " + skuId);
        TourCooLogUtil.i(TAG, TAG + ":" + "goodsCount = " + goodsCount);
        TourCooLogUtil.i(TAG, TAG + ":" + "goodsId = " + goodsId);
        if (TextUtils.isEmpty(addressInfo)) {
            setVisible(rlSettleRemark, false);
        } else {
            setVisible(rlSettleRemark, true);
            tvSettleRemark.setText(addressInfo);
        }
        //拼团id
        pinId = getIntent().getIntExtra(EXTRA_PIN_USER_ID, -1);
        if (pinId >= 0) {
            isPin = true;
        } else {
            isPin = false;
        }
        //配送日期
        tvDeliveryTime = findViewById(R.id.tvDeliveryTime);
        tvDeliveryTime.setOnClickListener(this);
        //金币抵扣布局
        tvCoinAmount = findViewById(R.id.tvCoinAmount);
        llDiscountMinus = findViewById(R.id.llDiscountMinus);
        tvDiscountMinus = findViewById(R.id.tvDiscountMinus);
        contentView = findViewById(R.id.contentView);
        ivDiscount = findViewById(R.id.ivDiscount);
        tvSelectDiscount = findViewById(R.id.tvSelectDiscount);
        tvCanUseCount = findViewById(R.id.tvCanUseCount);
        rlDiscount = findViewById(R.id.rlDiscount);
        rlDiscount.setOnClickListener(this);
        api = WXAPIFactory.createWXAPI(mContext, null);
        etRemark = findViewById(R.id.etRemark);
        fillAddress = findViewById(R.id.fillAddress);
        llAddressLayout = findViewById(R.id.llAddressLayout);
        llUseCoin = findViewById(R.id.llUseCoin);
        llAddressInfo = findViewById(R.id.llAddressInfo);
        llAddressInfo.setOnClickListener(this);
        switchUseCoin = findViewById(R.id.switchUseCoin);
        tvOrderPrice = findViewById(R.id.tvOrderPrice);
        tvExpressPrice = findViewById(R.id.tvExpressPrice);
        tvMobile = findViewById(R.id.tvMobile);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvGoodsTypeCount = findViewById(R.id.tvGoodsTypeCount);
        tvSettleAccounts = findViewById(R.id.tvSettleAccounts);
        tvNickName = findViewById(R.id.tvNickName);
        tvAddress = findViewById(R.id.tvAddress);
        tvPayPrice = findViewById(R.id.tvPayPrice);
        tvSettleAccounts.setOnClickListener(this);
        goodsOrderRecyclerView = findViewById(R.id.goodsOrderRecyclerView);
        goodsOrderRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mSettleEntity = (SettleEntity) getIntent().getSerializableExtra(EXTRA_SETTLE);
        EventBus.getDefault().register(this);
        loadCoinSwitchAndPrice();
        initTimePicker();
        listenCoinSwitch();
        requestPermission();
        KeyboardHelper.with(mContext)
                .setEnable(SOFT_INPUT_ADJUST_NOTHING);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("订单结算");
    }

    @Override
    public void loadData() {
        super.loadData();
        mStatusLayoutManager.showLoadingLayout();
        initAdapter();
        requestBalance();
        doRequestByCondition();
    }


    private void initAdapter() {
        TourCooLogUtil.i(TAG, TAG + "是否是拼团订单:" + isPin);
        mGoodsAdapter = new OrderGoodsSettleAdapter(isPin);
        mGoodsAdapter.bindToRecyclerView(goodsOrderRecyclerView);
    }


    private void showAddressInfo(AddressEntity addressEntity) {
        if (addressEntity == null || addressEntity.getArea() == null) {
            llAddressLayout.setVisibility(View.GONE);
            fillAddress.setVisibility(View.VISIBLE);
            return;
        }
        llAddressLayout.setVisibility(View.VISIBLE);
        fillAddress.setVisibility(View.GONE);
        tvNickName.setText(addressEntity.getName());
        tvMobile.setText(addressEntity.getPhone());
        tvAddress.setText(AccountInfoHelper.getInstance().getWholeAddressInfo(addressEntity));
    }

    /**
     * 显示结算信息
     *
     * @param settleEntity
     */
    private void showSettleInfo(SettleEntity settleEntity) {
        //清空优惠券信息
        if (settleEntity == null || settleEntity.getGoods_list() == null) {
            ToastUtil.showFailed("未获取到商品信息");
            mStatusLayoutManager.showErrorLayout();
            return;
        }
        List<Goods> goodsList = settleEntity.getGoods_list();
        mGoodsAdapter.setNewData(goodsList);
        //显示配送地址
        showAddressInfo(settleEntity.getExist_address());
        listenCoinSwitch();
        loadCoinSwitchAndPrice();
        //商品数量
        String amount = "共" + settleEntity.getOrder_total_num() + "件商品";
//        String amount = "共" + mGoodsAdapter.getData().size() + "件商品";
        tvGoodsTypeCount.setText(amount);
        //配送费
        tvExpressPrice.setText("¥" + TourCooUtil.doubleTransString(settleEntity.getExpress_price()));
        tvTotalPrice.setText("¥" + TourCooUtil.doubleTransString(settleEntity.getOrder_total_price()));
        double shouldPrice;
        boolean userCoin = settleEntity.getCoin() > 0;
        if (userCoin) {
            //有积分
            shouldPrice = settleEntity.getOrder_pay_price() + settleEntity.getCoin();
            String value = "¥" + TourCooUtil.doubleTrans(shouldPrice);
            tvOrderPrice.setText(value);
            recordPrice = shouldPrice;
        } else {
            //没有积分
            tvOrderPrice.setText("¥" + TourCooUtil.doubleTransString(settleEntity.getOrder_pay_price()));
            recordPrice = settleEntity.getOrder_pay_price();
        }
        payMoney = TourCooUtil.minusDouble(settleEntity.getOrder_pay_price(), minusMoney);
        if (payMoney <= MIN_PAY_MONEY) {
            payMoney = MIN_PAY_MONEY;
        }
        String payMonty = "¥" + formateMoney(payMoney);
        //底部应支付金额
        tvPayPrice.setText(payMonty);
        //显示金币
        showCoin(settleEntity);
        //todo 请求优惠券数量 显示优惠券
        requestAvailableDiscountNumber(mSettleEntity.getOrder_total_price());
        mStatusLayoutManager.showSuccessLayout();
        if (settleEntity.isHas_error() && settleEntity.getError_msg() != null) {
            if (!TextUtils.isEmpty(settleEntity.getError_msg().toString())) {
                showErrorDialog(settleEntity.getError_msg().toString());
            }
        }
        double orderPrice = computeOrderMoney(settleEntity);
        String orderPriceValue = "¥" + TourCooUtil.doubleTransString(orderPrice);
        //实际支付价格
        double payPrice = computePayMoney(settleEntity);
        String payPriceValue = "¥" + TourCooUtil.doubleTransString(payPrice);
        tvOrderPrice.setText(orderPriceValue);
        tvPayPrice.setText(payPriceValue);
        double discountMoney = computeDiscountPrice(mDiscountInfoList);
        if (discountMoney > 0) {
            llDiscountMinus.setVisibility(View.GONE);
        } else {
            llDiscountMinus.setVisibility(View.VISIBLE);
            String discountPrice = "¥" + TourCooUtil.doubleTransString(discountMoney);
            tvDiscountMinus.setText(discountPrice);
        }
        payMoney = payPrice;
    }

    /**
     * 显示已经存在的结算订单（id > 0）
     *
     * @param settleEntity
     */
    private void showSettleInfoExsist(SettleEntity settleEntity) {
        if (settleEntity == null || settleEntity.getGoods_list() == null) {
            ToastUtil.showFailed("未获取到商品信息");
            mStatusLayoutManager.showErrorLayout();
            return;
        }
        List<Goods> goodsList = settleEntity.getGoods_list();
        mGoodsAdapter.setNewData(goodsList);
        //显示配送地址
        showAddressInfo(settleEntity.getExist_address());
        loadCoinSwitchAndPrice();
        //商品数量
        //todo 暂时要求显示商品种类数量
//        String amount = "共" + settleEntity.getOrder_total_num() + "件商品";
        String amount = "共" + mGoodsAdapter.getData().size() + "件商品";
        tvGoodsTypeCount.setText(amount);
        //配送费
        tvExpressPrice.setText("¥" + TourCooUtil.doubleTransString(settleEntity.getExpress_price()));
        tvTotalPrice.setText("¥" + TourCooUtil.doubleTransString(settleEntity.getOrder_total_price()));
        if (TextUtils.isEmpty(settleEntity.getRemark())) {
            etRemark.setText("无备注");
        } else {
            etRemark.setText(settleEntity.getRemark());
        }
        etRemark.setCursorVisible(false);
        etRemark.setFocusable(false);
        etRemark.setFocusableInTouchMode(false);
        tvDeliveryTime.setText(settleEntity.getTime());
        double shouldPrice;
        boolean userCoin = settleEntity.getCoin() > 0;
        if (userCoin) {
            //有积分
            shouldPrice = settleEntity.getOrder_pay_price() + settleEntity.getCoin();
            String value = "¥" + TourCooUtil.doubleTrans(shouldPrice);
            tvOrderPrice.setText(value);
            recordPrice = shouldPrice;
        } else {
            //没有积分
            tvOrderPrice.setText("¥" + TourCooUtil.doubleTransString(settleEntity.getOrder_pay_price()));
            recordPrice = settleEntity.getOrder_pay_price();
        }
        payMoney = settleEntity.getOrder_pay_price() - minusMoney;
        if (payMoney <= MIN_PAY_MONEY) {
            payMoney = MIN_PAY_MONEY;
        }
        String payMonty = "¥" + TourCooUtil.doubleTransString(payMoney);
        //底部应支付金额
        tvPayPrice.setText(payMonty);
        //显示金币
        showCoin(settleEntity);
        //todo 请求优惠券数量 显示优惠券
        mStatusLayoutManager.showSuccessLayout();
        //无需请求 直接显示优惠券抵扣信息
        showSelectDiscoutAndPayPrice(settleEntity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSettleAccounts:
                if (mSettleEntity == null) {
                    ToastUtil.showFailed("未获取到结算信息");
                    return;
                }
                if (mSettleEntity.getExist_address() == null) {
                    ToastUtil.showFailed("请先设置配送地址");
                    return;
                }
             /*   if (mSettleEntity.isHas_error() && mSettleEntity.getError_msg() != null) {
                    ToastUtil.showFailed(mSettleEntity.getError_msg().toString());
                    return;
                }*/
                //弹出支付宝/微信
                if (TextUtils.isEmpty(getTextValue(tvDeliveryTime))) {
                    ToastUtil.show("请选择配送时间");
                    return;
                }
                showPayDialog(payMoney, cash);
                break;
            case R.id.llAddressInfo:
                //跳转地址选择
                if (!canEdit) {
                    ToastUtil.show("该订单已经生成,无法修改相关信息");
                    return;
                }
                doSkipAddressManager();
                break;
            case R.id.tvDeliveryTime:
                if (!canEdit) {
                    ToastUtil.show("该订单已经生成,无法修改相关信息");
                    return;
                }
                pvTime.show();
                break;
            case R.id.rlDiscount:
                if (!canEdit) {
                    ToastUtil.show("该订单已经生成,无法修改相关信息");
                    return;
                }
                skipDiscountList(mSettleEntity);
                break;
            default:
                break;
        }
    }

    /**
     * 是否显示抵扣金币
     *
     * @param settleEntity
     */
    private void showCoin(SettleEntity settleEntity) {
        double coin = settleEntity.getCoin();
        if (coin <= 0) {
            setVisible(llUseCoin, false);
        } else {
            setVisible(llUseCoin, true);
            String ducuteCoin = "可抵现¥" + TourCooUtil.doubleTransString(settleEntity.getCoin());
            tvCoinAmount.setText(ducuteCoin);
        }
        if (settleEntity.getCoin_status() == NOT_USE_COIN) {
            switchUseCoin.setChecked(false);
        } else {
            switchUseCoin.setChecked(true);
        }
    }


    private void setVisible(View view, boolean visible) {
        if (view == null) {
            TourCooLogUtil.e(TAG, TAG + ":" + "为null");
            return;
        }
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    private void loadCoinSwitchAndPrice() {
        if (mSettleEntity == null) {
            TourCooLogUtil.e(TAG, TAG + "订单结算实体为空");
            return;
        }
        /*switchUseCoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //使用抵扣
                    mSettleEntity.setCoin_status(USE_COIN);
                    double price = mSettleEntity.getOrder_total_price() + mSettleEntity.getExpress_price();
                    TourCooLogUtil.d(TAG, TAG + "运费金额:" + mSettleEntity.getExpress_price());
                    TourCooLogUtil.d(TAG, TAG + "运费金额:" + mSettleEntity.getOrder_total_price());
                    String shouldPrice = "¥" + TourCooUtil.doubleTrans(price);
                    tvShouldPayPrice.setText(shouldPrice);
                    payMoney = TourCooUtil.minusDouble(mSettleEntity.getOrder_pay_price(), minusMoney);
                    recordPrice = mSettleEntity.getOrder_pay_price();
                } else {
                    //不使用抵扣
                    mSettleEntity.setCoin_status(NOT_USE_COIN);
                    payMoney = mSettleEntity.getOrder_pay_price() + mSettleEntity.getCoin() - minusMoney;
                    recordPrice = mSettleEntity.getOrder_pay_price() + mSettleEntity.getCoin();
//                    tvShouldPayPrice.setText("¥" + mSettleEntity.getOrder_total_price() + mSettleEntity.getCoin());
//                    tvShouldPayPrice.setText("¥" + mSettleEntity.getOrder_total_price());
                    double price = mSettleEntity.getOrder_total_price() + mSettleEntity.getExpress_price();
                    TourCooLogUtil.i(TAG, TAG + "运费金额:" + mSettleEntity.getExpress_price());
                    TourCooLogUtil.i(TAG, TAG + "运费金额:" + mSettleEntity.getOrder_total_price());
                    String shouldPrice = "¥" + TourCooUtil.doubleTrans(price);
                    tvShouldPayPrice.setText(shouldPrice);
                }
            }
        });*/
    }

    private void listenCoinSwitch() {
        switchUseCoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //todo 监听
                if (isChecked) {
                    //使用抵扣
                    mSettleEntity.setCoin_status(USE_COIN);
//                    tvShouldPayPrice.setText("¥" + mSettleEntity.getOrder_pay_price());
                    payMoney = mSettleEntity.getOrder_pay_price();
                    recordPrice = mSettleEntity.getOrder_pay_price();
                    double money = TourCooUtil.minusDouble(mSettleEntity.getOrder_pay_price(), minusMoney);
                    payMoney = money;
                    if (money <= MIN_PAY_MONEY) {
                        payMoney = MIN_PAY_MONEY;
                    }
                    String value = "¥" + TourCooUtil.doubleTransString(payMoney);
                    tvPayPrice.setText(value);
                } else {
                    //不使用抵扣
                    mSettleEntity.setCoin_status(NOT_USE_COIN);
                    recordPrice = TourCooUtil.addDouble(mSettleEntity.getOrder_pay_price(), mSettleEntity.getCoin());
                    payMoney = recordPrice;
                    double result = TourCooUtil.addDouble(mSettleEntity.getOrder_pay_price(), mSettleEntity.getCoin());
                    double money = TourCooUtil.minusDouble(result, minusMoney);
                    payMoney = money;
                    if (money <= MIN_PAY_MONEY) {
                        payMoney = MIN_PAY_MONEY;
                    }
                    String value = "¥" + formateMoney(payMoney);
                    tvPayPrice.setText(value);
                }
            }
        });

    }


    private void showPayDialog(double money, double blalance) {
        PayDialog payDialog = new PayDialog(mContext, money, blalance, new PayDialog.PayListener() {
            @Override
            public void pay(int payType, Dialog dialog) {
                isCreateOrder = false;
                mPayType = payType;
                switch (mSettleType) {
                    case SETTLE_TYPE_SINGLE:
                        //单独购买发起的结算
                        createSinglePay(payType);
                        break;
                    case SETTLE_TYPE_CAR:
                        //购物车结算支付
                        createCarPay(payType);
                        break;
                    case SETTLE_TYPE_PIN:
                        //拼团结算
                        createPinPay(payType);
                        break;
                    default:
                        ToastUtil.show("未获取到结算类型");
                        break;
                }

                dialog.dismiss();
            }
        });
        payDialog.show();
    }


    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mContext,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {
                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
                    ToastUtil.show("关闭了权限弹窗");
                    return;
                }
                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        ToastUtil.show(getString(R.string.permission_rejected));
                        return;
                    }
                }
                // 所需的权限均正常获取
//                ToastUtil.show(getString(R.string.permission_granted));
            }
            default:
                break;
        }
    }

    /**
     * 单独支付接口
     */
    private void createSinglePay(int payType) {
        if (mSettleEntity == null || mSettleEntity.getGoods_list() == null || mSettleEntity.getGoods_list().isEmpty()) {
            ToastUtil.show("未获取到订单信息");
            return;
        }
        Map<String, Object> params = new HashMap<>(1);
        Goods goods = mSettleEntity.getGoods_list().get(0);
        GoodsSkuBean goodsSkuBean = goods.getGoods_sku();
        if (goodsSkuBean == null) {
            ToastUtil.showFailed("未获取到商品信息");
            return;
        }
        params.put("goods_id", goods.getGoods_id());
        params.put("goods_num", mSettleEntity.getOrder_total_num());
        params.put("remark", getRemark());
        params.put("time", getTextValue(tvDeliveryTime));
        params.put("goods_sku_id", goodsSkuBean.getSpec_sku_id());
        if (!TextUtils.isEmpty(discountIds)) {
            params.put("coupon_id", discountIds);
        }
        if (switchUseCoin.isChecked()) {
            params.put("coin_status", 1);
        } else {
            params.put("coin_status", 0);
        }
        ApiRepository.getInstance().buyNowPay(params, payType).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                ThreadPoolManager.getThreadPoolProxy().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        //订单生成成功 将状态置为true
                                        TourCooLogUtil.i(TAG, TAG + ":" + "订单生成成功");
                                        switch (mPayType) {
                                            case PAY_TYPE_WE_XIN:
                                                weiChatPay(entity.data.toString(), WEI_XIN_PAY_TAG_NORMAL);
                                                break;
                                            case PAY_TYPE_ALI:
                                                aliPay(entity.data.toString());
                                                break;
                                            case PAY_TYPE_BALANCE:
                                                payFailedAndSkipToOrderListAndFinish();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                            } else {
                                ToastUtil.show(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 购物车支付接口
     */
    private void createCarPay(int payType) {
        if (mSettleEntity == null || mSettleEntity.getGoods_list() == null || mSettleEntity.getGoods_list().isEmpty()) {
            ToastUtil.show("未获取到订单信息");
            return;
        }
        boolean useCoin = switchUseCoin.isChecked();
        ApiRepository.getInstance().requestCarPay(payType, useCoin, getRemark(), getTextValue(tvDeliveryTime), discountIds).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
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
                                                paySuccessAndskipOrderList();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                            } else {
                                ToastUtil.show(entity.msg);
                            }
                        }
                    }
                });
    }

    private void aliPay(String payInfo) {
        PayTask aliPay = new PayTask(mContext);
        Map<String, String> result = aliPay.payV2(payInfo, true);
        Message msg = new Message();
        msg.what = SDK_PAY_FLAG;
        msg.obj = result;
        mHandler.sendMessage(msg);
        //订单生成成功 将状态置为true
        isCreateOrder = true;
    }


    @SuppressWarnings("unchecked")
    private static class PaymentHandler extends Handler {
        private WeakReference<OrderSettleDetailActivity> softReference;

        public PaymentHandler(OrderSettleDetailActivity orderSettleDetailActivity) {
            softReference = new WeakReference<>(orderSettleDetailActivity);
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
                                ToastUtil.showSuccess("支付成功");
                                softReference.get().paySuccessAndSkipToOrderListAndFinish();
                            } else {
                                ToastUtil.showFailed("支付失败");
                                TourCooLogUtil.e(TAG, result);
                                softReference.get().payFailedAndSkipToOrderListAndFinish();
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
     * 微信支付
     *
     * @param payInfo
     */
    private void weiChatPay(String payInfo, int payAction) {
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
            WxConfig.weiXinPayTag = payAction;
            isCreateOrder = true;
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


    private String getRemark() {
        return etRemark.getText().toString();
    }


    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        EventBus.getDefault().unregister(this);
        if (api != null) {
            api.detach();
        }
        super.onDestroy();
    }


    /**
     * 查询账户余额
     */
    private void requestBalance() {
        ApiRepository.getInstance().requestBalance().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<CashEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<CashEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                TourCooLogUtil.i(TAG, entity.data);
                                cash = entity.data.getCash();
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private void doSkipAddressManager() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SKIP_TAG_SETTLE, SKIP_TAG_SETTLE);
        intent.setClass(mContext, AddressManagerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_EDIT_ADDRESS);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_EDIT_ADDRESS:
                if (resultCode == RESULT_OK && data != null) {
                    AddressEntity entity = (AddressEntity) data.getSerializableExtra(EXTRA_ADDRESS_INFO);
                    showAddressInfo(entity);
                    if (mSettleEntity != null) {
                        mSettleEntity.setExist_address(entity);
                    }
                    if (isPin) {
                        //拼团订单重新结算
                        TourCooLogUtil.i(TAG, TAG + "拼团订单的结算:");
                        doRequestByCondition();
                    } else {
                        //非拼团订单需要判断是否是购物车结算/立即购买结算
                        if (isShoppingCarSettle) {
                            //购物车结算
                            requestSettleShoppingCar();
                            //此时购物车的结算信息也要刷新
                            EventBus.getDefault().post(new RefreshEvent());
                            TourCooLogUtil.i(TAG, TAG + "购物车结算:");
                        } else {
                            TourCooLogUtil.i(TAG, TAG + "立即购买结算:");
                            settleNow(goodsId, goodsCount, skuId);
                        }
                    }
                }
                break;
            case REQUEST_CODE_SELECT_DISCOUNT:
                if (resultCode == RESULT_OK && data != null) {
                    List<DiscountInfo> discountInfoList = (List<DiscountInfo>) data.getSerializableExtra(EXTRA_DISCOUNT_LIST);
                    if (discountInfoList == null) {
                        return;
                    }
                    mDiscountInfoList.clear();
                    mDiscountInfoList.addAll(discountInfoList);
                    showSelectDiscoutAndPayPrice(mDiscountInfoList, mSettleEntity);
                    List<String> ids = new ArrayList<>();
                    for (DiscountInfo discountInfo : mDiscountInfoList) {
                        ids.add(discountInfo.getId() + "");
                    }
                    discountIds = StringUtils.join(ids, ",");
                    double payTotal = computePayMoney(mSettleEntity);
                    String payValue = "¥" + TourCooUtil.doubleTransString(payTotal);
                    tvPayPrice.setText(payValue);
                    payMoney = payTotal;
                    TourCooLogUtil.i(TAG, TAG + "需要支付的金额:" + payMoney + "discountIds=" + discountIds);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 跳转到订单列表
     */
    private void paySuccessAndskipOrderList() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setClass(mContext, MyOrderListActivity.class);
                startActivity(intent);
                TourCooLogUtil.i(TAG, TAG + ":" + "已经跳转");
                finish();
            }
        });
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
                if (WxConfig.weiXinPayTag == WEI_XIN_PAY_TAG_NORMAL) {
                    paySuccessAndskipOrderList();
                }
                break;
            case EVENT_ACTION_PAY_FRESH_FAILED:
                if (WxConfig.weiXinPayTag == WEI_XIN_PAY_TAG_NORMAL) {
                    TourCooLogUtil.e(TAG, TAG + ":" + "微信支付失败");
                    payFailedAndSkipToOrderListAndFinish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 拼团结算
     *
     * @param pinId
     */
    private void requestPinSettle(int pinId) {
        ApiRepository.getInstance().requestPinSettle(pinId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                TourCooLogUtil.i(TAG, entity.data);
                                mSettleEntity = parseSettleInfo(entity.data);
                                if (mSettleEntity != null) {
                                    if (mSettleEntity.getId() > 0) {
                                        //表示此订单已经生成,此时所以信息都不可编辑
                                        canEdit = false;
                                        showSettleInfoExsist(mSettleEntity);
                                    } else {
                                        canEdit = true;
                                        showSettleInfo(mSettleEntity);
                                    }
                                } else {
                                    mStatusLayoutManager.showErrorLayout();
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


    /**
     * 结算实体
     *
     * @param data
     * @return
     */
    private SettleEntity parseSettleInfo(Object data) {
        if (data == null) {
            return null;
        }
        try {
            String homeInfo = JSONObject.toJSONString(data);
            Gson gson = new Gson();
            TourCooLogUtil.i(TAG, "准备解析:" + homeInfo);
            return gson.fromJson(homeInfo, SettleEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    /**
     * 拼团支付接口
     */
    private void createPinPay(int payType) {
        if (mSettleEntity == null || mSettleEntity.getGoods_list() == null || mSettleEntity.getGoods_list().isEmpty()) {
            ToastUtil.show("未获取到订单信息");
            return;
        }
        ApiRepository.getInstance().requestPinPay(pinId, payType, getRemark(), getTextValue(tvDeliveryTime), discountIds).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
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
                                                paySuccessAndskipOrderList();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                            } else {
                                ToastUtil.show(entity.msg);
                            }
                        }
                    }
                });
    }

    /**
     * 支付失败 跳转至订单列表 并finish页面
     */
    private void payFailedAndSkipToOrderListAndFinish() {
        Intent intent = new Intent();
        //跳转至我的订单 全部列表
        intent.putExtra(EXTRA_CURRENT_TAB_INDEX, 0);
        intent.setClass(mContext, MyOrderListActivity.class);
        startActivity(intent);
        TourCooLogUtil.i(TAG, TAG + ":" + "已经跳转");
        finish();
    }


    private void paySuccessAndSkipToOrderListAndFinish() {
        try {
            Intent intent = new Intent();
            //跳转至我的订单 全部列表
            intent.putExtra(EXTRA_CURRENT_TAB_INDEX, 0);
            intent.setClass(mContext, MyOrderListActivity.class);
            startActivity(intent);
            TourCooLogUtil.i(TAG, TAG + ":" + "已经跳转");
            finish();
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, TAG + ":" + e.toString());
        }

    }

    private void doRequestByCondition() {
        mStatusLayoutManager.showLoadingLayout();
        //显示结算页面
        if (isShoppingCarSettle) {
            //请求购物车结算
            requestSettleShoppingCar();
            return;
        }
        if (mSettleType == SETTLE_TYPE_PIN) {
            requestPinSettle(pinId);
        } else {
            showSettleInfo(mSettleEntity);
        }
    }


    private String getTextValue(TextView textView) {
        return textView.getText().toString();
    }

    private void setTextValue(TextView textView, String value) {
        textView.setText(value);
    }


    /**
     * Dialog 模式下，在底部弹出
     */
    private void initTimePicker() {
        pvTime = new TimePickerBuilder(this, (date, v) -> setTextValue(tvDeliveryTime, getTime(date)))
                .setType(new boolean[]{false, false, false, true, true, false})
                //默认设置false ，内部实现将DecorView 作为它的父控件。
                .setTitleText("请选择配送时间")
                .isDialog(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                //修改动画样式
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);
                //改成Bottom,底部显示
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }


    @Override
    protected void onResume() {
        super.onResume();
        TourCooLogUtil.i(TAG, TAG + "当前订单状态:" + isCreateOrder);
        if (isCreateOrder) {
            payFailedAndSkipToOrderListAndFinish();
        }
    }


    /**
     * 跳转至优惠券选择
     */
    private void skipDiscountList(SettleEntity settleEntity) {
        Intent intent = new Intent();
        //跳转至我的可用优惠券 全部列表
        intent.putExtra(EXTRA_PRICE, settleEntity.getOrder_total_price());
        intent.putExtra(EXTRA_DISCOUNT_LIST_SELECT, (Serializable) mDiscountInfoList);
        intent.setClass(mContext, DisCountSelectListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECT_DISCOUNT);
        TourCooLogUtil.i(TAG, TAG + ":" + "已经跳转");
    }


    /**
     * 获取可用优惠券数量相关信息
     */
    private void requestAvailableDiscountNumber(double price) {
        mStatusLayoutManager.showLoadingLayout();
        ApiRepository.getInstance().requestAvailableDiscountNumber(price).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                showDiscountByCondition(parseNum(entity.data));
                                mStatusLayoutManager.showSuccessLayout();
                            } else {
                                mStatusLayoutManager.showErrorLayout();
                            }
                        } else {
                            mStatusLayoutManager.showErrorLayout();
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        TourCooLogUtil.e(TAG, "请求异常:" + e.toString());
                        mStatusLayoutManager.showErrorLayout();
                    }
                });
    }


    private void showDiscountByCondition(int num) {
        if (num < 1) {
            setVisible(rlDiscount, false);
            setVisible(llDiscountMinus, false);
        } else {
            setVisible(rlDiscount, true);
            String value = num + "张可用";
            tvCanUseCount.setText(value);
            if (mDiscountInfoList.isEmpty()) {
                setVisible(llDiscountMinus, false);
            } else {
                setVisible(llDiscountMinus, true);
            }
        }
    }

    private int parseNum(Object data) {
        try {
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(data));
            return jsonObject.getIntValue("num");
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "value:" + e.toString());
            return -1;
        }
    }


    private String getSelectDiscout(List<DiscountInfo> discountInfoList) {
        String discoutInfo = "";
        for (DiscountInfo discountInfo : discountInfoList) {
            discoutInfo = "¥" + discountInfo.getWorth() + " x" + discountInfoList.size();
        }
        return discoutInfo;
    }


    private void showSelectDiscoutAndPayPrice(List<DiscountInfo> discountInfoList, SettleEntity settleEntity) {

        if (discountInfoList.size() > 0) {
            ivDiscount.setVisibility(View.GONE);
            tvCanUseCount.setVisibility(View.GONE);
            tvSelectDiscount.setVisibility(View.VISIBLE);
            tvSelectDiscount.setTextColor(TourCooUtil.getColor(R.color.whiteCommon));
            tvSelectDiscount.setText(getSelectDiscout(discountInfoList));
            double minus = comuterDiscountPrice(discountInfoList);
            minusMoney = minus;
            llDiscountMinus.setVisibility(View.VISIBLE);
            tvDiscountMinus.setText("-¥" + minus);
            payMoney = TourCooUtil.minusDouble(recordPrice, minus);
            if (settleEntity.getCoin_status() == 1) {
                //表示当前使用积分抵扣
                payMoney = TourCooUtil.minusDouble(payMoney, settleEntity.getCoin());
            }
            //todo
            if (payMoney <= MIN_PAY_MONEY) {
                payMoney = MIN_PAY_MONEY;
            }
            String value = "¥" + formateMoney(payMoney);
            TourCooLogUtil.i(TAG, TAG + "显示的金额:" + (recordPrice - minus));
            tvPayPrice.setText(value);
        } else {
            minusMoney = 0;
            ivDiscount.setVisibility(View.VISIBLE);
            tvCanUseCount.setVisibility(View.VISIBLE);
            llDiscountMinus.setVisibility(View.GONE);
            tvSelectDiscount.setTextColor(TourCooUtil.getColor(R.color.black));
            tvSelectDiscount.setVisibility(View.GONE);
            payMoney = recordPrice;
            TourCooLogUtil.i(TAG, TAG + "显示的金额:" + payMoney);
            String value = "¥" + formateMoney(payMoney);
            tvPayPrice.setText(value);
        }
    }


    private void showSelectDiscoutAndPayPrice(SettleEntity settleEntity) {
        if (settleEntity.getCoupon_worth() > 0) {
            rlDiscount.setVisibility(View.VISIBLE);
            ivDiscount.setVisibility(View.GONE);
            tvCanUseCount.setVisibility(View.GONE);
            tvSelectDiscount.setVisibility(View.VISIBLE);
            tvSelectDiscount.setTextColor(TourCooUtil.getColor(R.color.whiteCommon));
            tvSelectDiscount.setText("¥" + settleEntity.getCoupon_worth());
            double minus = settleEntity.getCoupon_worth();
            minusMoney = minus;
            llDiscountMinus.setVisibility(View.VISIBLE);
            tvDiscountMinus.setText("-¥" + minus);
            payMoney = TourCooUtil.minusDouble(recordPrice, minus);
            if (payMoney <= MIN_PAY_MONEY) {
                payMoney = MIN_PAY_MONEY;
            }
            String value = "¥" + formateMoney(payMoney);
            TourCooLogUtil.i(TAG, TAG + "显示的金额:" + (recordPrice - minus));
            tvPayPrice.setText(value);
        } else {
            minusMoney = 0;
            ivDiscount.setVisibility(View.VISIBLE);
            tvCanUseCount.setVisibility(View.VISIBLE);
            rlDiscount.setVisibility(View.GONE);
            tvSelectDiscount.setTextColor(TourCooUtil.getColor(R.color.black));
            tvSelectDiscount.setVisibility(View.GONE);
            payMoney = recordPrice;
            TourCooLogUtil.i(TAG, TAG + "显示的金额:" + recordPrice);
            String value = "¥" + formateMoney(recordPrice);
            tvPayPrice.setText(value);
        }
    }

    private double comuterDiscountPrice(List<DiscountInfo> discountInfoList) {
        double worthMoney = 0;
        for (DiscountInfo discountInfo : discountInfoList) {
            worthMoney += discountInfo.getWorth();
            TourCooLogUtil.i(TAG, TAG + "抵扣金额:" + worthMoney);
        }
        return worthMoney;
    }


    private String formateMoney(double money) {
        return TourCooUtil.doubleTransString(money);
    }


    /**
     * 请求添加商品接口(结算)
     */
    private void settleNow(int goodsId, int count, String skuId) {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager.showLoadingLayout();
        }
        ApiRepository.getInstance().settleGoods(goodsId, count, skuId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    //跳转到结算
                                    SettleEntity settleEntity = parseSettleInfo(entity.data);
                                    if (settleEntity != null) {
                                        showSettleInfo(settleEntity);
                                    } else {
                                        ToastUtil.showFailed("失败");
                                        mStatusLayoutManager.showErrorLayout();
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                mStatusLayoutManager.showErrorLayout();
                            }
                        }
                    }
                });
    }


    /**
     * 购物车结算
     */
    private void requestSettleShoppingCar() {
        mStatusLayoutManager.showLoadingLayout();
        ApiRepository.getInstance().requestSettleShoppingCar().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    mSettleEntity = parseSettleInfo(entity.data);
                                    if (mSettleEntity != null) {
                                        showSettleInfo(mSettleEntity);
                                    } else {
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


    /**
     * 显示错误对话框
     *
     * @param msg
     */
    protected void showErrorDialog(String msg) {
        showAlertDialog("温馨提示", msg, "我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //todo
                dialog.dismiss();
                if (mSettleEntity != null) {
                    //将配送地址置为null
                    mSettleEntity.setExist_address(null);
                }
                showAddressInfo(null);
            }
        });
    }


    /**
     * 计算订单总金额
     *
     * @param settleEntity
     * @return
     */
    private double computeOrderMoney(SettleEntity settleEntity) {
        if (settleEntity == null) {
            return 0;
        }
        double shouldPrice;
        boolean hasCoin = settleEntity.getCoin() > 0;
        if (hasCoin) {
            //用户有金币抵扣 则说明实际订单价格 = 支付价格+金币抵扣价格
            shouldPrice = TourCooUtil.addDouble(settleEntity.getOrder_pay_price(), settleEntity.getCoin());
        } else {
            //用户没有金币 则代表 实际订单价格 = 支付价格
            shouldPrice = settleEntity.getOrder_pay_price();
        }
        return shouldPrice;
    }


    /**
     * 计算优惠券抵扣的金额
     *
     * @param discountInfoList
     * @return
     */
    private double computeDiscountPrice(List<DiscountInfo> discountInfoList) {
        if (discountInfoList == null || discountInfoList.isEmpty()) {
            return 0;
        }
        double worthMoney = 0;
        for (DiscountInfo discountInfo : discountInfoList) {
            worthMoney += discountInfo.getWorth();
            TourCooLogUtil.i(TAG, TAG + "抵扣金额:" + worthMoney);
        }
        return worthMoney;
    }


    /**
     * 计算需要支付的总金额
     *
     * @param settleEntity
     * @return
     */
    private double computePayMoney(SettleEntity settleEntity) {
        if (settleEntity == null) {
            return 0;
        }
        //先计算订单总金额
        double orderPrice = computeOrderMoney(settleEntity);
        double shouldPrice;
        boolean useCoin = isUseCoin(settleEntity);
        if (useCoin) {
            //表示用户使用了金币抵扣 则 应付价格 = 总金额 - 抵扣金额
            shouldPrice = TourCooUtil.minusDouble(orderPrice, settleEntity.getCoin());
        } else {
            //用户没有金币 则代表 应付价格 = 总金额
            shouldPrice = orderPrice;
        }
        //接下来再计算金币优惠券抵扣情况 先计算优惠券抵扣的金额
        double discountMoney = computeDiscountPrice(mDiscountInfoList);
        //支付金额 = shouldPrice - 优惠券抵扣金额
        shouldPrice = TourCooUtil.minusDouble(shouldPrice, discountMoney);
        //最后 如果支付金额为负数 则默认支付0.01元
        if (shouldPrice <= 0) {
            shouldPrice = 0.01;
        }
        return shouldPrice;
    }


    /**
     * 判断是否使用金币抵扣
     *
     * @param settleEntity
     * @return
     */
    private boolean isUseCoin(SettleEntity settleEntity) {
        if (settleEntity == null) {
            return false;
        }
        return settleEntity.getCoin() > 0 && settleEntity.getCoin_status() == 1;
    }

}
