package com.tourcoo.xiantao.entity.home;

import com.tourcoo.xiantao.entity.banner.BannerBean;
import com.tourcoo.xiantao.entity.news.NewsBean;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月21日15:54
 * @Email: 971613168@qq.com
 */
public class HomeGoodsNewBean implements Serializable {


    /**
     * news : []
     * banner : [{"id":0,"image":"https://app.ahxtao.com/uploads/20190517/b8c30233c729a01c2c34c7e631b15418.jpg","status_text":""},{"id":0,"image":"https://app.ahxtao.com/uploads/20190517/1c32e2822fe92d27e2382ff79e3b869c.jpg","status_text":""},{"id":0,"image":"https://app.ahxtao.com/uploads/20190517/41c04e90ac2afa48c07c1f9e0dcb7989.jpg","status_text":""}]
     * goods : [{"goods_id":37,"goods_name":"怡颗莓蓝莓","images":"https://app.ahxtao.com/uploads/20190517/2f4314b089c37db01aa52c4813e412f3.jpg,https://app.ahxtao.com/uploads/20190517/24db42b7717eeb137c3e223f6a26cff7.jpg,https://app.ahxtao.com/uploads/20190517/21d3824479d5ff7b0ff6fb25aec957e8.jpg","goods_sort":37,"goods_status":"10","is_delete":"0","origin":"云南","label":"国产新鲜蓝莓鲜果粉嫩浆果新品当季水果","goods_min_price":"25.00","image":"https://app.ahxtao.com/uploads/20190517/2f4314b089c37db01aa52c4813e412f3.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":36,"goods_name":"美都西瓜","images":"https://ahxtao.hfcoco.top/uploads/20190517/381eff447a77d4ecf6ece51c21b97681.jpg,https://ahxtao.hfcoco.top/uploads/20190517/256a3062da57ebced727de5476754b82.jpg,/uploads/20190517/a4156e1281d64afa8a6bc1391e3a86a4.jpg","goods_sort":36,"goods_status":"10","is_delete":"0","origin":"上海","label":"美都西瓜超甜8424西瓜新鲜薄皮500g","goods_min_price":"7.00","image":"https://ahxtao.hfcoco.top/uploads/20190517/381eff447a77d4ecf6ece51c21b97681.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":35,"goods_name":"山东红富士","images":"https://app.ahxtao.com/uploads/20190517/f3f355d50ffc684412bed3a47e6a4413.jpg,https://app.ahxtao.com/uploads/20190517/2969d813152e3aee2f4c4a0979aac0a9.jpg,https://app.ahxtao.com/uploads/20190517/1f5e94e8f49b7ffb9100b07ffb42fece.jpg,https://app.ahxtao.com/uploads/20190517/2cab81bbe270ea6a22177dc30cde167c.jpg","goods_sort":35,"goods_status":"10","is_delete":"0","origin":"山东","label":"山东烟台红富士苹果","goods_min_price":"12.00","image":"https://app.ahxtao.com/uploads/20190517/f3f355d50ffc684412bed3a47e6a4413.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":34,"goods_name":"丑橘","images":"http://ahxtao.hfcoco.top/uploads/20190517/5e2c563f09fea10b29e8879841b57e59.jpg,/uploads/20190517/140ffeae297738fedeeea09772beb2b7.jpg,/uploads/20190517/6ffb625b7e7822ae2c39f135189ae058.jpg,/uploads/20190517/4d4ab9aae2e131883554d9b0c3d01e64.jpg","goods_sort":34,"goods_status":"10","is_delete":"0","origin":"四川","label":"四川丑橘新鲜水果当季橘子丑八怪包邮带箱","goods_min_price":"13.80","image":"http://ahxtao.hfcoco.top/uploads/20190517/5e2c563f09fea10b29e8879841b57e59.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":26,"goods_name":"国产樱桃","images":"http://ahxtao.hfcoco.top/uploads/20190517/4a869b993281f12b7dbdb0d0c35f5d85.jpg,http://ahxtao.hfcoco.top/uploads/20190517/a0b3fcebd941a4c7e7bf6d781e432de8.jpg,http://ahxtao.hfcoco.top/uploads/20190517/12aa3968e21b4196166a5dd777435c88.jpg,http://ahxtao.hfcoco.top/uploads/20190517/49965bc2cacacdd0415c2c0965dd6b0d.jpg","goods_sort":26,"goods_status":"10","is_delete":"0","origin":"山东","label":"国产樱桃","goods_min_price":"70.00","image":"http://ahxtao.hfcoco.top/uploads/20190517/4a869b993281f12b7dbdb0d0c35f5d85.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"},{"goods_id":25,"goods_name":"新奇士橙子","images":"http://ahxtao.hfcoco.top/uploads/20190517/0d921fc01b6da1e10aa1a2822fda9130.jpg,http://ahxtao.hfcoco.top/uploads/20190517/aa98de223b1e454ac24a7d4fbf22ee07.jpg,http://ahxtao.hfcoco.top/uploads/20190517/10260aea24e25a1940672dbae008e132.jpg","goods_sort":25,"goods_status":"10","is_delete":"0","origin":"美国","label":"美国进口新奇士橙子","goods_min_price":"15.00","image":"http://ahxtao.hfcoco.top/uploads/20190517/0d921fc01b6da1e10aa1a2822fda9130.jpg","deduct":0,"deduct_coin":0,"deduct_rule":"直接购买可用"}]
     * index : {"top":{"image":"https://app.ahxtao.com/uploads/20190521/0d10fea89c96a4d507309d538f9b3581.png","type":"content","param":"top"},"bottom":{"image":"https://app.ahxtao.com/uploads/20190521/f35cda3f786479199b4873aa08c0ff73.png","type":"content","param":"bottom"},"recommend1":{"image":"https://app.ahxtao.com/uploads/20190521/c6f655fe86ee2421436ca0b34b33ce19.png","type":"goods","param":"recommend1"},"recommend2":{"image":"https://app.ahxtao.com/uploads/20190521/3fe137eafc4be7919d7f18d1e42c31d8.png","type":"goods","param":"recommend2"},"recommend3":{"image":"https://app.ahxtao.com/uploads/20190521/0a0bf1926c0f48d3abd5745de7b0ca6c.png","type":"goods","param":"recommend3"}}
     */

