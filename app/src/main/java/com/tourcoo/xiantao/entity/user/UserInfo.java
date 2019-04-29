package com.tourcoo.xiantao.entity.user;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * @author :zhoujian
 * @description :
 * @company :翼迈科技
 * @date 2019年 04月 20日 17时24分
 * @Email: 971613168@qq.com
 */
public class UserInfo extends LitePalSupport implements Serializable {

    /**
     * id : 1
     * username : admin
     * nickname : admin
     * mobile : 13888888888
     * avatar : /assets/img/avatar.png
     * score : 0
     * token : dd6c5f18-2721-4052-bf9c-4a8e686389a1
     * user_id : 1
     * createtime : 1555751877
     * expiretime : 1558343877
     * expires_in : 2592000
     */

    private long id;
    private String username;
    private String nickname;
    private String mobile;
    private String avatar;
    private int score;
    private String token;
    private int user_id;
    private int createtime;
    private int expiretime;
    private int expires_in;
    private String birthday;
    private double ag;
    private int gender;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getAg() {
        return ag;
    }

    public void setAg(double ag) {
        this.ag = ag;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getAu() {
        return au;
    }

    public void setAu(double au) {
        this.au = au;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    private double au;
    private double cash;
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(int expiretime) {
        this.expiretime = expiretime;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
