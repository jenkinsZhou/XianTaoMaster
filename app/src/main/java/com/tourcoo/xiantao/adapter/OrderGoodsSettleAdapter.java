package com.tourcoo.xiantao.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;
import com.tourcoo.xiantao.entity.goods.GoodsSkuBean;
import com.tourcoo.xiantao.entity.goods.TuanRule;

import androidx.annotation.NonNull;


/**
 * @author :JenkinsZhou
 * @description :结算列表中的商品适配器
 * @company :途酷科技
 * @date 2019年04月25日12:58
 * @Email: 971613168@qq.com
 */
public class OrderGoodsSettleAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    private boolean isPin;
    public OrderGoodsSettleAdapter(boolean isPin) {
        super(R.layout.item_order_grid_good);
        this.isPin = isPin;
    }


    @Override
    protected void convert(BaseViewHolder helper, @NonNull Goods goods) {
        helper.setText(R.id.tvGoodsName, goods.getGoods_name());
        GoodsSkuBean goodsSkuBean = goods.getGoods_sku();
        if (goodsSkuBean != null) {
                helper.setText(R.id.tvGoodsPrice, "¥ " + goods.getGoods_sku().getGoods_price());
                if (!TextUtils.isEmpty(goodsSkuBean.getGoods_attr())) {
                    helper.setVisible(R.id.llGoodsLabel, true);
                    helper.setGone(R.id.tvGoodsLabel, true);
                    helper.setText(R.id.tvGoodsLabel, goods.getGoods_sku().getGoods_attr());
                } else {
                    helper.setGone(R.id.tvGoodsLabel, false);
                    helper.setVisible(R.id.llGoodsLabel, false);
                }
        } else {
            helper.setText(R.id.tvGoodsPrice, "¥ " + goods.getGoods_price());
        }
         TourCooLogUtil.i(TAG,TAG+"是否是拼团:"+goods.isTuan() );
        if (isPin&&goods.isTuan() && goods.getTuan_rule() != null) {
            //说明此时是拼团结算
            TuanRule tuanRule = new Gson().fromJson(goods.getTuan_rule().toString(), TuanRule.class);
            if (tuanRule != null) {
                helper.setVisible(R.id.llGoodsLabel, true);
                helper.setGone(R.id.tvGoodsLabel, true);
                helper.setText(R.id.tvGoodsLabel, tuanRule.getName());
                helper.setText(R.id.tvGoodsPrice, "¥ " + tuanRule.getPrice());
            }
        }
        //当前商品的数量
        helper.setText(R.id.goodsCount, "x" + goods.getTotal_num());
        RoundedImageView civGoodsIcon = helper.getView(R.id.civGoodsIcon);
        GlideManager.loadImg(goods.getImage(), civGoodsIcon);

    }
}
