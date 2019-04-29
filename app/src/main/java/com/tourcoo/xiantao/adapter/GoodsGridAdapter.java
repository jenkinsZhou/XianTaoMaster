package com.tourcoo.xiantao.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.entity.goods.HomeGoodsBean;
import com.tourcoo.xiantao.entity.goods.Spec;

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
public class GoodsGridAdapter extends BaseQuickAdapter<HomeGoodsBean, BaseViewHolder> {
    public GoodsGridAdapter() {
        super(R.layout.item_goods_grid);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeGoodsBean item) {
        helper.setImageResource(R.id.rvGoodsImage, R.mipmap.ic_orange);
        helper.setText(R.id.tvGuessLikeGoodsName, TourCoolUtil.getStringNotNull(item.getGoods_name()));
//        helper.setText(R.id.tvGuessLikeGoodsSpec, "/" + item.goodsSpec);
        RoundedImageView roundedImageView = helper.getView(R.id.rvGoodsImage);
        List<Spec> specList = item.getSpecBean();
        Spec spec;
        if (specList != null && !specList.isEmpty()) {
            spec = specList.get(0);
            helper.setText(R.id.tvGuessLikeSinglePrice, "￥" + spec.getGoods_price());
        } else {
            helper.setText(R.id.tvGuessLikeSinglePrice, "￥" + item.getGoods_min_price());
        }

        if (item.isTuan()) {
            helper.setVisible(R.id.tvAssemble, true);
        } else {
            helper.setVisible(R.id.tvAssemble, false);
        }
        GlideManager.loadImg(item.getImage(), roundedImageView);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
