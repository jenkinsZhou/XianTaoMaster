package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.discount.DiscountEntity;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月10日15:41
 * @Email: 971613168@qq.com
 */
public class DiscountSelectAdapter extends BaseQuickAdapter<DiscountEntity, BaseViewHolder> {
    public DiscountSelectAdapter() {
        super(R.layout.item_discount_select);
    }


    @Override
    protected void convert(BaseViewHolder helper, DiscountEntity item) {

    }
}
