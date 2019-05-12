package com.tourcoo.xiantao.ui.discount;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.DiscountAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseRefreshFragment;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.discount.DiscountEntity;
import com.tourcoo.xiantao.entity.discount.DiscountInfo;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.ArrayList;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :已使用优惠券列表
 * @company :途酷科技
 * @date 2019年05月10日9:27
 * @Email: 971613168@qq.com
 */
public class DiscountHasUseListFragment extends BaseRefreshFragment<DiscountInfo> {
    private DiscountAdapter mDiscountAdapter;
    private static final int TYPE_DISCOUNT_HAS_USE = 2;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_my_discount_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDefaultPage = 1;
        mDefaultPageSize = 10;
    }

    @Override
    public DiscountAdapter getAdapter() {
        mDiscountAdapter = new DiscountAdapter();
        return mDiscountAdapter;
    }

    @Override
    public void loadData() {
        super.loadData();
        mStatusManager.showSuccessLayout();
    }

    @Override
    public void loadData(int page) {
        requestMyDiscount(page);
    }


    public static DiscountHasUseListFragment newInstance() {
        Bundle args = new Bundle();
        DiscountHasUseListFragment fragment = new DiscountHasUseListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * 根据商品类型请求相应数据
     */
    public void requestMyDiscount(int pageIndex) {
        ApiRepository.getInstance().requestMyDiscount(TYPE_DISCOUNT_HAS_USE, pageIndex).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    DiscountEntity discountEntity = parseDiscountEntity(entity.data);
                                    if (discountEntity != null) {
                                        UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), discountEntity.getData() == null ? new ArrayList<>() : discountEntity.getData(), null);
                                    } else {
                                        mStatusManager.showErrorLayout();
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private DiscountEntity parseDiscountEntity(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String info = JSONObject.toJSONString(object);
            return JSON.parseObject(info, DiscountEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }

}
