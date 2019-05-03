package com.tourcoo.xiantao.entity.goods;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :拼团规则实体(Goods内部使用)
 * @company :途酷科技
 * @date 2019年04月25日15:27
 * @Email: 971613168@qq.com
 */
public class TuanRule implements Serializable {
    /**
     * id : 2
     * name : 风光ix5  280TGDI CVT智尊型
     * price : 2
     * weigh : 3
     * num : 5
     * validity : 2
     * status_text :
     */

    private int id;
    private String name;
    private String price;
    private String weigh;
    private int num;
    private int validity;
    private String status_text;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeigh() {
        return weigh;
    }

    public void setWeigh(String weigh) {
        this.weigh = weigh;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}
