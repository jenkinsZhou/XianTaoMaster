package com.tourcoo.xiantao.ui.comment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.CoinHistoryAdapter;
import com.tourcoo.xiantao.adapter.CommentAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.dialog.alert.ConfirmDialog;
import com.tourcoo.xiantao.core.widget.divider.TourCoolRecycleViewDivider;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.coin.CoinDetail;
import com.tourcoo.xiantao.entity.coin.CoinHistory;
import com.tourcoo.xiantao.entity.comment.CommentDetail;
import com.tourcoo.xiantao.entity.comment.CommentEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooRefreshLoadActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.ArrayList;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.goods.HomeFragment.EXTRA_GOODS_ID;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月30日17:57
 * @Email: 971613168@qq.com
 */
public class CommentListActivity extends BaseTourCooRefreshLoadActivity<CommentDetail> {
    private CommentAdapter adapter;
    private int goodsId;

    @Override
    public int getContentLayout() {
        return R.layout.activity_comment_list;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        goodsId = getIntent().getIntExtra(EXTRA_GOODS_ID, -1);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("全部评价");
    }


    @Override
    public BaseQuickAdapter<CommentDetail, BaseViewHolder> getAdapter() {
        adapter = new CommentAdapter();
        return adapter;
    }

    @Override
    public void loadData(int page) {
        requestCommentList(goodsId, page);
    }

    @Override
    public void loadData() {
        super.loadData();
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mDefaultPage = 1;
        super.onRefresh(refreshlayout);
    }


    /**
     * 查询评价记录
     */
    private void requestCommentList(int orderId, int page) {
        ApiRepository.getInstance().requestCommentList(orderId, page).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<CommentEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<CommentEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                CommentEntity commentEntity = entity.data;
                                UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), commentEntity.getData() == null ? new ArrayList<>() : commentEntity.getData(), null);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mStatusManager.showErrorLayout();
                    }
                });
    }


}
