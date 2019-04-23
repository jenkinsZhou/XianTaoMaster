package com.tourcoo.xiantao.retrofit.repository;


import com.tourcoo.xiantao.core.frame.retrofit.RetryWhen;
import com.tourcoo.xiantao.core.frame.retrofit.TourCoolRetrofit;
import com.tourcoo.xiantao.core.frame.retrofit.TourCoolTransformer;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.entity.AddressInfoEntity;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.TokenInfo;
import com.tourcoo.xiantao.entity.banner.BannerDetail;
import com.tourcoo.xiantao.retrofit.service.ApiService;

import java.util.HashMap;
import java.util.List;
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
    private static final String TAG = "ApiRepository";


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
        mApiService = TourCoolRetrofit.getInstance().createService(ApiService.class);
        return mApiService;
    }


    /**
     * 发送验证码
     *
     * @param mobile
     * @param
     * @return
     */
    public Observable<BaseEntity> sendVCode(String mobile, String event) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("mobile", mobile);
        params.put("event", event);
        return TourCoolTransformer.switchSchedulersIo(getApiService().sendVCode(params).retryWhen(new RetryWhen()));
    }


    /**
     * 注册
     *
     * @param mobile
     * @param
     * @return
     */
    public Observable<BaseEntity> register(String mobile, String password, String captcha) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("mobile", mobile);
        params.put("username", mobile);
        params.put("password", password);
        params.put("event", "register");
        params.put("captcha", captcha);
        TourCooLogUtil.i(TAG, "验证码:" + captcha);
        return TourCoolTransformer.switchSchedulersIo(getApiService().register(params).retryWhen(new RetryWhen()));
    }


    /**
     * 密码登录
     *
     * @param account
     * @param password
     * @return
     */
    public Observable<BaseEntity> loginByPassword(String account, String password) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("account", account);
        params.put("password", password);
        return TourCoolTransformer.switchSchedulersIo(getApiService().login(params).retryWhen(new RetryWhen()));
    }

    /**
     * 验证码登录
     *
     * @param account
     * @param captcha
     * @return
     */
    public Observable<BaseEntity> loginByVCode(String account, String captcha) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("mobile", account);
        params.put("captcha", captcha);
        params.put("event", "mobilelogin");
        return TourCoolTransformer.switchSchedulersIo(getApiService().mobileLogin(params).retryWhen(new RetryWhen()));
    }


    /**
     * 注销登录
     *
     * @return
     */
    public Observable<BaseEntity> logout() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().logout().retryWhen(new RetryWhen()));
    }

    /**
     * 首页banner
     *
     * @return
     */
    public Observable<BaseEntity> getHomeBanner() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().homeBanner().retryWhen(new RetryWhen()));
    }

    /**
     * 校验token
     *
     * @return
     */
    public Observable<BaseEntity<TokenInfo>> checkToken() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().checkToken().retryWhen(new RetryWhen()));
    }

    /**
     * banner详情
     *
     * @return
     */
    public Observable<BaseEntity<BannerDetail>> getBannerDetail(String bannerId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", bannerId);
        return TourCoolTransformer.switchSchedulersIo(getApiService().bannerDetail(params).retryWhen(new RetryWhen()));
    }

    /**
     * 地址列表
     *
     * @return
     */
    public Observable<BaseEntity<List<AddressInfoEntity>>> myAddressList() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().myAddressList().retryWhen(new RetryWhen()));
    }


}
