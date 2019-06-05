package com.tourcoo.xiantao.ui.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.MenuAdapter;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleFragment;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.frame.util.SizeUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.action.ActionSheetDialog;
import com.tourcoo.xiantao.core.widget.core.action.BaseDialog;
import com.tourcoo.xiantao.core.widget.core.util.StatusBarUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.MenuItem;
import com.tourcoo.xiantao.entity.SystemSettingEntity;
import com.tourcoo.xiantao.entity.TokenInfo;
import com.tourcoo.xiantao.entity.event.LoginEvent;
import com.tourcoo.xiantao.entity.event.MessageEvent;
import com.tourcoo.xiantao.entity.event.RefreshEvent;
import com.tourcoo.xiantao.entity.message.MessageBean;
import com.tourcoo.xiantao.entity.user.PersonalCenterInfo;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.FeedbackActivity;
import com.tourcoo.xiantao.ui.SettingActivity;
import com.tourcoo.xiantao.ui.coin.MyCoinListActivity;
import com.tourcoo.xiantao.ui.discount.MyDiscountListActivity;
import com.tourcoo.xiantao.ui.goods.CollectionGoodsListActivity;
import com.tourcoo.xiantao.ui.mine.MyInviteCodeActivity;
import com.tourcoo.xiantao.ui.msg.MsgSystemActivity;
import com.tourcoo.xiantao.ui.order.MyOrderListActivity;
import com.tourcoo.xiantao.ui.order.ReturnOrderListActivity;
import com.tourcoo.xiantao.ui.recharge.AccountBalanceActivity;
import com.tourcoo.xiantao.ui.tuan.MyTuanListActivity;
import com.tourcoo.xiantao.util.MoneyUtil;
import com.trello.rxlifecycle3.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_TEL_PHONE_KEY;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_TEL_QQ_KEY;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_TEL_WEI_XIN_KEY;
import static com.tourcoo.xiantao.ui.order.MyOrderListActivity.EXTRA_CURRENT_TAB_INDEX;

/**
 * @author :JenkinsZhou
 * @description :个人中心
 * @company :途酷科技
 * @date 2019年03月28日17:40
 * @Email: 971613168@qq.com
 */
public class MineFragment extends BaseTitleFragment implements View.OnClickListener, OnRefreshListener {

    public static final String NO_LOGIN = "请登录";
    private MenuAdapter mMenuAdapter;
    private List<MenuItem> mMenuItemList = new ArrayList<>();
    private RecyclerView rvMineMenu;
    private TextView tvUserNickName;
    private CircleImageView civUserAvatar;
    private String phone;
    private String weiXinNumber;
    private String qqNumber;
    private SmartRefreshLayout refreshLayout;
    public static final int REQUEST_CODE_EDIT_USER_INFO = 10;
    public static final int REQUEST_CODE_MESSAGE_CENTER = 11;
    private TextView tvMessageCount;

    /**
     * 账户余额
     */
    private TextView tvBalance;

//    private TextView tvAccumulatePoints;

