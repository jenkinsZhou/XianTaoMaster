package com.tourcoo.xiantao.entity.news;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月23日16:26
 * @Email: 971613168@qq.com
 */
public class NewsBean {

    public NewsBean() {

    }

    public NewsBean(String name) {
        this.name = name;
    }

    /**
     * id : 1
     * name : 新闻111111111111
     * status_text :
     */

    private int id;
    private String name;
    private String status_text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}
