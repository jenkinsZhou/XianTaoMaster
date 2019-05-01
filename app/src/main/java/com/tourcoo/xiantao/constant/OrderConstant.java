package com.tourcoo.xiantao.constant;

/**
 * @author :JenkinsZhou
 * @description :订单常量
 * @company :途酷科技
 * @date 2019年04月27日19:36
 * @Email: 971613168@qq.com
 */
public class OrderConstant {
    /**
     * 0全部1待付款2待发货3待收货4待评价5退单
     */
    public static final int ORDER_STATUS_ALL = 0;

    public static final int ORDER_STATUS_WAIT_PAY = 1;

    /**
     * 待发货
     */
    public static final int ORDER_STATUS_WAIT_SEND = 2;

    public static final int ORDER_STATUS_WAIT_RECIEVE = 3;

    public static final int ORDER_STATUS_WAIT_COMMENT = 4;

    /**
     * 退货列表
     */
    public static final int ORDER_STATUS_BACK = 5;

    public static final int ORDER_STATUS_FINISH = 6;
    /**
     * 退货中
     */
    public static final int ORDER_STATUS_BACK_ING = 7;

    /**
     * 退货被拒绝
     */
    public static final int ORDER_STATUS_BACK_REFUSE = 8;

    /**
     * 退货完成
     */
    public static final int ORDER_STATUS_BACK_FINISH = 9;

    public static final int NOT_FINISH = 10;
    public static final int FINISH = 20;
}
