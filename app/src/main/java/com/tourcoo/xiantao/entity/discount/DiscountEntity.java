package com.tourcoo.xiantao.entity.discount;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月12日17:06
 * @Email: 971613168@qq.com
 */
public class DiscountEntity {


    /**
     * total : 3
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":2,"user_id":2,"rule_id":2,"name":"测试优惠券","worth":10,"cost":20,"status":"normal","createtime":1557649556,"updatetime":1557649556,"admin_id":1,"deadline":1560241556,"source":"注册登录","num":2,"status_text":"正常"},{"id":3,"user_id":2,"rule_id":1,"name":"注册礼券","worth":10,"cost":50,"status":"normal","createtime":1557649636,"updatetime":1557649636,"admin_id":1,"deadline":1560241636,"source":"测试","num":10,"status_text":"正常"},{"id":4,"user_id":2,"rule_id":2,"name":"测试优惠券","worth":10,"cost":20,"status":"normal","createtime":1557649718,"updatetime":1557649718,"admin_id":1,"deadline":1560241718,"source":"测试2","num":2,"status_text":"正常"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DiscountInfo> data;

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

    public List<DiscountInfo> getData() {
        return data;
    }

    public void setData(List<DiscountInfo> data) {
        this.data = data;
    }


}
