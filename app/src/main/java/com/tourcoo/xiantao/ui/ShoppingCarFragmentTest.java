package com.tourcoo.xiantao.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.BaseShoppingCartAdapter1;
import com.tourcoo.xiantao.adapter.GoodsGridAdapter;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleTourCoolFragment;
import com.tourcoo.xiantao.core.frame.delegate.MainTabDelegate;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.button.AnimShopButton;
import com.tourcoo.xiantao.core.widget.button.IOnAddDelListener;
import com.tourcoo.xiantao.core.widget.core.util.SizeUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.GoodsEntity;
import com.tourcoo.xiantao.event.TabChangeEvent;
import com.tourcoo.xiantao.helper.ShoppingCarInstance;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * @author :zhoujian
 * @description :购物车fragment版本一
 * @company :翼迈科技
 * @date 2019年 04月 14日 17时03分
 * @Email: 971613168@qq.com
 */
public class ShoppingCarFragmentTest extends BaseTitleTourCoolFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    private RecyclerView rvGuessLike;
    private RecyclerView mGoodsRecyclerView;
    private List<GoodsEntity> mGoodsList = new ArrayList<>();
    private BaseShoppingCartAdapter1 mShoppingCartAdapter;
    private List<GoodsEntity> mGuessLikeGoodsList = new ArrayList<>();
    private GoodsGridAdapter mGoodsGridAdapter;
    private SmartRefreshLayout refreshLayout;
    private View footView;
    private MainTabActivity mMainTabActivity;
    private CheckBox cBoxAllGoods;
    private ShoppingCarInstance shoppingCar;
    private TextView tvTotalMoneyAmount;
    /**
     * 还差xxx包邮
     */
    private TextView tvPackagePrice;

    /**
     * 空购物车
     */
    private View emptyGoodsView;
    /**
     * 默认处于编辑状态
     */
    private boolean editFlag = true;

    /**
     * 凑单栏
     */
    private RelativeLayout rlCollectBills;
    /**
     * 底部编辑结算栏
     */
    private RelativeLayout llBottomLayout;

    private RelativeLayout rlTotalMoney;
    private TextView tvDeleteGoods;
    private TextView tvSettleAccounts;
    private StatusLayoutManager mStatusLayoutManager;
    private MainTabDelegate mTabDelegate;

    private double limit = 100.00;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initView();
        mTabDelegate = ((MainTabActivity) mContext).getTabDelegate();
        EventBus.getDefault().register(this);
        initLoadGuessLike();
        mShoppingCartAdapter.notifyDataSetChanged();
        mGoodsGridAdapter.notifyDataSetChanged();
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setEnableLoadMore(true);
        cBoxAllGoods = mContentView.findViewById(R.id.cBoxAllGoods);
