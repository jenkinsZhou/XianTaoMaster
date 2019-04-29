package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.classify.ClassifyGoodsBean;

import androidx.annotation.NonNull;


/**
 * @author :JenkinsZhou
 * @description :分类页面商品适配器
 * @company :途酷科技
 * @date 2019年04月24日15:55
 * @Email: 971613168@qq.com
 */
public class ClassifyGoodsGridAdapter extends BaseQuickAdapter<ClassifyGoodsBean, BaseViewHolder> {
    private static final String TAG = "ClassifyGoodsGridAdapter";
    public ClassifyGoodsGridAdapter() {
        super(R.layout.item_classify_goods);
    }

    @Override
    protected void convert(BaseViewHolder helper, @NonNull ClassifyGoodsBean item) {
        helper.setText(R.id.tvGoodsName, TourCooUtil.getNotNullValue(item.getName()));
        String goodsUrl = TourCooUtil.getNotNullValue(item.getImage());
//        goodsUrl = RequestConfig.BASE_URL + goodsUrl;
        TourCooLogUtil.i(TAG, TAG + ":" + goodsUrl);
        RoundedImageView roundedImageView = helper.getView(R.id.ivGoodsImage);
        GlideManager.loadImg(goodsUrl, roundedImageView);
    }
}
