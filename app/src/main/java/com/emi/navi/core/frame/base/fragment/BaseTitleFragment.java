package com.emi.navi.core.frame.base.fragment;

import android.os.Bundle;

import com.emi.navi.core.frame.base.fragment.BaseFragment;
import com.emi.navi.core.frame.delegate.TitleBarDelegate;
import com.emi.navi.core.frame.interfaces.ITitleView;
import com.emi.navi.widget.core.view.titlebar.TitleBarView;

/**
 * @author :zhoujian
 * @description :设置有TitleBar的BaseFragment
 * @company :翼迈科技
 * @date 2019年 03月 05日 20时34分
 * @Email: 971613168@qq.com
 */
public abstract class BaseTitleFragment extends BaseFragment implements ITitleView {

    protected TitleBarDelegate mFastTitleDelegate;
    protected TitleBarView mTitleBar;

    @Override
    public void beforeSetTitleBar(TitleBarView titleBar) {
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
        mFastTitleDelegate = new TitleBarDelegate(mContentView, this,this.getClass());
        mTitleBar = mFastTitleDelegate.mTitleBar;
    }
}
