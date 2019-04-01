package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.ReceivingInfoEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :JenkinsZhou
 * @description :收货信息适配器
 * @company :途酷科技
 * @date 2019年03月29日13:04
 * @Email: 971613168@qq.com
 */
public class ReceivingInfoAdapter extends BaseQuickAdapter<ReceivingInfoEntity, BaseViewHolder> {
    public ReceivingInfoAdapter(@Nullable List<ReceivingInfoEntity> data) {
        super(R.layout.item_shipping_address, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReceivingInfoEntity item) {
        helper.setText(R.id.tvConsignee, "收货人:" + item.consignee);
        helper.setText(R.id.tvPhoneNumber, item.phoneNumber);
        helper.setText(R.id.tvShippingAddress, "收货地址:" + item.shippingAddress);
    }
}
