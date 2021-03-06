package com.tourcoo.xiantao.entity;

/**
 * @author :JenkinsZhou
 * @description :系统信息实体类
 * @company :途酷科技
 * @date 2019年05月05日13:45
 * @Email: 971613168@qq.com
 */
public class SystemSettingEntity {
    /**
     * cashrule :
     * ios_version : 1.0.1
     * ios_info :
     * ios_update : 0
     * ios_download :
     * android_version : 1.0.1
     * android_info :
     * android_update : 0
     * android_download :
     * kefu : 000-0000000
     */
    private String register;
    private String address;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    private String company;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKefu_wx() {
        return kefu_wx;
    }

    public void setKefu_wx(String kefu_wx) {
        this.kefu_wx = kefu_wx;
    }

    public String getKefu_qq() {
        return kefu_qq;
    }

    public void setKefu_qq(String kefu_qq) {
        this.kefu_qq = kefu_qq;
    }

    private String kefu_wx;

    private String kefu_qq;

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    private String cashrule;
    private String ios_version;
    private String ios_info;
    private String ios_update;
    private String ios_download;
    private String android_version;
    private String android_info;
    private int android_update;
    private String android_download;
    private String kefu;
    private int android_version_code;

    public int getAndroid_version_code() {
        return android_version_code;
    }

    public void setAndroid_version_code(int android_version_code) {
        this.android_version_code = android_version_code;
    }

    public String getCashrule() {
        return cashrule;
    }

    public void setCashrule(String cashrule) {
        this.cashrule = cashrule;
    }

    public String getIos_version() {
        return ios_version;
    }

    public void setIos_version(String ios_version) {
        this.ios_version = ios_version;
    }

    public String getIos_info() {
        return ios_info;
    }

    public void setIos_info(String ios_info) {
        this.ios_info = ios_info;
    }

    public String getIos_update() {
        return ios_update;
    }

    public void setIos_update(String ios_update) {
        this.ios_update = ios_update;
    }

    public String getIos_download() {
        return ios_download;
    }

    public void setIos_download(String ios_download) {
        this.ios_download = ios_download;
    }

    public String getAndroid_version() {
        return android_version;
    }

    public void setAndroid_version(String android_version) {
        this.android_version = android_version;
    }

    public String getAndroid_info() {
        return android_info;
    }

    public void setAndroid_info(String android_info) {
        this.android_info = android_info;
    }

    public int getAndroid_update() {
        return android_update;
    }

    public void setAndroid_update(int android_update) {
        this.android_update = android_update;
    }

    public String getAndroid_download() {
        return android_download;
    }

    public void setAndroid_download(String android_download) {
        this.android_download = android_download;
    }

    public String getKefu() {
        return kefu;
    }

    public void setKefu(String kefu) {
        this.kefu = kefu;
    }
}
