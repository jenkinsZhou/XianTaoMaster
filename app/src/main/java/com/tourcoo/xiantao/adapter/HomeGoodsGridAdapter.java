package com.tourcoo.xiantao.adapter;

import android.text.TextUtils;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.home.HomeGoodsNewBean;


/**
 * @author :JenkinsZhou
 * @description :首页商品网格布局适配器
 * @company :途酷科技
 * @date 2019年05月21日15:22
 * @Email: 971613168@qq.com
 */
public class HomeGoodsGridAdapter extends BaseQuickAdapter<HomeGoodsNewBean.GoodsBean, BaseViewHolder> {
    public HomeGoodsGridAdapter() {
        super(R.layout.item_home_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeGoodsNewBean.GoodsBean item) {
        if (item == null) {
            return;
        }
        RoundedImageView roundedImageView = helper.getView(R.id.rvGoodsImage);
        GlideManager.loadImg(TourCooUtil.getUrl(item.getImage()), roundedImageView);
        helper.setText(R.id.tvGoodsName, item.getGoods_name());
        helper.setText(R.id.tvGoodsPrice, "¥ " + TourCooUtil.doubleTransString(item.getGoods_min_price()));
        helper.setText(R.id.tvGoodsOrigin, "产地:" + item.getOrigin());
        boolean showLabel = !TextUtils.isEmpty(item.getLabel());
        helper.setGone(R.id.llGoodsLabel, showLabel);
        if (showLabel) {
            String[] labelArray = item.getLabel().split(",");
            switch (labelArray.length) {
                case 0:
                    helper.setGone(R.id.llGoodsLabel, false);
                    break;
                case 1:
                    helper.setVisible(R.id.tvAssemble1, true);
                    helper.setGone(R.id.tvAssemble2, false);
                    helper.setText(R.id.tvAssemble1, labelArray[0]);
                    break;
                default:
                    helper.setVisible(R.id.tvAssemble1, true);
                    helper.setVisible(R.id.tvAssemble2, true);
                    helper.setText(R.id.tvAssemble1, labelArray[0]);
                    helper.setText(R.id.tvAssemble2, labelArray[1]);
                    break;
            }
        }
        //根据条件显示抵扣相关信息
        boolean isShowDecut = item.getDeduct() > 0;
        TourCooLogUtil.i(TAG, TAG + "是否可抵扣:" + isShowDecut);
        if (isShowDecut) {
            helper.setText(R.id.tvDeduct, TourCooUtil.doubleTransString(item.getDeduct()));
            helper.setText(R.id.tvDeductCoin, TourCooUtil.doubleTransString(item.getDeduct_coin()));
            if (TextUtils.isEmpty(item.getDeduct_rule())) {
                helper.setGone(R.id.llDeductInfo, false);
            } else {
                helper.setGone(R.id.llDeductInfo, true);
                helper.setText(R.id.deductRule, item.getDeduct_rule());
            }
        } else {
            helper.setGone(R.id.llDeductInfo, false);
        }

       /* helper.setImageResource(R.id.rvGoodsImage, R.mipmap.img_zwt);
        helper.setText(R.id.tvGuessLikeGoodsName, TourCoolUtil.getStringNotNull(item.getGoods_name()));
//        helper.setText(R.id.tvGuessLikeGoodsSpec, "/" + item.goodsSpec);
        RoundedImageView roundedImageView = helper.getView(R.id.rvGoodsImage);
        List<Spec> specList = item.getSpecBean();
        Spec spec;
        if (specList != null && !specList.isEmpty()) {
            spec = specList.get(0);

            helper.setText(R.id.tvGuessLikeSinglePrice, "¥ " + spec.getGoods_price());
        } else {
            helper.setText(R.id.tvGuessLikeSinglePrice, "¥ " + item.getGoods_min_price());
        }

        if (item.isTuan()) {
            helper.setVisible(R.id.tvAssemble, true);
        } else {
            helper.setVisible(R.id.tvAssemble, false);
        }
        GlideManager.loadImg(item.getImage(), roundedImageView);*/
    }


   /* private String subString(String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }

    }*/
}
