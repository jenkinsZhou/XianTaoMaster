package com.tourcoo.xiantao.entity.event;

/**
 * @author :JenkinsZhou
 * @description :tab切换事件
 * @company :途酷科技
 * @date 2019年04月17日14:31
 * @Email: 971613168@qq.com
 */
public class TabChangeEvent {
    public int tag;
    public int currentPosition;

    public TabChangeEvent(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public TabChangeEvent(int tag, int currentPosition) {
        this.tag = tag;
        this.currentPosition = currentPosition;
    }


}
