package com.tourcoo.xiantao.entity.sku;


import com.tourcoo.xiantao.entity.spec.SkuAttribute;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wuhenzhizao on 2017/3/6.
 */
public class Sku implements Serializable {
    private String spec_sku_id;
    private String mainImage;
    private int stockQuantity;
    private boolean inStock;
    private long originPrice;
    private long sellingPrice;
    private List<SkuAttribute> attributes;

    public String getSpec_sku_id() {
        return spec_sku_id;
    }

    public void setSpec_sku_id(String spec_sku_id) {
        this.spec_sku_id = spec_sku_id;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public long getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(long originPrice) {
        this.originPrice = originPrice;
    }

    public long getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(long sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public List<SkuAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<SkuAttribute> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Sku{" +
                "id='" + spec_sku_id + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", inStock=" + inStock +
                ", originPrice=" + originPrice +
                ", sellingPrice=" + sellingPrice +
                ", attributes=" + attributes +
                '}';
    }

    public Sku() {
    }




}
