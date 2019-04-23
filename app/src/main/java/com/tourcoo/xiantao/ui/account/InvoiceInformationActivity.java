package com.tourcoo.xiantao.ui.account;


import android.os.Bundle;
import android.os.Handler;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.InvoiceInformationAdapter;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.InvoiceInfomationEntity;
import com.tourcoo.xiantao.ui.BaseTourcooRefreshLoadActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author :JenkinsZhou
 * @description :发票信息
 * @company :途酷科技
 * @date 2019年03月29日20:19
 * @Email: 971613168@qq.com
 */
public class InvoiceInformationActivity extends BaseTourcooRefreshLoadActivity<InvoiceInfomationEntity> {
    private InvoiceInformationAdapter mInvoiceInformationAdapter;
    private List<InvoiceInfomationEntity> mInvoiceInformationList = new ArrayList<>();

    @Override
    public int getContentLayout() {
        return R.layout.activity_invoice_information;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        TourCoolRecycleViewDivider divider = new TourCoolRecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.grayDivider));
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void loadData() {
        super.loadData();
        mStatusManager.showSuccessLayout();
        testLoad();
    }

    @Override
    public BaseQuickAdapter<InvoiceInfomationEntity, BaseViewHolder> getAdapter() {
        mInvoiceInformationAdapter = new InvoiceInformationAdapter(mInvoiceInformationList);
        return mInvoiceInformationAdapter;
    }

    @Override
    public void loadData(int page) {

    }

    private void testLoad() {
        int size = 15;
        InvoiceInfomationEntity entity;
        for (int i = 0; i < size; i++) {
            entity = new InvoiceInfomationEntity();
            if (i % 3 != 0) {
                entity.invoiceStatus = 1;
                entity.invoiceType = 1;
            } else {
                entity.invoiceStatus = 2;
                entity.invoiceType = 2;
            }
            entity.invoiceCompany = "南京途酷科技有限公司";
            entity.invoiceNumber = "82731970349702983";
            entity.invoiceDescription = "购物费用";
            entity.invoiceMoney = i + 58;
            mInvoiceInformationList.add(entity);
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
}
