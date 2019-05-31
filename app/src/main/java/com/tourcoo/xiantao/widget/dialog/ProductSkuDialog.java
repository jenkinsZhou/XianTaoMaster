package com.tourcoo.xiantao.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;
import com.tourcoo.xiantao.entity.goods.Spec;
import com.tourcoo.xiantao.entity.goods.TuanRule;
import com.tourcoo.xiantao.entity.spec.SkuAttribute;
import com.tourcoo.xiantao.entity.spec.SpecAttr;
import com.tourcoo.xiantao.entity.spec.SpecData;
import com.tourcoo.xiantao.entity.spec.SpecList;
import com.tourcoo.xiantao.util.NumberUtils;
import com.tourcoo.xiantao.widget.sku.view.OnSkuListener;
import com.tourcoo.xiantao.widget.sku.view.SkuSelectScrollView;
import com.wuhenzhizao.titlebar.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by liufei on 2017/11/30.
 */
public class ProductSkuDialog extends Dialog {
    private static final String TAG = "ProductSkuDialog";
    private Context context;
    private GoodsEntity product;
    private List<SpecAttr> skuList;
    private Callback callback;
    private String saleOut = "(已售完)";
    private SpecList selectedSku;
    private String priceFormat;
    private String stockQuantityFormat;

    public static final int SHOPPING_CART = 1;
    public static final int PING_TUAN = 2;
    public static final int BUY_NOW = 3;
    private int type;


    public ProductSkuDialog(@NonNull Context context, GoodsEntity product, Callback callback, int type) {
        super(context, R.style.CommonBottomDialogStyle);
        this.context = context;
        this.product = product;
        this.callback = callback;
        this.type = type;

    }


    //标题
    private TextView tvTitle;
    private TextView tvGoodsName;
    //减少商品
    private TextView btnSkuQuantityMinus;
    //增加商品
    private TextView btnSkuQuantityPlus;
    //规格详情
    private TextView tvSkuInfo;
    //商品库存
    private TextView tvSkuQuantity;
    //商品售卖价格
    private TextView tvSkuSellingPrice;
    //商品单位
//    private TextView tvSkuSellingPriceUnit;

    private Button btnSubmit;
    //商品数量输入框
    private EditText etSkuQuantityInput;
    //商品规格sku
    private SkuSelectScrollView scrollSkuList;
    //商品规格图标
    private RoundedImageView ivSkuLogo;

    private int currentSkuQuantity = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_goods_select);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        stockQuantityFormat = context.getString(R.string.product_detail_sku_stock);
        tvGoodsName = findViewById(R.id.tvGoodsName);
        tvTitle = findViewById(R.id.tvTitle);
        btnSkuQuantityMinus = findViewById(R.id.btn_sku_quantity_minus);
        etSkuQuantityInput = findViewById(R.id.et_sku_quantity_input);
        btnSkuQuantityPlus = findViewById(R.id.btn_sku_quantity_plus);
        scrollSkuList = findViewById(R.id.scroll_sku_list);
        tvSkuInfo = findViewById(R.id.tv_sku_info);
        tvSkuQuantity = findViewById(R.id.tv_sku_quantity);
        btnSubmit = findViewById(R.id.btn_submit);
        ivSkuLogo = findViewById(R.id.ivGoodsImage);
        tvSkuSellingPrice = findViewById(R.id.tv_sku_selling_price);
//        tvSkuSellingPriceUnit =findViewById(R.id.tv_sku_selling_price_unit);
        showGoodsStockByCondition();
        btnSkuQuantityMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 1) {
                    String newQuantity = String.valueOf(quantityInt - 1);
                    etSkuQuantityInput.setText(newQuantity);
                    etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt - 1);
                }
            }
        });
        btnSkuQuantityPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (currentSkuQuantity != -1) {
                    if (quantityInt < currentSkuQuantity) {
                        String newQuantity = String.valueOf(quantityInt + 1);
                        etSkuQuantityInput.setText(newQuantity);
                        etSkuQuantityInput.setSelection(newQuantity.length());
                        updateQuantityOperator(quantityInt + 1);
                    } else {
                        ToastUtil.showFailed("已达到商品数量上限");
                    }
                } else {
                    String newQuantity = String.valueOf(quantityInt + 1);
                    etSkuQuantityInput.setText(newQuantity);
                    etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt + 1);
                }
            }
        });
        etSkuQuantityInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != EditorInfo.IME_ACTION_DONE || selectedSku == null) {
                    return false;
                }
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return false;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt <= 1) {
                    etSkuQuantityInput.setText("1");
                    etSkuQuantityInput.setSelection(1);
                    updateQuantityOperator(1);
                }

