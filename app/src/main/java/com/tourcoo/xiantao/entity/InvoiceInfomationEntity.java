package com.tourcoo.xiantao.entity;

/**
 * @author :JenkinsZhou
 * @description :发票实体
 * @company :途酷科技
 * @date 2019年03月29日19:43
 * @Email: 971613168@qq.com
 */
public class InvoiceInfomationEntity {

    /**
     * 公司抬头
     */
    public String invoiceCompany;

    /**
     * 发票类型
     */
    public int invoiceType;

    /**
     * 发票金额
     */
    public double invoiceMoney;
    /**
     * 发票状态
     */
    public int invoiceStatus;

    /**
     * 发票号码
     */
    public String invoiceNumber;

    /**
     * 发票描述
     */
    public String invoiceDescription;
}
