package com.tourcoo.xiantao.ui.recharge;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.aries.ui.view.tab.CommonTabLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.RechargeAmountAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.entity.TabEntity;
import com.tourcoo.xiantao.core.frame.impl.SwipeBackControlImpl;
import com.tourcoo.xiantao.core.frame.interfaces.IHomeView;
import com.tourcoo.xiantao.core.frame.interfaces.SwipeBackControl;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.threadpool.ThreadPoolManager;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.ResourceUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.recharge.RechargeEntity;
import com.tourcoo.xiantao.entity.event.BaseEvent;
import com.tourcoo.xiantao.entity.pay.WeiXinPay;
import com.tourcoo.xiantao.entity.user.CashEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.util.MoneyTextWatcher;
import com.tourcoo.xiantao.widget.dialog.PayDialog;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

import static com.tourcoo.xiantao.constant.WxConfig.APP_ID;
import static com.tourcoo.xiantao.constant.WxConfig.WEI_XIN_PAY_TAG_RECHARGE;
import static com.tourcoo.xiantao.constant.WxConfig.weiXinPayTag;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_PAY_FRESH_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.PAY_STATUS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.PAY_STATUS_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.SDK_PAY_FLAG;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_ALI;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_WE_XIN;

/**
 * @author :JenkinsZhou
 * @description :账户余额页面
 * @company :途酷科技
 * @date 2019年03月28日14:52
 * @Email: 971613168@qq.com
 */
public class AccountBalanceActivity extends BaseTourCooTitleActivity implements View.OnClickListener, SwipeBackControl {
    private RecyclerView rechargeRecyclerView;
    private IWXAPI api;
    private int mPayType;
    private RechargeAmountAdapter mRechargeAmountAdapter;
    private List<RechargeEntity> mRechargeEntityList = new ArrayList<>();
    private static final String TAG = "AccountBalanceActivity";
    private PaymentHandler mHandler = new PaymentHandler(AccountBalanceActivity.this);
    private AppCompatEditText etRechargeAmount;
    private String rechargeAmount;

    private TextView tvCurrentAccountBalance;

    @Override
    public int getContentLayout() {
        return R.layout.activity_account_balance;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvCurrentAccountBalance = findViewById(R.id.tvCurrentAccountBalance);
        rechargeRecyclerView = findViewById(R.id.rechargeRecyclerView);
        findViewById(R.id.tvRechargeConfirm).setOnClickListener(this);
        etRechargeAmount = findViewById(R.id.etRechargeAmount);
        rechargeRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        findViewById(R.id.tvRechargeRule).setOnClickListener(this);
        EventBus.getDefault().register(this);
        setInputListener();
    }


    @Override
    public void loadData() {
        super.loadData();
        api = WXAPIFactory.createWXAPI(mContext, null);
        initData();
        requestBalance();
    }


