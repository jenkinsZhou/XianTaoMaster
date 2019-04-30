package com.tourcoo.xiantao.entity.coin;

/**
 * @author :JenkinsZhou
 * @description :积分兑换详情实体
 * @company :途酷科技
 * @date 2019年04月30日11:53
 * @Email: 971613168@qq.com
 */
public class CoinDetail {
    /**
     * id : 1
     * user_id : 2
     * attr : 1
     * type : 1
     * amount : 0
     * createtime : 1555915811
     * updatetime : 1555915811
     * coin : 0
     * order_id : 0
     * symbol : +
     * type_text : 下单抵扣
     * createtime_text : 2019-04-22 14:50:11
     * updatetime_text : 2019-04-22 14:50:11
     */

    private int id;
    private int user_id;
    private int attr;
    private int type;
    private double amount;
    private int createtime;
    private int updatetime;
    private int coin;
    private int order_id;
    private String symbol;
    private String type_text;
    private String createtime_text;
    private String updatetime_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAttr() {
        return attr;
    }

    public void setAttr(int attr) {
        this.attr = attr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType_text() {
        return type_text;
    }

    public void setType_text(String type_text) {
        this.type_text = type_text;
    }

    public String getCreatetime_text() {
        return createtime_text;
    }

    public void setCreatetime_text(String createtime_text) {
        this.createtime_text = createtime_text;
    }

    public String getUpdatetime_text() {
        return updatetime_text;
    }

    public void setUpdatetime_text(String updatetime_text) {
        this.updatetime_text = updatetime_text;
    }
}
