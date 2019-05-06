package com.tourcoo.xiantao.entity.advertisement;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :广告实体
 * @company :途酷科技
 * @date 2019年05月06日14:07
 * @Email: 971613168@qq.com
 */
public class AdvertisEntity implements Serializable {

    /**
     * id : 5
     * image :
     * type : 1
     * goods_id : 0
     */

    private int id;
    private String image;
    private int type;
    private int goods_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }
}
