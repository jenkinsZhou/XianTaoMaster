package com.tourcoo.xiantao.entity;

/**
 * @author :JenkinsZhou
 * @description :商品实体
 * @company :途酷科技
 * @date 2019年03月29日15:56
 * @Email: 971613168@qq.com
 */
public class GoodsEntity {
    public String goodsName;

    /**
     * 商品描述
     */
    public String goodsDescription;

    /**
     * 商品图片url
     */
    public String goodsImageUrl;
    /**
     * 标签属性
     */
    public String goodsLabels;
    /**
     * 当前价格
     */
    public double goodsCurrentPrice;

    /**
     * 过去价格
     */
    public double goodsOldPrice;

    /**
     * 数量
     */
    public int goosCount;


    /**
     * 实际价格
     */
    public double goodsRealPrice;

    /**
     * 规格
     */
    public String goodsSpec;

}
