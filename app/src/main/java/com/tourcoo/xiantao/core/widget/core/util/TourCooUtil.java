package com.tourcoo.xiantao.core.widget.core.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;


import com.tourcoo.xiantao.XianTaoApplication;
import com.tourcoo.xiantao.core.common.RequestConfig;
import com.tourcoo.xiantao.core.frame.util.FormatUtil;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;


/**
 * @author :zhoujian
 * @description : 工具类
 * @company :翼迈科技
 * @date 2019年 03月 015日 10时46分
 * @Email: 971613168@qq.com
 */
public class TourCooUtil {
    public static final String TAG = "TourCooUtil";
    private static int ACTIVITY_SINGLE_FLAG = Intent.FLAG_ACTIVITY_SINGLE_TOP;
    private static final String STRING_EMPTY = "";
    private static final String URL_TAG = "http";
    private static final String URL_TAG_HTTPS = "https";
    private static final int LENGTH_PHONE = 11;

    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static CharSequence getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getText(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            TourCooLogUtil.e(TAG, "getAppName:" + e.getMessage());
        }
        return null;
    }

    /**
     * 获取 一定范围随机数
     *
     * @param max 最大值
     * @param min 最小值
     * @return
     */
    public static int getRandom(int max, int min) {
        // 定义随机类
        Random random = new Random();
        int result = random.nextInt(max) % (max - min + 1) + min;
        return result;
    }

    /**
     * 获取一定长度随机数
     *
     * @param length
     * @return
     */
    public static int getRandom(int length) {
        return getRandom(length, 1);
    }

    /**
     * 获取Activity 根布局
     *
     * @param activity
     * @return
     */
    public static View getRootView(Activity activity) {
        if (activity == null) {
            return null;
        }
        if (activity.findViewById(android.R.id.content) == null) {
            return null;
        }
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 给一个Drawable变换线框颜色
     *
     * @param drawable 需要变换颜色的drawable
     * @param color    需要变换的颜色
     * @return
     */
    public static Drawable getTintDrawable(Drawable drawable, @ColorInt int color) {
        if (drawable != null) {
            DrawableCompat.setTint(drawable, color);
        }
        return drawable;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (null != packageManager) {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                if (null != packageInfo) {
                    return packageInfo.versionName;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            TourCooLogUtil.e("getVersionName:" + e.getMessage());
        }
        return "";
    }

    /**
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            if (null != packageManager) {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                if (null != packageInfo) {
                    return packageInfo.versionCode;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            TourCooLogUtil.e("getVersionCode:" + e.getMessage());
        }
        return -1;
    }

    /**
     * 检查某个class是否存在
     *
     * @param className class的全路径包括包名+类名
     * @return
     */
    public static boolean isClassExist(String className) {
        boolean isExit = false;
        try {
            Class.forName(className);
            isExit = true;
        } catch (ClassNotFoundException e) {
            TourCooLogUtil.e("isClassExist:" + e.getMessage());
        }
        return isExit;
    }

    public static boolean isRunningForeground(Context context) {
        return isRunningForeground(context, null);
    }

    /**
     * 检查某个应用是否前台运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isRunningForeground(Context context, String packageName) {
        if (context == null) {
            return false;
        }
        if (TextUtils.isEmpty(packageName)) {
            packageName = context.getPackageName();
        }
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static void jumpMarket(Context mContext) {
        jumpMarket(mContext, null);
    }

    /**
     * 跳转应用市场详情
     *
     * @param mContext
     * @param packageName
     */
    public static void jumpMarket(Context mContext, String packageName) {
        if (mContext == null) {
            return;
        }
        if (TextUtils.isEmpty(packageName)) {
            packageName = mContext.getPackageName();
        }
        String mAddress = "market://details?id=" + packageName;
        try {
            Intent marketIntent = new Intent("android.intent.action.VIEW");
            marketIntent.setData(Uri.parse(mAddress));
            mContext.startActivity(marketIntent);
        } catch (Exception e) {
            TourCooLogUtil.e("jumpMarket:" + e.getMessage());
        }
    }

    /**
     * 设置Activity只启动一个Flag
     *
     * @param flag {@link Intent#FLAG_ACTIVITY_SINGLE_TOP}
     *             {@link Intent#FLAG_ACTIVITY_NEW_TASK}
     *             {@link Intent#FLAG_ACTIVITY_CLEAR_TOP}
     */
    public static void setActivitySingleFlag(int flag) {
        ACTIVITY_SINGLE_FLAG = flag;
    }

    /**
     * @param context
     * @param activity 跳转Activity
     * @param bundle
     * @param isSingle
     */
    public static void startActivity(Context context, Class<? extends Activity> activity, Bundle bundle, boolean isSingle) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, activity);
        intent.setFlags(isSingle ? ACTIVITY_SINGLE_FLAG : Intent.FLAG_ACTIVITY_NEW_TASK);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<? extends Activity> activity, Bundle bundle) {
        startActivity(context, activity, bundle, true);
    }

    public static void startActivity(Context context, Class<? extends Activity> activity) {
        startActivity(context, activity, null);
    }

    public static void startActivity(Context context, Class<? extends Activity> activity, boolean isSingle) {
        startActivity(context, activity, null, isSingle);
    }

    /**
     * @param context 上下文
     * @param text    分享内容
     * @param title   分享标题
     */
    public static void startShareText(Context context, String text, CharSequence title) {
        if (context == null) {
            return;
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, title));
    }

    /**
     * @param context 上下文
     * @param url     分享文字
     */
    public static void startShareText(Context context, String url) {
        startShareText(context, url, null);
    }

    /**
     * 拷贝到粘贴板
     *
     * @param context 上下文
     * @param str     需要拷贝的文字
     */
    public static void copyToClipboard(Context context, String str) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(str);
        } else {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(ClipData.newPlainText("content", str));
        }
    }


    public static String getString(int stringId) {
        return XianTaoApplication.getInstance().getResources().getString(stringId);
    }

    public static int getColor(int colorId) {
        return ContextCompat.getColor(XianTaoApplication.getInstance(), colorId);
    }


    /**
     * 判断字符串是否符合手机号码格式
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     *
     * @param mobileNums
     * @return 待检测的字符串
     */
    public static boolean isMobileNumber(String mobileNums) {
        /**
         * 判断字符串是否符合手机号码格式
         * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
         * 电信号段: 133,149,153,170,173,177,180,181,189
         * @param str
         * @return 待检测的字符串
         */
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
        if (TextUtils.isEmpty(mobileNums)) {
            return false;
        } else {
            String startValue = "1";
            return mobileNums.length() == LENGTH_PHONE && mobileNums.startsWith(startValue);
//            return mobileNums.matches(telRegex);
        }
    }

    public static Drawable getDrawable(int drawableId) {
        return ContextCompat.getDrawable(XianTaoApplication.getInstance(), drawableId);
    }

    public static String getNotNullValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }
        return value;
    }

    public static String getNotNullValue(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * 车牌号格式：汉字 + A-Z + 5位A-Z或0-9
     * （只包括了普通车牌号，教练车和部分部队车等车牌号不包括在内）
     */
    public static boolean isCarNumber(String carNumber) {
        String carNumRegex = "[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}";
        if (TextUtils.isEmpty(carNumber)) {
            return false;
        } else {
            return carNumber.matches(carNumRegex);
        }
    }


    public static String getUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return STRING_EMPTY;
        }
        if (url.contains(URL_TAG) || url.contains(URL_TAG_HTTPS)) {
            return url;
        } else {
            if (url.startsWith("/")) {
                return RequestConfig.BASE_URL_NO_LINE + url;
            } else {
                return RequestConfig.BASE_URL + url;
            }
        }
    }


    /**
     * double类型如果小数点后为零显示整数否则保留 返回String
     *
     * @param num
     * @return
     */
    public static String doubleTrans(double num) {
//        return FormatUtil.formatDoubleSize(num, 2);
        double value = Double.parseDouble(FormatUtil.formatDoubleSize(num, 2));
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(value);
    }


    /**
     * 加法运算
     *
     * @param m1
     * @param m2
     * @return
     */
    public static double addDouble(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.add(p2).doubleValue();
    }

    public static double multiply(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.multiply(p2).doubleValue();
    }

    /**
     * 减法运算
     *
     * @param m1
     * @param m2
     * @return
     */
    public static double minusDouble(double m1, double m2) {
        BigDecimal p1 = new BigDecimal(Double.toString(m1));
        BigDecimal p2 = new BigDecimal(Double.toString(m2));
        return p1.subtract(p2).doubleValue();
    }


    public static String doubleTransString(double d) {
       /* if (Math.round(d) - d == 0) {
            return String.valueOf((long) d);
        }*/
        //四舍五入 并保留两位小数
        double value = Double.parseDouble(FormatUtil.formatDoubleSize(d, 2));
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(value);
    }

    public static String doubleTransStringZhen(double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d);
        }
        //四舍五入 并保留两位小数
        double value = Double.parseDouble(FormatUtil.formatDoubleSize(d, 2));
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(value);
    }

    /**
     * 保留一位小数
     * @param d
     * @return
     */
    public static String doubleTransStringZhenKeepOnePoint(double d) {
        if (Math.round(d) - d == 0) {
            return String.valueOf((long) d);
        }
        //四舍五入 并保留两位小数
        double value = Double.parseDouble(FormatUtil.formatDoubleSize(d, 1));
        DecimalFormat df = new DecimalFormat("#0.0");
        return df.format(value);
    }

    private static final Double MILLION = 10000.0;
    private static final Double MILLIONS = 1000000.0;
    private static final Double BILLION = 100000000.0;
    private static final String MILLION_UNIT = "万";
    private static final String BILLION_UNIT = "亿";

    /**
     * 将数字转换成以万为单位或者以亿为单位，因为在前端数字太大显示有问题
     *
     * @param amount 报销金额
     * @return
     * @author
     * @version 1.00.00
     * @date 2018年1月18日
     */
    public static String amountConversion(double amount) {
        //最终返回的结果值
        String result = String.valueOf(amount);
        //四舍五入后的值
        double value = 0;
        //转换后的值
        double tempValue = 0;
        //余数
        double remainder = 0;

        //金额大于1百万小于1亿
        if (amount > MILLIONS && amount < BILLION) {
            tempValue = amount / MILLION;
            remainder = amount % MILLION;

            //余数小于5000则不进行四舍五入
            if (remainder < (MILLION / 2)) {
                value = formatNumber(tempValue, 2, false);
            } else {
                value = formatNumber(tempValue, 2, true);
            }
            //如果值刚好是10000万，则要变成1亿
            if (value == MILLION) {
                result = zeroFill(value / MILLION) + BILLION_UNIT;
            } else {
                result = zeroFill(value) + MILLION_UNIT;
            }
        }
        //金额大于1亿
        else if (amount > BILLION) {
            tempValue = amount / BILLION;
            remainder = amount % BILLION;

            //余数小于50000000则不进行四舍五入
            if (remainder < (BILLION / 2)) {
                value = formatNumber(tempValue, 2, false);
            } else {
                value = formatNumber(tempValue, 2, true);
            }
            result = zeroFill(value) + BILLION_UNIT;
        } else {
            result = zeroFill(amount);
        }
        return result;
    }


    /**
     * 对数字进行四舍五入，保留2位小数
     *
     * @param number   要四舍五入的数字
     * @param decimal  保留的小数点数
     * @param rounding 是否四舍五入
     */
    public static Double formatNumber(double number, int decimal, boolean rounding) {
        BigDecimal bigDecimal = new BigDecimal(number);

        if (rounding) {
            return bigDecimal.setScale(decimal, RoundingMode.HALF_UP).doubleValue();
        } else {
            return bigDecimal.setScale(decimal, RoundingMode.DOWN).doubleValue();
        }
    }

    /**
     * 对四舍五入的数据进行补0显示，即显示.00
     *
     * @return
     * @author
     * @version 1.00.00
     * @date 2018年1月23日
     */
    public static String zeroFill(double number) {
        String value = String.valueOf(number);

        if (value.indexOf(".") < 0) {
            value = value + ".00";
        } else {
            String decimalValue = value.substring(value.indexOf(".") + 1);

            if (decimalValue.length() < 2) {
                value = value + "0";
            }
        }
        return value;
    }


}
