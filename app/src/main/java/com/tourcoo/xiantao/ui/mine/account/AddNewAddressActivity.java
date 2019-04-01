package com.tourcoo.xiantao.ui.mine.account;

import android.os.Bundle;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.ui.BaseTourcooTitleActivity;

/**
 * @author :JenkinsZhou
 * @description :新增地址
 * @company :途酷科技
 * @date 2019年03月26日16:05
 * @Email: 971613168@qq.com
 */
public class AddNewAddressActivity extends BaseTourcooTitleActivity {
    @Override
    public int getContentLayout() {
        return R.layout.activity_add_new_address;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("新增地址");
    }
}
