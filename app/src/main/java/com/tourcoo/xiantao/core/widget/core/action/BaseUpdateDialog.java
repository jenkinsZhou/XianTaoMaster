package com.tourcoo.xiantao.core.widget.core.action;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Allen Liu on 2017/2/23.
 */
public class BaseUpdateDialog extends Dialog {
    private int res;

    public BaseUpdateDialog(Context context, int theme, int res) {
        super(context, theme);
        setContentView(res);
        this.res = res;
        setCanceledOnTouchOutside(false);
    }

}