package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;

import androidx.annotation.NonNull;


/**
 * @author :JenkinsZhou
 * @description :订单列表中的商品适配器
 * @company :途酷科技
 * @date 2019年04月25日12:58
 * @Email: 971613168@qq.com
 */
public class OrderGoodsSettleAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {

    public OrderGoodsSettleAdapter() {
        super(R.layout.item_order_grid_good);
    }


    @Override
    protected void convert(BaseViewHolder helper, @NonNull Goods goods) {
        helper.setText(R.id.tvGoodsName, goods.getGoods_name());
        helper.setText(R.id.tvGoodsPrice, "￥ " + goods.getSpecBean().get(0).getGoods_price());
        //当前商品的数量
        helper.setText(R.id.goodsCount, "x" + goods.getTotal_num());
        RoundedImageView civGoodsIcon = helper.getView(R.id.civGoodsIcon);
        GlideManager.loadImg(goods.getImage(), civGoodsIcon);
    }
}
