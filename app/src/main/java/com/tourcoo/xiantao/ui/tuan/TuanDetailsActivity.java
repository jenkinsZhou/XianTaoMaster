package com.tourcoo.xiantao.ui.tuan;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.TuanDetailsAdapter;
import com.tourcoo.xiantao.adapter.TuanListAdapter;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.tuan.TuanDetails;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.widget.dialog.PinTuanDialog;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.List;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;


public class TuanDetailsActivity extends BaseTourCooTitleActivity implements OnRefreshListener {
    private TuanDetailsAdapter mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView rvContent;

    private int tuan_id;


    @Override
    public int getContentLayout() {
        return R.layout.activity_tuan_list;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("参团详情");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tuan_id = getIntent().getIntExtra("tuan_id", 0);

        mRefreshLayout = findViewById(R.id.smartLayoutRoot);
        rvContent = findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new TuanDetailsAdapter(this, null);
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(new TourCoolRecycleViewDivider(this, LinearLayoutManager.VERTICAL));

        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        tuanDetails();

    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        tuanDetails();
    }

    /**
     * 单个团详情
     */
    private void tuanDetails() {
        ApiRepository.getInstance().tuanDetails(tuan_id).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<List<TuanDetails>>>() {
                    @Override
                    public void onRequestNext(BaseEntity<List<TuanDetails>> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                if (!entity.data.isEmpty()) {
                                    mAdapter.setNewData(entity.data);
                                    mRefreshLayout.finishRefresh(true);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        TourCooLogUtil.e(TAG, TAG + "异常:" + e.toString());
                    }
                });
    }

}
