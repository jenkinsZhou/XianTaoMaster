package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.util.TourcoolUtil;
import com.tourcoo.xiantao.entity.GoodsEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年03月29日15:51
 * @Email: 971613168@qq.com
 */
public class CollectionGoodsAdapter extends BaseQuickAdapter<GoodsEntity, BaseViewHolder> {
    public CollectionGoodsAdapter(@Nullable List<GoodsEntity> data) {
        super(R.layout.item_goods, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsEntity item) {
        helper.setImageResource(R.id.ivGoodsIcon, R.mipmap.ic_orange);
        helper.setText(R.id.tvGoodsName, TourcoolUtil.getStringNotNull(item.goodsName));
        helper.setText(R.id.tvGoodsLabel, TourcoolUtil.getStringNotNull(item.goodsLabels));
        helper.setText(R.id.tvGoodsPrice, "￥" + item.goodsCurrentPrice);
        helper.setText(R.id.tvGoodsSpec, TourcoolUtil.getStringNotNull(item.goodsSpec));

    }
}
