package com.tourcoo.xiantao.ui.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.launcher.ARouter;
import com.luck.picture.lib.tools.Constant;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;

/**
 * @author :JenkinsZhou
 * @description :登录拦截器
 * @company :途酷科技
 * @date 2019年06月05日13:56
 * @Email: 971613168@qq.com
 */
@Interceptor(priority = 1, name = "登录拦截器")
public class LoginInterceptor implements IInterceptor {
    private static final String TAG = "LoginInterceptor";

    /**
     * 比较经典的应用就是在跳转过程中处理登陆事件，这样就不需要在目标页重复做登陆检查
     * 拦截器会在跳转之间执行，多个拦截器会按优先级顺序依次执行
     */
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        // 处理完成，交还控制权
        TourCooLogUtil.i(TAG, "LoginInterceptor 开始执行");
        //给需要跳转的页面添加值为Constant.LOGIN_NEEDED的extra参数，用来标记是否需要用户先登录才可以访问该页面
        // 先判断需不需要
      /*  if (postcard.getExtra() == Constant.LOGIN_NEEDED) {
            boolean isLogin = App.getSharedPreferences().getBoolean(Constant.IS_LOGIN, false);
            TourCooLogUtil.i(TAG, "是否已登录: " + isLogin);
            // 判断用户的登录情况，可以把值保存在sp中
            if (isLogin) {
                callback.onContinue(postcard);
            } else {
                // 没有登录,注意需要传入context
                ARouter.getInstance().build(RouterPath.LOGIN_ACTIIVTY).navigation(mContext);
            }
        } else {
            // 没有extra参数时则继续执行，不做拦截
            callback.onContinue(postcard);
        }*/

    }

    @Override
    public void init(Context context) {
// 拦截器的初始化，会在sdk初始化的时候调用该方法，仅会调用一次
    }
}
