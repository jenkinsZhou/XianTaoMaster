package com.tourcoo.xiantao.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsCollectEntity;
import com.tourcoo.xiantao.entity.goods.GoodsDetailEntity;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年03月29日15:51
 * @Email: 971613168@qq.com
 */
public class CollectionGoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    public CollectionGoodsAdapter() {
        super(R.layout.item_goods_collection);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        if (item == null) {
            return;
        }
        helper.addOnClickListener(R.id.rrContent);
        helper.addOnClickListener(R.id.btnCancelCollection);
        RoundedImageView ivGoodsIcon = helper.getView(R.id.ivGoodsIcon);
        GlideManager.loadImg(TourCooUtil.getUrl(item.getImage()), ivGoodsIcon);
        helper.setText(R.id.tvGoodsName, TourCoolUtil.getStringNotNull(item.getGoods_name()));
        if(TextUtils.isEmpty(item.getLabel())){
            helper.setVisible(R.id.llGoodsLabel,false);
        }else {
            helper.setVisible(R.id.llGoodsLabel,true);
            helper.setVisible(R.id.tvGoodsLabel,true);
            helper.setText(R.id.tvGoodsLabel,item.getLabel());
        }
//        helper.setText(R.id.tvGoodsPrice, "¥" + item.getGoods_price());

        helper.setVisible(R.id.tvGoodsPrice,false);


    }
}
