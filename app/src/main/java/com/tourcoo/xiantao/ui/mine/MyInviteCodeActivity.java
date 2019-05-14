package com.tourcoo.xiantao.ui.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

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
    private LinearLayout llContentView;
    private StatusLayoutManager mStatusLayoutManager;
    private TextView tvErrorContent;

    @Override
    public int getContentLayout() {
        return R.layout.activity_my_invite_code;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvInviteCode = findViewById(R.id.tvInviteCode);
        llContentView = findViewById(R.id.llContentView);
        setupStatusLayoutManager();
        mStatusLayoutManager.showLoadingLayout();
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
                                mStatusLayoutManager.showErrorLayout();
                                tvErrorContent.setText(TourCooUtil.getNotNullValue(entity.msg));
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        tvInviteCode.setText("");
                        mStatusLayoutManager.showErrorLayout();
                        tvErrorContent.setText(TourCooUtil.getNotNullValue("请求异常"));
                    }
                });
    }


    private void setupStatusLayoutManager() {
        View view = inflateLayout(R.layout.custom_no_invite_code);
        tvErrorContent = view.findViewById(R.id.tvErrorContent);
        mStatusLayoutManager = new StatusLayoutManager.Builder(llContentView)
                // 设置默认布局属性
//                .setDefaultEmptyText("空白了，哈哈哈哈")
//                .setDefaultEmptyImg(R.mipmap.ic_launcher)
//                .setDefaultEmptyClickViewText("retry")
//                .setDefaultEmptyClickViewTextColor(getResources().getColor(R.color.colorAccent))
//                .setDefaultEmptyClickViewVisible(false)
//
//                .setDefaultErrorText(R.string.app_name)
//                .setDefaultErrorImg(R.mipmap.ic_launcher)
//                .setDefaultErrorClickViewText("重试一波")
//                .setDefaultErrorClickViewTextColor(getResources().getColor(R.color.colorPrimaryDark))
//                .setDefaultErrorClickViewVisible(true)
//
//                .setDefaultLayoutsBackgroundColor(Color.WHITE)

                // 自定义布局
                .setLoadingLayout(getLoadingLayout())
//                .setEmptyLayout(inflate(R.layout.layout_empty))
                .setErrorLayout(view)
                .setErrorClickViewID(R.id.tvRefresh)
//                .setLoadingLayout(R.layout.layout_loading)
//                .setEmptyLayout(R.layout.layout_empty)
//                .setErrorLayout(R.layout.layout_error)
//
//                .setEmptyClickViewID(R.id.tv_empty)
//                .setErrorClickViewID(R.id.tv_error)

                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                     /*   if (view.getId() == R.id.tv_customer) {
                            Toast.makeText(MainActivity.this, R.string.request_access, Toast.LENGTH_SHORT).show();
                        } else if (view.getId() == R.id.tv_customer1) {
                            Toast.makeText(MainActivity.this, R.string.switch_account, Toast.LENGTH_SHORT).show();
                        }*/

                    }
                })
                .build();
    }


}
