package com.tourcoo.xiantao.entity.home;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月22日11:43
 * @Email: 971613168@qq.com
 */
public class WebContentInfo implements Serializable {

    /**
     * content : <p>测试文字大小</p><p><img src="https://app.ahxtao.com/uploads/20190522/5b41cd4f4963ef476218ad211f651949.jpg" data-filename="filename" style="width: 588px;"><br></p>
     */

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
