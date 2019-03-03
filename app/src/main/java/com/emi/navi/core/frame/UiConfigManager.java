package com.emi.navi.core.frame;


import android.app.Application;

import com.emi.navi.core.frame.interfaces.SwipeBackControl;
import com.emi.navi.core.frame.interfaces.TitleBarViewControl;
import com.emi.navi.core.frame.interfaces.ToastControl;

/**
 * @author :zhoujian
 * @description :UI配置管理类
 * @company :翼迈科技
 * @date 2019年 03月 02日 20时26分
 * @Email: 971613168@qq.com
 */
public class UiConfigManager {

    private static Application mApplication;
    private UiConfigManager() {
    }

    private static class SingletonInstance {
        private static final UiConfigManager INSTANCE = new UiConfigManager();
    }

    public static UiConfigManager getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public UiConfigManager setmToastControl(ToastControl mToastControl) {
        this.mToastControl = mToastControl;
        return this;
    }

    public Application getApplication() {
        return mApplication;
    }

    /**
     * ToastUtil相关配置
     */
    private ToastControl mToastControl;

    private TitleBarViewControl mTitleBarViewControl;

    private SwipeBackControl swipeBackControl;

    public SwipeBackControl getSwipeBackControl() {
        return swipeBackControl;
    }

    public void setSwipeBackControl(SwipeBackControl swipeBackControl) {
        this.swipeBackControl = swipeBackControl;
    }

    public TitleBarViewControl getTitleBarViewControl() {
        return mTitleBarViewControl;
    }

    public void setTitleBarViewControl(TitleBarViewControl mTitleBarViewControl) {
        this.mTitleBarViewControl = mTitleBarViewControl;
    }

    public ToastControl getToastControl() {
        return mToastControl;
    }
}