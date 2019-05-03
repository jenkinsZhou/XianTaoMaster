package com.tourcoo.xiantao.entity.order;

import java.util.List;

/**
 * Created by Administrator on 2019/3/15.
 */

public class LogisticsModel {


    /**
     * message : ok
     * status : 1
     * state : 运输中
     * com : zhongtong
     * nu : 75134466728974
     * data : [{"time":"11:59","context":"【合肥市】 快件已送达【快递超市的百商现代名苑3栋2单元105室】, 如有问题请电联（18019575496 / 18019575496）, 感谢使用中通快递, 期待再次为您服务!","date":"03-15"},{"time":"09:10","context":"【合肥市】 【合肥高新三部】 的张家文18019575496（18019575496） 正在第1次派件, 请保持电话畅通,并耐心等待","date":"03-15"},{"time":"09:11","context":"【合肥市】 快件已经到达 【合肥高新三部】","date":"03-15"},{"time":"07:12","context":"【合肥市】 快件离开 【合肥中转部】 已发往 【合肥高新三部】","date":"03-14"},{"time":"07:07","context":"【合肥市】 快件已经到达 【合肥中转部】","date":"03-14"},{"time":"05:36","context":"【金华市】 快件离开 【金华中转部】 已发往 【合肥中转部】","date":"03-14"},{"time":"12:15","context":"【金华市】 快件离开 【义乌中转部】 已发往 【合肥中转部】","date":"03-14"},{"time":"12:14","context":"【金华市】 快件已经到达 【义乌中转部】","date":"03-14"},{"time":"12:14","context":"【金华市】 【义乌营销部】（0579-85236725、0579-85236768） 的 义乌市场营销部 （17348826308） 已揽收","date":"03-14"}]
     * company : 中通快递
     * address : 公子小白
     * image : /uploads/20190311/2ad562e846cd74630921daec2d04bc77.jpg
     */

    private String message;
    private String status;
    private String state;
    private String com;
    private String nu;
    private String company;
    private String address;
    private String image;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 11:59
         * context : 【合肥市】 快件已送达【快递超市的百商现代名苑3栋2单元105室】, 如有问题请电联（18019575496 / 18019575496）, 感谢使用中通快递, 期待再次为您服务!
         * date : 03-15
         */

        private String time;
        private String context;
        private String date;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
