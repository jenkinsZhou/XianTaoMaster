package com.tourcoo.xiantao.widget.sku.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.entity.spec.SkuAttribute;
import com.tourcoo.xiantao.entity.spec.SpecAttr;
import com.tourcoo.xiantao.entity.spec.SpecData;
import com.tourcoo.xiantao.entity.spec.SpecList;
import com.tourcoo.xiantao.widget.sku.utils.ViewUtils;
import com.tourcoo.xiantao.widget.sku.widget.SkuMaxHeightScrollView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wuhenzhizao on 2017/7/31.
 */
public class SkuSelectScrollView extends SkuMaxHeightScrollView implements SkuItemLayout.OnSkuItemSelectListener {
    private LinearLayout skuContainerLayout;
    private SpecData specData;
    private List<SkuAttribute> selectedAttributeList;  // 存放当前属性选中信息
    private OnSkuListener listener;                    // sku选中状态回调接口

    public SkuSelectScrollView(Context context) {
        super(context);
        init(context, null);
    }

    public SkuSelectScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setFillViewport(true);
        setOverScrollMode(OVER_SCROLL_NEVER);
        skuContainerLayout = new LinearLayout(context, attrs);
        skuContainerLayout.setId(ViewUtils.generateViewId());
        skuContainerLayout.setOrientation(LinearLayout.VERTICAL);
        skuContainerLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(skuContainerLayout);
    }

    /**
     * 设置SkuView委托，MVVM + Databinding模式下使用
     *
     * @param delegate
     */
    public void setSkuViewDelegate(SkuViewDelegate delegate) {
        this.listener = delegate.getListener();
    }

    /**
     * 设置监听接口
     *
     * @param listener {@link OnSkuListener}
     */
    public void setListener(OnSkuListener listener) {
        this.listener = listener;
    }

    /**
     * 绑定sku数据
     *
     * @param specData
     */
    public void setSkuData(SpecData specData) {
        this.specData = specData;
        // 清空sku视图
        skuContainerLayout.removeAllViews();

        // 获取分组的sku集合
        Map<String, List<SkuAttribute>> dataMap = getSkuGroupByName(specData.getSpec_attr());
        selectedAttributeList = new LinkedList<>();
        int index = 0;
        for (Iterator<Map.Entry<String, List<SkuAttribute>>> it = dataMap.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<String, List<SkuAttribute>> entry = it.next();

            // 构建sku视图
            SkuItemLayout itemLayout = new SkuItemLayout(getContext());
            itemLayout.setId(ViewUtils.generateViewId());
            itemLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            itemLayout.buildItemLayout(index++, entry.getKey(), entry.getValue());
            itemLayout.setListener(this);
            skuContainerLayout.addView(itemLayout);
            // 初始状态下，所有属性信息设置为空
            selectedAttributeList.add(new SkuAttribute(entry.getKey(), ""));
        }

        // 一个sku时，默认选中
        if (this.specData.getSpec_attr().size() == 1) {
            selectedAttributeList.clear();
            for (SkuAttribute attribute : this.specData.getSpec_attr().get(0).getSpec_items()) {
                selectedAttributeList.add(new SkuAttribute(attribute.getItem_id(), attribute.getSpec_value()));
            }
        }
        // 清除所有选中状态
        clearAllLayoutStatus();
        // 设置是否可点击
        optionLayoutEnableStatus();
        // 设置选中状态
        optionLayoutSelectStatus();
    }


    /**
     * 将sku根据属性名进行分组
     *
     * @param list
     * @return 如{ "颜色": {"id":1; "value":"白色"}}
     */
    private Map<String, List<SkuAttribute>> getSkuGroupByName(List<SpecAttr> list) {
        Map<String, List<SkuAttribute>> dataMap = new LinkedHashMap<>();
        for (int i = 0; i < list.size(); i++) {
            SpecAttr attribute = list.get(i);
            String attributeName = attribute.getGroup_name();
            dataMap.put(attributeName, attribute.getSpec_items());
        }
        return dataMap;
    }


    /**
     * 重置所有属性的选中状态
     */
    private void clearAllLayoutStatus() {
        for (int i = 0; i < skuContainerLayout.getChildCount(); i++) {
            SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(i);
            itemLayout.clearItemViewStatus();
        }
    }

    /**
     * 设置所有属性的Enable状态，即是否可点击
     */
    private void optionLayoutEnableStatus() {
        int childCount = skuContainerLayout.getChildCount();
        if (childCount <= 1) {
            optionLayoutEnableStatusSingleProperty();
        } else {
            optionLayoutEnableStatusMultipleProperties();
        }
    }

    private void optionLayoutEnableStatusSingleProperty() {
        SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(0);
        SpecAttr specAttr = specData.getSpec_attr().get(0);
        // 遍历sku列表
        for (int i = 0; i < specAttr.getSpec_items().size(); i++) {
            SkuAttribute attribute = specAttr.getSpec_items().get(i);

            for (SpecList specList : specData.getSpec_list()) {
                if (specList.getSpec_sku_id().equals(attribute.getItem_id())) {
                    // 属性值是否可点击flag  默认禁止点击
                    if (specList.getForm().getStock_num() >= 0) {
                        //如果当前库存为0包括大于0时，该商品有该类型的属性，只是当前库存为0，有可能会允许购买，可以点击
                        String attributeValue = specAttr.getSpec_items().get(i).getSpec_value();
                        itemLayout.optionItemViewEnableStatus(attributeValue);
                    } else {
                        //如果当前库存为 -1时，该商品没有该类型的属性，禁止点击
                    }
                }
            }
        }
    }

    private void optionLayoutEnableStatusMultipleProperties() {
        //先获取到所有不存在的规则组合ID
        List<String> skuIdList = new ArrayList<>();
        for (SpecList specList : specData.getSpec_list()) {
            if (specList.getForm().getStock_num() == -1) {
                skuIdList.add(specList.getSpec_sku_id());
            }
        }

        TourCooLogUtil.e(skuIdList);

        // 获取选中信息列表ID
        List<String> selectSpecId = new ArrayList<>();
        for (int k = 0; k < selectedAttributeList.size(); k++) {
            // 选中信息为空，则说明未选中
            if (StringUtils.isEmpty(selectedAttributeList.get(k).getSpec_value())) {
                selectSpecId.add("-1");
            } else {
                selectSpecId.add(selectedAttributeList.get(k).getItem_id());
            }
        }

        TourCooLogUtil.e(selectSpecId);

        //如果不存在需要设置不可点击的组合，则对所有的sku全部设置成可点击状态
        if (skuIdList.size() == 0) {
            for (int k = 0; k < skuContainerLayout.getChildCount(); k++) {
                SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(k);
                // 遍历sku列表
                SpecAttr specAttr = specData.getSpec_attr().get(k);
                // 对所有的sku全部设置成可点击状态
                List<SkuAttribute> attributeBeanList = specAttr.getSpec_items();
                for (SkuAttribute attribute : attributeBeanList) {
                    String attributeValue = attribute.getSpec_value();
                    itemLayout.optionItemViewEnableStatus(attributeValue);
                }
            }
        } else { //存在需要设置不可点击的组合

            //从不允许点击的组合中取出最符合当前选中的组
            List<Integer> existMulList = new ArrayList<>();

            for (int i = 0; i < skuIdList.size(); i++) {
                //获取不可点击的ID集合
                String[] skuIds = skuIdList.get(i).split("_");
                //不可点击的ID集合与当前选中的ID集合 两者相同下标的数据作比较，获取到当前有多少个sku属性是相同的
                //通过true false来动态控制sku的可点击状态
                List<Boolean> existList = new ArrayList<>();
                for (int j = 0; j < skuIds.length; j++) {
                    existList.add(skuIds[j].equals(selectSpecId.get(j)));
                }
                //获取到当前相同ID的个数
                //如果出现true的次数等于（集合的长度 - 1），此时需要限制sku的其中一个属性不可点击
                int num = Collections.frequency(existList, true);
                if (num == existList.size() - 1) {
                    existMulList.add(i);
                }
            }

            TourCooLogUtil.e(existMulList);


            if (existMulList.size() == 0) {
                for (int k = 0; k < skuContainerLayout.getChildCount(); k++) {
                    SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(k);
                    // 遍历sku列表
                    for (int l = 0; l < specData.getSpec_attr().size(); l++) {
                        SpecAttr specAttr = specData.getSpec_attr().get(l);
                        // 属性值是否可点击flag
                        List<SkuAttribute> attributeBeanList = specAttr.getSpec_items();
                        for (SkuAttribute attribute : attributeBeanList) {
                            String attributeValue = attribute.getSpec_value();
                            itemLayout.optionItemViewEnableStatus(attributeValue);
                        }
                    }
                }
            } else {


                Map<Integer, List<String>> map = new HashMap<>();

                for (int existIndex : existMulList) {
                    List<String> unclickId = new ArrayList<>();

                    String[] skuIds = skuIdList.get(existIndex).split("_");
                    List<Boolean> existList = new ArrayList<>();
                    for (int j = 0; j < skuIds.length; j++) {
                        existList.add(skuIds[j].equals(selectSpecId.get(j)));
                    }

                    //获取不可点击的sku Item布局的下标
                    int index = existList.indexOf(false);
                    if (map.get(index) == null) {
                        unclickId.add(skuIds[index]);
                        map.put(index, unclickId);
                    } else {
                        unclickId = map.get(index);
                        unclickId.add(skuIds[index]);
                        map.put(index, unclickId);
                    }
                }

                TourCooLogUtil.e(map);

                //禁止不存在的规则组合的点击
                for (int k = 0; k < skuContainerLayout.getChildCount(); k++) {
                    SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(k);
                    // 遍历sku列表
                    SpecAttr specAttr = specData.getSpec_attr().get(k);
                    // 属性值是否可点击flag
                    List<SkuAttribute> attributeBeanList = specAttr.getSpec_items();
                    for (SkuAttribute attribute : attributeBeanList) {

                        if (map.get(k) != null) {
                            List<String> unclickId = map.get(k);
                            if(!unclickId.contains(attribute.getItem_id())){
                                String attributeValue = attribute.getSpec_value();
                                itemLayout.optionItemViewEnableStatus(attributeValue);
                            }

                        } else {
                            String attributeValue = attribute.getSpec_value();
                            itemLayout.optionItemViewEnableStatus(attributeValue);
                        }

                    }
                }

            }

        }


    }


    /**
     * 设置所有属性的选中状态
     */
    private void optionLayoutSelectStatus() {
        for (int i = 0; i < skuContainerLayout.getChildCount(); i++) {
            SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(i);
            itemLayout.optionItemViewSelectStatus(selectedAttributeList.get(i));
        }
    }

    /**
     * 是否有sku选中
     *
     * @return
     */
    private boolean isSkuSelected() {
        for (SkuAttribute attribute : selectedAttributeList) {
            if (TextUtils.isEmpty(attribute.getSpec_value())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取第一个未选中的属性名
     *
     * @return
     */
    public String getFirstUnelectedAttributeName() {
        for (int i = 0; i < skuContainerLayout.getChildCount(); i++) {
            SkuItemLayout itemLayout = (SkuItemLayout) skuContainerLayout.getChildAt(i);
            if (!itemLayout.isSelected()) {
                return itemLayout.getAttributeName();
            }
        }
        return "";
    }

    /**
     * 获取选中的Sku
     *
     * @return
     */
    public SpecList getSelectedSku() {
        // 判断是否有选中的sku
        if (!isSkuSelected()) {
            return null;
        }

        StringBuilder spec_sku_id = new StringBuilder();
        for (int i = 0; i < selectedAttributeList.size(); i++) {
            SkuAttribute attribute = selectedAttributeList.get(i);
            if (i == selectedAttributeList.size() - 1) {
                spec_sku_id.append(attribute.getItem_id());
            } else {
                spec_sku_id.append(attribute.getItem_id() + "_");
            }
        }
        List<SpecList> specLists = specData.getSpec_list();
        for (SpecList specList : specLists) {
            // 将sku的属性列表与selectedAttributeList匹配，完全匹配则为已选中sku
            boolean flag = true;
            if (!specList.getSpec_sku_id().equals(spec_sku_id.toString())) {
                flag = false;
            }
            if (flag) {
                return specList;
            }

        }
        return null;
    }

    /**
     * 设置选中的sku
     *
     * @param specAttrs
     */
    public void setSelectedSku(List<SpecAttr> specAttrs) {
        selectedAttributeList.clear();

        for (int i = 0; i < specAttrs.size(); i++) {
            selectedAttributeList.add(new SkuAttribute(
                    specAttrs.get(i).getSpec_items().get(0).getItem_id(),
                    specAttrs.get(i).getSpec_items().get(0).getSpec_value()
            ));
        }

        // 清除所有选中状态
        clearAllLayoutStatus();
        // 设置是否可点击
        optionLayoutEnableStatus();
        // 设置选中状态
        optionLayoutSelectStatus();
    }

    @Override
    public void onSelect(int position, boolean selected, SkuAttribute attribute) {
        if (selected) {
            // 选中，保存选中信息
            selectedAttributeList.set(position, attribute);
        } else {
            // 取消选中，清空保存的选中信息
            selectedAttributeList.get(position).setSpec_value("");
        }
        clearAllLayoutStatus();
        // 设置是否可点击
        optionLayoutEnableStatus();
        // 设置选中状态
        optionLayoutSelectStatus();
        // 回调接口
        if (isSkuSelected()) {
            listener.onSkuSelected(getSelectedSku());
        } else if (selected) {
            listener.onSelect(attribute);
        } else {
            listener.onUnselected(attribute);
        }
    }
}