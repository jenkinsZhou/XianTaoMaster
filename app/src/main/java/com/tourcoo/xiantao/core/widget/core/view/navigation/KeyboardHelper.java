package com.tourcoo.xiantao.core.widget.core.view.navigation;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.ref.WeakReference;

/**
 * @author :zhoujian
 * @description : 软键盘帮助类
 * @company :途酷科技
 * @date 2019年02月28日下午 03:03
 * @Email: 971613168@qq.com
 */

public class KeyboardHelper {
    private WeakReference<Activity> mActivity;
    private  WeakReference<View> mContentView;
    private int mKeyMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    /**
     * 显示软键盘的延迟时间
     */
    public static final int KEYBOARD_SHOW_DELAY_TIME = 300;
    /**
     * 判断软键盘开启的阈值
     */
    public final static int KEYBOARD_VISIBLE_THRESHOLD_DP = 100;
    /**
     * 记录contentView的距离底部padding
     */
    private int mPaddingBottom;
    /**
     * 保存软键盘历史开启状态
     */
    private boolean mIsKeyboardOpened = false;
    private OnKeyboardVisibilityChangedListener mOnKeyboardVisibilityChangedListener;
    private boolean mLogEnable;

    public interface OnKeyboardVisibilityChangedListener {

        /**
         * 软键盘开启隐藏监听
         *
         * @param activity         当前Activity
         * @param isOpen           软键盘是否开启
         * @param heightDiff       预留让软件盘上移高度--给contentView设置的paddingBottom
         * @param navigationHeight 当前导航栏高度
         * @return true 监听一次即移除全局监听,false一直监听
         */
        boolean onKeyboardVisibilityChanged(Activity activity, boolean isOpen, int heightDiff, int navigationHeight);
    }

