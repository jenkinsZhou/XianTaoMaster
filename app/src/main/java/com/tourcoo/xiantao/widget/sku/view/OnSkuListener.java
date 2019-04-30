package com.tourcoo.xiantao.widget.sku.view;

import com.tourcoo.xiantao.entity.spec.SkuAttribute;
import com.tourcoo.xiantao.entity.spec.SpecAttr;
import com.tourcoo.xiantao.entity.spec.SpecList;

import java.util.List;

/**
 * Created by wuhenzhizao on 2017/8/30.
 */
public interface OnSkuListener {
    /**
     * 属性取消选中
     *
     * @param unselectedAttribute
     */
    void onUnselected(SkuAttribute unselectedAttribute);

    /**
     * 属性选中
     *
     * @param selectAttribute
     */
    void onSelect(SkuAttribute selectAttribute);

    /**
     * sku选中
     *
     * @param specList
     */
    void onSkuSelected(SpecList specList);
}