package com.tourcoo.xiantao.ui.msg;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.SystemMsgAdapter;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.base.activity.BaseRefreshLoadActivity;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.message.MessageEntity;
import com.tourcoo.xiantao.entity.event.BaseEvent;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;

import static com.tourcoo.xiantao.core.common.EventConstant.EVENT_REQUEST_MSG_COUNT;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;


/**
 * @author :JenkinsZhou
 * @description :系统消息页面
 * @company :途酷科技
 * @date 2019年03月18日13:53
 * @Email: 971613168@qq.com
 */
public class MsgSystemActivity extends BaseRefreshLoadActivity<MessageEntity> {
    private SystemMsgAdapter systemMsgAdapter;
    public static final String EXTRA_MESSAGE_DETAIL = "EXTRA_MESSAGE_DETAIL";
    public static final int EXTRA_MESSAGE_CODE = 102;
    @Override
    public int getContentLayout() {
        return R.layout.layout_title_refresh_recycler;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        getMsgList(true, 1);
    }

    @Override
    public BaseQuickAdapter getAdapter() {
        systemMsgAdapter = new SystemMsgAdapter();
        return systemMsgAdapter;
    }

    @Override
    public void loadData(int page) {
        TourCooLogUtil.d("loadData:" + page);
        if (page == 0) {
            page = 1;
        }
        getMsgList(false, page);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        super.onRefresh(refreshlayout);
        mDefaultPage = 1;
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();
        TourCooLogUtil.d("onLoadMoreRequested:");
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        TextView textView = titleBar.getTextView(Gravity.CENTER | Gravity.TOP);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        titleBar.setTitleMainText("消息中心");
    }

    /**
     * 系统消息
     */
    private void getMsgList(boolean isShowLoading, int pageIndex) {
        if (isShowLoading) {
            ApiRepository.getInstance().requestMessageList(pageIndex).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                    subscribe(new BaseLoadingObserver<BaseEntity<MessageEntity>>() {
                        @Override
                        public void onRequestNext(BaseEntity<MessageEntity> entity) {
                            if (entity != null) {
                                if (entity.code == CODE_REQUEST_SUCCESS) {
                                    MessageEntity messageEntity = entity.data;
                                    if (messageEntity != null) {
                                        UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), messageEntity.getData() == null ? new ArrayList<>() : messageEntity.getData(), null);
                                    }
                                } else {
                                    ToastUtil.showFailed(entity.msg);
                                }
                            }
                        }
                    });

        } else {
            ApiRepository.getInstance().requestMessageList(pageIndex).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                    subscribe(new BaseObserver<BaseEntity<MessageEntity>>() {
                        @Override
                        public void onRequestNext(BaseEntity<MessageEntity> entity) {
                            if (entity != null) {
                                if (entity.code == CODE_REQUEST_SUCCESS) {
                                    MessageEntity messageEntity = entity.data;
                                    if (messageEntity != null) {
                                        UiConfigManager.getInstance().getHttpRequestControl().httpRequestSuccess(getIHttpRequestControl(), messageEntity.getData() == null ? new ArrayList<>() : messageEntity.getData(), null);
                                    }
                                } else {
                                    ToastUtil.showFailed(entity.msg);
                                }
                            }
                        }
                    });
        }
    }


    @Override
    public void finish() {
        //发送消息
        EventBus.getDefault().post(new BaseEvent(EVENT_REQUEST_MSG_COUNT));
        super.finish();
    }


    private void initItemClick() {
        systemMsgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_MESSAGE_DETAIL, systemMsgAdapter.getData().get(position));
                intent.setClass(mContext, MessageDetailActivity.class);
                startActivityForResult(intent,EXTRA_MESSAGE_CODE);
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        initItemClick();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EXTRA_MESSAGE_CODE:
                 TourCooLogUtil.i(TAG,TAG+":"+ "刷新了列表");
                getMsgList(false, 1);
                break;
            default:
                break;
        }
    }
}
