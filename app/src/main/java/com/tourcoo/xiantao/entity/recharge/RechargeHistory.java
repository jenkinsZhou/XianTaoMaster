package com.tourcoo.xiantao.entity.recharge;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :充值记录实体
 * @company :途酷科技
 * @date 2019年04月30日10:56
 * @Email: 971613168@qq.com
 */
public class RechargeHistory {

    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":165,"user_id":11,"type":3,"amount":1,"symbol":"+","cash":39970,"createtime":1556588405,"updatetime":1556588433,"out_trade_no":"5cc7a7751c3af1556588405","transaction_id":"4200000296201904308576306815","pay_type":"wx","status":1,"createtime_text":"2019-04-30 09:40:05","updatetime_text":"2019-04-30 09:40:33"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<RechargeDetail> data;

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

    public List<RechargeDetail> getData() {
        return data;
    }

    public void setData(List<RechargeDetail> data) {
        this.data = data;

    }
}
