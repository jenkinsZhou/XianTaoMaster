package com.tourcoo.xiantao.entity.spec;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月26日9:42
 * @Email: 971613168@qq.com
 */
public class Form implements Serializable {

    /**
     * goods_no : mac_0001
     * goods_price : 12608.00
     * goods_weight : 1.2
     * line_price : 0.00
     * stock_num : 989
     * spec_image :
     * imgshow : null
     */

    private String goods_no;
    private double goods_price;
    private double goods_weight;
    private String line_price;
    private int stock_num;
    private String spec_image;
    private Object imgshow;

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public double getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(double goods_weight) {
        this.goods_weight = goods_weight;
    }

    public String getLine_price() {
        return line_price;
    }

    public void setLine_price(String line_price) {
        this.line_price = line_price;
    }

    public int getStock_num() {
        return stock_num;
    }

    public void setStock_num(int stock_num) {
        this.stock_num = stock_num;
    }

    public String getSpec_image() {
        return spec_image;
    }

    public void setSpec_image(String spec_image) {
        this.spec_image = spec_image;
    }

    public Object getImgshow() {
        return imgshow;
    }

    public void setImgshow(Object imgshow) {
        this.imgshow = imgshow;
    }
}
