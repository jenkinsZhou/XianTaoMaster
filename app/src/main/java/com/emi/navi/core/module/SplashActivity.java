package com.emi.navi.core.module;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.emi.navi.R;
import com.emi.navi.core.frame.base.activity.BaseTitleActivity;
import com.emi.navi.core.frame.manager.RxJavaManager;
import com.emi.navi.core.frame.util.StackUtil;
import com.emi.navi.core.log.NaViLogUtil;
import com.emi.navi.core.util.NaViUtil;
import com.emi.navi.widget.core.util.StatusBarUtil;
import com.emi.navi.widget.core.view.titlebar.TitleBarView;

import androidx.core.content.ContextCompat;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author :zhoujian
 * @description : zj
 * @company :翼迈科技
 * @date 2019年03月06日上午 11:43
 * @Email: 971613168@qq.com
 */
public class SplashActivity extends BaseTitleActivity {
    private ImageView ivBg;
    private Disposable mDisposable;
    private String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551858906703&di=1ca8f8f6a8b88467bd4051128bf76ee2&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fa9716e409a267f1923ded98bb7642a42b80bd7961b5438-kdBlwv_fw658";

    @Override
    public int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        NaViLogUtil.i(TAG, "isTaskRoot:" + isTaskRoot() + ";getCurrent:" + StackUtil.getInstance().getCurrent());
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
        ivBg = findViewById(R.id.sp_bg);
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_back);
        NaViUtil.getTintDrawable(drawable, Color.WHITE);
//        GlideManager.loadImg(url, ivBg);
        RxJavaManager.getInstance().doEventByInterval(1000, new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Long aLong) {
                NaViLogUtil.i(TAG, "延时时间:" + aLong);
                if (aLong >= 2) {
                    onComplete();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                if (mDisposable != null) {
                    mDisposable.dispose();
                }
                NaViUtil.startActivity(mContext, MainTabActivity.class);
                finish();
            }
        });
      /*  RxJavaManager.getInstance().setTimer(3000)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<Long>() {

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        NaViUtil.startActivity(mContext, MainTabActivity.class);
                        finish();
                    }

                    @Override
                    public void onNext(Long entity) {
                        NaViLogUtil.d(TAG, "延时时间:" + entity);
                    }

                    @Override
                    public void onRequestNext(Long entity) {
                        NaViLogUtil.i(TAG, "延时时间:" + entity);
                    }
                });*/
    }


    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setStatusBarLightMode(false)
                .setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }
}
