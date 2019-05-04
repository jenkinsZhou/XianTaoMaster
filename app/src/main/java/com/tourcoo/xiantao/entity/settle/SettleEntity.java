package com.tourcoo.xiantao.entity.settle;

import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.goods.Goods;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :结算实体
 * @company :途酷科技
 * @date 2019年04月25日15:15
 * @Email: 971613168@qq.com
 */
public class SettleEntity implements Serializable {


    /**
     * goods_list : [{"goods_id":24,"goods_name":"车厘子智利进口","category_id":7,"images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type":"10","deduct_stock_type":"20","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","sales_initial":0,"sales_actual":12,"goods_sort":24,"delivery_id":26,"goods_status":"10","is_delete":"0","createtime":1541403509,"updatetime":1556098800,"label":"","origin":"","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","show_error":0,"goods_sku":{"goods_spec_id":160,"goods_id":24,"goods_no":"CHE001","goods_price":"258.00","line_price":"299.00","stock_num":94,"goods_sales":0,"goods_weight":1,"spec_sku_id":"","spec_image":"","create_time":1556098800,"update_time":1556098800,"goods_attr":""},"goods_price":"258.00","total_num":"1","total_price":"258.00","category":{"id":7,"pid":6,"name":"进口水果","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","weigh":7,"createtime":1540367326,"updatetime":1541403531},"spec":[{"goods_spec_id":160,"goods_id":24,"goods_no":"CHE001","goods_price":"258.00","line_price":"299.00","stock_num":94,"goods_sales":0,"goods_weight":1,"spec_sku_id":"","spec_image":"","create_time":1556098800,"update_time":1556098800,"goods_attr":""}],"spec_rel":[],"freight":{"id":26,"name":"省内","method":"20","weigh":26,"createtime":1555318901,"updatetime":1555318901,"rule":[{"rule_id":52,"litestore_freight_id":26,"region":"1047,1057,1066,1074,1081,1088,1093,1098,1110,1118,1127,1136,1142,1150,1155,1160","first":1,"first_fee":"10.00","additional":1,"additional_fee":"1.00","weigh":0,"create_time":1555318901,"region_data":["1047","1057","1066","1074","1081","1088","1093","1098","1110","1118","1127","1136","1142","1150","1155","1160"]}],"method_text":"Method 20"},"goods_sales":12,"tuan_rule":{"status_text":""},"tuan":false,"tuan_list":[],"comment_list":[],"coin":12}]
     * order_total_num : 1
     * order_total_price : 258.00
     * coin : 1
     * remark :
     * coin_status : 1
     * order_pay_price : 267.00
     * address : {"address_id":27,"name":"周健","phone":"18256070563","province_id":1046,"city_id":1047,"region_id":1050,"detail":"经开区高速翡翠湖畔1号楼","user_id":2,"isdefault":"1","createtime":1556091048,"updatetime":1556091048,"Area":{"province":"安徽省","city":"合肥市","region":"蜀山区"}}
     * exist_address : {"address_id":27,"name":"周健","phone":"18256070563","province_id":1046,"city_id":1047,"region_id":1050,"detail":"经开区高速翡翠湖畔1号楼","user_id":2,"isdefault":"1","createtime":1556091048,"updatetime":1556091048,"Area":{"province":"安徽省","city":"合肥市","region":"蜀山区"}}
     * express_price : 10.00
     * intra_region : true
     * has_error : false
     * error_msg : null
     */

    private String order_total_num;
    private String order_total_price;
    private int coin;
    private String remark;
    private int coin_status;
    private double order_pay_price;
    private AddressEntity address;
    private AddressEntity exist_address;
    private String express_price;
    private boolean intra_region;
    private boolean has_error;
    private Object error_msg;
    private List<Goods> goods_list;

    public String getOrder_total_num() {
        return order_total_num;
    }

    public void setOrder_total_num(String order_total_num) {
        this.order_total_num = order_total_num;
    }

    public String getOrder_total_price() {
        return order_total_price;
    }

    public void setOrder_total_price(String order_total_price) {
        this.order_total_price = order_total_price;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCoin_status() {
        return coin_status;
    }

    public void setCoin_status(int coin_status) {
        this.coin_status = coin_status;
    }

    public double getOrder_pay_price() {
        return order_pay_price;
    }

    public void setOrder_pay_price(double order_pay_price) {
        this.order_pay_price = order_pay_price;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public AddressEntity getExist_address() {
        return exist_address;
    }

    public void setExist_address(AddressEntity exist_address) {
        this.exist_address = exist_address;
    }

    public String getExpress_price() {
        return express_price;
    }

    public void setExpress_price(String express_price) {
        this.express_price = express_price;
    }

    public boolean isIntra_region() {
        return intra_region;
    }

    public void setIntra_region(boolean intra_region) {
        this.intra_region = intra_region;
    }

    public boolean isHas_error() {
        return has_error;
    }

    public void setHas_error(boolean has_error) {
        this.has_error = has_error;
    }

    public Object getError_msg() {
        return error_msg;
    }

    public void setError_msg(Object error_msg) {
        this.error_msg = error_msg;
    }

    public List<Goods> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<Goods> goods_list) {
        this.goods_list = goods_list;
    }


    public static class GoodsSkuBean {
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

    public static class CategoryBean {
        /**
         * id : 7
         * pid : 6
         * name : 进口水果
         * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg
         * weigh : 7
         * createtime : 1540367326
         * updatetime : 1541403531
         */

        private int id;
        private int pid;
        private String name;
        private String image;
        private int weigh;
        private int createtime;
        private int updatetime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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
    }

    public static class FreightBean {
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
        private List<RuleBean> rule;

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

        public List<RuleBean> getRule() {
            return rule;
        }

        public void setRule(List<RuleBean> rule) {
            this.rule = rule;
        }

        public static class RuleBean {
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
    }

    public static class TuanRuleBean {
        /**
         * status_text :
         */

        private String status_text;

        public String getStatus_text() {
            return status_text;
        }

        public void setStatus_text(String status_text) {
            this.status_text = status_text;
        }
    }

    public static class SpecBean {
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
}
