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
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.event.OrderRefreshEvent;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsCollectEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooRefreshLoadActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_ALL;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_PAY;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.home.HomeFragment.EXTRA_GOODS_ID;

/**
 * @author :JenkinsZhou
 * @description :收藏商品列表
 * @company :途酷科技
 * @date 2019年03月29日16:21
 * @Email: 971613168@qq.com
 */
public class CollectionGoodsListActivity extends BaseTourCooRefreshLoadActivity<Goods> {
    private int currentSelectPosition;
    private CollectionGoodsAdapter mCollectionGoodsAdapter;
    public static final int REQUSET_CODE_COLLECTION = 113;

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
        mCollectionGoodsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.rrContent:
                        skipGoodsDetail(mCollectionGoodsAdapter.getData().get(position).getGoods_id());
                        break;
                    case R.id.btnCancelCollection:
                        currentSelectPosition = position;
                        collectCancel(mCollectionGoodsAdapter.getData().get(position).getGoods_id());
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void skipGoodsDetail(int goodsId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_GOODS_ID, goodsId);
        intent.setClass(mContext, GoodsDetailActivity.class);
        startActivityForResult(intent, REQUSET_CODE_COLLECTION);
    }


    private void collectCancel(int goodsId) {
        if (goodsId < 0) {
            ToastUtil.showFailed("未获取到商品信息");
            return;
        }
        ApiRepository.getInstance().collectCancel(goodsId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            ToastUtil.showSuccess(entity.msg);
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                refreshCollectList(currentSelectPosition);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 刷新列表
     */
    private void refreshCollectList(int position) {

        if (position < 0 || position >= mCollectionGoodsAdapter.getData().size()) {
            return;
        }
        TourCooLogUtil.i(TAG, TAG + "当前操作的位置:" + position);
        mCollectionGoodsAdapter.remove(position);
        mCollectionGoodsAdapter.refreshNotifyItemChanged(position);
        if (mCollectionGoodsAdapter.getData().isEmpty()) {
            mStatusManager.showEmptyLayout();
        }
        //todo
//        mRefreshLayout.autoRefresh();
       /* if (mAdapter.getData().isEmpty()) {
            mRefreshLayout.autoRefresh();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUSET_CODE_COLLECTION:
                if (resultCode == RESULT_OK) {
                    mRefreshLayout.autoRefresh();
                }
                break;
            default:
                break;
        }
    }
}
