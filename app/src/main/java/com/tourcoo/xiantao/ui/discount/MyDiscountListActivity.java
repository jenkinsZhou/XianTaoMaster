package com.tourcoo.xiantao.ui.discount;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @author :JenkinsZhou
 * @description :我的优惠券
 * @company :途酷科技
 * @date 2019年05月09日16:37
 * @Email: 971613168@qq.com
 */
public class MyDiscountListActivity extends BaseTourCooTitleActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private String[] titles = new String[]{"未使用", "已使用", "已过期"};
    private List<Fragment> fragmentList = new ArrayList<>();
    public static final String EXTRA_CURRENT_TAB_INDEX = "EXTRA_CURRENT_TAB_INDEX";
    private int currentTabIndex;
    public DiscountNotUseListFragment mNotUseListFragment;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_discount;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTabLayout = findViewById(R.id.mTabLayout);
        mViewPager = findViewById(R.id.mViewPager);
        initTabTitle();
        currentTabIndex = getIntent().getIntExtra(EXTRA_CURRENT_TAB_INDEX, -1);
    }


    @Override
    public void loadData() {
        super.loadData();
        mNotUseListFragment = DiscountNotUseListFragment.newInstance();
        fragmentList.add(mNotUseListFragment);
        fragmentList.add(DiscountHasUseListFragment.newInstance());
        fragmentList.add(DiscountExpiredListFragment.newInstance());
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        initTabTitle();
        TourCooLogUtil.i(TAG, "value索引:" + currentTabIndex);
        setCurrentTabByPosition(currentTabIndex);
    }

    private void initTabTitle() {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setText(titles[i]);
            }
        }
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("我的卡券");
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
        mViewPager.setCurrentItem(position);
    }



}
