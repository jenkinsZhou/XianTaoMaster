package com.tourcoo.xiantao.core.helper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;

import com.tourcoo.xiantao.core.frame.base.activity.BaseActivity;
import com.tourcoo.xiantao.core.frame.retrofit.BaseDownloadObserver;
import com.tourcoo.xiantao.core.frame.retrofit.TourcoolRetrofit;
import com.tourcoo.xiantao.core.frame.util.FileUtil;
import com.tourcoo.xiantao.core.frame.util.FormatUtil;
import com.tourcoo.xiantao.core.frame.util.StackUtil;
import com.tourcoo.xiantao.core.log.TourcoolLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.entity.UpdateEntity;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;

/**
 * @author :JenkinsZhou
 * @description : 版本更新帮助类
 * @company :途酷科技
 * @date 2019年03月27日15:48
 * @Email: 971613168@qq.com
 */
public class VersionCheckHelper {
    private BaseDownloadObserver mDownloadObserver;
    private SoftReference<BaseActivity> mActivity;
    private boolean mIsLoading = false;

    private VersionCheckHelper(BaseActivity activity) {
        this.mActivity = new SoftReference<>(activity);
    }

    public static VersionCheckHelper with(BaseActivity activity) {
        return new VersionCheckHelper(activity);
    }


    public void checkVersion(UpdateEntity entity) {
        if (entity == null) {
            if (mIsLoading) {
                ToastUtil.show("版本信息有误");
            }
            return;
        }
        if (TextUtils.isEmpty(entity.url) || !entity.url.contains("apk")) {
            if (mIsLoading) {
                ToastUtil.show("不是有效的下载链接:" + entity.url);
            }
            TourcoolLogUtil.e("检测新版本:不是有效的apk下载链接");
            return;
        }
        showAlert(entity);
    }

    /**
     * 提示用户
     *
     * @param entity
     */
    private void showAlert(UpdateEntity entity) {
        Activity activity = mActivity.get();
        if (activity == null || activity.isFinishing()) {
            activity = StackUtil.getInstance().getCurrent();
        }
        if (activity == null || activity.isFinishing()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("发现新版本:V" + entity.versionName)
                .setMessage("msg")
                .setPositiveButton("立即更新", (dialog, which) ->
                        downloadApk(entity, "FastLib_" + entity.versionName + ".apk", true));
        if (!entity.force) {
            builder.setNegativeButton("暂不更新", null);
        }
        builder.create()
                .show();
    }

    /**
     * 下载apk--实际情况需自己调整逻辑避免因range不准造成下载解析不了问题--建议普通应用包下载(20M以内的不使用断点续传)
     *
     * @param entity
     * @param fileName      文件名
     * @param isRangeEnable 是否断点续传
     */
    public void downloadApk(UpdateEntity entity, String fileName, boolean isRangeEnable) {
        Activity activity = mActivity.get();
        if (activity == null || activity.isFinishing()) {
            activity = StackUtil.getInstance().getCurrent();
        }
        if (activity == null || activity.isFinishing()) {
            return;
        }

        ProgressDialog mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setTitle(entity.getTitle());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("测试");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(!entity.force);
        mProgressDialog.setProgressNumberFormat("");
        mProgressDialog.setCanceledOnTouchOutside(!entity.force);

        //暂停下载-慎用;建议使用 Disposable.dispose();
//        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "暂停", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (mDownloadObserver != null) {
//                    mDownloadObserver.pause();
//                }
//            }
//        });

        File mLocal = new File(FileUtil.getCacheDir(), fileName);
        Map<String, Object> header = null;
        long length = mLocal.length();
        if (isRangeEnable) {
            header = new HashMap<>(1);
            header.put("range", "bytes=" + length + "-");
            TourcoolLogUtil.i("downloadApk", "length:" + length);
        }
        //不同url不能使用相同的本地绝对路径不然很可能将B的后半部分下载追加到A的后面--最终也是错误的
        ProgressDialog finalMProgressDialog = mProgressDialog;
        mDownloadObserver = new BaseDownloadObserver(fileName, finalMProgressDialog, isRangeEnable) {
            @Override
            public void onSuccess(File file) {
                FileUtil.installApk(file);
            }

            @Override
            public void onFail(Throwable e) {
                TourcoolLogUtil.e("downloadApk", e.getMessage());
                //HTTP 416 Range Not Satisfiable 出现该错误--很大可能性是文件已下载完成传递的
                boolean satisfiable = e != null && e.getMessage().contains("416") && e.getMessage().toLowerCase().contains("range");
                if (satisfiable) {
                    onSuccess(mLocal);
                    return;
                }
                boolean isPause = e != null && e.getMessage().equals("FAST_DOWNLOAD_PAUSE");
                if (isPause) {
                    ToastUtil.show("暂停下载");
                    return;
                }
                ToastUtil.show("下载失败:" + e.getMessage());
            }

            @Override
            public void onProgress(float progress, long current, long total) {
                TourcoolLogUtil.i("downloadApk", "current:" + current + ";total:" + total);
                if (!finalMProgressDialog.isShowing()) {
                    return;
                }
                finalMProgressDialog.setProgressNumberFormat(FormatUtil.formatDataSize(current) + "/" + FormatUtil.formatDataSize(total));
                finalMProgressDialog.setMax((int) total);
                finalMProgressDialog.setProgress((int) current);
            }
        };
        TourcoolRetrofit.getInstance().downloadFile(entity.url, header)
                .compose(((RxAppCompatActivity) activity).bindUntilEvent(ActivityEvent.DESTROY))
                //可自定义保存路径默认//storage/emulated/0/Android/data/<package-name>/cache/xxx/
                .subscribe(mDownloadObserver);
    }


}
