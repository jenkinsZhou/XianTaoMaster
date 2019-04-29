package com.tourcoo.xiantao.entity.banner;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月23日16:28
 * @Email: 971613168@qq.com
 */
public class BannerBean {
    public BannerBean() {
    }

    /**
     * id : 1
     * image : /uploads/20190416/3ff61d64e13e64af937b785462f5fe13.png
     * status_text :
     */


    private int id;
    private String image;
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

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }
}
