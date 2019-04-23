package com.tourcoo.xiantao.entity.goods;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :商品实体
 * @company :途酷科技
 * @date 2019年03月29日15:56
 * @Email: 971613168@qq.com
 */
public class GoodsEntity implements Serializable {

    /**
     * 商品Id
     */
    public long goodsId;
    public String goodsName;

    /**
     * 商品缩略图
     */
    public String imageThumbnailUrl;

    /**
     * 是否选中
     */
    public boolean select;


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
    public int goodsCount;


    /**
     * 实际价格
     */
    public double goodsRealPrice;

    /**
     * 规格属性
     */
    public String goodsSpec;

    public GoodsEntity(long goodsId) {
        this.goodsId = goodsId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getGoodsLabels() {
        return goodsLabels;
    }

    public void setGoodsLabels(String goodsLabels) {
        this.goodsLabels = goodsLabels;
    }

    public double getGoodsCurrentPrice() {
        return goodsCurrentPrice;
    }

    public void setGoodsCurrentPrice(double goodsCurrentPrice) {
        this.goodsCurrentPrice = goodsCurrentPrice;
    }

    public double getGoodsOldPrice() {
        return goodsOldPrice;
    }

    public void setGoodsOldPrice(double goodsOldPrice) {
        this.goodsOldPrice = goodsOldPrice;
    }

    public int getGoosCount() {
        return goodsCount;
    }

    public void setGoosCount(int goosCount) {
        this.goodsCount = goosCount;
    }

    public double getGoodsRealPrice() {
        return goodsRealPrice;
    }

    public void setGoodsRealPrice(double goodsRealPrice) {
        this.goodsRealPrice = goodsRealPrice;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }
}
