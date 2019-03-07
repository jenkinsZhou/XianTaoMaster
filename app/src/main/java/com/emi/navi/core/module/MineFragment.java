package com.emi.navi.core.module;

import android.os.Bundle;

import com.emi.navi.R;
import com.emi.navi.core.frame.base.fragment.BaseTitleFragment;
import com.emi.navi.widget.core.view.titlebar.TitleBarView;

/**
 * @author :zhoujian
 * @description : zj
 * @company :翼迈科技
 * @date 2019年03月06日上午 10:11
 * @Email: 971613168@qq.com
 */
public class MineFragment extends BaseTitleFragment {
    @Override
    public int getContentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("我的");
    }

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
