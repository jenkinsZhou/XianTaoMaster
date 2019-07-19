package com.tourcoo.xiantao.adapter;

import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.GridLayoutManager;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.home.HomeGoodsEntity;
import com.tourcoo.xiantao.entity.home.HomeGoodsNewBean;
import com.tourcoo.xiantao.util.FormatDuration;


/**
 * @author :JenkinsZhou
 * @description :首页商品网格布局适配器
 * @company :途酷科技
 * @date 2019年05月21日15:22
 * @Email: 971613168@qq.com
 */
public class HomeGoodsGridAdapter extends BaseQuickAdapter<HomeGoodsEntity.GoodsBean, BaseViewHolder> {
    public static final String IS_SPECIAL = "1";
    private GridLayoutManager layoutManager;

    public HomeGoodsGridAdapter(GridLayoutManager layoutManager) {
        super(R.layout.item_home_goods2);
        this.layoutManager = layoutManager;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeGoodsEntity.GoodsBean item) {
        if (item == null) {
            return;
        }
     /*   //设置item的高度跟随宽度走
        ViewGroup.LayoutParams parm = holder.layoutContent.getLayoutParams();
        parm.height = gridLayoutManager.getWidth()/ gridLayoutManager.getSpanCount()
                - 2*holder.layoutContent.getPaddingLeft() - 2*((ViewGroup.MarginLayoutParams)parm).leftMargin;*/
        boolean special = IS_SPECIAL.equals(item.getSpecial());
        ImageView ivLabel = helper.getView(R.id.ivSpecial);
        boolean isLimit = item.getQuota() > 0;
        if (item.getQuota() > 0) {
            //大于0 表示当前商品为限购商品
            ivLabel.setImageResource(R.mipmap.img_sale_purchasing);
            helper.setVisible(R.id.ivSpecial, isLimit);
        } else {
            //当前不是特价商品，需要判断当前商品是否是特价商品
            ivLabel.setImageResource(R.mipmap.img_sale);
            if (special) {
                helper.setVisible(R.id.ivSpecial, true);
            } else {
                helper.setVisible(R.id.ivSpecial, false);
            }
        }
        RoundedImageView roundedImageView = helper.getView(R.id.rvGoodsImage);
        ViewGroup.LayoutParams params = roundedImageView.getLayoutParams();
        params.height = layoutManager.getWidth() / layoutManager.getSpanCount()
                - roundedImageView.getPaddingLeft();
        TourCooLogUtil.i(TAG, "layoutManager宽度:" + layoutManager.getWidth());
        GlideManager.loadImg(TourCooUtil.getUrl(item.getImage()), roundedImageView);
        helper.setText(R.id.tvGoodsName, item.getGoods_name());
        helper.setText(R.id.tvGoodsPrice, "¥ " + TourCooUtil.doubleTransString(item.getGoods_min_price()));
        if (item.getGoods_line_price() <= 0) {
            helper.setVisible(R.id.tvGoodsLinePrice, false);
            helper.setVisible(R.id.tvSaleCount, false);
        } else {
            helper.setVisible(R.id.tvGoodsLinePrice, true);
            TextView textView = helper.getView(R.id.tvGoodsLinePrice);
            //中划线
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            textView.getPaint().setAntiAlias(true);
            //抗锯齿
            helper.setText(R.id.tvGoodsLinePrice, "¥ " + TourCooUtil.doubleTransString(item.getGoods_line_price()));
            double sale = item.getGoods_min_price() / item.getGoods_line_price();
            sale = sale * 10;
            double limitMin = 0.1;
            double limitMax = 10;
            if (sale <= 0 || sale < limitMin || sale >= limitMax) {
                helper.setGone(R.id.tvSaleCount, false);
            } else {
                helper.setGone(R.id.tvSaleCount, true);
                String onSaleValue = TourCooUtil.doubleTransStringZhenKeepOnePoint(TourCooUtil.formatNumber(sale, 1, false)) + "折";
                helper.setText(R.id.tvSaleCount, onSaleValue);
            }
        }
        helper.setText(R.id.tvGoodsOrigin, "产地:" + item.getOrigin());
        boolean showLabel = !TextUtils.isEmpty(item.getLabel());
        helper.setGone(R.id.llGoodsLabel, showLabel);
        if (showLabel) {
            String label = item.getLabel();
            String result = label.replaceAll("，", ",");
            String[] labelArray = result.split(",");
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
            if (TextUtils.isEmpty(item.getDeduct_rule())) {
                helper.setGone(R.id.llDeductInfo, false);
                SpannableStringBuilder stringBuilder = new SpanUtils()
                        .append("金币每满").setForegroundColor(TourCoolUtil.getColor(R.color.colorBalck333))
                        .append(TourCooUtil.doubleTransStringZhen(item.getDeduct()))
                        .setForegroundColor(TourCoolUtil.getColor(R.color.red_F95B47)).append("用").setForegroundColor(TourCooUtil.getColor((R.color.colorBalck333))).append(TourCooUtil.doubleTransString(item.getDeduct_coin())).setForegroundColor(TourCooUtil.getColor(R.color.red_F95B47)).create();
                helper.setText(R.id.tvDecuctInfo, stringBuilder);
            } else {
                helper.setGone(R.id.llDeductInfo, true);
                String rule = " (" + item.getDeduct_rule() + ")";
                SpannableStringBuilder stringBuilder = new SpanUtils()
                        .append("金币每满").setForegroundColor(TourCoolUtil.getColor(R.color.colorBalck333))
                        .append(TourCooUtil.doubleTransStringZhen(item.getDeduct()))
                        .setForegroundColor(TourCoolUtil.getColor(R.color.red_F95B47)).append("用").setForegroundColor(TourCoolUtil.getColor(R.color.colorBalck333)).append(TourCooUtil.doubleTransStringZhen(item.getDeduct_coin())).setForegroundColor(TourCooUtil.getColor(R.color.red_F95B47)).append(rule).setForegroundColor(TourCooUtil.getColor(R.color.colorGray9A9A)).create();
                helper.setText(R.id.tvDecuctInfo, stringBuilder);
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


        ViewGroup.LayoutParams layoutParams = roundedImageView.getLayoutParams();
        layoutParams.height = layoutManager.getWidth() / layoutManager.getSpanCount()
                - 2 * roundedImageView.getPaddingLeft() - 2 * ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;//margin为什么要乘以2留给你们思考一下

    }


   /* private String subString(String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }

    }*/
}
