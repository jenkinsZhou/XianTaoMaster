package com.tourcoo.xiantao.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :评价图片
 * @company :途酷科技
 * @date 2019年06月11日12:30
 * @Email: 971613168@qq.com
 */
public class GridCommentImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public GridCommentImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_grid_comment_image, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideManager.loadImg(item, helper.getView(R.id.additionalRoundedImageView));
    }
}
