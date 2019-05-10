package com.tourcoo.xiantao.ui.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;

/**
 * @author :JenkinsZhou
 * @description :我的邀请码
 * @company :途酷科技
 * @date 2019年05月10日10:22
 * @Email: 971613168@qq.com
 */
public class MyInviteCodeActivity extends BaseTourCooTitleActivity {
    private TextView tvInviteCode;
    @Override
    public int getContentLayout() {
        return R.layout.activity_my_invite_code;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvInviteCode = findViewById(R.id.tvInviteCode);
        if(AccountInfoHelper.getInstance().getUserInfo() != null){
            tvInviteCode.setText(AccountInfoHelper.getInstance().getUserInfo().getMobile());
        }
        tvInviteCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = AccountInfoHelper.getInstance().getUserInfo().getMobile();
                if(TextUtils.isEmpty(phone)){
                    return;
                }
                copyToClipboard(phone);
                ToastUtil.showSuccess("已复制到粘贴板");
            }
        });
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("邀请码");
    }




    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    private boolean copyToClipboard(String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
