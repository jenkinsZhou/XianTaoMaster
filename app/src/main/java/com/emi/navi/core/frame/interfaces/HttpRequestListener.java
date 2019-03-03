package com.emi.navi.core.frame.interfaces;

/**
 * @author :zhoujian
 * @description :
 * @company :翼迈科技
 * @date 2019年 03月 02日 22时17分
 * @Email: 971613168@qq.com
 */
public interface HttpRequestListener {

    /**
     * 无数据回调
     */
    void onEmpty();

    /**
     * 无更多数据回调
     */
    void onNoMore();

    /**
     * 加载数据回调
     */
    void onNext();
}
