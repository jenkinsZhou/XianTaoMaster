package com.tourcoo.xiantao.ui.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.RxJavaManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.user.UserInfo;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;


/**
 * @author :zhoujian
 * @description :修改密码
 * @company :翼迈科技
 * @date 2019年 03月 17日 13时49分
 * @Email: 971613168@qq.com
 */
public class EditPasswordActivity extends BaseTourCooTitleActivity implements View.OnClickListener {
    private TextView tvSendVerificationCode;
    private List<Disposable> disposableList = new ArrayList<>();
    private static final long SECOND = 1000;
    private static final int COUNT = 60;
    private int count = COUNT;
    private EditText etPasswordConfirm;
    private EditText etPasswordNew;
    private EditText etVCode;

    @Override
    public int getContentLayout() {
        return R.layout.activity_edit_password;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvSendVerificationCode = findViewById(R.id.tvSendVerificationCode);
        tvSendVerificationCode.setOnClickListener(this);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        etPasswordNew = findViewById(R.id.etPasswordNew);
        findViewById(R.id.tvEditPassword).setOnClickListener(this);
        etVCode = findViewById(R.id.etVCode);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("修改密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSendVerificationCode:
                UserInfo userInfo = AccountInfoHelper.getInstance().getUserInfo();
                if (userInfo == null || TextUtils.isEmpty(userInfo.getMobile())) {
                    ToastUtil.showFailed("未获取到用户手机号");
                    return;
                }
                sendVCodeAndCountDownTime(userInfo.getMobile());
                break;
            case R.id.tvEditPassword:
                doEditPassword();
                break;
            default:
                break;
        }
    }

    private void cancelTime() {
        if (disposableList != null && !disposableList.isEmpty()) {
            Disposable disposable;
            for (int i = 0; i < disposableList.size(); i++) {
                disposable = disposableList.get(i);
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                    disposableList.remove(disposable);
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        cancelTime();
        super.onDestroy();
    }

    private void setClickEnable(boolean clickEnable) {
        tvSendVerificationCode.setEnabled(clickEnable);
    }

    private void setText(String text) {
        tvSendVerificationCode.setText(text);
    }

    private void reset() {
        setClickEnable(true);
        count = COUNT;
        setText("发送验证码");
    }

    /**
     * 倒计时
     */
    private void countDownTime() {
        reset();
        setClickEnable(false);
        RxJavaManager.getInstance().doEventByInterval(SECOND, new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposableList.add(d);
            }


            @Override
            public void onNext(Long aLong) {
                --count;
                setText("还有" + count + "秒");
                if (aLong >= COUNT - 1 || count < 0) {
                    onComplete();
                }
            }

            @Override
            public void onError(Throwable e) {
                cancelTime();
            }

            @Override
            public void onComplete() {
                reset();
                cancelTime();
            }
        });
    }

    private String getNewPass() {
        return etPasswordNew.getText().toString();
    }

    private String getConfirmPass() {
        return etPasswordConfirm.getText().toString();
    }


    private void doEditPassword() {
        if (TextUtils.isEmpty(getNewPass())) {
            ToastUtil.show("请输入密码");
            return;
        }
        if (TextUtils.isEmpty(getConfirmPass())) {
            ToastUtil.show("请输入确认密码");
            return;
        }
        if (TextUtils.isEmpty(getVCode())) {
            ToastUtil.show("请输入验证码");
            return;
        }
        if (!getNewPass().equals(getConfirmPass())) {
            ToastUtil.show("两次密码输入不一致");
            return;
        }
        UserInfo userInfo = AccountInfoHelper.getInstance().getUserInfo();
        if (userInfo == null || TextUtils.isEmpty(userInfo.getMobile())) {
            ToastUtil.showFailed("未获取到用户手机号");
            return;
        }
        editPassword(userInfo.getMobile(), getNewPass(), getVCode());
    }

    /**
     * 修改密码
     *
     * @param mobile
     * @param pass
     * @param vCode
     */
    private void editPassword(String mobile, String pass, String vCode) {
        ApiRepository.getInstance().restPassword(mobile, pass, vCode).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                ToastUtil.showSuccess("修改成功");
                                finish();
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 验证码发送接口并倒计时
     *
     * @param phone
     */
    private void sendVCodeAndCountDownTime(String phone) {
        ApiRepository.getInstance().sendVCode(phone,"resetpwd").compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity == null) {
                            ToastUtil.showFailed("服务器异常");
                            return;
                        }
                        if (entity.code == CODE_REQUEST_SUCCESS) {
                            ToastUtil.showSuccess(entity.msg);
                            //验证码发送成功开始，倒计时
                            countDownTime();
                        } else {
                            ToastUtil.showFailed(entity.msg);
                        }
                    }
                });
    }


    private String getVCode() {
        return etVCode.getText().toString();
    }


}