    private TextView tvRedDotWaitReturn;
    private TextView tvRedDotWaitPay;
    private TextView tvRedDotWaitSend;
    private TextView tvRedDotWaitReceive;
    private TextView tvRedDotWaitEvaluate;
    private ImageView ivMsg;
    private MainTabActivity mMainTabActivity;
    private TextView tvAccumulatePointsGold;
    private TextView tvAccumulatePointsYin;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mMainTabActivity = (MainTabActivity) mContext;
        refreshLayout = mContentView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        mContentView.findViewById(R.id.llGold).setOnClickListener(this);
        mContentView.findViewById(R.id.llYin).setOnClickListener(this);
        tvMessageCount = mContentView.findViewById(R.id.tvMessageCount);
        tvAccumulatePointsGold = mContentView.findViewById(R.id.tvAccumulatePointsGold);
        tvAccumulatePointsYin = mContentView.findViewById(R.id.tvAccumulatePointsYin);
        mContentView.findViewById(R.id.llReturnGood).setOnClickListener(this);
        mContentView.findViewById(R.id.llWaitSend).setOnClickListener(this);
        mContentView.findViewById(R.id.llWaitReceive).setOnClickListener(this);
        mContentView.findViewById(R.id.llWaitEvaluate).setOnClickListener(this);
        mContentView.findViewById(R.id.llWaitEvaluate).setOnClickListener(this);
        ivMsg = mContentView.findViewById(R.id.ivMsg);
        ivMsg.setOnClickListener(this);
        tvRedDotWaitPay = mContentView.findViewById(R.id.tvRedDotWaitPay);
        tvRedDotWaitEvaluate = mContentView.findViewById(R.id.tvRedDotWaitEvaluate);
        tvRedDotWaitSend = mContentView.findViewById(R.id.tvRedDotWaitSend);
        tvRedDotWaitReceive = mContentView.findViewById(R.id.tvRedDotWaitReceive);
        tvRedDotWaitReturn = mContentView.findViewById(R.id.tvRedDotWaitReturn);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        tvUserNickName = mContentView.findViewById(R.id.tvUserNickName);
        tvUserNickName.setOnClickListener(this);
        civUserAvatar = mContentView.findViewById(R.id.civUserAvatar);
        civUserAvatar.setOnClickListener(this);
        tvBalance = mContentView.findViewById(R.id.tvBalance);
    /*    tvAccumulatePoints = mContentView.findViewById(R.id.tvAccumulatePoints);
        tvAccumulatePoints.setOnClickListener(this);*/
        RelativeLayout rlSearch = mContentView.findViewById(R.id.rlTitleBar);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rlSearch.getLayoutParams());
        //4个参数按顺序分别是左上右下
        lp.setMargins(0, StatusBarUtil.getStatusBarHeight(), 0, 0);
        rlSearch.setLayoutParams(lp);
        rvMineMenu = mContentView.findViewById(R.id.rvMineMenu);
        TourCoolRecycleViewDivider divider = new TourCoolRecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.grayDivider));
        rvMineMenu.addItemDecoration(divider);
        rvMineMenu.setLayoutManager(new GridLayoutManager(mContext, 4));
        mContentView.findViewById(R.id.llWaitPay).setOnClickListener(this);
        EventBus.getDefault().register(this);
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("我的");
    }

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadData() {
        super.loadData();
        init();
        phone = (String) SharedPreferencesUtil.get(PREF_TEL_PHONE_KEY, "");
        qqNumber = (String) SharedPreferencesUtil.get(PREF_TEL_QQ_KEY, "");
        weiXinNumber = (String) SharedPreferencesUtil.get(PREF_TEL_WEI_XIN_KEY, "");
        requestSystemConfig();
        if (AccountInfoHelper.getInstance().isLogin()) {
            checkTokenAndRequestUserInfo();
        } else {
            showUnLoginUI();
        }
    }


    private void initMenu() {
        mMenuItemList.clear();
        mMenuItemList.add(new MenuItem(R.mipmap.ic_coupons, "优惠券"));
//        mMenuItemList.add(new MenuItem(R.mipmap.ic_account_balance, "账户余额"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_spell_group_records, "拼团记录"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_collection_goods, "收藏商品"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_shipping_address, "收货地址"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_customer_service_telephone, "客服电话"));
//        mMenuItemList.add(new MenuItem(R.mipmap.ic_invoice_information, "发票信息"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_invitation_code_mine, "邀请码"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_problem_feedback, "问题反馈"));
    }

    private void init() {
        initMenu();
        mContentView.findViewById(R.id.ivSetting).setOnClickListener(this);
        mContentView.findViewById(R.id.llAllOrder).setOnClickListener(this);
        mMenuAdapter = new MenuAdapter(mMenuItemList);
        mMenuAdapter.bindToRecyclerView(rvMineMenu);
        mMenuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                   /* case 0:
                        //账户余额
                        if (!AccountInfoHelper.getInstance().isLogin()) {
                            TourCoolUtil.startActivity(mContext, LoginActivity.class);
                            return;
                        }
                        skipAccount();
                        break;*/

                    case 0:
                        //我的优惠券
                        if (!AccountInfoHelper.getInstance().isLogin()) {
                            skipToLoginActivity();
                            return;
                        }
                        TourCoolUtil.startActivity(mContext, MyDiscountListActivity.class);
                        break;

                    case 1:
                        //拼团记录
                        if (!AccountInfoHelper.getInstance().isLogin()) {
                            skipToLoginActivity();
                            return;
                        }
                        TourCoolUtil.startActivity(mContext, MyTuanListActivity.class);
                        break;
                    case 2:
                        //收藏商品
                        if (!AccountInfoHelper.getInstance().isLogin()) {
                            skipToLoginActivity();
                            return;
                        }
                        TourCoolUtil.startActivity(mContext, CollectionGoodsListActivity.class);
                        break;
                    case 3:
                        //收货地址
                        if (!AccountInfoHelper.getInstance().isLogin()) {
                            skipToLoginActivity();
                            return;
                        }
                        TourCoolUtil.startActivity(mContext, AddressManagerActivity.class);
                        break;
                    case 4:
                        //客服
                        showContactUsDialog();
                        break;
                    case 5:
                        //邀请码
                        if (!AccountInfoHelper.getInstance().isLogin()) {
                            skipToLoginActivity();
                            return;
                        }
                        TourCoolUtil.startActivity(mContext, MyInviteCodeActivity.class);
                        break;
                    case 6:
                        //问题反馈
                        TourCoolUtil.startActivity(mContext, FeedbackActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private void showPhoneDialog() {
        //客服电话
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(mContext);
        builder.setTitle("客服电话").setFirstMessage(phone)
                .setFirstMsgSize(15).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        call(phone);
                        dialog.dismiss();
//                        ApiRepository.getInstance().updateApp()
                    }
                });
        showConfirmDialog(builder);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llYin:
            case R.id.llGold:
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    skipToLoginActivity();
                    return;
                }
                Intent coinIntent = new Intent();
                coinIntent.setClass(mContext, MyCoinListActivity.class);
                startActivityForResult(coinIntent, REQUEST_CODE_EDIT_USER_INFO);
                break;
            case R.id.llAllOrder:
                //订单列表
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    TourCooUtil.startActivity(mContext, LoginActivity.class);
                    return;
                }
                TourCooUtil.startActivity(mContext, MyOrderListActivity.class);
                break;
            case R.id.llWaitPay:
                //待付款
                skipToOrderList(1);
                break;
            case R.id.ivSetting:
                TourCooUtil.startActivity(mContext, SettingActivity.class);
                break;
            case R.id.tvUserNickName:
                if (AccountInfoHelper.getInstance().isLogin()) {
                    Intent intent = new Intent();
                    intent.setClass(mContext, PersonalDataActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_EDIT_USER_INFO);
                    return;
                }
                TourCooUtil.startActivity(mContext, LoginActivity.class);
                break;
            case R.id.civUserAvatar:
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    TourCooUtil.startActivity(mContext, LoginActivity.class);
                    return;
                }
                Intent intent = new Intent();
                intent.setClass(mContext, PersonalDataActivity.class);
                startActivityForResult(intent, REQUEST_CODE_EDIT_USER_INFO);
                break;
            case R.id.ivMsg:
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    TourCooUtil.startActivity(mContext, LoginActivity.class);
                    return;
                }
                skipToMessageCenter();
                break;

            case R.id.llReturnGood:
                //退货列表
                skipReturnOrderList();
                break;
            case R.id.llWaitSend:
                //待发货
                skipToOrderList(2);
                break;
            case R.id.llWaitReceive:
                //待收货
                skipToOrderList(3);
                break;
            case R.id.llWaitEvaluate:
                //待评价
                skipToOrderList(4);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_EDIT_USER_INFO:
                if (resultCode == RESULT_OK) {
                    checkTokenAndRequestUserInfo();
                    TourCooLogUtil.i(TAG, TAG + ":" + "刷新了");
                }
            case REQUEST_CODE_MESSAGE_CENTER:
                if (resultCode == RESULT_OK) {
                    checkTokenAndRequestUserInfo();
                    TourCooLogUtil.i(TAG, TAG + ":" + "刷新了");
                }
                break;
            default:
                break;
        }
    }

    private void showUI(PersonalCenterInfo data) {
        if (AccountInfoHelper.getInstance().isLogin()) {
            showHasLoginUI(data);
        } else {
            showUnLoginUI();
        }

    }

    /**
     * 未登录状态下显示
     */
    private void showUnLoginUI() {
        tvUserNickName.setText("登录/注册");
        GlideManager.loadDefaultAvatar(TourCooUtil.getDrawable(R.mipmap.img_default_avatar), civUserAvatar);
        tvBalance.setText("_");
        tvAccumulatePointsGold.setText("_");
        tvAccumulatePointsYin.setText("_");
        showRedDot(tvRedDotWaitEvaluate, 0);
        showRedDot(tvRedDotWaitReturn, 0);
        showRedDot(tvRedDotWaitPay, 0);
        showRedDot(tvRedDotWaitSend, 0);
        showRedDot(tvRedDotWaitReceive, 0);
    }

    /**
     * 已登录状态下显示
     *
     * @param data
     */
    private void showHasLoginUI(PersonalCenterInfo data) {
        if (data == null) {
            showUnLoginUI();
            return;
        }
        tvUserNickName.setText(getNotNullValue(data.getNickname()));
        String url = TourCooUtil.getUrl(data.getAvatar());
        TourCooLogUtil.i(TAG, TAG + "头像:" + url);
        TourCooLogUtil.i(TAG, TAG + "昵称:" + data.getNickname());
        GlideManager.loadImg(url, civUserAvatar, TourCooUtil.getDrawable(R.mipmap.img_default_avatar));
        tvBalance.setText("¥" + data.getCash());
        String coinGold = TourCooUtil.doubleTransStringZhen(data.getAu()) + "";
//        String coinYin = MoneyUtil.amountConversion(data.getAg()) + "";
        String coinYin =  TourCooUtil.doubleTransStringZhen(data.getAg()) + "";
        tvAccumulatePointsGold.setText(coinGold);
        tvAccumulatePointsYin.setText(coinYin);
        showMineInfo(data);
    }


    private String getNotNullValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return "未填写";
        }
        return value;
    }


    /**
     * 获取个人中心信息
     */
    private void getPersonalCenter() {
        TourCooLogUtil.i(TAG, TAG + ":" + "执行了1");
        ApiRepository.getInstance().getPersonalCenter().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    LogUtils.i(JSON.toJSONString(entity.data));
                                    PersonalCenterInfo centerBean = parseInfo(entity.data);
                                    if (centerBean != null) {
                                        AccountInfoHelper.getInstance().savePersonalCenter(centerBean);
                                        showUI(centerBean);
                                        refreshLayout.finishRefresh(true);
                                    } else {
                                        showUnLoginUI();
                                        refreshLayout.finishRefresh(false);
                                    }
                                }
                            } else {
                                if (entity.msg.contains(NO_LOGIN)) {
                                    AccountInfoHelper.getInstance().deleteUserAccount();
                                } else {
                                    ToastUtil.showFailed(entity.msg);
                                }
                                refreshLayout.finishRefresh();
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        refreshLayout.finishRefresh(false);
                    }
                });
    }


    private PersonalCenterInfo parseInfo(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseObject(info, PersonalCenterInfo.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }

    /**
     * 显示小红点
     *
     * @param tvDot
     * @param count
     */
    private void showRedDot(TextView tvDot, int count) {
        if (count <= 0) {
            tvDot.setVisibility(View.GONE);
            return;
        }
        tvDot.setVisibility(View.VISIBLE);
        if (count > 99) {
            tvDot.setText("99+");
        } else {
            tvDot.setText(count + "");
        }
    }

    private void showMineInfo(PersonalCenterInfo data) {
        if (data == null) {
            return;
        }
        showRedDot(tvRedDotWaitEvaluate, data.getNocomment());
        showRedDot(tvRedDotWaitReturn, data.getReturnnum());
        showRedDot(tvRedDotWaitPay, data.getNopay());
        showRedDot(tvRedDotWaitSend, data.getNofreight());
        showRedDot(tvRedDotWaitReceive, data.getNoreceipt());
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        checkTokenAndRequestUserInfo();
    }

    /**
     * 校验token是否失效并刷新个人信息
     */
    public void checkTokenAndRequestUserInfo() {
        ApiRepository.getInstance().checkToken().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<TokenInfo>>() {
                    @Override
                    public void onRequestNext(BaseEntity<TokenInfo> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                boolean isLogin = tokenCheckCallBack(entity.data);
                                if (isLogin) {
                                    getPersonalCenter();
                                    //获取未读消息数量
                                    requestMessageNoReadCount();
                                } else {
                                    showUnLoginUI();
                                }
                            } else {
                                refreshLayout.finishRefresh();
                                setNoLogin(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        refreshLayout.finishRefresh(false);
                    }
                });
    }


    private boolean tokenCheckCallBack(TokenInfo tokenInfo) {
        if (tokenInfo == null || AccountInfoHelper.getInstance().getUserInfo() == null) {
            return false;
        }
        return tokenInfo.getToken().equals(AccountInfoHelper.getInstance().getToken());
    }

    private void showMsg(int noReadCount) {
        if (noReadCount > 0) {
            if (noReadCount > 99) {
                tvMessageCount.setText("99+");
                tvMessageCount.setVisibility(View.VISIBLE);
            } else {
                tvMessageCount.setText(String.valueOf(noReadCount));
                tvMessageCount.setVisibility(View.VISIBLE);
            }

        } else {
            tvMessageCount.setVisibility(View.GONE);
        }
    }

    private void skipToMessageCenter() {
        Intent intent = new Intent();
        intent.setClass(mContext, MsgSystemActivity.class);
        startActivityForResult(intent, REQUEST_CODE_MESSAGE_CENTER);
    }


    /**
     * 查询消息列表
     */
    private void requestMessageNoReadCount() {
        ApiRepository.getInstance().requestMessageNoReadCount().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<MessageBean>>() {
                    @Override
                    public void onRequestNext(BaseEntity<MessageBean> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                TourCooLogUtil.i(TAG, "未读消息数量:" + entity.data.getNum());
                                //并且将消息数量发送出去
                                EventBus.getDefault().post(new MessageEvent(entity.data.getNum()));
                                showMsg(entity.data.getNum());
                            } else {
                                setNoLogin(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 跳转到订单列表
     *
     * @param index
     */
    private void skipToOrderList(int index) {
        if (!AccountInfoHelper.getInstance().isLogin()) {
            TourCoolUtil.startActivity(mContext, LoginActivity.class);
            return;
        }
        Intent intent = new Intent();
        intent.setClass(mContext, MyOrderListActivity.class);
        intent.putExtra(EXTRA_CURRENT_TAB_INDEX, index);
        startActivityForResult(intent, REQUEST_CODE_MESSAGE_CENTER);
    }


    /**
     * @param refreshEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshUIEvent(RefreshEvent refreshEvent) {
        //todo 刷新ui
        if (refreshEvent == null) {
            return;
        }
        checkTokenAndRequestUserInfo();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /**
     * 退货列表
     */
    private void skipReturnOrderList() {
        if (!AccountInfoHelper.getInstance().isLogin()) {
            TourCoolUtil.startActivity(mContext, LoginActivity.class);
            return;
        }
        Intent returnIntent = new Intent();
        returnIntent.setClass(mContext, ReturnOrderListActivity.class);
        startActivityForResult(returnIntent, REQUEST_CODE_EDIT_USER_INFO);
    }


    private void skipAccount() {
        Intent coinIntent = new Intent();
        coinIntent.setClass(mContext, AccountBalanceActivity.class);
        startActivityForResult(coinIntent, REQUEST_CODE_EDIT_USER_INFO);
    }


    /**
     * 获取系统相关信息
     */
    private void requestSystemConfig() {
        ApiRepository.getInstance().requestSystemConfig().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<SystemSettingEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<SystemSettingEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                SystemSettingEntity settingEntity = entity.data;
                                if (settingEntity != null) {
                                    phone = settingEntity.getKefu();
                                    qqNumber = settingEntity.getKefu_qq();
                                    weiXinNumber = settingEntity.getKefu_wx();
                                    SharedPreferencesUtil.put(PREF_TEL_PHONE_KEY, phone);
                                    SharedPreferencesUtil.put(PREF_TEL_QQ_KEY, qqNumber);
                                    SharedPreferencesUtil.put(PREF_TEL_WEI_XIN_KEY, weiXinNumber);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }

    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void showSheetDialog(String phone, String qqNumber, String weiXinNumber) {
        String[] array = new String[]{"客服电话", "客服QQ", "客服微信"};
        array[0] = phone;
        array[1] = qqNumber;
        array[2] = weiXinNumber;
        new ActionSheetDialog.ListIOSBuilder(mContext)
                .addItems(array)
                .setTitle("联系客服")
//                .setBackgroundColor(TourCooUtil.getColor(R.color.whiteCommon))
                .setItemsTextColorResource(R.color.colorActionSheetItemText)
                .setCancel(R.string.cancel)
                .setCancelMarginTop(SizeUtil.dp2px(8))
                .setCancelTextColorResource(R.color.colorActionSheetItemText)
                .setOnItemClickListener(mOnItemClickListener)
                .create()
//                .setDimAmount(0.6f)
                .show();
    }


    private ActionSheetDialog.OnItemClickListener mOnItemClickListener = new ActionSheetDialog.OnItemClickListener() {
        @Override
        public void onClick(BaseDialog dialog, View itemView, int position) {
            switch (position) {
                case 0:
                    call(phone);
                    break;
                case 1:
                    mMainTabActivity.copyToClipboard(qqNumber);
                    ToastUtil.showSuccess("已经复制到粘贴板");
                    break;
                case 2:
                    mMainTabActivity.copyToClipboard(weiXinNumber);
                    ToastUtil.showSuccess("已经复制到粘贴板");
                    break;
                default:
                    break;

            }
            dialog.dismiss();
        }
    };


    private void showContactUsDialog() {
        if (TextUtils.isEmpty(weiXinNumber) || TextUtils.isEmpty(qqNumber)) {
            ToastUtil.show("未获取到客服联系方式");
            return;
        }
        String phoneValue = "客服电话:" + phone + " (点击拨打)";
        String qqValue = "客服QQ:" + qqNumber + " (点击复制)";
        String weiXinValue = "客服微信:" + weiXinNumber + " (点击复制)";
        showSheetDialog(phoneValue, qqValue, weiXinValue);
    }


    private void skipToAddressManager() {

    }


    private void setNoLogin(String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        if (value.contains(NO_LOGIN)) {
            AccountInfoHelper.getInstance().deleteUserAccount();
        } else {
            ToastUtil.showFailed(value);
        }
    }


    private void skipToLoginActivity(){
        if(mMainTabActivity != null){
            mMainTabActivity.skipToLoginActivity();
        }
    }


    /**
     * 登录回调
     * @param loginEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent loginEvent) {
        //todo 刷新ui
        if (loginEvent == null) {
            return;
        }
        checkTokenAndRequestUserInfo();
    }
}



