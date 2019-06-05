package com.tourcoo.xiantao.entity.event;

/**
 * @author :JenkinsZhou
 * @description :登录成功事件
 * @company :途酷科技
 * @date 2019年06月05日15:48
 * @Email: 971613168@qq.com
 */
public class LoginEvent {
    public int id;
    public String tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
