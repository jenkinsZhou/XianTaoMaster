package com.tourcoo.xiantao.core.module;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.activity.BaseTitleActivity;
import com.tourcoo.xiantao.core.frame.manager.RxJavaManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.StackUtil;
import com.tourcoo.xiantao.core.log.TourcoolLogUtil;
import com.tourcoo.xiantao.core.util.TourcoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.StatusBarUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.trello.rxlifecycle3.android.ActivityEvent;

import androidx.core.content.ContextCompat;
import io.reactivex.disposables.Disposable;

/**
 * @author :zhoujian
 * @description : zj
 * @company :途酷科技
 * @date 2019年03月06日上午 11:43
 * @Email: 971613168@qq.com
 */
public class SplashActivity extends BaseTitleActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        TourcoolLogUtil.i(TAG, "isTaskRoot:" + isTaskRoot() + ";getCurrent:" + StackUtil.getInstance().getCurrent());
        //防止应用后台后点击桌面图标造成重启的假象---MIUI及Flyme上发现过(原生未发现)
        if (!isTaskRoot()) {
            finish();
            return;
        }
        super.beforeSetContentView();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (!isTaskRoot()) {
            return;
        }
        if (!StatusBarUtil.isSupportStatusBarFontChange()) {
            //隐藏状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_back);
        TourcoolUtil.getTintDrawable(drawable, Color.WHITE);
        RxJavaManager.getInstance().setTimer(500)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<Long>() {

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        TourcoolUtil.startActivity(mContext, MainTabActivity.class);
                        finish();
                    }

                    @Override
                    public void onNext(Long entity) {
                        TourcoolLogUtil.d(TAG, "延时时间:" + entity);
                    }

                    @Override
                    public void onRequestNext(Long entity) {
                        TourcoolLogUtil.i(TAG, "延时时间:" + entity);
                    }
                });
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setStatusBarLightMode(false)
                .setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
