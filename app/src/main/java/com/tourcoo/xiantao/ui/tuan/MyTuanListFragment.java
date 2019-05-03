package com.tourcoo.xiantao.ui.tuan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.MyTuanListAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseFragment;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.order.OrderEntity;
import com.tourcoo.xiantao.entity.tuan.TuanEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.coment.EvaluationActivity;
import com.tourcoo.xiantao.ui.order.OrderDetailActivity;
import com.tourcoo.xiantao.ui.order.ReturnGoodsActivity;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_COMMENT;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_SEND;
import static com.tourcoo.xiantao.constant.TuanConstant.TUAN_STATUS_MINE;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderDetailActivity.EXTRA_ORDER_ID;
import static com.tourcoo.xiantao.ui.order.ReturnGoodsActivity.EXTRA_GOODS_LIST;

/**
 * @author :JenkinsZhou
 * @description :订单列表（全部状态）
 * @company :途酷科技
 * @date 2019年04月27日18:30
 * @Email: 971613168@qq.com
 */
public class MyTuanListFragment extends BaseFragment implements OnRefreshLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private int mDefaultPage = 1;
    private int mDefaultPageSize = 10;
    private boolean isLoadMore = false;
    private int totalPage = -1;

    private MyTuanListAdapter mAdapter;
    private int tuanStatus = TUAN_STATUS_MINE;
    public static final String EXTRA_TUAN_STATUS = "EXTRA_TUAN_STATUS";


    public static MyTuanListFragment newInstance(int tuanStatus) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_TUAN_STATUS, tuanStatus);
        MyTuanListFragment fragment = new MyTuanListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.layout__smart_refresh;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (getArguments() == null) {
            ToastUtil.show("未获取到数据");
            return;
        }
        tuanStatus = getArguments().getInt(EXTRA_TUAN_STATUS, -1);

        mAdapter = new MyTuanListAdapter(getContext(), null);
        mRefreshLayout = mContentView.findViewById(R.id.smartLayout_rootFastLib);
        mRecyclerView = mContentView.findViewById(R.id.rv_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        loadData(mDefaultPage);
    }

    private void loadData(int page) {
        requestTuanListInfo(page);
    }

    /**
     * 获取拼团列表记录
     */
    private void requestTuanListInfo(int page) {
        ApiRepository.getInstance().requestTuanListInfo(tuanStatus, page).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    TuanEntity tuanEntity = parseTuanEntity(entity.data);
                                    if (tuanEntity != null) {
                                        totalPage = tuanEntity.getLast_page();
                                        if (!isLoadMore) {
                                            mAdapter.setNewData(tuanEntity.getData());
                                        } else {
                                            mAdapter.addMoreItem(tuanEntity.getData());
                                        }

                                    } else {
                                        ToastUtil.showFailed(entity.msg);
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                mRefreshLayout.finishRefresh(false);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mRefreshLayout.finishRefresh(false);
                    }
                });
    }


    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        isLoadMore = true;
        if (totalPage != -1 && mDefaultPage + 1 < totalPage) {
            mDefaultPage++;
            loadData(mDefaultPage);
        } else {
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        isLoadMore = false;
        mDefaultPage = 1;
        loadData(mDefaultPage);
    }


    private TuanEntity parseTuanEntity(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseObject(info, TuanEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mAdapter != null){
            mAdapter.cancelAllTimers();
        }
    }


}
