package com.tourcoo.xiantao.ui;

import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

import com.tourcoo.xiantao.core.frame.base.activity.BaseRefreshLoadActivity;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年03月26日19:30
 * @Email: 971613168@qq.com
 */
public abstract class BaseTourCooRefreshLoadActivity<T> extends BaseRefreshLoadActivity<T> {

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        TextView textView = titleBar.getTextView(Gravity.CENTER | Gravity.TOP);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }
}
