package com.tourcoo.xiantao.entity.goods;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :商品SKU
 * @company :途酷科技
 * @date 2019年04月25日15:36
 * @Email: 971613168@qq.com
 */
public class GoodsSkuBean implements Serializable {

    /**
     * goods_spec_id : 160
     * goods_id : 24
     * goods_no : CHE001
     * goods_price : 258.00
     * line_price : 299.00
     * stock_num : 94
     * goods_sales : 0
     * goods_weight : 1
     * spec_sku_id :
     * spec_image :
     * create_time : 1556098800
     * update_time : 1556098800
     * goods_attr :
     */

    private int goods_spec_id;
    private int goods_id;
    private String goods_no;
    private String goods_price;
    private String line_price;
    private int stock_num;
    private int goods_sales;
    private int goods_weight;
    private String spec_sku_id;
    private String spec_image;
    private int create_time;
    private int update_time;
    private String goods_attr;

    public int getGoods_spec_id() {
        return goods_spec_id;
    }

    public void setGoods_spec_id(int goods_spec_id) {
        this.goods_spec_id = goods_spec_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_no() {
        return goods_no;
    }

    public void setGoods_no(String goods_no) {
        this.goods_no = goods_no;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
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

    public int getGoods_sales() {
        return goods_sales;
    }

    public void setGoods_sales(int goods_sales) {
        this.goods_sales = goods_sales;
    }

    public int getGoods_weight() {
        return goods_weight;
    }

    public void setGoods_weight(int goods_weight) {
        this.goods_weight = goods_weight;
    }

    public String getSpec_sku_id() {
        return spec_sku_id;
    }

    public void setSpec_sku_id(String spec_sku_id) {
        this.spec_sku_id = spec_sku_id;
    }

    public String getSpec_image() {
        return spec_image;
    }

    public void setSpec_image(String spec_image) {
        this.spec_image = spec_image;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(int update_time) {
        this.update_time = update_time;
    }

    public String getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(String goods_attr) {
        this.goods_attr = goods_attr;
    }
}
