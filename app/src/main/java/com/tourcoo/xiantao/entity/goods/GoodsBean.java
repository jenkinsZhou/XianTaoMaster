package com.tourcoo.xiantao.entity.goods;

import java.util.List;

/**
 * @author :zhoujian
 * @description :商品实体类
 * @company :翼迈科技
 * @date 2019年 04月 20日 19时54分
 * @Email: 971613168@qq.com
 */
public class GoodsBean {

    /**
     * goods_id : 24
     * goods_name : 车厘子智利进口
     * category_id : 7
     * images : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg
     * spec_type : 10
     * deduct_stock_type : 20
     * content : <p><img src="https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg" data-filename="filename" style="width: 603px;"><br></p>
     * sales_initial : 0
     * sales_actual : 12
     * goods_sort : 24
     * delivery_id : 26
     * goods_status : 10
     * is_delete : 0
     * createtime : 1541403509
     * updatetime : 1555318910
     * goods_sales : 12
     * goods_min_price : 258.00
     * spec : [{"goods_spec_id":125,"goods_id":24,"goods_no":"CHE001","goods_price":"258.00","line_price":"299.00","stock_num":94,"goods_sales":0,"goods_weight":1,"spec_sku_id":"","spec_image":"","create_time":1555318910,"update_time":1555318910}]
     * spec_type_text : Spec_type 10
     * deduct_stock_type_text : Deduct_stock_type 20
     * goods_status_text : Goods_status 10
     * is_delete_text : Is_delete 0
     * tuan_rule : false
     * tuan : false
     * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg
     */

    private int goods_id;
    private String goods_name;
    private int category_id;
    private String images;
    private String spec_type;
    private String deduct_stock_type;
    private String content;
    private int sales_initial;
    private int sales_actual;
    private int goods_sort;
    private int delivery_id;
    private String goods_status;
    private String is_delete;
    private int createtime;
    private int updatetime;
    private int goods_sales;
    private String goods_min_price;
    private String spec_type_text;
    private String deduct_stock_type_text;
    private String goods_status_text;
    private String is_delete_text;
    private boolean tuan_rule;
    private boolean tuan;
    private String image;
    private List<SpecBean> spec;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getSpec_type() {
        return spec_type;
    }

    public void setSpec_type(String spec_type) {
        this.spec_type = spec_type;
    }

    public String getDeduct_stock_type() {
        return deduct_stock_type;
    }

    public void setDeduct_stock_type(String deduct_stock_type) {
        this.deduct_stock_type = deduct_stock_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSales_initial() {
        return sales_initial;
    }

    public void setSales_initial(int sales_initial) {
        this.sales_initial = sales_initial;
    }

    public int getSales_actual() {
        return sales_actual;
    }

    public void setSales_actual(int sales_actual) {
        this.sales_actual = sales_actual;
    }

    public int getGoods_sort() {
        return goods_sort;
    }

    public void setGoods_sort(int goods_sort) {
        this.goods_sort = goods_sort;
    }

    public int getDelivery_id() {
        return delivery_id;
    }

    public void setDelivery_id(int delivery_id) {
        this.delivery_id = delivery_id;
    }

    public String getGoods_status() {
        return goods_status;
    }

    public void setGoods_status(String goods_status) {
        this.goods_status = goods_status;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
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

    public int getGoods_sales() {
        return goods_sales;
    }

    public void setGoods_sales(int goods_sales) {
        this.goods_sales = goods_sales;
    }

    public String getGoods_min_price() {
        return goods_min_price;
    }

    public void setGoods_min_price(String goods_min_price) {
        this.goods_min_price = goods_min_price;
    }

    public String getSpec_type_text() {
        return spec_type_text;
    }

    public void setSpec_type_text(String spec_type_text) {
        this.spec_type_text = spec_type_text;
    }

    public String getDeduct_stock_type_text() {
        return deduct_stock_type_text;
    }

    public void setDeduct_stock_type_text(String deduct_stock_type_text) {
        this.deduct_stock_type_text = deduct_stock_type_text;
    }

    public String getGoods_status_text() {
        return goods_status_text;
    }

    public void setGoods_status_text(String goods_status_text) {
        this.goods_status_text = goods_status_text;
    }

    public String getIs_delete_text() {
        return is_delete_text;
    }

    public void setIs_delete_text(String is_delete_text) {
        this.is_delete_text = is_delete_text;
    }

    public boolean isTuan_rule() {
        return tuan_rule;
    }

    public void setTuan_rule(boolean tuan_rule) {
        this.tuan_rule = tuan_rule;
    }

    public boolean isTuan() {
        return tuan;
    }

    public void setTuan(boolean tuan) {
        this.tuan = tuan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<SpecBean> getSpec() {
        return spec;
    }

    public void setSpec(List<SpecBean> spec) {
        this.spec = spec;
    }
}
