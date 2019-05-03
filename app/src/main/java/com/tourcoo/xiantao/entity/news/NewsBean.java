package com.tourcoo.xiantao.entity.news;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月23日16:26
 * @Email: 971613168@qq.com
 */
public class NewsBean {


    /**
     * id : 1
     * name : 一元特卖，新品尝鲜
     * content :
     * status : normal
     * createtime : 1555393900
     * updatetime : 1556332552
     * admin_id : 0
     * status_text : 正常
     */

    private int id;
    private String name;
    private String content;
    private String status;
    private int createtime;
    private int updatetime;
    private int admin_id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}
