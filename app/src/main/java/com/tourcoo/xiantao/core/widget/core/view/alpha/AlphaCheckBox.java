package com.tourcoo.xiantao.core.widget.core.view.alpha;

import android.content.Context;
import android.util.AttributeSet;

import com.tourcoo.xiantao.core.widget.core.view.alpha.delegate.AlphaDelegate;


/**
 * @Author: AriesHoo on 2018/7/19 9:52
 * @E-Mail: AriesHoo@126.com
 * Function: 控制Alpha 按下效果
 * Description:
 */

/**
 * @author :zhoujian
 * @description : 控制Alpha 按下效果
 * @company :途酷科技
 * @date 2019年02月28日下午 03:19
 * @email: 971613168@qq.com
 */
public class AlphaCheckBox extends androidx.appcompat.widget.AppCompatRadioButton {
    private AlphaDelegate delegate;

    public AlphaCheckBox(Context context) {
        this(context, null);
    }

    public AlphaCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        delegate = new AlphaDelegate(this);
    }

    public AlphaDelegate getDelegate() {
        return delegate;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        delegate.getAlphaViewHelper().onPressedChanged(this, pressed);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        delegate.getAlphaViewHelper().onEnabledChanged(this, enabled);
    }
}
