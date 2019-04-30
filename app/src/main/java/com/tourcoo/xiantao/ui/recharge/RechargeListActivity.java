package com.tourcoo.xiantao.ui.recharge;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.RechargeDetailAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.recharge.RechargeDetail;
import com.tourcoo.xiantao.entity.recharge.RechargeHistory;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooRefreshLoadActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :充值明细列表
 * @company :途酷科技
 * @date 2019年03月26日17:49
 * @Email: 971613168@qq.com
 */
public class RechargeListActivity extends BaseTourCooRefreshLoadActivity<RechargeDetail> {
    private RechargeDetailAdapter adapter;

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
       /* titleBar.setRightText("开具发票").
                setRightTextColor(ResourceUtil.getColor(R.color.colorPrimary)).
                setRightTextSize(15).setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("开具发票");
            }
        });*/
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 20, 0);
        TextView textView = titleBar.getTextView(Gravity.END);
        textView.setLayoutParams(params);

    }


    @Override
    public RechargeDetailAdapter getAdapter() {
        adapter = new RechargeDetailAdapter();
        return adapter;
    }

    @Override
    public void loadData(int page) {
        requestRechargeList(page);
    }

    @Override
    public void loadData() {
        super.loadData();
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mDefaultPage = 1;
        super.onRefresh(refreshlayout);
    }


    /**
     * 查询充值记录
     */
    private void requestRechargeList(int page) {
        ApiRepository.getInstance().requestRechargeList(page).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<RechargeHistory>>() {
                    @Override
                    public void onRequestNext(BaseEntity<RechargeHistory> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                RechargeHistory rechargeHistory = entity.data;
                                UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), rechargeHistory.getData() == null ? new ArrayList<>() : rechargeHistory.getData(), null);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }

                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mStatusManager.showErrorLayout();
                    }
                });
    }


}
