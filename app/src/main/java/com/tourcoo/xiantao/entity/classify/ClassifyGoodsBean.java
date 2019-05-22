package com.tourcoo.xiantao.entity.classify;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :商品
 * @company :途酷科技
 * @date 2019年04月24日17:15
 * @Email: 971613168@qq.com
 */
public class ClassifyGoodsBean {
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    /**
     * id : 9
     * pid : 6
     * name : 国产水果
     * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/753cd25e97135e874dca8ab5126ad144.jpg
     * weigh : 9
     * createtime : 1541403546
     * updatetime : 1541403622
     * spacer : &nbsp;├
     * childlist : []
     * ImageFrist : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/753cd25e97135e874dca8ab5126ad144.jpg
     */

    private int id;
    private int pid;
    private String name;
    private String image;
    private int weigh;
    private int createtime;
    private int updatetime;
    private String spacer;
    private String ImageFrist;
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

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

    public String getImageFrist() {
        return ImageFrist;
    }

    public void setImageFrist(String ImageFrist) {
        this.ImageFrist = ImageFrist;
    }

    public List<?> getChildlist() {
        return childlist;
    }

    public void setChildlist(List<ClassifyGoodsBean> childlist) {
        this.childlist = childlist;
    }
}
