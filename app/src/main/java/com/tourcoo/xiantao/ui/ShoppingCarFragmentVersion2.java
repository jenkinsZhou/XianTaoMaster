package com.tourcoo.xiantao.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aries.ui.view.tab.widget.MsgView;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.BaseShoppingCartAdapter2;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleTourCoolFragment;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.button.AnimShopButton;
import com.tourcoo.xiantao.core.widget.button.IOnAddDelListener;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;
import com.tourcoo.xiantao.event.TabChangeEvent;
import com.tourcoo.xiantao.helper.ShoppingCar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月18日13:05
 * @Email: 971613168@qq.com
 */
public class ShoppingCarFragmentVersion2 extends BaseTitleTourCoolFragment implements View.OnClickListener {
    private BaseShoppingCartAdapter2 mShoppingCartAdapter;
    private MainTabActivity mMainTabActivity;
    private TextView tvTotalMoneyAmount;

    /**
     * 底部编辑结算栏
     */
    private RelativeLayout llBottomLayout;

    /**
     * 空购物车
     */
    private View emptyGoodsView;

    private StatusLayoutManager mStatusLayoutManager;
    /**
     * 购物车列表
     */
    private RecyclerView rvGoods;
    /**
     * 购物车中的商品
     */
    private List<GoodsEntity> mGoodsList = new ArrayList<>();

    private ShoppingCar shoppingCar;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_shopping_cart_version2;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initView();
        EventBus.getDefault().register(this);
    }


    private void initView() {
        mMainTabActivity = (MainTabActivity) mContext;
        llBottomLayout = mContentView.findViewById(R.id.llCart);
        tvTotalMoneyAmount = mContentView.findViewById(R.id.tvTotalMoneyAmount);
        rvGoods = mContentView.findViewById(R.id.rvGoods);
        emptyGoodsView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, null, false);
        emptyGoodsView.findViewById(R.id.tvGoShopping).setOnClickListener(this);
        rvGoods.setLayoutManager(new LinearLayoutManager(mContext));
        shoppingCar = ShoppingCar.getInstance();
        initStatusLayout();
        initAdapter();
        showBottomToolBarByCondition();
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


    private void initAdapter() {
        mShoppingCartAdapter = new BaseShoppingCartAdapter2(mGoodsList) {
            @Override
            protected void convert(BaseViewHolder helper, GoodsEntity item) {
                helper.setImageResource(R.id.ivGoodsIcon, R.mipmap.ic_orange);
                helper.setText(R.id.tvGoodsName, TourCoolUtil.getStringNotNull(item.goodsName));
                helper.setText(R.id.tvGoodsLabel, TourCoolUtil.getStringNotNull(item.goodsLabels));
                helper.setText(R.id.tvGoodsPrice, "￥" + item.goodsCurrentPrice);
                helper.setText(R.id.tvGoodsSpec, TourCoolUtil.getStringNotNull(item.goodsSpec));
                initAddDelButtonListener(helper, item);
                intDeleteListener(helper, item);
            }
        };
        mShoppingCartAdapter.bindToRecyclerView(rvGoods);
        mShoppingCartAdapter.setEmptyView(emptyGoodsView);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("购物车");
    }

    /**
     * 显示购物车商品数量
     *
     * @param count
     */
    private void showGoodsCount(int count) {
        if (mMainTabActivity != null) {
            if(count == 0){
          MsgView msgView =  mMainTabActivity.mTabLayout.getMsgView(2);
                msgView.setVisibility(View.GONE);
            }else {
                mMainTabActivity.mTabLayout.showMsg(2, count);
            }
        }
    }


    /**
     * 显示金额
     */
    private void showBottomMoney() {
        //计算金额
        double money = shoppingCar.getTotalMoneyBySelect(mShoppingCartAdapter.getData());
        //显示金额
        showTotal(money);
    }

    /**
     * 底部结算栏金额显示并决定是否显示凑单栏
     *
     * @param totalMoney
     */
    private void showTotal(double totalMoney) {
        String money = "￥" + totalMoney;
        tvTotalMoneyAmount.setText(money);
    }

    /**
     * 刷新购物车
     */
    private void refreshGoodsList() {
        if (mShoppingCartAdapter != null) {
            mShoppingCartAdapter.notifyDataSetChanged();
        }
    }


    /**
     * @param tabChangeEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onTabChangeEvent(TabChangeEvent tabChangeEvent) {
        //todo 执行购物车显示逻辑
        showGoodsByCondition();
        //根据条件显示底部带单栏
        showBottomToolBarByCondition();
        //显示底部栏金额
        showBottomMoney();
        //移除粘性事件
        if (tabChangeEvent != null) {
            EventBus.getDefault().removeStickyEvent(tabChangeEvent);
        }
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /**
     * 显示横向购物车列表数据
     */
    private void showGoodsByCondition() {
        mShoppingCartAdapter.getData().clear();
        mShoppingCartAdapter.getData().addAll(ShoppingCar.getInstance().getShoppingCar());
        if (ShoppingCar.getInstance().isEmpty()) {
        } else {
            mStatusLayoutManager.showSuccessLayout();
        }
        mShoppingCartAdapter.notifyDataSetChanged();
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
    private void initAddDelButtonListener(BaseViewHolder helper, GoodsEntity item) {
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
                showBottomMoney();
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
                showBottomMoney();
            }

            @Override
            public void onDelFailed(int count, FailType failType) {
                ToastUtil.show("当前商品不能再减少了哦");
            }
        });
    }


    /**
     * 初始化侧滑删除点击事件
     */
    private void intDeleteListener(BaseViewHolder helper, GoodsEntity item) {
        Button btnDelete = helper.getView(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.select = false;
                item.goodsCount = 0;
                mShoppingCartAdapter.remove(helper.getLayoutPosition());
                //从购物车中移除该商品
                shoppingCar.deleteGoods(item);
                refreshGoodsList();
                showGoodsCount(shoppingCar.getGoodsCount());
                showBottomMoney();
                showBottomToolBarByCondition();
            }
        });
    }



    /**
     * 显示根据条件显示底部导航栏
     */
    private void showBottomToolBarByCondition() {
        if (mShoppingCartAdapter.getData().isEmpty()) {
            hideView(llBottomLayout);
        } else {
            showView(llBottomLayout);
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


}
