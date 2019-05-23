package com.tourcoo.xiantao.entity.home;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月23日10:55
 * @Email: 971613168@qq.com
 */
public class HomeGoodsEntity {


    /**
     * total : 10
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"goods_id":47,"goods_name":"佳沛金果","images":"https://app.ahxtao.com/uploads/20190523/940d6722f67f9d0b61a3dd7bf9abc97f.jpg,https://app.ahxtao.com/uploads/20190523/6ea80bc1320c1ae68349e09ddff0ca22.jpg,https://app.ahxtao.com/uploads/20190523/922ac9168e88ee1fc1ed56a178f15382.jpg","goods_sort":47,"goods_status":"10","is_delete":"0","origin":"新西兰","label":"进口,黄心,新鲜","goods_min_price":"8.00","image":"https://app.ahxtao.com/uploads/20190523/940d6722f67f9d0b61a3dd7bf9abc97f.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":46,"goods_name":"佳沛绿果","images":"https://app.ahxtao.com/uploads/20190523/e5328b0ecd359fa7651188952c555594.jpg,https://app.ahxtao.com/uploads/20190523/1980e60528e7b3d80e2500bb21d67825.jpg,/uploads/20190523/f9fb9aed2abdb4bd61f7aea42f4207f3.jpg","goods_sort":46,"goods_status":"10","is_delete":"0","origin":"新西兰","label":"新鲜,进口,绿心","goods_min_price":"4.00","image":"https://app.ahxtao.com/uploads/20190523/e5328b0ecd359fa7651188952c555594.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":45,"goods_name":"妃子笑","images":"https://app.ahxtao.com/uploads/20190523/d9cd12abef22f66451cce9dbd1802122.jpg,https://app.ahxtao.com/uploads/20190523/7c9516f9d573f4f3b7e88e5547de9d30.jpg,https://app.ahxtao.com/uploads/20190523/473565d69b1661ad2ffb205b696cef4a.jpg","goods_sort":45,"goods_status":"10","is_delete":"0","origin":"海南","label":"新鲜,白糖窰","goods_min_price":"19.80","image":"https://app.ahxtao.com/uploads/20190523/d9cd12abef22f66451cce9dbd1802122.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":44,"goods_name":"大青芒","images":"https://app.ahxtao.com/uploads/20190523/929cdfb9f5e2289db6e3c303cf748ddf.jpg,/uploads/20190523/3f00276a7fd7890e7a741af117bc8d58.jpg","goods_sort":44,"goods_status":"10","is_delete":"0","origin":"海南","label":"新鲜芒果,大青芒,海南厦门","goods_min_price":"13.00","image":"https://app.ahxtao.com/uploads/20190523/929cdfb9f5e2289db6e3c303cf748ddf.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":37,"goods_name":"怡颗莓蓝莓","images":"https://ahxtao.hfcoco.top/uploads/20190517/2f4314b089c37db01aa52c4813e412f3.jpg,https://ahxtao.hfcoco.top/uploads/20190517/24db42b7717eeb137c3e223f6a26cff7.jpg,https://ahxtao.hfcoco.top/uploads/20190517/21d3824479d5ff7b0ff6fb25aec957e8.jpg","goods_sort":37,"goods_status":"10","is_delete":"0","origin":"云南","label":"国产蓝莓,新鲜蓝莓,鲜果粉嫩浆果,新品上市,当季水果","goods_min_price":"25.00","image":"https://ahxtao.hfcoco.top/uploads/20190517/2f4314b089c37db01aa52c4813e412f3.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":36,"goods_name":"美都西瓜","images":"https://ahxtao.hfcoco.top/uploads/20190517/381eff447a77d4ecf6ece51c21b97681.jpg,https://ahxtao.hfcoco.top/uploads/20190517/256a3062da57ebced727de5476754b82.jpg,/uploads/20190517/a4156e1281d64afa8a6bc1391e3a86a4.jpg","goods_sort":36,"goods_status":"10","is_delete":"0","origin":"上海","label":"美都西瓜超甜8424西瓜新鲜薄皮500g","goods_min_price":"7.00","image":"https://ahxtao.hfcoco.top/uploads/20190517/381eff447a77d4ecf6ece51c21b97681.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":35,"goods_name":"山东红富士","images":"https://ahxtao.hfcoco.top/uploads/20190517/f3f355d50ffc684412bed3a47e6a4413.jpg,https://ahxtao.hfcoco.top/uploads/20190517/2969d813152e3aee2f4c4a0979aac0a9.jpg,https://ahxtao.hfcoco.top/uploads/20190517/1f5e94e8f49b7ffb9100b07ffb42fece.jpg,https://ahxtao.hfcoco.top/uploads/20190517/2cab81bbe270ea6a22177dc30cde167c.jpg","goods_sort":35,"goods_status":"10","is_delete":"0","origin":"山东","label":"山东烟台红富士苹果","goods_min_price":"12.00","image":"https://ahxtao.hfcoco.top/uploads/20190517/f3f355d50ffc684412bed3a47e6a4413.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":34,"goods_name":"丑橘","images":"http://ahxtao.hfcoco.top/uploads/20190517/5e2c563f09fea10b29e8879841b57e59.jpg,/uploads/20190517/140ffeae297738fedeeea09772beb2b7.jpg,/uploads/20190517/6ffb625b7e7822ae2c39f135189ae058.jpg,/uploads/20190517/4d4ab9aae2e131883554d9b0c3d01e64.jpg","goods_sort":34,"goods_status":"10","is_delete":"0","origin":"四川","label":"四川丑橘新鲜水果当季橘子丑八怪包邮带箱","goods_min_price":"13.80","image":"http://ahxtao.hfcoco.top/uploads/20190517/5e2c563f09fea10b29e8879841b57e59.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":26,"goods_name":"国产樱桃","images":"http://ahxtao.hfcoco.top/uploads/20190517/4a869b993281f12b7dbdb0d0c35f5d85.jpg,http://ahxtao.hfcoco.top/uploads/20190517/a0b3fcebd941a4c7e7bf6d781e432de8.jpg,http://ahxtao.hfcoco.top/uploads/20190517/12aa3968e21b4196166a5dd777435c88.jpg,http://ahxtao.hfcoco.top/uploads/20190517/49965bc2cacacdd0415c2c0965dd6b0d.jpg","goods_sort":26,"goods_status":"10","is_delete":"0","origin":"山东","label":"国产樱桃","goods_min_price":"70.00","image":"http://ahxtao.hfcoco.top/uploads/20190517/4a869b993281f12b7dbdb0d0c35f5d85.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":25,"goods_name":"新奇士橙子","images":"http://ahxtao.hfcoco.top/uploads/20190517/0d921fc01b6da1e10aa1a2822fda9130.jpg,http://ahxtao.hfcoco.top/uploads/20190517/aa98de223b1e454ac24a7d4fbf22ee07.jpg,http://ahxtao.hfcoco.top/uploads/20190517/10260aea24e25a1940672dbae008e132.jpg","goods_sort":25,"goods_status":"10","is_delete":"0","origin":"美国","label":"美国进口新奇士橙子","goods_min_price":"15.00","image":"http://ahxtao.hfcoco.top/uploads/20190517/0d921fc01b6da1e10aa1a2822fda9130.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<GoodsBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public List<GoodsBean> getData() {
        return data;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
    }

    public static class GoodsBean {
        /**
         * goods_id : 47
         * goods_name : 佳沛金果
         * images : https://app.ahxtao.com/uploads/20190523/940d6722f67f9d0b61a3dd7bf9abc97f.jpg,https://app.ahxtao.com/uploads/20190523/6ea80bc1320c1ae68349e09ddff0ca22.jpg,https://app.ahxtao.com/uploads/20190523/922ac9168e88ee1fc1ed56a178f15382.jpg
         * goods_sort : 47
         * goods_status : 10
         * is_delete : 0
         * origin : 新西兰
         * label : 进口,黄心,新鲜
         * goods_min_price : 8.00
         * image : https://app.ahxtao.com/uploads/20190523/940d6722f67f9d0b61a3dd7bf9abc97f.jpg
         * deduct : 0
         * deduct_coin : 0
         * deduct_rule : 直接购买可用
         */

        private int goods_id;
        private String goods_name;
        private String images;
        private int goods_sort;
        private String goods_status;
        private String is_delete;
        private String origin;
        private String label;
        private double goods_min_price;
        private String image;
        private double deduct;
        private double deduct_coin;
        private String deduct_rule;

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

        public int getGoods_sort() {
            return goods_sort;
        }

        public void setGoods_sort(int goods_sort) {
            this.goods_sort = goods_sort;
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

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public double getGoods_min_price() {
            return goods_min_price;
        }

        public void setGoods_min_price(double goods_min_price) {
            this.goods_min_price = goods_min_price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public double getDeduct() {
            return deduct;
        }

        public void setDeduct(double deduct) {
            this.deduct = deduct;
        }

        public double getDeduct_coin() {
            return deduct_coin;
        }

        public void setDeduct_coin(double deduct_coin) {
            this.deduct_coin = deduct_coin;
        }

        public String getDeduct_rule() {
            return deduct_rule;
        }

        public void setDeduct_rule(String deduct_rule) {
            this.deduct_rule = deduct_rule;
        }
    }
}
