package com.tourcoo.xiantao.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.previewlibrary.GPreviewBuilder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.ImageEntity;
import com.tourcoo.xiantao.entity.comment.CommentDetail;
import com.tourcoo.xiantao.widget.ratingstar.RatingStarView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author :JenkinsZhou
 * @description :全部评论适配器
 * @company :途酷科技
 * @date 2019年05月31日9:48
 * @Email: 971613168@qq.com
 */
public class AllCommentAdapter extends BaseQuickAdapter<CommentDetail, BaseViewHolder> {
    private Drawable mDrawable;

    public AllCommentAdapter() {
        super(R.layout.item_comment_all);
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
            GridImageMatchContentCommentAdapter gridImageAdapter = new GridImageMatchContentCommentAdapter(imageUrlList);
            gridImageAdapter.bindToRecyclerView(commentImageRecyclerView);
            gridImageAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (mContext instanceof Activity) {
                        List<ImageEntity> imageEntityList = parseImageEntityList(gridImageAdapter.getData());
                        computeBoundsBackward(commentImageRecyclerView, imageEntityList);
                        GPreviewBuilder.from((Activity) mContext)
                                .setData(imageEntityList)
                                .setCurrentIndex(position)
                                .setSingleFling(true)
                                .setType(GPreviewBuilder.IndicatorType.Number)
                                .start();
                    } else {
                        onThumbnailClick(imageUrlList.get(position));
                    }

                }
            });
        } else {
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


    private List<ImageEntity> parseImageEntityList(List<String> imageUrlList) {
        List<ImageEntity> imageEntityList = new ArrayList<>();
        if (imageUrlList == null || imageUrlList.isEmpty()) {
            return imageEntityList;
        }
        ImageEntity imageEntity;
        for (String url : imageUrlList) {
            imageEntity = new ImageEntity();
            imageEntity.setImageUrl(url);
            imageEntityList.add(imageEntity);
        }
        return imageEntityList;
    }

    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(RecyclerView imageRecyclerView, List<ImageEntity> imageEntityList) {
        if (imageRecyclerView == null || !(imageRecyclerView.getLayoutManager() instanceof GridLayoutManager)) {
            return;
        }
        GridLayoutManager gridLayoutManager = (GridLayoutManager) imageRecyclerView.getLayoutManager();
        int firstCompletelyVisiblePos = gridLayoutManager.findFirstVisibleItemPosition();
        for (int i = firstCompletelyVisiblePos; i < imageEntityList.size(); i++) {
            View itemView = gridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = itemView.findViewById(R.id.additionalRoundedImageView);
                thumbView.getGlobalVisibleRect(bounds);
            }
            imageEntityList.get(i).setBounds(bounds);
        }
    }
}
