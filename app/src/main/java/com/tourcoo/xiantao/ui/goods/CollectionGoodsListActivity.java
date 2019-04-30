package com.tourcoo.xiantao.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.CollectionGoodsAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsCollectEntity;
import com.tourcoo.xiantao.entity.goods.GoodsDetailEntity;
import com.tourcoo.xiantao.entity.message.MessageBean;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooRefreshLoadActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.goods.HomeFragment.EXTRA_GOODS_ID;

/**
 * @author :JenkinsZhou
 * @description :收藏商品列表
 * @company :途酷科技
 * @date 2019年03月29日16:21
 * @Email: 971613168@qq.com
 */
public class CollectionGoodsListActivity extends BaseTourCooRefreshLoadActivity<Goods> {
    private CollectionGoodsAdapter mCollectionGoodsAdapter;

    @Override
    public int getContentLayout() {
        return R.layout.activity_collection_goods;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("收藏商品");
    }

    @Override
    public CollectionGoodsAdapter getAdapter() {
        mCollectionGoodsAdapter = new CollectionGoodsAdapter();
        return mCollectionGoodsAdapter;
    }

    @Override
    public void loadData() {
        super.loadData();
        initItemClick();
    }

    @Override
    public void loadData(int page) {
        requestGoodsCollectList(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        super.onRefresh(refreshlayout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.finishRefresh();
            }
        }, 100);
    }


    /**
     * 查询消息列表
     */
    private void requestGoodsCollectList(int page) {
        ApiRepository.getInstance().requestGoodsCollectList(page).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<GoodsCollectEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<GoodsCollectEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                GoodsCollectEntity collectEntity = entity.data;
                                UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), collectEntity.getData() == null ? new ArrayList<>() : collectEntity.getData(), null);
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


    private void initItemClick() {
        mCollectionGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                skipGoodsDetail(mCollectionGoodsAdapter.getData().get(position).getGoods_id());
            }
        });
    }

    private void skipGoodsDetail(int goodsId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_GOODS_ID, goodsId);
        intent.setClass(mContext, GoodsDetailActivity.class);
        startActivity(intent);
    }
}