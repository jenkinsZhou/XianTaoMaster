package com.tourcoo.xiantao.entity.advertisement;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月06日15:22
 * @Email: 971613168@qq.com
 */
public class AdverDetailEntity  {


    /**
     * id : 1
     * name : 1
     * image : https://ahxtao.hfcoco.top/uploads/20190505/c7239e0f281d1302a5e2dc391ee76f6c.png
     * content :
     * status : normal
     * createtime : 0
     * updatetime : 1557122802
     * admin_id : 0
     * type : 1
     * goods_id : 0
     */

    private int id;
    private String name;
    private String image;
    private String content;
    private String status;
    private int createtime;
    private int updatetime;
    private int admin_id;
    private int type;
    private int goods_id;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }
}
