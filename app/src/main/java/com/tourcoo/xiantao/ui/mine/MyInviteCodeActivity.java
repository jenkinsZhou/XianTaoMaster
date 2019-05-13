package com.tourcoo.xiantao.ui.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.advertisement.AdverDetailEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

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
        if (AccountInfoHelper.getInstance().getUserInfo() != null) {
            tvInviteCode.setText(AccountInfoHelper.getInstance().getUserInfo().getMobile());
        }
        tvInviteCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = tvInviteCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    return;
                }
                copyToClipboard(code);
                ToastUtil.showSuccess("已复制到粘贴板");
            }
        });
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("邀请码");
    }

    @Override
    public void loadData() {
        super.loadData();
        requestInvitecode();
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


    private void requestInvitecode() {
        ApiRepository.getInstance().requestInvitecode().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity<String>>() {
                    @Override
                    public void onRequestNext(BaseEntity<String> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                tvInviteCode.setText(entity.data);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        tvInviteCode.setText("");
                    }
                });
    }
}
