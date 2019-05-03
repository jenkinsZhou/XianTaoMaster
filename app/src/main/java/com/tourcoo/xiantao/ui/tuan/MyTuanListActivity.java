package com.tourcoo.xiantao.ui.tuan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.ui.order.OrderListFragment;

import java.util.ArrayList;
import java.util.List;

import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_ALL;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_COMMENT;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_PAY;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_RECIEVE;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_SEND;
import static com.tourcoo.xiantao.constant.TuanConstant.TUAN_STATUS_JOIN;
import static com.tourcoo.xiantao.constant.TuanConstant.TUAN_STATUS_MINE;

/**
 * 我的拼团记录
 */
public class MyTuanListActivity extends BaseTourCooTitleActivity {
    private ViewPager tuanViewPager;
    private TabLayout tuanTabLayout;
    private String[] titles = new String[]{"我发起的", "我参与的"};
    private List<Fragment> fragmentList;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_tuan_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitleBar.setTitleMainText("拼团记录");
        tuanTabLayout = findViewById(R.id.tuanTabLayout);
        tuanViewPager = findViewById(R.id.tuanViewPager);
        initTabTitle();
    }


    @Override
    public void loadData() {
        super.loadData();
        fragmentList = new ArrayList<>();
        fragmentList.add(MyTuanListFragment.newInstance(TUAN_STATUS_MINE));
        fragmentList.add(MyTuanListFragment.newInstance(TUAN_STATUS_JOIN));
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        tuanViewPager.setAdapter(pagerAdapter);
        tuanViewPager.setOffscreenPageLimit(2);
        tuanTabLayout.setupWithViewPager(tuanViewPager);
        initTabTitle();
    }

    private void initTabTitle() {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tuanTabLayout.getTabAt(i);
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
    protected void onDestroy() {
        super.onDestroy();
        if (fragmentList != null) {
            fragmentList.clear();
            fragmentList = null;
        }
    }
}
