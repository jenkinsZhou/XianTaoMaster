package com.tourcoo.xiantao.entity.event;

/**
 * @author :zhoujian
 * @description :
 * @company :翼迈科技
 * @date 2019年 05月 04日 09时22分
 * @Email: 971613168@qq.com
 */
public class MessageEvent  {

    private int msgCount;

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public MessageEvent() {
    }

    public MessageEvent(int msgCount) {
        this.msgCount = msgCount;
    }
}