    private void setInputListener() {
        etRechargeAmount.addTextChangedListener(new MoneyTextWatcher(etRechargeAmount) {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                //如果长度不等于0
                if (s.length() != 0) {
                    for (RechargeEntity datum : mRechargeAmountAdapter.getData()) {
                        datum.selected = false;
                    }
                    mRechargeAmountAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    private void initData() {
        mRechargeEntityList.add(new RechargeEntity(100, true));
        mRechargeEntityList.add(new RechargeEntity(200));
        mRechargeEntityList.add(new RechargeEntity(300));
        mRechargeEntityList.add(new RechargeEntity(500));
        mRechargeEntityList.add(new RechargeEntity(800));
        mRechargeEntityList.add(new RechargeEntity(1000));
        mRechargeAmountAdapter = new RechargeAmountAdapter(mRechargeEntityList);
        mRechargeAmountAdapter.bindToRecyclerView(rechargeRecyclerView);
        mRechargeAmountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setSelect(position);
                adapter.notifyDataSetChanged();
                etRechargeAmount.setText("");
            }
        });
    }

    /**
     * 设置选中属性
     *
     * @param position
     */
    private void setSelect(int position) {
        if (position >= mRechargeEntityList.size()) {
            return;
        }
        RechargeEntity rechargeEntity;
        for (int i = 0; i < mRechargeEntityList.size(); i++) {
            rechargeEntity = mRechargeEntityList.get(i);
            if (i == position) {
                rechargeEntity.selected = true;
            } else {
                rechargeEntity.selected = false;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRechargeRule:
                TourCoolUtil.startActivity(mContext, RechargeRuleActivity.class);
                break;
            case R.id.tvRechargeConfirm:
                doRecharge();
                break;
            default:
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("账户余额");
        titleBar.setRightText("充值明细").
                setRightTextColor(ResourceUtil.getColor(R.color.colorPrimary)).
                setRightTextSize(15).setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TourCoolUtil.startActivity(mContext, RechargeListActivity.class);
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 30, 0);
        TextView textView = titleBar.getTextView(Gravity.END);
        textView.setLayoutParams(params);
    }

    /**
     * 支付接口
     */
    private void createPay(String amount, int payType) {
        ApiRepository.getInstance().recharge(amount, payType).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                ThreadPoolManager.getThreadPoolProxy().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        switch (payType) {
                                            case PAY_TYPE_WE_XIN:
                                                weiChatPay(entity.data.toString(), WEI_XIN_PAY_TAG_RECHARGE);
                                                break;
                                            case PAY_TYPE_ALI:
                                                aliPay(entity.data.toString());
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                            } else {
                                ToastUtil.showSuccess(entity.data.toString());
                            }
                        }
                    }
                });
    }

    @Override
    public boolean isSwipeBackEnable(Activity activity) {
        return false;
    }

    @Override
    public void setSwipeBack(Activity activity, BGASwipeBackHelper bgaSwipeBackHelper) {
        bgaSwipeBackHelper.setSwipeBackEnable(false);
    }

    @Override
    public void onSwipeBackLayoutSlide(Activity activity, float slideOffset) {

    }

    @Override
    public void onSwipeBackLayoutCancel(Activity activity) {

    }

    @Override
    public void onSwipeBackLayoutExecuted(Activity activity) {

    }


    @SuppressWarnings("unchecked")
    private static class PaymentHandler extends Handler {
        private WeakReference<AccountBalanceActivity> softReference;

        public PaymentHandler(AccountBalanceActivity activity) {
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
                                softReference.get().requestBalance();
                                ToastUtil.showSuccess("支付完成");
                                softReference.get().setResult(RESULT_OK);
                            } else {
                                ToastUtil.showFailed("支付失败");
                                TourCooLogUtil.e(TAG, result);
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


    private void aliPay(String payInfo) {
        PayTask aliPay = new PayTask(mContext);
        Map<String, String> result = aliPay.payV2(payInfo, true);
        Message msg = new Message();
        msg.what = SDK_PAY_FLAG;
        msg.obj = result;
        mHandler.sendMessage(msg);
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
            weiXinPayTag = payAction;
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

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        if (api != null) {
            api.detach();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private String getUserInput() {
        return etRechargeAmount.getText().toString();
    }

    private void doRecharge() {
        if (!TextUtils.isEmpty(getUserInput())) {
            //使用自定义充值
            rechargeAmount = getUserInput();
        } else {
            for (RechargeEntity datum : mRechargeAmountAdapter.getData()) {
                if (datum.selected) {
                    rechargeAmount = datum.rechargeMoney + "";
                    break;
                }
            }
            if (TextUtils.isEmpty(rechargeAmount)) {
                ToastUtil.show("请选择充值金额");
                return;
            }
        }
        showPayDialog();
    }


    private void showPayDialog() {
        double money = Double.parseDouble(rechargeAmount);
        PayDialog payDialog = new PayDialog(mContext, money, new PayDialog.PayListener() {
            @Override
            public void pay(int payType, Dialog dialog) {
                mPayType = payType;
                createPay(rechargeAmount, mPayType);
                dialog.dismiss();
            }
        }, false);
        payDialog.show();
    }


    /**
     * 查询账户信息
     */
    private void requestBalance() {
        ApiRepository.getInstance().requestBalance().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<CashEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<CashEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                TourCooLogUtil.i(TAG, entity.data);
                                tvCurrentAccountBalance.setText(entity.data.getCash() + "");
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onRechargeEvent(BaseEvent event) {
        if (event == null) {
            TourCooLogUtil.e(TAG, "直接拦截");
            return;
        }
        switch (event.id) {
            case EVENT_ACTION_PAY_FRESH_SUCCESS:
                //充值成功 刷新账户余额
                requestBalance();
                setResult(RESULT_OK);
                break;
            default:
                break;
        }
    }


}
