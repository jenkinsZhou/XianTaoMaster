package com.tourcoo.xiantao.ui.mine.account;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.ReceivingInfoAdapter;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.ResourceUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.ReceivingInfoEntity;
import com.tourcoo.xiantao.ui.BaseTourcooRefreshLoadActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :收货地址
 * @company :途酷科技
 * @date 2019年03月29日12:50
 * @Email: 971613168@qq.com
 */
public class ShippingAddressActivity extends BaseTourcooRefreshLoadActivity<ReceivingInfoEntity> {
    private ReceivingInfoAdapter mAdapter;
    private List<ReceivingInfoEntity> mReceivingInfoEntityList = new ArrayList<>();

    @Override
    public int getContentLayout() {
        return R.layout.activity_shipping_address;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public BaseQuickAdapter<ReceivingInfoEntity, BaseViewHolder> getAdapter() {
        mAdapter = new ReceivingInfoAdapter(mReceivingInfoEntityList);
        return mAdapter;
    }

    @Override
    public void loadData(int page) {

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("收货地址");
        titleBar.setRightText("新增地址").
                setRightTextColor(ResourceUtil.getColor(R.color.colorPrimary)).
                setRightTextSize(15).setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TourCoolUtil.startActivity(mContext, AddNewAddressActivity.class);
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        testLoad();
        mRefreshLoadDelegate.mStatusManager.showSuccessLayout();
    }

    private void testLoad() {
        mReceivingInfoEntityList.clear();
        String addr = "安徽省合肥市经开区高速翡翠湖畔测试号楼一单元1103室";
        ReceivingInfoEntity entity = new ReceivingInfoEntity("蕙雅", "18256079757", addr);
        int count = 10;
        for (int i = 0; i < count; i++) {
            mReceivingInfoEntityList.add(entity);
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        super.onRefresh(refreshlayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefresh();
            }
        },1000);
    }

    @Override
    public boolean isRefreshEnable() {
        return true;
    }

    @Override
    public boolean isLoadMoreEnable() {
        return true;
    }
}
