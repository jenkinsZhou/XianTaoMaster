package com.tourcoo.xiantao.ui.mine.recharge;

import android.os.Bundle;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.ui.BaseTourcooTitleActivity;

/**
 * @author :JenkinsZhou
 * @description :充值规则
 * @company :途酷科技
 * @date 2019年03月28日17:03
 * @Email: 971613168@qq.com
 */
public class RechargeRuleActivity extends BaseTourcooTitleActivity {
    @Override
    public int getContentLayout() {
        return R.layout.activity_recharge_rule;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("充值规则");
    }
}
