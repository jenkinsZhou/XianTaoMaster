package com.tourcoo.xiantao.ui.tuan;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ImageUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.MyTuanListAdapter;
import com.tourcoo.xiantao.constant.WxConfig;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseFragment;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.StackUtil;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.threadpool.ThreadPoolManager;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.custom.SharePopupWindow;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.event.BaseEvent;
import com.tourcoo.xiantao.entity.pay.WeiXinPay;
import com.tourcoo.xiantao.entity.tuan.TuanEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity;
import com.tourcoo.xiantao.widget.dialog.PayDialog;
import com.trello.rxlifecycle3.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.Map;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.constant.TuanConstant.TUAN_STATUS_MINE;
import static com.tourcoo.xiantao.constant.WxConfig.APP_ID;
import static com.tourcoo.xiantao.constant.WxConfig.WEIXIN_PIN_URL;
import static com.tourcoo.xiantao.constant.WxConfig.WEI_XIN_PAY_TAG_NORMAL;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_PAY_FRESH_FAILED;
import static com.tourcoo.xiantao.entity.event.EventConstant.EVENT_ACTION_PAY_FRESH_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.EXTRA_PIN_USER_ID;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.EXTRA_SETTLE_TYPE;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.PAY_STATUS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.PAY_STATUS_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.SDK_PAY_FLAG;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.SETTLE_TYPE_PIN;
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
public class MyTuanListFragment extends BaseFragment implements OnRefreshLoadMoreListener {

    private static final String TAG = "MyTuanListFragment";
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private int mDefaultPage = 1;
    private int mDefaultPageSize = 10;
    private boolean isLoadMore = false;
    private int totalPage = -1;
    int mPayType;
    private PaymentHandler mHandler = new PaymentHandler(MyTuanListFragment.this);
    private SharePopupWindow sharePopupWindow;
    private View footView;
    private IWXAPI api;

    private MyTuanListAdapter mAdapter;
    private int tuanStatus = TUAN_STATUS_MINE;
    public static final String EXTRA_TUAN_STATUS = "EXTRA_TUAN_STATUS";
    private RelativeLayout rrRootView;

    private boolean isInit = false;
    private StatusLayoutManager mStatusLayoutManager;

