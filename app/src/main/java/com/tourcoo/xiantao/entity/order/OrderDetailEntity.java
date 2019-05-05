package com.tourcoo.xiantao.entity.order;

import com.tourcoo.xiantao.entity.address.AddressEntity;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :订单详情实体
 * @company :途酷科技
 * @date 2019年04月28日10:12
 * @Email: 971613168@qq.com
 */
public class OrderDetailEntity {
    /**
     * order : {"id":38,"order_no":"2019042552101575","total_price":"258.00","pay_price":"268.00","pay_status":"10","pay_time":0,"express_price":"10.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556191540,"updatetime":1556191540,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":43,"goods_id":24,"goods_name":"车厘子智利进口","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","deduct_stock_type":"20","spec_type":"10","spec_sku_id":"","goods_spec_id":160,"goods_attr":"","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"CHE001","goods_price":"258.00","line_price":"299.00","goods_weight":1,"total_num":1,"total_price":"258.00","order_id":38,"user_id":3,"createtime":1556191540,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","sku_image":"","spec":{"goods_spec_id":172,"goods_id":24,"goods_no":"CHE001","goods_price":"0.01","line_price":"0.01","stock_num":93,"goods_sales":0,"goods_weight":1,"spec_sku_id":"","spec_image":"","create_time":1556334606,"update_time":1556334606},"goods":{"goods_id":24,"goods_name":"车厘子智利进口","category_id":7,"images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type":"10","deduct_stock_type":"20","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","sales_initial":0,"sales_actual":14,"goods_sort":24,"delivery_id":24,"goods_status":"10","is_delete":"0","createtime":1541403509,"updatetime":1556361678,"label":"","origin":"","goods_sales":14,"tuan_rule":{"id":1,"name":"10元/200g","price":10,"weigh":0.2,"num":10,"validity":3,"status_text":""},"tuan":true,"tuan_list":[{"id":1,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556333914,"updatetime":1556333914,"deadline":1556593114,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":2,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556351238,"updatetime":1556351238,"deadline":1556610438,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":3,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556354777,"updatetime":1556354777,"deadline":1556613977,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":4,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556357186,"updatetime":1556357186,"deadline":1556616386,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":5,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556357621,"updatetime":1556357621,"deadline":1556616821,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":6,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556361670,"updatetime":1556361670,"deadline":1556620870,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}}],"comment_list":[],"coin":12}}],"address":{"id":38,"name":"陈舟为","phone":"13625627267","province_id":1046,"city_id":1047,"region_id":1050,"detail":"洪泰创新空间","order_id":38,"user_id":3,"createtime":1556191540,"Area":{"province":"安徽省","city":"合肥市","region":"蜀山区"}},"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-25 19:25:40"}
     */

