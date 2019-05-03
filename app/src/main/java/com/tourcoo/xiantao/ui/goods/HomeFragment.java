package com.tourcoo.xiantao.ui.goods;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.XianTaoApplication;
import com.tourcoo.xiantao.adapter.GoodsGridAdapter;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleFragment;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.NetworkUtil;
import com.tourcoo.xiantao.core.frame.util.SizeUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.helper.TitleBarViewHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.StatusBarUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.banner.BannerBean;
import com.tourcoo.xiantao.entity.goods.GoodsDetailEntity;
import com.tourcoo.xiantao.entity.HomeInfoBean;
import com.tourcoo.xiantao.entity.banner.BannerDetail;
import com.tourcoo.xiantao.entity.goods.HomeGoodsBean;
import com.tourcoo.xiantao.entity.news.NewsBean;
import com.tourcoo.xiantao.helper.ShoppingCar;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.base.WebViewActivity;
import com.tourcoo.xiantao.ui.msg.HomeNewsDetailActivity;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.bingoogolapple.bgabanner.BGABanner;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.adapter.AddressInfoAdapter.ADDRESS_DEFAULT;
import static com.tourcoo.xiantao.core.common.RequestConfig.BASE_URL;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :zhoujian
 * @description : 首页
 * @company :途酷科技
 * @date 2019年03月06日上午 10:12
 * @Email: 971613168@qq.com
 */
