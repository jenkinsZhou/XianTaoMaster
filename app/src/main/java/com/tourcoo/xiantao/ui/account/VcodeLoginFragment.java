package com.tourcoo.xiantao.ui.account;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.common.CommonConfig;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseFragment;
import com.tourcoo.xiantao.core.frame.manager.RxJavaManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.util.StackUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.user.UserInfoBean;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.tourcoo.xiantao.core.common.CommonConstant.VCODE_TIME;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :zhoujian
 * @description :
 * @company :翼迈科技
 * @date 2019年 04月 20日 14时52分
 * @Email: 971613168@qq.com
 */
public class VcodeLoginFragment extends BaseFragment implements View.OnClickListener {
    private EditText etPhoneNumber;
    private EditText etVCode;
    private List<Disposable> disposableList = new ArrayList<>();
    private static final long SECOND = 1000;
    private static final int COUNT = VCODE_TIME;
    private int count = COUNT;

    private ImageView ivClearPhone;
    private TextView tvSendVerificationCode;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_login_vcode;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        etPhoneNumber = mContentView.findViewById(R.id.etPhoneNumber);
        ivClearPhone = mContentView.findViewById(R.id.ivClearPhone);
        etVCode = mContentView.findViewById(R.id.etVCode);
        tvSendVerificationCode = mContentView.findViewById(R.id.tvSendVerificationCode);
        tvSendVerificationCode.setOnClickListener(this);
        listenInput(etPhoneNumber, ivClearPhone);
        View linePhoneUnFocus = mContentView.findViewById(R.id.linePhoneUnFocus);
        View linePhoneFocus = mContentView.findViewById(R.id.linePhoneFocus);
        View linePassUnFocus = mContentView.findViewById(R.id.linePassUnFocus);
        View linePassFocus = mContentView.findViewById(R.id.linePassFocus);
        listenInputFocus(etPhoneNumber, linePhoneFocus, linePhoneUnFocus);
        listenInputFocus(etVCode, linePassFocus, linePassUnFocus);
    }


    public static VcodeLoginFragment newInstance() {
        Bundle args = new Bundle();
        VcodeLoginFragment fragment = new VcodeLoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void showPassword(ImageView imageView, EditText editText) {
        imageView.setImageResource(R.mipmap.ic_eye_open);
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void hidePassword(ImageView imageView, EditText editText) {
        imageView.setImageResource(R.mipmap.ic_eye_hide);
        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPass:

                break;
            case R.id.tvSendVerificationCode:
                sendVCodeAndCountDownTime(getPhone());
                break;

            default:
                break;
        }
    }

    private String getPhone() {
        return etPhoneNumber.getText().toString();
    }


    /**
     * 执行登录
     */
    public void doLoginByVCode() {
        if (TextUtils.isEmpty(getPhone())) {
            ToastUtil.show("请输入手机号");
            return;
        }
        if (!TourCooUtil.isMobileNumber(getPhone())) {
            ToastUtil.show("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(getVCode())) {
            ToastUtil.show("请输入验证码");
            return;
        }
        loginByVCode(getPhone(), getVCode());
    }


    private void loginByVCode(String account, String vCode) {
        ApiRepository.getInstance().loginByVCode(account, vCode).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity == null) {
                            ToastUtil.showFailed("服务器异常");
                            return;
                        }
                        if (entity.code == CODE_REQUEST_SUCCESS) {
                            doSaveUserAndSkip(entity);
                        } else {
                            ToastUtil.showFailed(entity.msg);
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
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.show("请输入手机号");
            return;
        }
        if (!TourCooUtil.isMobileNumber(phone)) {
            ToastUtil.show("请输入正确的手机号");
            return;
        }
        ApiRepository.getInstance().sendVCode(phone, "mobilelogin").compose(bindUntilEvent(FragmentEvent.DESTROY)).
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


    private void reset() {
        setClickEnable(true);
        count = COUNT;
        setText("发送验证码");
    }

    private void setText(String text) {
        tvSendVerificationCode.setText(text);
    }

    private void setClickEnable(boolean clickEnable) {
        tvSendVerificationCode.setEnabled(clickEnable);
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


    @Override
    public void onDestroy() {
        cancelTime();
        super.onDestroy();
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
            mContext.finish();
            finishMainTab();
            TourCoolUtil.startActivity(mContext, MainTabActivity.class);
        } else {
            ToastUtil.showFailed("注册失败");
        }
    }

    private void finishMainTab() {
        Activity activity = StackUtil.getInstance().getActivity(MainTabActivity.class);
        if (activity != null) {
            activity.finish();
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

}
