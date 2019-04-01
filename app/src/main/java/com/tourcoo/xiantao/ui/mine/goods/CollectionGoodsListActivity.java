package com.tourcoo.xiantao.ui.mine.goods;

import android.os.Bundle;
import android.os.Handler;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.CollectionGoodsAdapter;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.GoodsEntity;
import com.tourcoo.xiantao.ui.BaseTourcooRefreshLoadActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :收藏商品列表
 * @company :途酷科技
 * @date 2019年03月29日16:21
 * @Email: 971613168@qq.com
 */
public class CollectionGoodsListActivity extends BaseTourcooRefreshLoadActivity<GoodsEntity> {
    private List<GoodsEntity> collectionGoodsList = new ArrayList<>();
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
        mCollectionGoodsAdapter = new CollectionGoodsAdapter(collectionGoodsList);
        return mCollectionGoodsAdapter;
    }

    @Override
    public void loadData() {
        super.loadData();
        testLoad();
    }

    @Override
    public void loadData(int page) {

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

    private void testLoad() {
        mStatusManager.showSuccessLayout();
        int size = 25;
        GoodsEntity goodsEntity;
        for (int i = 0; i < size; i++) {
            goodsEntity = new GoodsEntity();
            goodsEntity.goodsSpec = "10" + i + "g/包";
            goodsEntity.goodsCurrentPrice = 10 + i + 0.9;
            goodsEntity.goodsLabels = "维生素C 很甜";
            goodsEntity.goodsName = "赣州脐橙";
            collectionGoodsList.add(goodsEntity);
        }

    }
}
