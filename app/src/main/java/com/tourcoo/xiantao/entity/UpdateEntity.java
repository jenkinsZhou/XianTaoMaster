package com.tourcoo.xiantao.entity;

import android.text.TextUtils;

/**
 * @author :zhoujian
 * @description : 版本更新实体类
 * @company :途酷科技
 * @date 2019年03月27日15:42
 * @Email: 971613168@qq.com
 */
public class UpdateEntity {

    public int versionCode;
    public String versionName;
    public String url;
    public boolean force;
    public String message;
    public String size;


    public CharSequence getSize() {
        return TextUtils.isEmpty(size) ? "下载新版本" : "安装包大小:" + size;
    }

    public CharSequence getTitle() {
        return TextUtils.isEmpty(versionName) ? "下载文件" : "下载新版:V" + versionName;
    }

}
