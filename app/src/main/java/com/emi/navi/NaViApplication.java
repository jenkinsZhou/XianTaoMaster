package com.emi.navi;

import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

import com.emi.navi.core.crash.CrashManager;
import com.emi.navi.core.log.NaViLogUtil;
import com.emi.navi.core.log.widget.LogFileEngineFactory;
import com.emi.navi.core.log.widget.config.LogLevel;
import com.emi.navi.core.util.ToastUtil;
import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePalApplication;

import static com.emi.navi.core.common.CommonConfig.DEBUG_MODE;
import static com.emi.navi.core.common.CommonConstant.TAG_PRE_SUFFIX;

/**
 * @author :zhoujian
 * @description :
 * @company :翼迈科技
 * @date 2019年 03月 01日 21时54分
 * @Email: 971613168@qq.com
 */
public class NaViApplication extends LitePalApplication {
    private static Application mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initLog();
        initCrashHandle();
        ToastUtil.init(mContext);
        if (LeakCanary.isInAnalyzerProcess(mContext)) {
            return;
        }
        LeakCanary.install(mContext);
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
        Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
    }

    /**
     * 初始化日志配置
     */
    private void initLog() {
        NaViLogUtil.getLogConfig()
                .configAllowLog(DEBUG_MODE)
                .configTagPrefix(TAG_PRE_SUFFIX)
                .configShowBorders(false).
                configLevel(LogLevel.TYPE_VERBOSE);
        // 支持输入日志到文件
        String filePath = Environment.getExternalStorageDirectory() + "/NaViMaster/logs/";
        NaViLogUtil.getLogFileConfig().configLogFileEnable(DEBUG_MODE)
                .configLogFilePath(filePath)
                .configLogFileLevel(LogLevel.TYPE_VERBOSE)
                .configLogFileEngine(new LogFileEngineFactory(this));
    }

}
