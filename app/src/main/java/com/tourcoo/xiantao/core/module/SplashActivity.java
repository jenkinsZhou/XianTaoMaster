package com.tourcoo.xiantao.core.module;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.BarUtils;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.activity.BaseTitleActivity;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.manager.RxJavaManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.NetworkUtil;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.frame.util.StackUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.StatusBarUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.advertisement.AdvertisEntity;
import com.tourcoo.xiantao.entity.user.UserInfo;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.account.LoginActivity;
import com.tourcoo.xiantao.ui.goods.GoodsDetailActivity;
import com.tourcoo.xiantao.ui.msg.AdvDetailActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import androidx.core.content.ContextCompat;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.tourcoo.xiantao.core.common.CommonConfig.PREF_IMAGE_ADVERTISEMENT;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.home.HomeFragment.EXTRA_GOODS_ID;

/**
 * @author :zhoujian
 * @description : 引导页
 * @company :途酷科技
 * @date 2019年03月06日上午 11:43
 * @Email: 971613168@qq.com
 */
public class SplashActivity extends BaseTitleActivity implements View.OnClickListener {
    private String imageUrl;
    private ImageView sp_bg;
    private TextView tvSecond;
    private static final int MSG_TIME = 1;
    private TimeHandler mTimeHandler = new TimeHandler(this);
    private List<Disposable> mDisposableList = new ArrayList<>();
    private int time = 3;
    private AdvertisEntity mAdvertisEntity;
    private LinearLayout layoutSkip;
    private boolean finish = false;
    public static final String EXTRA_ADV_TAG = "EXTRA_ADV_TAG";

    @Override
    public int getContentLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void beforeInitView(Bundle savedInstanceState) {
        TourCooLogUtil.i(TAG, "isTaskRoot:" + isTaskRoot() + ";getCurrent:" + StackUtil.getInstance().getCurrent());
        //防止应用后台后点击桌面图标造成重启的假象---MIUI及Flyme上发现过(原生未发现)
        if (!isTaskRoot()) {
            finish();
            return;
        }
        imageUrl = (String) SharedPreferencesUtil.get(PREF_IMAGE_ADVERTISEMENT, "");
        TourCooLogUtil.i(TAG, TAG + "保存的广告url:" + imageUrl);
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
        BarUtils.setStatusBarVisibility(SplashActivity.this, false);
        sp_bg = findViewById(R.id.sp_bg);
        layoutSkip = findViewById(R.id.layoutSkip);
        layoutSkip.setOnClickListener(this);
        sp_bg.setOnClickListener(this);
        tvSecond = findViewById(R.id.tvSecond);
        if (NetworkUtil.isConnected(mContext)) {
            requestAdvertisement();
        }
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_back);
        if (imageUrl != null && imageUrl.contains("http")) {
            GlideManager.loadImg(imageUrl, sp_bg, R.mipmap.img_start_page);
        }
        //todo
        countDownTime();
        TourCoolUtil.getTintDrawable(drawable, Color.WHITE);
        RxJavaManager.getInstance().setTimer(3000)
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new BaseObserver<Long>() {

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (!finish) {
                            TourCoolUtil.startActivity(mContext, MainTabActivity.class);
                        }
                        finish();
                    }

                    @Override
                    public void onNext(Long entity) {
                        TourCooLogUtil.d(TAG, "延时时间:" + entity);
                    }

