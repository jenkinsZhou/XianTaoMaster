package com.emi.navi.widget.core.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.util.TypedValue;

/**
 * @author :zhoujian
 * @description : 资源文件帮助类
 * @company :翼迈科技
 * @date 2019年02月28日下午 02:22
 * @Email: 971613168@qq.com
 */

public class ResourceUtil {
    private Context mContext;

    public ResourceUtil(Context context) {
        this.mContext = context;
    }

    public CharSequence getText(int res) {
        CharSequence txt = null;
        try {
            txt = mContext.getText(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return txt;
    }

    public CharSequence[] getTextArray(int res) {
        CharSequence[] result = new CharSequence[0];
        try {
            result = mContext.getResources().getTextArray(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Drawable getDrawable(int res) {
        Drawable drawable = null;
        try {
            drawable = mContext.getResources().getDrawable(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }

    public int getColor(int res) {
        int result = 0;
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                result = ContextCompat.getColor(mContext, res);
            } else {
                result = mContext.getResources().getColor(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public ColorStateList getColorStateList(int res) {
        ColorStateList color = null;
        try {
            color = mContext.getResources().getColorStateList(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    public float getDimension(int res) {
        float result = 0;
        try {
            result = mContext.getResources().getDimension(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getDimensionPixelSize(int res) {
        int result = 0;
        try {
            result = mContext.getResources().getDimensionPixelSize(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String[] getStringArray(int res) {
        String[] result = new String[0];
        try {
            result = mContext.getResources().getStringArray(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getAttrColor(int attrRes) {
        int result = 0;
        try {
            TypedValue typedValue = new TypedValue();
            mContext.getTheme().resolveAttribute(attrRes, typedValue, true);
            result = typedValue.data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public float getAttrFloat(int attrRes) {
        return getAttrFloat(attrRes, 1.0f);
    }

    public float getAttrFloat(int attrRes, float def) {
        float result = def;
        try {
            TypedValue typedValue = new TypedValue();
            mContext.getTheme().resolveAttribute(attrRes, typedValue, true);
            result = typedValue.getFloat();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == 0 ? def : result;
    }
}
