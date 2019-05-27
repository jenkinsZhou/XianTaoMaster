package com.tourcoo.xiantao.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.entity.AddressPickerBean;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.TuanRule;
import com.tourcoo.xiantao.entity.order.OrderDetailEntity;

/**
 * @author :JenkinsZhou
 * @description :订单详情中商品适配器
 * @company :途酷科技
 * @date 2019年04月28日12:24
 * @Email: 971613168@qq.com
 */
public class OrderGoodsDetailAdapter extends BaseQuickAdapter<OrderDetailEntity.OrderBean.GoodsBean, BaseViewHolder> {
    private boolean isPin;

    public OrderGoodsDetailAdapter(boolean isPin) {
        super(R.layout.item_order_grid_good);
        this.isPin = isPin;
    }


    @Override
    protected void convert(BaseViewHolder helper, @NonNull OrderDetailEntity.OrderBean.GoodsBean goods) {

        helper.setText(R.id.tvGoodsName, goods.getGoods_name());
        helper.setText(R.id.tvGoodsPrice, "¥ " + goods.getGoods_price());
        //当前商品的数量
        helper.setText(R.id.goodsCount, "x" + goods.getTotal_num());
        RoundedImageView civGoodsIcon = helper.getView(R.id.civGoodsIcon);
        if (TextUtils.isEmpty(goods.getGoods_attr())) {
            helper.setVisible(R.id.llGoodsLabel, false);
        } else {
            helper.setVisible(R.id.llGoodsLabel, true);
            helper.setText(R.id.tvGoodsLabel, goods.getGoods_attr());
        }
       /* if(isPin){
            if (goods.getTuan_rule() != null) {
                Gson gson = new Gson();
                TuanRule tuanRule = gson.fromJson(goods.getTuan_rule().toString(), TuanRule.class);
                if (tuanRule != null) {
                    helper.setVisible(R.id.llGoodsLabel, true);
                    helper.setText(R.id.tvGoodsPrice, "¥ " + tuanRule.getPrice());
                    helper.setText(R.id.tvGoodsLabel, tuanRule.getName());
                }
            }
        }*/

        GlideManager.loadImg(goods.getImage(), civGoodsIcon);
    }
}