                    @Override
                    public void onRequestNext(Long entity) {
                        TourCooLogUtil.i(TAG, "延时时间:" + entity);
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
        cancelTime();
        if (mTimeHandler != null) {
            mTimeHandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }


    private void checkLogin() {
        UserInfo userInfo = AccountInfoHelper.getInstance().getUserInfo();
        boolean isLogin = userInfo != null;
        if (isLogin) {
            AccountInfoHelper.getInstance().setToken(userInfo.getToken());
            TourCoolUtil.startActivity(mContext, MainTabActivity.class);
        } else {
            TourCoolUtil.startActivity(mContext, LoginActivity.class);
        }
        finish();
    }


    /**
     * 获取广告相关信息
     */
    private void requestAdvertisement() {
        ApiRepository.getInstance().requestAdvertisement().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                AdvertisEntity advertisEntity = parseAdvertis(entity.data);
                                if (advertisEntity != null) {
                                    mAdvertisEntity = advertisEntity;
                                    if (advertisEntity.getImage() == null) {
                                        layoutSkip.setVisibility(View.INVISIBLE);
                                        skipToMainTab();
                                    } else {
                                        layoutSkip.setVisibility(View.VISIBLE);
                                    }
                                    String url = TourCooUtil.getUrl(advertisEntity.getImage());
                                    TourCooLogUtil.i(TAG, TAG + "图片url:" + url);
                                    SharedPreferencesUtil.put(PREF_IMAGE_ADVERTISEMENT, url);
                                } else {
                                    layoutSkip.setVisibility(View.INVISIBLE);
                                    skipToMainTab();
                                }
                            } else {
                                layoutSkip.setVisibility(View.INVISIBLE);
                                skipToMainTab();
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        layoutSkip.setVisibility(View.INVISIBLE);
                        skipToMainTab();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sp_bg:
                AccountInfoHelper.getInstance().getUserInfo();
                doSkipByCondition(mAdvertisEntity);
                break;
            case R.id.layoutSkip:
                TourCoolUtil.startActivity(mContext, MainTabActivity.class);
                finish();
                break;
            default:
                break;
        }
    }


    @SuppressWarnings("unchecked")
    private static class TimeHandler extends Handler {
        private WeakReference<SplashActivity> softReference;

        public TimeHandler(SplashActivity activity) {
            softReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_TIME:
//                    int time = softReference.get().cuttentTime--;
//                    softReference.get().showTime(time);
                    break;
                default:
                    break;
            }
        }
    }


    private void showTime(int second) {
        String secondString = second + " 跳过";
        if (tvSecond != null && layoutSkip != null) {
            tvSecond.setText(secondString);
        }
    }


    private void countDownTime() {
        RxJavaManager.getInstance().doEventByInterval(1000, new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposableList.add(d);
            }

            @Override
            public void onNext(Long aLong) {
                showTime(time);
                time--;
                TourCooLogUtil.i(TAG, TAG + "当前aLong:" + aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void cancelTime() {
        if (mDisposableList != null && !mDisposableList.isEmpty()) {
            Disposable disposable;
            for (int i = 0; i < mDisposableList.size(); i++) {
                disposable = mDisposableList.get(i);
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                    mDisposableList.remove(disposable);
                }
            }
        }
    }

    private void doSkipByCondition(AdvertisEntity advertisEntity) {
        if (advertisEntity == null) {
            return;
        }
        switch (advertisEntity.getType()) {
            case 1:
                skipAdvDetail(advertisEntity);
                break;
            case 2:
                skipGoodsDetail(advertisEntity.getGoods_id());
                break;
            default:
                break;
        }
    }


    private void skipAdvDetail(AdvertisEntity advertisEntity) {
        try {
            Intent intent = new Intent();
            if (advertisEntity.getId() == 0) {
                TourCooLogUtil.e(TAG, TAG + ":" + "广告id为0");
                return;
            }
            finish = true;
            intent.putExtra("id", advertisEntity.getId());
            intent.setClass(mContext, AdvDetailActivity.class);
            //跳转广告详情
            startActivity(intent);
            finish();
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, TAG + "异常了:" + e.toString());
        }
    }


    private void skipGoodsDetail(int goodsId) {
        try {
            if (goodsId <= 0) {
                TourCooLogUtil.e(TAG, TAG + ":" + "商品id为0");
                return;
            }
            finish = true;
            Intent intent = new Intent();
            Intent mainIntent = new Intent();
            mainIntent.setClass(mContext, MainTabActivity.class);
            intent.putExtra(EXTRA_GOODS_ID, goodsId);
            //广告的tag
            intent.putExtra(EXTRA_ADV_TAG, "EXTRA_ADV_TAG");
            intent.setClass(mContext, GoodsDetailActivity.class);
            //跳转广告详情
            Intent[] intents = new Intent[]{mainIntent, intent};
            startActivities(intents);
            finish();
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, TAG + ":" + "异常了：" + e.toString());
        }
    }

    private AdvertisEntity parseAdvertis(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String userInfo = JSONObject.toJSONString(object);
            return JSON.parseObject(userInfo, AdvertisEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "value:" + e.toString());
            return null;
        }
    }

    private void skipToMainTab(){
        mTimeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                TourCoolUtil.startActivity(mContext, MainTabActivity.class);
                finish();
            }
        },500);
    }
}
