package com.tourcoo.xiantao.ui.goods;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.XianTaoApplication;
import com.tourcoo.xiantao.adapter.ClassifyGoodsGridAdapter;
import com.tourcoo.xiantao.adapter.ClassifyNameAdapter;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleFragment;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.helper.TitleBarViewHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.StatusBarUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.classify.ClassifyGoodsBean;
import com.tourcoo.xiantao.entity.classify.GoodClassifyEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.goods.HomeFragment.EXTRA_GOODS_ID;

/**
 * @author :JenkinsZhou
 * @description :分类tab页
 * @company :途酷科技
 * @date 2019年04月24日16:32
 * @Email: 971613168@qq.com
 */
public class ClassifyGoodsFragment extends BaseTitleFragment {
    private RecyclerView classifyRecyclerView;
    private RecyclerView goodsRecyclerView;
    private ClassifyNameAdapter mClassifyNameAdapter;
    private ClassifyGoodsGridAdapter mGoodsGridAdapter;
    /**
     * 商品的分类ID
     */
    public static final String EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID";

    @Override
    public int getContentLayout() {
        return R.layout.fragment_goods_classify;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initTitle();
        init();
        getGoodsClassify();
    }


    private void initTitle() {
        RelativeLayout rlSearch = mContentView.findViewById(R.id.rlSearch);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rlSearch.getLayoutParams());
        //4个参数按顺序分别是左上右下
        lp.setMargins(0, StatusBarUtil.getStatusBarHeight(), 0, 0);
        rlSearch.setLayoutParams(lp);
        int mMaxHeight = XianTaoApplication.getImageHeight() - StatusBarUtil.getStatusBarHeight() - getResources().getDimensionPixelSize(R.dimen.dp_title_height);
        new TitleBarViewHelper(mContext)
                .setTitleBarView(mTitleBar)
                .setMinHeight(0)
                .setMaxHeight(mMaxHeight);
    }

    private void init() {
        classifyRecyclerView = mContentView.findViewById(R.id.classifyRecyclerView);
        classifyRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        goodsRecyclerView = mContentView.findViewById(R.id.goodsRecyclerView);
        goodsRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mClassifyNameAdapter = new ClassifyNameAdapter();
        mGoodsGridAdapter = new ClassifyGoodsGridAdapter();
        mClassifyNameAdapter.bindToRecyclerView(classifyRecyclerView);
        mGoodsGridAdapter.bindToRecyclerView(goodsRecyclerView);
        initClassifyItemClick();
        initGoodsItemClick();
    }


    public static ClassifyGoodsFragment newInstance() {
        Bundle args = new Bundle();
        ClassifyGoodsFragment fragment = new ClassifyGoodsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar
                .setDividerVisible(false)
                .setStatusAlpha(StatusBarUtil.isSupportStatusBarFontChange() ? 0 : 102)
                .setStatusAlpha(0)
                .setVisibility(View.GONE);
        StatusBarUtil.setStatusBarLightMode(mContext);
        titleBar.setLeftTextColor(Color.WHITE)
                .setBgColor(Color.WHITE);
    }


    /**
     * 请求商品分类信息
     */
    private void getGoodsClassify() {
        ApiRepository.getInstance().getGoodsClassify().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    showClassifyInfo(parseClassifyInfo(entity.data));
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private List<GoodClassifyEntity> parseClassifyInfo(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseArray(info, GoodClassifyEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    /**
     * 显示分类
     *
     * @param classifyEntityList
     */
    private void showClassifyInfo(List<GoodClassifyEntity> classifyEntityList) {
        if (classifyEntityList == null) {
            TourCooLogUtil.e(TAG, TAG + ":" + "null");
            return;
        }
        TourCooLogUtil.i(TAG, TAG + "集合长度:" + classifyEntityList.size());
        mClassifyNameAdapter.setNewData(classifyEntityList);
        List<GoodClassifyEntity> goodClassifyEntityList = mClassifyNameAdapter.getData();
        if (goodClassifyEntityList.isEmpty() || goodClassifyEntityList.get(0).getChildlist().isEmpty()) {
            return;
        }
        //默认显示第一排数据
        showSelect(goodClassifyEntityList, 0);

    }


    private void initClassifyItemClick() {
        mClassifyNameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<GoodClassifyEntity> goodClassifyEntityList = mClassifyNameAdapter.getData();
                showSelect(goodClassifyEntityList, position);
            }
        });

        mGoodsGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });
    }

    /**
     * 响应用户点击事件
     *
     * @param goodClassifyEntityList
     * @param position
     */
    private void showSelect(List<GoodClassifyEntity> goodClassifyEntityList, int position) {
        for (int i = 0; i < goodClassifyEntityList.size(); i++) {
            if (position == i) {
                goodClassifyEntityList.get(i).setSelect(true);
            } else {
                goodClassifyEntityList.get(i).setSelect(false);
            }
        }
        mClassifyNameAdapter.notifyDataSetChanged();
        showGridView(position);
    }


    private void showGridView(int position) {
        if (mClassifyNameAdapter.getData().isEmpty()) {
            return;
        }
        List<ClassifyGoodsBean> classifyGoodsBeanList = mClassifyNameAdapter.getData().get(position).getChildlist();
        if (classifyGoodsBeanList == null) {
            return;
        }
        mGoodsGridAdapter.setNewData(classifyGoodsBeanList);
    }


    /**
     * 初始化gridView中商品点击事件
     */
    private void initGoodsItemClick() {
        mGoodsGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ClassifyGoodsBean goodsBean = mGoodsGridAdapter.getItem(position);
                if (goodsBean == null) {
                    ToastUtil.showFailed("未获取到商品id");
                    return;
                }
                TourCooLogUtil.i(TAG, TAG + ":" + goodsBean.getId());
                int goodsId = goodsBean.getId();
//                skipGoodsDetail(goodsId);
                skipGoodsList(goodsBean.getId());
            }
        });
    }


    private void skipGoodsDetail(int goodsId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_GOODS_ID, goodsId);
        intent.setClass(mContext, GoodsDetailActivity.class);
        startActivity(intent);
    }


    private void skipGoodsList(int categoryId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        intent.setClass(mContext, GoodsCategoryListActivity.class);
        startActivity(intent);
    }


}
