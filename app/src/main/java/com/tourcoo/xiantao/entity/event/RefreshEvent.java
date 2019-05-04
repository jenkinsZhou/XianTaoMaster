package com.tourcoo.xiantao.entity.event;

/**
 * @author :zhoujian
 * @description :刷新事件
 * @company :翼迈科技
 * @date 2019年 05月 01日 21时37分
 * @Email: 971613168@qq.com
 */
public class RefreshEvent extends BaseEvent {
    private String action;

    public RefreshEvent(String action) {
        this.action = action;
    }

    public RefreshEvent() {
    }

    public RefreshEvent(int id) {
        super.id = id;
    }

}
