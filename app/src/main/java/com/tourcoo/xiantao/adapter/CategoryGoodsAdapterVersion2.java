package com.tourcoo.xiantao.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.entity.goods.GoodsCategoryBean;

import static com.tourcoo.xiantao.adapter.HomeGoodsGridAdapter.IS_SPECIAL;

/**
 * @author :JenkinsZhou
 * @description :分类tab页新适配器
 * @company :途酷科技
 * @date 2019年06月26日10:01
 * @Email: 971613168@qq.com
 */
public class CategoryGoodsAdapterVersion2 extends BaseQuickAdapter<GoodsCategoryBean.GoodsSimpleInfo, BaseViewHolder> {

    public CategoryGoodsAdapterVersion2() {
        super(R.layout.item_goods_catogry_version2);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsCategoryBean.GoodsSimpleInfo simpleInfo) {
        RoundedImageView ivGoodsIcon = helper.getView(R.id.ivGoodsIcon);
        boolean special = IS_SPECIAL.equals(simpleInfo.getSpecial());
        ImageView ivLabel = helper.getView(R.id.ivSpecial);
        if (simpleInfo.getQuota() > 0) {
            //大于0 表示当前商品为特价商品
            ivLabel.setImageResource(R.mipmap.img_sale_purchasing);
            helper.setVisible(R.id.ivSpecial, true);
        } else {
            //说明不是特价商品
            ivLabel.setImageResource(R.mipmap.img_sale);
            helper.setGone(R.id.ivSpecial, special);
        }
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
