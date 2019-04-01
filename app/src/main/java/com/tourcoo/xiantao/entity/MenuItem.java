package com.tourcoo.xiantao.entity;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年03月29日10:11
 * @Email: 971613168@qq.com
 */
public class MenuItem {
    public int menuImageId;

    public MenuItem(int menuImageId, String menuLabel) {
        this.menuImageId = menuImageId;
        this.menuLabel = menuLabel;
    }

    public String menuLabel;
}
