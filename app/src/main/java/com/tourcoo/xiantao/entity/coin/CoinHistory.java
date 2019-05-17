package com.tourcoo.xiantao.entity.coin;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月30日11:52
 * @Email: 971613168@qq.com
 */
public class CoinHistory {

    /**
     * total : 1
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":1,"user_id":2,"attr":1,"type":1,"amount":0,"createtime":1555915811,"updatetime":1555915811,"coin":0,"order_id":0,"symbol":"+","type_text":"下单抵扣","createtime_text":"2019-04-22 14:50:11","updatetime_text":"2019-04-22 14:50:11"}]
     * coin : 10
     * au : 523465
     * ag : 60001
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private double coin;
    private int au;
    private double ag;
    private List<CoinDetail> data;

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

    public double getCoin() {
        return coin;
    }

    public void setCoin(double coin) {
        this.coin = coin;
    }

    public int getAu() {
        return au;
    }

    public void setAu(int au) {
        this.au = au;
    }

    public double getAg() {
        return ag;
    }

    public void setAg(int ag) {
        this.ag = ag;
    }

    public List<CoinDetail> getData() {
        return data;
    }

    public void setData(List<CoinDetail> data) {
        this.data = data;
    }


}
