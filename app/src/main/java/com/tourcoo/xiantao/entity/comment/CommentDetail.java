package com.tourcoo.xiantao.entity.comment;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :评论详情实体
 * @company :途酷科技
 * @date 2019年04月30日9:51
 * @Email: 971613168@qq.com
 */
public class CommentDetail {


    /**
     * id : 15
     * goods_id : 0
     * user_id : 4
     * order_id : 131
     * nickname : 照明胧
     * avatar : https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png
     * star : 5
     * detail : 宝贝收到了，卖家发货很快，物流也非常给力,客服服务态度极好，很有耐心、给人一种亲切感，好喜欢。还有包装精美，高端大气上档次;看得出来商家很用心。宝贝真心不错，与图片相符，没有任何差异，真的是物超所值，物美价廉啊。质量很好，下次还会再关顾!快递很给力，好评！发货快性价比高，给满分好评！我买东西习惯默默拍下，没有太大的问题是不会去询问客服，当然如果产品有问题，我是不会发表这条言论的。在此希望店主越做越好，回头客多多。很不错的购物体验，对比好多家最后选择了这家，果然很安心!客服也很负责，很耐心的给了推荐，即时回答问题!很赞!关键是东东很满意!值得推荐!还在犹豫的朋友们可以下手了!
     * images : https://ahxtao.hfcoco.top/uploads/20190429/f16e6221dc715968f1cf50542fd98e23.png,https://ahxtao.hfcoco.top/uploads/20190429/6d3cf79834ad34eae5d72cb8cedc95ec.png,https://ahxtao.hfcoco.top/uploads/20190429/fb977bfad363c254ecf00812caf7ede9.png,https://ahxtao.hfcoco.top/uploads/20190429/4142c1b8ccaf092514651c41abdc5fc4.png,https://ahxtao.hfcoco.top/uploads/20190429/d1003640a2986dbee143ccb61f81f873.png,https://ahxtao.hfcoco.top/uploads/20190429/ce5b1f8993a393bb06f1f5e08f0b25ce.png,https://ahxtao.hfcoco.top/uploads/20190429/db672cf714b1c64ccf212a6448155419.png,https://ahxtao.hfcoco.top/uploads/20190429/d7593a9ab029746e0e67c152230b0225.png
     * createtime : 1556504940
     * updatetime : 1556504940
     * status : normal
     * reply : null
     * admin_id : 0
     * imgs_arr : ["https://ahxtao.hfcoco.top/uploads/20190429/f16e6221dc715968f1cf50542fd98e23.png","https://ahxtao.hfcoco.top/uploads/20190429/6d3cf79834ad34eae5d72cb8cedc95ec.png","https://ahxtao.hfcoco.top/uploads/20190429/fb977bfad363c254ecf00812caf7ede9.png","https://ahxtao.hfcoco.top/uploads/20190429/4142c1b8ccaf092514651c41abdc5fc4.png","https://ahxtao.hfcoco.top/uploads/20190429/d1003640a2986dbee143ccb61f81f873.png","https://ahxtao.hfcoco.top/uploads/20190429/ce5b1f8993a393bb06f1f5e08f0b25ce.png","https://ahxtao.hfcoco.top/uploads/20190429/db672cf714b1c64ccf212a6448155419.png","https://ahxtao.hfcoco.top/uploads/20190429/d7593a9ab029746e0e67c152230b0225.png"]
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
    private long createtime;
    private int updatetime;
    private String status;
    private Object reply;
    private int admin_id;
    private List<String> imgs_arr;

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

    public long getCreatetime() {
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

    public List<String> getImgs_arr() {
        return imgs_arr;
    }

    public void setImgs_arr(List<String> imgs_arr) {
        this.imgs_arr = imgs_arr;
    }
}
