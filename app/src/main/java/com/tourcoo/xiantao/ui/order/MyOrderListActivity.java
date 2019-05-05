package com.tourcoo.xiantao.ui.order;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.event.RefreshEvent;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.ui.account.MineFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_ALL;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_COMMENT;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_PAY;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_RECIEVE;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_SEND;

/**
 * @author :JenkinsZhou
 * @description :我的订单
 * @company :途酷科技
 * @date 2019年04月18日15:32
 * @Email: 971613168@qq.com
 */
public class MyOrderListActivity extends BaseTourCooTitleActivity implements ViewPager.OnPageChangeListener {
    private ViewPager orderViewPager;
    private TabLayout orderTabLayout;
    private String[] titles = new String[]{"全部", "待付款", "待发货", "待收货", "待评价"};
    private List<Fragment> fragmentList = new ArrayList<>();
    public static final String EXTRA_CURRENT_TAB_INDEX = "EXTRA_CURRENT_TAB_INDEX";
    private int currentTabIndex;

    @Override
    public int getContentLayout() {
        return R.layout.activity_order_all;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        orderTabLayout = findViewById(R.id.orderTabLayout);
        orderViewPager = findViewById(R.id.orderViewPager);
        initTabTitle();
        currentTabIndex = getIntent().getIntExtra(EXTRA_CURRENT_TAB_INDEX, -1);
    }


    @Override
    public void loadData() {
        super.loadData();
        fragmentList.add(OrderListFragment.newInstance(ORDER_STATUS_ALL));
        fragmentList.add(OrderListFragment.newInstance(ORDER_STATUS_WAIT_PAY));
        fragmentList.add(OrderListFragment.newInstance(ORDER_STATUS_WAIT_SEND));
        fragmentList.add(OrderListFragment.newInstance(ORDER_STATUS_WAIT_RECIEVE));
        fragmentList.add(OrderListFragment.newInstance(ORDER_STATUS_WAIT_COMMENT));
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        orderViewPager.addOnPageChangeListener(this);
        orderViewPager.setAdapter(pagerAdapter);
        orderViewPager.setOffscreenPageLimit(5);
        orderTabLayout.setupWithViewPager(orderViewPager);
        initTabTitle();
        TourCooLogUtil.i(TAG, "value索引:" + currentTabIndex);
        setCurrentTabByPosition(currentTabIndex);
    }

    private void initTabTitle() {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = orderTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setText(titles[i]);
            }
        }
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("订单列表");
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 设置当前tab
     *
     * @param position
     */
    public void setCurrentTabByPosition(int position) {
        if (position < 0 || position >= fragmentList.size()) {
            return;
        }
        orderViewPager.setCurrentItem(position);
    }


    @Override
    public void finish() {
        EventBus.getDefault().postSticky(new RefreshEvent());
        super.finish();
    }


}
