package com.tourcoo.xiantao.core.module;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.aries.ui.view.tab.CommonTabLayout;
import com.aries.ui.view.tab.listener.OnTabSelectListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.activity.BaseMainActivity;
import com.tourcoo.xiantao.core.frame.delegate.MainTabDelegate;
import com.tourcoo.xiantao.core.frame.entity.TabEntity;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.action.BaseUpdateDialog;
import com.tourcoo.xiantao.core.widget.core.util.SizeUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.SystemSettingEntity;
import com.tourcoo.xiantao.entity.event.TabChangeEvent;
import com.tourcoo.xiantao.helper.GoodsCount;
import com.tourcoo.xiantao.helper.ShoppingCar;
import com.tourcoo.xiantao.permission.PermissionManager;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.ShoppingCarFragmentVersion2;
import com.tourcoo.xiantao.ui.account.LoginActivity;
import com.tourcoo.xiantao.ui.account.MineFragment;
import com.tourcoo.xiantao.ui.goods.ClassifyGoodsFragmentVersion1;
import com.tourcoo.xiantao.ui.goods.ClassifyGoodsFragmentVersion2;
import com.tourcoo.xiantao.ui.home.HomeFragment;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import pub.devrel.easypermissions.EasyPermissions;

import static com.tourcoo.xiantao.core.common.CommonConstant.companyInfo;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_ADDRESS_KEY;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_TEL_PHONE_KEY;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_TEL_REGISTER_KEY;
import static com.tourcoo.xiantao.ui.account.MineFragment.NO_LOGIN;

/**
 * @author :zhoujian
 * @description : 首页
 * @company :途酷科技
 * @date 2019年03月06日上午 10:07
 * @Email: 971613168@qq.com
 */
public class MainTabActivity extends BaseMainActivity implements EasyPermissions.PermissionCallbacks {
    private static final String EXTRA_SKIP_TAG_MAIN = "EXTRA_SKIP_TAG_MAIN";
    private static final int REQUEST_CODE_SKIP_MAIN = 1000;
    private TabChangeEvent mTabChangeEvent;
    public CommonTabLayout mTabLayout;
    public static final int TAB_INDEX_MINE = 3;

