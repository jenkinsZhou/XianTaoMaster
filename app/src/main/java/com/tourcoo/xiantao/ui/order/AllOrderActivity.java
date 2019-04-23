package com.tourcoo.xiantao.ui.order;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.ui.account.MineFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author :JenkinsZhou
 * @description :全部订单
 * @company :途酷科技
 * @date 2019年04月18日15:32
 * @Email: 971613168@qq.com
 */
public class AllOrderActivity extends BaseTourCooTitleActivity implements ViewPager.OnPageChangeListener {
    private ViewPager orderViewPager;
    private TabLayout orderTabLayout;
    private String[] titles = new String[]{"全部", "待付款", "待发货", "待收货", "待评价"};

    @Override
    public int getContentLayout() {
        return R.layout.activity_order_all;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        orderTabLayout = findViewById(R.id.orderTabLayout);
        orderViewPager = findViewById(R.id.orderViewPager);
        initTabTitle();
    }



    @Override
    public void loadData() {
        super.loadData();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(MineFragment.newInstance());
        fragmentList.add(MineFragment.newInstance());
        fragmentList.add(MineFragment.newInstance());
        fragmentList.add(MineFragment.newInstance());
        fragmentList.add(MineFragment.newInstance());
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        orderViewPager.addOnPageChangeListener(this);
        orderViewPager.setAdapter(pagerAdapter);
        orderTabLayout.setupWithViewPager(orderViewPager);
        initTabTitle();
    }

    private void initTabTitle() {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = orderTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setText(titles[i]);
            }
        }
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
}