    private IndexBean index;
    private List<NewsBean> news;
    private List<BannerBean> banner;
    private List<GoodsBean> goods;

    public IndexBean getIndex() {
        return index;
    }

    public void setIndex(IndexBean index) {
        this.index = index;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

    public static class IndexBean implements Serializable {
        /**
         * top : {"image":"https://app.ahxtao.com/uploads/20190521/0d10fea89c96a4d507309d538f9b3581.png","type":"content","param":"top"}
         * bottom : {"image":"https://app.ahxtao.com/uploads/20190521/f35cda3f786479199b4873aa08c0ff73.png","type":"content","param":"bottom"}
         * recommend1 : {"image":"https://app.ahxtao.com/uploads/20190521/c6f655fe86ee2421436ca0b34b33ce19.png","type":"goods","param":"recommend1"}
         * recommend2 : {"image":"https://app.ahxtao.com/uploads/20190521/3fe137eafc4be7919d7f18d1e42c31d8.png","type":"goods","param":"recommend2"}
         * recommend3 : {"image":"https://app.ahxtao.com/uploads/20190521/0a0bf1926c0f48d3abd5745de7b0ca6c.png","type":"goods","param":"recommend3"}
         */

        private RecommendBean top;
        private RecommendBean bottom;
        private RecommendBean recommend1;
        private RecommendBean recommend2;
        private RecommendBean recommend3;

        public RecommendBean getTop() {
            return top;
        }

        public void setTop(RecommendBean top) {
            this.top = top;
        }

        public RecommendBean getBottom() {
            return bottom;
        }

        public void setBottom(RecommendBean bottom) {
            this.bottom = bottom;
        }

        public RecommendBean getRecommend1() {
            return recommend1;
        }

        public void setRecommend1(RecommendBean recommend1) {
            this.recommend1 = recommend1;
        }

        public RecommendBean getRecommend2() {
            return recommend2;
        }

        public void setRecommend2(RecommendBean recommend2) {
            this.recommend2 = recommend2;
        }

        public RecommendBean getRecommend3() {
            return recommend3;
        }

        public void setRecommend3(RecommendBean recommend3) {
            this.recommend3 = recommend3;
        }


    }



    public static class GoodsBean implements Serializable {
        /**
         * goods_id : 37
         * goods_name : 怡颗莓蓝莓
         * images : https://app.ahxtao.com/uploads/20190517/2f4314b089c37db01aa52c4813e412f3.jpg,https://app.ahxtao.com/uploads/20190517/24db42b7717eeb137c3e223f6a26cff7.jpg,https://app.ahxtao.com/uploads/20190517/21d3824479d5ff7b0ff6fb25aec957e8.jpg
         * goods_sort : 37
         * goods_status : 10
         * is_delete : 0
         * origin : 云南
         * label : 国产新鲜蓝莓鲜果粉嫩浆果新品当季水果
         * goods_min_price : 25.00
         * image : https://app.ahxtao.com/uploads/20190517/2f4314b089c37db01aa52c4813e412f3.jpg
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

        public void setDeduct_coin(int deduct_coin) {
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
