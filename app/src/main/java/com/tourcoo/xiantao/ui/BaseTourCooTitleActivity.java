package com.tourcoo.xiantao.ui;

import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TextView;

import com.tourcoo.xiantao.core.frame.base.activity.BaseTitleActivity;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.widget.core.progress.EmiProgressDialog;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;


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


}
