package com.tourcoo.xiantao.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月31日10:07
 * @Email: 971613168@qq.com
 */
public class GridImageMatchContentAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public GridImageMatchContentAdapter(@Nullable List<String> data) {
        super(R.layout.item_grid_image_match_content, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideManager.loadImg(item, helper.getView(R.id.additionalRoundedImageView));
    }
}