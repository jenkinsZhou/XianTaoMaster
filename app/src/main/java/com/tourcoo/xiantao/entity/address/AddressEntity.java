package com.tourcoo.xiantao.entity.address;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月24日10:15
 * @Email: 971613168@qq.com
 */
public class AddressEntity implements Serializable {

    /**
     * address_id : 11
     * name : 周健
     * phone : 18256070563
     * province_id : 1046
     * city_id : 1047
     * region_id : 3749
     * detail : 埃索达生命法杖
     * user_id : 3
     * isdefault : 0
     * createtime : 1556071737
     * updatetime : 1556071808
     * Area : {"province":"安徽省","city":"合肥市","region":"高新区"}
     */

    private int address_id;
    private String name;
    private String phone;
    private int province_id;
    private int city_id;
    private int region_id;
    private String detail;
    private int user_id;
    private String isdefault;
    private int createtime;
    private int updatetime;
    private AreaBean Area;

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

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

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
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

    public AreaBean getArea() {
        return Area;
    }

    public void setArea(AreaBean Area) {
        this.Area = Area;
    }

    public static class AreaBean implements Serializable{
        /**
         * province : 安徽省
         * city : 合肥市
         * region : 高新区
         */

        private String province;
        private String city;
        private String region;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }


}
