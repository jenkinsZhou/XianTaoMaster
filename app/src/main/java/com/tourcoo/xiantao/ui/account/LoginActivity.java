package com.tourcoo.xiantao.ui.account;

import android.content.ComponentName;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.custom.WrapContentHeightViewPager;
import com.tourcoo.xiantao.entity.event.LoginEvent;
import com.tourcoo.xiantao.entity.event.RefreshEvent;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.greenrobot.eventbus.EventBus;

/**
 * @author :zhoujian
 * @description :登录页面
 * @company :翼迈科技
 * @date 2019年 04月 20日 10时47分
 * @Email: 971613168@qq.com
 */
public class LoginActivity extends BaseTourCooTitleActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private WrapContentHeightViewPager wrapViewPager;
    private TextView tvVCodeLogin;
    private TextView tvPasswordLogin;
    private PasswordLoginFragment passwordLoginFragment;
    private VcodeLoginFragment vcodeLoginFragment;

    private int mCurrentPosition;

    @Override
    public int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mContentView.findViewById(R.id.tvSkipRegister).setOnClickListener(this);
        wrapViewPager = mContentView.findViewById(R.id.wrapViewPager);
        tvVCodeLogin = mContentView.findViewById(R.id.tvVCodeLogin);
        tvPasswordLogin = mContentView.findViewById(R.id.tvPasswordLogin);
        mContentView.findViewById(R.id.tvLogin).setOnClickListener(this);
        tvVCodeLogin.setOnClickListener(this);
        tvPasswordLogin.setOnClickListener(this);
        List<Fragment> fragmentList = new ArrayList<>();
        passwordLoginFragment = PasswordLoginFragment.newInstance();
        vcodeLoginFragment = VcodeLoginFragment.newInstance();
        fragmentList.add(passwordLoginFragment);
        fragmentList.add(vcodeLoginFragment);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fragmentList);
        wrapViewPager.addOnPageChangeListener(this);
        wrapViewPager.setAdapter(pagerAdapter);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            //密码登录
            case 0:
                showSelect(tvPasswordLogin);
                showUnSelect(tvVCodeLogin);
                break;
            //验证码登录
            case 1:
                showSelect(tvVCodeLogin);
                showUnSelect(tvPasswordLogin);
                break;
            default:
                break;
        }
        mCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPasswordLogin:
                wrapViewPager.setCurrentItem(0);
                mCurrentPosition = 0;
                break;
            case R.id.tvVCodeLogin:
                wrapViewPager.setCurrentItem(1);
                mCurrentPosition = 1;
                break;
            case R.id.tvLogin:
                doLogin(mCurrentPosition);
                break;
            case R.id.tvSkipRegister:
                TourCoolUtil.startActivity(mContext, RegisterActivity.class);
                break;
            default:
                break;
        }
    }


    class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


    private void showSelect(TextView textView) {
        textView.setTextColor(TourCoolUtil.getColor(R.color.blackLittle));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
    }

    private void showUnSelect(TextView textView) {
        textView.setTextColor(TourCoolUtil.getColor(R.color.text_gray_common));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
    }


    /**
     * 根据tab位置决定执行哪种方式登录
     *
     * @param position
     */
    private void doLogin(int position) {
        switch (position) {
            case 0:
                //密码登录
                passwordLoginFragment.doLoginByPassword();
                break;
            case 1:
                //验证码登录
                vcodeLoginFragment.doLoginByVCode();
                break;
            default:
                break;
        }

    }


    public void startActivity() {
        if (getIntent().getExtras() != null && getIntent().getExtras().getString("className") != null) {
            String className = getIntent().getExtras().getString("className");
            getIntent().removeExtra("className");
            if (className != null && !className.equals(mContext.getClass().getName())) {
                try {
                    ComponentName componentName = new ComponentName(mContext, Class.forName(className));
                    TourCooLogUtil.i(TAG, TAG + "componentName:" + componentName);
                    startActivity(getIntent().setComponent(componentName));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        EventBus.getDefault().post(new LoginEvent());
        finish();
    }


}
