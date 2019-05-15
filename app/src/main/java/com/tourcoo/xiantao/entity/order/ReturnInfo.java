package com.tourcoo.xiantao.entity.order;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :退货信息
 * @company :途酷科技
 * @date 2019年05月05日15:19
 * @Email: 971613168@qq.com
 */
public class ReturnInfo implements Serializable {


    /**
     * id : 127
     * goods_id : 714
     * user_id : 2
     * order_id : 604
     * nickname : 一个板凳
     * avatar : https://ahxtao.hfcoco.top/uploads/20190505/5a2ee8973cba811423f6072bda1f05da.jpg
     * detail : TM卡
     * images :
     * createtime : 1557039986
     * updatetime : 1557039986
     * reply : null
     * admin_id : 0
     * price : 0
     * reason : 缺货
     * type : 退货退款
     * status : 0
     * coin : 0
     * status_text : 退款申请中
     */

    private int id;
    private String goods_id;
    private int user_id;
    private int order_id;
    private String nickname;
    private String avatar;
    private String detail;
    private String images;
    private int createtime;
    private int updatetime;
    private Object reply;
    private int admin_id;
    private double price;
    private String reason;
    private String type;
    private String status;
    private double coin;
    private String status_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}
