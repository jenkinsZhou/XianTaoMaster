package com.tourcoo.xiantao.ui.goods;

import android.os.Bundle;
import android.text.TextUtils;

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

import static com.tourcoo.xiantao.constant.GoodsConstant.TYPE_GOODS_NORMAL;
import static com.tourcoo.xiantao.constant.GoodsConstant.TYPE_GOODS_PRICE;
import static com.tourcoo.xiantao.constant.GoodsConstant.TYPE_GOODS_SALES;
import static com.tourcoo.xiantao.ui.goods.ClassifyGoodsFragment.EXTRA_CATEGORY_ID;

/**
 * @author :JenkinsZhou
 * @description :商品分类列表页面(包含三个fragment)
 * @company :途酷科技
 * @date 2019年04月27日12:37
 * @Email: 971613168@qq.com
 */
public class GoodsCategoryListActivity extends BaseTourCooTitleActivity implements ViewPager.OnPageChangeListener {
    private String[] titles = new String[]{"销量", "新品", "价格"};
    private TabLayout goodsTabLayout;
    private int currentSelectPosition;
    private ViewPager goodsViewPager;
    private TitleBarView titleBarView;
    public static final String EXTRA_TITLE_NAME = "EXTRA_TITLE_NAME";
    public static final String EXTRA_KEY_WORD = "EXTRA_KEY_WORD";
    public String keyword;
    /**
     * 商品分类的id
     */
    public int categoryId;

    @Override
    public int getContentLayout() {
        return R.layout.activity_goods_category_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        categoryId = getIntent().getIntExtra(EXTRA_CATEGORY_ID, -1);
        keyword = getIntent().getStringExtra(EXTRA_KEY_WORD);
        String titleName = getIntent().getStringExtra(EXTRA_TITLE_NAME);
        showTitle(titleName);
        TourCooLogUtil.i(TAG, TAG + "商品id:" + categoryId);
        goodsViewPager = findViewById(R.id.goodsViewPager);
        goodsViewPager.setOffscreenPageLimit(3);
        initTabTitle();
    }


    private void initTabTitle() {
        goodsTabLayout = findViewById(R.id.goodsTabLayout);
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = goodsTabLayout.getTabAt(i);
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
        currentSelectPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBarView = titleBar;
    }


    @Override
    public void loadData() {
        super.loadData();
        List<Fragment> fragmentList = new ArrayList<>();
        //销量
        GoodsCategorySaleFragment categoryFragment1 = GoodsCategorySaleFragment.newInstance();
        //新品
        GoodsCategoryNormalFragment categoryFragment2 = GoodsCategoryNormalFragment.newInstance();
        //价格
        GoodsCategoryPriceFragment categoryFragment3 = GoodsCategoryPriceFragment.newInstance();
        fragmentList.add(categoryFragment1);
        fragmentList.add(categoryFragment2);
        fragmentList.add(categoryFragment3);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        goodsViewPager.addOnPageChangeListener(this);
        goodsViewPager.setAdapter(pagerAdapter);
        goodsTabLayout.setupWithViewPager(goodsViewPager);
        initTabTitle();
        goodsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentSelectPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void showTitle(String titleName) {
        if (TextUtils.isEmpty(titleName)) {
            return;
        }
        titleBarView.setTitleMainText(titleName);
    }
}
