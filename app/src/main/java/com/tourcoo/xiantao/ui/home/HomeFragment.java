package com.tourcoo.xiantao.ui.home;

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

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.XianTaoApplication;
import com.tourcoo.xiantao.adapter.HomeGoodsGridAdapter;
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
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.banner.BannerBean;
import com.tourcoo.xiantao.entity.event.MessageEvent;
import com.tourcoo.xiantao.entity.home.HomeGoodsEntity;
import com.tourcoo.xiantao.entity.home.HomeGoodsNewBean;
import com.tourcoo.xiantao.entity.home.RecommendBean;
import com.tourcoo.xiantao.entity.message.MessageBean;
import com.tourcoo.xiantao.entity.news.NewsBean;
import com.tourcoo.xiantao.permission.PermissionManager;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.account.LoginActivity;
import com.tourcoo.xiantao.ui.goods.GoodsCategoryListActivity;
import com.tourcoo.xiantao.ui.goods.GoodsDetailActivity;
import com.tourcoo.xiantao.ui.goods.SearchGoodsActivity;
import com.tourcoo.xiantao.ui.msg.BannerDetailActivity;
import com.tourcoo.xiantao.ui.msg.HomeNewsDetailActivity;
import com.tourcoo.xiantao.ui.msg.MsgSystemActivity;
import com.tourcoo.xiantao.util.LocateHelper;
import com.tourcoo.xiantao.widget.sku.utils.ScreenUtils;
import com.trello.rxlifecycle3.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static android.app.Activity.RESULT_OK;
import static com.tourcoo.xiantao.adapter.AddressInfoAdapter.ADDRESS_DEFAULT;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.account.MineFragment.NO_LOGIN;
import static com.tourcoo.xiantao.ui.account.MineFragment.REQUEST_CODE_MESSAGE_CENTER;
import static com.tourcoo.xiantao.ui.goods.ClassifyGoodsFragment.EXTRA_CATEGORY_NAME;
import static com.tourcoo.xiantao.ui.home.WebContentInfoActivity.ERTRA_TITLE;

/**
 * @author :zhoujian
 * @description : 首页
 * @company :途酷科技
 * @date 2019年03月06日上午 10:12
 * @Email: 971613168@qq.com
 */
public class HomeFragment extends BaseTitleFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {

    private boolean refreshTag = true;
    private int currentPage = 1;
    public static final String EXTRA_PARAM = "EXTRA_PARAM";
    private AMapLocation mapLocation;
    private SmartRefreshLayout mRefreshLayout;
    private Handler mHandler = new Handler();
    private BGABanner banner;
    private RecyclerView rvHome;
    public static final String EXTRA_GOODS_ID = "EXTRA_GOODS_ID";
    private HomeGoodsGridAdapter mGoodsGridAdapter;
    private List<HomeGoodsNewBean.GoodsBean> mGoodsBeanList = new ArrayList<>();
    private MainTabActivity mMainTabActivity;
    private StatusLayoutManager statusLayoutManager;
    private List<String> bannerImageList = new ArrayList<>();
    private List<BannerBean> mBannerBeanList = new ArrayList<>();
    private LinearLayout rlContentView;
    private HomeGoodsNewBean mHomeGoodsNewBean;

    /**
     * 没有更多数据足布局
     */
    private View footView;
    private ViewFlipper homeViewFlipper;
    private TextView tvMessageCount;
    private ImageView ivMsg;
    private ImageView ivLocate;
    private TextView tvCity;
    private ImageView imageTop;
    private RoundedImageView imageBottom;
    private RoundedImageView rvRecommend1;
    private RoundedImageView rvRecommend2;
    private RoundedImageView rvRecommend3;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentLayout() {
        return R.layout.home_fragment_version2;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initView();
        EventBus.getDefault().register(this);
        initBanner();
        initAdapter();
        initTitle();
        requestLocate();
        statusLayoutManager.showLoadingLayout();
        initGoodsItemClick();
    }

