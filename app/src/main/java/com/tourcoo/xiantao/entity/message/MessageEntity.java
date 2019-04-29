package com.tourcoo.xiantao.entity.message;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月29日19:03
 * @Email: 971613168@qq.com
 */
public class MessageEntity implements Serializable {


    /**
     * total : 4
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":33,"detail":"34234234234234","status":0,"createtime":"2019-04-29 11:23:22"},{"id":24,"detail":"消息33333","status":0,"createtime":"2019-04-28 16:47:57"},{"id":16,"detail":"消息22222","status":0,"createtime":"2019-04-28 16:47:48"},{"id":8,"detail":"消息11111","status":0,"createtime":"2019-04-28 16:47:22"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<MessageBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<MessageBean> getData() {
        return data;
    }

    public void setData(List<MessageBean> data) {
        this.data = data;
    }


}
