package com.tourcoo.xiantao.ui.mine.recharge;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tencent.bugly.beta.Beta;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.RechargeDetailAdapter;
import com.tourcoo.xiantao.core.helper.VersionCheckHelper;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.ResourceUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.RechargeEntity;
import com.tourcoo.xiantao.entity.UpdateEntity;
import com.tourcoo.xiantao.ui.BaseTourcooRefreshLoadActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author :JenkinsZhou
 * @description :充值明细
 * @company :途酷科技
 * @date 2019年03月26日17:49
 * @Email: 971613168@qq.com
 */
public class RechargeDetailActivity extends BaseTourcooRefreshLoadActivity<RechargeEntity> {
    private RechargeDetailAdapter adapter;
    private List<RechargeEntity> rechargeEntityList = new ArrayList<>();

    @Override
    public int getContentLayout() {
        return R.layout.activity_recharge_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        TourCoolRecycleViewDivider divider = new TourCoolRecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.grayDivider));
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("充值明细");
        titleBar.setRightText("开具发票").
                setRightTextColor(ResourceUtil.getColor(R.color.colorPrimary)).
                setRightTextSize(15).setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("开具发票");
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 20, 0);
        TextView textView = titleBar.getTextView(Gravity.END);
        textView.setLayoutParams(params);

    }


    @Override
    public RechargeDetailAdapter getAdapter() {
        adapter = new RechargeDetailAdapter(rechargeEntityList);
        return adapter;
    }

    @Override
    public void loadData(int page) {

    }

    @Override
    public void loadData() {
        super.loadData();
        mRefreshLoadDelegate.mStatusManager.showSuccessLayout();
        loadTestData();
        Beta.checkUpgrade(false, false);
    }

    private void loadTestData() {
        rechargeEntityList.clear();
        RechargeEntity rechargeEntity;
        int size = 21;
        for (int i = 0; i < size; i++) {
            rechargeEntity = new RechargeEntity();
            rechargeEntity.accountBalance = 36.25 + i;
            rechargeEntity.rechargeTime = "2019-02-28 12:34";
            rechargeEntity.rechargeMoney = 500.00;
            rechargeEntityList.add(rechargeEntity);
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        super.onRefresh(refreshlayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UpdateEntity updateEntity = new UpdateEntity();
                updateEntity.url = "http://www.baidu/test.apk";
                VersionCheckHelper.with(RechargeDetailActivity.this).checkVersion(updateEntity);
                mRefreshLayout.finishRefresh();
            }
        }, 1000);
    }


}
