package com.emi.navi.core.module;

import android.os.Bundle;

import com.aries.ui.view.tab.CommonTabLayout;
import com.emi.navi.R;
import com.emi.navi.core.frame.base.activity.BaseMainActivity;
import com.emi.navi.core.frame.entity.TabEntity;
import com.emi.navi.core.log.NaViLogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zhoujian
 * @description : zj
 * @company :翼迈科技
 * @date 2019年03月06日上午 10:07
 * @Email: 971613168@qq.com
 */
public class MainTabActivity extends BaseMainActivity {

    private CommonTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    public List<TabEntity> getTabList() {
        ArrayList<TabEntity> tabEntities = new ArrayList<>();
        tabEntities.add(new TabEntity("主页", R.drawable.ic_home_normal, R.drawable.ic_home_selected, HomeFragment.newInstance()));
        tabEntities.add(new TabEntity("活动", R.drawable.ic_home_normal, R.drawable.ic_home_selected, HomeFragment.newInstance()));
        tabEntities.add(new TabEntity("我的", R.drawable.ic_home_normal, R.drawable.ic_home_selected, MineFragment.newInstance()));
        return tabEntities;
    }

    @Override
    public void setTabLayout(CommonTabLayout tabLayout) {
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
       /* CheckVersionHelper.with(this)
                .checkVersion(false);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NaViLogUtil.i(TAG, "onDestroy");
    }
}
