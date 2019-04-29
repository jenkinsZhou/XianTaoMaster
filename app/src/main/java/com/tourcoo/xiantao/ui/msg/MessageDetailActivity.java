package com.tourcoo.xiantao.ui.msg;

import android.os.Bundle;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.message.MessageBean;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;

import static com.tourcoo.xiantao.ui.msg.MsgSystemActivity.EXTRA_MESSAGE_DETAIL;

/**
 * @author :JenkinsZhou
 * @description :消息详情
 * @company :途酷科技
 * @date 2019年04月29日19:33
 * @Email: 971613168@qq.com
 */
public class MessageDetailActivity extends BaseTourCooTitleActivity {
    private TextView tvMsgContent;
    private TextView tvMsgTime;

    @Override
    public int getContentLayout() {
        return R.layout.activity_message_detail;
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("消息详情");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvMsgContent = findViewById(R.id.tvMsgContent);
        tvMsgTime = findViewById(R.id.tvMsgTime);
        MessageBean mMessageInfo = (MessageBean) getIntent().getSerializableExtra(EXTRA_MESSAGE_DETAIL);
        showMessageDetail(mMessageInfo);
    }


    private void showMessageDetail(MessageBean messageInfo) {
        if (messageInfo == null) {
            ToastUtil.showFailed("未获取到消息内容");
            return;
        }
        tvMsgContent.setText(TourCooUtil.getNotNullValue(messageInfo.getDetail()));
        tvMsgTime.setText(TourCooUtil.getNotNullValue(messageInfo.getCreatetime()));
    }




}
