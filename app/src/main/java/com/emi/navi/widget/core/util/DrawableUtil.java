package com.emi.navi.widget.core.util;

import android.graphics.drawable.Drawable;

/**
 * @author :zhoujian
 * @description : Drawable工具类
 * @company :翼迈科技
 * @date 2019年02月28日下午 02:30
 * @Email: 971613168@qq.com
 */

public class DrawableUtil {

    /**
     * 设置drawable宽高
     *
     * @param drawable
     * @param width
     * @param height
     */
    public static void setDrawableWidthHeight(Drawable drawable, int width, int height) {
        try {
            if (drawable != null) {
                drawable.setBounds(0, 0,
                        width >= 0 ? width : drawable.getIntrinsicWidth(),
                        height >= 0 ? height : drawable.getIntrinsicHeight());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制当前drawable
     *
     * @param drawable
     * @return
     */
    public static Drawable getNewDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        return drawable.mutate();
    }


}
