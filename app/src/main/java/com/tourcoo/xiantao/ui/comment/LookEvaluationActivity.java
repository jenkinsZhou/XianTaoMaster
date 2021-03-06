package com.tourcoo.xiantao.ui.comment;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.previewlibrary.GPreviewBuilder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.GridCommentImageAdapter;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.ImageEntity;
import com.tourcoo.xiantao.entity.comment.CommentDetail;
import com.tourcoo.xiantao.entity.comment.CommentEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.tourcoo.xiantao.widget.ratingstar.RatingStarView;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;


/**
 * @author :JenkinsZhou
 * @description :查看评价
 * @company :途酷科技
 * @date 2019年04月11日21:02
 * @Email: 971613168@qq.com
 */
public class LookEvaluationActivity extends BaseTourCooTitleMultiViewActivity {
    private RecyclerView rvImageComment;
    private List<String> imageUrList = new ArrayList<>();
    private TextView tvCommentDetail;
    private RatingStarView rsvRating;
    private GridCommentImageAdapter gridImageAdapter;
    private TextView tvCommentTime;
    private CircleImageView civAvatar;
    private TextView tvNickName;
    private int orderId;
    public static final String EXTRA_ORDER_ID = "EXTRA_ORDER_ID";
    private LinearLayout llRootView;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_comment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        llRootView = findViewById(R.id.llRootView);
        orderId = getIntent().getIntExtra(EXTRA_ORDER_ID, -1);
        tvNickName = findViewById(R.id.tvNickName);
        civAvatar = findViewById(R.id.civAvatar);
        rvImageComment = findViewById(R.id.rvImageComment);
        rsvRating = findViewById(R.id.rsvRating);
        tvCommentDetail = findViewById(R.id.tvCommentDetail);
        tvCommentTime = findViewById(R.id.tvCommentTime);
        gridImageAdapter = new GridCommentImageAdapter(imageUrList);
        gridImageAdapter.bindToRecyclerView(rvImageComment);
        rvImageComment.setLayoutManager(new GridLayoutManager(mContext, 4));
        gridImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                onThumbnailClick(view, gridImageAdapter.getData().get(position));
                List<ImageEntity> imageEntityList = parseImageEntityList(gridImageAdapter.getData());
                computeBoundsBackward(rvImageComment, imageEntityList);
                GPreviewBuilder.from(LookEvaluationActivity.this)
                        .setData(imageEntityList)
                        .setCurrentIndex(position)
                        .setSingleFling(true)
                        .setType(GPreviewBuilder.IndicatorType.Number)
                        .start();
            }
        });


    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("我的评价");
    }


    @Override
    public void loadData() {
        super.loadData();
      /*  int id;
        try {
            id = Integer.parseInt(orderId);
        } catch (NumberFormatException e) {
            id = 0;
        }*/
        requestOrderCommentList(orderId);
    }


    private CommentDetail getDetail(List<CommentDetail> commentDetailList) {
        if (commentDetailList == null || commentDetailList.isEmpty()) {
            TourCooLogUtil.e(TAG, "参数为null");
            return null;
        }
        return commentDetailList.get(0);
    }


    private void showDetail(CommentDetail commentDetail) {
        if (commentDetail == null) {
             TourCooLogUtil.i(TAG,TAG+"mStatusLayoutManager:"+mStatusLayoutManager );
            showEmtyLayout("未获取到评价信息");
            return;
        }
        GlideManager.loadCircleImg(TourCooUtil.getUrl(commentDetail.getAvatar()), civAvatar, R.mipmap.img_default_avatar);
        tvCommentDetail.setText(getNotNullValue(commentDetail.getDetail()));
        rsvRating.setRating(commentDetail.getStar());
        rsvRating.setEnabled(false);
        tvNickName.setText(commentDetail.getNickname());
        tvCommentTime.setText(getNotNullValue(DateUtil.parseDate(commentDetail.getCreatetime())));
        String images = getNotNullValue(commentDetail.getImages());
        List<String> imageList = Arrays.asList(images.split(","));
        for (int i = 0; i < imageList.size(); i++) {
            if (!TextUtils.isEmpty(imageList.get(i))) {
//                imageList.set(i, RequestConfig.BASE + imageList.get(i));
                imageList.set(i, TourCooUtil.getUrl(imageList.get(i)));
            }
        }
        imageUrList.addAll(imageList);
        for (int i = imageUrList.size() - 1; i >= 0; i--) {
            if (TextUtils.isEmpty(imageUrList.get(i))) {
                imageUrList.remove(i);
            }
        }
        for (String s : imageUrList) {
            TourCooLogUtil.i(TAG, TAG + ":" + "图片url:" + s);
        }
        gridImageAdapter.notifyDataSetChanged();
        showSuccessLayout();
    }

    private String getNotNullValue(String value) {
        return TourCooUtil.getNotNullValue(value);
    }


    private ImageView getView() {
        ImageView imgView = new ImageView(this);
        imgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imgView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        return imgView;
    }


    public void onThumbnailClick(View v, String imageUrl) {
// 全屏显示的方法
        /* android.R.style.Theme_Black_NoTitleBar_Fullscreen*/
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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


    /**
     * 查询评价记录
     */
    private void requestOrderCommentList(int orderId) {
        TourCooLogUtil.i(TAG, TAG + "评价的订单id:" + orderId);
        showLoadingLayout();
        ApiRepository.getInstance().requestOrderCommentList(orderId, 1).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<CommentEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<CommentEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                CommentEntity commentEntity = entity.data;
                                if (commentEntity.getData() != null) {
                                    showDetail(getDetail(commentEntity.getData()));
                                } else {
                                    showEmtyLayout("暂无评价");
                                }
                            } else {
                                showEmtyLayout(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        showEmtyLayout();
                    }
                });
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


    @Override
    protected IMultiStatusView getMultiStatusView() {
        return new IMultiStatusView() {
            @Override
            public View getMultiStatusContentView() {
                return llRootView;
            }

            @Override
            public void setMultiStatusView(StatusLayoutManager.Builder statusView) {

            }

            @Override
            public View.OnClickListener getEmptyClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestOrderCommentList(orderId);
                    }
                };
            }

            @Override
            public View.OnClickListener getErrorClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestOrderCommentList(orderId);
                    }
                };
            }

            @Override
            public View.OnClickListener getCustomerClickListener() {
                return new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestOrderCommentList(orderId);
                    }
                };
            }
        };
    }
}
