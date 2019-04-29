package com.tourcoo.xiantao.adapter;

import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.address.AddressEntity;

/**
 * @author :JenkinsZhou
 * @description :收货地址适配器
 * @company :途酷科技
 * @date 2019年03月29日13:04
 * @Email: 971613168@qq.com
 */
public class AddressInfoAdapter extends BaseQuickAdapter<AddressEntity, BaseViewHolder> {
    public AddressInfoAdapter() {
        super(R.layout.item_shipping_address_version2);
    }

    public static final String ADDRESS_DEFAULT = "1";

    @Override
    protected void convert(BaseViewHolder helper, AddressEntity item) {
        helper.setText(R.id.tvConsigneeName, item.getName());
        helper.setText(R.id.tvConsigneePhone, item.getPhone());
        helper.addOnClickListener(R.id.ivEdit);
        helper.addOnClickListener(R.id.ivDelete);
        helper.addOnClickListener(R.id.llDefaultAddress);
        CheckBox checkBox = helper.getView(R.id.cBoxDefaultAddress);
        if (ADDRESS_DEFAULT.equals(item.getIsdefault())) {
            helper.setChecked(R.id.cBoxDefaultAddress, true);
            checkBox.setText("默认地址");
        } else {
            helper.setChecked(R.id.cBoxDefaultAddress, false);
            checkBox.setText("设为默认地址");
        }

        AddressEntity.AreaBean areaBean = item.getArea();
        if (areaBean != null) {
            String wholeAddress = areaBean.getProvince() + areaBean.getCity() + areaBean.getRegion();
            wholeAddress = wholeAddress + item.getDetail();
            helper.setText(R.id.tvShippingAddress, wholeAddress);
        }
    }
}
