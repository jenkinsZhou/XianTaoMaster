package com.emi.navi.widget.core.view.radius;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Switch;

import com.emi.navi.widget.core.view.radius.delegate.RadiusSwitchDelegate;


/**
 * @author :zhoujian
 * @description : 自定义switch--仅支持API16以上
 * @company :翼迈科技
 * @date 2019年02月28日下午 03:05
 * @email: 971613168@qq.com
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class RadiusSwitch extends Switch {
    private RadiusSwitchDelegate delegate;

    public RadiusSwitch(Context context) {
        this(context, null);
    }

    public RadiusSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        delegate = new RadiusSwitchDelegate(this, context, attrs);
    }

    /**
     * 获取代理类用于Java代码控制shape属性
     *
     * @return
     */
    public RadiusSwitchDelegate getDelegate() {
        return delegate;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (delegate != null && delegate.getWidthHeightEqualEnable() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (delegate != null) {
            if (delegate.getRadiusHalfHeightEnable()) {
                delegate.setRadius(getHeight() / 2);
            }
            delegate.init();
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (delegate != null) {
            delegate.setSelected(selected);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (delegate != null) {
            delegate.init();
        }
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (delegate != null) {
            delegate.init();
        }
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (delegate != null) {
            delegate.init();
        }
    }
}
