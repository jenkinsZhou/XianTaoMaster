package com.tourcoo.xiantao.ui.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.base.fragment.BaseTitleFragment;
import com.tourcoo.xiantao.core.helper.ImagePickerHelper;
import com.tourcoo.xiantao.core.log.TourcoolLogUtil;
import com.tourcoo.xiantao.core.util.TourcoolUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.ui.mine.account.AccountBalanceActivity;
import com.tourcoo.xiantao.ui.mine.account.RegisterActivity;
import com.tourcoo.xiantao.ui.mine.recharge.RechargeDetailActivity;

/**
 * @author :zhoujian
 * @description :故障报修Fragment
 * @company :翼迈科技
 * @date 2019年 03月 15日 20时55分
 * @Email: 971613168@qq.com
 */
public class FaultRepairFragment extends BaseTitleFragment implements View.OnClickListener {
    private ImagePickerHelper mImagePickerHelper;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_fault_repair;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mContentView.findViewById(R.id.ivCamera).setOnClickListener(this);
        mImagePickerHelper = new ImagePickerHelper(mContext);
        mContentView.findViewById(R.id.btnRepair).setOnClickListener(this);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        titleBar.setTitleMainText("故障报修");
    }

    private void selectPhoto() {
        mImagePickerHelper.selectFile(1001, 5, (requestCode, list) -> {
            if (list == null || list.size() == 0 || requestCode != 1001) {
                return;
            }
            TourcoolLogUtil.d(TAG, "选择的数据：" + list);
            ToastUtil.show("选择了");
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRepair:
//                TourcoolUtil.startActivity(mContext, RechargeDetailActivity.class);
//                TourcoolUtil.startActivity(mContext, RegisterActivity.class);
                TourcoolUtil.startActivity(mContext, AccountBalanceActivity.class);
                break;
            default:
                break;
        }
    }

    public static FaultRepairFragment newInstance() {
        Bundle args = new Bundle();
        FaultRepairFragment fragment = new FaultRepairFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mImagePickerHelper != null) {
            mImagePickerHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

}
