package com.tourcoo.xiantao.ui.discount;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.DiscountSelectAdapter;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.discount.DiscountInfo;
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
public class DisCountSelectListActivity extends BaseTourCooRefreshLoadActivity<DiscountInfo> {

    private List<DiscountInfo> mDiscountInfoList = new ArrayList<>();

    private DiscountSelectAdapter mDiscountAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_discount_select_list;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("卡券选择");
        titleBar.setRightText("确认");
        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DiscountInfo> discountInfoList = mDiscountAdapter.getData();
                int count = discountInfoList.size();
                int select = 0;
                for (DiscountInfo discountInfo : discountInfoList) {
                    if (discountInfo.isSelect()) {
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
        DiscountInfo discountInfo;
        for (int i = 0; i < size; i++) {
            discountInfo = new DiscountInfo();
            discountInfo.setClickEnable(true);
        /*    if ((i & 1) == 1) {
                discountEntity.setSelect(true);
                discountEntity.setClickEnable(true);
            } else {
                discountEntity.setClickEnable(false);
                discountEntity.setSelect(false);
            }*/
            mDiscountInfoList.add(discountInfo);
            discountInfo.setRuleId(i);
        }
        DiscountInfo discountInfo1 = new DiscountInfo();
        discountInfo1.setRuleId(1);
        discountInfo1.setClickEnable(true);
        DiscountInfo discountInfo2 = new DiscountInfo();
        discountInfo2.setRuleId(1);
        discountInfo2.setClickEnable(true);
        discountInfo2.setSelect(true);
        mDiscountInfoList.add(discountInfo1);
        mDiscountInfoList.add(discountInfo2);
        mDiscountAdapter.setNewData(mDiscountInfoList);
    }


    private void initClickListener() {
        mDiscountAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<DiscountInfo> discountInfoList = mDiscountAdapter.getData();
                DiscountInfo currentDiscount = discountInfoList.get(position);
                int selectRuleId = -1;
                if (currentDiscount.isSelect()) {
                    currentDiscount.setSelect(false);
                } else {
                    selectRuleId = currentDiscount.getRuleId();
                    currentDiscount.setSelect(true);
                }
                boolean noSelect = !checkSelect(discountInfoList);
                //如果没有任何优惠券被选中 则默认都可以勾选
                if (noSelect) {
                    setAllDiscountClickable(discountInfoList);
                } else {
                    //说明此时已经有优惠券被选中,则其他优惠券将根据id判断是否可以点击
                    setDiscountClickableByRuleId(discountInfoList, selectRuleId);
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
    private boolean checkSelect(List<DiscountInfo> entityList) {
        for (DiscountInfo discountInfo : entityList) {
            if (discountInfo.isSelect()) {
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
    private void setAllDiscountClickable(List<DiscountInfo> entityList) {
        for (DiscountInfo discountInfo : entityList) {
            discountInfo.setClickEnable(true);
        }
    }

    /**
     * 根据规则id判断是否可以点击
     *
     * @param entityList
     * @param ruleId
     */
    private void setDiscountClickableByRuleId(List<DiscountInfo> entityList, int ruleId) {
        for (DiscountInfo discountInfo : entityList) {
            if (discountInfo.getRuleId() == ruleId) {
                discountInfo.setClickEnable(true);
            } else {
                discountInfo.setClickEnable(false);
            }
            //只要是选中状态 肯定可以点击
            if (discountInfo.isSelect()) {
                discountInfo.setClickEnable(true);
            }
        }
    }


}
