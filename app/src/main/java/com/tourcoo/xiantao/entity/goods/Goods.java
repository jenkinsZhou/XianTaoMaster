package com.tourcoo.xiantao.entity.goods;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :商品实体
 * @company :途酷科技
 * @date 2019年04月25日15:43
 * @Email: 971613168@qq.com
 */
public class Goods implements Serializable {
    private int count;
    /**
     * 是否选中
     */
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private List<String> imgs_url;
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
    private String label;
    private String origin;
    private String image;
    private int show_error;
    private double goods_price;
    private int total_num;
    private String total_price;
    private Category category;
    private Freight freight;
    private int goods_sales;
    private TuanRule tuan_rule;
    private boolean tuan;
    private int coin;

    public List<Spec> getSpec() {
        return spec;
    }

    public void setSpec(List<Spec> spec) {
        this.spec = spec;
    }

    public List<?> getSpec_rel() {
        return spec_rel;
    }

    private List<Spec> spec;
    private List<?> spec_rel;
    private List<?> tuan_list;
    private List<?> comment_list;


    public int getShow_error() {
        return show_error;
    }

    public void setShow_error(int show_error) {
        this.show_error = show_error;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    /**
     * goods_id : 24
     * goods_name : 车厘子智利进口
     * category_id : 7
     * images : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg
     * spec_type : 10
     * deduct_stock_type : 20
     * content : <p><img src="https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg" data-filename="filename" style="width: 100%;"><br></p>
     * sales_initial : 0
     * sales_actual : 12
     * goods_sort : 24
     * delivery_id : 26
     * goods_status : 10
     * is_delete : 0
     * createtime : 1541403509
     * updatetime : 1556098800
     * label :
     * origin :
     * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg
     * imgs_url : ["https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg"]
     * category : {"id":7,"pid":6,"name":"进口水果","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","weigh":7,"createtime":1540367326,"updatetime":1541403531}
     * spec : [{"goods_spec_id":160,"goods_id":24,"goods_no":"CHE001","goods_price":"258.00","line_price":"299.00","stock_num":94,"goods_sales":0,"goods_weight":1,"spec_sku_id":"","spec_image":"","create_time":1556098800,"update_time":1556098800}]
     * spec_rel : []
     * freight : {"id":26,"name":"省内","method":"20","weigh":26,"createtime":1555318901,"updatetime":1555318901,"method_text":"Method 20"}
     * goods_sales : 12
     * tuan_rule : {"status_text":""}
     * tuan : false
     * tuan_list : []
     * comment_list : []
     * coin : 12
     */


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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Freight getFreight() {
        return freight;
    }

    public void setFreight(Freight freight) {
        this.freight = freight;
    }

    public int getGoods_sales() {
        return goods_sales;
    }

    public void setGoods_sales(int goods_sales) {
        this.goods_sales = goods_sales;
    }

    public TuanRule getTuan_rule() {
        return tuan_rule;
    }

    public void setTuan_rule(TuanRule tuan_rule) {
        this.tuan_rule = tuan_rule;
    }

    public boolean isTuan() {
        return tuan;
    }

    public void setTuan(boolean tuan) {
        this.tuan = tuan;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public List<String> getImgs_url() {
        return imgs_url;
    }

    public void setImgs_url(List<String> imgs_url) {
        this.imgs_url = imgs_url;
    }

    public List<Spec> getSpecBean() {
        return spec;
    }



    public void setSpec_rel(List<?> spec_rel) {
        this.spec_rel = spec_rel;
    }

    public List<?> getTuan_list() {
        return tuan_list;
    }

    public void setTuan_list(List<?> tuan_list) {
        this.tuan_list = tuan_list;
    }

    public List<?> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<?> comment_list) {
        this.comment_list = comment_list;
    }


}
