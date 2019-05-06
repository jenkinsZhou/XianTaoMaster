package com.tourcoo.xiantao.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.SystemSettingEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.trello.rxlifecycle3.android.ActivityEvent;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;


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
        tvAppVersion = findViewById(R.id.tvAppVersion);
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
          /*  case R.id.stvAboutUs:
                CheckVersionHelper.with(this).checkVersion(true);
                break;
            case R.id.stvPhoneNumber:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show("未获取到热线号码");
                    return;
                }
                call(phone);
                break;*/
            case R.id.stvPhoneNumber:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.show("未获取到客服号码");
                    return;
                }
                call(phone);
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
}
