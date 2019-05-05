package com.tourcoo.xiantao.adapter;


import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.entity.goods.Goods;


/**
 * @author :JenkinsZhou
 * @description :退货页面商品列表适配器
 * @company :途酷科技
 * @date 2019年04月28日17:21
 * @Email: 971613168@qq.com
 */
public class ReturnGoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {

    public ReturnGoodsAdapter() {
        super(R.layout.item_return_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, @NonNull Goods goods) {
        helper.setText(R.id.tvGoodsName, goods.getGoods_name());
        helper.setText(R.id.tvGoodsPrice, "￥ " + goods.getGoods_price());
        helper.setText(R.id.goodsCount,"x"+goods.getTotal_num());
        RoundedImageView civGoodsIcon = helper.getView(R.id.civGoodsIcon);
        GlideManager.loadImg(goods.getImage(), civGoodsIcon);
        CheckBox cBoxGoods = helper.getView(R.id.cBoxGoods);
//        helper.addOnClickListener(R.id.tvCBox);
        if (goods.isSelect()) {
            cBoxGoods.setChecked(true);
        } else {
            cBoxGoods.setChecked(false);
        }
    }
}
