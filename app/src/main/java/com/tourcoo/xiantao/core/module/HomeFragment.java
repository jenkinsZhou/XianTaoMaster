package com.tourcoo.xiantao.core.module;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
import com.tourcoo.xiantao.core.frame.util.SizeUtil;
import com.tourcoo.xiantao.core.helper.TitleBarViewHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.StatusBarUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.GoodsEntity;
import com.tourcoo.xiantao.helper.ShoppingCarInstance;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * @author :zhoujian
 * @description : 首页
 * @company :途酷科技
 * @date 2019年03月06日上午 10:12
 * @Email: 971613168@qq.com
 */
public class HomeFragment extends BaseTitleFragment implements View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    private SmartRefreshLayout mRefreshLayout;
    private BGABanner banner;
    private RecyclerView rvHome;
    private GoodsGridAdapter mGoodsGridAdapter;
    private List<GoodsEntity> mGuessLikeGoodsList = new ArrayList<>();

    private MainTabActivity mMainTabActivity;


    /**
     * 没有更多数据足布局
     */
    private View footView;

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
        initAdapter();
        setBanner();
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
        rvHome = mContentView.findViewById(R.id.rvHome);
        footView = LayoutInflater.from(mContext).inflate(R.layout.item_view, null);
        rvHome.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRefreshLayout = mContentView.findViewById(R.id.refreshLayoutHome);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setOnLoadMoreListener(this);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
    }

    /**
     * 初始化商品适配器
     */
    private void initAdapter() {
        mGoodsGridAdapter = new GoodsGridAdapter(mGuessLikeGoodsList);
        mGoodsGridAdapter.bindToRecyclerView(rvHome);
        mGoodsGridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                doAddGoods(mGoodsGridAdapter.getData().get(position));
                TourCooLogUtil.i(TAG, "点击了:" + position);
            }
        });
        mRefreshLayout.setEnableLoadMore(true);
        loadGuessLike();
    }

    private void setBanner() {
        List<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429289379&di=203535d4bccdb92b0d491e1b17fe5dbc&imgtype=0&src=http%3A%2F%2Fimg.19yxw.com%2Fwy%2Fupdate%2F20151012%2F2015101213124051007.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429511329&di=dbf86bef50e98ebd74969f414f64c8d9&imgtype=0&src=http%3A%2F%2Fpic62.nipic.com%2Ffile%2F20150318%2F5624330_150949597000_2.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429548089&di=093619d985005df0606ec6840c21a2f6&imgtype=0&src=http%3A%2F%2Fi3.17173.itc.cn%2F2011%2Fnews%2F2011%2F07%2F26%2Fy0726db06s.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429569807&di=02b9a0d6ed0f6411782d4b2d3917507c&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7a4a58f9735639e155b0055d024ede1270b31e4f16842-Ymvyre_fw658");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429569807&di=02b9a0d6ed0f6411782d4b2d3917507c&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7a4a58f9735639e155b0055d024ede1270b31e4f16842-Ymvyre_fw658");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555429569807&di=02b9a0d6ed0f6411782d4b2d3917507c&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F7a4a58f9735639e155b0055d024ede1270b31e4f16842-Ymvyre_fw658");
        if (banner == null) {
            banner = mContentView.findViewById(R.id.bgaBanner);
            banner.setAdapter(new BGABanner.Adapter() {
                @Override
                public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
                    GlideManager.loadImg(model, (ImageView) itemView);
                }
            });
            banner.setDelegate(new BGABanner.Delegate() {
                @Override
                public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                    ToastUtil.show("点击了");
                }
            });
            banner.setData(images, null);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) banner.getLayoutParams();
            params.height = XianTaoApplication.getImageHeight();
            TourCooLogUtil.i(TAG, "banner:" + params.height + ";width:" + SizeUtil.getScreenWidth());
        }
    }


    private void loadGuessLike() {
        int size = 6;
        GoodsEntity goodsEntity;
        for (int i = 0; i < size; i++) {
            goodsEntity = new GoodsEntity(System.currentTimeMillis() + i);
            goodsEntity.goodsSpec = "10" + i + "g";
            goodsEntity.goodsCurrentPrice = 10 + i + 0.9;
            goodsEntity.goodsLabels = "维生素C 很甜";
            goodsEntity.goodsName = "西班牙新鲜杻丁橙"+i;
            mGuessLikeGoodsList.add(goodsEntity);
        }
        mGoodsGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        doRefresh();
    }

    /**
     * 执行刷新逻辑
     */
    private void doRefresh() {
        mGoodsGridAdapter.getData().clear();
        loadGuessLike();
        mRefreshLayout.finishRefresh();
        mGoodsGridAdapter.notifyDataSetChanged();
        mRefreshLayout.setEnableLoadMore(true);
        mRefreshLayout.setNoMoreData(false);
        mGoodsGridAdapter.removeFooterView(footView);
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
        loadGuessLike();
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
    private void doAddGoods(GoodsEntity goodsEntity) {
        if (goodsEntity == null) {
            TourCooLogUtil.e(TAG, "商品为null");
            return;
        }
        ShoppingCarInstance.getInstance().addGoods(goodsEntity);
        showGoodsCount( ShoppingCarInstance.getInstance().getGoodsCount());
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
}
