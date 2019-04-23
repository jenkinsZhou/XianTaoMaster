package com.tourcoo.xiantao.entity;

import com.tourcoo.xiantao.entity.banner.BannerBean;
import com.tourcoo.xiantao.entity.goods.GoodsBean;
import com.tourcoo.xiantao.entity.news.NewsBean;

import java.util.List;

/**
 * @author :zhoujian
 * @description :首页信息实体
 * @company :翼迈科技
 * @date 2019年 04月 20日 19时46分
 * @Email: 971613168@qq.com
 */
public class HomeInfoBean {
    private List<NewsBean> news;
    private List<BannerBean> banner;
    private List<GoodsBean> goods;


    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }


    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }


}
