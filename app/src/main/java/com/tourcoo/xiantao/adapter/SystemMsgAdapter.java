package com.tourcoo.xiantao.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.message.MessageBean;


/**
 * @author :JenkinsZhou
 * @description :系统消息适配器
 * @company :途酷科技
 * @date 2019年03月18日13:59
 * @Email: 971613168@qq.com
 */
public class SystemMsgAdapter extends BaseQuickAdapter<MessageBean, BaseViewHolder> {
    public SystemMsgAdapter() {
        super(R.layout.item_system_msg);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.setText(R.id.tvMsgTime, item.getCreatetime());
        helper.setText(R.id.tvMsgContent, item.getDetail());
        if (item.getStatus() == 1) {
            helper.setVisible(R.id.tvRedDot, false);
        } else {
            helper.setVisible(R.id.tvRedDot, true);
        }

    }
}
