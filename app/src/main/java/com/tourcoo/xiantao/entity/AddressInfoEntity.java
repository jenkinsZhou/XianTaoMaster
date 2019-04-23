package com.tourcoo.xiantao.entity;

/**
 * @author :JenkinsZhou
 * @description :收货信息实体类
 * @company :途酷科技
 * @date 2019年03月29日12:53
 * @Email: 971613168@qq.com
 */
public class AddressInfoEntity {

    /**
     * 收货人
     */
    public String consignee;

    public String phoneNumber;

    public String shippingAddress;

    public AddressInfoEntity(String consignee, String phoneNumber, String shippingAddress) {
        this.consignee = consignee;
        this.phoneNumber = phoneNumber;
        this.shippingAddress = shippingAddress;
    }
}
