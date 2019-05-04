package com.tourcoo.xiantao.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.OrderListAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseRefreshFragment;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.order.OrderEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.comment.EvaluationActivity;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_ALL;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_COMMENT;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_PAY;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_RECIEVE;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_SEND;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderDetailActivity.EXTRA_ORDER_ID;
import static com.tourcoo.xiantao.ui.order.ReturnGoodsActivity.EXTRA_GOODS_LIST;

/**
 * @author :JenkinsZhou
 * @description :订单列表（全部状态）
 * @company :途酷科技
 * @date 2019年04月27日18:30
 * @Email: 971613168@qq.com
 */
public class OrderListFragment extends BaseRefreshFragment<OrderEntity.OrderInfo> {
    private OrderListAdapter mAdapter;
    private int orderStatus = ORDER_STATUS_ALL;
    /**
     * 订单详情
     */
//    public static final int REQUEST_CODE_ORDER_DETAIL = 2;
    public static final String EXTRA_ORDER_STATUS = "EXTRA_ORDER_STATUS";

    @Override
    public int getContentLayout() {
        return R.layout.layout__smart_refresh;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDefaultPage = 1;
        mDefaultPageSize = 10;
        if (getArguments() == null) {
            ToastUtil.show("未获取到数据");
            return;
        }
        orderStatus = getArguments().getInt(EXTRA_ORDER_STATUS, -1);
        TourCooLogUtil.i(TAG, TAG + "订单状态:" + orderStatus);
    }

    @Override
    public OrderListAdapter getAdapter() {
        mAdapter = new OrderListAdapter();
        return mAdapter;
    }

    @Override
    public void loadData(int page) {
        requestOrderInfo(page);
    }

    @Override
    public void loadData() {
        super.loadData();
        initItemButtonClick();
    }

    private OrderEntity parseOrderEntity(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            TourCooLogUtil.i(TAG, "准备解析:" + info);
            return JSON.parseObject(info, OrderEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }

    /**
     * 获取个人中心信息
     */
    private void requestOrderInfo(int page) {
        ApiRepository.getInstance().requestOrderInfo(orderStatus, page).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    LogUtils.i(JSON.toJSONString(entity.data));
                                    OrderEntity orderEntity = parseOrderEntity(entity.data);
                                    if (orderEntity != null) {
                                        UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), orderEntity.getData() == null ? new ArrayList<>() : orderEntity.getData(), null);
                                    } else {
                                        ToastUtil.showFailed(entity.msg);
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                mRefreshLayout.finishRefresh(false);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mRefreshLayout.finishRefresh(false);
                        mStatusManager.showErrorLayout();
                    }
                });
    }


    public static OrderListFragment newInstance(int orderStatus) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_ORDER_STATUS, orderStatus);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 初始化item按钮点击事件
     */
    private void initItemButtonClick() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OrderEntity.OrderInfo orderInfo = mAdapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.photoRecyclerView:
                    case R.id.llOrderInfo:
                        skipOrderDetail(orderInfo.getId());
                        break;
                    case R.id.btnOne:
                        ToastUtil.show("1");
                        break;
                    case R.id.btnTwo:
                        ToastUtil.show("2");
                        break;
                    case R.id.btnThree:
                        ToastUtil.show("3");
                        break;
                    case R.id.btnFour:
                        loadButton4Function(orderInfo);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 跳转到订单详情
     *
     * @param orderId
     */
    private void skipOrderDetail(int orderId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        intent.setClass(mContext, OrderDetailActivity.class);
        TourCooLogUtil.i(TAG, TAG + ":" + "订单状态");
        startActivityForResult(intent, orderStatus);
    }


    private void loadButton4Function(OrderEntity.OrderInfo orderInfo) {
        switch (orderInfo.getOrder_status()) {
            case ORDER_STATUS_WAIT_SEND:
                //申请退单
                skipReturnGoods(orderInfo);
                break;
            case ORDER_STATUS_WAIT_COMMENT:
                //待评价状态 去评价
                skipEvaluation(orderInfo);
                break;
            default:
                break;
        }
    }


    /**
     * 跳转至退货页面
     *
     * @param
     */
    private void skipReturnGoods(OrderEntity.OrderInfo orderInfo) {
        Intent intent = new Intent();
        List<Goods> goodsList = orderInfo.getGoods();
        intent.putExtra(EXTRA_GOODS_LIST, (Serializable) goodsList);
        intent.putExtra(EXTRA_ORDER_ID, orderInfo.getId());
        intent.setClass(mContext, ReturnGoodsActivity.class);
        startActivity(intent);
    }

    /**
     * 去评价
     *
     * @param orderInfo
     */
    private void skipEvaluation(OrderEntity.OrderInfo orderInfo) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderInfo.getId());
        intent.setClass(mContext, EvaluationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //订单详情回调
                case ORDER_STATUS_WAIT_PAY:
                    TourCooLogUtil.i(TAG, TAG + ":" + "代付款回调");
                    mRefreshLayout.autoRefresh();
                    break;
                case ORDER_STATUS_WAIT_COMMENT:
                    mRefreshLayout.autoRefresh();
                    break;
                case ORDER_STATUS_WAIT_SEND:
                    mRefreshLayout.autoRefresh();
                    break;
                case ORDER_STATUS_WAIT_RECIEVE:
                    mRefreshLayout.autoRefresh();
                    break;
                default:
                    break;
            }
        }

    }
}
