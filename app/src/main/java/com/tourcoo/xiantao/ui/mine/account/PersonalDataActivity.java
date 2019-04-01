package com.tourcoo.xiantao.ui.mine.account;

import android.os.Bundle;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.ui.BaseTourcooTitleActivity;

/**
 * @author :JenkinsZhou
 * @description :个人资料
 * @company :途酷科技
 * @date 2019年03月26日17:35
 * @Email: 971613168@qq.com
 */
public class PersonalDataActivity extends BaseTourcooTitleActivity {
    @Override
    public int getContentLayout() {
        return R.layout.activity_personal_data;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("个人资料");
    }
}
