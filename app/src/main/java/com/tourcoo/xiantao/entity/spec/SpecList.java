package com.tourcoo.xiantao.entity.spec;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月26日9:45
 * @Email: 971613168@qq.com
 */
public class SpecList {

    /**
     * goods_spec_id : 158
     * spec_sku_id : 48
     * rows : []
     * form : {"goods_no":"mac_0001","goods_price":"12608.00","goods_weight":1.2,"line_price":"0.00","stock_num":989,"spec_image":"","imgshow":null}
     */

    private int goods_spec_id;
    private String spec_sku_id;
    private Form form;
    private List<?> rows;

    public int getGoods_spec_id() {
        return goods_spec_id;
    }

    public void setGoods_spec_id(int goods_spec_id) {
        this.goods_spec_id = goods_spec_id;
    }

    public String getSpec_sku_id() {
        return spec_sku_id;
    }

    public void setSpec_sku_id(String spec_sku_id) {
        this.spec_sku_id = spec_sku_id;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