public class HomeFragment extends BaseTitleFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    private SmartRefreshLayout mRefreshLayout;
    private Handler mHandler = new Handler();
    private BGABanner banner;
    private RecyclerView rvHome;
    public static final String EXTRA_GOODS_ID = "EXTRA_GOODS_ID";
    private GoodsGridAdapter mGoodsGridAdapter;
    private List<HomeGoodsBean> mGuessLikeGoodsList = new ArrayList<>();
    private MainTabActivity mMainTabActivity;
    private StatusLayoutManager statusLayoutManager;
    private List<String> bannerImageList = new ArrayList<>();
    private List<BannerBean> mBannerBeanList = new ArrayList<>();

    private LinearLayout rlContentView;

    /**
     * 没有更多数据足布局
     */
    private View footView;
    private ViewFlipper homeViewFlipper;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initView();
        initBanner();
        initAdapter();
        initTitle();
        statusLayoutManager.showLoadingLayout();
        initGoodsItemClick();
        getHomeInfo();
    }

    @Override
    public void loadData() {
        super.loadData();
        //获取收货地址
        if (AccountInfoHelper.getInstance().isLogin()) {
            getMyAddressList();
            //请求购物车数量
            mMainTabActivity.getTotalNum();
        }
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


    private void initView() {
        mMainTabActivity = (MainTabActivity) mContext;
        mContentView.findViewById(R.id.rlSearchLayout).setOnClickListener(this);
        homeViewFlipper = mContentView.findViewById(R.id.homeViewFlipper);
        rlContentView = mContentView.findViewById(R.id.rlContentView);
        rvHome = mContentView.findViewById(R.id.rvHome);
        footView = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        rvHome.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRefreshLayout = mContentView.findViewById(R.id.refreshLayoutHome);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        setupStatusLayoutManager();
    }

    /**
     * 初始化商品适配器
     */
    private void initAdapter() {
        mGoodsGridAdapter = new GoodsGridAdapter();
        mGoodsGridAdapter.bindToRecyclerView(rvHome);
        mGoodsGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                doAddGoods(mGoodsGridAdapter.getData().get(position));
                TourCooLogUtil.i(TAG, "点击了:" + position);
            }
        });
    }

    /**
     * 初始化banner
     */
    private void initBanner() {
     /*   List<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429289379&di=203535d4bccdb92b0d491e1b17fe5dbc&imgtype=0&src=http%3A%2F%2Fimg.19yxw.com%2Fwy%2Fupdate%2F20151012%2F2015101213124051007.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429511329&di=dbf86bef50e98ebd74969f414f64c8d9&imgtype=0&src=http%3A%2F%2Fpic62.nipic.com%2Ffile%2F20150318%2F5624330_150949597000_2.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429548089&di=093619d985005df0606ec6840c21a2f6&imgtype=0&src=http%3A%2F%2Fi3.17173.itc.cn%2F2011%2Fnews%2F2011%2F07%2F26%2Fy0726db06s.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429569807&di=02b9a0d6ed0f6411782d4b2d3917507c&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7a4a58f9735639e155b0055d024ede1270b31e4f16842-Ymvyre_fw658");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429569807&di=02b9a0d6ed0f6411782d4b2d3917507c&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7a4a58f9735639e155b0055d024ede1270b31e4f16842-Ymvyre_fw658");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429569807&di=02b9a0d6ed0f6411782d4b2d3917507c&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7a4a58f9735639e155b0055d024ede1270b31e4f16842-Ymvyre_fw658");*/
        if (banner == null) {
            banner = mContentView.findViewById(R.id.bgaBanner);
            if (banner == null) {
                return;
            }
            banner.setAdapter(new BGABanner.Adapter() {
                @Override
                public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
                    GlideManager.loadImg(model, (ImageView) itemView);
                }
            });
            banner.setDelegate(new BGABanner.Delegate() {
                @Override
                public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                    if (mBannerBeanList.size() <= position) {
                        return;
                    }
                    BannerBean bannerBean = mBannerBeanList.get(position);
                    if (bannerBean != null) {
                        getBannerDetail(bannerBean.getId() + "");
                    }
                }
            });
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
            params.height = XianTaoApplication.getImageHeight();
            TourCooLogUtil.i(TAG, "banner:" + params.height + ";width:" + SizeUtil.getScreenWidth());
        }
    }


 /*   private void loadGuessLike() {
        int size = 21;
        GoodsEntity goodsEntity;
        for (int i = 0; i < size; i++) {
            goodsEntity = new GoodsEntity(System.currentTimeMillis() + i);
            goodsEntity.goodsSpec = "10" + i + "g";
            goodsEntity.goodsCurrentPrice = 10 + i + 0.9;
            goodsEntity.goodsLabels = "维生素C 很甜";
            goodsEntity.goodsName = "西班牙新鲜杻丁橙" + i;
            mGuessLikeGoodsList.add(goodsEntity);
        }
        mGoodsGridAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSearchLayout:
                TourCoolUtil.startActivity(mContext, SearchGoodsActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        doRefresh();
    }

    /**
     * 执行刷新逻辑
     */
    private void doRefresh() {
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setNoMoreData(false);
        mGoodsGridAdapter.removeFooterView(footView);
        getHomeInfoDelay();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        TourCooLogUtil.w(TAG, "执行加载更多Requested");
        doLoadMore();
    }

    /**
     * 执行加载更多逻辑
     */
    private void doLoadMore() {
        mGoodsGridAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore();
        if (mGoodsGridAdapter.getData().size() > 30) {
            mRefreshLayout.setEnableLoadMore(false);
            mRefreshLayout.setNoMoreData(true);
            mGoodsGridAdapter.addFooterView(footView);
        } else {
            mGoodsGridAdapter.loadMoreComplete();
        }
        TourCooLogUtil.i(TAG, "当前商品数量:" + mGoodsGridAdapter.getData().size());
    }

    /**
     * 添加商品并显示
     */
    private void doAddGoods(GoodsDetailEntity goodsDetailEntity) {
        if (goodsDetailEntity == null) {
            TourCooLogUtil.e(TAG, "商品为null");
            return;
        }
        ShoppingCar.getInstance().addGoods(goodsDetailEntity);
        showGoodsCount(ShoppingCar.getInstance().getGoodsCount());
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


    private void getHomeInfo() {
        ApiRepository.getInstance().getHomeBanner().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity == null) {
                            mRefreshLayout.finishRefresh(false);
                            ToastUtil.showFailed("服务器异常");
                            statusLayoutManager.showErrorLayout();
                            return;
                        }
                        if (entity.code == CODE_REQUEST_SUCCESS) {
                            statusLayoutManager.showSuccessLayout();
                            mRefreshLayout.finishRefresh(true);
                            //todo 显示主页
                            showHomeInfo(parseHomeInfo(entity.data));
                        } else {
                            mRefreshLayout.finishRefresh(false);
                            ToastUtil.showFailed(entity.msg);
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        statusLayoutManager.showErrorLayout();
                        mRefreshLayout.finishRefresh(false);
                    }
                });
    }


    private HomeInfoBean parseHomeInfo(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String homeInfo = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + homeInfo);
            return JSON.parseObject(homeInfo, HomeInfoBean.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    private void showHomeInfo(HomeInfoBean homeInfoBean) {
        boolean loadFailed = homeInfoBean == null || homeInfoBean.getBanner() == null ||
                homeInfoBean.getGoods() == null || homeInfoBean.getNews() == null;
        if (loadFailed) {
            statusLayoutManager.showErrorLayout();
            return;
        }
        bannerImageList.clear();
        mBannerBeanList.clear();
        List<BannerBean> bannerBeanList = homeInfoBean.getBanner();
        mBannerBeanList.addAll(bannerBeanList);
        String url;
        for (BannerBean bannerBean : bannerBeanList) {
            url = TourCooUtil.getUrl(bannerBean.getImage());
            TourCooLogUtil.i(TAG, "bannerImageList:" + url);
            bannerImageList.add(url);
        }
        List<HomeGoodsBean> homeGoodsBeanList = homeInfoBean.getGoods();
        if (homeGoodsBeanList != null && !homeGoodsBeanList.isEmpty()) {
            mGoodsGridAdapter.setNewData(homeGoodsBeanList);
        }
        mGuessLikeGoodsList.addAll(homeInfoBean.getGoods());
        mGoodsGridAdapter.notifyDataSetChanged();
        banner.setData(bannerImageList, null);
        loadHomeViewFlipperData(homeInfoBean.getNews());
    }


    private void setupStatusLayoutManager() {
        statusLayoutManager = new StatusLayoutManager.Builder(rlContentView)
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
//                        statusLayoutManager.showLoadingLayout();
//                        getData(1000);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        getHomeInfoDelay();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        statusLayoutManager.showLoadingLayout();
                        getHomeInfoDelay();
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

    @Override
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }

    private void getHomeInfoDelay() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getHomeInfo();
            }
        }, 500);
    }


    /**
     * 获取banner详情
     */
    private void getBannerDetail(String id) {
        ApiRepository.getInstance().getBannerDetail(id).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<BannerDetail>>() {
                    @Override
                    public void onRequestNext(BaseEntity<BannerDetail> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                doSkipBannerDetail(entity.data);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }

    /**
     * 跳转banner详情
     */
    private void doSkipBannerDetail(BannerDetail bannerDetail) {
        if (bannerDetail == null) {
            ToastUtil.showFailed("未获取到图片信息");
            return;
        }
        String url = BASE_URL + bannerDetail.getImage();
        WebViewActivity.start(mContext, url, false);
    }


    /**
     * 加载滚动新闻
     *
     * @param newsBeanList
     */
    private void loadHomeViewFlipperData(List<NewsBean> newsBeanList) {
        if (newsBeanList == null || newsBeanList.isEmpty()) {
            return;
        }
        View contentLayout;
//        ImageView ivNewsIcon;
        TextView tvNewsContent;
        for (NewsBean newsBean : newsBeanList) {
            if (newsBean == null || TextUtils.isEmpty(newsBean.getName())) {
                continue;
            }
            contentLayout = View.inflate(mContext, R.layout.layout_news, null);
//            ivNewsIcon = contentLayout.findViewById(R.id.icBulletin);
            tvNewsContent = contentLayout.findViewById(R.id.tvNewsContent);
            tvNewsContent.setText(newsBean.getName());
            tvNewsContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), HomeNewsDetailActivity.class);
                    intent.putExtra("id", newsBean.getId());
                    startActivity(intent);
                }
            });
            homeViewFlipper.addView(contentLayout);
            //todo
