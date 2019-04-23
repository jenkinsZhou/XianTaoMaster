package com.tourcoo.xiantao.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * @author :JenkinsZhou
 * @description :系统通知栏操作工具栏
 * @company :途酷科技
 * @date 2019年03月20日14:54
 * @Email: 971613168@qq.com
 */
public class NotificationUtil {

    private NotificationManager mNotificationManager;
    private Context mContext;
    private static volatile NotificationUtil sInstance;

    private NotificationUtil() {
    }

    public static NotificationUtil getInstance() {
        if (sInstance == null) {
            synchronized (NotificationUtil.class) {
                if (sInstance == null) {
                    sInstance = new NotificationUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取通知管理器
     *
     * @return
     */
    public NotificationManager getNotificationManager() {
        if (mNotificationManager == null) {
            init(mContext);
            mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mNotificationManager;
    }

    /**
     * 初始化 建议在Application
     *
     * @param context
     * @return
     */
    public NotificationUtil init(Context context) {
        if (context == null && mContext == null) {
            throw new NullPointerException("You've to call static method init() first in Application");
        }
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
        return sInstance;
    }

    /**
     * 发送通知
     *
     * @param tag
     * @param id
     * @param notification
     * @return
     */
    public NotificationUtil notify(String tag, int id, Notification notification) {
        getNotificationManager().notify(tag, id, notification);
        return sInstance;
    }

    /**
     * 发送通知
     *
     * @param id
     * @param notification
     * @return
     */
    public NotificationUtil notify(int id, Notification notification) {
        return notify(null, id, notification);
    }

    public NotificationUtil cancel(String tag, int id) {
        getNotificationManager().cancel(tag, id);
        return sInstance;
    }

    public NotificationUtil cancel(int id) {
        return cancel(null, id);
    }

    /**
     * 关闭所有的通知
     *
     * @return
     */
    public NotificationUtil cancelAll() {
        getNotificationManager().cancelAll();
        return sInstance;
    }
}
