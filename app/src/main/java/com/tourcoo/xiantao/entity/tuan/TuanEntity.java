package com.tourcoo.xiantao.entity.tuan;

import java.util.List;

public class TuanEntity {


    /**
     * total : 4
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":118,"goods_id":29,"user_id":10,"rule_id":5,"status":1,"createtime":1556777016,"updatetime":1556777016,"deadline":1556863416,"user_status":0,"tuanuser_id":158,"goods":{"goods_name":"精选砀山酥梨1","label":"","images":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","image":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":5,"goods_id":29,"price":15,"name":"55/10kg","weigh":2.5,"num":2,"status":"normal","createtime":1556345831,"updatetime":1556530406,"validity":1,"admin_id":0,"status_text":"开启"},"user":{"nickname":"18133676739","avatar":"https://ahxtao.hfcoco.top/assets/img/avatar.png"},"tuan":{"num":2,"surplus":5}},{"id":117,"goods_id":29,"user_id":10,"rule_id":5,"status":1,"createtime":1556776922,"updatetime":1556776922,"deadline":1556863322,"user_status":0,"tuanuser_id":157,"goods":{"goods_name":"精选砀山酥梨1","label":"","images":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","image":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":5,"goods_id":29,"price":15,"name":"55/10kg","weigh":2.5,"num":2,"status":"normal","createtime":1556345831,"updatetime":1556530406,"validity":1,"admin_id":0,"status_text":"开启"},"user":{"nickname":"18133676739","avatar":"https://ahxtao.hfcoco.top/assets/img/avatar.png"},"tuan":{"num":2,"surplus":5}},{"id":116,"goods_id":29,"user_id":10,"rule_id":5,"status":1,"createtime":1556741426,"updatetime":1556741426,"deadline":1556827826,"user_status":0,"tuanuser_id":156,"goods":{"goods_name":"精选砀山酥梨1","label":"","images":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","image":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":5,"goods_id":29,"price":15,"name":"55/10kg","weigh":2.5,"num":2,"status":"normal","createtime":1556345831,"updatetime":1556530406,"validity":1,"admin_id":0,"status_text":"开启"},"user":{"nickname":"18133676739","avatar":"https://ahxtao.hfcoco.top/assets/img/avatar.png"},"tuan":{"num":2,"surplus":5}},{"id":115,"goods_id":29,"user_id":10,"rule_id":5,"status":1,"createtime":1556741367,"updatetime":1556741367,"deadline":1556827767,"user_status":0,"tuanuser_id":155,"goods":{"goods_name":"精选砀山酥梨1","label":"","images":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","image":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"tuan_list":[],"comment_list":[],"coin":0},"tuan_rule":{"id":5,"goods_id":29,"price":15,"name":"55/10kg","weigh":2.5,"num":2,"status":"normal","createtime":1556345831,"updatetime":1556530406,"validity":1,"admin_id":0,"status_text":"开启"},"user":{"nickname":"18133676739","avatar":"https://ahxtao.hfcoco.top/assets/img/avatar.png"},"tuan":{"num":2,"surplus":5}}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 118
         * goods_id : 29
         * user_id : 10
         * rule_id : 5
         * status : 1
         * createtime : 1556777016
         * updatetime : 1556777016
         * deadline : 1556863416
         * user_status : 0
         * tuanuser_id : 158
         * goods : {"goods_name":"精选砀山酥梨1","label":"","images":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","image":"https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg","spec_type_text":"","deduct_stock_type_text":"","goods_status_text":"","is_delete_text":"","tuan_rule":false,"tuan":false,"tuan_list":[],"comment_list":[],"coin":0}
         * tuan_rule : {"id":5,"goods_id":29,"price":15,"name":"55/10kg","weigh":2.5,"num":2,"status":"normal","createtime":1556345831,"updatetime":1556530406,"validity":1,"admin_id":0,"status_text":"开启"}
         * user : {"nickname":"18133676739","avatar":"https://ahxtao.hfcoco.top/assets/img/avatar.png"}
         * tuan : {"num":2,"surplus":5}
         */

        private int id;
        private int goods_id;
        private int user_id;
        private int rule_id;
        private int status;
        private int createtime;
        private int updatetime;
        private int deadline;
        private int user_status;
        private int tuanuser_id;
        private GoodsBean goods;
        private TuanRuleBean tuan_rule;
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

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }

        public int getTuanuser_id() {
            return tuanuser_id;
        }

        public void setTuanuser_id(int tuanuser_id) {
            this.tuanuser_id = tuanuser_id;
        }

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public TuanRuleBean getTuan_rule() {
            return tuan_rule;
        }

        public void setTuan_rule(TuanRuleBean tuan_rule) {
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

        public static class GoodsBean {
            /**
             * goods_name : 精选砀山酥梨1
             * label :
             * images : https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg
             * image : https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg
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

        public static class TuanRuleBean {
            /**
             * id : 5
             * goods_id : 29
             * price : 15
             * name : 55/10kg
             * weigh : 2.5
             * num : 2
             * status : normal
             * createtime : 1556345831
             * updatetime : 1556530406
             * validity : 1
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

        public static class UserBean {
            /**
             * nickname : 18133676739
             * avatar : https://ahxtao.hfcoco.top/assets/img/avatar.png
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

        public static class TuanBean {
            /**
             * num : 2
             * surplus : 5
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
}
