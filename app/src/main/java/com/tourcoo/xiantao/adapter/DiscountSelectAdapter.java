package com.tourcoo.xiantao.adapter;

import android.widget.ImageView;
import android.widget.RelativeLayout;

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
    protected void convert(BaseViewHolder helper, DiscountEntity discountEntity) {
        RelativeLayout rlSelect = helper.getView(R.id.rlSelect);
        helper.addOnClickListener(R.id.rlSelect);
        rlSelect.setClickable(discountEntity.isClickEnable());
        ImageView imageView = helper.getView(R.id.ivSelect);
        if (discountEntity.isSelect()) {
            imageView.setImageResource(R.mipmap.ic_checked);
        } else {
            imageView.setImageResource(R.mipmap.ic_unchecked);
        }

    }
}
