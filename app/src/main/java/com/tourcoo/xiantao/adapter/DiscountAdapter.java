package com.tourcoo.xiantao.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.discount.DiscountEntity;

/**
 * @author :JenkinsZhou
 * @description :个人中心优惠券列表适配器
 * @company :途酷科技
 * @date 2019年05月09日17:02
 * @Email: 971613168@qq.com
 */
public class DiscountAdapter extends BaseQuickAdapter<DiscountEntity,BaseViewHolder> {

    public DiscountAdapter() {
        super(R.layout.item_discount);
    }


    @Override
    protected void convert(BaseViewHolder helper, DiscountEntity item) {

    }
}
