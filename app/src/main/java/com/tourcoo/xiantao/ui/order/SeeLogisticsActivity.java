package com.tourcoo.xiantao.ui.order;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.order.LogisticsModel;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * 查看物流
 */
public class SeeLogisticsActivity extends BaseTourCooTitleActivity {

    private RoundedImageView ivPhoto;
    private TextView tvNickName;
    private TextView tvMobile;
    private TextView tvAddress;
    private TextView tvStatus;
    private TextView tvCompany;
    private TextView tvNu;
    private TextView tvAddressInfo;

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


    @Override
    public int getContentLayout() {
        return R.layout.activity_see_logistics;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitleBar.setTitleMainText("查看物流");
        int id = getIntent().getIntExtra("id", 0);
        String photoUrl = getIntent().getStringExtra("photo");

        initViews();

        if (photoUrl != null && !StringUtils.isEmpty(photoUrl)) {
            GlideManager.loadImg(photoUrl, ivPhoto);
        }

        getLogisticsDetails(id);

    }

    private void initViews() {
        ivPhoto = findViewById(R.id.ivPhoto);
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


    /**
     * 查看物流详情
     */
    private void getLogisticsDetails(int id) {
        ApiRepository.getInstance().logout().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity == null) {
                            ToastUtil.showFailed("服务器异常");
                            return;
                        }
                        if (entity.code == CODE_REQUEST_SUCCESS) {
//                            setLogisticsData(entity.data);
                        } else {
                            ToastUtil.showFailed(entity.msg);
                        }
                    }
                });
    }


    private void setLogisticsData(LogisticsModel model) {

        if (model != null) {
            GlideManager.loadImg(model.getImage(), ivPhoto);

            tvNu.setText(model.getNu());
            tvCompany.setText(model.getCompany() + "：");
            tvStatus.setText(model.getState());
            tvAddress.setText("[收货地址]" + model.getAddress());
            tvAddressInfo.setText("收货地址：" + model.getAddress());

            switch (model.getData().size()) {
                case 0:
                    break;

                case 1:
                    llLastLayout.setVisibility(View.VISIBLE);
                    tvLastTime.setText(model.getData().get(0).getDate() + "\n" + model.getData().get(0).getTime());
                    tvLastContent.setText(model.getData().get(0).getContext());
                    break;

                case 2:
                    llLastLayout.setVisibility(View.VISIBLE);
                    llFirstLayout.setVisibility(View.VISIBLE);

                    tvLastTime.setText(model.getData().get(0).getDate() + "\n" + model.getData().get(0).getTime());
                    tvLastContent.setText(model.getData().get(0).getContext());

                    tvFirstTime.setText(model.getData().get(1).getDate() + "\n" + model.getData().get(1).getTime());
                    tvFirstContent.setText(model.getData().get(1).getContext());

                    break;

                case 3:
                    llLastLayout.setVisibility(View.VISIBLE);
                    llSecondLayout.setVisibility(View.VISIBLE);
                    llFirstLayout.setVisibility(View.VISIBLE);


                    tvLastTime.setText(model.getData().get(0).getDate() + "\n" + model.getData().get(0).getTime());
                    tvLastContent.setText(model.getData().get(0).getContext());

                    tvSecondTime.setText(model.getData().get(1).getDate() + "\n" + model.getData().get(1).getTime());
                    tvSecondContent.setText(model.getData().get(1).getContext());

                    tvFirstTime.setText(model.getData().get(2).getDate() + "\n" + model.getData().get(2).getTime());
                    tvFirstContent.setText(model.getData().get(2).getContext());

                    break;
                default:
                    llLastLayout.setVisibility(View.VISIBLE);
                    llSecondLayout.setVisibility(View.VISIBLE);
                    llFirstLayout.setVisibility(View.VISIBLE);

                    tvLastTime.setText(model.getData().get(0).getDate() + "\n" + model.getData().get(0).getTime());
                    tvLastContent.setText(model.getData().get(0).getContext());

                    int addIndex = model.getData().size() - 3;
                    for (int i = addIndex - 1; i >= 0; i--) {
                        View view = View.inflate(this, R.layout.item_add_logistics_view_layout, null);
                        ((TextView) view.findViewById(R.id.tvTime)).setText(model.getData().get(i + 2).getDate() + "\n" + model.getData().get(i + 2).getTime());
                        ((TextView) view.findViewById(R.id.tvContent)).setText(model.getData().get(i + 2).getContext());
                        container.addView(view, 0);
                    }

                    tvSecondTime.setText(model.getData().get(model.getData().size() - 2).getDate() + "\n" + model.getData().get(model.getData().size() - 2).getTime());
                    tvSecondContent.setText(model.getData().get(model.getData().size() - 2).getContext());

                    tvFirstTime.setText(model.getData().get(model.getData().size() - 1).getDate() + "\n" + model.getData().get(model.getData().size() - 1).getTime());
                    tvFirstContent.setText(model.getData().get(model.getData().size() - 1).getContext());

                    break;

            }
        }
    }


}
