package com.tourcoo.xiantao.entity.shoppingcar;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :购物车实体
 * @company :途酷科技
 * @date 2019年05月23日14:56
 * @Email: 971613168@qq.com
 */
public class ShoppingCarEntity {


    /**
     * goods_list : [{"goods_id":25,"goods_price":"15.00","stock_num":100,"spec_sku_id":"77_83_85","spec_value":"产地:美国;品种:4013;重量:500g;","total_num":"4","image":"https://test.ahxtao.com/uploads/20190522/86842ebb9f9ada12d87a6ff93cde62a7.jpg","goods_name":"新奇士橙子","total_price":60},{"goods_id":26,"goods_price":"210.00","stock_num":100,"spec_sku_id":"75","spec_value":"山东烟台大樱桃:1500g;","total_num":"1","image":"https://test.ahxtao.com/uploads/20190522/82eb55091ecf4229d632a61900722867.jpg","goods_name":"国产樱桃","total_price":210},{"goods_id":35,"goods_price":"60.00","stock_num":100,"spec_sku_id":"90","spec_value":"山东红富士80#:2500g;","total_num":"1","image":"https://test.ahxtao.com/uploads/20190522/e17e6c6dd6a281b71263b1b34abb3dc8.png","goods_name":"山东红富士","total_price":60}]
     * order_total_num : 6
     * order_total_price : 330
     */

    private int order_total_num;
    private double order_total_price;
    private List<GoodsBean> goods_list;

    public int getOrder_total_num() {
        return order_total_num;
    }

    public void setOrder_total_num(int order_total_num) {
        this.order_total_num = order_total_num;
    }

    public double getOrder_total_price() {
        return order_total_price;
    }

    public void setOrder_total_price(double order_total_price) {
        this.order_total_price = order_total_price;
    }

    public List<GoodsBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsBean {
        /**
         * goods_id : 25
         * goods_price : 15.00
         * stock_num : 100
         * spec_sku_id : 77_83_85
         * spec_value : 产地:美国;品种:4013;重量:500g;
         * total_num : 4
         * image : https://test.ahxtao.com/uploads/20190522/86842ebb9f9ada12d87a6ff93cde62a7.jpg
         * goods_name : 新奇士橙子
         * total_price : 60
         * quota :3
         */
        private int quota;

        public int getQuota() {
            return quota;
        }

        public void setQuota(int quota) {
            this.quota = quota;
        }

        private int goods_id;
        private double goods_price;
        private int stock_num;
        private String spec_sku_id;
        private String spec_value;
        private int total_num;
        private String image;
        private String goods_name;
        private double total_price;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public double getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
        }

        public int getStock_num() {
            return stock_num;
        }

        public void setStock_num(int stock_num) {
            this.stock_num = stock_num;
        }

        public String getSpec_sku_id() {
            return spec_sku_id;
        }

        public void setSpec_sku_id(String spec_sku_id) {
            this.spec_sku_id = spec_sku_id;
        }

        public String getSpec_value() {
            return spec_value;
        }

        public void setSpec_value(String spec_value) {
            this.spec_value = spec_value;
        }

        public int getTotal_num() {
            return total_num;
        }

        public void setTotal_num(int total_num) {
            this.total_num = total_num;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }
    }
}
