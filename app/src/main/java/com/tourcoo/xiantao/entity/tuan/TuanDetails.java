package com.tourcoo.xiantao.entity.tuan;

public class TuanDetails {


    /**
     * id : 1
     * goods_id : 24
     * user_id : 3
     * tuan_id : 1
     * order_id : 0
     * rule_id : 1
     * price : 20
     * weigh : 0.4
     * num : 2
     * createtime : 1556333914
     * updatetime : 1556333914
     * status : 0
     * userinfo : {"nickname":"陈舟为","avatar":"https://ahxtao.hfcoco.top/uploads/20190426/324e19196a9b75b279324bb974776dbe.png"}
     * createtime_text : 2019-04-27 10:58:34
     * updatetime_text : 2019-04-27 10:58:34
     */

    private int id;
    private int goods_id;
    private int user_id;
    private int tuan_id;
    private int order_id;
    private int rule_id;
    private String price;
    private String weigh;
    private int num;
    private int createtime;
    private int updatetime;
    private int status;
    private UserinfoBean userinfo;
    private String createtime_text;
    private String updatetime_text;

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

    public int getTuan_id() {
        return tuan_id;
    }

    public void setTuan_id(int tuan_id) {
        this.tuan_id = tuan_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getRule_id() {
        return rule_id;
    }

    public void setRule_id(int rule_id) {
        this.rule_id = rule_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public String getCreatetime_text() {
        return createtime_text;
    }

    public void setCreatetime_text(String createtime_text) {
        this.createtime_text = createtime_text;
    }

    public String getUpdatetime_text() {
        return updatetime_text;
    }

    public void setUpdatetime_text(String updatetime_text) {
        this.updatetime_text = updatetime_text;
    }

    public static class UserinfoBean {
        /**
         * nickname : 陈舟为
         * avatar : https://ahxtao.hfcoco.top/uploads/20190426/324e19196a9b75b279324bb974776dbe.png
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
}
