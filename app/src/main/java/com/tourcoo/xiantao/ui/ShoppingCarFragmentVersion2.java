package com.tourcoo.xiantao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aries.ui.view.tab.widget.MsgView;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.BaseShoppingCartAdapter2;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleTourCoolFragment;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.retrofit.TourCoolRetrofit;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.event.RefreshEvent;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsDetailEntity;
import com.tourcoo.xiantao.entity.goods.GoodsSkuBean;
import com.tourcoo.xiantao.entity.settle.SettleEntity;
import com.tourcoo.xiantao.entity.event.TabChangeEvent;
import com.tourcoo.xiantao.helper.GoodsCount;
import com.tourcoo.xiantao.helper.ShoppingCar;
import com.tourcoo.xiantao.listener.OnAddDelCallback;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity;
import com.trello.rxlifecycle3.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.goods.GoodsDetailActivity.EXTRA_SETTLE;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.EXTRA_SETTLE_TYPE;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.SETTLE_TYPE_CAR;

/**
 * @author :JenkinsZhou
 * @description :购物车tab版本2
 * @company :途酷科技
 * @date 2019年04月18日13:05
 * @Email: 971613168@qq.com
 */
public class ShoppingCarFragmentVersion2 extends BaseTitleTourCoolFragment implements View.OnClickListener, OnRefreshListener {
    private BaseShoppingCartAdapter2 mShoppingCartAdapter;
    private MainTabActivity mMainTabActivity;
    private TextView tvTotalMoneyAmount;
    private SmartRefreshLayout refreshLayout;

    /**
     * 底部编辑结算栏
     */
    private RelativeLayout llBottomLayout;

    /**
     * 空购物车
     */
    private View emptyView;

    private StatusLayoutManager mStatusLayoutManager;
    /**
     * 购物车列表
     */
    private RecyclerView rvGoods;


    private RelativeLayout rlRootView;

    private SettleEntity mSettleEntity;

