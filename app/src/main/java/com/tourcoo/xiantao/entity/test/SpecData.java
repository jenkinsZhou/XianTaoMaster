package com.tourcoo.xiantao.entity.test;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月26日14:41
 * @Email: 971613168@qq.com
 */
public class SpecData {

    private List<SpecAttrBean> spec_attr;
    private List<SpecListBean> spec_list;

    public List<SpecAttrBean> getSpec_attr() {
        return spec_attr;
    }

    public void setSpec_attr(List<SpecAttrBean> spec_attr) {
        this.spec_attr = spec_attr;
    }

    public List<SpecListBean> getSpec_list() {
        return spec_list;
    }

    public void setSpec_list(List<SpecListBean> spec_list) {
        this.spec_list = spec_list;
    }

    public static class SpecAttrBean {
        /**
         * group_id : 20
         * group_name : 颜色
         * spec_items : [{"item_id":48,"spec_value":"天空灰"},{"item_id":49,"spec_value":"银色"}]
         */

        private int group_id;
        private String group_name;
        private List<SpecItemsBean> spec_items;

        public int getGroup_id() {
            return group_id;
        }

        public void setGroup_id(int group_id) {
            this.group_id = group_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public List<SpecItemsBean> getSpec_items() {
            return spec_items;
        }

        public void setSpec_items(List<SpecItemsBean> spec_items) {
            this.spec_items = spec_items;
        }

        public static class SpecItemsBean {
            /**
             * item_id : 48
             * spec_value : 天空灰
             */

            private int item_id;
            private String spec_value;

            public int getItem_id() {
                return item_id;
            }

            public void setItem_id(int item_id) {
                this.item_id = item_id;
            }

            public String getSpec_value() {
                return spec_value;
            }

            public void setSpec_value(String spec_value) {
                this.spec_value = spec_value;
            }
        }
    }

    public static class SpecListBean {
        /**
         * form : {"goods_no":"mac_0001","goods_price":"12608.00","goods_weight":1.2,"line_price":"0.00","spec_image":"","stock_num":989}
         * goods_spec_id : 158
         * rows : []
         * spec_sku_id : 48
         */

        private FormBean form;
        private int goods_spec_id;
        private String spec_sku_id;
        private List<?> rows;

        public FormBean getForm() {
            return form;
        }

        public void setForm(FormBean form) {
            this.form = form;
        }

        public int getGoods_spec_id() {
            return goods_spec_id;
        }

        public void setGoods_spec_id(int goods_spec_id) {
            this.goods_spec_id = goods_spec_id;
        }

        public String getSpec_sku_id() {
            return spec_sku_id;
        }

        public void setSpec_sku_id(String spec_sku_id) {
            this.spec_sku_id = spec_sku_id;
        }

        public List<?> getRows() {
            return rows;
        }

        public void setRows(List<?> rows) {
            this.rows = rows;
        }

        public static class FormBean {
            /**
             * goods_no : mac_0001
             * goods_price : 12608.00
             * goods_weight : 1.2
             * line_price : 0.00
             * spec_image :
             * stock_num : 989
             */

            private String goods_no;
            private String goods_price;
            private double goods_weight;
            private String line_price;
            private String spec_image;
            private int stock_num;

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

            public String getSpec_image() {
                return spec_image;
            }

            public void setSpec_image(String spec_image) {
                this.spec_image = spec_image;
            }

            public int getStock_num() {
                return stock_num;
            }

            public void setStock_num(int stock_num) {
                this.stock_num = stock_num;
            }
        }
    }
}
