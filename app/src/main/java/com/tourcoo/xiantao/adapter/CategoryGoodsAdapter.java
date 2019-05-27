package com.tourcoo.xiantao.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.entity.goods.GoodsCategoryBean;

/**
 * @author :JenkinsZhou
 * @description :商城中的商品列表类型适配器
 * @company :途酷科技
 * @date 2019年04月27日14:24
 * @Email: 971613168@qq.com
 */
public class CategoryGoodsAdapter extends BaseQuickAdapter<GoodsCategoryBean.GoodsSimpleInfo, BaseViewHolder> {
    public CategoryGoodsAdapter() {
        super(R.layout.item_goods_catogry);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsCategoryBean.GoodsSimpleInfo simpleInfo) {
        RoundedImageView ivGoodsIcon = helper.getView(R.id.ivGoodsIcon);
        GlideManager.loadImg(simpleInfo.getImage(), ivGoodsIcon, R.mipmap.img_zwt);
        helper.setText(R.id.tvGoodsName, simpleInfo.getGoods_name());
        if (!TextUtils.isEmpty(simpleInfo.getLabel())) {
            helper.setVisible(R.id.llGoodsLabel, true);
            helper.setVisible(R.id.tvGoodsLabel, true);
            helper.setText(R.id.tvGoodsLabel, simpleInfo.getLabel());
        } else {
            helper.setVisible(R.id.llGoodsLabel, false);
        }
        helper.setText(R.id.tvGoodsPrice, "¥ " + simpleInfo.getGoods_min_price());
    }

}
