package com.tourcoo.xiantao.core.frame.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.base.activity.BaseActivity;
import com.tourcoo.xiantao.core.frame.interfaces.IBaseView;
import com.tourcoo.xiantao.core.frame.interfaces.IRefreshLoadView;
import com.tourcoo.xiantao.core.frame.manager.RxJavaManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.widget.custom.BounceLoadingView;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.trello.rxlifecycle3.components.support.RxFragment;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentManager;

/**
 * @author :zhoujian
 * @description : 所有Fragment的基类实现懒加载
 * @company :途酷科技
 * @date 2019年03月04日下午 05:05
 * @Email: 971613168@qq.com
 */
public abstract class BaseFragment extends RxFragment implements IBaseView {
    protected Activity mContext;
    protected View mContentView;
    protected boolean mIsFirstShow;
    protected boolean mIsViewLoaded;
    protected final String TAG = getClass().getSimpleName();
    protected boolean mIsVisibleChanged = false;
    private boolean mIsInViewPager;
    protected Bundle mSavedInstanceState;
    private List<BounceLoadingView> mBounceLoadingViewList = new ArrayList<>();

    @Override
    public boolean isEventBusEnable() {
        return true;
    }

    /**
     * 检查Fragment或FragmentActivity承载的Fragment是否只有一个
     *
     * @return
     */
    protected boolean isSingleFragment() {
        int size = 0;
        FragmentManager manager = getFragmentManager();
        if (manager != null) {
            size = manager.getFragments().size();
        }
        TourCooLogUtil.i(TAG, TAG + ";FragmentManager承载Fragment数量:" + size);
        return size <= 1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
        mIsFirstShow = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mSavedInstanceState = savedInstanceState;
        beforeSetContentView();
        mContentView = inflater.inflate(getContentLayout(), container, false);
        //解决StatusLayoutManager与SmartRefreshLayout冲突
        if (this instanceof IRefreshLoadView && mContentView.getClass() == SmartRefreshLayout.class) {
            FrameLayout frameLayout = new FrameLayout(container.getContext());
            if (mContentView.getLayoutParams() != null) {
                frameLayout.setLayoutParams(mContentView.getLayoutParams());
            }
            frameLayout.addView(mContentView);
            mContentView = frameLayout;
        }
        mIsViewLoaded = true;
        if (isEventBusEnable()) {
            EventBus.getDefault().register(this);
        }
        beforeInitView(savedInstanceState);
        initView(savedInstanceState);
        if (isSingleFragment() && !mIsVisibleChanged) {
            if (getUserVisibleHint() || isVisible() || !isHidden()) {
                onVisibleChanged(true);
            }
        }
        TourCooLogUtil.i(TAG, TAG + ";mIsVisibleChanged:" + mIsVisibleChanged
                + ";getUserVisibleHint:" + getUserVisibleHint()
                + ";isHidden:" + isHidden() + ";isVisible:" + isVisible());
        return mContentView;
    }

    @Override
    public void beforeSetContentView() {

    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        if (UiConfigManager.getInstance().getActivityFragmentControl() != null) {
            UiConfigManager.getInstance().getActivityFragmentControl().setContentViewBackground(mContentView, this.getClass());
        }
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BounceLoadingView bounceLoadingView;
        for (int i = mBounceLoadingViewList.size() - 1; i >= 0; i--) {
            bounceLoadingView = mBounceLoadingViewList.get(i);
            if (bounceLoadingView != null) {
                bounceLoadingView.stop();
                mBounceLoadingViewList.remove(bounceLoadingView);
                bounceLoadingView = null;
            }
        }
        System.gc();
    }

