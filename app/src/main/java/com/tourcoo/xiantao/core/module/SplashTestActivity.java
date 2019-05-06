package com.tourcoo.xiantao.core.module;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.util.NetworkUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年05月06日13:40
 * @Email: 971613168@qq.com
 */
public class SplashTestActivity extends RxAppCompatActivity {
    private LinearLayout layoutSkip;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);
        layoutSkip = findViewById(R.id.layoutSkip);
        mHandler = new Handler();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (NetworkUtil.isConnected(SplashTestActivity.this)) {
            ToastUtil.showSuccess("网络已连接");
        }
        layoutSkip.setVisibility(View.INVISIBLE);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(-1), 1000);
    }


   /* private void skipMainTab(){

    }*/

}
