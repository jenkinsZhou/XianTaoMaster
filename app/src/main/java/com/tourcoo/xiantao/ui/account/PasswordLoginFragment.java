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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseFragment;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.frame.util.StackUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.user.UserInfo;
import com.tourcoo.xiantao.entity.user.UserInfoBean;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :zhoujian
 * @description :密码登录
 * @company :翼迈科技
 * @date 2019年 04月 20日 14时55分
 * @Email: 971613168@qq.com
 */
public class PasswordLoginFragment extends BaseFragment implements View.OnClickListener {
    private LoginActivity mLoginActivity;
    private EditText etPhoneNumber;

    private EditText etPassword;

    private boolean passwordShowFlag;

    private ImageView ivPass;

    private ImageView ivClearPhone;

    private ImageView ivClearPass;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_login_password;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLoginActivity = (LoginActivity) mContext;
        etPhoneNumber = mContentView.findViewById(R.id.etPhoneNumber);
        etPassword = mContentView.findViewById(R.id.etPassword);
        ivClearPhone = mContentView.findViewById(R.id.ivClearPhone);
        ivClearPass = mContentView.findViewById(R.id.ivClearPass);
        ivPass = mContentView.findViewById(R.id.ivPass);
        mContentView.findViewById(R.id.tvForgetPassword).setOnClickListener(this);
        ivPass.setOnClickListener(this);
        View linePhoneUnFocus = mContentView.findViewById(R.id.linePhoneUnFocus);
        View linePhoneFocus = mContentView.findViewById(R.id.linePhoneFocus);
        View linePassUnFocus = mContentView.findViewById(R.id.linePassUnFocus);
        View linePassFocus = mContentView.findViewById(R.id.linePassFocus);
        listenInputFocus(etPhoneNumber, linePhoneFocus, linePhoneUnFocus);
        listenInputFocus(etPassword, linePassFocus, linePassUnFocus);
        listenInput(etPhoneNumber, ivClearPhone);
        listenInput(etPassword, ivClearPass);

    }


    public static PasswordLoginFragment newInstance() {
        Bundle args = new Bundle();
        PasswordLoginFragment fragment = new PasswordLoginFragment();
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


    private void visiblePassByFlag(boolean show) {
        if (show) {
            showPassword(ivPass, etPassword);
        } else {
            hidePassword(ivPass, etPassword);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivPass:
                if (passwordShowFlag) {
                    passwordShowFlag = false;
                } else {
                    passwordShowFlag = true;
                }
                visiblePassByFlag(passwordShowFlag);
                break;

            case R.id.tvForgetPassword:
                TourCooUtil.startActivity(mContext, ForgetPasswordActivity.class);
                break;

            default:
                break;
        }
    }

    private String getPhone() {
        return etPhoneNumber.getText().toString();
    }

    private String getPassword() {
        return etPassword.getText().toString();
    }

    /**
     * 执行登录
     */
    public void doLoginByPassword() {
        if (TextUtils.isEmpty(getPhone())) {
            ToastUtil.show("请输入手机号");
            return;
        }
       /* if (!TourCooUtil.isMobileNumber(getPhone())) {
            ToastUtil.show("请输入正确的手机号");
            return;
        }*/
        if (TextUtils.isEmpty(getPassword())) {
            ToastUtil.show("请输入密码");
            return;
        }
        loginByPassword(getPhone(), getPassword());
    }


    private void loginByPassword(String account, String password) {
        ApiRepository.getInstance().loginByPassword(account, password).compose(bindUntilEvent(FragmentEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity == null) {
                            ToastUtil.showFailed("服务器异常");
                            return;
                        }
                        if (entity.code == CODE_REQUEST_SUCCESS) {
                            //保存用户信息并跳转至主页面
                            doSaveUserAndSkip(entity);
                        } else {
                            ToastUtil.showFailed(entity.msg);
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
          /*  finishMainTab();
            TourCoolUtil.startActivity(mContext, MainTabActivity.class);*/
            mLoginActivity.startActivity();
//            mContext.finish();

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