//            homeViewFlipper.addView(test);
        }
    }


    private void skipGoodsDetail(int goodsId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_GOODS_ID, goodsId);
        intent.setClass(mContext, GoodsDetailActivity.class);
        startActivity(intent);
    }


    private void initGoodsItemClick() {
        mGoodsGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeGoodsBean homeGoodsBean = mGoodsGridAdapter.getItem(position);
                if (homeGoodsBean == null) {
                    return;
                }
                skipGoodsDetail(homeGoodsBean.getGoods_id());
            }
        });
    }


    /**
     * 获取收货地址
     */

    private void getMyAddressList() {
        ApiRepository.getInstance().myAddressList().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<List<AddressEntity>>>() {
                    @Override
                    public void onRequestNext(BaseEntity<List<AddressEntity>> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                if (!entity.data.isEmpty()) {
                                    AccountInfoHelper.getInstance().setDefaultAddress(getDefaultAddress(entity.data));
                                    mRefreshLayout.finishRefresh(true);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        TourCooLogUtil.e(TAG, TAG + "异常:" + e.toString());
                    }
                });
    }


    private AddressEntity getDefaultAddress(List<AddressEntity> addressBeanList) {
        if (addressBeanList == null || addressBeanList.isEmpty()) {
            return null;
        }
        for (AddressEntity addressEntity : addressBeanList) {
            if (addressEntity.getIsdefault().equals(ADDRESS_DEFAULT)) {
                TourCooLogUtil.i(TAG, TAG + ":" + "默认地址设置成功");
                return addressEntity;
            }
        }
        return addressBeanList.get(0);
    }


}
