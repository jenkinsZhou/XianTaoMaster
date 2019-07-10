package com.tourcoo.xiantao.ui.goods;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.CategoryGoodsAdapter;
import com.tourcoo.xiantao.adapter.CategoryGoodsAdapterVersion2;
import com.tourcoo.xiantao.adapter.ClassifyNameAdapter;
import com.tourcoo.xiantao.adapter.GoodsCategoryNameAdapter;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleFragment;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.classify.CategoryEntity;
import com.tourcoo.xiantao.entity.classify.GoodClassifyEntity;
import com.tourcoo.xiantao.entity.goods.GoodsCategoryBean;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.List;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.home.HomeFragment.EXTRA_GOODS_ID;

/**
 * @author :JenkinsZhou
 * @description :分类tab页第二版
 * @company :途酷科技
 * @date 2019年06月25日17:33
 * @Email: 971613168@qq.com
 */
public class ClassifyGoodsFragmentVersion2 extends BaseTitleFragment implements OnLoadMoreListener {
    private static final int PER_PAGE_SIZE = 10;
    private RecyclerView classifyRecyclerView;
    private RecyclerView goodsRecyclerView;
    private SmartRefreshLayout smartLayoutRoot;
    private GoodsCategoryNameAdapter mClassifyNameAdapter;
    private StatusLayoutManager statusLayoutManager;
    private LinearLayout llContentView;
    private RelativeLayout rlContentViewChild;
    private StatusLayoutManager statusLayoutManagerChild;
    private CategoryGoodsAdapterVersion2 mCategoryGoodsAdapter;
    /**
     * 商品的分类ID
     */
    public static final String EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID";
    public static final String EXTRA_CATEGORY_NAME = "EXTRA_CATEGORY_NAME";
    private String classifyName;
    private int currentSelectId;
    private int currentPage;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_goods_classify;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();
        setupStatusLayoutManager();
        setupStatusLayoutManagerChild();
        getGoodsClassify();
    }


    private void init() {
        llContentView = mContentView.findViewById(R.id.llContentView);
        rlContentViewChild = mContentView.findViewById(R.id.rlContentViewChild);
        classifyRecyclerView = mContentView.findViewById(R.id.classifyRecyclerView);
        smartLayoutRoot = mContentView.findViewById(R.id.smartLayoutRoot);
        smartLayoutRoot.setEnableRefresh(false);
        smartLayoutRoot.setOnLoadMoreListener(this);
        classifyRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        goodsRecyclerView = mContentView.findViewById(R.id.goodsRecyclerView);
        goodsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mClassifyNameAdapter = new GoodsCategoryNameAdapter();
        mCategoryGoodsAdapter = new CategoryGoodsAdapterVersion2();
        mClassifyNameAdapter.bindToRecyclerView(classifyRecyclerView);
        mCategoryGoodsAdapter.bindToRecyclerView(goodsRecyclerView);
        smartLayoutRoot.setEnableLoadMore(true);
        initClassifyItemClick();
        initGoodsItemClick();
    }


    public static ClassifyGoodsFragmentVersion2 newInstance() {
        Bundle args = new Bundle();
        ClassifyGoodsFragmentVersion2 fragment = new ClassifyGoodsFragmentVersion2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        TextView textView = titleBar.getTextView(Gravity.CENTER | Gravity.TOP);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        titleBar.setTitleMainText("分类");

    }


    /**
     * 请求商品分类信息
     */
    private void getGoodsClassify() {
        statusLayoutManager.showLoadingLayout();
        ApiRepository.getInstance().requestCategoryList().compose(bindUntilEvent(FragmentEvent.DESTROY)).
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
                                statusLayoutManager.showErrorLayout();
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        statusLayoutManager.showErrorLayout();
                    }
                });
    }


    private List<CategoryEntity> parseClassifyInfo(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseArray(info, CategoryEntity.class);
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
    private void showClassifyInfo(List<CategoryEntity> classifyEntityList) {
        if (classifyEntityList == null) {
            TourCooLogUtil.e(TAG, TAG + ":" + "null");
            return;
        }
        statusLayoutManager.showSuccessLayout();
        TourCooLogUtil.i(TAG, TAG + "集合长度:" + classifyEntityList.size());
        mClassifyNameAdapter.setNewData(classifyEntityList);
        List<CategoryEntity> goodClassifyEntityList = mClassifyNameAdapter.getData();
        if (goodClassifyEntityList.isEmpty()) {
            return;
        }
        //默认显示第一排数据
        currentSelectId = goodClassifyEntityList.get(0).getId();
        showSelect(goodClassifyEntityList, 0);
        refreshCategoryGoodsList(goodClassifyEntityList.get(0).getId());
    }


    private void initClassifyItemClick() {
        mClassifyNameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<CategoryEntity> goodClassifyEntityList = mClassifyNameAdapter.getData();
                currentSelectId = goodClassifyEntityList.get(position).getId();
                showSelect(goodClassifyEntityList, position);
                refreshCategoryGoodsList(goodClassifyEntityList.get(position).getId());
            }
        });

        mCategoryGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
    private void showSelect(List<CategoryEntity> goodClassifyEntityList, int position) {
        for (int i = 0; i < goodClassifyEntityList.size(); i++) {
            if (position == i) {
                goodClassifyEntityList.get(i).setSelect(true);
            } else {
                goodClassifyEntityList.get(i).setSelect(false);
            }
        }
        mClassifyNameAdapter.notifyDataSetChanged();

    }


    /**
     * 初始化gridView中商品点击事件
     */
    private void initGoodsItemClick() {
        mCategoryGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsCategoryBean.GoodsSimpleInfo goodsBean = mCategoryGoodsAdapter.getItem(position);
                if (goodsBean == null) {
                    ToastUtil.showFailed("未获取到商品id");
                    return;
                }
                skipGoodsDetail(mCategoryGoodsAdapter.getData().get(position).getId());
            }
        });
    }


    private void skipGoodsList(int categoryId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGORY_ID, categoryId);
        intent.putExtra(EXTRA_CATEGORY_NAME, classifyName);
        intent.setClass(mContext, GoodsCategoryListActivity.class);
        startActivity(intent);
    }


    private void setupStatusLayoutManager() {
        statusLayoutManager = new StatusLayoutManager.Builder(llContentView)
                // 设置默认布局属性
//                .setDefaultEmptyText("空白了，哈哈哈哈")
//                .setDefaultEmptyImg(R.mipmap.ic_launcher)
//                .setDefaultEmptyClickViewText("retry")
//                .setDefaultEmptyClickViewTextColor(getResources().getColor(R.color.colorAccent))
//                .setDefaultEmptyClickViewVisible(false)
//
//                .setDefaultErrorText(R.string.app_name)
//                .setDefaultErrorImg(R.mipmap.ic_launcher)
//                .setDefaultErrorClickViewText("重试一波")
//                .setDefaultErrorClickViewTextColor(getResources().getColor(R.color.colorPrimaryDark))
//                .setDefaultErrorClickViewVisible(true)
//
//                .setDefaultLayoutsBackgroundColor(Color.WHITE)

                // 自定义布局
                .setLoadingLayout(getLoadingLayout())
//                .setEmptyLayout(inflate(R.layout.layout_empty))
                .setErrorLayout(inflate(R.layout.custom_error_layout))
                .setErrorClickViewID(R.id.tvRefresh)
//
//                .setLoadingLayout(R.layout.layout_loading)
//                .setEmptyLayout(R.layout.layout_empty)
//                .setErrorLayout(R.layout.layout_error)
//
//                .setEmptyClickViewID(R.id.tv_empty)
//                .setErrorClickViewID(R.id.tv_error)

                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        ToastUtil.showSuccess("点击了Empty");
                        getGoodsClassify();
//                        statusLayoutManager.showLoadingLayout();
//                        getData(1000);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        getGoodsClassify();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        getGoodsClassify();
                     /*   if (view.getId() == R.id.tv_customer) {
                            Toast.makeText(MainActivity.this, R.string.request_access, Toast.LENGTH_SHORT).show();
                        } else if (view.getId() == R.id.tv_customer1) {
                            Toast.makeText(MainActivity.this, R.string.switch_account, Toast.LENGTH_SHORT).show();
                        }*/

                    }
                })
                .build();
    }


    private void setupStatusLayoutManagerChild() {
        statusLayoutManagerChild = new StatusLayoutManager.Builder(rlContentViewChild)
                // 自定义布局
                .setLoadingLayout(getLoadingLayout())
                .setEmptyLayout(R.layout.custom_empty_layout)
                .setErrorLayout(inflate(R.layout.custom_error_layout))
                .setErrorClickViewID(R.id.tvRefresh)
                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        ToastUtil.showSuccess("点击了Empty");
//                        statusLayoutManager.showLoadingLayout();
//                        getData(1000);
                    }

                    @Override
                    public void onErrorChildClick(View view) {

                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        statusLayoutManagerChild.showLoadingLayout();
                     /*   if (view.getId() == R.id.tv_customer) {
                            Toast.makeText(MainActivity.this, R.string.request_access, Toast.LENGTH_SHORT).show();
                        } else if (view.getId() == R.id.tv_customer1) {
                            Toast.makeText(MainActivity.this, R.string.switch_account, Toast.LENGTH_SHORT).show();
                        }*/

                    }
                })
                .build();
    }

    private View inflate(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }


    /**
     * 根据商品类型请求相应数据
     */
    public void getCategoryGoodsList(int categoryId, int pageIndex) {
        TourCooLogUtil.i(TAG, "当前页码" + pageIndex + ":" + "当前分类.categoryId = " + categoryId);
        ApiRepository.getInstance().getCategoryGoodsListNoSearch(categoryId, pageIndex).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        smartLayoutRoot.finishLoadMore();
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    GoodsCategoryBean garageEntity = parseGoodsInfo(entity.data);
                                    if (garageEntity != null) {
                                        if (garageEntity.getListdata() == null || garageEntity.getListdata().isEmpty()) {
                                            if (pageIndex <= 1) {
                                                statusLayoutManagerChild.showEmptyLayout();
                                            } else {
                                                smartLayoutRoot.setEnableLoadMore(isLoadMoreEnable(garageEntity.getListdata()));
                                            }
                                        } else {
                                            mCategoryGoodsAdapter.addData(garageEntity.getListdata());
                                            smartLayoutRoot.setEnableLoadMore(isLoadMoreEnable(garageEntity.getListdata()));
                                        }
                                    } else {
                                        statusLayoutManagerChild.showEmptyLayout();
                                    }
                                } else {
                                    statusLayoutManagerChild.showEmptyLayout();
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                statusLayoutManagerChild.showEmptyLayout();
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        smartLayoutRoot.finishLoadMore();
                        statusLayoutManagerChild.showEmptyLayout();
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


    private void skipGoodsDetail(int goodsId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_GOODS_ID, goodsId);
        intent.setClass(mContext, GoodsDetailActivity.class);
        startActivity(intent);
    }


    /**
     * 刷新请求列表
     *
     * @param categoryId
     */
    private void refreshCategoryGoodsList(int categoryId) {
        currentPage = 1;
        ApiRepository.getInstance().getCategoryGoodsListNoSearch(categoryId, 1).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    GoodsCategoryBean garageEntity = parseGoodsInfo(entity.data);
                                    if (garageEntity == null) {
                                        return;
                                    }
                                    if (garageEntity.getListdata() != null) {
                                        if (garageEntity.getListdata().isEmpty()) {
                                            statusLayoutManagerChild.showEmptyLayout();
                                        } else {
                                            statusLayoutManagerChild.showSuccessLayout();
                                        }
                                        mCategoryGoodsAdapter.setNewData(garageEntity.getListdata());
                                        smartLayoutRoot.setEnableLoadMore(isLoadMoreEnable(garageEntity.getListdata()));

                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                statusLayoutManagerChild.showEmptyLayout();
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
//                        mStatusManager.showErrorLayout();
                    }
                });

    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        currentPage++;
        TourCooLogUtil.i("当前页码:" + currentPage + "分类id:" + currentSelectId);
        getCategoryGoodsList(currentSelectId, currentPage);
    }


    private boolean isLoadMoreEnable(List<GoodsCategoryBean.GoodsSimpleInfo> listData) {
        if (listData == null) {
            return false;
        }
        return listData.size() >= PER_PAGE_SIZE;
    }
}
