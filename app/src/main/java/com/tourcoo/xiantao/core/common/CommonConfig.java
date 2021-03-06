package com.tourcoo.xiantao.core.common;

import static com.tourcoo.xiantao.core.common.CommonConstant.TOAST_DURATION_DEFAULT;

/**
 * @author :zhoujian
 * @description :通用配置
 * @company :途酷科技
 * @date 2019年 03月 01日 22时01分
 * @Email: 971613168@qq.com
 */
public class CommonConfig {
    /**
     * 是否开启调试模式
     */
    public static boolean DEBUG_MODE = false;

    /**
     * 腾讯bugSDK APP_ID
     */
    public static final String BUGLY_APPID = "01892b858f";

    /**
     * 吐司默认持续时间
     */
    public static long TOAST_DURATION = TOAST_DURATION_DEFAULT;

    /**
     * 图片广告的KEY
     */
    public static final String PREF_IMAGE_ADVERTISEMENT = "PREF_IMAGE_ADVERTISEMENT";

}
