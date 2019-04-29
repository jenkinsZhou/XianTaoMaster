package com.tourcoo.xiantao.adapter;

import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.classify.GoodClassifyEntity;


import androidx.annotation.NonNull;

/**
 * @author :JenkinsZhou
 * @description :分类名称
 * @company :途酷科技
 * @date 2019年04月24日16:05
 * @Email: 971613168@qq.com
 */
public class ClassifyNameAdapter extends BaseQuickAdapter<GoodClassifyEntity, BaseViewHolder> {
    public ClassifyNameAdapter() {
        super(R.layout.item_classify_name);
    }

    @Override
    protected void convert(BaseViewHolder helper, @NonNull GoodClassifyEntity classifyEntity) {
//        helper.setText(R.id.tvClassifyName,item.getGoods_name());
        helper.setVisible(R.id.viewIndicator, classifyEntity.isSelect());
        helper.setText(R.id.tvClassifyName, classifyEntity.getName());
        RelativeLayout relativeLayout = helper.getView(R.id.rlBackGround);
        TextView tvClassifyName = helper.getView(R.id.tvClassifyName);
        if (classifyEntity.isSelect()) {
            tvClassifyName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            tvClassifyName.setTextColor(TourCooUtil.getColor(R.color.black));
            relativeLayout.setBackgroundColor(TourCooUtil.getColor(R.color.whiteCommon));
        } else {
            tvClassifyName.setTextColor(TourCooUtil.getColor(R.color.grayCommon999));
            tvClassifyName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            relativeLayout.setBackgroundColor(TourCooUtil.getColor(R.color.colorGrayCommon));
        }

    }
}
