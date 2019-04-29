package com.tourcoo.xiantao.entity.goods;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :配送信息实体
 * @company :途酷科技
 * @date 2019年04月25日15:31
 * @Email: 971613168@qq.com
 */
public class Freight implements Serializable {

    /**
     * id : 26
     * name : 省内
     * method : 20
     * weigh : 26
     * createtime : 1555318901
     * updatetime : 1555318901
     * rule : [{"rule_id":52,"litestore_freight_id":26,"region":"1047,1057,1066,1074,1081,1088,1093,1098,1110,1118,1127,1136,1142,1150,1155,1160","first":1,"first_fee":"10.00","additional":1,"additional_fee":"1.00","weigh":0,"create_time":1555318901,"region_data":["1047","1057","1066","1074","1081","1088","1093","1098","1110","1118","1127","1136","1142","1150","1155","1160"]}]
     * method_text : Method 20
     */

    private int id;
    private String name;
    private String method;
    private int weigh;
    private int createtime;
    private int updatetime;
    private String method_text;
    private List<Rule> rule;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
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

    public String getMethod_text() {
        return method_text;
    }

    public void setMethod_text(String method_text) {
        this.method_text = method_text;
    }

    public List<Rule> getRule() {
        return rule;
    }

    public void setRule(List<Rule> rule) {
        this.rule = rule;
    }


}
