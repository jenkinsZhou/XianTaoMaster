package com.tourcoo.xiantao.ui.account;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.tourcoo.xiantao.core.common.RequestConfig;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleFragment;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.StatusBarUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.MenuItem;
import com.tourcoo.xiantao.entity.TokenInfo;
import com.tourcoo.xiantao.entity.user.PensonalCenterBean;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.SettingActivity;
import com.tourcoo.xiantao.ui.goods.CollectionGoodsListActivity;
import com.tourcoo.xiantao.ui.order.MyOrderListActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :个人中心
 * @company :途酷科技
 * @date 2019年03月28日17:40
 * @Email: 971613168@qq.com
 */
public class MineFragment extends BaseTitleFragment implements View.OnClickListener, OnRefreshListener {
    private MenuAdapter mMenuAdapter;
    private List<MenuItem> mMenuItemList = new ArrayList<>();
    private RecyclerView rvMineMenu;
    private TextView tvUserNickName;
    private CircleImageView civUserAvatar;
    private SmartRefreshLayout refreshLayout;
    /**
     * 账户余额
     */
    private TextView tvBalance;
    private TextView tvAccumulatePoints;

    private TextView tvRedDotWaitReturn;
    private TextView tvRedDotWaitPay;
    private TextView tvRedDotWaitSend;
    private TextView tvRedDotWaitReceive;
    private TextView tvRedDotWaitEvaluate;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        refreshLayout = mContentView.findViewById(R.id.refreshLayout);
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
        tvAccumulatePoints = mContentView.findViewById(R.id.tvAccumulatePoints);
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
        if (AccountInfoHelper.getInstance().isLogin()) {
            checkTokenAndRequestUserInfo();
        } else {
            showUnLoginUI();
        }
    }


    private void initMenu() {
        mMenuItemList.clear();
        mMenuItemList.add(new MenuItem(R.mipmap.ic_account_balance, "账户余额"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_spell_group_records, "拼团记录"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_collection_goods, "收藏商品"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_shipping_address, "收货地址"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_customer_service_telephone, "客服电话"));
        mMenuItemList.add(new MenuItem(R.mipmap.ic_invoice_information, "发票信息"));
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
                    case 0:
                        //账户余额
                        TourCoolUtil.startActivity(mContext, AccountBalanceActivity.class);
                        break;
                    case 1:
                        //拼团记录
                        break;
                    case 2:
                        //收藏商品
                        TourCoolUtil.startActivity(mContext, CollectionGoodsListActivity.class);
                        break;
                    case 3:
                        //收货地址
                        if (!AccountInfoHelper.getInstance().isLogin()) {
                            ToastUtil.show("您还没有登录");
                            return;
                        }
                        TourCoolUtil.startActivity(mContext, AddressManagerActivity.class);
                        break;
                    case 4:
                        //客服电话
                        showPhoneDialog();
                        break;
                    case 5:
                        //发票信息
                        TourCoolUtil.startActivity(mContext, InvoiceInformationActivity.class);
                        break;
                    case 6:
                        break;
                    case 7:
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
        builder.setTitle("客服电话").setFirstMessage(getString(R.string.customer_service_telephone_numbers))
                .setFirstMsgSize(15).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.show("呼叫");
                        dialog.dismiss();
//                        ApiRepository.getInstance().updateApp()
                    }
                });
        showConfirmDialog(builder);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAllOrder:
                TourCooUtil.startActivity(mContext, MyOrderListActivity.class);
                break;
            case R.id.llWaitPay:
                TourCooUtil.startActivity(mContext, RegisterActivity.class);
                break;
            case R.id.ivSetting:
                TourCooUtil.startActivity(mContext, SettingActivity.class);
                break;
            case R.id.tvUserNickName:
                if (AccountInfoHelper.getInstance().isLogin()) {
                    return;
                }
                TourCooUtil.startActivity(mContext, LoginActivity.class);
                break;
            case R.id.civUserAvatar:
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    ToastUtil.show("您还未登录");
                    return;
                }
                TourCooUtil.startActivity(mContext, PersonalDataActivity.class);
                break;
            default:
                break;
        }
    }


    private void showUI(PensonalCenterBean data) {
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
        GlideManager.loadImg(TourCooUtil.getDrawable(R.mipmap.img_default_avatar), civUserAvatar);
        tvBalance.setText("_");
        tvAccumulatePoints.setText("_");
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
    private void showHasLoginUI(PensonalCenterBean data) {
        if (data == null) {
            showUnLoginUI();
            return;
        }
        tvUserNickName.setText(getNotNullValue(data.getNickname()));
        String url = TourCooUtil.getUrl(data.getAvatar());
        TourCooLogUtil.i(TAG, TAG + "头像:" + url);
        GlideManager.loadImg(url, civUserAvatar);
        tvBalance.setText("￥" + data.getCash());
//        tvAccumulatePoints.setText(userInfo.getScore() + "积分");
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
        ApiRepository.getInstance().getPersonalCenter().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    LogUtils.i(JSON.toJSONString(entity.data));
                                    PensonalCenterBean centerBean = parseInfo(entity.data);
                                    if (centerBean != null) {
                                        showUI(centerBean);
                                        refreshLayout.finishRefresh(true);
                                    } else {
                                        showUnLoginUI();
                                        refreshLayout.finishRefresh(false);
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                refreshLayout.finishRefresh(false);
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


    private PensonalCenterBean parseInfo(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseObject(info, PensonalCenterBean.class);
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

    private void showMineInfo(PensonalCenterBean data) {
        if (data == null) {
            return;
        }
        showRedDot(tvRedDotWaitEvaluate, data.getNocomment());
        showRedDot(tvRedDotWaitReturn, data.getReturnX());
        showRedDot(tvRedDotWaitPay, data.getNopay());
        showRedDot(tvRedDotWaitSend, data.getNofreight());
        showRedDot(tvRedDotWaitReceive, data.getNoreceipt());
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        checkTokenAndRequestUserInfo();
    }

    /**
     * 校验token是否失效
     */
    private void checkTokenAndRequestUserInfo() {
        ApiRepository.getInstance().checkToken().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<TokenInfo>>() {
                    @Override
                    public void onRequestNext(BaseEntity<TokenInfo> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                boolean isLogin = tokenCheckCallBack(entity.data);
                                if (isLogin) {
                                    getPersonalCenter();
                                } else {
                                    showUnLoginUI();
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private boolean tokenCheckCallBack(TokenInfo tokenInfo) {
        if (tokenInfo == null || AccountInfoHelper.getInstance().getUserInfo() == null) {
            return false;
        }
        return tokenInfo.getToken().equals(AccountInfoHelper.getInstance().getToken());
    }
}
