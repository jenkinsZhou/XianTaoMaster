package com.tourcoo.xiantao.core.frame.base.fragment;

import android.os.Bundle;

import com.tourcoo.xiantao.core.frame.delegate.TitleBarDelegate;
import com.tourcoo.xiantao.core.frame.interfaces.ITitleView;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * @author :zhoujian
 * @description :设置有TitleBar的BaseFragment
 * @company :途酷科技
 * @date 2019年 03月 05日 20时34分
 * @Email: 971613168@qq.com
 */
public abstract class BaseTitleFragment extends BaseFragment implements ITitleView {

    protected TitleBarDelegate mFastTitleDelegate;
    protected TitleBarView mTitleBar;
    protected StatusLayoutManager layoutManager;



    @Override
    public void beforeSetTitleBar(TitleBarView titleBar) {
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
        mFastTitleDelegate = new TitleBarDelegate(mContentView, this, this.getClass());
        mTitleBar = mFastTitleDelegate.mTitleBar;
    }


    /**
     * 确认弹窗
     *
     * @param builder
     */
    protected void showConfirmDialog(ConfirmDialog.Builder builder) {
        boolean enable = mContext != null && !mContext.isFinishing() && builder != null;
        if (enable) {
            builder.create().show();
        }
    }
}
