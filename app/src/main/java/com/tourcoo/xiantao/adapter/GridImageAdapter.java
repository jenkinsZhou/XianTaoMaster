package com.tourcoo.xiantao.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :横向图片适配器
 * @company :途酷科技
 * @date 2019年03月22日17:05
 * @Email: 971613168@qq.com
 */
public class GridImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public GridImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_grid_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideManager.loadImg(item, helper.getView(R.id.additionalRoundedImageView));
         TourCooLogUtil.i(TAG,TAG+"图片地址:"+ item);
    }
}
