package com.tourcoo.xiantao.entity.user;


import org.litepal.crud.LitePalSupport;

/**
 * @author :JenkinsZhou
 * @description :个人中心实体
 * @company :途酷科技
 * @date 2019年04月27日17:01
 * @Email: 971613168@qq.com
 */
public class PersonalCenterInfo extends LitePalSupport {

    /**
     * ag : 1
     * au : 1
     * avatar : /uploads/20190427/43597faeb457812746578ca99bcc893b.png
     * birthday : 0000-00-00
     * cash : 1
     * gender : 0
     * mobile : 18256070563
     * nickname : 周健
     * nocomment : 0
     * nofreight : 0
     * nopay : 20
     * noreceipt : 0
     * return : 0
     */
    /**
     * 银币
     */
    private double ag;
    /**
     * 金币
     */
    private int au;
    private String avatar;
    private String birthday;
    private String cash;
    private int gender;
    private String mobile;
    private String nickname;
    private int nocomment;
    private int nofreight;
    private int nopay;
    private int noreceipt;
    private int returnnum;

    public double getAg() {
        return ag;
    }

    public void setAg(double ag) {
        this.ag = ag;
    }

    public int getAu() {
        return au;
    }

    public void setAu(int au) {
        this.au = au;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getNocomment() {
        return nocomment;
    }

    public void setNocomment(int nocomment) {
        this.nocomment = nocomment;
    }

    public int getNofreight() {
        return nofreight;
    }

    public void setNofreight(int nofreight) {
        this.nofreight = nofreight;
    }

    public int getNopay() {
        return nopay;
    }

    public void setNopay(int nopay) {
        this.nopay = nopay;
    }

    public int getNoreceipt() {
        return noreceipt;
    }

    public void setNoreceipt(int noreceipt) {
        this.noreceipt = noreceipt;
    }


    public int getReturnnum() {
        return returnnum;
    }

    public void setReturnnum(int returnnum) {
        this.returnnum = returnnum;
    }
}
