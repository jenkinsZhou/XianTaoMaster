package com.tourcoo.xiantao.entity.goods;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月26日9:51
 * @Email: 971613168@qq.com
 */
public class Pivot {
    /**
     * id : 140
     * goods_id : 23
     * spec_id : 20
     * spec_value_id : 48
     * create_time : 1556098793
     */

    private int id;
    private int goods_id;
    private int spec_id;
    private int spec_value_id;
    private int create_time;

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

    public int getSpec_id() {
        return spec_id;
    }

    public void setSpec_id(int spec_id) {
        this.spec_id = spec_id;
    }

    public int getSpec_value_id() {
        return spec_value_id;
    }

    public void setSpec_value_id(int spec_value_id) {
        this.spec_value_id = spec_value_id;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }
}