    public static MyTuanListFragment newInstance(int tuanStatus) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_TUAN_STATUS, tuanStatus);
        MyTuanListFragment fragment = new MyTuanListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void updateDate() {
        if (isInit) {
            mDefaultPage = 1;
            mRefreshLayout.setNoMoreData(false);
            mRefreshLayout.setEnableLoadMore(true);
            isLoadMore = false;
            loadData(mDefaultPage);
        }
    }


    @Override
    public int getContentLayout() {
        return R.layout.layout__smart_refresh;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (getArguments() == null) {
            ToastUtil.show("未获取到数据");
            return;
        }
        isInit = true;
        api = WXAPIFactory.createWXAPI(getContext(), WxConfig.APP_ID);
        sharePopupWindow = new SharePopupWindow(getContext(), true);
        tuanStatus = getArguments().getInt(EXTRA_TUAN_STATUS, 1);
        footView = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        rrRootView = mContentView.findViewById(R.id.rrRootView);
        mAdapter = new MyTuanListAdapter(getContext(), null);
        mRefreshLayout = mContentView.findViewById(R.id.smartLayout_rootFastLib);
        mRecyclerView = mContentView.findViewById(R.id.rv_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        EventBus.getDefault().register(this);
        mAdapter.setIOnItemClickListener(new MyTuanListAdapter.IOnItemClickListener() {
            @Override
            public void onItemClick(int tuan_id) {
                Intent intent = new Intent(getContext(), TuanDetailsActivity.class);
                intent.putExtra("tuan_id", tuan_id);
                startActivity(intent);
            }

            @Override
            public void onBtnClick(int tuan_id, Bitmap bitmap) {

                sharePopupWindow.setISharePopupWindowClickListener(new SharePopupWindow.ISharePopupWindowClickListener() {
                    @Override
                    public void onWxClick() {
                        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
                        //自定义
                        miniProgram.webpageUrl = WEIXIN_PIN_URL;
                        //小程序端提供参数
                        miniProgram.userName = WxConfig.MINI_PROGRAM_USERNAME;
                        //小程序端提供参数
                        miniProgram.path = WxConfig.MINI_PROGRAM_PATH + tuan_id;
//                        miniProgram.miniprogramType = WXMiniProgramObject.MINIPROGRAM_TYPE_TEST;// 正式版:0，测试版:1，体验版:2
                        miniProgram.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;
                        WXMediaMessage mediaMessage = new WXMediaMessage(miniProgram);
                        //自定义
                        mediaMessage.title = "拼团钜惠";
                        //自定义
                        mediaMessage.description = "拼团钜惠";
                        Bitmap bitmapStatic = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_wei_xin_app);
                        int width = bitmapStatic.getWidth();
                        int height = bitmapStatic.getHeight();
                        Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmapStatic, width, height, false);
                        bitmap.recycle();
                        mediaMessage.thumbData = ImageUtils.bitmap2Bytes(sendBitmap, Bitmap.CompressFormat.PNG);
                        bitmapStatic.recycle();
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = "";
                        req.scene = SendMessageToWX.Req.WXSceneSession;
                        req.message = mediaMessage;
                        api.sendReq(req);
                        sendBitmap.recycle();
                        sharePopupWindow.dismiss();
                    }

                    @Override
                    public void onWxFriendClick() {
                        sharePopupWindow.dismiss();
                    }
                });
                sharePopupWindow.showAtScreenBottom(mRefreshLayout);

            }

            @Override
            public void onPayClick(int tuanuser_id, int orderId, double payPrice) {
                TourCooLogUtil.d("订单id：" + orderId);
                if (orderId > 0) {
                    //表示订单已经生成 直接结算支付 无需跳转
                    showPayDialog(payPrice, tuanuser_id);
                } else {
                    skipOrderSettleByPin(tuanuser_id);
                }
            }
        });
        loadData(mDefaultPage);
        setupStatusLayoutManager();
    }

    @Override
    public void loadData() {
        super.loadData();
//        mStatusLayoutManager.showLoadingLayout();
    }

    /**
     * 发起拼团的结算跳转
     *
     * @param pinId
     */
    private void skipOrderSettleByPin(int pinId) {
        Intent intent = new Intent();
        //单独购买结算类型
        intent.putExtra(EXTRA_SETTLE_TYPE, SETTLE_TYPE_PIN);
        intent.putExtra(EXTRA_PIN_USER_ID, pinId);
        intent.setClass(mContext, OrderSettleDetailActivity.class);
        TourCooLogUtil.i(TAG, "value:" + pinId);
        startActivity(intent);
    }


    private void loadData(int page) {
        requestTuanListInfo(page);
    }

    /**
     * 刷新列表
     */
    private void refreshData() {
        mAdapter.getData().clear();
        mDefaultPage = 1;
        requestTuanListInfo(mDefaultPage);
    }

    /**
     * 获取拼团列表记录
     */
    private void requestTuanListInfo(int page) {
        ApiRepository.getInstance().requestTuanListInfo(tuanStatus, page).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    TuanEntity tuanEntity = parseTuanEntity(entity.data);
                                    if (tuanEntity != null) {
                                        mStatusLayoutManager.showSuccessLayout();
                                        totalPage = tuanEntity.getLast_page();
                                        if (!isLoadMore) {
                                            mAdapter.setNewData(tuanEntity.getData());
                                            mRefreshLayout.finishRefresh(true);
                                        } else {
                                            mAdapter.addMoreItem(tuanEntity.getData());
                                            mRefreshLayout.finishLoadMore();
                                        }
                                        if (mAdapter.getItemCount() == 0) {
                                            mStatusLayoutManager.showEmptyLayout();
                                        } else {
                                            mStatusLayoutManager.showSuccessLayout();
                                        }
                                    } else {
                                        ToastUtil.showFailed(entity.msg);
                                        mStatusLayoutManager.showErrorLayout();
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
                        mStatusLayoutManager.showErrorLayout();
                    }
                });
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        isLoadMore = true;
        if (totalPage != -1 && mDefaultPage + 1 <= totalPage) {
            mDefaultPage++;
            loadData(mDefaultPage);
        } else {
            refreshLayout.setEnableLoadMore(false);
            refreshLayout.setNoMoreData(true);
            refreshLayout.finishLoadMore();
        }

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refresh();
    }


    private TuanEntity parseTuanEntity(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseObject(info, TuanEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdapter != null) {
            mAdapter.cancelAllTimers();
        }
        EventBus.getDefault().unregister(this);
    }


    private void setupStatusLayoutManager() {
        mStatusLayoutManager = new StatusLayoutManager.Builder(rrRootView)
                // 设置默认布局属性
//                .setDefaultEmptyText("空白了，哈哈哈哈")
//                .setDefaultEmptyImg(R.mipmap.ic_launcher)
//                .setDefaultEmptyClickViewText("retry")
//                .setDefaultEmptyClickViewTextColor(getResources().getColor(R.color.colorAccent))
//                .setDefaultEmptyClickViewVisible(false)
//
//                .setDefaultErrorText(R.string.app_name)
//                .setDefaultErrorImg(R.mipmap.ic_launcher)
//                .setDefaultErrorClickViewText("重试一波")
//                .setDefaultErrorClickViewTextColor(getResources().getColor(R.color.colorPrimaryDark))
//                .setDefaultErrorClickViewVisible(true)
//
//                .setDefaultLayoutsBackgroundColor(Color.WHITE)

                // 自定义布局
                .setLoadingLayout(getLoadingLayout())
                .setEmptyLayout(inflate(R.layout.custom_empty_layout))
                .setEmptyClickViewID(R.id.tvRefresh)
                .setErrorLayout(inflate(R.layout.custom_error_layout))
                .setErrorClickViewID(R.id.tvRefresh)
//
//                .setLoadingLayout(R.layout.layout_loading)
//                .setEmptyLayout(R.layout.layout_empty)
//                .setErrorLayout(R.layout.layout_error)
//
//                .setEmptyClickViewID(R.id.tv_empty)
//                .setErrorClickViewID(R.id.tv_error)

                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        mStatusLayoutManager.showLoadingLayout();
                        refresh();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        mStatusLayoutManager.showLoadingLayout();
                        refresh();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        mStatusLayoutManager.showLoadingLayout();
                        refresh();
                     /*   if (view.getId() == R.id.tv_customer) {
                            Toast.makeText(MainActivity.this, R.string.request_access, Toast.LENGTH_SHORT).show();
                        } else if (view.getId() == R.id.tv_customer1) {
                            Toast.makeText(MainActivity.this, R.string.switch_account, Toast.LENGTH_SHORT).show();
                        }*/

                    }
                })
                .build();
    }


    private View inflate(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    private void refresh() {
        mRefreshLayout.setNoMoreData(false);
        mRefreshLayout.setEnableLoadMore(true);
        isLoadMore = false;
        mDefaultPage = 1;
        loadData(mDefaultPage);
    }


    /**
     * 拼团支付接口
     */
    private void createPinPay(int payType, int pinId) {
        ApiRepository.getInstance().requestPinPay(payType, pinId).compose(bindUntilEvent(FragmentEvent.DESTROY)).
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
                                                refreshData();
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


    private void showPayDialog(double money, int pinId) {
        PayDialog payDialog = new PayDialog(mContext, money, 0, new PayDialog.PayListener() {
            @Override
            public void pay(int payType, Dialog dialog) {
                mPayType = payType;
                //拼团结算
                createPinPay(payType, pinId);
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
        mHandler.sendMessage(msg);
        //订单生成成功 将状态置为true
    }


    @SuppressWarnings("unchecked")
    private static class PaymentHandler extends Handler {
        private WeakReference<MyTuanListFragment> softReference;

        public PaymentHandler(MyTuanListFragment fragment) {
            softReference = new WeakReference<>(fragment);
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
                                softReference.get().refreshData();
                            } else {
                                ToastUtil.showFailed("支付失败");
                                TourCooLogUtil.e(TAG, result);
                                softReference.get().refreshData();
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
                if (WxConfig.weiXinPayTag == WEI_XIN_PAY_TAG_NORMAL) {
                    //刷新列表
                    refreshData();
                }
                break;
            case EVENT_ACTION_PAY_FRESH_FAILED:
                if (WxConfig.weiXinPayTag == WEI_XIN_PAY_TAG_NORMAL) {
                    TourCooLogUtil.e(TAG, TAG + ":" + "微信支付失败");
                }
                break;
            default:
                break;
        }
    }

}
