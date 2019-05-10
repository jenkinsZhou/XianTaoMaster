package com.tourcoo.xiantao.ui.discount;

import android.os.Bundle;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.DiscountAdapter;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseRefreshFragment;
import com.tourcoo.xiantao.entity.discount.DiscountEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :已失效的优惠券
 * @company :途酷科技
 * @date 2019年05月10日9:31
 * @Email: 971613168@qq.com
 */
public class DiscountExpiredListFragment extends BaseRefreshFragment<DiscountEntity> {

    private List<DiscountEntity> mDiscountEntityList = new ArrayList<>();

    private DiscountAdapter mDiscountAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_my_discount_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDefaultPage = 1;
        mDefaultPageSize = 10;
    }

    @Override
    public DiscountAdapter getAdapter() {
        mDiscountAdapter = new DiscountAdapter();
        return mDiscountAdapter;
    }

    @Override
    public void loadData() {
        super.loadData();
        requsetData();
        mStatusManager.showSuccessLayout();
    }

    @Override
    public void loadData(int page) {
    }


    public static DiscountExpiredListFragment newInstance() {
        Bundle args = new Bundle();
        DiscountExpiredListFragment fragment = new DiscountExpiredListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void requsetData() {
        int size = 10;
        for (int i = 0; i < size; i++) {
            mDiscountEntityList.add(new DiscountEntity());
        }
        mDiscountAdapter.setNewData(mDiscountEntityList);
    }
}
