package com.tourcoo.xiantao.core.frame.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.widget.IosLoadingView;


/**
 * @author :CHX
 * 自定义对话框
 */
public class LoadingDialog {
    private IosLoadingView mLoadingView;
    private Dialog mLoadingDialog;
    private TextView loadingText;

    public LoadingDialog(Context context, String msg) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.ios_loading_dialog_view, null);
        // 获取整个布局
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        mLoadingView = view.findViewById(R.id.iosView);
        // 页面中显示文本
        loadingText = view.findViewById(R.id.loading_text);
        // 显示文本
        loadingText.setText(msg);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void show() {
        mLoadingDialog.show();
    }

    public void close() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    public void setMessage(String message) {
        loadingText.setText(message);
        loadingText.invalidate();
    }

    public boolean isShowing() {
        if (mLoadingDialog != null) {
            return mLoadingDialog.isShowing();
        }
        return false;
    }


    public void setCanclelable(boolean cancelNable) {
        if(mLoadingDialog != null){
            mLoadingDialog.setCanceledOnTouchOutside(cancelNable);
        }
    }
}
