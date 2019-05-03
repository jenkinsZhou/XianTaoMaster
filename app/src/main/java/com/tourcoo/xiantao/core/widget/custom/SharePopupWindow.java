package com.tourcoo.xiantao.core.widget.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tourcoo.xiantao.R;

/**
 * popupWindow
 */
public class SharePopupWindow extends PopupWindow {
    private int popupWidth;
    private int popupHeight;
    private View parentView;
    private Context mContext;

    private TextView btnShareWX;
    private TextView btnShareWXFriend;
    private TextView btnCancel;


    public SharePopupWindow(Context context) {
        this(context,false);
    }

    public SharePopupWindow(Context context,boolean isHideWXFriend) {
        super(context);
        this.mContext = context;
        initView(context,isHideWXFriend);
        setPopConfig();
    }

    /**
     *   初始化控件
     * @param context
     */
    private void initView(Context context,boolean isHideWXFriend) {
        parentView = View.inflate(context, R.layout.layout_share_popup_window, null);
        setContentView(parentView);
        btnShareWX = parentView.findViewById(R.id.btnShareWX);
        btnShareWXFriend = parentView.findViewById(R.id.btnShareWXFriend);
        btnCancel = parentView.findViewById(R.id.btnCancel);

        if(isHideWXFriend){
            btnShareWXFriend.setVisibility(View.GONE);
        }

        btnShareWX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onWxClick();
                }
            }
        });

        btnShareWXFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onWxFriendClick();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     *
     * 配置弹出框属性
     * @updateInfo (此处输入修改内容,若无修改可不写.)
     *
     */
    private void setPopConfig() {
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setOutsideTouchable(true);// 设置外部触摸会关闭窗口
        // 设置动画
        this.setAnimationStyle(R.style.CommonBottomDialogAnim);


        //获取自身的长宽高
        parentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupHeight = parentView.getMeasuredHeight();
        popupWidth = parentView.getMeasuredWidth();
    }



    /**
     * 显示在屏幕的下方
     */
    public void showAtScreenBottom(View parent){
        this.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        popOutShadow();
    }


    /**
     * 让popupwindow以外区域阴影显示
     */
    private void popOutShadow() {
        final Window window = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;//设置阴影透明度
        window.setAttributes(lp);
        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
    }


    private ISharePopupWindowClickListener listener;

    public void setISharePopupWindowClickListener(ISharePopupWindowClickListener listener){
        this.listener = listener;
    }

    public interface ISharePopupWindowClickListener{
        void onWxClick();
        void onWxFriendClick();
    }

}