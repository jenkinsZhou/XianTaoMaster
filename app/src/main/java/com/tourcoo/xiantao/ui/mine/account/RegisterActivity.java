package com.tourcoo.xiantao.ui.mine.account;

import android.os.Bundle;
import android.view.View;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.custom.SuperEditText;
import com.tourcoo.xiantao.ui.BaseTourcooTitleActivity;

/**
 * @author :JenkinsZhou
 * @description :注册页面
 * @company :途酷科技
 * @date 2019年03月27日20:25
 * @Email: 971613168@qq.com
 */
public class RegisterActivity extends BaseTourcooTitleActivity {
    private SuperEditText superEtPassword;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        init();
    }


    private void init() {
        superEtPassword = findViewById(R.id.superEtPassword);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setMinimumHeight(0);
        titleBar.setVisibility(View.GONE);
    }
}
