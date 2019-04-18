package com.tourcoo.xiantao.core.module;

import android.os.Bundle;

import com.aries.ui.view.tab.CommonTabLayout;
import com.aries.ui.view.tab.listener.OnTabSelectListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.activity.BaseMainActivity;
import com.tourcoo.xiantao.core.frame.delegate.MainTabDelegate;
import com.tourcoo.xiantao.core.frame.entity.TabEntity;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.event.TabChangeEvent;
import com.tourcoo.xiantao.helper.ShoppingCarInstance;
import com.tourcoo.xiantao.ui.ShoppingCarFragmentTest;
import com.tourcoo.xiantao.ui.mine.account.MineFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :zhoujian
 * @description : 首页
 * @company :途酷科技
 * @date 2019年03月06日上午 10:07
 * @Email: 971613168@qq.com
 */
public class MainTabActivity extends BaseMainActivity {
    private TabChangeEvent mTabChangeEvent;
    public CommonTabLayout mTabLayout;

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
        tabEntities.add(new TabEntity("分类", R.mipmap.tab_classification_normal, R.mipmap.tab_classification_selected, ShoppingCarFragmentTest.newInstance()));
        tabEntities.add(new TabEntity("购物车", R.mipmap.tab_shopping_cart_normal, R.mipmap.tab_shopping_cart_selected, ShoppingCarFragmentTest.newInstance()));
        tabEntities.add(new TabEntity("个人中心", R.mipmap.tab_personal_center_normal, R.mipmap.tab_personal_center_selected, MineFragment.newInstance()));
        return tabEntities;
    }

    @Override
    public void setTabLayout(CommonTabLayout tabLayout) {
        mTabLayout = tabLayout;
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        super.beforeInitView(savedInstanceState);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTabChangeEvent = new TabChangeEvent(0);
        initTabChangeListener();
       /* CheckVersionHelper.with(this)
                .checkVersion(false);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TourCooLogUtil.i(TAG, "onDestroy");
        ShoppingCarInstance.getInstance().clearShoppingCar();
    }

    /**
     * 初始化tab切换监听
     */
    private void initTabChangeListener() {
        if (mTabLayout != null) {
            mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    TourCooLogUtil.i(TAG, "当前位置:" + position);
                    mTabChangeEvent.currentPosition = position;
                    EventBus.getDefault().postSticky(mTabChangeEvent);
                }

                @Override
                public void onTabReselect(int position) {
                }
            });
        }
    }


    public MainTabDelegate getTabDelegate() {
        return mMainTabDelegate;
    }

}
