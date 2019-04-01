package com.tourcoo.xiantao.core.frame.base;

import android.app.Activity;

import com.tourcoo.xiantao.core.widget.core.util.ResourceUtil;

import org.simple.eventbus.EventBus;

/**
 * @author :zhoujian
 * @description :
 * @company :翼迈科技
 * @date 2019年 03月 16日 00时34分
 * @Email: 971613168@qq.com
 */
public class BaseHelper {

    protected Activity mContext;
    protected ResourceUtil mResourceUtil;

    public BaseHelper(Activity context) {
        mContext = context;
        mResourceUtil = new ResourceUtil(mContext);
    }

    /**
     * Activity 关闭onDestroy调用
     */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}
