package com.tourcoo.xiantao.entity.goods;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :拼团规则实体(Goods内部使用)
 * @company :途酷科技
 * @date 2019年04月25日15:27
 * @Email: 971613168@qq.com
 */
public class TuanRule implements Serializable {
    /**
     * status_text :
     */

    private String status_text;

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}
