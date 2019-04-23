package com.tourcoo.xiantao.entity.banner;

/**
 * @author :JenkinsZhou
 * @description :banner详情
 * @company :途酷科技
 * @date 2019年04月21日17:39
 * @Email: 971613168@qq.com
 */
public class BannerDetail {

    /**
     * id : 1
     * image : /uploads/20190416/3ff61d64e13e64af937b785462f5fe13.png
     * content : <p>111111111111111111111111111111111111</p>
     * status : normal
     * createtime : 1555393860
     * updatetime : 1555393860
     * admin_id : 0
     * status_text : 正常
     */

    private int id;
    private String image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
