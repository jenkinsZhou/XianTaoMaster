package com.tourcoo.xiantao.retrofit.service;


import com.tourcoo.xiantao.entity.coin.CoinHistory;
import com.tourcoo.xiantao.entity.comment.CommentDetail;
import com.tourcoo.xiantao.entity.comment.CommentEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsCollectEntity;
import com.tourcoo.xiantao.entity.message.MessageBean;
import com.tourcoo.xiantao.entity.message.MessageEntity;
import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.TokenInfo;
import com.tourcoo.xiantao.entity.banner.BannerDetail;
import com.tourcoo.xiantao.entity.recharge.RechargeHistory;
import com.tourcoo.xiantao.entity.upload.UploadEntity;
import com.tourcoo.xiantao.entity.user.CashEntity;
import com.tourcoo.xiantao.helper.GoodsCount;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
     * 多个文件上传
     *
     * @param files
     * @return
     */
    @POST("common/upload")
    Call<BaseEntity<UploadEntity>> uploadFiles(@Body RequestBody files);

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
     * 修改密码
     *
     * @param map
     * @return
     */
    @POST("user/resetpwd")
    Observable<BaseEntity> restPassword(@QueryMap Map<String, Object> map);

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
    Observable<BaseEntity<List<AddressEntity>>> myAddressList();


    /**
     * 新增地址
     *
     * @param map
     * @return
     */
    @POST("adress/add")
    Observable<BaseEntity> addAddress(@Body Map<String, Object> map);

    /**
     * 删除地址
     *
     * @param map
     * @return
     */
    @POST("adress/del")
    Observable<BaseEntity> deleteAddress(@QueryMap Map<String, Object> map);

    /**
     * 编辑地址
     *
     * @param map
     * @return
     */
    @POST("adress/edit")
    Observable<BaseEntity> editAddress(@QueryMap Map<String, Object> map);


    /**
     * 设置默认地址
     *
     * @param map
     * @return
     */
    @POST("adress/setdefault")
    Observable<BaseEntity> setDefaultAddress(@QueryMap Map<String, Object> map);


    /**
     * 商品分类tab页
     *
     * @return
     */
    @POST("category/Showlist")
    Observable<BaseEntity> getGoodsClassify();


    /**
     * 商品详请
     *
     * @param map
     * @return
     */
    @POST("goods/detail")
    Observable<BaseEntity> getGoodsDetail(@QueryMap Map<String, Object> map);


    /**
     * 商品结算
     *
     * @param map
     * @return
     */
    @POST("order/buyNow")
    Observable<BaseEntity> settleGoods(@QueryMap Map<String, Object> map);

    /**
     * 详情中立即购买
     *
     * @param map
     * @return
     */
    @POST("order/buyNow_pay")
    Observable<BaseEntity> buyNowPay(@QueryMap Map<String, Object> map);


    /**
     * 添加商品到购物车
     *
     * @param map
     * @return
     */
    @POST("cart/add")
    Observable<BaseEntity<GoodsCount>> addGoods(@QueryMap Map<String, Object> map);

    /**
     * 获取购物车中商品数量
     *
     * @return
     */
    @POST("cart/getTotalNum")
    Observable<BaseEntity<GoodsCount>> getTotalNum();

    /**
     * 获取我的购物车商品列表
     *
     * @return
     */
    @POST("cart/getlists")
    Observable<BaseEntity> getMyShoppingCarList();


    /**
     * 购物车减
     *
     * @param map
     * @return
     */
    @POST("cart/sub")
    Observable<BaseEntity<GoodsCount>> reduceGoods(@QueryMap Map<String, Object> map);


    /**
     * 删除购物车
     *
     * @param map
     * @return
     */
    @POST("cart/delete")
    Observable<BaseEntity<GoodsCount>> deleteGoods(@QueryMap Map<String, Object> map);


    /**
     * 获取分类的商品列表
     *
     * @param map
     * @return
     */
    @POST("goods/category_list")
    Observable<BaseEntity> getCategoryGoodsList(@QueryMap Map<String, Object> map);


    /**
     * 个人中心接口
     *
     * @return
     */
    @POST("user/index")
    Observable<BaseEntity> getPersonalCenter();


    /**
     * 我的订单
     *
     * @param map
     * @return
     */
    @POST("order/my")
    Observable<BaseEntity> requestOrderInfo(@QueryMap Map<String, Object> map);


    /**
     * 我的拼团列表
     *
     * @param map
     * @return
     */
    @POST("tuan/index")
    Observable<BaseEntity> requestTuanListInfo(@QueryMap Map<String, Object> map);


    /**
     * 订单详情
     *
     * @param map
     * @return
     */
    @POST("order/detail")
    Observable<BaseEntity> requestOrderDetail(@QueryMap Map<String, Object> map);

    /**
     * 取消订单
     *
     * @param map
     * @return
     */
    @POST("order/cancel")
    Observable<BaseEntity> requestCancelOrder(@Body Map<String, Object> map);


    /**
     * 退货
     *
     * @param map
     * @return
     */
    @POST("order/order_return")
    Observable<BaseEntity> requestReturnGoods(@QueryMap Map<String, Object> map);

    /**
     * 添加收藏
     *
     * @param map
     * @return
     */
    @POST("collect/add")
    Observable<BaseEntity> collectAdd(@QueryMap Map<String, Object> map);


    /**
     * 取消收藏
     *
     * @param map
     * @return
     */
    @POST("collect/cancel")
    Observable<BaseEntity> collectCancel(@QueryMap Map<String, Object> map);

    /**
     * 修改个人信息
     *
     * @param map
     * @return
     */
    @POST("user/profile")
    Observable<BaseEntity> editUserInfo(@QueryMap Map<String, Object> map);

    /**
     * 充值
     *
     * @param map
     * @return
     */
    @POST("cash/recharge")
    Observable<BaseEntity> recharge(@QueryMap Map<String, Object> map);


    /**
     * 查询账户余额信息
     *
     * @return
     */
    @POST("cash/index")
    Observable<BaseEntity<CashEntity>> requestBalance();


    /**
     * 充值记录列表
     *
     * @param map
     * @return
     */
    @POST("cash/lists")
    Observable<BaseEntity<RechargeHistory>> requestRechargeList(@QueryMap Map<String, Object> map);


    /**
     * 消息列表
     *
     * @param map
     * @return
     */
    @POST("msg/index")
    Observable<BaseEntity<MessageEntity>> requestMessageList(@QueryMap Map<String, Object> map);


    /**
     * 未读消息数量
     *
     * @param
     * @return
     */
    @POST("msg/num")
    Observable<BaseEntity<MessageBean>> requestMessageNoReadCount();


    /**
     * 商品收藏列表
     *
     * @param map
     * @return
     */
    @POST("collect/index")
    Observable<BaseEntity<GoodsCollectEntity>> requestGoodsCollectList(@QueryMap Map<String, Object> map);


    /**
     * 金币消费列表
     *
     * @param map
     * @return
     */
    @POST("coin/index")
    Observable<BaseEntity<CoinHistory>> requestMyCoinList(@QueryMap Map<String, Object> map);

    /**
     * 转换金币
     *
     * @return
     */
    @POST("coin/exchange")
    Observable<BaseEntity> requestExchange();


    /**
     * 添加评价
     *
     * @param map
     * @return
     */
    @POST("comment/add")
    Observable<BaseEntity> requestAddComment(@QueryMap Map<String, Object> map);


    /**
     * 确认收货
     *
     * @param map
     * @return
     */
    @POST("order/finish")
    Observable<BaseEntity> requestConfirmFinish(@Body Map<String, Object> map);


    /**
     * 评论列表
     *
     * @param map
     * @return
     */
    @POST("comment/index")
    Observable<BaseEntity<CommentEntity>> requestCommentList(@Body Map<String, Object> map);


    /**
     * 订单支付
     *
     * @param map
     * @return
     */
    @POST("order/order_pay")
    Observable<BaseEntity> requestOrderPay(@QueryMap Map<String, Object> map);

    /**
     * 发起拼团接口
     * @return
     */
    @POST("tuan/launch")
    Observable<BaseEntity> startNewTuan(@QueryMap Map<String, Object> params);


    /**
     * 单个商品的拼团列表
     * @return
     */
    @POST("tuan/goods")
    Observable<BaseEntity<List<Goods.TuanListBean>>> tuanList(@QueryMap Map<String, Object> params);
}
