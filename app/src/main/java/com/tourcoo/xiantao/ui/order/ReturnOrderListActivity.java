package com.tourcoo.xiantao.ui.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.OrderListAdapter;
import com.tourcoo.xiantao.adapter.ReturnOrderListAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.order.OrderEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooRefreshLoadActivity;
import com.tourcoo.xiantao.ui.comment.EvaluationActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK_FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK_ING;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_BACK_REFUSE;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_FINISH;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_COMMENT;
import static com.tourcoo.xiantao.constant.OrderConstant.ORDER_STATUS_WAIT_SEND;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.order.OrderDetailActivity.EXTRA_ORDER_ID;
import static com.tourcoo.xiantao.ui.order.OrderDetailActivity.EXTRA_PIN_TAG;
import static com.tourcoo.xiantao.ui.order.ReturnGoodsActivity.EXTRA_GOODS_LIST;

/**
 * @author :zhoujian
 * @description :退货列表
 * @company :翼迈科技
 * @date 2019年 04月 30日 23时11分
 * @Email: 971613168@qq.com
 */
public class ReturnOrderListActivity extends BaseTourCooRefreshLoadActivity<OrderEntity.OrderInfo> implements View.OnClickListener {
    private ReturnOrderListAdapter mAdapter;
    private int orderStatus = ORDER_STATUS_BACK;
    public static final String EXTRA_ORDER_STATUS = "EXTRA_ORDER_STATUS";
    public static final int REQUEST_CODE_RETURN_LIST = 1005;
    /**
     * 跳转订单详情
     */
    public static final int REQUEST_CODE_ORDER_DETAIL = 1006;

    /**
     * 退单详情
     */
    public static final int REQUEST_CODE_RETURN_DETAIL = 1007;

    @Override
    public int getContentLayout() {
        return R.layout.layout_title_refresh_recycler;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDefaultPage = 1;
        mDefaultPageSize = 10;
        TourCooLogUtil.i(TAG, TAG + "订单状态:" + orderStatus);
    }

    @Override
    public ReturnOrderListAdapter getAdapter() {
        mAdapter = new ReturnOrderListAdapter();
        return mAdapter;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("退单列表");
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
        ApiRepository.getInstance().requestOrderInfo(orderStatus, page).compose(bindUntilEvent(ActivityEvent.DESTROY)).
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
                if(orderInfo == null){
                    ToastUtil.showFailed("未获取到订单信息");
                    return;
                }
                switch (view.getId()) {
                    case R.id.photoRecyclerView:
                    case R.id.llOrderInfo:
                        skipOrderDetail(orderInfo.getId(), orderInfo.getTuan());
                        break;
                    case R.id.btnOne:
                        ToastUtil.show("1");
                        break;
                    case R.id.btnTwo:
                        loadButton2Function(orderInfo);
                        break;
                    case R.id.btnThree:
                        loadButton3Function(orderInfo);
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
    private void skipOrderDetail(int orderId, int isPin) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        intent.putExtra(EXTRA_PIN_TAG, isPin);
        intent.setClass(mContext, OrderDetailActivity.class);
        //跳转至订单详情
        startActivityForResult(intent, REQUEST_CODE_ORDER_DETAIL);
    }


    /**
     * 跳转到退单详情
     *
     * @param orderId
     */
    private void skipReturnDetail(int orderId, int pinTag) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        intent.putExtra(EXTRA_PIN_TAG, pinTag);
        intent.setClass(mContext, ReturnGoodsDetailActivity.class);
        //跳转至退单详情
        startActivityForResult(intent, REQUEST_CODE_RETURN_DETAIL);
    }

    private void loadButton3Function(OrderEntity.OrderInfo orderInfo) {
        TourCooLogUtil.i(TAG, TAG + "订单状态:" + orderInfo.getOrder_status());
        switch (orderInfo.getOrder_status()) {
            case ORDER_STATUS_BACK_ING:
                showConfirmCancelDialog(orderInfo);
                break;
            case ORDER_STATUS_FINISH:
                skipSeeLogistics(orderInfo.getId());
                break;
            case ORDER_STATUS_BACK_REFUSE:
                skipSeeLogistics(orderInfo.getId());
                break;
            case ORDER_STATUS_BACK_FINISH:
                skipSeeLogistics(orderInfo.getId());
                break;
            default:
                break;
        }
    }

    /**
     * 查看物流
     */
    private void skipSeeLogistics(int orderId) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ORDER_ID, orderId);
        intent.setClass(mContext, SeeLogisticsActivity.class);
        startActivity(intent);
    }

    private void loadButton4Function(OrderEntity.OrderInfo orderInfo) {
        TourCooLogUtil.i(TAG, TAG + "订单状态:" + orderInfo.getOrder_status());
        switch (orderInfo.getOrder_status()) {
            case ORDER_STATUS_WAIT_SEND:
                //申请退单
                skipReturnGoods(orderInfo);
                break;
            case ORDER_STATUS_WAIT_COMMENT:
                //待评价状态 去评价
                skipEvaluation(orderInfo);
                break;
            case ORDER_STATUS_BACK:
            case ORDER_STATUS_FINISH:
            case ORDER_STATUS_BACK_ING:
            case ORDER_STATUS_BACK_REFUSE:
            case ORDER_STATUS_BACK_FINISH:
                //查看退货详情
                skipReturnDetail(orderInfo.getId(), orderInfo.getTuan());
                break;
            default:
                break;
        }
    }

    private void loadButton2Function(OrderEntity.OrderInfo orderInfo) {
        TourCooLogUtil.i(TAG, TAG + "订单状态:" + orderInfo.getOrder_status());
        switch (orderInfo.getOrder_status()) {
            case ORDER_STATUS_WAIT_SEND:
                //申请退单
//                skipReturnGoods(orderInfo);
                break;
            case ORDER_STATUS_WAIT_COMMENT:
                //待评价状态 去评价
//                skipEvaluation(orderInfo);
                break;
            case ORDER_STATUS_BACK:
            case ORDER_STATUS_FINISH:
            case ORDER_STATUS_BACK_ING:
            case ORDER_STATUS_BACK_REFUSE:
            case ORDER_STATUS_BACK_FINISH:
                skipSeeLogistics(orderInfo.getId());
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
        //跳转至退货页面
        startActivityForResult(intent, REQUEST_CODE_RETURN_LIST);
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
    public void onClick(View v) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_RETURN_LIST:
                    //退货成功 刷新列表
                    mRefreshLayout.autoRefresh();
                    break;
                case REQUEST_CODE_ORDER_DETAIL:
                    //退货成功 刷新列表
                    mRefreshLayout.autoRefresh();
                    break;
                case REQUEST_CODE_RETURN_DETAIL:
                    //取消退货成功 刷新列表
                    mRefreshLayout.autoRefresh();
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 取消退单
     */
    private void requestCancelReturn(int orderId) {
        TourCooLogUtil.i(TAG, TAG + "订单id:" + orderId);
        ApiRepository.getInstance().requestCancelReturn(orderId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
//                                refreshRequest();
                                ToastUtil.showSuccess("已取消退单");
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }

    private void showConfirmCancelDialog(OrderEntity.OrderInfo orderInfo) {
        //删除地址
        ConfirmDialog.Builder builder = new ConfirmDialog.Builder(mContext);
        builder.setTitle("取消退单").setFirstMessage("是否取消退单？")
                .setFirstMsgSize(15).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestCancelReturn(orderInfo.getId());
                        dialog.dismiss();
//                        ApiRepository.getInstance().updateApp()
                    }
                });
        showConfirmDialog(builder);
    }

}
