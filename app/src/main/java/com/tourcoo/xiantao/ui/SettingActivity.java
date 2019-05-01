package com.tourcoo.xiantao.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.allen.library.SuperTextView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.frame.util.StackUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
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

    @Override
    public int getContentLayout() {
        return R.layout.activity_setting_system;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        stvClearCache = findViewById(R.id.stvClearCache);
        stvClearCache.setOnClickListener(this);
        findViewById(R.id.btnExitLogin).setOnClickListener(this);
        findViewById(R.id.stvResetPassword).setOnClickListener(this);
        showCache();
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
                TourCooUtil.startActivity(mContext, EditPasswordActivity.class);
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
        TourCooUtil.startActivity(mContext, LoginActivity.class);
        finish();
    }
}
