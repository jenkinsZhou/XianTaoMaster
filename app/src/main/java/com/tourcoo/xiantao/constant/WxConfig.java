package com.tourcoo.xiantao.constant;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月26日10:05
 * @Email: 971613168@qq.com
 */
public class WxConfig {
    /**
     * 微信支付 APP_ID
     */
    public static final String APP_ID = "wx0617b9768e39eaca";
    public static final String APP_SECRET = "ecda10305d1f9e30043b0414877d0f2c";


    /**
     * 拼团链接
     */
    public static final String WEIXIN_PIN_URL = "http://www.ahxtao.com/";

    /**
     * 微信分享小程序
     */
    /**
     * 小程序ID
     */
    public static final String MINI_PROGRAM_USERNAME = "gh_a16418673486";
    /**
     * 小程序路径
     */
    public static final String MINI_PROGRAM_PATH = "/pages/progress/progress?from=out&tuan_id=";

    public static final String MINI_PROGRAM_PATH_GOODS_DETAIL = "/pages/goodsLsit/goodsDetails/goodsDetails?goods_id=";


    /**
     * 微信支付的tag
     */
    public static int weiXinPayTag = 1;

    /**
     * 微信支付充值标记
     */
    public static final int WEI_XIN_PAY_TAG_RECHARGE = 2;

    /**
     * 微信支付普通标记
     */
    public static final int WEI_XIN_PAY_TAG_NORMAL = 1;
}
