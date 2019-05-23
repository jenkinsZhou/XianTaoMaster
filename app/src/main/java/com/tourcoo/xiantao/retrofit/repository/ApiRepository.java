package com.tourcoo.xiantao.retrofit.repository;


import android.text.TextUtils;

import com.tourcoo.xiantao.core.frame.retrofit.RetryWhen;
import com.tourcoo.xiantao.core.frame.retrofit.TourCoolRetrofit;
import com.tourcoo.xiantao.core.frame.retrofit.TourCoolTransformer;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.entity.SystemSettingEntity;
import com.tourcoo.xiantao.entity.advertisement.AdverDetailEntity;
import com.tourcoo.xiantao.entity.advertisement.AdvertisEntity;
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
import com.tourcoo.xiantao.entity.news.NewsBean;
import com.tourcoo.xiantao.entity.recharge.RechargeHistory;
import com.tourcoo.xiantao.entity.tuan.TuanDetails;
import com.tourcoo.xiantao.entity.user.CashEntity;
import com.tourcoo.xiantao.helper.GoodsCount;
import com.tourcoo.xiantao.retrofit.service.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import io.reactivex.Observable;

import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_ALI;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_BALANCE;
import static com.tourcoo.xiantao.widget.dialog.PayDialog.PAY_TYPE_WE_XIN;

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


    public ApiService getApiService() {
        mApiService = TourCoolRetrofit.getInstance().createService(ApiService.class);
        return mApiService;
    }

    /**
     * 不使用缓存
     *
     * @return
     */
    public ApiService getNoCacheApiService() {
        mApiService = TourCoolRetrofit.getInstance().createService(ApiService.class, false);
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
    public Observable<BaseEntity> register(String mobile, String password, String captcha, String inviteCode) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("mobile", mobile);
        params.put("username", mobile);
        params.put("password", password);
        params.put("event", "register");
        params.put("captcha", captcha);
        if (!TextUtils.isEmpty(inviteCode)) {
            params.put("invite", inviteCode);
        }
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
     * 重置密码
     *
     * @return
     */
    public Observable<BaseEntity> restPassword(String mobile, String password, String vCode) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("mobile", mobile);
        params.put("newpassword", password);
        params.put("event", "resetpwd");
        params.put("captcha", vCode);
        return TourCoolTransformer.switchSchedulersIo(getApiService().restPassword(params).retryWhen(new RetryWhen()));
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
    public Observable<BaseEntity> requestHomeInfo() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestHomeInfo().retryWhen(new RetryWhen()));
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
    public Observable<BaseEntity<List<AddressEntity>>> myAddressList() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().myAddressList().retryWhen(new RetryWhen()));
    }

    /**
     * 地址列表
     *
     * @return
     */
    public Observable<BaseEntity> addAddress(String region, String name, String phone, String detail) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("region", region);
        params.put("name", name);
        params.put("phone", phone);
        params.put("detail", detail);
        return TourCoolTransformer.switchSchedulersIo(getApiService().addAddress(params).retryWhen(new RetryWhen()));
    }

    /**
     * 删除地址
     *
     * @param addressId
     * @return
     */
    public Observable<BaseEntity> deleteAddress(int addressId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", addressId + "");
        return TourCoolTransformer.switchSchedulersIo(getApiService().deleteAddress(params).retryWhen(new RetryWhen()));
    }

    /**
     * 编辑地址
     *
     * @param addressId
     * @return
     */
    public Observable<BaseEntity> editAddress(int addressId, String region, String name, String phone, String detail) {
        Map<String, Object> params = new HashMap<>(5);
        params.put("id", addressId + "");
        params.put("region", region);
        params.put("name", name);
        params.put("phone", phone);
        params.put("detail", detail);
        TourCooLogUtil.i(TAG, params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().editAddress(params).retryWhen(new RetryWhen()));
    }

    /**
     * 设置默认地址
     *
     * @param addressId
     * @return
     */
    public Observable<BaseEntity> setDefaultAddress(int addressId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", addressId + "");
        TourCooLogUtil.i(TAG, params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().setDefaultAddress(params).retryWhen(new RetryWhen()));
    }

    /**
     * 获取分类页面商品列表
     *
     * @return
     */
    public Observable<BaseEntity> getGoodsClassify() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().getGoodsClassify().retryWhen(new RetryWhen()));
    }


    /**
     * 根据商品id获取商品详情
     *
     * @return
     */
    public Observable<BaseEntity> getGoodsDetail(int goodsId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goodsId);
        TourCooLogUtil.i(TAG, TAG + ":" + goodsId);
        return TourCoolTransformer.switchSchedulersIo(getApiService().getGoodsDetail(params).retryWhen(new RetryWhen()));
    }


    /**
     * 直接购买结算接口
     *
     * @param goodsId
     * @param goodsCount
     * @param skuId
     * @return
     */
    public Observable<BaseEntity> settleGoods(int goodsId, int goodsCount, String skuId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goodsId);
        params.put("goods_num", goodsCount);
        params.put("goods_sku_id", skuId);
        TourCooLogUtil.i("结算订单参数", params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().settleGoods(params).retryWhen(new RetryWhen()));
    }

    /**
     * 单个购买立即支付接口
     *
     * @param map
     * @param payType
     * @return
     */
    public Observable<BaseEntity> buyNowPay(Map<String, Object> map, int payType) {
        Map<String, Object> params = new HashMap<>(4);
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            params.put(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        }
        String payTypeString;
        switch (payType) {
            case PAY_TYPE_ALI:
                payTypeString = "ali";
                break;
            case PAY_TYPE_BALANCE:
                payTypeString = "cash";
                break;
            case PAY_TYPE_WE_XIN:
                payTypeString = "wx";
                break;
            default:
                payTypeString = "cash";
                break;
        }
        params.put("pay_type", payTypeString);
        TourCooLogUtil.i("订单结算", params);

        /**
         * goods_id	string	是	商品id
         * goods_num	string	是	数量
         * goods_sku_id	string	是	规格sku_id
         * coin_status	string	是	1使用金币抵扣,0不使用
         * remark	string	是	订单备注
         * pay_type	string	是	cash余额,ali支付宝,wx微信
         */
        return TourCoolTransformer.switchSchedulersIo(getApiService().buyNowPay(params).retryWhen(new RetryWhen()));
    }


    /**
     * 添加购物车接口
     *
     * @param goodsId
     * @param goodsCount
     * @param skuId
     * @return
     */
    public Observable<BaseEntity<GoodsCount>> addGoods(int goodsId, int goodsCount, String skuId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goodsId);
        params.put("goods_num", goodsCount);
        params.put("goods_sku_id", skuId);
        TourCooLogUtil.i(TAG, TAG + ":" + goodsId);
        return TourCoolTransformer.switchSchedulersIo(getApiService().addGoods(params).retryWhen(new RetryWhen()));
    }


    /**
     * 获取购物车中商品数量
     *
     * @return
     */
    public Observable<BaseEntity<GoodsCount>> getTotalNum() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().getTotalNum().retryWhen(new RetryWhen()));
    }

    /**
     * 结算我的购物车中商品列表
     *
     * @return
     */
    public Observable<BaseEntity> settleMyShoppingCarList() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().settleMyShoppingCarList().retryWhen(new RetryWhen()));
    }

    /**
     * 结算我的购物车中商品列表
     *
     * @return
     */
    public Observable<BaseEntity> getMyShoppingCarList() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().getMyShoppingCarList().retryWhen(new RetryWhen()));
    }

    /**
     * 购物车减
     *
     * @param goodsId
     * @param skuId
     * @return
     */
    public Observable<BaseEntity<GoodsCount>> reduceGoods(int goodsId, String skuId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goodsId);
        params.put("goods_sku_id", skuId);
        TourCooLogUtil.i(TAG, TAG + ":" + goodsId);
        return TourCoolTransformer.switchSchedulersIo(getApiService().reduceGoods(params).retryWhen(new RetryWhen()));
    }

    /**
     * 删除购物车中当前商品
     *
     * @param goodsId
     * @param skuId
     * @return
     */
    public Observable<BaseEntity<GoodsCount>> deleteGoods(int goodsId, String skuId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goodsId);
        params.put("goods_sku_id", skuId);
        TourCooLogUtil.i(TAG, TAG + ":" + goodsId);
        return TourCoolTransformer.switchSchedulersIo(getApiService().deleteGoods(params).retryWhen(new RetryWhen()));
    }


    /**
     * 根据商品分类id获取商品列表
     *
     * @param categoryId
     * @return
     */
    public Observable<BaseEntity> getCategoryGoodsList(String param, int categoryId, String categoryType, int pageIndex, String keyWord) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("types", categoryType);
        params.put("p", pageIndex);
        if (param != null) {
            params.put("param", param);
        } else {
            if (!TextUtils.isEmpty(keyWord)) {
                params.put("id", -1);
                params.put("name", keyWord);
            } else {
                params.put("id", categoryId);
            }
        }
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().getCategoryGoodsList(params).retryWhen(new RetryWhen()));
    }


    /**
     * 会员中心
     *
     * @return
     */
    public Observable<BaseEntity> getPersonalCenter() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().getPersonalCenter().retryWhen(new RetryWhen()));
    }


    /**
     * 获取我的订单列表
     *
     * @return
     */
    public Observable<BaseEntity> requestOrderInfo(int orderStatus, int page) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("p", page);
        params.put("status", orderStatus);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestOrderInfo(params).retryWhen(new RetryWhen()));
    }


    /**
     * 获取订单详情
     *
     * @return
     */
    public Observable<BaseEntity> requestOrderDetail(int orderId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", orderId);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestOrderDetail(params).retryWhen(new RetryWhen()));
    }


    /**
     * 取消订单
     *
     * @return
     */
    public Observable<BaseEntity> requestCancelOrder(int orderId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", orderId);
        TourCooLogUtil.i(TAG, TAG + "参数:" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestCancelOrder(params).retryWhen(new RetryWhen()));
    }

    /**
     * 申请退单
     *
     * @param orderId
     * @return
     */
    public Observable<BaseEntity> requestReturnGoods(int orderId, String goodsIds, String detail, String images, String reason, String type) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", orderId);
        params.put("goods_id", goodsIds);
        params.put("detail", detail);
        if (!TextUtils.isEmpty(images)) {
            params.put("images", images);
        }
        params.put("reason", reason);
        params.put("type", type);
        TourCooLogUtil.i(TAG, TAG + "退货提交的参数:" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestReturnGoods(params).retryWhen(new RetryWhen()));
    }


    public Observable<BaseEntity> collectAdd(int goodsIds) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goodsIds);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().collectAdd(params).retryWhen(new RetryWhen()));
    }


    public Observable<BaseEntity> collectCancel(int goodsIds) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goodsIds);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().collectCancel(params).retryWhen(new RetryWhen()));
    }


    /**
     * 获取我的拼团记录
     *
     * @return
     */
    public Observable<BaseEntity> requestTuanListInfo(int tuanStatus, int page) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("p", page);
        params.put("type", tuanStatus);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestTuanListInfo(params).retryWhen(new RetryWhen()));
    }


    /**
     * 修改个人信息
     *
     * @param avatarUrl
     * @param nickname
     * @param gender
     * @param birthday
     * @return
     */
    public Observable<BaseEntity> editUserInfo(String avatarUrl, String nickname, int gender, String birthday) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("avatar", avatarUrl);
        params.put("nickname", nickname);
        params.put("gender", gender);
        params.put("birthday", birthday);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().editUserInfo(params).retryWhen(new RetryWhen()));
    }


    /**
     * 充值
     *
     * @return
     */
    public Observable<BaseEntity> recharge(String amount, int payType) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("amount", amount);
        String payTypeString;
        switch (payType) {
            case PAY_TYPE_ALI:
                payTypeString = "ali";
                break;
            case PAY_TYPE_WE_XIN:
                payTypeString = "wx";
                break;
            default:
                payTypeString = "ali";
                break;
        }
        params.put("pay_type", payTypeString);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().recharge(params).retryWhen(new RetryWhen()));
    }

    /**
     * 账户查询
     *
     * @return
     */
    public Observable<BaseEntity<CashEntity>> requestBalance() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestBalance().retryWhen(new RetryWhen()));
    }

    /**
     * 充值记录列表
     *
     * @return
     */
    public Observable<BaseEntity<RechargeHistory>> requestRechargeList(int page) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("p", page);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestRechargeList(params).retryWhen(new RetryWhen()));
    }


    /**
     * 消息列表
     *
     * @return
     */
    public Observable<BaseEntity<MessageEntity>> requestMessageList(int index) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("p", index);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestMessageList(params).retryWhen(new RetryWhen()));
    }

    /**
     * 未读消息数量
     *
     * @return
     */
    public Observable<BaseEntity<MessageBean>> requestMessageNoReadCount() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestMessageNoReadCount().retryWhen(new RetryWhen()));
    }


    /**
     * 信息详情
     *
     * @return
     */
    public Observable<BaseEntity<MessageBean>> requestMessageBean(String id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestMessageBean(params).retryWhen(new RetryWhen()));
    }


    /**
     * 获取商品收藏列表
     *
     * @return
     */
    public Observable<BaseEntity<GoodsCollectEntity>> requestGoodsCollectList(int pageIndex) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("p", pageIndex);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestGoodsCollectList(params).retryWhen(new RetryWhen()));
    }


    /**
     * 金币列表
     *
     * @param pageIndex
     * @return
     */
    public Observable<BaseEntity<CoinHistory>> requestMyCoinList(int pageIndex) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("p", pageIndex);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestMyCoinList(params).retryWhen(new RetryWhen()));
    }


    /**
     * 银币兑换金币
     *
     * @return
     */
    public Observable<BaseEntity> requestExchange() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestExchange().retryWhen(new RetryWhen()));
    }


    /**
     * 添加评价
     *
     * @param orderId
     * @param detail
     * @param star
     * @param images
     * @return
     */
    public Observable<BaseEntity> requestAddComment(int orderId, String detail, String star, String images) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("order_id", orderId);
        params.put("detail", detail);
        if (!TextUtils.isEmpty(images)) {
            params.put("images", images);
        }
        params.put("star", star);
        TourCooLogUtil.i(TAG, TAG + "图片url:" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestAddComment(params).retryWhen(new RetryWhen()));
    }


    /**
     * 确认收货
     *
     * @param orderId
     * @return
     */
    public Observable<BaseEntity> requestConfirmFinish(int orderId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", orderId);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestConfirmFinish(params).retryWhen(new RetryWhen()));
    }

    /**
     * 商品的评论列表
     *
     * @param goodsId
     * @return
     */
    public Observable<BaseEntity<CommentEntity>> requestCommentList(int goodsId, int page) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goodsId);
        params.put("p", page);
        TourCooLogUtil.i(TAG, TAG + "商品id:" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestCommentList(params).retryWhen(new RetryWhen()));
    }


    /**
     * 商品的评论列表
     *
     * @param orderId
     * @return
     */
    public Observable<BaseEntity<CommentEntity>> requestOrderCommentList(int orderId, int page) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("order_id", orderId);
        params.put("p", page);
        TourCooLogUtil.i(TAG, TAG + "商品id:" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestCommentList(params).retryWhen(new RetryWhen()));
    }

    /**
     * 发起拼团接口
     *
     * @param goodsId
     * @param goodsCount
     * @return
     */
    public Observable<BaseEntity> startNewTuan(int goodsId, int goodsCount) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goodsId);
        params.put("num", goodsCount);
        TourCooLogUtil.i(TAG, TAG + ":" + goodsId);
        return TourCoolTransformer.switchSchedulersIo(getApiService().startNewTuan(params).retryWhen(new RetryWhen()));
    }

    /**
     * 单个商品的拼团列表
     *
     * @param goods_id
     * @return
     */
    public Observable<BaseEntity<List<Goods.TuanListBean>>> tuanList(int goods_id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("goods_id", goods_id);
        return TourCoolTransformer.switchSchedulersIo(getApiService().tuanList(params).retryWhen(new RetryWhen()));
    }

    /**
     * 单个团详情
     *
     * @param tuan_id
     * @return
     */
    public Observable<BaseEntity<List<TuanDetails>>> tuanDetails(int tuan_id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("tuan_id", tuan_id);
        return TourCoolTransformer.switchSchedulersIo(getApiService().tuanDetails(params).retryWhen(new RetryWhen()));
    }


    /**
     * 支付订单
     *
     * @param orderId
     * @param payType
     * @return
     */
    public Observable<BaseEntity> requestOrderPay(int orderId, int payType) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", orderId);
        String payTypeString;
        switch (payType) {
            case PAY_TYPE_ALI:
                payTypeString = "ali";
                break;
            case PAY_TYPE_WE_XIN:
                payTypeString = "wx";
                break;
            case PAY_TYPE_BALANCE:
                payTypeString = "cash";
                break;
            default:
                payTypeString = "ali";
                break;
        }
        params.put("pay_type", payTypeString);
        TourCooLogUtil.i(TAG, TAG + "提交的参数:" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestOrderPay(params).retryWhen(new RetryWhen()));
    }


    /**
     * 加入拼团
     *
     * @param tuan_id
     * @param num
     * @return
     */
    public Observable<BaseEntity> joinTuan(int tuan_id, int num) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("tuan_id", tuan_id);
        params.put("num", num);
        return TourCoolTransformer.switchSchedulersIo(getApiService().joinTuan(params).retryWhen(new RetryWhen()));
    }


    /**
     * 购物车结算
     *
     * @return
     */
    public Observable<BaseEntity> requestCarPay(int payType, boolean useCoin, String remark, String time, String discountIds) {
        Map<String, Object> params = new HashMap<>(1);
        String payTypeString;
        switch (payType) {
            case PAY_TYPE_ALI:
                payTypeString = "ali";
                break;
            case PAY_TYPE_WE_XIN:
                payTypeString = "wx";
                break;
            case PAY_TYPE_BALANCE:
                payTypeString = "cash";
                break;
            default:
                payTypeString = "ali";
                break;
        }
        params.put("pay_type", payTypeString);
        params.put("time", time);
        if (useCoin) {
            params.put("coin_status", 1);
        } else {
            params.put("coin_status", 0);
        }
        if (!TextUtils.isEmpty(remark)) {
            params.put("remark", remark);
        } else {
            params.put("remark", "");
        }
        if (!TextUtils.isEmpty(discountIds)) {
            params.put("coupon_id", discountIds);
        }
        TourCooLogUtil.i(TAG, TAG + "提交的参数:" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestCarPay(params).retryWhen(new RetryWhen()));
    }


    /**
     * news详情
     *
     * @param id
     * @return
     */
    public Observable<BaseEntity<NewsBean>> getNewsDetails(int id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return TourCoolTransformer.switchSchedulersIo(getApiService().getNewsDetails(params).retryWhen(new RetryWhen()));
    }

    public Observable<BaseEntity> requestHomeWebDetails(String param) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("param", param);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestHomeWebDetails(params).retryWhen(new RetryWhen()));
    }


    /**
     * banner详情
     *
     * @param id
     * @return
     */
    public Observable<BaseEntity<BannerDetail>> getBannerDetails(int id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return TourCoolTransformer.switchSchedulersIo(getApiService().getBannerDetails(params).retryWhen(new RetryWhen()));
    }


    /**
     * 参团结算
     *
     * @param pinId :参团id
     * @return
     */
    public Observable<BaseEntity> requestPinSettle(int pinId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("tuanuser_id", pinId);
        TourCooLogUtil.i(TAG, TAG + ":" + "团userId=" + pinId);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestPinSettle(params).retryWhen(new RetryWhen()));
    }


    /**
     * 拼团支付
     *
     * @param pinId :参团的id
     * @return
     */
    public Observable<BaseEntity> requestPinPay(int pinId, int payType, String remark, String time, String discountIds) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("tuanuser_id", pinId);
        String payTypeString;
        switch (payType) {
            case PAY_TYPE_ALI:
                payTypeString = "ali";
                break;
            case PAY_TYPE_WE_XIN:
                payTypeString = "wx";
                break;
            case PAY_TYPE_BALANCE:
                payTypeString = "cash";
                break;
            default:
                payTypeString = "ali";
                break;
        }
        params.put("pay_type", payTypeString);
        if (!TextUtils.isEmpty(discountIds)) {
            params.put("coupon_id", discountIds);
        }
        if (!TextUtils.isEmpty(remark)) {
            params.put("remark", remark);
        } else {
            params.put("remark", "");
        }
        params.put("time", time);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestPinPay(params).retryWhen(new RetryWhen()));
    }


    public Observable<BaseEntity> requestFeedback(String detail, String images, String type) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("type", type);
        params.put("detail", detail);
        if (!TextUtils.isEmpty(images)) {
            params.put("images", images);
        }
        TourCooLogUtil.i(TAG, TAG + "问题反馈提交的参数:" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestFeedback(params).retryWhen(new RetryWhen()));
    }


    /**
     * 取消退单
     *
     * @param orderId
     * @return
     */
    public Observable<BaseEntity> requestCancelReturn(int orderId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", orderId);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestCancelReturn(params).retryWhen(new RetryWhen()));
    }

    /**
     * 查看物流
     *
     * @param orderId
     * @return
     */
    public Observable<BaseEntity> requestLogistics(int orderId) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", orderId);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestLogistics(params).retryWhen(new RetryWhen()));
    }


    public Observable<BaseEntity<SystemSettingEntity>> requestSystemConfig() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestSystemConfig().retryWhen(new RetryWhen()));
    }


    /**
     * 获取广告信息
     *
     * @return
     */
    public Observable<BaseEntity> requestAdvertisement() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestAdvertisement().retryWhen(new RetryWhen()));
    }


    /**
     * 获取广告详情
     *
     * @return
     */
    public Observable<BaseEntity<AdverDetailEntity>> requestAdvertisementDetail(int id) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestAdvertisementDetail(params).retryWhen(new RetryWhen()));
    }


    /**
     * 更换手机号
     *
     * @param mobile
     * @return
     */
    public Observable<BaseEntity> requestChangeMobile(String mobile, String vCode) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("mobile", mobile);
        params.put("event", "changemobile");
        params.put("captcha", vCode);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestChangeMobile(params).retryWhen(new RetryWhen()));
    }


    /**
     * 我的优惠券
     *
     * @param type
     * @param pageIndex
     * @return
     */
    public Observable<BaseEntity> requestMyDiscount(int type, int pageIndex) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("type", type);
        params.put("p", pageIndex);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestMyDiscount(params).retryWhen(new RetryWhen()));
    }

    /**
     * 获取我的可用优惠券数量
     *
     * @return
     */
    public Observable<BaseEntity> requestAvailableDiscountNumber(double price) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("price", price);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestAvailableDiscountNumber(params).retryWhen(new RetryWhen()));
    }


    /**
     * 获取我的可用优惠券列表
     *
     * @return
     */
    public Observable<BaseEntity> requestAvailableList(double price) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("price", price);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestAvailableList(params).retryWhen(new RetryWhen()));
    }


    /**
     * 我的邀请码
     *
     * @return
     */
    public Observable<BaseEntity<String>> requestInvitecode() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestInvitecode().retryWhen(new RetryWhen()));
    }


    public Observable<BaseEntity> requestHomeGoodsList(int pageIndex) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("p", pageIndex);
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestHomeGoodsList(params).retryWhen(new RetryWhen()));
    }


    public Observable<BaseEntity> requestDeleteAllMsg() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestDeleteAllMsg().retryWhen(new RetryWhen()));
    }


    /**
     * @return
     */
    public Observable<BaseEntity> requestSettleShoppingCar() {
        return TourCoolTransformer.switchSchedulersIo(getApiService().requestSettleShoppingCar().retryWhen(new RetryWhen()));
    }

}
