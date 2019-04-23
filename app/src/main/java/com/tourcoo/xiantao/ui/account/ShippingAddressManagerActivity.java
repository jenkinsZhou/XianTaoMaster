package com.tourcoo.xiantao.ui.account;


import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.AddressInfoAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.threadpool.ThreadPoolManager;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.ResourceUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.AddressInfoEntity;
import com.tourcoo.xiantao.entity.banner.BannerDetail;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.tourcoo.xiantao.ui.BaseTourcooRefreshLoadActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :收货地址管理页面
 * @company :途酷科技
 * @date 2019年03月29日12:50
 * @Email: 971613168@qq.com
 */
public class ShippingAddressManagerActivity extends BaseTourCooTitleMultiViewActivity implements IMultiStatusView {
    private AddressInfoAdapter mAdapter;
    private List<AddressInfoEntity> mAddressInfoEntityList = new ArrayList<>();
    private SmartRefreshLayout smartLayoutRoot;
    private RelativeLayout rlContentView;

    @Override
    public int getContentLayout() {
        return R.layout.activity_shipping_address;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rlContentView = findViewById(R.id.rlContentView);
    }

    @Override
    protected IMultiStatusView getMultiStatusView() {
        return this;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("收货地址");
        titleBar.setRightText("新增地址").
                setRightTextColor(ResourceUtil.getColor(R.color.colorPrimary)).
                setRightTextSize(15).setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TourCoolUtil.startActivity(mContext, AddNewAddressActivity.class);
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        getMyAddressList();
    }


    /**
     * 获取收货地址
     */

    private void getMyAddressList() {
        ApiRepository.getInstance().myAddressList().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<List<AddressInfoEntity>>>() {
                    @Override
                    public void onRequestNext(BaseEntity<List<AddressInfoEntity>> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                if (!entity.data.isEmpty()) {
                                    mStatusLayoutManager.showSuccessLayout();
                                } else {
                                    mStatusLayoutManager.showEmptyLayout();
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                mStatusLayoutManager.showErrorLayout();
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mStatusLayoutManager.showErrorLayout();
                    }
                });
    }

    @Override
    public View getMultiStatusContentView() {
        return rlContentView;
    }

    @Override
    public void setMultiStatusView(StatusLayoutManager.Builder statusView) {
    }

    @Override
    public View.OnClickListener getEmptyClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLoadDataDelay();
            }
        };
    }

    @Override
    public View.OnClickListener getErrorClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLoadDataDelay();
            }
        };
    }

    @Override
    public View.OnClickListener getCustomerClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLoadDataDelay();
            }
        };
    }

    protected void doLoadDataDelay() {
        mStatusLayoutManager.showLoadingLayout();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getMyAddressList();
            }
        }, delayTime);
    }
}