//                else if (quantityInt >= selectedSku.getStockQuantity()) {
//                    String newQuantity = String.valueOf(selectedSku.getStockQuantity());
//                    etSkuQuantityInput.setText(newQuantity);
//                    etSkuQuantityInput.setSelection(newQuantity.length());
//                    updateQuantityOperator(selectedSku.getStockQuantity());
//                }

                else {
                    etSkuQuantityInput.setSelection(quantity.length());
                    updateQuantityOperator(quantityInt);
                }
                return false;
            }
        });
        scrollSkuList.setListener(new OnSkuListener() {
            /**
             * 属性取消选中
             *
             * @param unselectedAttribute
             */
            @Override
            public void onUnselected(SkuAttribute unselectedAttribute) {
                selectedSku = null;
                currentSkuQuantity = -1;
                tvSkuSellingPrice.setText("");
                GlideManager.loadImg(product.getDetail().getImage(), ivSkuLogo);
                tvSkuQuantity.setVisibility(View.GONE);
                tvSkuQuantity.setText(String.format(stockQuantityFormat, 0));
                String firstUnselectedAttributeName = scrollSkuList.getFirstUnelectedAttributeName();
                tvSkuInfo.setText("选择：" + firstUnselectedAttributeName);
                btnSubmit.setEnabled(false);
                String quantity = etSkuQuantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }

            }

            /**
             * 属性选中
             *
             * @param selectAttribute
             */
            @Override
            public void onSelect(SkuAttribute selectAttribute) {
                String firstUnselectedAttributeName = scrollSkuList.getFirstUnelectedAttributeName();
                tvSkuInfo.setText("选择：" + firstUnselectedAttributeName);
                tvSkuSellingPrice.setText("");
            }

            /**
             * sku选中
             *
             * @param specList
             */
            @Override
            public void onSkuSelected(SpecList specList) {
                selectedSku = specList;
                currentSkuQuantity = specList.getForm().getStock_num();
                StringBuilder builder = new StringBuilder();
                StringBuilder spec_sku_id = new StringBuilder();
                String[] skuIds = selectedSku.getSpec_sku_id().split("_");
                List<SpecAttr> specAttrList = product.getSpecData().getSpec_attr();
                for (int i = 0; i < specAttrList.size(); i++) {
                    List<SkuAttribute> skuAttributeList = specAttrList.get(i).getSpec_items();
                    for (int j = 0; j < skuAttributeList.size(); j++) {
                        SkuAttribute attribute = skuAttributeList.get(j);
                        if (skuIds[i].equals("" + attribute.getItem_id())) {
                            //选中的sku属性实体
                            if (i == specAttrList.size() - 1) {
                                spec_sku_id.append(attribute.getItem_id());
                            } else {
                                spec_sku_id.append(attribute.getItem_id() + "_");
                            }

                            if (i != 0) {
                                builder.append("　");
                            }
                            builder.append("\"" + attribute.getSpec_value() + "\"");

                        }
                    }
                }


                if (specList.getForm().getImgshow() == null) {
                    GlideManager.loadImg(product.getDetail().getImage(), ivSkuLogo);
                } else {
                    GlideManager.loadImg(specList.getForm().getImgshow().toString(), ivSkuLogo);
                }

                tvSkuQuantity.setVisibility(View.VISIBLE);
                showGoodsStock(specList.getForm().getStock_num());
                tvSkuSellingPrice.setText(String.format(priceFormat, specList.getForm().getGoods_price()));
                tvSkuInfo.setText("已选：" + builder.toString());

                btnSubmit.setEnabled(true);
                String quantity = etSkuQuantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }

                if (product.getSpecData() != null && type != PING_TUAN) {

                    if (scrollSkuList.getSelectedSku() == null) {
                        String info = "请" + tvSkuInfo.getText().toString();
                        if (StringUtils.isEmpty(info)) {
                            ToastUtil.showFailed("请选择商品规格");
                        } else {
                            ToastUtil.showFailed(info);
                        }
                        return;
                    }

                    StringBuilder spec_sku_id = new StringBuilder();
                    String[] skuIds = selectedSku.getSpec_sku_id().split("_");
                    List<SpecAttr> specAttrList = product.getSpecData().getSpec_attr();
                    for (int i = 0; i < specAttrList.size(); i++) {
                        List<SkuAttribute> skuAttributeList = specAttrList.get(i).getSpec_items();
                        for (int j = 0; j < skuAttributeList.size(); j++) {
                            SkuAttribute attribute = skuAttributeList.get(j);
                            if (skuIds[i].equals("" + attribute.getItem_id())) {
                                //选中的sku属性实体
                                if (i == specAttrList.size() - 1) {
                                    spec_sku_id.append(attribute.getItem_id());
                                } else {
                                    spec_sku_id.append(attribute.getItem_id() + "_");
                                }
                            }
                        }
                    }
                    int quantityInt = Integer.parseInt(quantity);

                    if (quantityInt > 0 && quantityInt <= selectedSku.getForm().getStock_num()) {
                        callback.onAdded(spec_sku_id.toString(), quantityInt);
                        dismiss();
                    } else {
                        ToastUtil.showFailed("商品数量超出库存");
                    }
                } else {
                    int quantityInt = Integer.parseInt(quantity);
                    if (type == PING_TUAN) {
                        TuanRule tuanRule = new Gson().fromJson(product.getDetail().getTuan_rule().toString(), TuanRule.class);
                        if (quantityInt > 0 && quantityInt <= tuanRule.getNum()) {
                            callback.onAdded("", quantityInt);
                            dismiss();
                        } else {
                            ToastUtil.showFailed("已达到商品数量上限");
                        }
                    } else {
                        callback.onAdded("", quantityInt);
                        dismiss();
                    }
                }
            }
        });

        priceFormat = context.getString(R.string.comm_price_format);


        tvGoodsName.setText(product.getDetail().getGoods_name());

        switch (type) {
            case SHOPPING_CART:
                tvTitle.setText("加入购物车");
                if (product.getSpecData() == null) {
                    scrollSkuList.setVisibility(View.GONE);
                    GlideManager.loadImg(product.getDetail().getImage(), ivSkuLogo);
                    tvSkuSellingPrice.setText(String.format(priceFormat, product.getDetail().getSpec().get(0).getGoods_price()));
                    btnSubmit.setEnabled(true);
                    tvSkuInfo.setText("选择：数量");
                    btnSkuQuantityMinus.setEnabled(false);
                    btnSkuQuantityPlus.setEnabled(true);
                } else {
                    this.skuList = product.getSpecData().getSpec_attr();
                    updateSkuData();
                    updateQuantityOperator(1);
                }
                break;
            case PING_TUAN:
                tvTitle.setText("发起拼团");
                scrollSkuList.setVisibility(View.GONE);
                GlideManager.loadImg(product.getDetail().getImage(), ivSkuLogo);
                if (product.getDetail().isTuan()) {
                    TuanRule tuanRule = new Gson().fromJson(product.getDetail().getTuan_rule().toString(), TuanRule.class);
                    tvSkuSellingPrice.setText(tuanRule.getName());
                    currentSkuQuantity = tuanRule.getNum();
                }
                btnSubmit.setEnabled(true);
                tvSkuInfo.setText("选择：数量");
                btnSkuQuantityMinus.setEnabled(false);
                btnSkuQuantityPlus.setEnabled(true);
                break;
            case BUY_NOW:
                tvTitle.setText("单独购买");
                if (product.getSpecData() == null) {
                    scrollSkuList.setVisibility(View.GONE);
                    GlideManager.loadImg(product.getDetail().getImage(), ivSkuLogo);
                    tvSkuSellingPrice.setText(String.format(priceFormat, product.getDetail().getSpec().get(0).getGoods_price()));
                    btnSubmit.setEnabled(true);
                    tvSkuInfo.setText("选择：数量");
                    btnSkuQuantityMinus.setEnabled(false);
                    btnSkuQuantityPlus.setEnabled(true);
                } else {
                    this.skuList = product.getSpecData().getSpec_attr();
                    updateSkuData();
                    updateQuantityOperator(1);
                }
                break;
            default:
                break;
        }

    }

    private void updateSkuData() {
        scrollSkuList.setSkuData(product.getSpecData());
//                SpecList firstSku = null;
//                for (SpecList first : product.getSpecData().getSpec_list()) {
//                    if (first.getForm().getStock_num() >= 0) {
//                        firstSku = first;
//                        TourCooLogUtil.e(first);
//                        break;
//                    }
//                }
//
//                if (firstSku != null && firstSku.getForm().getStock_num() >= 0) {
//
//                    selectedSku = firstSku;
//                    currentSkuQuantity = firstSku.getForm().getStock_num();
//                     //选中第一个sku
//                    scrollSkuList.setSelectedSku(firstSku);
//
//                    StringBuilder builder = new StringBuilder();
//                    StringBuilder spec_sku_id = new StringBuilder();
//                    String[] skuIds = selectedSku.getSpec_sku_id().split("_");
//                    List<SpecAttr> specAttrList = product.getSpecData().getSpec_attr();
//                    for (int i = 0; i < specAttrList.size(); i++) {
//                        List<SkuAttribute> skuAttributeList = specAttrList.get(i).getSpec_items();
//                        for (int j = 0; j < skuAttributeList.size(); j++) {
//                            SkuAttribute attribute = skuAttributeList.get(j);
//                            if (skuIds[i].equals("" + attribute.getItem_id())) {
//                                //选中的sku属性实体
//                                if (i == specAttrList.size() - 1) {
//                                    spec_sku_id.append(attribute.getItem_id());
//                                } else {
//                                    spec_sku_id.append(attribute.getItem_id() + "_");
//                                }
//
//                                if (i != 0) {
//                                    builder.append(" ");
//                                }
//                                builder.append("\"" + attribute.getSpec_value() + "\"");
//
//                            }
//                        }
//                    }
//
//
//                    if (selectedSku.getForm().getImgshow() == null) {
//                        GlideManager.loadImg(product.getDetail().getImage(), ivSkuLogo);
//                    } else {
//                        GlideManager.loadImg(selectedSku.getForm().getImgshow().toString(), ivSkuLogo);
//                    }
//                    tvSkuSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(selectedSku.getForm().getGoods_price())));
//                    tvSkuQuantity.setVisibility(View.VISIBLE);
//                    tvSkuQuantity.setText(String.format(stockQuantityFormat, selectedSku.getForm().getStock_num()));
//                    btnSubmit.setEnabled(selectedSku.getForm().getStock_num() >= 0);
//
//                    tvSkuInfo.setText("已选：" + builder.toString());
//                } else {
        GlideManager.loadImg(product.getDetail().getImage(), ivSkuLogo);
        try {
            if (type == PING_TUAN) {
                Object object = product.getDetail().getTuan_rule();
                if (object != null) {
                    TuanRule tuanRule = new Gson().fromJson(object.toString(), TuanRule.class);
                    tvSkuSellingPrice.setText(tuanRule.getName());
                }
            }
            btnSubmit.setEnabled(true);
            tvSkuInfo.setText("选择：" + skuList.get(0).getGroup_name());
        } catch (Exception e) {
            e.printStackTrace();
        }

//                }


    }

    private void updateQuantityOperator(int newQuantity) {
        if (product.getSpecData() != null && selectedSku == null && type != PING_TUAN) {
            btnSkuQuantityMinus.setEnabled(false);
            btnSkuQuantityPlus.setEnabled(false);
            etSkuQuantityInput.setEnabled(false);
        } else {
            if (newQuantity <= 1) {
                btnSkuQuantityMinus.setEnabled(false);
                btnSkuQuantityPlus.setEnabled(true);
            }
//            else if (newQuantity >= selectedSku.getStockQuantity()) {
//                btnSkuQuantityMinus.setEnabled(true);
//                btnSkuQuantityPlus.setEnabled(false);
//            }
            else {
                btnSkuQuantityMinus.setEnabled(true);
                btnSkuQuantityPlus.setEnabled(true);
            }
            etSkuQuantityInput.setEnabled(true);
        }

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 解决键盘遮挡输入框问题
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        //设置Dialog距离底部的距离
        lp.y = 30;
        dialogWindow.setAttributes(lp);
        AppUtils.transparencyBar(getWindow());
    }


    public interface Callback {
        void onAdded(String specSkuId, int quantity);
    }


    private void showGoodsStockByCondition() {
        if (product != null && product.getDetail() != null) {
            if (product.getDetail().getSpec() != null && product.getDetail().getSpec().size() == 1) {
                //当前商品只有一种属性
                Spec spec = product.getDetail().getSpec().get(0);
                if (spec != null) {
                    showGoodsStock(spec.getStock_num());
                }
            }
        }
    }


    private void showGoodsStock(int stockNum) {
        if (tvSkuQuantity == null) {
            return;
        }
        tvSkuQuantity.setVisibility(View.VISIBLE);
        if (stockNum <= 0) {
            tvSkuQuantity.setTextColor(TourCooUtil.getColor(R.color.redTextCommon));
            tvSkuQuantity.setText(saleOut);
        } else {
            tvSkuQuantity.setTextColor(TourCooUtil.getColor(R.color.comm_text_gray));
            tvSkuQuantity.setText(String.format(stockQuantityFormat, stockNum));
        }
    }
}
