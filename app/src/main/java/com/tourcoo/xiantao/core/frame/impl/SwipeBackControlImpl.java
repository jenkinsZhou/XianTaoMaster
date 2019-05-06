package com.tourcoo.xiantao.core.frame.impl;

import android.app.Activity;

import com.tourcoo.xiantao.XianTaoApplication;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.SwipeBackControl;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * @author :zhoujian
 * @description :滑动返回具体实现
 * @company :途酷科技
 * @date 2019年 03月 05日 21时14分
 * @Email: 971613168@qq.com
 */
public class SwipeBackControlImpl implements SwipeBackControl {

    /**
     * 设置当前Activity是否支持滑动返回(用于控制是否添加一层{@link BGASwipeBackHelper})
     * 返回为true {@link #setSwipeBack(Activity, BGASwipeBackHelper)}才有设置的意义
     *
     * @param activity
     * @return
     */
    @Override
    public boolean isSwipeBackEnable(Activity activity) {
        return true;
    }


    /**
     * 设置Activity 全局滑动属性--包括三方库
     *
     * @param activity
     * @param bgaSwipeBackHelper BGASwipeBackHelper 控制详见
     */

    @Override
    public void setSwipeBack(Activity activity, BGASwipeBackHelper bgaSwipeBackHelper) {
        //以下为默认设置
        //需设置activity window背景为透明避免滑动过程中漏出背景也可减少背景层级降低过度绘制
        activity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        bgaSwipeBackHelper.setSwipeBackEnable(false)
                .setShadowResId(R.color.colorSwipeBackBackground)
                //底部导航条是否悬浮在内容上设置过NavigationViewHelper可以不用设置该属性
                .setIsNavigationBarOverlap(XianTaoApplication.isControlNavigation());
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
}
