package com.tourcoo.xiantao.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.action.BaseUpdateDialog;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.SystemSettingEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.trello.rxlifecycle3.android.ActivityEvent;

import static com.tourcoo.xiantao.core.common.CommonConstant.companyInfo;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_ADDRESS_KEY;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_TEL_PHONE_KEY;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_TEL_REGISTER_KEY;


/**
 * @author :zhoujian
 * @description :关于我们
 * @company :翼迈科技
 * @date 2019年 03月 17日 10时15分
 * @Email: 971613168@qq.com
 */
public class AboutUsActivity extends BaseTourCooTitleActivity implements View.OnClickListener {
    private String phone;
    private SuperTextView stvPhoneNumber;
    public static final String PREF_TEL_PHONE_KEY = "PREF_TEL_PHONE_KEY";
    private TextView tvAppVersion;

    @Override
    public int getContentLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//        findViewById(R.id.stvAppVersion).setOnClickListener(this);
//        findViewById(R.id.stvPhoneNumber).setOnClickListener(this);
        stvPhoneNumber = findViewById(R.id.stvPhoneNumber);
        findViewById(R.id.stvCheckVersion).setOnClickListener(this);
        tvAppVersion = findViewById(R.id.tvAppVersion);
        findViewById(R.id.stvCheckVersion).setOnClickListener(this);
        stvPhoneNumber.setOnClickListener(this);
        phone = (String) SharedPreferencesUtil.get(PREF_TEL_PHONE_KEY, "");
    }

    @Override
    public void loadData() {
        showPhone(phone);
        showAppVersion();
        requestSystemConfig();
        super.loadData();
    }

    private void showPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return;
        }
        stvPhoneNumber.setRightString(phone);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("关于我们");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stvPhoneNumber:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show("未获取到客服号码");
                    return;
                }
                call(phone);
                break;
            case R.id.stvCheckVersion:
                checkUpdate();
                break;
            default:
                break;
        }
    }


    /**
     * 调用拨号功能
     *
     * @param phone 电话号码
     */
    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    /**
     * 获取系统相关信息
     */
    private void requestSystemConfig() {
        ApiRepository.getInstance().requestSystemConfig().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<SystemSettingEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<SystemSettingEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                SystemSettingEntity settingEntity = entity.data;
                                if (settingEntity != null) {
                                    phone = settingEntity.getKefu();
                                    showPhone(phone);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }

    private void showAppVersion() {
        String version = "V" + TourCooUtil.getVersionName(mContext);
        tvAppVersion.setText(version);
    }


    private boolean needUpdate(int versionCode) {
        TourCooLogUtil.i(TAG, TAG + "后台的版本号:" + versionCode);
        int localVersionCode = TourCooUtil.getVersionCode(mContext);
        TourCooLogUtil.i(TAG, TAG + "本地的版本号:" + localVersionCode);
        return localVersionCode < versionCode;
    }

    private boolean isForce(int code) {
        return code == 1;
    }

    public void updateVersion(Context context, String downloadUrl, String content, boolean isForce) {
        try {
            DownloadBuilder builder = AllenVersionChecker.getInstance()
                    .downloadOnly(
                            UIData.create().setDownloadUrl(downloadUrl).setContent(content)
                    );

            if (isForce) {
//        强制更新 取消回调
                builder.setForceUpdateListener(new ForceUpdateListener() {
                    @Override
                    public void onShouldForceUpdate() {
                        ActivityUtils.finishAllActivities();
                    }
                });
            }
            //静默下载
            builder.setSilentDownload(false);
            //如果本地有安装包缓存也会重新下载apk
            builder.setForceRedownload(true);
            //更新界面选择
            builder.setCustomVersionDialogListener(createCustomDialogOne());
            //自定义下载路径
            builder.setDownloadAPKPath(Environment.getExternalStorageDirectory() + "/XianTao/download/");
            builder.executeMission(context);
        } catch (Exception e) {
            ToastUtil.showFailed("下载地址有误");
            TourCooLogUtil.e(TAG, TAG + "更新异常:" + e.toString());
        }

    }

    /**
     * 务必用库传回来的context 实例化你的dialog
     * 自定义的dialog UI参数展示，使用versionBundle
     *
     * @return
     */
    private CustomVersionDialogListener createCustomDialogOne() {
        CustomVersionDialogListener listener = new CustomVersionDialogListener() {
            @Override
            public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                BaseUpdateDialog baseDialog = new BaseUpdateDialog(context, R.style.UpdateDialog, R.layout.custom_dialog_one_layout);
                TextView textView = baseDialog.findViewById(R.id.tv_msg);
                textView.setText(versionBundle.getContent());
                return baseDialog;
            }
        };
        return listener;
    }


    /**
     * 获取系统相关信息
     */
    private void checkUpdate() {
        ApiRepository.getInstance().requestSystemConfig().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity<SystemSettingEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<SystemSettingEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                SystemSettingEntity settingEntity = entity.data;
                                if (settingEntity != null) {
                                    phone = settingEntity.getKefu();
                                    showPhone(phone);
                                    boolean needUpdate;
                                    if (settingEntity.getAndroid_version_code() == 0 || TextUtils.isEmpty(settingEntity.getAndroid_version())) {
                                        ToastUtil.show("当前已是最新版本");
                                        return;
                                    }
                                    needUpdate = needUpdate(settingEntity.getAndroid_version_code());
                                    boolean isForce = isForce(settingEntity.getAndroid_update());
                                    if (needUpdate) {
                                        updateVersion(mContext, settingEntity.getAndroid_download(), settingEntity.getAndroid_info(), isForce);
                                    } else {
                                        ToastUtil.show("当前已是最新版本");
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


}
