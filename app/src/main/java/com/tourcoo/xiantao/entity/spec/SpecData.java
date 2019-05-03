package com.tourcoo.xiantao.entity.spec;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月26日9:46
 * @Email: 971613168@qq.com
 */
public class SpecData implements Serializable {
    private List<SpecAttr> spec_attr;
    private List<SpecList> spec_list;

    public List<SpecAttr> getSpec_attr() {
        return spec_attr;
    }

    public void setSpec_attr(List<SpecAttr> spec_attr) {
        this.spec_attr = spec_attr;
    }

    public List<SpecList> getSpec_list() {
        return spec_list;
    }

    public void setSpec_list(List<SpecList> spec_list) {
        this.spec_list = spec_list;
    }
}