    private TextView tvSettleAccounts;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_shopping_cart_version2;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();
        EventBus.getDefault().register(this);
    }


    private void init() {
        refreshLayout = mContentView.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setOnRefreshListener(this);
        mMainTabActivity = (MainTabActivity) mContext;
        rlRootView = mContentView.findViewById(R.id.rlRootView);
        llBottomLayout = mContentView.findViewById(R.id.llCart);
        tvTotalMoneyAmount = mContentView.findViewById(R.id.tvTotalMoneyAmount);
        rvGoods = mContentView.findViewById(R.id.rvGoods);
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, null, false);
        emptyView.findViewById(R.id.tvGoShopping).setOnClickListener(this);
        rvGoods.setLayoutManager(new LinearLayoutManager(mContext));
        mContentView.findViewById(R.id.tvSettleAccounts).setOnClickListener(this);
        initStatusLayout();
        initAdapter();
        if (AccountInfoHelper.getInstance().isLogin()) {
            refreshShoppingCarNoDialog();
        } else {
            showEmptyLayout();
            hideView(llBottomLayout);
        }
    }

    /**
     * 初始化多状态布局
     */
    private void initStatusLayout() {
        StatusLayoutManager.Builder builder = new StatusLayoutManager.Builder(rlRootView);
        builder.setEmptyLayout(emptyView);
        builder.setEmptyClickViewID(R.id.tvGoShopping);
        builder.setErrorLayout(inflate(R.layout.custom_error_layout))
                .setErrorClickViewID(R.id.tvRefresh);
        // 自定义布局
        builder.setLoadingLayout(getLoadingLayout());
        builder.setOnStatusChildClickListener(new OnStatusChildClickListener() {
            @Override
            public void onEmptyChildClick(View view) {
                mMainTabActivity.mTabLayout.setCurrentTab(1);
            }

            @Override
            public void onErrorChildClick(View view) {
                getMyShoppingCarList();
            }

            @Override
            public void onCustomerChildClick(View view) {
                getMyShoppingCarList();
            }
        });
        mStatusLayoutManager = builder.build();
    }

    private View inflate(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    private void initAdapter() {
        mShoppingCartAdapter = new BaseShoppingCartAdapter2() {
            @Override
            protected void convert(BaseViewHolder helper, Goods goods) {
                RoundedImageView ivGoodsIcon = helper.getView(R.id.ivGoodsIcon);
                GlideManager.loadImg(goods.getImage(), ivGoodsIcon);
                GoodsSkuBean goodsSkuBean = goods.getGoods_sku();
                if (goodsSkuBean != null && goodsSkuBean.getGoods_attr() != null) {
                    helper.setText(R.id.tvGoodsLabel, TourCoolUtil.getStringNotNull(goodsSkuBean.getGoods_attr()));
                }
                helper.setText(R.id.tvGoodsName, TourCoolUtil.getStringNotNull(goods.getGoods_name()));
//                helper.setText(R.id.tvGoodsLabel, TourCoolUtil.getStringNotNull(item.goodsLabels));
                helper.setText(R.id.tvGoodsPrice, "￥" + goods.getGoods_price());
//                helper.setText(R.id.tvGoodsSpec, TourCoolUtil.getStringNotNull(item.goodsSpec));
                initAddDelButtonListener(helper, goods);
                intSwipeDeleteListener(helper, goods);
            }
        };
        mShoppingCartAdapter.bindToRecyclerView(rvGoods);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSettleAccounts:
                skipOrderSettleDetail(mSettleEntity);
                break;
            default:
                break;
        }
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("购物车");
    }


    /**
     * @param refreshEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShoppingCarRefreshEvent(RefreshEvent refreshEvent) {
        //todo 执行购物车列表刷新
        if (refreshEvent == null) {
            return;
        }
        TourCooLogUtil.i(TAG, "刷新购物车");
        mMainTabActivity.getTotalNumAndRefreshShoppingCar();
//        refreshShoppingCarNoDialog();
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    public static ShoppingCarFragmentVersion2 newInstance() {
        Bundle args = new Bundle();
        ShoppingCarFragmentVersion2 fragment = new ShoppingCarFragmentVersion2();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 在适配器中监听每个item的加减按钮
     */
    private void initAddDelButtonListener(BaseViewHolder helper, Goods goods) {
        /**
         * 减控件
         */
        TextView tvGoodsMinus = helper.getView(R.id.tvGoodsMinus);
        EditText etNumber = helper.getView(R.id.etNumber);
        showTextNumber(etNumber, goods.getTotal_num() + "");
        tvGoodsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TourCooLogUtil.i(TAG, TAG + "商品:" + goods.getTotal_num());
                if (goods.getTotal_num() <= 1) {
                    ToastUtil.show("当前商品不能再减了哦");
                    return;
                }
                reduceGoods(goods.getGoods_id(), goods.getGoods_sku_id());
            }
        });
        /**
         * 加控件
         */
        TextView tvGoodsPlus = helper.getView(R.id.tvGoodsPlus);
        tvGoodsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsSkuBean goodsSkuBean = goods.getGoods_sku();
                if (goodsSkuBean == null) {
                    return;
                }
                if (goodsSkuBean.getStock_num() < goods.getTotal_num()) {
                    ToastUtil.showFailed("超出了该商品库存");
                    return;
                }
                addGoods(goods.getGoods_id(), 1, goods.getGoods_sku_id());
            }
        });
        //显示每个商品的数量

    }


    /**
     * 初始化侧滑删除点击事件
     */
    private void intSwipeDeleteListener(BaseViewHolder helper, Goods goods) {
        Button btnDelete = helper.getView(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                item.select = false;
//                item.goodsCount = 0;
                //todo 请求删除商品接口
//                mShoppingCartAdapter.remove(helper.getLayoutPosition());

             /*   deleteGoods(goods.getGoods_id(), "48");
                showBottomMoney();
                showBottomToolBarByCondition();
                showEmptyViewByCondition();*/
                deleteGoods(goods.getGoods_id(), goods.getGoods_sku_id());
            }
        });
    }


    /**
     * 显示控件
     *
     * @param view
     */
    private void showView(View view) {
        if (view == null) {
            TourCooLogUtil.e(TAG, "控件为null");
            return;
        }
        view.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏控件
     *
     * @param view
     */
    private void hideView(View view) {
        if (view == null) {
            TourCooLogUtil.e(TAG, "控件为null");
            return;
        }
        view.setVisibility(View.GONE);
    }

    /**
     * 获取我的购物车列表
     */
    private void getMyShoppingCarList() {
        ApiRepository.getInstance().getMyShoppingCarList().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    mSettleEntity = parseGoodsList(entity.data);
                                    showGoodsList(mSettleEntity);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        showErrorLayout();
                    }
                });
    }


    private SettleEntity parseGoodsList(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            LogUtils.e(TAG, info);
            return JSON.parseObject(info, SettleEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    /**
     * 显示购物车列表
     */
    private void showGoodsList(SettleEntity settleEntity) {
        if (settleEntity == null || settleEntity.getGoods_list() == null) {
            showErrorLayout();
            return;
        }
        List<Goods> goodsList = settleEntity.getGoods_list();
        if (!goodsList.isEmpty()) {
            mStatusLayoutManager.showSuccessLayout();
            mShoppingCartAdapter.setNewData(goodsList);
            mShoppingCartAdapter.notifyDataSetChanged();
            showBottomMoney(settleEntity);
            int count = 0;
            for (Goods goods : goodsList) {
                count += goods.getTotal_num();
            }
            mMainTabActivity.showRedDot(count);
            showView(llBottomLayout);
        } else {
            hideView(llBottomLayout);
            showEmptyLayout();
            mMainTabActivity.showRedDot(0);
        }

    }

    /**
     * 显示加减控件的数量
     */
    private void showTextNumber(EditText editText, String count) {
        editText.setText(count);
    }


    /**
     * 购物车减
     *
     * @param goodsId
     * @param skuId
     */
    private void reduceGoods(int goodsId, String skuId) {
        ApiRepository.getInstance().reduceGoods(goodsId, skuId).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity<GoodsCount>>() {
                    @Override
                    public void onRequestNext(BaseEntity<GoodsCount> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    TourCooLogUtil.i("购物车减", entity);
                                    refreshShoppingCar();
                                } else {
                                    ToastUtil.showFailed(entity.msg);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 请求添加商品接口
     */
    private void addGoods(int goodsId, int count, String skuId) {
        ApiRepository.getInstance().addGoods(goodsId, count, skuId).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity<GoodsCount>>() {
                    @Override
                    public void onRequestNext(BaseEntity<GoodsCount> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    refreshShoppingCar();
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 显示底部合计金额
     */
    private void showBottomMoney(SettleEntity settleEntity) {
        String payPrice = "￥0.00";
        if (settleEntity == null) {
            tvTotalMoneyAmount.setText(payPrice);
            return;
        }
        payPrice = "￥" + settleEntity.getOrder_total_price();
        tvTotalMoneyAmount.setText(payPrice);
    }


    /**
     * 根据商品id删除商品
     *
     * @param goodsId
     * @param skuId
     */
    private void deleteGoods(int goodsId, String skuId) {
        ApiRepository.getInstance().deleteGoods(goodsId, skuId).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity<GoodsCount>>() {
                    @Override
                    public void onRequestNext(BaseEntity<GoodsCount> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    //刷新购物车
                                    refreshShoppingCar();
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 刷新购物车
     */
    private void refreshShoppingCar() {
        if (AccountInfoHelper.getInstance().isLogin()) {
            getMyShoppingCarList();
        }
    }

    /**
     * 不显示loading对话框
     */
    public void refreshShoppingCarNoDialog() {
        if (AccountInfoHelper.getInstance().isLogin()) {
            ApiRepository.getInstance().getMyShoppingCarList().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                    subscribe(new BaseObserver<BaseEntity>() {
                        @Override
                        public void onRequestNext(BaseEntity entity) {
                            if (entity != null) {
                                if (entity.code == CODE_REQUEST_SUCCESS) {
                                    if (entity.data != null) {
                                        mSettleEntity = parseGoodsList(entity.data);
                                        showGoodsList(mSettleEntity);
                                        refreshLayout.finishRefresh(true);
                                    }
                                } else {
                                    ToastUtil.showFailed(entity.msg);
                                    refreshLayout.finishRefresh(false);
                                }
                            }
                        }

                        @Override
                        public void onRequestError(Throwable e) {
                            super.onRequestError(e);
                            showErrorLayout();
                        }
                    });
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        refreshShoppingCarNoDialog();
    }


    public void showEmptyLayout() {
        mShoppingCartAdapter.getData().clear();
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager.showEmptyLayout();
        }
        hideView(llBottomLayout);
    }


    public void showErrorLayout() {
        if (mStatusLayoutManager != null) {
            mStatusLayoutManager.showErrorLayout();
        }
        hideView(llBottomLayout);
    }


    /**
     * 结算后跳转到结算页面显示
     *
     * @param settleEntity
     */
    private void skipOrderSettleDetail(SettleEntity settleEntity) {
        boolean skipEnable = settleEntity != null && settleEntity.getGoods_list() != null;
        if (!skipEnable) {
            ToastUtil.showFailed("未获取到商品信息");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SETTLE, settleEntity);
        intent.putExtra(EXTRA_SETTLE_TYPE, SETTLE_TYPE_CAR);
        intent.setClass(mContext, OrderSettleDetailActivity.class);
        startActivity(intent);
    }


}
