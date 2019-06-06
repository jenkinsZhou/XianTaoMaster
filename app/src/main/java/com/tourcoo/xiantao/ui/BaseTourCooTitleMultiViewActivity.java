package com.tourcoo.xiantao.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.widget.custom.BounceLoadingView;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * @author :zhoujian
 * @description :
 * @company :翼迈科技
 * @date 2019年 04月 21日 22时33分
 * @Email: 971613168@qq.com
 */
public abstract class BaseTourCooTitleMultiViewActivity extends BaseTourCooTitleActivity {
    private List<BounceLoadingView> mBounceLoadingViewList = new ArrayList<>();
    protected Handler mHandler = new Handler();
    protected long delayTime = 1000L;



    @Override
    public void loadData() {
        super.loadData();
        //初始化StatusLayoutManager
        setupStatusLayoutManager();
    }

    /**
     * 获取多状态布局
     *
     * @return
     */
    protected abstract IMultiStatusView getMultiStatusView();

    private void setupStatusLayoutManager() {
        multiStatusView = getMultiStatusView();
        if (multiStatusView == null) {
            return;
        }
        if (multiStatusView.getMultiStatusContentView() == null) {
            TourCooLogUtil.e(TAG, "value:" + "getMultiStatusContentView未实例化");
            return;
        }
        StatusLayoutManager.Builder builder = new StatusLayoutManager.Builder(multiStatusView.getMultiStatusContentView())
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
                .setEmptyLayout(inflateLayout(R.layout.custom_empty_layout))
                .setEmptyClickViewID(R.id.tvRefresh)
                .setErrorLayout(inflateLayout(R.layout.custom_error_layout))
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
                        if (multiStatusView != null) {
                            multiStatusView.getEmptyClickListener().onClick(view);
                        }
//                        statusLayoutManager.showLoadingLayout();
//                        getData(1000);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        if (multiStatusView != null) {
                            multiStatusView.getErrorClickListener().onClick(view);
                        }
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                        if (multiStatusView != null) {
                            multiStatusView.getCustomerClickListener().onClick(view);
                        }
                     /*   if (view.getId() == R.id.tv_customer) {
                            Toast.makeText(MainActivity.this, R.string.request_access, Toast.LENGTH_SHORT).show();
                        } else if (view.getId() == R.id.tv_customer1) {
                            Toast.makeText(MainActivity.this, R.string.switch_account, Toast.LENGTH_SHORT).show();
                        }*/

                    }
                });
        multiStatusView.setMultiStatusView(builder);
        mStatusLayoutManager = builder.build();
//                .build();
    }

    @Override
    protected void onDestroy() {
        BounceLoadingView bounceLoadingView;
        for (int i = mBounceLoadingViewList.size() - 1; i >= 0; i--) {
            bounceLoadingView = mBounceLoadingViewList.get(i);
            if (bounceLoadingView != null) {
                bounceLoadingView.stop();
                mBounceLoadingViewList.remove(bounceLoadingView);
                bounceLoadingView = null;
            }
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        System.gc();
        super.onDestroy();
    }


    @Override
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


    @Override
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
