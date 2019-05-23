package com.tourcoo.xiantao.ui;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.activity.BaseTitleActivity;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.widget.core.progress.EmiProgressDialog;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.permission.PermissionManager;


import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * @author :zhoujian
 * @description :带标题栏的基类activity
 * @company :翼迈科技
 * @date 2019年 03月 16日 12时04分
 * @Email: 971613168@qq.com
 */
public abstract class BaseTourCooTitleActivity extends BaseTitleActivity {
    protected EmiProgressDialog dialog;
    protected StatusLayoutManager mStatusLayoutManager;
    protected IMultiStatusView multiStatusView;


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        TextView textView = titleBar.getTextView(Gravity.CENTER | Gravity.TOP);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    /**
     * 判断是否有相关权限
     *
     * @return
     */
    protected boolean checkPermission() {
        return PermissionManager.checkAllNeedPermission(this);
    }


    protected void showErrorLayoutMsg(String errorInfo) {
        if (mStatusLayoutManager == null || mStatusLayoutManager.getErrorLayout() == null) {
            return;
        }
        View errorView = mStatusLayoutManager.getErrorLayout();
        TextView tvErrorContent = errorView.findViewById(R.id.tvErrorContent);
        if (tvErrorContent == null || TextUtils.isEmpty(errorInfo)) {
            return;
        }
        tvErrorContent.setText(errorInfo);
    }

    protected void showErrorLayout() {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager.showErrorLayout();
        }
    }

    protected void showEmtyLayout() {
        if (mStatusLayoutManager != null&&mStatusLayoutManager.getEmptyLayout() != null) {
            mStatusLayoutManager.showEmptyLayout();
        }
    }

    protected void showErrorLayout(String msg) {
        if (mStatusLayoutManager != null && mStatusLayoutManager.getErrorLayout() != null) {
            mStatusLayoutManager.showErrorLayout();
            if (TextUtils.isEmpty(msg)) {
                return;
            }
            TextView tvErrorInfo = mStatusLayoutManager.getErrorLayout().findViewById(R.id.tvErrorContent);
            if (tvErrorInfo != null) {
                tvErrorInfo.setText(msg);
            }
        }
    }

}
