package com.tourcoo.xiantao.core.frame.base.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.tourcoo.xiantao.core.frame.delegate.TitleBarDelegate;
import com.tourcoo.xiantao.core.frame.interfaces.ITitleView;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;

/**
 * @author :zhoujian
 * @description :设置有TitleBar及下拉刷新基类Fragment
 * @company :途酷科技
 * @date 2019年 03月 05日 20时50分
 * @Email: 971613168@qq.com
 */
public abstract class BaseTitleViewRefreshFragment<T> extends BaseRefreshFragment<T> implements ITitleView {

    protected TitleBarDelegate mFastTitleDelegate;
    protected TitleBarView mTitleBar;

    @Override
    public void beforeSetTitleBar(TitleBarView titleBar) {
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
        mFastTitleDelegate = new TitleBarDelegate(mContentView, this, this.getClass());
        mTitleBar = mFastTitleDelegate.mTitleBar;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        mTitleBar = titleBar;
        TextView textView = titleBar.getTextView(Gravity.CENTER | Gravity.TOP);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }
}
