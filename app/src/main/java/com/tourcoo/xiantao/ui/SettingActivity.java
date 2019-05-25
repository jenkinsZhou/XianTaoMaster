package com.tourcoo.xiantao.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.frame.util.StackUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.SystemSettingEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.account.EditPasswordActivity;
import com.tourcoo.xiantao.ui.account.LoginActivity;
import com.tourcoo.xiantao.util.DataCleanManager;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.util.DataCleanManager.EMPTY_CACHE;

/**
 * @author :zhoujian
 * @description :设置页面
 * @company :翼迈科技
 * @date 2019年 04月 20日 18时56分
 * @Email: 971613168@qq.com
 */
public class SettingActivity extends BaseTourCooTitleActivity implements View.OnClickListener {
    private SuperTextView stvClearCache;
    private TextView btnGoLogin;
    private TextView btnExitLogin;

    @Override
    public int getContentLayout() {
        return R.layout.activity_setting_system;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        findViewById(R.id.ivReturn).setOnClickListener(this);
        stvClearCache = findViewById(R.id.stvClearCache);
        btnGoLogin = findViewById(R.id.btnGoLogin);
        stvClearCache.setOnClickListener(this);
        btnExitLogin = findViewById(R.id.btnExitLogin);
        btnExitLogin.setOnClickListener(this);
        findViewById(R.id.stvResetPassword).setOnClickListener(this);
        findViewById(R.id.stvAboutUs).setOnClickListener(this);
        btnGoLogin.setOnClickListener(this);
        showCache();
        showLogin();
    }

    private void showLogin() {
        if (AccountInfoHelper.getInstance().isLogin()) {
            btnGoLogin.setVisibility(View.GONE);
            btnExitLogin.setVisibility(View.VISIBLE);
        } else {
            btnGoLogin.setVisibility(View.VISIBLE);
            btnExitLogin.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setHeight(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stvClearCache:
                cleanCache();
                showCache();
                ToastUtil.showSuccess("清除成功");
                break;
            case R.id.btnExitLogin:
                if (AccountInfoHelper.getInstance().isLogin()) {
                    logout();
                } else {
                    ToastUtil.show("您还未登录");
                }
                break;
            case R.id.stvResetPassword:
                if (AccountInfoHelper.getInstance().isLogin()) {
                    TourCooUtil.startActivity(mContext, EditPasswordActivity.class);
                } else {
                    TourCooUtil.startActivity(mContext, LoginActivity.class);
                }
                break;
            case R.id.stvAboutUs:
                //关于我们
                TourCooUtil.startActivity(mContext, AboutUsActivity.class);
                break;
            case R.id.btnGoLogin:
                //去登陆
                TourCooUtil.startActivity(mContext, LoginActivity.class);
                break;
            case R.id.ivReturn:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    private String getCacheSize() {
        String str = "";
        try {
            str = DataCleanManager.getTotalCacheSize(mContext);
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
        return str;
    }


    /**
     * 清空缓存
     */
    private void cleanCache() {
        DataCleanManager.clearAllCache(mContext);
    }


    private void showCache() {
        if (EMPTY_CACHE.equalsIgnoreCase(getCacheSize())) {
            stvClearCache.setRightString("");
        } else {
            stvClearCache.setRightString(getCacheSize());
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        ApiRepository.getInstance().logout().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity == null) {
                            ToastUtil.showFailed("服务器异常");
                            return;
                        }
                        if (entity.code == CODE_REQUEST_SUCCESS) {
                            //todo 注销登录
                            doLogoutCallback();
                        } else {
                            //说明此时token已经失效 需要重新登录
                            AccountInfoHelper.getInstance().deleteUserAccount();
                            ToastUtil.showFailed(entity.msg);
                        }
                    }
                });
    }


    private void doLogoutCallback() {
        AccountInfoHelper.getInstance().deleteUserAccount();
        SharedPreferencesUtil.clearAll(mContext);
        Activity activity = StackUtil.getInstance().getActivity(MainTabActivity.class);
        if (activity != null) {
            activity.finish();
        }
        Intent intent = new Intent(mContext, MainTabActivity.class);
        Intent intentMain = new Intent(mContext, LoginActivity.class);
        Intent[] intents = {intent, intentMain};
        startActivities(intents);
        finish();
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
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }
}
