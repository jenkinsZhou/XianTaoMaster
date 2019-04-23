package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.AddressInfoEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :JenkinsZhou
 * @description :收货地址适配器
 * @company :途酷科技
 * @date 2019年03月29日13:04
 * @Email: 971613168@qq.com
 */
public class AddressInfoAdapter extends BaseQuickAdapter<AddressInfoEntity, BaseViewHolder> {
    public AddressInfoAdapter() {
        super(R.layout.item_shipping_address_version1);
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressInfoEntity item) {
        helper.setText(R.id.tvConsignee, "收货人:" + item.consignee);
        helper.setText(R.id.tvPhoneNumber, item.phoneNumber);
        helper.setText(R.id.tvShippingAddress, "收货地址:" + item.shippingAddress);
    }
}
