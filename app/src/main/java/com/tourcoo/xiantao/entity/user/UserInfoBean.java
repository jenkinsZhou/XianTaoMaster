package com.tourcoo.xiantao.entity.user;

/**
 * @author :zhoujian
 * @description :
 * @company :翼迈科技
 * @date 2019年 04月 20日 17时20分
 * @Email: 971613168@qq.com
 */
public class UserInfoBean  {
    /**
     * userinfo : {"id":1,"username":"admin","nickname":"admin","mobile":"13888888888","avatar":"/assets/img/avatar.png","score":0,"token":"dd6c5f18-2721-4052-bf9c-4a8e686389a1","user_id":1,"createtime":1555751877,"expiretime":1558343877,"expires_in":2592000}
     */

    private UserInfo userinfo;

    public UserInfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserInfo userinfo) {
        this.userinfo = userinfo;
    }


}
