package com.tourcoo.xiantao.ui.account;

import android.app.Activity;
import android.content.ComponentName;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.RxJavaManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.util.StackUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.event.LoginEvent;
import com.tourcoo.xiantao.entity.user.UserInfoBean;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :注册页面
 * @company :途酷科技
 * @date 2019年03月27日20:25
 * @Email: 971613168@qq.com
 */
public class RegisterActivity extends BaseTourCooTitleActivity implements View.OnClickListener {
    private View linePhoneUnFocus;
    private View linePhoneFocus;
    private View lineVCodeUnFocus;
    private View lineVCodeFocus;

    private View linePassUnFocus;
    private View linePassFocus;
    private EditText etPhoneNumber;
    private EditText etVCode;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private EditText etInviteCode;

    private TextView tvSendVerificationCode;

    private ImageView ivConfirmPass;
    private ImageView ivPass;
    private boolean passwordShowFlag = true;
    private boolean confirmPasswordShowFlag = true;

    private CheckBox cBoxAgree;
    private TextView tvLicensing;
    private List<Disposable> disposableList = new ArrayList<>();
    private static final long SECOND = 1000;
    private static final int COUNT = 60;
    private int count = COUNT;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();
    }


    private void init() {
        findViewById(R.id.tvSkipLogin).setOnClickListener(this);
        findViewById(R.id.tvLogin).setOnClickListener(this);
        tvLicensing = findViewById(R.id.tvLicensing);
        tvLicensing.setOnClickListener(this);
        etInviteCode = findViewById(R.id.etInviteCode);
        tvSendVerificationCode = findViewById(R.id.tvSendVerificationCode);
        tvSendVerificationCode.setOnClickListener(this);
        cBoxAgree = findViewById(R.id.cBoxAgree);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        linePhoneUnFocus = findViewById(R.id.linePhoneUnFocus);
        linePhoneFocus = findViewById(R.id.linePhoneFocus);
        lineVCodeUnFocus = findViewById(R.id.lineVCodeUnFocus);
        lineVCodeFocus = findViewById(R.id.lineVCodeFocus);
        linePassFocus = findViewById(R.id.linePassFocus);
        linePassUnFocus = findViewById(R.id.linePassUnFocus);
        etVCode = findViewById(R.id.etVCode);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        tvSendVerificationCode = findViewById(R.id.tvSendVerificationCode);
        ivPass = findViewById(R.id.ivPass);
        ivConfirmPass = findViewById(R.id.ivConfirmPass);
        ivConfirmPass.setOnClickListener(this);
        tvSendVerificationCode.setOnClickListener(this);
        ivPass.setOnClickListener(this);
        visiblePassByFlag(passwordShowFlag);
        visibleConfirmPassByFlag(confirmPasswordShowFlag);
        View linePassConfirmFocus = findViewById(R.id.linePassConfirmFocus);
        View linePassConfirmUnFocus = findViewById(R.id.linePassConfirmUnFocus);
        View lineInviteCodeFocus = findViewById(R.id.lineInviteCodeFocus);
        View lineInviteCodeUnFocus = findViewById(R.id.lineInviteCodeUnFocus);
        listenInputFocus(etPhoneNumber, linePhoneFocus, linePhoneUnFocus);
        listenInputFocus(etVCode, lineVCodeFocus, lineVCodeUnFocus);
        listenInputFocus(etPassword, linePassFocus, linePassUnFocus);
        listenInputFocus(etPassword, linePassFocus, linePassUnFocus);
        listenInputFocus(etPasswordConfirm, linePassConfirmFocus, linePassConfirmUnFocus);
        listenInputFocus(etInviteCode, lineInviteCodeFocus, lineInviteCodeUnFocus);
        ImageView ivClearPhone = findViewById(R.id.ivClearPhone);
        ImageView ivClearPass = findViewById(R.id.ivClearPass);
        ImageView ivClearConfirmPass = findViewById(R.id.ivClearConfirmPass);
        ImageView ivClearInviteCode = findViewById(R.id.ivClearInviteCode);
        listenInput(etPhoneNumber, ivClearPhone);
        listenInput(etPassword, ivClearPass);
        listenInput(etPasswordConfirm, ivClearConfirmPass);
        listenInput(etInviteCode, ivClearInviteCode);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setMinimumHeight(0);
        titleBar.setVisibility(View.GONE);
    }


    private void hideView(View view) {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    private void showView(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }


    private void showPassword(ImageView imageView, EditText editText) {
        imageView.setImageResource(R.mipmap.ic_eye_open);
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void hidePassword(ImageView imageView, EditText editText) {
        imageView.setImageResource(R.mipmap.ic_eye_hide);
        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSendVerificationCode:
                sendVCodeAndCountDownTime(getPhoneNumber());
                break;
            case R.id.ivPass:
                if (passwordShowFlag) {
                    passwordShowFlag = false;
                } else {
                    passwordShowFlag = true;
                }
                visiblePassByFlag(passwordShowFlag);
                break;
            case R.id.ivConfirmPass:
                if (confirmPasswordShowFlag) {
                    confirmPasswordShowFlag = false;
                } else {
                    confirmPasswordShowFlag = true;
                }
                visibleConfirmPassByFlag(confirmPasswordShowFlag);
                break;
            case R.id.tvLogin:
                doRegister();
                break;
            case R.id.tvSkipLogin:
                TourCoolUtil.startActivity(mContext, LoginActivity.class);
                break;
            case R.id.tvLicensing:
                TourCoolUtil.startActivity(mContext, RegistrationOrdinanceActivity.class);
                break;
            default:
                break;
        }
    }


    private void visiblePassByFlag(boolean show) {
        if (show) {
            showPassword(ivPass, etPassword);
        } else {
            hidePassword(ivPass, etPassword);
        }
    }

    private void visibleConfirmPassByFlag(boolean show) {
        if (show) {
            showPassword(ivConfirmPass, etPasswordConfirm);
        } else {
            hidePassword(ivConfirmPass, etPasswordConfirm);
        }
    }


    private void listenInputFocus(EditText editText, View lineFocusView, View lineUnFocusView) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //获得焦点
                    showView(lineFocusView);
                    hideView(lineUnFocusView);
                } else {
                    //失去焦点
                    showView(lineUnFocusView);
                    hideView(lineFocusView);
                }
            }
        });
    }


    private void listenInput(EditText editText, ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    hideView(imageView);
                } else {
                    showView(imageView);
                }
            }
        });
    }

    private String getPhoneNumber() {
        return etPhoneNumber.getText().toString();
    }

    private String getPass() {
        return etPassword.getText().toString();
    }

    private String getPassConfirm() {
        return etPasswordConfirm.getText().toString();
    }

    private boolean isAgree() {
        return cBoxAgree.isChecked();
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
                if (aLong >= COUNT - 1) {
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


    private String getVCode() {
        return etVCode.getText().toString();
    }

    private String getInviteCode() {
        return etInviteCode.getText().toString();
    }

    /**
     * 验证码发送接口并倒计时
     *
     * @param phone
     */
    private void sendVCodeAndCountDownTime(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show("请输入手机号");
            return;
        }
        if (!TourCooUtil.isMobileNumber(phone)) {
            ToastUtil.show("请输入正确的手机号");
            return;
        }
        ApiRepository.getInstance().sendVCode(phone, "register").compose(bindUntilEvent(ActivityEvent.DESTROY)).
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


    private void doRegister() {
        if (!isAgree()) {
            ToastUtil.show("您未同意注册协议");
            return;
        }
        if (TextUtils.isEmpty(getPhoneNumber())) {
            ToastUtil.show("请先输入手机号");
            return;
        }
        if (!TourCooUtil.isMobileNumber(getPhoneNumber())) {
            ToastUtil.show("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(getVCode())) {
            ToastUtil.show("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(getPassword())) {
            ToastUtil.show("请输入密码");
            return;
        }
        if (!getPassword().equals(getPasswordConfirm())) {
            ToastUtil.show("两次输入密码不一致");
            return;
        }
        register();
        //执行登录
    }


    private String getPassword() {
        return etPassword.getText().toString();
    }

    private String getPasswordConfirm() {
        return etPasswordConfirm.getText().toString();
    }


    /**
     * 注册请求
     */
    private void register() {
        ApiRepository.getInstance().register(getPhoneNumber(), getPassword(), getVCode(), getInviteCode()).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                //保存用户信息并跳转至主页面
                                doSaveUserAndSkip(entity);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });

    }

    private UserInfoBean parseUserInfo(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String userInfo = JSONObject.toJSONString(object);
            return JSON.parseObject(userInfo, UserInfoBean.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "value:" + e.toString());
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        cancelTime();
        super.onDestroy();
    }


    private void doSaveUserAndSkip(BaseEntity baseEntity) {
        if (baseEntity == null || baseEntity.data == null) {
            ToastUtil.showFailed("注册失败");
            return;
        }
        UserInfoBean userInfoBean = parseUserInfo(baseEntity.data);
        if (userInfoBean != null && userInfoBean.getUserinfo() != null) {
            //存储本地 保存用户信息并跳转
            AccountInfoHelper.getInstance().saveUserInfoToSq(userInfoBean.getUserinfo());
            AccountInfoHelper.getInstance().setUserInfo(userInfoBean.getUserinfo());
            startActivity();
//            mContext.finish();
//            finishMainTab();
//            TourCoolUtil.startActivity(mContext, MainTabActivity.class);
        } else {
            ToastUtil.showSuccess("注册失败");
        }
    }

    private void finishMainTab() {
        Activity activity = StackUtil.getInstance().getActivity(MainTabActivity.class);
        if (activity != null) {
            activity.finish();
        }
    }


    private void startActivity() {
        if (getIntent().getExtras() != null && getIntent().getExtras().getString("className") != null) {
            String className = getIntent().getExtras().getString("className");
            getIntent().removeExtra("className");
            if (className != null && !className.equals(mContext.getClass().getName())) {
                try {
                    ComponentName componentName = new ComponentName(mContext, Class.forName(className));
                    TourCooLogUtil.i(TAG, TAG + "componentName:" + componentName);
                    startActivity(getIntent().setComponent(componentName));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        EventBus.getDefault().post(new LoginEvent());
        //登录成功 关闭登录页面
        closeLoginActivity();
        finish();
    }

    /**
     * 关闭登录页
     */
    private void closeLoginActivity() {
        Activity activity = StackUtil.getInstance().getActivity(LoginActivity.class);
        if (activity != null) {
            activity.finish();
        }
    }
}
