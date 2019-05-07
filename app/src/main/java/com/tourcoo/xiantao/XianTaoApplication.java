package com.tourcoo.xiantao;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tourcoo.xiantao.core.crash.CrashManager;
import com.tourcoo.xiantao.core.frame.UiConfigManager;
import com.tourcoo.xiantao.core.frame.impl.ActivityControlImpl;
import com.tourcoo.xiantao.core.frame.impl.HttpRequestControlImpl;
import com.tourcoo.xiantao.core.frame.impl.SwipeBackControlImpl;
import com.tourcoo.xiantao.core.frame.impl.UiConfigImpl;
import com.tourcoo.xiantao.core.frame.retrofit.TourCoolRetrofit;
import com.tourcoo.xiantao.core.frame.util.SizeUtil;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.LogFileEngineFactory;
import com.tourcoo.xiantao.core.log.widget.config.LogLevel;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.squareup.leakcanary.LeakCanary;
import com.tourcoo.xiantao.util.AddressHelper;

import org.litepal.LitePalApplication;

import androidx.multidex.MultiDex;

import static com.tourcoo.xiantao.core.common.CommonConfig.BUGLY_APPID;
import static com.tourcoo.xiantao.core.common.CommonConfig.DEBUG_MODE;
import static com.tourcoo.xiantao.core.common.CommonConstant.TAG_PRE_SUFFIX;
import static com.tourcoo.xiantao.core.common.RequestConfig.BASE_URL;
import static com.tourcoo.xiantao.core.common.RequestConfig.BASE_URL_API;

/**
 * @author :zhoujian
 * @description :
 * @company :途酷科技
 * @date 2019年 03月 01日 21时54分
 * @Email: 971613168@qq.com
 */
public class XianTaoApplication extends LitePalApplication {
    private static Application mContext;
    private static int imageHeight = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initLog();
        //初始化地址信息
        AddressHelper.getInstance().initAddressData();
        initCrashHandle();
        ToastUtil.init(mContext);
        //最简单UI配置模式-必须进行初始化
        UiConfigManager.init(this);
//        initBugLy();
        UiConfigImpl impl = new UiConfigImpl(mContext);
        ActivityControlImpl activityControl = new ActivityControlImpl();
        UiConfigManager.getInstance()
                //设置Adapter加载更多视图--默认设置了FastLoadMoreView
                .setLoadMoreFoot(impl)
                //全局设置RecyclerView
                .setRecyclerViewControl(impl)
                //设置RecyclerView加载过程多布局属性
                .setMultiStatusView(impl)
                //设置全局网络请求等待Loading提示框如登录等待loading--观察者必须为FastLoadingObserver及其子类
                .setLoadingDialog(impl)
                //设置SmartRefreshLayout刷新头-自定加载使用BaseRecyclerViewAdapterHelper
                .setDefaultRefreshHeader(impl)
                //设置全局TitleBarView相关配置
                .setTitleBarViewControl(impl)
                //设置Activity滑动返回控制-默认开启滑动返回功能不需要设置透明主题
                .setSwipeBackControl(new SwipeBackControlImpl())
                //设置Activity/Fragment相关配置(横竖屏+背景+虚拟导航栏+状态栏+生命周期)
                .setActivityFragmentControl(activityControl)
                //设置BasisActivity 子类按键监听
                .setActivityKeyEventControl(activityControl)
                //配置BasisActivity 子类事件派发相关
                .setActivityDispatchEventControl(activityControl)
                //设置http请求结果全局控制
                .setHttpRequestControl(new HttpRequestControlImpl())
                //设置主页返回键控制-默认效果为2000 毫秒时延退出程序
                .setQuitAppControl(impl)
                //设置ToastUtil全局控制
                .setToastControl(impl);

        //初始化Retrofit配置
        TourCoolRetrofit.getInstance()
                //配置全局网络请求BaseUrl
                //todo
                .setBaseUrl(BASE_URL_API)
                //信任所有证书--也可设置setCertificates(单/双向验证)
                .setCertificates()
                //设置统一请求头
//                .addHeader(header)
//                .addHeader(key,value)
                //设置请求全局log-可设置tag及Level类型
                .setLogEnable(true)
//                .setLogEnable(BuildConfig.DEBUG, TAG, HttpLoggingInterceptor.Level.BODY)
                //设置统一超时--也可单独调用read/write/connect超时(可以设置时间单位TimeUnit)
                //默认20 s
                .setTimeout(10);

        if (DEBUG_MODE) {
            if (LeakCanary.isInAnalyzerProcess(mContext)) {
                return;
            }
            LeakCanary.install(mContext);
        }

    }

    public static Application getInstance() {
        return mContext;
    }

    /**
     * 异常机制初始化
     */
    private void initCrashHandle() {
        //todo：暂时不将错误日志写入到文件
        CrashManager.init(mContext);
    }

    /**
     * 初始化日志配置
     */
    private void initLog() {
        TourCooLogUtil.getLogConfig()
                .configAllowLog(DEBUG_MODE)
                .configTagPrefix(TAG_PRE_SUFFIX)
                .configShowBorders(false).
                configLevel(LogLevel.TYPE_VERBOSE);
        // 支持输入日志到文件
        String filePath = Environment.getExternalStorageDirectory() + "/XianTaoMaster/logs/";
        TourCooLogUtil.getLogFileConfig().configLogFileEnable(DEBUG_MODE)
                .configLogFilePath(filePath)
                .configLogFileLevel(LogLevel.TYPE_VERBOSE)
                .configLogFileEngine(new LogFileEngineFactory(this));
    }

    /**
     * 是否控制底部导航栏---目前发现小米8上检查是否有导航栏出现问题
     *
     * @return
     */
    public static boolean isControlNavigation() {
        TourCooLogUtil.i("XianTaoApplication", "mode:" + Build.MODEL);
        return true;
    }


    /**
     * 初始化腾讯bug管理平台
     */
    private void initBugLy() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppVersion(TourCoolUtil.getVersionName(this));
        strategy.setAppPackageName(TourCoolUtil.getPackageName(this));
        strategy.setAppReportDelay(20000);
        Bugly.init(this, BUGLY_APPID, true, strategy);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 获取banner及个人中心设置ImageView宽高
     *
     * @return
     */
    public static int getImageHeight() {
        imageHeight = (int) (SizeUtil.getScreenWidth() * 0.55);
        return imageHeight;
    }
}
