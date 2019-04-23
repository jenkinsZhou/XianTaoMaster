package com.tourcoo.xiantao.retrofit.service;


import com.tourcoo.xiantao.entity.AddressInfoEntity;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.TokenInfo;
import com.tourcoo.xiantao.entity.UpdateEntity;
import com.tourcoo.xiantao.entity.banner.BannerDetail;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
     * 发送验证码
     *
     * @param map
     * @return
     */
    @GET("sms/send")
    Observable<BaseEntity> sendVCode(@QueryMap Map<String, Object> map);


    /**
     * 会员注册
     *
     * @param map
     * @return
     */
    @POST("user/register")
    Observable<BaseEntity> register(@QueryMap Map<String, Object> map);


    /**
     * 会员密码登录
     *
     * @param map
     * @return
     */
    @POST("user/login")
    Observable<BaseEntity> login(@QueryMap Map<String, Object> map);

    /**
     * 验证码登录
     *
     * @param map
     * @return
     */
    @POST("user/mobilelogin")
    Observable<BaseEntity> mobileLogin(@QueryMap Map<String, Object> map);


    /**
     * 注销登录
     *
     * @param
     * @return
     */
    @POST("user/logout")
    Observable<BaseEntity> logout();

    /**
     * 首页的banner
     *
     * @return
     */
    @POST("index/index")
    Observable<BaseEntity> homeBanner();

    /**
     * 检测token是否失效
     *
     * @return
     */
    @POST("token/check")
    Observable<BaseEntity<TokenInfo>> checkToken();


    /**
     * banner详情
     *
     * @param map
     * @return
     */
    @POST("banner/detail")
    Observable<BaseEntity<BannerDetail>> bannerDetail(@QueryMap Map<String, Object> map);

    /**
     * 地址列表
     *
     * @return
     */
    @POST("adress/lists")
    Observable<BaseEntity<List<AddressInfoEntity>>> myAddressList();


}
