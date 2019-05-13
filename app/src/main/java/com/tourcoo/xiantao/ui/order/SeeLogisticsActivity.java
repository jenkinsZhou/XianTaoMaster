package com.tourcoo.xiantao.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.order.LogisticsBean;
import com.tourcoo.xiantao.entity.order.LogisticsModel;
import com.tourcoo.xiantao.entity.user.PersonalCenterInfo;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderDetailActivity.EXTRA_ORDER_ID;

/**
 * 查看物流
 */
public class SeeLogisticsActivity extends BaseTourCooTitleMultiViewActivity {

    private RoundedImageView ivPhoto;
    private TextView tvNickName;
    private TextView tvMobile;
    private TextView tvAddress;
    private TextView tvStatus;
    private TextView tvCompany;
    private TextView tvNu;
    private TextView tvAddressInfo;
    private LinearLayout llContentView;

    private LinearLayout llLastLayout;
    private TextView tvLastTime;
    private TextView tvLastContent;

    private LinearLayout container;

    private LinearLayout llSecondLayout;
    private TextView tvSecondTime;
    private TextView tvSecondContent;

    private LinearLayout llFirstLayout;
    private TextView tvFirstTime;
    private TextView tvFirstContent;

    private int id;
    @Override
    public int getContentLayout() {
        return R.layout.activity_see_logistics;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("物流详情");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
         id = getIntent().getIntExtra(EXTRA_ORDER_ID, 0);
        String photoUrl = getIntent().getStringExtra("photo");
        initViews();
        if (photoUrl != null && !StringUtils.isEmpty(photoUrl)) {
            GlideManager.loadImg(photoUrl, ivPhoto);
        }
        TourCooLogUtil.i(TAG, TAG + ":" + "订单id=" + id);
    }

    private void initViews() {
        ivPhoto = findViewById(R.id.ivPhoto);
        llContentView = findViewById(R.id.llContentView);
        tvNickName = findViewById(R.id.tvNickName);
        tvMobile = findViewById(R.id.tvMobile);
        tvAddress = findViewById(R.id.tvAddress);
        tvStatus = findViewById(R.id.tvStatus);
        tvCompany = findViewById(R.id.tvCompany);
        tvNu = findViewById(R.id.tvNu);
        tvAddressInfo = findViewById(R.id.tvAddressInfo);
        tvAddress = findViewById(R.id.tvAddress);
        llLastLayout = findViewById(R.id.llLastLayout);
        tvLastTime = findViewById(R.id.tvLastTime);
        tvLastContent = findViewById(R.id.tvLastContent);
        container = findViewById(R.id.container);
        llSecondLayout = findViewById(R.id.llSecondLayout);
        tvSecondTime = findViewById(R.id.tvSecondTime);
        tvSecondContent = findViewById(R.id.tvSecondContent);
        llFirstLayout = findViewById(R.id.llFirstLayout);
        tvFirstTime = findViewById(R.id.tvFirstTime);
        tvFirstContent = findViewById(R.id.tvFirstContent);
    }

    @Override
    public void loadData() {
        super.loadData();
        getLogisticsDetails(id);
    }

