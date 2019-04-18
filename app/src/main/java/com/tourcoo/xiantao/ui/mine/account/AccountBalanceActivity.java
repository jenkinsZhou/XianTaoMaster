package com.tourcoo.xiantao.ui.mine.account;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.RechargeAmountAdapter;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.ResourceUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.RechargeEntity;
import com.tourcoo.xiantao.ui.BaseTourcooTitleActivity;
import com.tourcoo.xiantao.ui.mine.recharge.RechargeDetailActivity;
import com.tourcoo.xiantao.ui.mine.recharge.RechargeRuleActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年03月28日14:52
 * @Email: 971613168@qq.com
 */
public class AccountBalanceActivity extends BaseTourcooTitleActivity implements View.OnClickListener {
    private RecyclerView rechargeRecyclerView;
    private RechargeAmountAdapter mRechargeAmountAdapter;
    private List<RechargeEntity> mRechargeEntityList = new ArrayList<>();

    @Override
    public int getContentLayout() {
        return R.layout.activity_account_balance;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rechargeRecyclerView = findViewById(R.id.rechargeRecyclerView);
        rechargeRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        findViewById(R.id.tvRechargeRule).setOnClickListener(this);
    }

    @Override
    public void loadData() {
        super.loadData();
        initData();
    }

    private void initData() {
        mRechargeEntityList.add(new RechargeEntity(100, true));
        mRechargeEntityList.add(new RechargeEntity(200));
        mRechargeEntityList.add(new RechargeEntity(300));
        mRechargeEntityList.add(new RechargeEntity(500));
        mRechargeEntityList.add(new RechargeEntity(800));
        mRechargeEntityList.add(new RechargeEntity(1000));
        mRechargeAmountAdapter = new RechargeAmountAdapter(mRechargeEntityList);
        mRechargeAmountAdapter.bindToRecyclerView(rechargeRecyclerView);
        mRechargeAmountAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                setSelect(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 设置选中属性
     *
     * @param position
     */
    private void setSelect(int position) {
        if (position >= mRechargeEntityList.size()) {
            return;
        }
        RechargeEntity rechargeEntity;
        for (int i = 0; i < mRechargeEntityList.size(); i++) {
            rechargeEntity = mRechargeEntityList.get(i);
            if (i == position) {
                rechargeEntity.selected = true;
            } else {
                rechargeEntity.selected = false;
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvRechargeRule:
                TourCoolUtil.startActivity(mContext, RechargeRuleActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("账户余额");
        titleBar.setRightText("充值明细").
                setRightTextColor(ResourceUtil.getColor(R.color.colorPrimary)).
                setRightTextSize(15).setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TourCoolUtil.startActivity(mContext, RechargeDetailActivity.class);
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 30, 0);
        TextView textView = titleBar.getTextView(Gravity.END);
        textView.setLayoutParams(params);
    }
}
