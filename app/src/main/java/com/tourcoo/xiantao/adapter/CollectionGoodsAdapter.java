package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.entity.goods.GoodsDetailEntity;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年03月29日15:51
 * @Email: 971613168@qq.com
 */
public class CollectionGoodsAdapter extends BaseQuickAdapter<GoodsDetailEntity, BaseViewHolder> {
    public CollectionGoodsAdapter(List<GoodsDetailEntity> data) {
        super(R.layout.item_goods_version, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsDetailEntity item) {
        if (item == null) {
            return;
        }
        helper.setImageResource(R.id.ivGoodsIcon, R.mipmap.ic_orange);
      /*  helper.setText(R.id.tvGoodsName, TourCoolUtil.getStringNotNull(item.goodsName));
        helper.setText(R.id.tvGoodsLabel, TourCoolUtil.getStringNotNull(item.goodsLabels));
        helper.setText(R.id.tvGoodsPrice, "￥" + item.goodsCurrentPrice);
        helper.setText(R.id.tvGoodsSpec, TourCoolUtil.getStringNotNull(item.goodsSpec));*/

    }
}
