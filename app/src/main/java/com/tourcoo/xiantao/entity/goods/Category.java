package com.tourcoo.xiantao.entity.goods;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :商品类型实体
 * @company :途酷科技
 * @date 2019年04月25日15:37
 * @Email: 971613168@qq.com
 */
public class Category implements Serializable {


    /**
     * id : 7
     * pid : 6
     * name : 进口水果
     * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg
     * weigh : 7
     * createtime : 1540367326
     * updatetime : 1541403531
     */

    private int id;
    private int pid;
    private String name;
    private String image;
    private int weigh;
    private int createtime;
    private int updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getWeigh() {
        return weigh;
    }

    public void setWeigh(int weigh) {
        this.weigh = weigh;
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
}
