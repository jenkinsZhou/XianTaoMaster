package com.tourcoo.xiantao.entity.discount;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月12日14:52
 * @Email: 971613168@qq.com
 */
public class as {

    /**
     * id : 1
     * rule_id : 1
     * name : 优惠券
     * worth : 20
     * deadline : 截止时间
     * cost : 50
     * rule : 可叠加 / 不可叠加
     * num : 3
     */

    private int id;
    private String rule_id;
    private String name;
    private String worth;
    private String deadline;
    private String cost;
    private String rule;
    private String num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRule_id() {
        return rule_id;
    }

    public void setRule_id(String rule_id) {
        this.rule_id = rule_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorth() {
        return worth;
    }

    public void setWorth(String worth) {
        this.worth = worth;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