    public static KeyboardHelper with(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity不能为null");
        }
        return new KeyboardHelper(activity);
    }

    private KeyboardHelper(Activity activity) {
        this(activity, ((ViewGroup) activity.getWindow().getDecorView().findViewById(android.R.id.content)).getChildAt(0));
    }

    private KeyboardHelper(Activity activity, View contentView) {
        this(activity, null, contentView);
    }

    private KeyboardHelper(Activity activity, Dialog dialog) {
        this(activity, dialog, dialog.getWindow().findViewById(android.R.id.content));
    }

    private KeyboardHelper(Activity activity, Dialog dialog, View contentView) {
        this.mActivity = new WeakReference<>(activity);
        checkNull();
        register();
        Window window = dialog != null ? dialog.getWindow() : activity.getWindow();
        View mContentView = contentView != null ? contentView : window.getDecorView().findViewById(android.R.id.content);
        mPaddingBottom = mContentView.getPaddingBottom();
        this.mContentView = new WeakReference<>(mContentView);
    }

    private KeyboardHelper(Activity activity, Window window) {
        this.mActivity = new WeakReference<>(activity);
        checkNull();
        register();
        ViewGroup frameLayout = window.getDecorView().findViewById(android.R.id.content);
        View contentView = frameLayout.getChildAt(0) != null ? frameLayout.getChildAt(0) : frameLayout;
        mPaddingBottom = contentView.getPaddingBottom();
        this.mContentView = new WeakReference<>(contentView);
    }

    private void checkNull() {
        if (mActivity == null) {
            throw new IllegalArgumentException("Activity不能为null");
        }
    }

    private void register() {
        Activity activity = mActivity.get();
        if (activity == null) {
            return;
        }
        activity.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                log("onActivityResumed--" + activity.getClass().getSimpleName() + ";KeyboardOpened:" + mIsKeyboardOpened + ";focus:" + activity.getCurrentFocus());
                //部分手机在打开状态下息屏后软键盘自动关闭没有收到相应监听--测试得为华为手机
                if (mIsKeyboardOpened) {
                    View focusView = activity.getCurrentFocus();
                    if (focusView instanceof EditText) {
                        openKeyboard((EditText) focusView);
                    } else {
                        closeKeyboard(activity);
                    }
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activity == null) {
                    return;
                }
                log("onActivityDestroyed--" + activity.getClass().getSimpleName() + ";KeyboardOpened:" + mIsKeyboardOpened +
                        ";isFinishing:" + activity.isFinishing());
                //被系统回收后还可以恢复
                if (!activity.isFinishing()) {
                    return;
                }
                //移除监听
                activity.getApplication().unregisterActivityLifecycleCallbacks(this);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
                } else {
                    activity.getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
                }
            }
        });
    }

    /**
     * 是否打印log
     *
     * @param logEnable
     * @return
     */
    public KeyboardHelper setLogEnable(boolean logEnable) {
        mLogEnable = logEnable;
        return this;
    }

    /**
     * @param listener
     * @return
     */
    public KeyboardHelper setOnKeyboardVisibilityChangedListener(OnKeyboardVisibilityChangedListener listener) {
        this.mOnKeyboardVisibilityChangedListener = listener;
        return this;
    }

    /**
     * 监听layout变化
     */
    public KeyboardHelper setEnable() {
        /*setEnable(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);*/
//        setEnable(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return this;
    }

    /**
     * 设置监听
     *
     * @param mode
     */
    public KeyboardHelper setEnable(int mode) {
        Activity activity = mActivity.get();
        if (activity == null) {
            return this;
        }
        activity.getWindow().setSoftInputMode(mode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时,所要调用的回调函数的接口类
            activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
        return this;
    }

    /**
     * 取消监听
     */
    public KeyboardHelper setDisable() {
        setDisable(mKeyMode);
        return this;
    }

    /**
     * 取消监听
     *
     * @param mode
     */
    public KeyboardHelper setDisable(int mode) {
        Activity activity = mActivity.get();
        if (activity == null) {
            return this;
        }
        activity.getWindow().setSoftInputMode(mode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
        return this;
    }

    /**
     * 设置View变化监听
     */
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        private final Rect r = new Rect();
        private int visibleThreshold = 0;

        @Override
        public void onGlobalLayout() {
            Activity activity = mActivity.get();
            View contentView = mContentView.get();
            if (activity == null||contentView==null) {
                return;
            }
            if (visibleThreshold <= 0) {
                visibleThreshold = Math.round((int) (KEYBOARD_VISIBLE_THRESHOLD_DP * Resources.getSystem().getDisplayMetrics().density + 0.5f));
            }
            //获取当前窗口可视区域大小的
            contentView.getWindowVisibleDisplayFrame(r);
            int heightDiff = activity.getWindow().getDecorView().getRootView().getHeight() - r.bottom;
            boolean isOpen = heightDiff > visibleThreshold;
            heightDiff -= NavigationBarUtil.getFakeNavigationBarHeight(activity);
            heightDiff -= NavigationBarUtil.getRealNavigationBarHeight(activity);
            if (isOpen == mIsKeyboardOpened && !isOpen) {
                return;
            }
            //避免Activity多少初始化造成paddingBottom累加--一般paddingBottom不会超过软键盘高度
            if (isOpen) {
                mPaddingBottom = mPaddingBottom % heightDiff;
            }
            mIsKeyboardOpened = isOpen;
            contentView.setPadding(contentView.getPaddingLeft(), contentView.getPaddingTop(), contentView.getPaddingRight(), mPaddingBottom + heightDiff);
            if (mOnKeyboardVisibilityChangedListener != null) {
                boolean remove = mOnKeyboardVisibilityChangedListener.onKeyboardVisibilityChanged(activity, isOpen, heightDiff, NavigationBarUtil.getNavigationBarHeight(activity));
                if (remove) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        activity.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        activity.getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            }
            log("setting:" + (Settings.System.getString(activity.getContentResolver(), "policy_control")) + ";diff:" + heightDiff + ";paddingBottom:" + mPaddingBottom);
        }
    };

    private void log(String log) {
        if (mLogEnable) {
            Log.i(getClass().getSimpleName(), log);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param view
     */
    public static void closeKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 关闭activity中打开的键盘
     *
     * @param activity
     */
    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        closeKeyboard(view);
    }

    /**
     * 关闭dialog中打开的键盘
     *
     * @param dialog
     */
    public static void closeKeyboard(Dialog dialog) {
        View view = dialog.getWindow().peekDecorView();
        closeKeyboard(view);
    }

    /**
     * 打开键盘
     *
     * @param editText
     * @param delay
     */
    public static void openKeyboard(final EditText editText, long delay) {
        if (editText == null) {
            return;
        }
        delay = delay <= 0 ? KEYBOARD_SHOW_DELAY_TIME : delay;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                editText.requestFocus();
                editText.setSelection(editText.getText().toString().length());
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            }
        }, delay);
    }

    /**
     * 打开键盘
     *
     * @param editText
     */
    public static void openKeyboard(EditText editText) {
        openKeyboard(editText, KEYBOARD_SHOW_DELAY_TIME);
    }

    /**
     * 切换键盘的显示与隐藏
     *
     * @param activity
     */
    public static void toggleKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 处理点击非 EditText 区域时，自动关闭键盘
     *
     * @param isAutoCloseKeyboard 是否自动关闭键盘
     * @param currentFocusView    当前获取焦点的控件
     * @param motionEvent         触摸事件
     * @param dialogOrActivity    Dialog 或 Activity
     */
    public static void handleAutoCloseKeyboard(boolean isAutoCloseKeyboard, View currentFocusView, MotionEvent motionEvent, Object dialogOrActivity) {
        if (isAutoCloseKeyboard && motionEvent.getAction() == MotionEvent.ACTION_DOWN && currentFocusView != null && (currentFocusView instanceof EditText) && dialogOrActivity != null) {
            int[] leftTop = {0, 0};
            currentFocusView.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + currentFocusView.getHeight();
            int right = left + currentFocusView.getWidth();
            if (!(motionEvent.getX() > left && motionEvent.getX() < right && motionEvent.getY() > top && motionEvent.getY() < bottom)) {
                if (dialogOrActivity instanceof Dialog) {
                    closeKeyboard((Dialog) dialogOrActivity);
                } else if (dialogOrActivity instanceof Activity) {
                    closeKeyboard((Activity) dialogOrActivity);
                }
            }
        }
    }

}
