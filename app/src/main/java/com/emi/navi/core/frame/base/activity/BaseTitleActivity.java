package com.emi.navi.core.frame.base.activity;

import android.os.Bundle;

import com.emi.navi.core.frame.delegate.TitleBarDelegate;
import com.emi.navi.core.frame.interfaces.ITitleView;
import com.emi.navi.widget.core.view.titlebar.TitleBarView;

/**
 * @author :zhoujian
 * @description : 包含TitleBarView的基类Activity
 * @company :翼迈科技
 * @date 2019年03月04日下午 05:27
 * @Email: 971613168@qq.com
 */
public abstract class BaseTitleActivity extends BaseActivity implements ITitleView {

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
}
