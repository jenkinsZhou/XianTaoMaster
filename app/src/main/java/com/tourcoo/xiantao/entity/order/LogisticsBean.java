package com.tourcoo.xiantao.entity.order;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月05日10:53
 * @Email: 971613168@qq.com
 */
public class LogisticsBean {


    /**
     * address : {"name":"周健","phone":"18256070563","address":"安徽省合肥市蜀山区"}
     * status : 已签收
     * company : 中通快递
     * no : 75145359993290
     * info : [{"time":"2019-05-04 17:36:27","context":"【合肥市】 已签收, 签收人凭取货码签收, 如有疑问请电联: 18019575496 / 18019575496, 您的快递已经妥投。风里来雨里去, 只为客官您满意。上有老下有小, 赏个好评好不好？【请在评价快递员处帮忙点亮五颗星星哦~】"},{"time":"2019-05-04 15:48:03","context":"【合肥市】 快件已送达【快递超市的百商现代名苑3栋2单元105室】, 如有问题请电联（18019575496 / 18019575496）, 感谢使用中通快递, 期待再次为您服务!"},{"time":"2019-05-04 13:43:14","context":"【合肥市】 【合肥高新三部】 的张家文18019575496（18019575496） 正在第1次派件, 请保持电话畅通,并耐心等待"},{"time":"2019-05-04 13:41:32","context":"【合肥市】 快件已经到达 【合肥高新三部】"},{"time":"2019-05-04 10:15:12","context":"【合肥市】 快件离开 【合肥中转部】 已发往 【合肥高新三部】"},{"time":"2019-05-04 10:11:28","context":"【合肥市】 快件已经到达 【合肥中转部】"},{"time":"2019-05-03 03:45:13","context":"【东莞市】 快件离开 【虎门中心】 已发往 【合肥中转部】"},{"time":"2019-05-03 03:42:30","context":"【东莞市】 快件已经到达 【虎门中心】"},{"time":"2019-05-03 01:20:23","context":"【深圳市】 快件离开 【福田新福星】 已发往 【合肥中转部】"},{"time":"2019-05-02 18:58:12","context":"【深圳市】 【福田新福星】（0755-83269390、0755-36854690） 的 新区变色龙 （15974290119） 已揽收"}]
     */

    private AddressBean address;
    private String status;
    private String company;
    private String no;
    private List<InfoBean> info;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class AddressBean {
        /**
         * name : 周健
         * phone : 18256070563
         * address : 安徽省合肥市蜀山区
         */

        private String name;
        private String phone;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class InfoBean {
        /**
         * time : 2019-05-04 17:36:27
         * context : 【合肥市】 已签收, 签收人凭取货码签收, 如有疑问请电联: 18019575496 / 18019575496, 您的快递已经妥投。风里来雨里去, 只为客官您满意。上有老下有小, 赏个好评好不好？【请在评价快递员处帮忙点亮五颗星星哦~】
         */

        private String time;
        private String context;

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
    }
}
