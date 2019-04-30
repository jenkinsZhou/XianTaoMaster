package com.tourcoo.xiantao.entity.recharge;

/**
 * @author :JenkinsZhou
 * @description :充值明细实体
 * @company :途酷科技
 * @date 2019年04月30日10:58
 * @Email: 971613168@qq.com
 */
public class RechargeDetail {


    /**
     * id : 165
     * user_id : 11
     * type : 3
     * amount : 1
     * symbol : +
     * cash : 39970
     * createtime : 1556588405
     * updatetime : 1556588433
     * out_trade_no : 5cc7a7751c3af1556588405
     * transaction_id : 4200000296201904308576306815
     * pay_type : wx
     * status : 1
     * createtime_text : 2019-04-30 09:40:05
     * updatetime_text : 2019-04-30 09:40:33
     */

    private int id;
    private int user_id;
    private int type;
    private double amount;
    private String symbol;
    private double cash;
    private long createtime;
    private int updatetime;
    private String out_trade_no;
    private String transaction_id;
    private String pay_type;
    private int status;
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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public long getCreatetime() {
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

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