    private OrderBean order;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class OrderBean {
        /**
         * id : 38
         * order_no : 2019042552101575
         * total_price : 258.00
         * pay_price : 268.00
         * pay_status : 10
         * pay_time : 0
         * express_price : 10.00
         * express_company :
         * express_no :
         * freight_status : 10
         * freight_time : 0
         * receipt_status : 10
         * receipt_time : 0
         * order_status : 10
         * transaction_id :
         * user_id : 2
         * createtime : 1556191540
         * updatetime : 1556191540
         * coin : 0
         * coin_status : 0
         * tuan : 0
         * comment_status : 10
         * pay_type : cash
         * remark : null
         * return_status : 10
         * goods : [{"id":43,"goods_id":24,"goods_name":"车厘子智利进口","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","deduct_stock_type":"20","spec_type":"10","spec_sku_id":"","goods_spec_id":160,"goods_attr":"","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"CHE001","goods_price":"258.00","line_price":"299.00","goods_weight":1,"total_num":1,"total_price":"258.00","order_id":38,"user_id":3,"createtime":1556191540,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","sku_image":"","spec":{"goods_spec_id":172,"goods_id":24,"goods_no":"CHE001","goods_price":"0.01","line_price":"0.01","stock_num":93,"goods_sales":0,"goods_weight":1,"spec_sku_id":"","spec_image":"","create_time":1556334606,"update_time":1556334606},"goods":{"goods_id":24,"goods_name":"车厘子智利进口","category_id":7,"images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type":"10","deduct_stock_type":"20","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","sales_initial":0,"sales_actual":14,"goods_sort":24,"delivery_id":24,"goods_status":"10","is_delete":"0","createtime":1541403509,"updatetime":1556361678,"label":"","origin":"","goods_sales":14,"tuan_rule":{"id":1,"name":"10元/200g","price":10,"weigh":0.2,"num":10,"validity":3,"status_text":""},"tuan":true,"tuan_list":[{"id":1,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556333914,"updatetime":1556333914,"deadline":1556593114,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":2,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556351238,"updatetime":1556351238,"deadline":1556610438,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":3,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556354777,"updatetime":1556354777,"deadline":1556613977,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":4,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556357186,"updatetime":1556357186,"deadline":1556616386,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":5,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556357621,"updatetime":1556357621,"deadline":1556616821,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":6,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556361670,"updatetime":1556361670,"deadline":1556620870,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}}],"comment_list":[],"coin":12}}]
         * address : {"id":38,"name":"陈舟为","phone":"13625627267","province_id":1046,"city_id":1047,"region_id":1050,"detail":"洪泰创新空间","order_id":38,"user_id":3,"createtime":1556191540,"Area":{"province":"安徽省","city":"合肥市","region":"蜀山区"}}
         * pay_status_text : Pay_status 10
         * pay_time_text : 1970-01-01 08:00:00
         * freight_status_text : Freight_status 10
         * freight_time_text : 1970-01-01 08:00:00
         * receipt_status_text : Receipt_status 10
         * receipt_time_text : 1970-01-01 08:00:00
         * order_status_text : Order_status 10
         * creattime_text : 2019-04-25 19:25:40
         */

        private int id;
        private String order_no;
        private String total_price;
        private double pay_price;
        private int pay_status;
        private int pay_time;
        private double express_price;
        private String express_company;
        private String express_no;
        private int freight_status;
        private String freight_time;
        private int receipt_status;
        private int receipt_time;
        private int order_status;
        private String transaction_id;
        private int user_id;
        private long createtime;
        private long updatetime;
        private double coin;
        private int coin_status;
        private int tuan;
        private int comment_status;
        private String pay_type;
        private Object remark;
        private int return_status;
        private AddressEntity address;
        private String pay_status_text;
        private String pay_time_text;
        private String freight_status_text;
        private String freight_time_text;
        private String receipt_status_text;
        private String receipt_time_text;
        private String order_status_text;
        private String creattime_text;
        private List<GoodsBean> goods;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public double getPay_price() {
            return pay_price;
        }

        public void setPay_price(double pay_price) {
            this.pay_price = pay_price;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getPay_time() {
            return pay_time;
        }

        public void setPay_time(int pay_time) {
            this.pay_time = pay_time;
        }

        public double getExpress_price() {
            return express_price;
        }

        public void setExpress_price(double express_price) {
            this.express_price = express_price;
        }

        public String getExpress_company() {
            return express_company;
        }

        public void setExpress_company(String express_company) {
            this.express_company = express_company;
        }

        public String getExpress_no() {
            return express_no;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public int getFreight_status() {
            return freight_status;
        }

        public void setFreight_status(int freight_status) {
            this.freight_status = freight_status;
        }

        public String getFreight_time() {
            return freight_time;
        }

        public void setFreight_time(String freight_time) {
            this.freight_time = freight_time;
        }

        public int getReceipt_status() {
            return receipt_status;
        }

        public void setReceipt_status(int receipt_status) {
            this.receipt_status = receipt_status;
        }

        public int getReceipt_time() {
            return receipt_time;
        }

        public void setReceipt_time(int receipt_time) {
            this.receipt_time = receipt_time;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public long getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
        }

        public double getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public int getCoin_status() {
            return coin_status;
        }

        public void setCoin_status(int coin_status) {
            this.coin_status = coin_status;
        }

        public int getTuan() {
            return tuan;
        }

        public void setTuan(int tuan) {
            this.tuan = tuan;
        }

        public int getComment_status() {
            return comment_status;
        }

        public void setComment_status(int comment_status) {
            this.comment_status = comment_status;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getReturn_status() {
            return return_status;
        }

        public void setReturn_status(int return_status) {
            this.return_status = return_status;
        }

        public AddressEntity getAddress() {
            return address;
        }

        public void setAddress(AddressEntity address) {
            this.address = address;
        }

        public String getPay_status_text() {
            return pay_status_text;
        }

        public void setPay_status_text(String pay_status_text) {
            this.pay_status_text = pay_status_text;
        }

        public String getPay_time_text() {
            return pay_time_text;
        }

        public void setPay_time_text(String pay_time_text) {
            this.pay_time_text = pay_time_text;
        }

        public String getFreight_status_text() {
            return freight_status_text;
        }

        public void setFreight_status_text(String freight_status_text) {
            this.freight_status_text = freight_status_text;
        }

        public String getFreight_time_text() {
            return freight_time_text;
        }

        public void setFreight_time_text(String freight_time_text) {
            this.freight_time_text = freight_time_text;
        }

        public String getReceipt_status_text() {
            return receipt_status_text;
        }

        public void setReceipt_status_text(String receipt_status_text) {
            this.receipt_status_text = receipt_status_text;
        }

        public String getReceipt_time_text() {
            return receipt_time_text;
        }

        public void setReceipt_time_text(String receipt_time_text) {
            this.receipt_time_text = receipt_time_text;
        }

        public String getOrder_status_text() {
            return order_status_text;
        }

        public void setOrder_status_text(String order_status_text) {
            this.order_status_text = order_status_text;
        }

        public String getCreattime_text() {
            return creattime_text;
        }

        public void setCreattime_text(String creattime_text) {
            this.creattime_text = creattime_text;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }


        public static class GoodsBean {
            /**
             * id : 43
             * goods_id : 24
             * goods_name : 车厘子智利进口
             * images : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg
             * deduct_stock_type : 20
             * spec_type : 10
             * spec_sku_id :
             * goods_spec_id : 160
             * goods_attr :
             * content : <p><img src="https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg" data-filename="filename" style="width: 100%;"><br></p>
             * goods_no : CHE001
             * goods_price : 258.00
             * line_price : 299.00
             * goods_weight : 1
             * total_num : 1
             * total_price : 258.00
             * order_id : 38
             * user_id : 3
             * createtime : 1556191540
             * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg
             * sku_image :
             * spec : {"goods_spec_id":172,"goods_id":24,"goods_no":"CHE001","goods_price":"0.01","line_price":"0.01","stock_num":93,"goods_sales":0,"goods_weight":1,"spec_sku_id":"","spec_image":"","create_time":1556334606,"update_time":1556334606}
             * goods : {"goods_id":24,"goods_name":"车厘子智利进口","category_id":7,"images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type":"10","deduct_stock_type":"20","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","sales_initial":0,"sales_actual":14,"goods_sort":24,"delivery_id":24,"goods_status":"10","is_delete":"0","createtime":1541403509,"updatetime":1556361678,"label":"","origin":"","goods_sales":14,"tuan_rule":{"id":1,"name":"10元/200g","price":10,"weigh":0.2,"num":10,"validity":3,"status_text":""},"tuan":true,"tuan_list":[{"id":1,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556333914,"updatetime":1556333914,"deadline":1556593114,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":2,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556351238,"updatetime":1556351238,"deadline":1556610438,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":3,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556354777,"updatetime":1556354777,"deadline":1556613977,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":4,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556357186,"updatetime":1556357186,"deadline":1556616386,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":5,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556357621,"updatetime":1556357621,"deadline":1556616821,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":6,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556361670,"updatetime":1556361670,"deadline":1556620870,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}}],"comment_list":[],"coin":12}
             */

            private int id;
            private int goods_id;
            private String goods_name;
            private String images;
            private String deduct_stock_type;
            private String spec_type;
            private String spec_sku_id;
            private int goods_spec_id;
            private String goods_attr;
            private String content;
            private String goods_no;
            private double goods_price;
            private String line_price;
            private int goods_weight;
            private int total_num;
            private String total_price;
            private int order_id;
            private int user_id;
            private int createtime;
            private String image;
            private String sku_image;
            private SpecBean spec;
            private GoodsBeanX goods;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getDeduct_stock_type() {
                return deduct_stock_type;
            }

            public void setDeduct_stock_type(String deduct_stock_type) {
                this.deduct_stock_type = deduct_stock_type;
            }

            public String getSpec_type() {
                return spec_type;
            }

            public void setSpec_type(String spec_type) {
                this.spec_type = spec_type;
            }

            public String getSpec_sku_id() {
                return spec_sku_id;
            }

            public void setSpec_sku_id(String spec_sku_id) {
                this.spec_sku_id = spec_sku_id;
            }

            public int getGoods_spec_id() {
                return goods_spec_id;
            }

            public void setGoods_spec_id(int goods_spec_id) {
                this.goods_spec_id = goods_spec_id;
            }

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

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

            public String getLine_price() {
                return line_price;
            }

            public void setLine_price(String line_price) {
                this.line_price = line_price;
            }

            public int getGoods_weight() {
                return goods_weight;
            }

            public void setGoods_weight(int goods_weight) {
                this.goods_weight = goods_weight;
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

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getSku_image() {
                return sku_image;
            }

            public void setSku_image(String sku_image) {
                this.sku_image = sku_image;
            }

            public SpecBean getSpec() {
                return spec;
            }

            public void setSpec(SpecBean spec) {
                this.spec = spec;
            }

            public GoodsBeanX getGoods() {
                return goods;
            }

            public void setGoods(GoodsBeanX goods) {
                this.goods = goods;
            }

            public static class SpecBean {
                /**
                 * goods_spec_id : 172
                 * goods_id : 24
                 * goods_no : CHE001
                 * goods_price : 0.01
                 * line_price : 0.01
                 * stock_num : 93
                 * goods_sales : 0
                 * goods_weight : 1
                 * spec_sku_id :
                 * spec_image :
                 * create_time : 1556334606
                 * update_time : 1556334606
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
            }

            public static class GoodsBeanX {
                /**
                 * goods_id : 24
                 * goods_name : 车厘子智利进口
                 * category_id : 7
                 * images : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg
                 * spec_type : 10
                 * deduct_stock_type : 20
                 * content : <p><img src="https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg" data-filename="filename" style="width: 100%;"><br></p>
                 * sales_initial : 0
                 * sales_actual : 14
                 * goods_sort : 24
                 * delivery_id : 24
                 * goods_status : 10
                 * is_delete : 0
                 * createtime : 1541403509
                 * updatetime : 1556361678
                 * label :
                 * origin :
                 * goods_sales : 14
                 * tuan_rule : {"id":1,"name":"10元/200g","price":10,"weigh":0.2,"num":10,"validity":3,"status_text":""}
                 * tuan : true
                 * tuan_list : [{"id":1,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556333914,"updatetime":1556333914,"deadline":1556593114,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":2,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556351238,"updatetime":1556351238,"deadline":1556610438,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":3,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556354777,"updatetime":1556354777,"deadline":1556613977,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":4,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556357186,"updatetime":1556357186,"deadline":1556616386,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":5,"goods_id":24,"user_id":2,"rule_id":1,"status":1,"createtime":1556357621,"updatetime":1556357621,"deadline":1556616821,"nickname":"周健","avatar":"/uploads/20190427/43597faeb457812746578ca99bcc893b.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}},{"id":6,"goods_id":24,"user_id":3,"rule_id":1,"status":1,"createtime":1556361670,"updatetime":1556361670,"deadline":1556620870,"nickname":"陈舟为","avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","num":10,"surplus":2,"goods":{"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}}]
                 * comment_list : []
                 * coin : 12
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
                private String label;
                private String origin;
                private int goods_sales;
                private TuanRuleBean tuan_rule;
                private boolean tuan;
                private int coin;
                private List<TuanListBean> tuan_list;
                private List<?> comment_list;

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

                public int getGoods_sales() {
                    return goods_sales;
                }

                public void setGoods_sales(int goods_sales) {
                    this.goods_sales = goods_sales;
                }

                public TuanRuleBean getTuan_rule() {
                    return tuan_rule;
                }

                public void setTuan_rule(TuanRuleBean tuan_rule) {
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

                public List<TuanListBean> getTuan_list() {
                    return tuan_list;
                }

                public void setTuan_list(List<TuanListBean> tuan_list) {
                    this.tuan_list = tuan_list;
                }

                public List<?> getComment_list() {
                    return comment_list;
                }

                public void setComment_list(List<?> comment_list) {
                    this.comment_list = comment_list;
                }

                public static class TuanRuleBean {
                    /**
                     * id : 1
                     * name : 10元/200g
                     * price : 10
                     * weigh : 0.2
                     * num : 10
                     * validity : 3
                     * status_text :
                     */

                    private int id;
                    private String name;
                    private int price;
                    private double weigh;
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

                    public int getPrice() {
                        return price;
                    }

                    public void setPrice(int price) {
                        this.price = price;
                    }

                    public double getWeigh() {
                        return weigh;
                    }

                    public void setWeigh(double weigh) {
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

                public static class TuanListBean {
                    /**
                     * id : 1
                     * goods_id : 24
                     * user_id : 3
                     * rule_id : 1
                     * status : 1
                     * createtime : 1556333914
                     * updatetime : 1556333914
                     * deadline : 1556593114
                     * nickname : 陈舟为
                     * avatar : /uploads/20190426/324e19196a9b75b279324bb974776dbe.png
                     * num : 10
                     * surplus : 2
                     * goods : {"goods_name":"车厘子智利进口","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","tuan_list":[],"comment_list":[],"coin":0}
                     * tuan_rule : {"id":1,"goods_id":24,"price":10,"name":"10元/200g","weigh":0.2,"num":10,"status":"normal","createtime":1555916335,"updatetime":1556264243,"validity":3,"admin_id":0,"status_text":"开启"}
                     */

                    private int id;
                    private int goods_id;
                    private int user_id;
                    private int rule_id;
                    private int status;
                    private int createtime;
                    private int updatetime;
                    private int deadline;
                    private String nickname;
                    private String avatar;
                    private int num;
                    private int surplus;
                    private GoodsBean goods;
                    private TuanRuleBeanX tuan_rule;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getGoods_id() {
                        return goods_id;
                    }

                    public void setGoods_id(int goods_id) {
                        this.goods_id = goods_id;
                    }

                    public int getUser_id() {
                        return user_id;
                    }

                    public void setUser_id(int user_id) {
                        this.user_id = user_id;
                    }

                    public int getRule_id() {
                        return rule_id;
                    }

                    public void setRule_id(int rule_id) {
                        this.rule_id = rule_id;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
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

                    public int getDeadline() {
                        return deadline;
                    }

                    public void setDeadline(int deadline) {
                        this.deadline = deadline;
                    }

                    public String getNickname() {
                        return nickname;
                    }

                    public void setNickname(String nickname) {
                        this.nickname = nickname;
                    }

                    public String getAvatar() {
                        return avatar;
                    }

                    public void setAvatar(String avatar) {
                        this.avatar = avatar;
                    }

                    public int getNum() {
                        return num;
                    }

                    public void setNum(int num) {
                        this.num = num;
                    }

                    public int getSurplus() {
                        return surplus;
                    }

                    public void setSurplus(int surplus) {
                        this.surplus = surplus;
                    }

                    public GoodsBean getGoods() {
                        return goods;
                    }

                    public void setGoods(GoodsBean goods) {
                        this.goods = goods;
                    }

                    public TuanRuleBeanX getTuan_rule() {
                        return tuan_rule;
                    }

                    public void setTuan_rule(TuanRuleBeanX tuan_rule) {
                        this.tuan_rule = tuan_rule;
                    }



                    public static class TuanRuleBeanX {
                        /**
                         * id : 1
                         * goods_id : 24
                         * price : 10
                         * name : 10元/200g
                         * weigh : 0.2
                         * num : 10
                         * status : normal
                         * createtime : 1555916335
                         * updatetime : 1556264243
                         * validity : 3
                         * admin_id : 0
                         * status_text : 开启
                         */

                        private int id;
                        private int goods_id;
                        private int price;
                        private String name;
                        private double weigh;
                        private int num;
                        private String status;
                        private int createtime;
                        private int updatetime;
                        private int validity;
                        private int admin_id;
                        private String status_text;

                        public int getId() {
                            return id;
                        }

                        public void setId(int id) {
                            this.id = id;
                        }

                        public int getGoods_id() {
                            return goods_id;
                        }

                        public void setGoods_id(int goods_id) {
                            this.goods_id = goods_id;
                        }

                        public int getPrice() {
                            return price;
                        }

                        public void setPrice(int price) {
                            this.price = price;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public double getWeigh() {
                            return weigh;
                        }

                        public void setWeigh(double weigh) {
                            this.weigh = weigh;
                        }

                        public int getNum() {
                            return num;
                        }

                        public void setNum(int num) {
                            this.num = num;
                        }

                        public String getStatus() {
                            return status;
                        }

                        public void setStatus(String status) {
                            this.status = status;
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

                        public int getValidity() {
                            return validity;
                        }

                        public void setValidity(int validity) {
                            this.validity = validity;
                        }

                        public int getAdmin_id() {
                            return admin_id;
                        }

                        public void setAdmin_id(int admin_id) {
                            this.admin_id = admin_id;
                        }

                        public String getStatus_text() {
                            return status_text;
                        }

                        public void setStatus_text(String status_text) {
                            this.status_text = status_text;
                        }
                    }
                }
            }
        }
    }
}
