package com.tourcoo.xiantao.core.frame.base.fragment;

import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月17日14:20
 * @Email: 971613168@qq.com
 */
public abstract class BaseTitleTourCoolFragment extends BaseTitleFragment {

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        mTitleBar = titleBar;
        TextView textView = titleBar.getTextView(Gravity.CENTER | Gravity.TOP);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }
}
