package com.tourcoo.xiantao.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.TuanListAdapter;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity;
import com.tourcoo.xiantao.widget.dialog.PinTuanDialog;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.List;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.EXTRA_PIN_USER_ID;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.EXTRA_SETTLE_TYPE;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.SETTLE_TYPE_PIN;


public class TuanListActivity extends BaseTourCooTitleActivity implements OnRefreshListener {
    private TuanListAdapter mAdapter;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView rvContent;

    private int goods_id;
    private PinTuanDialog dialog;


    @Override
    public int getContentLayout() {
        return R.layout.activity_tuan_list;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("全部拼团");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        goods_id = getIntent().getIntExtra("goods_id", 0);

        mRefreshLayout = findViewById(R.id.smartLayoutRoot);
        rvContent = findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new TuanListAdapter(this, null);
        rvContent.setAdapter(mAdapter);
        rvContent.addItemDecoration(new TourCoolRecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mAdapter.setIOnItemClickListener(new TuanListAdapter.IOnItemClickListener() {
            @Override
            public void onJoinTuanClick(int tuan_id, int num,String surplus,long time) {
                dialog = new PinTuanDialog(TuanListActivity.this, num,surplus,time, new PinTuanDialog.Callback() {
                    @Override
                    public void onAdded(int quantity) {
                        joinTuan(tuan_id, quantity);
                    }
                });
                dialog.show();
            }
        });
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        getTuanList();

    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getTuanList();
    }

    /**
     * 获取拼团列表
     */
    private void getTuanList() {
        ApiRepository.getInstance().tuanList(goods_id).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<List<Goods.TuanListBean>>>() {
                    @Override
                    public void onRequestNext(BaseEntity<List<Goods.TuanListBean>> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                if (!entity.data.isEmpty()) {
//                                    mStatusLayoutManager.showSuccessLayout();
                                    mAdapter.setNewData(entity.data);
                                    mRefreshLayout.finishRefresh(true);
                                } else {
                                    mStatusLayoutManager.showEmptyLayout();
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
//                                mStatusLayoutManager.showErrorLayout();
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        TourCooLogUtil.e(TAG, TAG + "异常:" + e.toString());
//                        mStatusLayoutManager.showErrorLayout();
                    }
                });
    }


    /**
     * 加入拼团
     */
    private void joinTuan(int tuan_id, int num) {
        ApiRepository.getInstance().joinTuan(tuan_id, num).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    String info = JSON.toJSONString(entity.data);
                                    JSONObject jsonObject = JSONObject.parseObject(info);
                                    int pinId = jsonObject.getInteger("tuanuser_id");
                                    skipOrderSettleByPin(pinId);
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


    /**
     * 发起拼团的结算跳转
     *
     * @param pinId
     */
    private void skipOrderSettleByPin(int pinId) {
        Intent intent = new Intent();
        //单独购买结算类型
        intent.putExtra(EXTRA_SETTLE_TYPE, SETTLE_TYPE_PIN);
        intent.putExtra(EXTRA_PIN_USER_ID, pinId);
        intent.setClass(mContext, OrderSettleDetailActivity.class);
        TourCooLogUtil.i(TAG, "value:" + pinId);
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cancelAllTimers();
        }
    }
}
