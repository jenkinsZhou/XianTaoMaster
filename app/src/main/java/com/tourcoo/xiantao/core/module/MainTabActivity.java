package com.tourcoo.xiantao.core.module;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.aries.ui.view.tab.CommonTabLayout;
import com.aries.ui.view.tab.widget.MsgView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.activity.BaseMainActivity;
import com.tourcoo.xiantao.core.frame.entity.TabEntity;
import com.tourcoo.xiantao.core.log.TourcoolLogUtil;
import com.tourcoo.xiantao.core.widget.core.util.SizeUtil;
import com.tourcoo.xiantao.core.widget.core.view.layout.CollapsingTitleBarLayout;
import com.tourcoo.xiantao.ui.mine.account.MineFragment;
import com.tourcoo.xiantao.ui.repair.FaultRepairFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zhoujian
 * @description : zj
 * @company :途酷科技
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
        tabEntities.add(new TabEntity("首页", R.mipmap.tab_home_normal, R.mipmap.tab_home_selected, HomeFragment.newInstance()));
        tabEntities.add(new TabEntity("分类", R.mipmap.tab_classification_normal, R.mipmap.tab_classification_selected, HomeFragment.newInstance()));
        tabEntities.add(new TabEntity("购物车", R.mipmap.tab_shopping_cart_normal, R.mipmap.tab_shopping_cart_selected, FaultRepairFragment.newInstance()));
        tabEntities.add(new TabEntity("个人中心", R.mipmap.tab_personal_center_normal, R.mipmap.tab_personal_center_selected, MineFragment.newInstance()));
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
        TourcoolLogUtil.i(TAG, "onDestroy");
    }


}