    public static final int TAB_INDEX_SHOPPING_CAR = 2;
    private boolean isFirstLoad = true;
    private MineFragment mineFragment;
    private ShoppingCarFragmentVersion2 shoppingCarFragment;
    private HomeFragment mHomeFragment;
    /**
     * 当前购物车中商品数量
     */
    private int currentGoodsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (NetworkUtil.isConnected(mContext) && AccountInfoHelper.getInstance().isLogin()) {
            checkToken();
        }*/
       /* if (!hasPermission()) {
            //请求一次权限
            PermissionManager.requestAllNeedPermission(this);
        }*/
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    public List<TabEntity> getTabList() {
        ArrayList<TabEntity> tabEntities = new ArrayList<>();
        mineFragment = MineFragment.newInstance();
        shoppingCarFragment = ShoppingCarFragmentVersion2.newInstance();
        mHomeFragment = HomeFragment.newInstance();
        tabEntities.add(new TabEntity("首页", R.mipmap.tab_home_normal, R.mipmap.tab_home_selected, mHomeFragment));
        tabEntities.add(new TabEntity("分类", R.mipmap.tab_classification_normal, R.mipmap.tab_classification_selected, ClassifyGoodsFragmentVersion2.newInstance()));
        tabEntities.add(new TabEntity("购物车", R.mipmap.tab_shopping_cart_normal, R.mipmap.tab_shopping_cart_selected, shoppingCarFragment));
        tabEntities.add(new TabEntity("个人中心", R.mipmap.tab_personal_center_normal, R.mipmap.tab_personal_center_selected, mineFragment));
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
                        //获取购物车中商品数量并刷新商品信息
                        if (position == TAB_INDEX_MINE) {
                            getTotalNumAndRefreshShoppingCar(false);
                            if (mineFragment != null) {
                                mineFragment.checkTokenAndRequestUserInfo();
                            }
                        } else if (position == TAB_INDEX_SHOPPING_CAR) {
                            getTotalNumAndRefreshShoppingCar(true);
                        } else {
                            getTotalNumAndRefreshShoppingCar(false);
                        }
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
        if (mHomeFragment != null) {
            mHomeFragment.requestLocate();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        //权限被用户拒绝 则退出软件
        showPermissionDialog("请前往授权管理授权");
    }


    /**
     * 获取当前购物车中商品数量
     */
    public void getTotalNumAndRefreshShoppingCar(boolean isRequestGoodsList) {
        ApiRepository.getInstance().getTotalNum().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<GoodsCount>>() {
                    @Override
                    public void onRequestNext(BaseEntity<GoodsCount> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    currentGoodsCount = entity.data.getCart_total_num();
                                    showRedDot(currentGoodsCount);
                                    if (currentGoodsCount > 0) {
                                        //根据情况刷新购物车列表
                                        if (shoppingCarFragment != null && isRequestGoodsList) {
                                            shoppingCarFragment.refreshShoppingCarNoDialog(isRequestGoodsList);
                                        }
                                    } else {
                                        if (shoppingCarFragment != null) {
                                            shoppingCarFragment.showEmptyLayout();
                                        }
                                    }
                                }
                            } else {
                                setNoLogin(entity.msg);
                            }
                        }
                    }
                });
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


    public void showRedDot(int count) {
        if (count <= 0) {
            mTabLayout.hideMsg(2);
        } else {
            mTabLayout.showMsg(2, count);
            int leftPading = -SizeUtil.dp2px(5);
            mTabLayout.setMsgMargin(2, leftPading, 0);
        }
    }


    /**
     * 获取系统相关信息
     */
    private void requestSystemConfig() {
        ApiRepository.getInstance().requestSystemConfig().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<SystemSettingEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<SystemSettingEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                SystemSettingEntity settingEntity = entity.data;
                                if (settingEntity != null) {
                                    String register = settingEntity.getRegister();
                                    String phone = settingEntity.getKefu();
                                    if (TextUtils.isEmpty(settingEntity.getAddress())) {
                                        settingEntity.setAddress("");
                                    }
                                    if (TextUtils.isEmpty(settingEntity.getCompany())) {
                                        settingEntity.setCompany("");
                                    }
                                    //保存注册条例
                                    SharedPreferencesUtil.put(PREF_TEL_REGISTER_KEY, register);
                                    SharedPreferencesUtil.put(PREF_TEL_PHONE_KEY, phone);
                                    SharedPreferencesUtil.put(PREF_ADDRESS_KEY, settingEntity.getAddress());
                                    companyInfo = settingEntity.getCompany();
                                    boolean needUpdate = needUpdate(settingEntity.getAndroid_version_code());
                                    boolean isForce = isForce(settingEntity.getAndroid_update());
                                    if (needUpdate) {
                                        updateVersion(mContext, settingEntity.getAndroid_download(), settingEntity.getAndroid_info(), isForce);
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private boolean needUpdate(int versionCode) {
        TourCooLogUtil.i(TAG, TAG + "后台的版本号:" + versionCode);
        int localVersionCode = TourCooUtil.getVersionCode(mContext);
        TourCooLogUtil.i(TAG, TAG + "本地的版本号:" + localVersionCode);
        return localVersionCode < versionCode;
    }

    private boolean isForce(int code) {
        return code == 1;
    }

    public void updateVersion(Context context, String downloadUrl, String content, boolean isForce) {
        try {
            DownloadBuilder builder = AllenVersionChecker.getInstance()
                    .downloadOnly(
                            UIData.create().setDownloadUrl(downloadUrl).setContent(content)
                    );

            if (isForce) {
//        强制更新 取消回调
                builder.setForceUpdateListener(new ForceUpdateListener() {
                    @Override
                    public void onShouldForceUpdate() {
                        ActivityUtils.finishAllActivities();
                    }
                });
            }
            //静默下载
            builder.setSilentDownload(false);
            //如果本地有安装包缓存也会重新下载apk
            builder.setForceRedownload(true);
            //更新界面选择
            builder.setCustomVersionDialogListener(createCustomDialogOne());
            //自定义下载路径
            builder.setDownloadAPKPath(Environment.getExternalStorageDirectory() + "/XianTao/download/");
            builder.executeMission(context);
        } catch (Exception e) {
            ToastUtil.showFailed("下载地址有误");
            TourCooLogUtil.e(TAG, TAG + "更新异常:" + e.toString());
        }

    }

    /**
     * 务必用库传回来的context 实例化你的dialog
     * 自定义的dialog UI参数展示，使用versionBundle
     *
     * @return
     */
    private CustomVersionDialogListener createCustomDialogOne() {
        CustomVersionDialogListener listener = new CustomVersionDialogListener() {
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                BaseUpdateDialog baseDialog = new BaseUpdateDialog(context, R.style.UpdateDialog, R.layout.custom_dialog_one_layout);
                TextView textView = baseDialog.findViewById(R.id.tv_msg);
                textView.setText(versionBundle.getContent());
                return baseDialog;
            }
        };
        return listener;
    }

    @Override
    public void loadData() {
        super.loadData();
        requestSystemConfigDelay();
    }


    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    public boolean copyToClipboard(String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 延迟请求后台配置信息
     */
    private void requestSystemConfigDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!hasPermission()) {
                    PermissionManager.requestAllNeedPermission(mContext);
                }
                requestSystemConfig();
            }
        }, 500);
    }

    public void startActivityAfterLogin(Intent intent) {
        //未登录（这里用自己的登录逻辑去判断是否未登录）
        if (!AccountInfoHelper.getInstance().isLogin()) {
            ComponentName componentName = new ComponentName(mContext, LoginActivity.class);
            intent.putExtra("className", intent.getComponent().getClassName());
            intent.setComponent(componentName);
            super.startActivity(intent);
        } else {
            super.startActivity(intent);
        }
    }


    public void skipToLoginActivity() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SKIP_TAG_MAIN, EXTRA_SKIP_TAG_MAIN);
        intent.setClass(mContext, LoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SKIP_MAIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SKIP_MAIN) {
            if (resultCode == RESULT_OK && mineFragment != null) {
                TourCooLogUtil.i(TAG, TAG + "刷新个人中心:");
                mineFragment.checkTokenAndRequestUserInfo();
            }
        }
    }



}
