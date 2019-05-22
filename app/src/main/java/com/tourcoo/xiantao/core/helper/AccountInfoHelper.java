package com.tourcoo.xiantao.core.helper;


import android.text.TextUtils;

import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.entity.address.AddressEntity;
import com.tourcoo.xiantao.entity.user.PersonalCenterInfo;
import com.tourcoo.xiantao.entity.user.UserInfo;

import org.litepal.LitePal;

import java.util.List;

/**
 * @author :zhoujian
 * @description :账户信息帮助类
 * @company :途酷科技
 * @date 2019年 04月 3日 16时52分
 * @Email: 971613168@qq.com
 */
public class AccountInfoHelper {
    private static final String TAG = "AccountInfoHelper";
    private String openId = "";
    private String token = "";
    private AddressEntity defaultAddress;
    private PersonalCenterInfo mPersonalCenterInfo;

    public static final String PREF_TEL_PHONE_KEY = "PREF_TEL_PHONE_KEY";
    public static final String PREF_TEL_QQ_KEY = "PREF_TEL_QQ_KEY";
    public static final String PREF_TEL_WEI_XIN_KEY = "PREF_TEL_WEI_XIN_KEY";

    public static final String PREF_ADDRESS_KEY = "PREF_ADDRESS_KEY";
    /**
     * 充值规则
     */
    public static final String PREF_TEL_RECHARGE_RULE_KEY = "PREF_TEL_RECHARGE_RULE_KEY";
    /**
     * 注册条例
     */
    public static final String PREF_TEL_REGISTER_KEY = "PREF_TEL_REGISTER_KEY";

    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            if (userInfo != null) {
                token = userInfo.getToken();
            }
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private static class SingletonInstance {
        private static final AccountInfoHelper INSTANCE = new AccountInfoHelper();
    }

    public static AccountInfoHelper getInstance() {
        return SingletonInstance.INSTANCE;
    }


    private UserInfo userInfo;


    /**
     * 将用户数据保存到本地
     *
     * @param userInfo
     */
    public void saveUserInfoToSq(UserInfo userInfo) {
        if (userInfo == null) {
            return;
        }
        //先删除旧数据
        LitePal.deleteAll(UserInfo.class);
        userInfo.save();
    }


    public void setUserInfo(UserInfo userInfo) {
        token = userInfo.getToken();
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        if (userInfo == null) {
            userInfo = getUserInfoFromSq();
        }
        return userInfo;
    }


    public UserInfo getUserInfoFromSq() {
        List<UserInfo> userInfoList = LitePal.findAll(UserInfo.class);
        if (userInfoList != null && !userInfoList.isEmpty()) {
            return userInfoList.get(0);
        }
        return null;
    }

    /**
     * 删除用户数据
     */
    public void deleteUserAccount() {
        try {
            LitePal.deleteAll(UserInfo.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG,TAG+"删除异常:"+e.toString() );
        }
        userInfo = null;
        token = "";
    }

    /**
     * 判断用户是否登录
     *
     * @return
     */
    public boolean isLogin() {
        return AccountInfoHelper.getInstance().getUserInfo() != null;
    }


    public void setDefaultAddress(AddressEntity addressEntity) {
        if (addressEntity == null) {
            return;
        }
        defaultAddress = addressEntity;
    }

    public AddressEntity getDefaultAddress() {
        return defaultAddress;
    }

    public String getWholeAddressInfo(AddressEntity defaultAddress) {
        if (defaultAddress == null || defaultAddress.getArea() == null) {
            return "";
        }
        AddressEntity.AreaBean areaBean = defaultAddress.getArea();
        return areaBean.getProvince() + areaBean.getCity() + areaBean.getRegion() + defaultAddress.getDetail();
    }

    public String getPhone(AddressEntity defaultAddress) {
        if (defaultAddress == null || defaultAddress.getArea() == null) {
            return "";
        }
        return defaultAddress.getPhone();
    }

    public String getName(AddressEntity defaultAddress) {
        if (defaultAddress == null || defaultAddress.getArea() == null) {
            return "";
        }
        return defaultAddress.getName();
    }


    public void savePersonalCenter(PersonalCenterInfo personalCenterInfo) {
        if (personalCenterInfo == null) {
            return;
        }
        //先删除旧数据
        LitePal.deleteAll(PersonalCenterInfo.class);
        personalCenterInfo.save();
        mPersonalCenterInfo = personalCenterInfo;
    }


    /**
     * 修改个人信息
     */
    public PersonalCenterInfo getPersonalCenter() {
        return mPersonalCenterInfo;
    }
}