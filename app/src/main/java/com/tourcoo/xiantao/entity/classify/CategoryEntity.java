package com.tourcoo.xiantao.entity.classify;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年06月26日9:32
 * @Email: 971613168@qq.com
 */
public class CategoryEntity {

    /**
     * id : 11
     * pid : 10
     * name : 当季热销
     * image : https://test2.ahxtao.comhttps://test2.ahxtao.comhttps://test2.ahxtao.com/uploads/20190522/efff3b8a201b7047d2c1702794531467.jpg
     * weigh : 50
     * createtime : 1558061829
     * updatetime : 1561428451
     * ImageFrist : https://test2.ahxtao.comhttps://test2.ahxtao.comhttps://test2.ahxtao.com/uploads/20190522/efff3b8a201b7047d2c1702794531467.jpg
     */
    private boolean select;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    private int id;
    private int pid;
    private String name;
    private String image;
    private int weigh;
    private int createtime;
    private int updatetime;
    private String ImageFrist;

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

    public String getImageFrist() {
        return ImageFrist;
    }

    public void setImageFrist(String ImageFrist) {
        this.ImageFrist = ImageFrist;
    }
}
