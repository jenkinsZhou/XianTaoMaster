package com.tourcoo.xiantao.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.CategoryGoodsAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseRefreshFragment;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.GoodsCategoryBean;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.ArrayList;

import static com.tourcoo.xiantao.constant.GoodsConstant.TYPE_GOODS_NORMAL;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.home.HomeFragment.EXTRA_GOODS_ID;

/**
 * @author :JenkinsZhou
 * @description :新品分类列表
 * @company :途酷科技
 * @date 2019年04月27日14:35
 * @Email: 971613168@qq.com
 */
public class GoodsCategoryNormalFragment extends BaseRefreshFragment<GoodsCategoryBean.GoodsSimpleInfo> {
    private CategoryGoodsAdapter mCategoryGoodsAdapter;
    private static final String EXTRA_TYPE_GOODS = "EXTRA_TYPE_GOODS";
    private GoodsCategoryListActivity mActivity;

    @Override
    public int getContentLayout() {
        return R.layout.layout__smart_refresh;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mActivity = (GoodsCategoryListActivity) mContext;
        mDefaultPageSize = 5;
        mDefaultPage = 1;
        if (getArguments() == null) {
            return;
        }
    }

    @Override
    public CategoryGoodsAdapter getAdapter() {
        mCategoryGoodsAdapter = new CategoryGoodsAdapter();
        return mCategoryGoodsAdapter;
    }

    @Override
    public void loadData(int page) {
        TourCooLogUtil.i(TAG, TAG + "执行了:loadData（page=" + page);
        getCategoryGoodsList(TYPE_GOODS_NORMAL, page);
        initItemClick();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mDefaultPage = 1;
        super.onRefresh(refreshlayout);
    }


    public static GoodsCategoryNormalFragment newInstance() {
        Bundle args = new Bundle();
        GoodsCategoryNormalFragment fragment = new GoodsCategoryNormalFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void loadData() {
        super.loadData();
    }


    /**
     * 根据商品类型请求相应数据
     */
    public void getCategoryGoodsList(String type, int pageIndex) {
        TourCooLogUtil.i(TAG, TAG + ":" + "mActivity.categoryId = " + mActivity.categoryId);
        ApiRepository.getInstance().getCategoryGoodsList(mActivity.param,mActivity.categoryId, type, pageIndex, mActivity.keyword).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    GoodsCategoryBean garageEntity = parseGoodsInfo(entity.data);
                                    if (garageEntity != null) {
                                        UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), garageEntity.getListdata() == null ? new ArrayList<>() : garageEntity.getListdata(), null);
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                mStatusManager.showErrorLayout();
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


    private GoodsCategoryBean parseGoodsInfo(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String homeInfo = JSONObject.toJSONString(object);
            LogUtils.e(TAG, homeInfo);
            return JSON.parseObject(homeInfo, GoodsCategoryBean.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    private void initItemClick() {
        mCategoryGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                skipGoodsDetail(mCategoryGoodsAdapter.getData().get(position).getId());
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

