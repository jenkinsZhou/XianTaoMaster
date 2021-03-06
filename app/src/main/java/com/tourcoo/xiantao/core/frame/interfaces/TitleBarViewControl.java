package com.tourcoo.xiantao.core.frame.interfaces;

import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;

/**
 * @author :zhoujian
 * @description :TitleBarView控制
 * @company :途酷科技
 * @date 2019年 03月 02日 20时59分
 * @Email: 971613168@qq.com
 */
public interface TitleBarViewControl {

    /**
     * 全局设置TitleBarView 属性回调
     *
     * @param titleBar
     * @param cls 包含TitleBarView的类
     * @return
     */
    boolean createTitleBarViewControl(TitleBarView titleBar, Class<?> cls);
}
