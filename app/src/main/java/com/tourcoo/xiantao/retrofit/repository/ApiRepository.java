package com.tourcoo.xiantao.retrofit.repository;

import com.tourcoo.xiantao.XianTaoApplication;
import com.tourcoo.xiantao.core.frame.retrofit.RetryWhen;
import com.tourcoo.xiantao.core.frame.retrofit.TourcoolRetrofit;
import com.tourcoo.xiantao.core.frame.retrofit.TourCooTransformer;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.entity.UpdateEntity;
import com.tourcoo.xiantao.retrofit.service.ApiService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年03月27日16:05
 * @Email: 971613168@qq.com
 */
public class ApiRepository extends BaseRepository {
    private static volatile ApiRepository instance;
    private ApiService mApiService;

    private ApiRepository() {
        mApiService = getApiService();
    }

    public static ApiRepository getInstance() {
        if (instance == null) {
            synchronized (ApiRepository.class) {
                if (instance == null) {
                    instance = new ApiRepository();
                }
            }
        }
        return instance;
    }


    private ApiService getApiService() {
        mApiService = TourcoolRetrofit.getInstance().createService(ApiService.class);
        return mApiService;
    }


    /**
     * 检查版本--是否传递本地App 版本相关信息根据具体接口而定(demo这里是可以不需要传的,所有判断逻辑放在app端--不推荐)
     *
     * @return
     */
    public Observable<UpdateEntity> updateApp() {
        Map<String, Object> params = new HashMap<>(2);
        params.put("versionCode", TourCoolUtil.getVersionCode(XianTaoApplication.getContext()));
        params.put("versionName", TourCoolUtil.getVersionName(XianTaoApplication.getContext()));
        return TourCooTransformer.switchSchedulers(getApiService().updateApp(params).retryWhen(new RetryWhen()));
    }



/*    *//**
     * 获取电影列表
     *
     * @param url   拼接URL
     * @param start 起始 下标
     * @param count 请求总数量
     * @return
     *//*
    public Observable<String> testWheather(String url, int start, int count) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("start", start);
        params.put("count", count);
//        return TourCooTransformer.switchSchedulers(getApiService().testWeather(url, params).retryWhen(new FastRetryWhen()));
    }*/
}
