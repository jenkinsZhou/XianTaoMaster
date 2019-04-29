package com.tourcoo.xiantao.entity.spec;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月26日9:40
 * @Email: 971613168@qq.com
 */
public class SpecAttr implements Serializable {

    /**
     * group_id : 20
     * group_name : 颜色
     * spec_items : [{"item_id":48,"spec_value":"天空灰"},{"item_id":49,"spec_value":"银色"}]
     */

    private int group_id;
    private String group_name;
    private List<SkuAttribute> spec_items;

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public List<SkuAttribute> getSpec_items() {
        return spec_items;
    }

    public void setSpec_items(List<SkuAttribute> spec_items) {
        this.spec_items = spec_items;
    }

}
