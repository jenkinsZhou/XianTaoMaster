package com.tourcoo.xiantao.retrofit.repository;


import android.text.TextUtils;

import com.tourcoo.xiantao.core.frame.retrofit.RetryWhen;
import com.tourcoo.xiantao.core.frame.retrofit.TourCoolRetrofit;
import com.tourcoo.xiantao.core.frame.retrofit.TourCoolTransformer;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
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
        TourCooLogUtil.i(TAG, TAG + ":" + goodsId);
        return TourCoolTransformer.switchSchedulersIo(getApiService().settleGoods(params).retryWhen(new RetryWhen()));
    }

    /**
     * 立即支付接口
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
        TourCooLogUtil.i(TAG, params);
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
     * 获取我的购物车中商品列表
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
        TourCooLogUtil.i("购物车参数:", params);
        return TourCoolTransformer.switchSchedulersIo(getNoCacheApiService().reduceGoods(params).retryWhen(new RetryWhen()));
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
    public Observable<BaseEntity> getCategoryGoodsList(int categoryId, String categoryType, int pageIndex, String keyWord) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("types", categoryType);
        params.put("p", pageIndex);
        if (!TextUtils.isEmpty(keyWord)) {
            params.put("id", -1);
            params.put("name", keyWord);
        } else {
            params.put("id", categoryId);
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
     * 申请退货
     *
     * @param orderId
     * @return
     */
    public Observable<BaseEntity> requestReturnGoods(int orderId, String goodsIds) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", orderId);
        params.put("goods_id", goodsIds);
        TourCooLogUtil.i(TAG, TAG + ":" + params);
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
        TourCooLogUtil.i(TAG, TAG + ":" + params);
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
}