    @Override
    public void onDestroy() {
        if (isEventBusEnable()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        TourCooLogUtil.i(TAG, "onResume-isAdded:" + isAdded() + ";getUserVisibleHint:" + getUserVisibleHint()
                + ";isHidden:" + isHidden() + ";isVisible:" + isVisible() + ";isResume:" + isResumed() + ";isVisibleToUser:" + isVisibleToUser(this) + ";host:");
        if (isAdded() && isVisibleToUser(this)) {
            onVisibleChanged(true);
        }
    }

    /**
     * @param fragment
     * @return
     */
    private boolean isVisibleToUser(BaseFragment fragment) {
        if (fragment == null) {
            return false;
        }
        if (fragment.getParentFragment() != null) {
            return isVisibleToUser((BaseFragment) fragment.getParentFragment()) && (fragment.isInViewPager() ? fragment.getUserVisibleHint() : fragment.isVisible());
        }
        return fragment.isInViewPager() ? fragment.getUserVisibleHint() : fragment.isVisible();
    }

    /**
     * 不在viewpager中Fragment懒加载
     */
    @Override
    public void onHiddenChanged(final boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!mIsViewLoaded) {
            RxJavaManager.getInstance().setTimer(10)
                    .compose(this.<Long>bindUntilEvent(FragmentEvent.DESTROY))
                    .subscribe(new BaseObserver<Long>() {
                        @Override
                        public void onRequestNext(Long entity) {
                            onHiddenChanged(hidden);
                        }
                    });
        } else {
            onVisibleChanged(!hidden);
        }

    }

    /**
     * 在viewpager中的Fragment懒加载
     */
    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsInViewPager = true;
        if (!mIsViewLoaded) {
            RxJavaManager.getInstance().setTimer(10)
                    .compose(this.<Long>bindUntilEvent(FragmentEvent.DESTROY))
                    .subscribe(new BaseObserver<Long>() {
                        @Override
                        public void onRequestNext(Long entity) {
                            setUserVisibleHint(isVisibleToUser);
                        }
                    });
        } else {
            onVisibleChanged(isVisibleToUser);
        }
    }

    /**
     * 是否在ViewPager
     *
     * @return
     */
    public boolean isInViewPager() {
        return mIsInViewPager;
    }

    /**
     * 用户可见变化回调
     *
     * @param isVisibleToUser
     */
    protected void onVisibleChanged(final boolean isVisibleToUser) {
        TourCooLogUtil.i(TAG, "onVisibleChanged-isVisibleToUser:" + isVisibleToUser);
        mIsVisibleChanged = true;
        if (isVisibleToUser) {
            //避免因视图未加载子类刷新UI抛出异常
            if (!mIsViewLoaded) {
                RxJavaManager.getInstance().setTimer(10)
                        .compose(this.<Long>bindUntilEvent(FragmentEvent.DESTROY))
                        .subscribe(new BaseObserver<Long>() {
                            @Override
                            public void onRequestNext(Long entity) {
                                onVisibleChanged(isVisibleToUser);
                            }
                        });
            } else {
                lazyLoad();
            }
        }
    }

    private void lazyLoad() {
        if (mIsFirstShow && mIsViewLoaded) {
            mIsFirstShow = false;
            loadData();
        }
    }

    protected View inflateLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }


    private BounceLoadingView getLoadingView(View rootView) {
        if (rootView == null) {
            return null;
        }
        BounceLoadingView bounceLoadingView;
        bounceLoadingView = rootView.findViewById(R.id.bounceLoadingView);
        if (bounceLoadingView == null) {
            return null;
        }
        bounceLoadingView.addBitmap(R.mipmap.v4);
        bounceLoadingView.addBitmap(R.mipmap.v5);
        bounceLoadingView.addBitmap(R.mipmap.v6);
        bounceLoadingView.addBitmap(R.mipmap.v7);
        bounceLoadingView.addBitmap(R.mipmap.v8);
        bounceLoadingView.addBitmap(R.mipmap.v9);
        bounceLoadingView.setShadowColor(Color.LTGRAY);
        bounceLoadingView.setDuration(700);
        mBounceLoadingViewList.add(bounceLoadingView);
        return bounceLoadingView;
    }


    protected View getLoadingLayout() {
        View loadingLayout;
        loadingLayout = inflateLayout(R.layout.custom_loading_layout);
        if (loadingLayout == null) {
            return null;
        }
        BounceLoadingView bounceLoadingView = getLoadingView(loadingLayout);
        if (bounceLoadingView != null) {
            bounceLoadingView.start();
        }
        return loadingLayout;
    }



}
