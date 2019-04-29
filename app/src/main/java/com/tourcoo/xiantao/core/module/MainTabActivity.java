package com.tourcoo.xiantao.core.module;

import android.content.DialogInterface;
import android.os.Bundle;

import com.aries.ui.view.tab.CommonTabLayout;
import com.aries.ui.view.tab.listener.OnTabSelectListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.activity.BaseMainActivity;
import com.tourcoo.xiantao.core.frame.delegate.MainTabDelegate;
import com.tourcoo.xiantao.core.frame.entity.TabEntity;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.NetworkUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.TokenInfo;
import com.tourcoo.xiantao.event.TabChangeEvent;
import com.tourcoo.xiantao.helper.GoodsCount;
import com.tourcoo.xiantao.helper.ShoppingCar;
import com.tourcoo.xiantao.permission.PermissionManager;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.ShoppingCarFragmentVersion1;
import com.tourcoo.xiantao.ui.ShoppingCarFragmentVersion2;
import com.tourcoo.xiantao.ui.account.LoginActivity;
import com.tourcoo.xiantao.ui.account.MineFragment;
import com.tourcoo.xiantao.ui.goods.ClassifyGoodsFragment;
import com.tourcoo.xiantao.ui.goods.HomeFragment;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import pub.devrel.easypermissions.EasyPermissions;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :zhoujian
 * @description : 首页
 * @company :途酷科技
 * @date 2019年03月06日上午 10:07
 * @Email: 971613168@qq.com
 */
public class MainTabActivity extends BaseMainActivity implements EasyPermissions.PermissionCallbacks {
    private TabChangeEvent mTabChangeEvent;
    public CommonTabLayout mTabLayout;
    private boolean isFirstLoad = true;
    /**
     * 当前购物车中商品数量
     */
    private int currentGoodsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NetworkUtil.isConnected(mContext) && AccountInfoHelper.getInstance().isLogin()) {
            checkToken();
        }
        if (!hasPermission()) {
            //请求一次权限
            PermissionManager.requestAllNeedPermission(this);
        }
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    public List<TabEntity> getTabList() {
        ArrayList<TabEntity> tabEntities = new ArrayList<>();
        tabEntities.add(new TabEntity("首页", R.mipmap.tab_home_normal, R.mipmap.tab_home_selected, HomeFragment.newInstance()));
        tabEntities.add(new TabEntity("分类", R.mipmap.tab_classification_normal, R.mipmap.tab_classification_selected, ClassifyGoodsFragment.newInstance()));
        tabEntities.add(new TabEntity("购物车", R.mipmap.tab_shopping_cart_normal, R.mipmap.tab_shopping_cart_selected, ShoppingCarFragmentVersion2.newInstance()));
        tabEntities.add(new TabEntity("个人中心", R.mipmap.tab_personal_center_normal, R.mipmap.tab_personal_center_selected, MineFragment.newInstance()));
        return tabEntities;
    }

    @Override
    public void setTabLayout(CommonTabLayout tabLayout) {
        mTabLayout = tabLayout;
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mTabChangeEvent = new TabChangeEvent(0);
        initTabChangeListener();
       /* CheckVersionHelper.with(this)
                .checkVersion(false);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TourCooLogUtil.i(TAG, "onDestroy");
        ShoppingCar.getInstance().clearShoppingCar();
    }

    /**
     * 初始化tab切换监听
     */
    private void initTabChangeListener() {
        if (mTabLayout != null) {
            mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    TourCooLogUtil.i(TAG, "当前位置:" + position);
                    mTabChangeEvent.currentPosition = position;
                    EventBus.getDefault().postSticky(mTabChangeEvent);
                    if (AccountInfoHelper.getInstance().isLogin()) {
                        //获取购物车中商品数量
                        getTotalNum();
                    }
                }

                @Override
                public void onTabReselect(int position) {
                }
            });
        }
    }


    public MainTabDelegate getTabDelegate() {
        return mMainTabDelegate;
    }


    @Override
    protected void onResume() {
        super.onResume();
        //如果没有权限 则请求一次权限
        if (!hasPermission() && !isFirstLoad) {
            showPermissionDialog("您未授予相关权限,软件即将退出");
        }
        isFirstLoad = false;
        TourCooLogUtil.i(TAG, TAG + ":" + "onResume");
    }

    /**
     * 显示权限弹窗
     *
     * @param msg
     */
    protected void showPermissionDialog(String msg) {
        showAlertDialog("未授予权限", msg, "我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
    }

    /**
     * 判断是否有相关权限
     *
     * @return
     */
    private boolean hasPermission() {
        return PermissionManager.checkAllNeedPermission(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //权限已被用户授予 //什么也不做
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //权限被用户拒绝 则退出软件
        showPermissionDialog("请前往授权管理授权");
    }

    /**
     * 校验token是否失效
     */
    private void checkToken() {
        ApiRepository.getInstance().checkToken().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<TokenInfo>>() {
                    @Override
                    public void onRequestNext(BaseEntity<TokenInfo> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                tokenCheckCallBack(entity.data);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private void tokenCheckCallBack(TokenInfo tokenInfo) {
        if (tokenInfo == null || AccountInfoHelper.getInstance().getUserInfo() == null) {
            TourCooUtil.startActivity(mContext, LoginActivity.class);
            finish();
            return;
        }
        if (!tokenInfo.getToken().equals(AccountInfoHelper.getInstance().getToken())) {
            ToastUtil.showFailed("登录已失效,请重新登录");
            TourCooUtil.startActivity(mContext, LoginActivity.class);
            finish();
        }
    }


    /**
     * 获取当前购物车中商品数量
     */
    public void getTotalNum() {
        ApiRepository.getInstance().getTotalNum().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<GoodsCount>>() {
                    @Override
                    public void onRequestNext(BaseEntity<GoodsCount> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    currentGoodsCount = entity.data.getCart_total_num();
                                    showRedDot(currentGoodsCount);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    public void showRedDot(int count) {
        if (count <= 0) {
            mTabLayout.hideMsg(2);
        } else {
            mTabLayout.showMsg(2, count);
        }
    }





}
