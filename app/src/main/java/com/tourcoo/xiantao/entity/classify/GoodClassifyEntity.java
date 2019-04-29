package com.tourcoo.xiantao.entity.classify;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :分类实体
 * @company :途酷科技
 * @date 2019年04月24日17:05
 * @Email: 971613168@qq.com
 */
public class GoodClassifyEntity {
    private boolean select;

    /**
     * id : 6
     * pid : 0
     * name : 水果
     * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg
     * weigh : 6
     * createtime : 1540367311
     * updatetime : 1541403647
     * spacer :
     * childlist : [{"id":9,"pid":6,"name":"国产水果","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/753cd25e97135e874dca8ab5126ad144.jpg","weigh":9,"createtime":1541403546,"updatetime":1541403622,"spacer":"&nbsp;├","childlist":[],"ImageFrist":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/753cd25e97135e874dca8ab5126ad144.jpg"},{"id":7,"pid":6,"name":"进口水果","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","weigh":7,"createtime":1540367326,"updatetime":1541403531,"spacer":"&nbsp;└","childlist":[],"ImageFrist":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg"}]
     */

    private int id;
    private int pid;
    private String name;
    private String image;
    private int weigh;
    private int createtime;
    private int updatetime;
    private String spacer;
    private List<ClassifyGoodsBean> childlist;

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

    public String getSpacer() {
        return spacer;
    }

    public void setSpacer(String spacer) {
        this.spacer = spacer;
    }

    public List<ClassifyGoodsBean> getChildlist() {
        return childlist;
    }

    public void setChildlist(List<ClassifyGoodsBean> childlist) {
        this.childlist = childlist;
    }


    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