    @Override
    public void loadData() {
        super.loadData();
        getHomeInfo();
        currentPage = 1;
        requestHomeGoodsList(currentPage);
        getMessageCount();
        //获取收货地址
        if (AccountInfoHelper.getInstance().isLogin()) {
            getMyAddressList();
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
        ivLocate = mContentView.findViewById(R.id.ivLocate);
        tvCity = mContentView.findViewById(R.id.tvCity);
        tvCity.setOnClickListener(this);
        ivLocate.setOnClickListener(this);
        imageTop = mContentView.findViewById(R.id.imageTop);
        imageBottom = mContentView.findViewById(R.id.imageBottom);
        rvRecommend1 = mContentView.findViewById(R.id.rvRecommend1);
        rvRecommend2 = mContentView.findViewById(R.id.rvRecommend2);
        rvRecommend3 = mContentView.findViewById(R.id.rvRecommend3);
        imageTop.setOnClickListener(this);
        imageBottom.setOnClickListener(this);
        rvRecommend1.setOnClickListener(this);
        rvRecommend2.setOnClickListener(this);
        rvRecommend3.setOnClickListener(this);
        mContentView.findViewById(R.id.rlSearchLayout).setOnClickListener(this);
        homeViewFlipper = mContentView.findViewById(R.id.homeViewFlipper);
        rlContentView = mContentView.findViewById(R.id.rlContentView);
        tvMessageCount = mContentView.findViewById(R.id.tvMessageCount);
        ivMsg = mContentView.findViewById(R.id.ivMsg);
        ivMsg.setOnClickListener(this);
        rvHome = mContentView.findViewById(R.id.rvHome);
        footView = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        rvHome.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRefreshLayout = mContentView.findViewById(R.id.refreshLayoutHome);
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        setupStatusLayoutManager();
    }

    /**
     * 初始化商品适配器
     */
    private void initAdapter() {
        mGoodsGridAdapter = new HomeGoodsGridAdapter();
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
        if (banner == null) {
            banner = mContentView.findViewById(R.id.bgaBanner);
            if (banner == null) {
                return;
            }
            int width = ScreenUtils.getScreenWidth(XianTaoApplication.getInstance());
            TourCooLogUtil.i(TAG, TAG + "屏幕尺寸:" + width);
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
                        if (bannerBean.getId() <= 0) {
                            return;
                        }
                        doSkipBannerDetail(bannerBean.getId());
                    }
                }
            });
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
            params.height = XianTaoApplication.getImageHeight();
            TourCooLogUtil.i(TAG, "banner:" + params.height + ";width:" + SizeUtil.getScreenWidth());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlSearchLayout:
                TourCoolUtil.startActivity(mContext, SearchGoodsActivity.class);
                break;
            case R.id.ivMsg:
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    TourCooUtil.startActivity(mContext, LoginActivity.class);
                    return;
                }
                skipToMessageCenter();
                break;
            case R.id.tvCity:
                refreshLocate();
                break;
            case R.id.ivLocate:
                refreshLocate();
                break;
            case R.id.imageTop:
                if (mHomeGoodsNewBean == null || mHomeGoodsNewBean.getIndex() == null) {
                    ToastUtil.showFailed("未获取到信息");
                    return;
                }
                doSkipByCondition(mHomeGoodsNewBean.getIndex().getTop());
                break;
            case R.id.rvRecommend1:
                if (mHomeGoodsNewBean == null || mHomeGoodsNewBean.getIndex() == null) {
                    ToastUtil.showFailed("未获取到信息");
                    return;
                }
                doSkipByCondition(mHomeGoodsNewBean.getIndex().getRecommend1());

                break;
            case R.id.rvRecommend2:
                if (mHomeGoodsNewBean == null || mHomeGoodsNewBean.getIndex() == null) {
                    ToastUtil.showFailed("未获取到信息");
                    return;
                }
                doSkipByCondition(mHomeGoodsNewBean.getIndex().getRecommend2());
                break;
            case R.id.rvRecommend3:
                if (mHomeGoodsNewBean == null || mHomeGoodsNewBean.getIndex() == null) {
                    ToastUtil.showFailed("未获取到信息");
                    return;
                }
                doSkipByCondition(mHomeGoodsNewBean.getIndex().getRecommend3());
                break;
            case R.id.imageBottom:
                if (mHomeGoodsNewBean == null || mHomeGoodsNewBean.getIndex() == null) {
                    ToastUtil.showFailed("未获取到信息");
                    return;
                }
                doSkipByCondition(mHomeGoodsNewBean.getIndex().getBottom());
                break;
            default:
                break;
        }
    }


    private void skipToMessageCenter() {
        Intent intent = new Intent();
        intent.setClass(mContext, MsgSystemActivity.class);
        startActivityForResult(intent, REQUEST_CODE_MESSAGE_CENTER);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        currentPage = 1;
        refreshTag = true;
        doRefresh();
        requestHomeGoodsList(currentPage);
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




    private void getHomeInfo() {
        ApiRepository.getInstance().requestHomeInfo().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity == null) {
                            mRefreshLayout.finishRefresh(false);
                            ToastUtil.showFailed("服务器异常");
                            statusLayoutManager.showErrorLayout();
                            if (!hasPermission()) {
                                PermissionManager.requestAllNeedPermission(mContext);
                            }
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
                            if (!hasPermission()) {
                                PermissionManager.requestAllNeedPermission(mContext);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        statusLayoutManager.showErrorLayout();
                        mRefreshLayout.finishRefresh(false);
                        if (!hasPermission()) {
                            PermissionManager.requestAllNeedPermission(mContext);
                        }
                    }
                });
    }


    private HomeGoodsNewBean parseHomeInfo(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String homeInfo = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + homeInfo);
            return JSON.parseObject(homeInfo, HomeGoodsNewBean.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    private void showHomeInfo(HomeGoodsNewBean homeInfoBean) {
        boolean loadFailed = homeInfoBean == null || homeInfoBean.getBanner() == null ||
                homeInfoBean.getGoods() == null || homeInfoBean.getNews() == null;
        if (loadFailed) {
            statusLayoutManager.showErrorLayout();
            return;
        }
        mHomeGoodsNewBean = homeInfoBean;
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
     /*   List<HomeGoodsNewBean.GoodsBean> homeGoodsBeanList = homeInfoBean.getGoods();
        if (homeGoodsBeanList != null && !homeGoodsBeanList.isEmpty()) {
            mGoodsGridAdapter.setNewData(homeGoodsBeanList);
            mGoodsBeanList.addAll(homeInfoBean.getGoods());
        }*/
//        mGoodsGridAdapter.notifyDataSetChanged();
        banner.setData(bannerImageList, null);
        loadHomeViewFlipperData(homeInfoBean.getNews());
        HomeGoodsNewBean.IndexBean indexBean = homeInfoBean.getIndex();
        if (indexBean == null) {
            return;
        }
        if (indexBean.getTop() != null) {
            imageTop.setClickable(true);
            GlideManager.loadImg(TourCooUtil.getUrl(indexBean.getTop().getImage()), imageTop, R.mipmap.img_zwt);
        }
        if (indexBean.getBottom() != null) {
            imageBottom.setClickable(true);
            GlideManager.loadImg(TourCooUtil.getUrl(indexBean.getBottom().getImage()), imageBottom, R.mipmap.img_zwt);
        }
        if (indexBean.getRecommend1() != null) {
            rvRecommend1.setClickable(true);
            GlideManager.loadImg(TourCooUtil.getUrl(indexBean.getRecommend1().getImage()), rvRecommend1, R.mipmap.img_zwt);
        }
        if (indexBean.getRecommend2() != null) {
            rvRecommend2.setClickable(true);
            GlideManager.loadImg(TourCooUtil.getUrl(indexBean.getRecommend2().getImage()), rvRecommend2, R.mipmap.img_zwt);
        }
        if (indexBean.getRecommend3() != null) {
            rvRecommend3.setClickable(true);
            GlideManager.loadImg(TourCooUtil.getUrl(indexBean.getRecommend3().getImage()), rvRecommend3, R.mipmap.img_zwt);
        }
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
        EventBus.getDefault().unregister(this);
        LocateHelper.getInstance().destroyLocationInstance();
        super.onDestroy();
    }

    private void getHomeInfoDelay() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getHomeInfo();
            }
        }, 10);
    }


    /**
     * 跳转banner详情
     */
    private void doSkipBannerDetail(int id) {
        Intent intent = new Intent(mContext, BannerDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
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
                    if (newsBean.getId() <= 0) {
                        return;
                    }
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
                HomeGoodsEntity.GoodsBean homeGoodsBean = mGoodsGridAdapter.getItem(position);
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


    /**
     * 查询消息列表
     */
    private void requestMessageNoReadCount() {
        ApiRepository.getInstance().requestMessageNoReadCount().compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<MessageBean>>() {
                    @Override
                    public void onRequestNext(BaseEntity<MessageBean> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                TourCooLogUtil.i(TAG, "未读消息数量:" + entity.data.getNum());
                                showMsg(entity.data.getNum());
                            } else {
                                setNoLogin(entity.msg);
                            }
                        }
                    }
                });
    }


    private void showMsg(int noReadCount) {
        if (noReadCount > 0) {
            if (noReadCount > 99) {
                tvMessageCount.setText("99+");
                tvMessageCount.setVisibility(View.VISIBLE);
            } else {
                tvMessageCount.setText(String.valueOf(noReadCount));
                tvMessageCount.setVisibility(View.VISIBLE);
            }
        } else {
            tvMessageCount.setVisibility(View.GONE);
        }
    }


    /**
     *
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageRefreshEvent(MessageEvent messageEvent) {
        // 顶部消息刷新
        if (messageEvent == null) {
            return;
        }
        showMsg(messageEvent.getMsgCount());
    }


    /***
     * 定位
     */
    public void requestLocate() {
        LocateHelper.getInstance().startLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                mapLocation = aMapLocation;
                if (mapLocation != null) {
                    showLocate(aMapLocation);
                }
                LocateHelper.getInstance().stopLocation();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_MESSAGE_CENTER:
                if (resultCode == RESULT_OK) {
                    requestMessageNoReadCount();
                }
                break;
            default:
                break;
        }
    }


    private void showLocate(AMapLocation mapLocation) {
        closeLoadingDialog();
        if (mapLocation != null) {
            if (mapLocation.getErrorCode() == 0) {
                TourCooLogUtil.d(TAG, "回调结果:" + mapLocation.getCity());
                String city = mapLocation.getCity();
                String rigion = "市";
                if (city.endsWith(rigion)) {
                    city = city.substring(0, city.length() - 1);
                }
                tvCity.setText(city);
            }
        }
    }


    private void refreshLocate() {
        if (!hasPermission()) {
            PermissionManager.requestAllNeedPermission(mContext);
        } else {
            showLoadingDialog();
            LocateHelper.getInstance().startLocation(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    mapLocation = aMapLocation;
                    if (mapLocation != null) {
                        showLocate(aMapLocation);
                    }
                    LocateHelper.getInstance().stopLocation();
                    closeLoadingDialog();
                }
            });
        }

    }

    private void getMessageCount() {
        if (!AccountInfoHelper.getInstance().isLogin() || !NetworkUtil.isConnected(mContext)) {
            return;
        }
        requestMessageNoReadCount();
    }


    private void doSkipByCondition(RecommendBean recommendBean) {
        if (recommendBean == null) {
            ToastUtil.showFailed("未获取到相关信息");
            return;
        }
        String typeContent = "content";
        String typeGoods = "goods";
        String noContent = "0";
        TourCooLogUtil.i(TAG, TAG + ":recommendBean：" + recommendBean.getParam());
        if (noContent.equals(recommendBean.getParam())) {
            return;
        }
        if (typeContent.equalsIgnoreCase(recommendBean.getType())) {
            Intent intent = new Intent(mContext, WebContentInfoActivity.class);
            intent.putExtra(ERTRA_TITLE, recommendBean.getName());
            intent.putExtra(EXTRA_PARAM, recommendBean.getParam());
            startActivity(intent);
        } else if (typeGoods.equalsIgnoreCase(recommendBean.getType())) {
            Intent intent = new Intent(mContext, GoodsCategoryListActivity.class);
//            intent.putExtra(ERTRA_ID, id);
            intent.putExtra(EXTRA_CATEGORY_NAME, recommendBean.getName());
            intent.putExtra(EXTRA_PARAM, recommendBean.getParam());
            startActivity(intent);
        }
    }

    /**
     * 判断是否有相关权限
     *
     * @return
     */
    private boolean hasPermission() {
        return PermissionManager.checkAllNeedPermission(mContext);
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        refreshTag = false;
        currentPage++;
        doLoadMore(currentPage);
    }


    /**
     * 执行加载更多逻辑
     */
    private void doLoadMore(int page) {
      /*  mGoodsGridAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore();
        if (mGoodsGridAdapter.getData().size() > 30) {
            mRefreshLayout.setEnableLoadMore(false);
            mRefreshLayout.setNoMoreData(true);
            mGoodsGridAdapter.addFooterView(footView);
        } else {
            mGoodsGridAdapter.loadMoreComplete();
        }*/
        requestHomeGoodsList(page);
    }

    /**
     * 获取首页商品信息
     *
     * @param page
     */
    private void requestHomeGoodsList(int page) {
        ApiRepository.getInstance().requestHomeGoodsList(page).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity == null) {
                            return;
                        }
                        if (entity.code == CODE_REQUEST_SUCCESS) {
                            HomeGoodsEntity homeGoodsEntity = parseHomeGoodsList(entity.data);
                            if (homeGoodsEntity == null || homeGoodsEntity.getData() == null) {
                                statusLayoutManager.showErrorLayout();
                                mRefreshLayout.finishLoadMore(false);
                            } else {
                                //添加到集合中
                                TourCooLogUtil.i(TAG, "当前页码:" + currentPage + "数据长度:" + homeGoodsEntity.getData().size());
                                if (refreshTag) {
                                    //下拉刷新
                                    mRefreshLayout.finishRefresh(true);
                                    mGoodsGridAdapter.setNewData(homeGoodsEntity.getData());
                                } else {
                                    //加载更多
                                    mRefreshLayout.finishLoadMore(true);
                                    mGoodsGridAdapter.getData().addAll(homeGoodsEntity.getData());
                                    if (homeGoodsEntity.getData().isEmpty() || homeGoodsEntity.getData().size() <homeGoodsEntity.getPer_page()  ) {
                                        //没有更多了
                                        try {
                                            mGoodsGridAdapter.addFooterView(footView);
                                        } catch (Exception e) {
                                            TourCooLogUtil.e(TAG, TAG + "异常:" + e.toString());
                                        }
                                        mRefreshLayout.setEnableLoadMore(false);
                                        mRefreshLayout.setNoMoreData(true);
                                    } else {
                                        mRefreshLayout.setEnableLoadMore(true);
//                                        mRefreshLayout.removeView(footView);
                                    }
                                }
                                mGoodsGridAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.show(entity.msg);
                            mRefreshLayout.finishLoadMore(false);
                        }
                    }
                });
    }


    private HomeGoodsEntity parseHomeGoodsList(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String userInfo = JSONObject.toJSONString(object);
            return JSON.parseObject(userInfo, HomeGoodsEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "value:" + e.toString());
            return null;
        }
    }

    private void setNoLogin(String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        if (value.contains(NO_LOGIN)) {
            AccountInfoHelper.getInstance().deleteUserAccount();
        } else {
            ToastUtil.showFailed(value);
        }
    }
}
