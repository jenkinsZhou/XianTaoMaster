package com.emi.navi.core.module;

import android.os.Bundle;

import com.emi.navi.R;
import com.emi.navi.core.frame.base.fragment.BaseTitleFragment;
import com.emi.navi.widget.core.view.titlebar.TitleBarView;

/**
 * @author :zhoujian
 * @description : zj
 * @company :翼迈科技
 * @date 2019年03月06日上午 10:12
 * @Email: 971613168@qq.com
 */
public class HomeFragment extends BaseTitleFragment {

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

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {

    }
}
