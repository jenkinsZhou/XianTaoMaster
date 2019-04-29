package com.tourcoo.xiantao.entity.pay;

import com.google.gson.annotations.SerializedName;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月09日10:32
 * @Email: 971613168@qq.com
 */
public class WeiXinPay {

    /**
     * appid : wx0617b9768e39eaca
     * partnerid : 1533004401
     * prepayid : wx2610220416307779a05fd72d0341424215
     * timestamp : 1556245324
     * noncestr : o3Hz9Pl8YY8G8fFO
     * package : Sign=WXPay
     * sign : B3603948411F76B17FC7898EECFE3FAD
     */

    private String appid;
    private String partnerid;
    private String prepayid;
    private String timestamp;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
