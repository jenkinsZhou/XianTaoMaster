package com.tourcoo.xiantao.retrofit.service;


import com.tourcoo.xiantao.entity.UpdateEntity;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * @author :JenkinsZhou
 * @description : 接口定义
 * @company :途酷科技
 * @date 2019年03月27日15:48
 * @E-Mail: 971613168@qq.com
 */
public interface ApiService {

    /**
     * 检查应用更新--同时设置了Method及Header模式重定向请求Url,默认Method优先;
     *
     * @param map
     * @return
     */
    @GET("update")
    @Headers({"BASE_URL_NAME: " + "update"})
    Observable<UpdateEntity> updateApp(@QueryMap Map<String, Object> map);
}
