package com.tourcoo.xiantao.ui.discount;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.DiscountSelectAdapter;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.discount.DiscountEntity;
import com.tourcoo.xiantao.ui.BaseTourCooRefreshLoadActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :选择优惠券页面
 * @company :途酷科技
 * @date 2019年05月10日17:41
 * @Email: 971613168@qq.com
 */
public class DisCountSelectListActivity extends BaseTourCooRefreshLoadActivity<DiscountEntity> {

    private List<DiscountEntity> mDiscountEntityList = new ArrayList<>();

    private DiscountSelectAdapter mDiscountAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_discount_select_list;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("卡券选择");
        titleBar.setRightText("结果");
        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DiscountEntity> discountEntityList = mDiscountAdapter.getData();
                int count = discountEntityList.size();
                int select = 0;
                for (DiscountEntity discountEntity : discountEntityList) {
                    if (discountEntity.isSelect()) {
                        select++;
                    }
                }
                ToastUtil.showSuccess("总共有" + count + "张优惠券，当前选中了" + select + "张");
            }
        });
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDefaultPage = 1;
        mDefaultPageSize = 10;
    }

    @Override
    public DiscountSelectAdapter getAdapter() {
        mDiscountAdapter = new DiscountSelectAdapter();
        return mDiscountAdapter;
    }

    @Override
    public void loadData() {
        super.loadData();
        initClickListener();
        requestData();
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


    private void requestData() {
        int size = 10;
        DiscountEntity discountEntity;
        for (int i = 0; i < size; i++) {
            discountEntity = new DiscountEntity();
            discountEntity.setClickEnable(true);
        /*    if ((i & 1) == 1) {
                discountEntity.setSelect(true);
                discountEntity.setClickEnable(true);
            } else {
                discountEntity.setClickEnable(false);
                discountEntity.setSelect(false);
            }*/
            mDiscountEntityList.add(discountEntity);
            discountEntity.setRuleId(i);
        }
        DiscountEntity discountEntity1 = new DiscountEntity();
        discountEntity1.setRuleId(1);
        discountEntity1.setClickEnable(true);
        DiscountEntity discountEntity2 = new DiscountEntity();
        discountEntity2.setRuleId(1);
        discountEntity2.setClickEnable(true);
        discountEntity2.setSelect(true);
        mDiscountEntityList.add(discountEntity1);
        mDiscountEntityList.add(discountEntity2);
        mDiscountAdapter.setNewData(mDiscountEntityList);
    }


    private void initClickListener() {
        mDiscountAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<DiscountEntity> discountEntityList = mDiscountAdapter.getData();
                DiscountEntity currentDiscount = discountEntityList.get(position);
                int selectRuleId = -1;
                if (currentDiscount.isSelect()) {
                    currentDiscount.setSelect(false);
                } else {
                    selectRuleId = currentDiscount.getRuleId();
                    currentDiscount.setSelect(true);
                }
                boolean noSelect = !checkSelect(discountEntityList);
                //如果没有任何优惠券被选中 则默认都可以勾选
                if (noSelect) {
                    setAllDiscountClickable(discountEntityList);
                } else {
                    //说明此时已经有优惠券被选中,则其他优惠券将根据id判断是否可以点击
                    setDiscountClickableByRuleId(discountEntityList, selectRuleId);
                }
                mDiscountAdapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * 判断是否有选中的优惠券
     *
     * @param entityList
     * @return
     */
    private boolean checkSelect(List<DiscountEntity> entityList) {
        for (DiscountEntity discountEntity : entityList) {
            if (discountEntity.isSelect()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将全部优惠券全部置为可以点击
     *
     * @param entityList
     */
    private void setAllDiscountClickable(List<DiscountEntity> entityList) {
        for (DiscountEntity discountEntity : entityList) {
            discountEntity.setClickEnable(true);
        }
    }

    /**
     * 根据规则id判断是否可以点击
     *
     * @param entityList
     * @param ruleId
     */
    private void setDiscountClickableByRuleId(List<DiscountEntity> entityList, int ruleId) {
        for (DiscountEntity discountEntity : entityList) {
            if (discountEntity.getRuleId() == ruleId) {
                discountEntity.setClickEnable(true);
            } else {
                discountEntity.setClickEnable(false);
            }
            //只要是选中状态 肯定可以点击
            if (discountEntity.isSelect()) {
                discountEntity.setClickEnable(true);
            }
        }
    }


}
