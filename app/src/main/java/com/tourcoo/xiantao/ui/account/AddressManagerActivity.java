package com.tourcoo.xiantao.ui.account;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.AddressInfoAdapter;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.adapter.AddressInfoAdapter.ADDRESS_DEFAULT;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.account.AddNewAddressActivity.RESULT_SUCCESS_ADDRESS;

/**
 * @author :JenkinsZhou
 * @description :收货地址管理页面
 * @company :途酷科技
 * @date 2019年03月29日12:50
 * @Email: 971613168@qq.com
 */
public class AddressManagerActivity extends BaseTourCooTitleMultiViewActivity implements IMultiStatusView, OnRefreshListener {
    private AddressInfoAdapter mAdapter;
    private RelativeLayout rlContentView;
    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView rvContent;
    public static final int REQUEST_CODE_ADD_ADDRESS = 100;
    public static final int REQUEST_CODE_EDIT_ADDRESS = 101;
    public static final String EXTRA_ADDRESS_INFO = "EXTRA_ADDRESS_INFO";

    @Override
    public int getContentLayout() {
        return R.layout.activity_shipping_address;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rlContentView = findViewById(R.id.rlContentView);
        mRefreshLayout = findViewById(R.id.smartLayoutRoot);
        rvContent = findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new AddressInfoAdapter();
        mAdapter.bindToRecyclerView(rvContent);
        findViewById(R.id.btnAddAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, AddNewAddressActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ADDRESS);
            }
        });
        initAddressAdapter();
    }

    @Override
    protected IMultiStatusView getMultiStatusView() {
        return this;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("收货地址");
     /*   titleBar.setRightText("新增地址").
                setRightTextColor(ResourceUtil.getColor(R.color.colorPrimary)).
                setRightTextSize(15).setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TourCoolUtil.startActivity(mContext, AddNewAddressActivity.class);
            }
        });*/
    }

    @Override
    public void loadData() {
        super.loadData();
        mStatusLayoutManager.showLoadingLayout();
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(mContext).setSpinnerStyle(SpinnerStyle.Translate));
        getMyAddressList();
    }


    /**
     * 获取收货地址
     */

    private void getMyAddressList() {
        ApiRepository.getInstance().myAddressList().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<List<AddressEntity>>>() {
                    @Override
                    public void onRequestNext(BaseEntity<List<AddressEntity>> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                if (!entity.data.isEmpty()) {
                                    mStatusLayoutManager.showSuccessLayout();
                                    mAdapter.setNewData(entity.data);
                                    AccountInfoHelper.getInstance().setDefaultAddress(getDefaultAddress(entity.data));
                                    mRefreshLayout.finishRefresh(true);
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
                        TourCooLogUtil.e(TAG, TAG + "异常:" + e.toString());
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

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        getMyAddressList();
    }


    private void initAddressAdapter() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ivDelete:
                        //删除地址
                        showDeleteDialog(position);
                        break;
                    case R.id.ivEdit:
                        doSkipEdit(mAdapter.getData().get(position));
                        //编辑地址
                        break;
                    case R.id.llDefaultAddress:
                        doSetDefaultAddress(mAdapter.getData().get(position).getAddress_id());
                        break;
                    default:
                        break;
                }
            }
        });
    }


    private void doDeleteAddress(int addressId) {
        ApiRepository.getInstance().deleteAddress(addressId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                ToastUtil.showSuccess(entity.msg);
                                getMyAddressList();
                            }
                        } else {
                            mStatusLayoutManager.showErrorLayout();
                        }
                    }
                });
    }

    private void doSetDefaultAddress(int addressId) {
        ApiRepository.getInstance().setDefaultAddress(addressId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                ToastUtil.showSuccess(entity.msg);
                                getMyAddressList();
                            }
                        } else {
                            mStatusLayoutManager.showErrorLayout();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD_ADDRESS:
                if (RESULT_SUCCESS_ADDRESS == resultCode) {
                    getMyAddressList();
                }
                break;
            case REQUEST_CODE_EDIT_ADDRESS:
                if (RESULT_SUCCESS_ADDRESS == resultCode) {
                    getMyAddressList();
                }
                break;
            default:
                break;
        }
    }

    private void doSkipEdit(AddressEntity addressEntity) {
        if (addressEntity == null) {
            ToastUtil.showFailed("未获取到地址信息");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ADDRESS_INFO, addressEntity);
        intent.setClass(mContext, EditAddressActivity.class);
        startActivityForResult(intent, REQUEST_CODE_EDIT_ADDRESS);
    }


    private AddressEntity getDefaultAddress(List<AddressEntity> addressBeanList) {
        if (addressBeanList == null || addressBeanList.isEmpty()) {
            return null;
        }
        for (AddressEntity addressEntity : addressBeanList) {
            if (addressEntity.getIsdefault().equals(ADDRESS_DEFAULT)) {
                return addressEntity;
            }
        }
        return addressBeanList.get(0);
    }


    private void showDeleteDialog(int position) {
        //删除地址
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(mContext);
        builder.setTitle("删除地址").setFirstMessage("确定要删除该地址吗？")
                .setFirstMsgSize(15).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doDeleteAddress(mAdapter.getData().get(position).getAddress_id());
                        dialog.dismiss();
//                        ApiRepository.getInstance().updateApp()
                    }
                });
        showConfirmDialog(builder);
    }
}
