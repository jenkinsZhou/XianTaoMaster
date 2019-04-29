package com.tourcoo.xiantao.entity.message;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月29日20:21
 * @Email: 971613168@qq.com
 */
public class MessageBean implements Serializable {
    /**
     * id : 33
     * detail : 34234234234234
     * status : 0
     * createtime : 2019-04-29 11:23:22
     */

    private int id;
    private String detail;
    private int status;
    private String createtime;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }



}
