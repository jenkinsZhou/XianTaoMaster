package com.tourcoo.xiantao.entity;

/**
 * @author :JenkinsZhou
 * @description :token
 * @company :途酷科技
 * @date 2019年04月21日16:08
 * @Email: 971613168@qq.com
 */
public class TokenInfo {

    /**
     * token : a73b941a-47ca-4e46-9c81-e1a3af559273
     * expires_in : 2591750
     */

    private String token;
    private int expires_in;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
