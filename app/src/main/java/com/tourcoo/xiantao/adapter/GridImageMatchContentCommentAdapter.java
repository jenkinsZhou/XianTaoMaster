package com.tourcoo.xiantao.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :评价图片适配器
 * @company :途酷科技
 * @date 2019年06月11日12:39
 * @Email: 971613168@qq.com
 */
public class GridImageMatchContentCommentAdapter  extends BaseQuickAdapter<String, BaseViewHolder> {
    public GridImageMatchContentCommentAdapter(@Nullable List<String> data) {
        super(R.layout.item_grid_image_match_content_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideManager.loadImg(item, helper.getView(R.id.additionalRoundedImageView));
    }
}
