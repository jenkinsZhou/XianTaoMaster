package com.tourcoo.xiantao.ui;

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
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.BaseShoppingCartAdapter2;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleTourCoolFragment;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsDetailEntity;
import com.tourcoo.xiantao.entity.settle.SettleEntity;
import com.tourcoo.xiantao.entity.event.TabChangeEvent;
import com.tourcoo.xiantao.helper.GoodsCount;
import com.tourcoo.xiantao.helper.ShoppingCar;
import com.tourcoo.xiantao.listener.OnAddDelCallback;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
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
    private SmartRefreshLayout refreshLayout;
    private int current = 1;
    private OnAddDelCallback mOnAddDelCallback;

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
    /**
     * 购物车中的商品
     */
    private List<GoodsDetailEntity> mGoodsList = new ArrayList<>();

    private ShoppingCar shoppingCar;

    private RelativeLayout rlRootView;

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
        mMainTabActivity = (MainTabActivity) mContext;
        rlRootView = mContentView.findViewById(R.id.rlRootView);
        llBottomLayout = mContentView.findViewById(R.id.llCart);
        tvTotalMoneyAmount = mContentView.findViewById(R.id.tvTotalMoneyAmount);
        rvGoods = mContentView.findViewById(R.id.rvGoods);
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, null, false);
        emptyView.findViewById(R.id.tvGoShopping).setOnClickListener(this);
        rvGoods.setLayoutManager(new LinearLayoutManager(mContext));
        shoppingCar = ShoppingCar.getInstance();
        initStatusLayout();
        initAdapter();
        if(AccountInfoHelper.getInstance().isLogin()){
            getMyShoppingCarList();
        }
    }

    /**
     * 初始化多状态布局
     */
    private void initStatusLayout() {
        StatusLayoutManager.Builder builder = new StatusLayoutManager.Builder(rlRootView);
        builder.setEmptyLayout(emptyView);
        builder.setEmptyClickViewID(R.id.tvGoShopping);
        builder.setOnStatusChildClickListener(new OnStatusChildClickListener() {
            @Override
            public void onEmptyChildClick(View view) {
                ToastUtil.showSuccess("点击了");
                mMainTabActivity.mTabLayout.setCurrentTab(1);
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
        mShoppingCartAdapter = new BaseShoppingCartAdapter2() {
            @Override
            protected void convert(BaseViewHolder helper, Goods goods) {
                RoundedImageView ivGoodsIcon = helper.getView(R.id.ivGoodsIcon);
                GlideManager.loadCircleImg(goods.getImage(), ivGoodsIcon);
                EditText etNumber = helper.getView(R.id.etNumber);
                helper.setText(R.id.tvGoodsName, TourCoolUtil.getStringNotNull(goods.getGoods_name()));
//                helper.setText(R.id.tvGoodsLabel, TourCoolUtil.getStringNotNull(item.goodsLabels));
                helper.setText(R.id.tvGoodsPrice, "￥" + goods.getGoods_price());
//                helper.setText(R.id.tvGoodsSpec, TourCoolUtil.getStringNotNull(item.goodsSpec));
                initAddDelButtonListener(helper, goods);
                mOnAddDelCallback = new OnAddDelCallback() {
                    @Override
                    public void showNumber(int goodsId, int number) {
                        Goods currentGoods = mShoppingCartAdapter.getData().get(helper.getLayoutPosition());
                        //根据服务器回调回来的数量来显示
                        currentGoods.setTotal_num(number);
                        showTextNumber(etNumber, number + "");
                        mMainTabActivity.showRedDot(number);
                        showBottomToolBarByCondition();
                        showBottomMoney();
                    }
                };
                intSwipeDeleteListener(helper, goods);
            }
        };
        mShoppingCartAdapter.bindToRecyclerView(rvGoods);
//        mShoppingCartAdapter.setEmptyView(emptyView);
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
            if (count == 0) {
                MsgView msgView = mMainTabActivity.mTabLayout.getMsgView(2);
                msgView.setVisibility(View.GONE);
            } else {
                mMainTabActivity.mTabLayout.showMsg(2, count);
            }
        }
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
      /*  //todo 执行购物车显示逻辑
//        showGoodsByCondition();
        //根据条件显示底部带单栏
        showBottomToolBarByCondition();
        //显示底部栏金额
        showBottomMoney();
        //移除粘性事件
        if (tabChangeEvent != null) {
            EventBus.getDefault().removeStickyEvent(tabChangeEvent);
        }*/
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
//        mShoppingCartAdapter.getData().addAll(ShoppingCar.getInstance().getShoppingCar());
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
    private void initAddDelButtonListener(BaseViewHolder helper, Goods item) {
        /**
         * 减控件
         */
        ImageView ivGoodsReduce = helper.getView(R.id.ivGoodsReduce);
        EditText etNumber = helper.getView(R.id.etNumber);
        showTextNumber(etNumber, item.getTotal_num() + "");
        ivGoodsReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TourCooLogUtil.i(TAG, TAG + "商品:" + item.getTotal_num());
                if (item.getTotal_num() <= 1) {
                    ToastUtil.show("当前商品不能再减了哦");
                    return;
                }
                reduceGoods(23, "48");
            }
        });
        /**
         * 加控件
         */
        ImageView ivGoodsAdd = helper.getView(R.id.ivGoodsAdd);

        ivGoodsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("点击了+");
                addGoods(23, 1, "48");
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
                deleteGoods(goods.getGoods_id(),"48");
                showBottomMoney();
                showBottomToolBarByCondition();
                showEmptyViewByCondition();
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

    /**
     * 获取我的购物车列表
     */
    private void getMyShoppingCarList() {
        ApiRepository.getInstance().getMyShoppingCarList().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    showGoodsList(parseGoodsList(entity.data));
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
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
            mStatusLayoutManager.showErrorLayout();
            return;
        }
        List<Goods> goodsList = settleEntity.getGoods_list();
        if (!goodsList.isEmpty()) {
            mShoppingCartAdapter.setNewData(goodsList);
            mShoppingCartAdapter.notifyDataSetChanged();
        }
        showBottomToolBarByCondition();
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
//                                    showNumber(,entity.data.getCart_total_num());
                                    if (mOnAddDelCallback != null) {
                                        mOnAddDelCallback.showNumber(34, entity.data.getCart_total_num());
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 请求添加商品接口" 23 48"
     */
    private void addGoods(int goodsId, int count, String skuId) {
        ApiRepository.getInstance().addGoods(goodsId, count, skuId).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity<GoodsCount>>() {
                    @Override
                    public void onRequestNext(BaseEntity<GoodsCount> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    if (mOnAddDelCallback != null) {
                                        mOnAddDelCallback.showNumber(34, entity.data.getCart_total_num());
                                    }
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
    private void showBottomMoney() {
        List<Goods> goodsList = mShoppingCartAdapter.getData();
        String yuan = "￥";
        double totalMoney = 0.00;
        BigDecimal bd = new BigDecimal(totalMoney);
        totalMoney = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String value = yuan + totalMoney;
        if (!goodsList.isEmpty()) {
            for (Goods goodsDetailEntity : goodsList) {
                TourCooLogUtil.i(TAG, "计算了一次:数量：" + goodsDetailEntity.getTotal_num());
                totalMoney += goodsDetailEntity.getGoods_price() * goodsDetailEntity.getTotal_num();
                TourCooLogUtil.i(TAG, "计算了一次:" + totalMoney);
            }
        }
        value = yuan + totalMoney;
        tvTotalMoneyAmount.setText(value);
    }


    private void showEmptyViewByCondition() {
        if (mShoppingCartAdapter.getData().isEmpty()) {
            mStatusLayoutManager.showEmptyLayout();
        } else {
            mStatusLayoutManager.showSuccessLayout();
        }
    }


    /**
     * 根据商品id删除商品
     * @param goodsId
     * @param skuId
     */
    private void deleteGoods(int goodsId,  String skuId) {
        ApiRepository.getInstance().deleteGoods(goodsId, skuId).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity<GoodsCount>>() {
                    @Override
                    public void onRequestNext(BaseEntity<GoodsCount> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    TourCooLogUtil.i(TAG,entity.data);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


}
