package com.tourcoo.xiantao.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.entity.GoodsEntity;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :JenkinsZhou
 * @description :商品网格布局适配器
 * @company :途酷科技
 * @date 2019年04月01日15:58
 * @Email: 971613168@qq.com
 */
public class GoodsGridAdapter extends BaseQuickAdapter<GoodsEntity, BaseViewHolder> {
    public GoodsGridAdapter(@Nullable List<GoodsEntity> data) {
        super(R.layout.item_goods_grid, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsEntity item) {
        helper.setImageResource(R.id.rvGoodsImage, R.mipmap.ic_orange);
        helper.setText(R.id.tvGuessLikeGoodsName, TourCoolUtil.getStringNotNull(item.goodsName));
        helper.setText(R.id.tvGuessLikeSinglePrice, "￥" + item.goodsCurrentPrice);
        helper.setText(R.id.tvGuessLikeGoodsSpec, "/" + item.goodsSpec);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
