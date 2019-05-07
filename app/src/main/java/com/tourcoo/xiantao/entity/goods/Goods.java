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


    /**
     * goods_id : 22
     * goods_name : Mate 20 华为 HUAWEI 1
     * category_id : 5
     * images : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/e0d6dc822cf7632c66f7718bdd0dc2bc.jpg
     * spec_type : 20
     * deduct_stock_type : 20
     * content : <p style="text-align: center; line-height: 1.6;"><img src="https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/3f0233e227359137bb55152c0750f8a2.png" data-filename="filename" style="width: 100%;"><img src="https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/509af801726984aaa359b4bf249f5716.png" data-filename="filename" style="width: 100%;"><br></p><p><br></p>
     * sales_initial : 10
     * sales_actual : 91
     * goods_sort : 22
     * delivery_id : 22
     * goods_status : 10
     * is_delete : 0
     * createtime : 1541402364
     * updatetime : 1556741133
     * label :
     * origin :
     * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg
     * imgs_url : ["https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg","https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg","https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/e0d6dc822cf7632c66f7718bdd0dc2bc.jpg"]
     * collect : 0
     * category : {"id":5,"pid":4,"name":"手机","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg","weigh":5,"createtime":1540367298,"updatetime":1541402932}
     * spec : [{"goods_spec_id":643,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":0,"goods_sales":0,"goods_weight":2,"spec_sku_id":"44_46_58_71","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":644,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":10,"goods_sales":0,"goods_weight":2,"spec_sku_id":"44_46_58_72","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":645,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"44_46_59_71","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":646,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"44_46_59_72","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":647,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"44_47_58_71","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":648,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":-1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"44_47_58_72","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":649,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":-1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"44_47_59_71","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":650,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":-1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"44_47_59_72","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":651,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"61_46_58_71","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":652,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"61_46_58_72","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":653,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":-1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"61_46_59_71","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":654,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":-1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"61_46_59_72","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":655,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"61_47_58_71","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":656,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"61_47_58_72","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":657,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"61_47_59_71","spec_image":"","create_time":1556734842,"update_time":1556734842},{"goods_spec_id":658,"goods_id":22,"goods_no":"1001","goods_price":"99.00","line_price":"999.00","stock_num":-1,"goods_sales":0,"goods_weight":2,"spec_sku_id":"61_47_59_72","spec_image":"","create_time":1556734842,"update_time":1556734842}]
     * spec_rel : [{"id":44,"spec_value":"亮黑色","spec_id":20,"createtime":1541402233,"pivot":{"id":328,"goods_id":22,"spec_id":20,"spec_value_id":44,"create_time":1556734842},"spec":{"id":20,"spec_name":"颜色","createtime":1541401442}},{"id":61,"spec_value":"白色","spec_id":20,"createtime":1556431836,"pivot":{"id":329,"goods_id":22,"spec_id":20,"spec_value_id":61,"create_time":1556734842}},{"id":46,"spec_value":"6GB+64GB","spec_id":22,"createtime":1541402271,"pivot":{"id":330,"goods_id":22,"spec_id":22,"spec_value_id":46,"create_time":1556734842},"spec":{"id":22,"spec_name":"内存","createtime":1541402270}},{"id":47,"spec_value":"8GB+128GB","spec_id":22,"createtime":1541402279,"pivot":{"id":331,"goods_id":22,"spec_id":22,"spec_value_id":47,"create_time":1556734842}},{"id":58,"spec_value":"1kg","spec_id":27,"createtime":1555056178,"pivot":{"id":332,"goods_id":22,"spec_id":27,"spec_value_id":58,"create_time":1556734842},"spec":{"id":27,"spec_name":"重量","createtime":1555056178}},{"id":59,"spec_value":"2kg","spec_id":27,"createtime":1555056183,"pivot":{"id":333,"goods_id":22,"spec_id":27,"spec_value_id":59,"create_time":1556734842}},{"id":71,"spec_value":"1m","spec_id":30,"createtime":1556618273,"pivot":{"id":334,"goods_id":22,"spec_id":30,"spec_value_id":71,"create_time":1556734842},"spec":{"id":30,"spec_name":"高度","createtime":1556618273}},{"id":72,"spec_value":"2m","spec_id":30,"createtime":1556618279,"pivot":{"id":335,"goods_id":22,"spec_id":30,"spec_value_id":72,"create_time":1556734842}}]
     * freight : {"id":22,"name":"手机","method":"10","weigh":22,"createtime":1540288755,"updatetime":1540288755,"method_text":"Method 10"}
     * goods_sales : 101
     * tuan_rule : {"id":2,"name":"风光ix5  280TGDI CVT智尊型","price":2,"weigh":3,"num":5,"validity":2,"status_text":""}
     * tuan : true
     * tuan_list : [{"id":114,"goods_id":22,"user_id":4,"rule_id":2,"status":1,"createtime":1556741129,"updatetime":1556741129,"deadline":1556913929,"nickname":"ﾟ默｡","avatar":"https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132","num":4,"surplus":12,"goods":{"goods_name":"Mate 20 华为 HUAWEI 1","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/e0d6dc822cf7632c66f7718bdd0dc2bc.jpg","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":2,"goods_id":22,"price":2,"name":"风光ix5  280TGDI CVT智尊型","weigh":3,"num":5,"status":"normal","createtime":1555916724,"updatetime":1556595809,"validity":2,"admin_id":0,"status_text":"开启"},"user":{"nickname":"ﾟ默｡","avatar":"https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132"},"tuan":{"num":4,"surplus":12}}]
     * comment_list : [{"id":29,"goods_id":0,"user_id":2,"order_id":302,"nickname":"嘻哈江湖","avatar":"https://ahxtao.hfcoco.top/uploads/20190429/db00fae60f092ccf9bef0607cef4924f.jpg","star":5,"detail":"很好","images":"https://ahxtao.hfcoco.top/uploads/20190501/37b99a4245257091f7f9076ca129edf0.jpg","createtime":1556717549,"updatetime":1556717549,"status":"normal","reply":null,"admin_id":0},{"id":21,"goods_id":0,"user_id":2,"order_id":214,"nickname":"嘻哈江湖","avatar":"https://ahxtao.hfcoco.top/uploads/20190429/db00fae60f092ccf9bef0607cef4924f.jpg","star":5,"detail":"测试八张图片","images":null,"createtime":1556615329,"updatetime":1556615329,"status":"normal","reply":null,"admin_id":0}]
     * coin : 0
     */
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int goods_id;
    private String goods_sku_id;
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
    private int collect;
    private CategoryBean category;
    private FreightBean freight;
    private int goods_sales;
    private Object tuan_rule;
    private boolean tuan;
    private int coin;
    private int total_num;
    private int give_coin;
    /**
     * 抵扣金币
     */
    private double deduct;
    /**
     * 赠送金币
     */
    private double give;
    private boolean isSelect;
    private double goods_price;
    private List<String> imgs_url;
    private List<Spec> spec;
    private List<SpecRelBean> spec_rel;
    private List<TuanListBean> tuan_list;
    private List<CommentListBean> comment_list;
    private GoodsSkuBean goods_sku;

    public String getGoods_sku_id() {
        return goods_sku_id;
    }

    public void setGoods_sku_id(String goods_sku_id) {
        this.goods_sku_id = goods_sku_id;
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

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public FreightBean getFreight() {
        return freight;
    }

    public void setFreight(FreightBean freight) {
        this.freight = freight;
    }

    public int getGoods_sales() {
        return goods_sales;
    }

    public void setGoods_sales(int goods_sales) {
        this.goods_sales = goods_sales;
    }

    public Object getTuan_rule() {
        return tuan_rule;
    }

    public void setTuan_rule(Object tuan_rule) {
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

    public List<Spec> getSpec() {
        return spec;
    }

    public void setSpec(List<Spec> spec) {
        this.spec = spec;
    }

    public List<SpecRelBean> getSpec_rel() {
        return spec_rel;
    }

    public void setSpec_rel(List<SpecRelBean> spec_rel) {
        this.spec_rel = spec_rel;
    }

    public List<TuanListBean> getTuan_list() {
        return tuan_list;
    }

    public void setTuan_list(List<TuanListBean> tuan_list) {
        this.tuan_list = tuan_list;
    }

    public List<CommentListBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<CommentListBean> comment_list) {
        this.comment_list = comment_list;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(double goods_price) {
        this.goods_price = goods_price;
    }

    public GoodsSkuBean getGoods_sku() {
        return goods_sku;
    }

    public void setGoods_sku(GoodsSkuBean goods_sku) {
        this.goods_sku = goods_sku;
    }

    public int getGive_coin() {
        return give_coin;
    }

    public void setGive_coin(int give_coin) {
        this.give_coin = give_coin;
    }

    public double getDeduct() {
        return deduct;
    }

    public void setDeduct(double deduct) {
        this.deduct = deduct;
    }

    public double getGive() {
        return give;
    }

    public void setGive(int give) {
        this.give = give;
    }

    public static class CategoryBean implements Serializable {
        /**
         * id : 5
         * pid : 4
         * name : 手机
         * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg
         * weigh : 5
         * createtime : 1540367298
         * updatetime : 1541402932
         */

        private int id;
        private int pid;
        private String name;
        private String image;
        private String weigh;
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

        public String getWeigh() {
            return weigh;
        }

        public void setWeigh(String weigh) {
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

    public static class FreightBean implements Serializable {
        /**
         * id : 22
         * name : 手机
         * method : 10
         * weigh : 22
         * createtime : 1540288755
         * updatetime : 1540288755
         * method_text : Method 10
         */

        private int id;
        private String name;
        private String method;
        private int weigh;
        private int createtime;
        private int updatetime;
        private String method_text;

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
    }


    public static class SpecRelBean implements Serializable {
        /**
         * id : 44
         * spec_value : 亮黑色
         * spec_id : 20
         * createtime : 1541402233
         * pivot : {"id":328,"goods_id":22,"spec_id":20,"spec_value_id":44,"create_time":1556734842}
         * spec : {"id":20,"spec_name":"颜色","createtime":1541401442}
         */

        private int id;
        private String spec_value;
        private int spec_id;
        private int createtime;
        private PivotBean pivot;
        private SpecBeanX spec;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSpec_value() {
            return spec_value;
        }

        public void setSpec_value(String spec_value) {
            this.spec_value = spec_value;
        }

        public int getSpec_id() {
            return spec_id;
        }

        public void setSpec_id(int spec_id) {
            this.spec_id = spec_id;
        }

        public int getCreatetime() {
            return createtime;
        }

        public void setCreatetime(int createtime) {
            this.createtime = createtime;
        }

        public PivotBean getPivot() {
            return pivot;
        }

        public void setPivot(PivotBean pivot) {
            this.pivot = pivot;
        }

        public SpecBeanX getSpec() {
            return spec;
        }

        public void setSpec(SpecBeanX spec) {
            this.spec = spec;
        }

        public static class PivotBean implements Serializable {
            /**
             * id : 328
             * goods_id : 22
             * spec_id : 20
             * spec_value_id : 44
             * create_time : 1556734842
             */

            private int id;
            private int goods_id;
            private int spec_id;
            private int spec_value_id;
            private int create_time;

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

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public int getSpec_value_id() {
                return spec_value_id;
            }

            public void setSpec_value_id(int spec_value_id) {
                this.spec_value_id = spec_value_id;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }
        }

        public static class SpecBeanX implements Serializable {
            /**
             * id : 20
             * spec_name : 颜色
             * createtime : 1541401442
             */

            private int id;
            private String spec_name;
            private int createtime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getSpec_name() {
                return spec_name;
            }

            public void setSpec_name(String spec_name) {
                this.spec_name = spec_name;
            }

            public int getCreatetime() {
                return createtime;
            }

            public void setCreatetime(int createtime) {
                this.createtime = createtime;
            }
        }
    }

    public static class TuanListBean implements Serializable {
        /**
         * id : 114
         * goods_id : 22
         * user_id : 4
         * rule_id : 2
         * status : 1
         * createtime : 1556741129
         * updatetime : 1556741129
         * deadline : 1556913929
         * nickname : ﾟ默｡
         * avatar : https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132
         * num : 4
         * surplus : 12
         * goods : {"goods_name":"Mate 20 华为 HUAWEI 1","label":"","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/e0d6dc822cf7632c66f7718bdd0dc2bc.jpg","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"tuan_list":[],"comment_list":[],"coin":0}
         * tuan_rule : {"id":2,"goods_id":22,"price":2,"name":"风光ix5  280TGDI CVT智尊型","weigh":3,"num":5,"status":"normal","createtime":1555916724,"updatetime":1556595809,"validity":2,"admin_id":0,"status_text":"开启"}
         * user : {"nickname":"ﾟ默｡","avatar":"https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132"}
         * tuan : {"num":4,"surplus":12}
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
        private String surplus;
        private GoodsBean goods;
        private TuanRuleBeanX tuan_rule;
        private UserBean user;
        private TuanBean tuan;

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

        public String getSurplus() {
            return surplus;
        }

        public void setSurplus(String surplus) {
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public TuanBean getTuan() {
            return tuan;
        }

        public void setTuan(TuanBean tuan) {
            this.tuan = tuan;
        }

        public static class GoodsBean implements Serializable {
            /**
             * goods_name : Mate 20 华为 HUAWEI 1
             * label :
             * images : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/e0d6dc822cf7632c66f7718bdd0dc2bc.jpg
             * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg
             * spec_type_text :
             * deduct_stock_type_text :
             * goods_status_text :
             * is_delete_text :
             * tuan_rule : false
             * tuan : false
             * tuan_list : []
             * comment_list : []
             * coin : 0
             */

            private String goods_name;
            private String label;
            private String images;
            private String image;
            private String spec_type_text;
            private String deduct_stock_type_text;
            private String goods_status_text;
            private String is_delete_text;
            private boolean tuan_rule;
            private boolean tuan;
            private int coin;
            private List<?> tuan_list;
            private List<?> comment_list;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
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

        public static class TuanRuleBeanX implements Serializable {
            /**
             * id : 2
             * goods_id : 22
             * price : 2
             * name : 风光ix5  280TGDI CVT智尊型
             * weigh : 3
             * num : 5
             * status : normal
             * createtime : 1555916724
             * updatetime : 1556595809
             * validity : 2
             * admin_id : 0
             * status_text : 开启
             */

            private int id;
            private int goods_id;
            private String price;
            private String name;
            private String weigh;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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

        public static class UserBean implements Serializable {
            /**
             * nickname : ﾟ默｡
             * avatar : https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132
             */

            private String nickname;
            private String avatar;

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
        }

        public static class TuanBean implements Serializable {
            /**
             * num : 4
             * surplus : 12
             */

            private int num;
            private String surplus;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getSurplus() {
                return surplus;
            }

            public void setSurplus(String surplus) {
                this.surplus = surplus;
            }
        }
    }

    public static class CommentListBean implements Serializable {
        /**
         * id : 29
         * goods_id : 0
         * user_id : 2
         * order_id : 302
         * nickname : 嘻哈江湖
         * avatar : https://ahxtao.hfcoco.top/uploads/20190429/db00fae60f092ccf9bef0607cef4924f.jpg
         * star : 5
         * detail : 很好
         * images : https://ahxtao.hfcoco.top/uploads/20190501/37b99a4245257091f7f9076ca129edf0.jpg
         * createtime : 1556717549
         * updatetime : 1556717549
         * status : normal
         * reply : null
         * admin_id : 0
         */

        private int id;
        private int goods_id;
        private int user_id;
        private int order_id;
        private String nickname;
        private String avatar;
        private int star;
        private String detail;
        private String images;
        private int createtime;
        private int updatetime;
        private String status;
        private Object reply;
        private int admin_id;

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

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
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

        public int getStar() {
            return star;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getReply() {
            return reply;
        }

        public void setReply(Object reply) {
            this.reply = reply;
        }

        public int getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(int admin_id) {
            this.admin_id = admin_id;
        }
    }
}
