package com.tourcoo.xiantao.core.module;

import android.os.Bundle;
import android.view.View;

import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleFragment;
import com.tourcoo.xiantao.core.frame.util.LoadingDialog;
import com.tourcoo.xiantao.core.frame.widget.IosProgressDialog;
import com.tourcoo.xiantao.core.frame.widget.LoadingIosDialog;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;

/**
 * @author :zhoujian
 * @description : zj
 * @company :途酷科技
 * @date 2019年03月06日上午 10:12
 * @Email: 971613168@qq.com
 */
public class HomeFragment extends BaseTitleFragment {
    private LoadingIosDialog mLoadingIosDialog;
    private LoadingDialog mLoadingDialog;
    private IosProgressDialog mIosProgressDialog;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initDialog();
        mContentView.findViewById(R.id.btnTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查版本号
                Beta.checkUpgrade();
            }
        });
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {

    }


    private void initDialog() {
        mIosProgressDialog = new IosProgressDialog(mContext);
    }


}
