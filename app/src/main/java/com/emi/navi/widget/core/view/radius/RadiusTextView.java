package com.emi.navi.widget.core.view.radius;

import android.content.Context;
import android.util.AttributeSet;

import com.emi.navi.widget.core.view.radius.delegate.RadiusTextViewDelegate;

/**
 * @author :zhoujian
 * @description : 用于需要圆角矩形框背景的TextView的情况,减少直接使用TextView时引入的shape资源文件
 * @company :翼迈科技
 * @date 2019年02月28日下午 03:09
 * @Email: 971613168@qq.com
 */
public class RadiusTextView extends androidx.appcompat.widget.AppCompatTextView {
    private RadiusTextViewDelegate delegate;

    public RadiusTextView(Context context) {
        this(context, null);
    }

    public RadiusTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadiusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        delegate = new RadiusTextViewDelegate(this, context, attrs);
    }

    /**
     * 获取代理类用于Java代码控制shape属性
     *
     * @return
     */
    public RadiusTextViewDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
                delegate.setRadius((float) getHeight() / 2);
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
}
