package com.tourcoo.xiantao.ui.discount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.DiscountSelectAdapter;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.discount.DiscountInfo;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooRefreshLoadActivity;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :选择优惠券页面
 * @company :途酷科技
 * @date 2019年05月10日17:41
 * @Email: 971613168@qq.com
 */
public class DisCountSelectListActivity extends BaseTourCooTitleMultiViewActivity {
    public static final String EXTRA_PRICE = "EXTRA_PRICE";

    public static final String EXTRA_DISCOUNT_LIST = "EXTRA_DISCOUNT_LIST";

    public static final String EXTRA_DISCOUNT_LIST_SELECT = "EXTRA_DISCOUNT_LIST_SELECT";

    public static final String EXTRA_DISCOUNT_CAN_USE_COUNT = "EXTRA_DISCOUNT_CAN_USE_COUNT";
    private double mPirce;

    private int ruleId = -1;


    private List<DiscountInfo> mDiscountInfoList = new ArrayList<>();

    private DiscountSelectAdapter mDiscountAdapter;

    /**
     * 可使用的优惠券数量
     */
    private int canUseCount = -1;
    private RelativeLayout rrRootView;

    private List<DiscountInfo> selectDiscountList = new ArrayList<>();

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_discount_select_list;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("卡券选择");
        titleBar.setRightText("确认");
        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DiscountInfo> allDiscout = mDiscountAdapter.getData();
                List<DiscountInfo> discountInfoList = getSelectList(allDiscout);
//                ToastUtil.showSuccess("总共有" + allDiscout.size() + "张优惠券，当前选中了" + discountInfoList.size() + "张");
                Intent data = new Intent();
                data.putExtra(EXTRA_DISCOUNT_LIST, (Serializable) discountInfoList);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPirce = getIntent().getDoubleExtra(EXTRA_PRICE, -1);
        SmartRefreshLayout refreshLayout = findViewById(R.id.smartLayout_rootFastLib);
        refreshLayout.setEnableRefresh(false);
        rrRootView = findViewById(R.id.rrRootView);
        RecyclerView rvContent = findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mDiscountAdapter = new DiscountSelectAdapter();
        mDiscountAdapter.bindToRecyclerView(rvContent);
        List<DiscountInfo> discountInfoList = (List<DiscountInfo>) getIntent().getSerializableExtra(EXTRA_DISCOUNT_LIST_SELECT);
        canUseCount = getIntent().getIntExtra(EXTRA_DISCOUNT_CAN_USE_COUNT, -1);
        if (discountInfoList != null && !discountInfoList.isEmpty()) {
            selectDiscountList.addAll(discountInfoList);
            for (DiscountInfo discountInfo : selectDiscountList) {
                TourCooLogUtil.i(TAG, "是否选中" + discountInfo.isSelect());
                TourCooLogUtil.i(TAG, "是否可以点击" + discountInfo.isClickEnable());
                TourCooLogUtil.i(TAG, "规则id：" + discountInfo.getRule_id());
            }
            if (selectDiscountList.get(0) != null) {
                ruleId = selectDiscountList.get(0).getRule_id();
                if (canUseCount < 0) {
                    //小于0才去计算可使用的优惠券数量
                    canUseCount = calculateUseCount(selectDiscountList.get(0));
                }
            }
        }


    }


    @Override
    public void loadData() {
        super.loadData();
        initClickListener();
        mStatusLayoutManager.showLoadingLayout();
        requestAvailableList(mPirce);
//        requestData();
    }

    @Override
    protected IMultiStatusView getMultiStatusView() {
        return new IMultiStatusView() {
            @Override
            public View getMultiStatusContentView() {
                return rrRootView;
            }

            @Override
            public void setMultiStatusView(StatusLayoutManager.Builder statusView) {
            }

            @Override
            public View.OnClickListener getEmptyClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mStatusLayoutManager.showLoadingLayout();
                        requestAvailableList(mPirce);
                    }
                };
            }

            @Override
            public View.OnClickListener getErrorClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mStatusLayoutManager.showLoadingLayout();
                        requestAvailableList(mPirce);
                    }
                };
            }

            @Override
            public View.OnClickListener getCustomerClickListener() {
                return null;
            }
        };
    }


    public static DiscountExpiredListFragment newInstance() {
        Bundle args = new Bundle();
        DiscountExpiredListFragment fragment = new DiscountExpiredListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void requestData() {
        int size = 10;
        DiscountInfo discountInfo;
        for (int i = 0; i < size; i++) {
            discountInfo = new DiscountInfo();
            discountInfo.setClickEnable(true);
        /*    if ((i & 1) == 1) {
                discountEntity.setSelect(true);
                discountEntity.setClickEnable(true);
            } else {
                discountEntity.setClickEnable(false);
                discountEntity.setSelect(false);
            }*/
            mDiscountInfoList.add(discountInfo);
            discountInfo.setRule_id(i);
        }
        DiscountInfo discountInfo1 = new DiscountInfo();
        discountInfo1.setRule_id(1);
        discountInfo1.setClickEnable(true);
        DiscountInfo discountInfo2 = new DiscountInfo();
        discountInfo2.setRule_id(1);
        discountInfo2.setClickEnable(true);
        discountInfo2.setSelect(true);
        mDiscountInfoList.add(discountInfo1);
        mDiscountInfoList.add(discountInfo2);
        mDiscountAdapter.setNewData(mDiscountInfoList);
    }


    private void initClickListener() {
        mDiscountAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<DiscountInfo> discountInfoList = mDiscountAdapter.getData();
                DiscountInfo currentDiscount = discountInfoList.get(position);
                int selectRuleId = currentDiscount.getRule_id();
                if (currentDiscount.isSelect()) {
                    currentDiscount.setClickEnable(true);
                    currentDiscount.setSelect(false);
                    mDiscountAdapter.notifyDataSetChanged();
                } else {
                    selectRuleId = currentDiscount.getRule_id();
                    canUseCount = calculateUseCount(currentDiscount);
                    currentDiscount.setClickEnable(true);
                    currentDiscount.setSelect(true);
                    mDiscountAdapter.notifyDataSetChanged();
                    if (getSelectCount(discountInfoList) > canUseCount) {
                        ToastUtil.showFailed("当前优惠券只能选择" + canUseCount + "张");
                        currentDiscount.setClickEnable(true);
                        currentDiscount.setSelect(false);
                        mDiscountAdapter.notifyDataSetChanged();
                        return;
                    }

                }

                if (getSelectCount(discountInfoList) > canUseCount) {
                    ToastUtil.showFailed("当前优惠券只能选择" + canUseCount + "张");
                    return;
                }
                boolean noSelect = !checkSelect(discountInfoList);
                //如果没有任何优惠券被选中 则默认都可以勾选
                if (noSelect) {
                    setAllDiscountClickable(discountInfoList);
                } else {
                    //说明此时已经有优惠券被选中,则其他优惠券将根据id判断是否可以点击
                    setDiscountClickableByRuleId(discountInfoList, selectRuleId);
                }
                canUseCount = calculateUseCount(currentDiscount);
                if (currentDiscount.isSelect()) {
                    if (getSelectCount(discountInfoList) > canUseCount) {
                        ToastUtil.showFailed("当前优惠券只能选择" + canUseCount + "张");
                        return;
                    }
                }
                mDiscountAdapter.notifyDataSetChanged();

            }
        });
    }


    /**
     * 判断是否有选中的优惠券
     *
     * @param entityList
     * @return
     */
    private boolean checkSelect(List<DiscountInfo> entityList) {
        for (DiscountInfo discountInfo : entityList) {
            if (discountInfo.isSelect()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将全部优惠券全部置为可以点击
     *
     * @param entityList
     */
    private void setAllDiscountClickable(List<DiscountInfo> entityList) {
        for (DiscountInfo discountInfo : entityList) {
            discountInfo.setClickEnable(true);
        }
    }

    /**
     * 根据规则id判断是否可以点击
     *
     * @param entityList
     * @param ruleId
     */
    private void setDiscountClickableByRuleId(List<DiscountInfo> entityList, int ruleId) {
        TourCooLogUtil.i(TAG, TAG + "规则id:" + ruleId);
        if (ruleId < 0) {
            return;
        }
        for (DiscountInfo discountInfo : entityList) {
            if (discountInfo.getRule_id() == ruleId) {
                discountInfo.setClickEnable(true);
            } else {
                discountInfo.setClickEnable(false);
            }
            //只要是选中状态 肯定可以点击
            if (discountInfo.isSelect()) {
                discountInfo.setClickEnable(true);
            }
        }
    }


    /**
     * 根据规则id判断是否可以点击
     *
     * @param entityList
     * @param ruleId
     */
    private void loadDiscountClickableByRuleId(List<DiscountInfo> entityList, int ruleId) {
        TourCooLogUtil.i(TAG, TAG + "规则id:" + ruleId);
        if (ruleId < 0) {
            return;
        }
        TourCooLogUtil.i(TAG, TAG + "entityList:" + entityList.size());
        for (DiscountInfo discountInfo : entityList) {
            TourCooLogUtil.i(TAG, TAG + "当前优惠券:" + entityList.size());
            //只要是选中状态 肯定可以点击
            TourCooLogUtil.i(TAG, TAG + ":" + "discountInfo：" + discountInfo.getId() + "是否选中:" + discountInfo.isSelect() + "面值:" + discountInfo.getName());
            if (discountInfo.isSelect()) {
                discountInfo.setClickEnable(true);
                TourCooLogUtil.i(TAG, TAG + "设置了属性:" + discountInfo.getId() + discountInfo.getName());
            } else {
                if (discountInfo.getRule_id() == ruleId) {
                    discountInfo.setClickEnable(true);
                } else {
                    discountInfo.setClickEnable(false);
                }
            }
        }
    }


    private void requestAvailableList(double price) {
        ApiRepository.getInstance().requestAvailableList(price).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                List<DiscountInfo> discountInfoList = parsrDiscountList(entity.data);
                                if (discountInfoList != null) {
                                    mStatusLayoutManager.showSuccessLayout();
                                    TourCooLogUtil.i(TAG, "数据数量:" + discountInfoList.size());
                                    //加载选中状态
                                    loadSelectStatus(discountInfoList);
                                    loadDiscountClickableByRuleId(discountInfoList, ruleId);
                                    mDiscountAdapter.setNewData(discountInfoList);
                                } else {
                                    mStatusLayoutManager.showErrorLayout();
                                }
                            } else {
                                mStatusLayoutManager.showErrorLayout();
                            }
                        } else {
                            mStatusLayoutManager.showErrorLayout();
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        TourCooLogUtil.e(TAG, "请求异常:" + e.toString());
                        mStatusLayoutManager.showErrorLayout();
                    }
                });
    }


    private List<DiscountInfo> parsrDiscountList(Object data) {
        if (data == null) {
            return null;
        }
        try {
            String info = JSON.toJSONString(data);
            return JSON.parseArray(info, DiscountInfo.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    /**
     * 计算可以使用的优惠券数量
     *
     * @return
     */
    private int calculateUseCount(DiscountInfo currentDiscount) {
        if (currentDiscount == null) {
            return -1;
        }
        int num = currentDiscount.getNum();
        TourCooLogUtil.d(TAG, TAG + "计算的数量:" + num);
        if (currentDiscount.getCost() <= 0) {
            return -1;
        }
        TourCooLogUtil.i(TAG, TAG + "当前的订单总价:" + mPirce);
        TourCooLogUtil.i(TAG, TAG + "当前的优惠券面值:" + currentDiscount.getCost());
        int calculateNum = (int) (mPirce / currentDiscount.getCost());
        TourCooLogUtil.i(TAG, TAG + "计算的数量:" + calculateNum);
        if (num > calculateNum) {
            return calculateNum;
        } else {
            return num;
        }
    }


    private int getSelectCount(List<DiscountInfo> discountInfoList) {
        int select = 0;
        for (DiscountInfo discountInfo : discountInfoList) {
            if (discountInfo.isSelect()) {
                select++;
            }
        }
        TourCooLogUtil.i(TAG, TAG + "当前选中的数量:" + select);
        return select;
    }


    private List<DiscountInfo> getSelectList(List<DiscountInfo> allDiscouts) {
        List<DiscountInfo> discountInfoList = new ArrayList<>();
        for (DiscountInfo allDiscout : allDiscouts) {
            if (allDiscout.isSelect()) {
                discountInfoList.add(allDiscout);
            }
        }
        return discountInfoList;
    }

    /**
     * 加载选中状态
     */
    private void loadSelectStatus(List<DiscountInfo> discountInfoList) {
        TourCooLogUtil.e(TAG, TAG + ":" + "mDiscountInfoList长度:" + selectDiscountList.size());
        if (selectDiscountList.isEmpty()) {
            return;
        }
        for (DiscountInfo discountInfo : discountInfoList) {
            // 说明用户有选择优惠券 则默认不可点击 也不可选中
            discountInfo.setClickEnable(false);
            discountInfo.setSelect(false);
            for (DiscountInfo selectDiscount : selectDiscountList) {
                if (selectDiscount == null) {
                    continue;
                }
                TourCooLogUtil.e(TAG, TAG + ":" + "设置了:" + selectDiscount.isSelect() + ":" + selectDiscount.getId());
                //如果选中
                if (discountInfo.getId() == selectDiscount.getId()) {
                    TourCooLogUtil.e(TAG, TAG + ":" + "设置了" + selectDiscount.getId());
                    discountInfo.setSelect(true);
                    ruleId = selectDiscount.getRule_id();
                }
                if (discountInfo.getRule_id() == selectDiscount.getRule_id()) {
                    discountInfo.setClickEnable(true);
                }
            }
        }
    }


}
