package com.tourcoo.xiantao.core.module;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.core.frame.base.activity.BaseRefreshLoadActivity;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;

/**
 * @author :zhoujian
 * @description : zj
 * @company :途酷科技
 * @date 2019年03月12日下午 05:25
 * @Email: 971613168@qq.com
 */
public class TestRefreshActivity extends BaseRefreshLoadActivity<String> {
    @Override
    public int getContentLayout() {
        return 0;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public BaseQuickAdapter<String, BaseViewHolder> getAdapter() {
        return null;
    }

    @Override
    public void loadData(int page) {

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {

    }
}
