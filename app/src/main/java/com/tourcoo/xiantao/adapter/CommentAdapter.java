package com.tourcoo.xiantao.adapter;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.common.RequestConfig;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.comment.CommentDetail;
import com.tourcoo.xiantao.entity.comment.CommentEntity;
import com.tourcoo.xiantao.widget.ratingstar.RatingStarView;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author :JenkinsZhou
 * @description :评论适配器
 * @company :途酷科技
 * @date 2019年04月13日15:56
 * @Email: 971613168@qq.com
 */
public class CommentAdapter extends BaseQuickAdapter<CommentDetail, BaseViewHolder> {
    private Drawable mDrawable;

    public CommentAdapter() {
        super(R.layout.item_comment);
        mDrawable = TourCooUtil.getDrawable(R.mipmap.img_default_avatar);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentDetail item) {
        helper.setText(R.id.tvNickName, TourCooUtil.getNotNullValue(item.getNickname()));
        helper.setText(R.id.tvTime, DateUtil.parseDate(item.getCreatetime()));
        helper.setText(R.id.tvComment, TourCooUtil.getNotNullValue(item.getDetail()));
        CircleImageView circleImageView = helper.getView(R.id.civAvatar);
        if (!TextUtils.isEmpty(item.getImages())) {
            List<String> imageUrlList = new ArrayList<>();
            if (item.getImgs_arr() != null) {
                ArrayList<String> imageArray = (ArrayList<String>) item.getImgs_arr();
                for (String image : imageArray) {
                    if (!TextUtils.isEmpty(image)) {
                        imageUrlList.add(TourCooUtil.getUrl(image));
                        TourCooLogUtil.i(TAG, "value:" + "添加的url:" + TourCooUtil.getUrl(image));
                    }
                }
            }

            RecyclerView commentImageRecyclerView = helper.getView(R.id.commentImageRecyclerView);
            commentImageRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            GridImageAdapter gridImageAdapter = new GridImageAdapter(imageUrlList);
            gridImageAdapter.bindToRecyclerView(commentImageRecyclerView);
            gridImageAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    onThumbnailClick(imageUrlList.get(position));
                }
            });
        }else {
            RecyclerView commentImageRecyclerView = helper.getView(R.id.commentImageRecyclerView);
            commentImageRecyclerView.setAdapter(null);
        }

        RatingStarView ratingStarView = helper.getView(R.id.rsvRating);
        if (ratingStarView != null) {
            ratingStarView.setEnabled(false);
            ratingStarView.setRating(item.getStar());
        }
        String imageUrl = TourCooUtil.getUrl(item.getAvatar());
        GlideManager.loadImg(imageUrl, circleImageView, mDrawable);
    }


    public void onThumbnailClick(String imageUrl) {
// 全屏显示的方法
        /* android.R.style.Theme_Black_NoTitleBar_Fullscreen*/
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ImageView imgView = getView();
        dialog.setContentView(imgView);
        dialog.show();
        GlideManager.loadImg(imageUrl, imgView);
// 点击图片消失
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private ImageView getView() {
        ImageView imgView = new ImageView(mContext);
        imgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imgView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        return imgView;
    }
}