    /**
     * 查看物流详情
     */
    private void getLogisticsDetails(int orderId) {
        mStatusLayoutManager.showLoadingLayout();
        ApiRepository.getInstance().requestLogistics(orderId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity == null) {
                            ToastUtil.showFailed("服务器出了点小差");
                            mStatusLayoutManager.showErrorLayout();
                            return;
                        }
                        if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                            mStatusLayoutManager.showSuccessLayout();
                            LogisticsBean logisticsBean = parseInfo(entity.data);
                            try {
                                setLogisticsData(logisticsBean);
                            }catch (Exception e){
                                mStatusLayoutManager.showEmptyLayout();
                            }
                        } else {
                            mStatusLayoutManager.showEmptyLayout();
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mStatusLayoutManager.showErrorLayout();
                    }
                });
    }


    private void setLogisticsData(LogisticsBean model) {
        if (model != null) {
            GlideManager.loadImg(TourCooUtil.getUrl(model.getImage()), ivPhoto);
            tvNu.setText(model.getNo());
            tvNickName.setText(model.getAddress().getName());
            tvMobile.setText(model.getAddress().getPhone());
            tvCompany.setText(model.getCompany() + "：");
            tvStatus.setText(model.getStatus());
            tvAddress.setText("[收货地址]" + model.getAddress().getAddress());
            tvAddressInfo.setText("收货地址：" + model.getAddress().getAddress());
            switch (model.getInfo().size()) {
                case 0:
                    break;
                case 1:
                    llLastLayout.setVisibility(View.VISIBLE);
                    tvLastTime.setText(model.getInfo().get(0).getTime());
                    tvLastContent.setText(model.getInfo().get(0).getContext());
                    break;

                case 2:
                    llLastLayout.setVisibility(View.VISIBLE);
                    llFirstLayout.setVisibility(View.VISIBLE);
                    tvLastTime.setText(model.getInfo().get(0).getTime());
//                    tvLastTime.setText(model.getData().get(0).getDate() + "\n" + model.getData().get(0).getTime());
//                    tvLastContent.setText(model.getData().get(0).getContext());
                    tvLastContent.setText(model.getInfo().get(0).getContext());
                    tvFirstTime.setText(model.getInfo().get(1).getTime());
                    tvFirstContent.setText(model.getInfo().get(1).getContext());
                    break;

                case 3:
                    llLastLayout.setVisibility(View.VISIBLE);
                    llSecondLayout.setVisibility(View.VISIBLE);
                    llFirstLayout.setVisibility(View.VISIBLE);
                    tvLastTime.setText(model.getInfo().get(0).getTime());
                    tvLastContent.setText(model.getInfo().get(0).getContext());
                    tvSecondTime.setText(model.getInfo().get(1).getTime());
                    tvSecondContent.setText(model.getInfo().get(1).getContext());
                    tvFirstTime.setText(model.getInfo().get(2).getTime());
                    tvFirstContent.setText(model.getInfo().get(2).getContext());

                    break;
                default:
                    llLastLayout.setVisibility(View.VISIBLE);
                    llSecondLayout.setVisibility(View.VISIBLE);
                    llFirstLayout.setVisibility(View.VISIBLE);

                    tvLastTime.setText(model.getInfo().get(0).getTime());
                    tvLastContent.setText(model.getInfo().get(0).getContext());
                    int addIndex = model.getInfo().size() - 3;
                    for (int i = addIndex - 1; i >= 0; i--) {
                        View view = View.inflate(this, R.layout.item_add_logistics_view_layout, null);
                        ((TextView) view.findViewById(R.id.tvTime)).setText(model.getInfo().get(i + 2).getTime());
                        ((TextView) view.findViewById(R.id.tvContent)).setText(model.getInfo().get(i + 2).getContext());
                        container.addView(view, 0);
                    }

                    tvSecondTime.setText(model.getInfo().get(model.getInfo().size() - 2).getTime());
                    tvSecondContent.setText(model.getInfo().get(model.getInfo().size() - 2).getContext());

                    tvFirstTime.setText(model.getInfo().get(model.getInfo().size() - 1).getTime());
                    tvFirstContent.setText(model.getInfo().get(model.getInfo().size() - 1).getContext());
                    break;
            }
        }
    }


    private LogisticsBean parseInfo(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseObject(info, LogisticsBean.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    @Override
    protected IMultiStatusView getMultiStatusView() {
        return new IMultiStatusView() {
            @Override
            public View getMultiStatusContentView() {
                return llContentView ;
            }

            @Override
            public void setMultiStatusView(StatusLayoutManager.Builder statusView) {

            }

            @Override
            public View.OnClickListener getEmptyClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getLogisticsDetails(id);
                    }
                };
            }

            @Override
            public View.OnClickListener getErrorClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getLogisticsDetails(id);
                    }
                };
            }

            @Override
            public View.OnClickListener getCustomerClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getLogisticsDetails(id);
                    }
                };
            }
        };
    }
}