//        refreshLayout.setOnLoadMoreListener(this);
    }

    public static ShoppingCarFragmentTest newInstance() {
        Bundle args = new Bundle();
        ShoppingCarFragmentTest fragment = new ShoppingCarFragmentTest();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        initTitleBar(titleBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //购物车为空时，去看看
            case R.id.tvGoShopping:
                if (mTabDelegate == null) {
                    return;
                } else {
                    mTabDelegate.mTabLayout.setCurrentTab(3);
                }
                break;
            //全选或反全选按钮
            case R.id.llSelectAll:
                if (isSelectAll()) {
                    cBoxAllGoods.setChecked(false);
                    doNotSelectAll();
                } else {
                    doSelectAll();
                    cBoxAllGoods.setChecked(true);
                }
                //显示顶部和底部金额相关信息
                showTopAndBottomUI();
                break;
            default:
                break;
        }
    }


    private void initView() {
        mMainTabActivity = (MainTabActivity) mContext;
        tvTotalMoneyAmount = mContentView.findViewById(R.id.tvTotalMoneyAmount);
        mContentView.findViewById(R.id.llSelectAll).setOnClickListener(this);
        rlCollectBills = mContentView.findViewById(R.id.rlCollectBills);
        emptyGoodsView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, null, false);
        initStatusLayout();
        shoppingCar = ShoppingCarInstance.getInstance();
        emptyGoodsView.findViewById(R.id.tvGoShopping).setOnClickListener(this);
        llBottomLayout = mContentView.findViewById(R.id.llCart);
        rlTotalMoney = mContentView.findViewById(R.id.rlTotalMoney);
        tvDeleteGoods = mContentView.findViewById(R.id.tvDeleteGoods);
        tvSettleAccounts = mContentView.findViewById(R.id.tvSettleAccounts);
        refreshLayout = mContentView.findViewById(R.id.refreshLayout);
        rvGuessLike = mContentView.findViewById(R.id.rvGuessLike);
        mGoodsRecyclerView = mContentView.findViewById(R.id.rvGoods);
        mGoodsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        rvGuessLike.setLayoutManager(new GridLayoutManager(mContext, 3));
        tvPackagePrice = mContentView.findViewById(R.id.tvPackagePrice);
        initAdapter();
        double money = shoppingCar.getTotalMoneyBySelect(mShoppingCartAdapter.getData());
        showTopPackPrice(money);
    }

    private void initAdapter() {
        mShoppingCartAdapter = new BaseShoppingCartAdapter1(mGoodsList) {
            @Override
            protected void convert(BaseViewHolder helper, GoodsEntity item) {
                helper.setImageResource(R.id.ivGoodsIcon, R.mipmap.ic_orange);
                helper.setText(R.id.tvGoodsName, TourCoolUtil.getStringNotNull(item.goodsName));
                helper.setText(R.id.tvGoodsLabel, TourCoolUtil.getStringNotNull(item.goodsLabels));
                helper.setText(R.id.tvGoodsPrice, "￥" + item.goodsCurrentPrice);
                helper.setChecked(R.id.cBoxGoods, item.select);
                helper.setText(R.id.tvGoodsSpec, TourCoolUtil.getStringNotNull(item.goodsSpec));
                helper.addOnClickListener(R.id.tvCBox);
                AnimShopButton addGoodsButton = helper.getView(R.id.addGoodsButton);
                addGoodsButton.setCount(item.goodsCount);
                addGoodsButton.setOnAddDelListener(new IOnAddDelListener() {
                    @Override
                    public void onAddSuccess(int count) {
                        int position = helper.getLayoutPosition();
                        GoodsEntity goodsEntity = mShoppingCartAdapter.getData().get(position);
                        TourCooLogUtil.i("点击了位置:" + position + "的+");
                        shoppingCar.addGoods(goodsEntity);
                        showGoodsCount(shoppingCar.getGoodsCount());
                        TourCooLogUtil.i(TAG, "当前选中的总金额:" + shoppingCar.getTotalMoneyBySelect(mShoppingCartAdapter.getData()));
                        //显示顶部和底部金额相关信息
                        showTopAndBottomUI();
                    }

                    @Override
                    public void onAddFailed(int count, FailType failType) {

                    }

                    @Override
                    public void onDelSuccess(int count) {
                        int position = helper.getLayoutPosition();
                        GoodsEntity goodsEntity = mShoppingCartAdapter.getData().get(position);
                        //先设置当前适配器中商品数量然后同步到购物车
                        goodsEntity.setGoosCount(count);
                        shoppingCar.setGoodsCount(goodsEntity);
                        //显示购物车中数量
                        showGoodsCount(shoppingCar.getGoodsCount());
                        TourCooLogUtil.d(TAG, "当前选中的总金额:" + shoppingCar.getTotalMoneyBySelect(mShoppingCartAdapter.getData()));
                        //显示顶部和底部金额相关信息
                        showTopAndBottomUI();
                    }

                    @Override
                    public void onDelFailed(int count, FailType failType) {
                        ToastUtil.show("当前商品不能再减少了哦");
                    }
                });
            }
        };
        mShoppingCartAdapter.setEmptyView(emptyGoodsView);
        mShoppingCartAdapter.bindToRecyclerView(mGoodsRecyclerView);
        mGoodsGridAdapter = new GoodsGridAdapter(mGuessLikeGoodsList);
        mGoodsGridAdapter.bindToRecyclerView(rvGuessLike);
        footView = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setRefreshHeader(new ClassicsHeader(mContext));
        initShoppingCarClickListener();
        showBottomToolBarByCondition();
        showCollectBillsByCondition();
        showTopAndBottomUI();
    }

    private void initLoadGuessLike() {
        int size = 3;
        GoodsEntity goodsEntity;
        for (int i = 0; i < size; i++) {
            goodsEntity = new GoodsEntity(System.currentTimeMillis());
            goodsEntity.goodsSpec = "10" + i + "g";
            goodsEntity.goodsCurrentPrice = 10 + i + 0.9;
            goodsEntity.goodsLabels = "维生素C 很甜";
            goodsEntity.goodsName = "西班牙新鲜杻丁橙";
            mGuessLikeGoodsList.add(goodsEntity);
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mGoodsGridAdapter.getData().clear();
                initLoadGuessLike();
                refreshLayout.finishRefresh();
                mGoodsGridAdapter.notifyDataSetChanged();
                refreshLayout.setEnableLoadMore(true);
                refreshLayout.setNoMoreData(false);
                mGoodsGridAdapter.removeFooterView(footView);
            }
        }, 100);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        TourCooLogUtil.w(TAG, "执行加载更多Requested");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initLoadGuessLike();
                mGoodsGridAdapter.notifyDataSetChanged();
                if (mGoodsGridAdapter.getData().size() > 30) {
                    refreshLayout.setEnableLoadMore(false);
                    refreshLayout.setNoMoreData(true);
                    mGoodsGridAdapter.addFooterView(footView);
                } else {
                    mGoodsGridAdapter.loadMoreComplete();
                }
                refreshLayout.finishLoadMore();
                TourCooLogUtil.i(TAG, "当前商品数量:" + mGoodsGridAdapter.getData().size());
            }
        }, 100);
    }


    /**
     * 显示根据条件显示底部导航栏
     */
    private void showBottomToolBarByCondition() {
        if (rlTotalMoney == null || tvDeleteGoods == null) {
            TourCooLogUtil.e(TAG, "null");
            return;
        }
        if (mShoppingCartAdapter.getData().isEmpty()) {
            hideView(llBottomLayout);
        } else {
            showView(llBottomLayout);
        }
        if (!editFlag) {
            rlTotalMoney.setVisibility(View.GONE);
            tvDeleteGoods.setVisibility(View.VISIBLE);
            tvSettleAccounts.setVisibility(View.GONE);
        } else {
            rlTotalMoney.setVisibility(View.VISIBLE);
            tvSettleAccounts.setVisibility(View.VISIBLE);
            tvDeleteGoods.setVisibility(View.GONE);
        }
    }


    private void initTitleBar(TitleBarView titleBar) {
        titleBar.setHeight(SizeUtil.dp2px(48));
        titleBar.setTitleMainText("购物车").setRightTextColor(TourCoolUtil.getColor(R.color.colorPrimary));
        showRightTitleByCondition(titleBar);
        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editFlag) {
                    editFlag = false;
                } else {
                    editFlag = true;
                }
                showRightTitleByCondition(titleBar);
                showBottomToolBarByCondition();
            }
        });
    }


    private void showRightTitleByCondition(TitleBarView titleBarView) {
        if (ShoppingCarInstance.getInstance().getGoodsCount() == 0) {
            titleBarView.setRightText("");
            return;
        }
        if (editFlag) {
            titleBarView.setRightText("编辑");
        } else {
            titleBarView.setRightText("完成");
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /**
     * @param tabChangeEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onTabChangeEvent(TabChangeEvent tabChangeEvent) {
        //todo 执行购物车显示逻辑
        //根据条件右上角titleBar标题
        showRightTitleByCondition(mTitleBar);
        showGoodsByCondition();
        //根据条件显示底部带单栏
        showBottomToolBarByCondition();
        //是否显示凑单列表
        showCollectBillsByCondition();
        showTopAndBottomUI();
        //移除粘性事件
        if (tabChangeEvent != null) {
            EventBus.getDefault().removeStickyEvent(tabChangeEvent);
        }
    }

    /**
     * 初始化多状态布局
     */
    private void initStatusLayout() {
        StatusLayoutManager.Builder builder = new StatusLayoutManager.Builder(mContentView);
        builder.setEmptyLayout(emptyGoodsView);
        builder.setOnStatusChildClickListener(new OnStatusChildClickListener() {
            @Override
            public void onEmptyChildClick(View view) {

            }

            @Override
            public void onErrorChildClick(View view) {

            }

            @Override
            public void onCustomerChildClick(View view) {

            }
        });
        mStatusLayoutManager = builder.build();
    }

    /**
     * 显示横向购物车列表数据
     */
    private void showGoodsByCondition() {
        mShoppingCartAdapter.getData().clear();
        mShoppingCartAdapter.getData().addAll(ShoppingCarInstance.getInstance().getShoppingCar());
        if (ShoppingCarInstance.getInstance().isEmpty()) {
        } else {
            mStatusLayoutManager.showSuccessLayout();
        }
        mShoppingCartAdapter.notifyDataSetChanged();
    }

    /**
     * 根据条件决定是否显示凑单栏
     */
    private void showCollectBillsByCondition() {
        if (mShoppingCartAdapter.getData().isEmpty()) {
            hideView(rlCollectBills);
        } else {
            showView(rlCollectBills);
        }
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
     * 初始化商品列表点击事件
     */
    private void initShoppingCarClickListener() {
        mShoppingCartAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tvCBox:
                        CheckBox checkBox = (CheckBox) adapter.getViewByPosition(position, R.id.cBoxGoods);
                        if (checkBox == null) {
                            return;
                        }
                        //一旦点击了某个商品 就移除全选状态
                        setCheckedAll(false);
                        showGoodsCheck(checkBox);
                        //根据checkbox状态，设置商品的状态(选中或未选中)
                        setGoodsSelect(mShoppingCartAdapter.getData().get(position), checkBox.isChecked());
                        //显示顶部和底部金额相关信息
                        showTopAndBottomUI();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 显示是否选中商品
     *
     * @param checkBox
     */
    private void showGoodsCheck(CheckBox checkBox) {
        if (checkBox == null) {
            return;
        }
        if (checkBox.isChecked()) {
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
        }
    }


    private boolean isSelectAll() {
        return cBoxAllGoods.isChecked();
    }

    /**
     * 设置是否全选
     *
     * @param checked
     */
    private void setCheckedAll(boolean checked) {
        cBoxAllGoods.setChecked(checked);
    }


    /**
     * 全选
     */
    private void doSelectAll() {
        List<GoodsEntity> goodsEntityList = mShoppingCartAdapter.getData();
        for (GoodsEntity goodsEntity : goodsEntityList) {
            goodsEntity.setSelect(true);
        }
        mShoppingCartAdapter.notifyDataSetChanged();
    }

    /**
     * 全不选
     */
    private void doNotSelectAll() {
        List<GoodsEntity> goodsEntityList = mShoppingCartAdapter.getData();
        for (GoodsEntity goodsEntity : goodsEntityList) {
            goodsEntity.setSelect(false);
        }
        mShoppingCartAdapter.notifyDataSetChanged();
    }


    /**
     * 添加商品并显示
     */
    private void doAddGoods(GoodsEntity goodsEntity) {
        if (goodsEntity == null) {
            TourCooLogUtil.e(TAG, "商品为null");
            return;
        }
        ShoppingCarInstance.getInstance().addGoods(goodsEntity);
        showGoodsCount(ShoppingCarInstance.getInstance().getGoodsCount());
    }

    /**
     * 显示购物车商品数量
     *
     * @param count
     */
    private void showGoodsCount(int count) {
        if (mMainTabActivity != null) {
            mMainTabActivity.mTabLayout.showMsg(2, count);
        }
    }

    /**
     * 设置某商品是否选中
     *
     * @param goodsEntity
     * @param select
     */
    private void setGoodsSelect(GoodsEntity goodsEntity, boolean select) {
        if (goodsEntity == null) {
            TourCooLogUtil.e(TAG, "商品为null");
            return;
        }
        goodsEntity.select = select;
    }

    /**
     * 底部结算栏金额显示并决定是否显示凑单栏
     *
     * @param totalMoney
     */
    private void showTotal(double totalMoney) {
        String money = "￥" + totalMoney;
        tvTotalMoneyAmount.setText(money);
        if (totalMoney >= limit) {
            hideView(rlCollectBills);
        } else {
            showView(rlCollectBills);
        }
    }

    /**
     * 显示顶部凑单栏
     *
     * @param money
     */
    private void showTopPackPrice(double money) {
        double d = limit - money;
        BigDecimal bd = new BigDecimal(d);
        d = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String text = "全场满" + limit + "元包邮 还差" + d + "元包邮";
        tvPackagePrice.setText(text);
    }


    private void showTopAndBottomUI() {
        //计算金额
        double money = shoppingCar.getTotalMoneyBySelect(mShoppingCartAdapter.getData());
        //显示金额
        showTotal(money);
        showTopPackPrice(money);
    }
}
