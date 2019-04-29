package com.tourcoo.xiantao.entity.goods;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :规则实体
 * @company :途酷科技
 * @date 2019年04月25日15:30
 * @Email: 971613168@qq.com
 */
public class Rule implements Serializable {
    /**
     * rule_id : 52
     * litestore_freight_id : 26
     * region : 1047,1057,1066,1074,1081,1088,1093,1098,1110,1118,1127,1136,1142,1150,1155,1160
     * first : 1
     * first_fee : 10.00
     * additional : 1
     * additional_fee : 1.00
     * weigh : 0
     * create_time : 1555318901
     * region_data : ["1047","1057","1066","1074","1081","1088","1093","1098","1110","1118","1127","1136","1142","1150","1155","1160"]
     */

    private int rule_id;
    private int litestore_freight_id;
    private String region;
    private int first;
    private String first_fee;
    private int additional;
    private String additional_fee;
    private int weigh;
    private int create_time;
    private List<String> region_data;

    public int getRule_id() {
        return rule_id;
    }

    public void setRule_id(int rule_id) {
        this.rule_id = rule_id;
    }

    public int getLitestore_freight_id() {
        return litestore_freight_id;
    }

    public void setLitestore_freight_id(int litestore_freight_id) {
        this.litestore_freight_id = litestore_freight_id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public String getFirst_fee() {
        return first_fee;
    }

    public void setFirst_fee(String first_fee) {
        this.first_fee = first_fee;
    }

    public int getAdditional() {
        return additional;
    }

    public void setAdditional(int additional) {
        this.additional = additional;
    }

    public String getAdditional_fee() {
        return additional_fee;
    }

    public void setAdditional_fee(String additional_fee) {
        this.additional_fee = additional_fee;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public List<String> getRegion_data() {
        return region_data;
    }

    public void setRegion_data(List<String> region_data) {
        this.region_data = region_data;
    }
}
