package com.tourcoo.xiantao.core.widget.core.view.navigation;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.tourcoo.xiantao.R;

import java.lang.reflect.Method;

/**
 * @author :zhoujian
 * @description : 导航栏控制工具类
 * @company :途酷科技
 * @date 2019年02月28日下午 03:04
 * @Email: 971613168@qq.com
 */

public class NavigationBarUtil {
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
    private static final String MIUI_FORCE_FSG_NAV_BAR = "force_fsg_nav_bar";
    private static final String VIVO_NAVIGATION_GESTURE_ON = "navigation_gesture_on";
    /**
     * 监控虚拟导航栏是否关闭--目前测试华为手机可用户手动开启关闭
     */
    private static final String NAVIGATION_BAR_IS_MIN = "navigationbar_is_min";
    /**
     * 三星导航栏可关闭属性
     */
    private static final String NAVIGATION_BAR_HIDE_BAR_ENABLED = "navigationbar_hide_bar_enabled";
    /**
     * 其它导航栏可关闭属性
     */
    private static final String NAVIGATION_BAR_POLICY_CONTROL = "policy_control";

    /**
     * 判断是否是全面屏
     */
    private volatile static boolean mHasCheckFullScreen;
    private volatile static boolean mIsFullScreenDevice;
    private volatile static float mAspectRatio = 1.97f;

    /**
     * 判断手机是否开启全面屏手势
     *
     * @param context
     * @return
     */
    public static boolean isOpenFullScreenGestures(Context context) {
        if (context == null || !isFullScreenDevice(context)) {
            return false;
        }
        //判断小米手机是否开启了全面屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(context.getContentResolver(), MIUI_FORCE_FSG_NAV_BAR, 0) != 0) {
                return true;
            }
        }
        //ViVo是否开启了全面屏手势
        if (Settings.Secure.getInt(context.getContentResolver(), VIVO_NAVIGATION_GESTURE_ON, 0) != 0) {
            return true;
        }
        //华为导航栏隐藏
        if (Settings.System.getInt(context.getContentResolver(), NAVIGATION_BAR_IS_MIN, 0) == 1) {
            return true;
        }
        //三星导航栏隐藏
        if (Settings.System.getInt(context.getContentResolver(), NAVIGATION_BAR_HIDE_BAR_ENABLED, 0) == 1) {
            return true;
        }
        //其它导航栏隐藏
        if ("immersive.navigation=*".equals(Settings.System.getString(context.getContentResolver(), NAVIGATION_BAR_POLICY_CONTROL))) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //华为导航栏隐藏
            if (Settings.Global.getInt(context.getContentResolver(), NAVIGATION_BAR_IS_MIN, 0) == 1) {
                return true;
            }
            //三星导航栏隐藏
            if (Settings.Global.getInt(context.getContentResolver(), NAVIGATION_BAR_HIDE_BAR_ENABLED, 0) == 1) {
                return true;
            }
            //其它导航栏隐藏
            if ("immersive.navigation=*".equals(Settings.Global.getString(context.getContentResolver(), NAVIGATION_BAR_POLICY_CONTROL))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否开启虚拟导航栏
     *
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (isOpenFullScreenGestures(activity)) {
            return false;
        }
        //其他手机根据屏幕真实高度与显示高度是否相同来判断
        WindowManager windowManager = activity.getWindowManager();
        Display d = windowManager.getDefaultDisplay();
        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            d.getRealMetrics(realDisplayMetrics);
        }
        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);
        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;
        return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
    }

    /**
     * 获取假导航栏高度
     *
     * @return
     */
    public static int getFakeNavigationBarHeight(Activity activity) {
        if (activity == null) {
            return 0;
        }
        View viewNavigation = activity.getWindow().getDecorView().findViewById(R.id.fake_navigation_layout);
        if (viewNavigation != null) {
            return viewNavigation.getMeasuredHeight();
        }
        return 0;
    }

    /**
     * 获取真实的导航栏高度--有些全面屏设置全面屏手势通过{@link #getNavigationBarHeight(Activity)}仍会获取到高度
     * 该方法通过获取Activity activity.getWindow().getDecorView()下是否有导航栏View --
     * 像华为这种可以动态隐藏的
     * 1-进入Activity 前没有打开导航栏 是获取不到导航栏View的,当上滑时才创建即可获取到，再关闭{@link View#getVisibility()}
     *
     * @param activity
     * @return 获取的前提是 window.getDecorView().setSystemUiVisibility 未设置 View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
     * 设置后系统是不会生成那个View的
     */
    public static int getRealNavigationBarHeight(Activity activity) {
        if (activity != null) {
            View viewNavigation = activity.getWindow().getDecorView().findViewById(android.R.id.navigationBarBackground);
            if (viewNavigation != null) {
                return viewNavigation.getMeasuredHeight();
            }
        }
        return 0;
    }

    /**
     * 获取导航栏高度
     *
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static int getNavigationBarHeight(Activity activity) {
        if (activity == null) {
            return 0;
        }
        //是否横屏
        boolean landscape = Resources.getSystem().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (hasNavBar(activity)) {
            return getInternalDimensionSize(landscape ? NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME : NAV_BAR_HEIGHT_RES_NAME);
        }
        return 0;
    }

    private static int getInternalDimensionSize(String key) {
        int result = 0;
        try {
            int resourceId = Resources.getSystem().getIdentifier(key, "dimen", "android");
            if (resourceId > 0) {
                result = Resources.getSystem().getDimensionPixelSize(resourceId);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取导航栏高度
     *
     * @param windowManager 废弃 使用{{@link #getNavigationBarHeight(Activity)}}替换
     * @return
     */
    @Deprecated
    public static int getNavigationBarHeight(WindowManager windowManager) {
        int dpi = 0;
        try {
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics dm = new DisplayMetrics();
            @SuppressWarnings("rawtypes")
            Class c;
            try {
                c = Class.forName("android.view.Display");
                @SuppressWarnings("unchecked")
                Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
                method.invoke(display, dm);
                dpi = dm.heightPixels;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dpi - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 判断当前手机是否为全面屏--通过纵横比
     *
     * @param context
     * @return
     */
    public static boolean isFullScreenDevice(Context context) {
        if (context == null) {
            return false;
        }
        if (mHasCheckFullScreen) {
            return mIsFullScreenDevice;
        }
        mHasCheckFullScreen = true;
        mIsFullScreenDevice = false;
        // 低于 API 21的，都不会是全面屏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        mIsFullScreenDevice = getAspectRatio(context) >= mAspectRatio;
        return mIsFullScreenDevice;
    }

    /**
     * 获取手机纵横比
     *
     * @param context
     * @return
     */
    public static float getAspectRatio(Context context) {
        if (context == null) {
            return 0f;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealSize(point);
            }
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            return height / width;
        }
        return 0f;
    }

}
