package com.tourcoo.xiantao.entity.spec;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月26日9:38
 * @Email: 971613168@qq.com
 */
public class SkuAttribute {
    private String item_id;
    private String spec_value;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getSpec_value() {
        return spec_value;
    }

    public void setSpec_value(String spec_value) {
        this.spec_value = spec_value;
    }

    public SkuAttribute() {
    }

    public SkuAttribute(String item_id, String spec_value) {
        this.item_id = item_id;
        this.spec_value = spec_value;
    }
}
