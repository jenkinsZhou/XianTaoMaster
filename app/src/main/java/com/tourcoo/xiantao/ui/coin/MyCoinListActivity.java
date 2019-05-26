package com.tourcoo.xiantao.ui.coin;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.CoinHistoryAdapter;
import com.tourcoo.xiantao.adapter.RechargeDetailAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.coin.CoinDetail;
import com.tourcoo.xiantao.entity.coin.CoinHistory;
import com.tourcoo.xiantao.entity.recharge.RechargeHistory;
import com.tourcoo.xiantao.entity.user.CashEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooRefreshLoadActivity;
import com.tourcoo.xiantao.util.MoneyUtil;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.ArrayList;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :我的金币列表
 * @company :途酷科技
 * @date 2019年04月30日12:31
 * @Email: 971613168@qq.com
 */
public class MyCoinListActivity extends BaseTourCooRefreshLoadActivity<CoinDetail> implements View.OnClickListener {

    private CoinHistoryAdapter adapter;
    private TextView tvCurrentGold;
    private TextView tvAg;
    private double currentAuAmount;
    private CoinHistory mCoinHistory;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_coin_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvAg = findViewById(R.id.tvAg);
        tvCurrentGold = findViewById(R.id.tvCurrentGold);
        findViewById(R.id.tvConvertGold).setOnClickListener(this);
        TourCoolRecycleViewDivider divider = new TourCoolRecycleViewDivider(
                mContext, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(mContext, R.color.grayDividerDeep));
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("我的金币");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 20, 0);
        TextView textView = titleBar.getTextView(Gravity.END);
        textView.setLayoutParams(params);

    }


    @Override
    public CoinHistoryAdapter getAdapter() {
        adapter = new CoinHistoryAdapter();
        return adapter;
    }

    @Override
    public void loadData(int page) {
        requestCoinList(page);
    }

    @Override
    public void loadData() {
        mDefaultPageSize = 10;
        super.loadData();
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mDefaultPage = 1;
        super.onRefresh(refreshlayout);
    }


    /**
     * 查询充值记录
     */
    private void requestCoinList(int page) {
        if (page == 1) {
            adapter.getData().clear();
        }
        TourCooLogUtil.i(TAG, TAG + ":" + "执行了");
        ApiRepository.getInstance().requestMyCoinList(page).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<CoinHistory>>() {
                    @Override
                    public void onRequestNext(BaseEntity<CoinHistory> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                CoinHistory coinHistory = entity.data;
                                mCoinHistory = coinHistory;
                                showMyCoin(coinHistory);
                                UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), coinHistory.getData() == null ? new ArrayList<>() : coinHistory.getData(), null);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mStatusManager.showErrorLayout();
                    }
                });
    }


    private void showMyCoin(CoinHistory coinHistory) {
        if (coinHistory == null) {
            return;
        }
        currentAuAmount = coinHistory.getAg();
        String au = TourCooUtil.doubleTransStringZhen(coinHistory.getAu());
        String ag = MoneyUtil.amountConversion(coinHistory.getAg());
        tvCurrentGold.setText(au);
        tvAg.setText(ag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvConvertGold:
                if (mCoinHistory == null || mCoinHistory.getAg() < mCoinHistory.getCoin()) {
                    ToastUtil.showFailed("银币不足,暂时无法兑换哦");
                    return;
                }
                showExchangeDialog();
                break;
            default:
                break;
        }
    }


    /**
     * 银币转换
     */
    private void requestExchange() {
        ApiRepository.getInstance().requestExchange().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                ToastUtil.showSuccess("兑换成功");
                                setResult(RESULT_OK);
                                requestCoinList(1);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private void showExchangeDialog() {
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(mContext);
        builder.setTitle("兑换金币").setFirstMessage("是否将银币全部兑换为金币？")
                .setFirstMsgSize(15).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestExchange();
                dialog.dismiss();
            }
        });
        showConfirmDialog(builder);
    }

    @Override
    public boolean isRefreshEnable() {
        return false;
    }
}
