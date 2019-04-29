package com.tourcoo.xiantao.widget.sku;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;
import com.tourcoo.xiantao.entity.spec.SkuAttribute;
import com.tourcoo.xiantao.entity.spec.SpecAttr;
import com.tourcoo.xiantao.entity.spec.SpecData;
import com.tourcoo.xiantao.entity.spec.SpecList;
import com.tourcoo.xiantao.util.BaseSkuModel;
import com.tourcoo.xiantao.util.SkuUtil;
import com.tourcoo.xiantao.widget.sku.view.OnSkuListener;
import com.tourcoo.xiantao.widget.sku.view.SkuSelectScrollView;
import com.wuhenzhizao.titlebar.utils.AppUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

/**
 * Created by liufei on 2017/11/30.
 */
public class ProductSkuDialog extends Dialog {
    private TextView btnSubmit;
    private TextView tvSkuInfo;
    private Context context;
    private GoodsEntity product;
    private List<Sku> skuList;
    private Callback callback;
    private View contentView;
    private EditText et_sku_quantity_input;
    private RoundedImageView iv_sku_logo;
    private Sku selectedSku;
    private String priceFormat;
    private String stockQuantityFormat;
    private ImageButton ib_sku_close;
    private TextView btn_sku_quantity_minus;
    private TextView btn_sku_quantity_plus;
    private SkuSelectScrollView scroll_sku_list;
    private TextView tvSkuSellingPrice;
    private TextView tvSkuQuantity;

    private TextView tvSkuSellingPriceUnit;

    public ProductSkuDialog(@NonNull Context context) {
        this(context, R.style.CommonBottomDialogStyle);
    }

    public ProductSkuDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        initView();
    }

    private void initView() {
        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_product_sku, null);
        setContentView(contentView);
        tvSkuSellingPriceUnit = contentView.findViewById(R.id.tv_sku_selling_price_unit);
        tvSkuSellingPrice = contentView.findViewById(R.id.tv_sku_selling_price);
        btnSubmit = contentView.findViewById(R.id.btn_submit);
        tvSkuQuantity = contentView.findViewById(R.id.tv_sku_quantity);
        tvSkuInfo = contentView.findViewById(R.id.tv_sku_info);
        iv_sku_logo = contentView.findViewById(R.id.iv_sku_logo);
        scroll_sku_list = contentView.findViewById(R.id.scroll_sku_list);
        btn_sku_quantity_plus = contentView.findViewById(R.id.btn_sku_quantity_plus);
        ib_sku_close = contentView.findViewById(R.id.ib_sku_close);
        et_sku_quantity_input = contentView.findViewById(R.id.et_sku_quantity_input);
        btn_sku_quantity_minus = contentView.findViewById(R.id.btn_sku_quantity_minus);
        ib_sku_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_sku_quantity_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = et_sku_quantity_input.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 1) {
                    String newQuantity = String.valueOf(quantityInt - 1);
                    et_sku_quantity_input.setText(newQuantity);
                    et_sku_quantity_input.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt - 1);
                }
            }
        });
        btn_sku_quantity_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = et_sku_quantity_input.getText().toString();
                if (TextUtils.isEmpty(quantity) || selectedSku == null) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt < selectedSku.getStockQuantity()) {
                    String newQuantity = String.valueOf(quantityInt + 1);
                    et_sku_quantity_input.setText(newQuantity);
                    et_sku_quantity_input.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt + 1);
                }
            }
        });
        et_sku_quantity_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != EditorInfo.IME_ACTION_DONE || selectedSku == null) {
                    return false;
                }
                String quantity = et_sku_quantity_input.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return false;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt <= 1) {
                    et_sku_quantity_input.setText("1");
                    et_sku_quantity_input.setSelection(1);
                    updateQuantityOperator(1);
                } else if (quantityInt >= selectedSku.getStockQuantity()) {
                    String newQuantity = String.valueOf(selectedSku.getStockQuantity());
                    et_sku_quantity_input.setText(newQuantity);
                    et_sku_quantity_input.setSelection(newQuantity.length());
                    updateQuantityOperator(selectedSku.getStockQuantity());
                } else {
                    et_sku_quantity_input.setSelection(quantity.length());
                    updateQuantityOperator(quantityInt);
                }
                return false;
            }
        });
        scroll_sku_list.setListener(new OnSkuListener() {
            @Override
            public void onUnselected(SkuAttribute unselectedAttribute) {
                selectedSku = null;
//                GImageLoader.displayUrl(context, binding.ivSkuLogo, product.getMainImage());
                GlideManager.loadImg(product.getDetail().getImage(), iv_sku_logo);
//                tvSkuQuantity.setText(String.format(stockQuantityFormat, product.getStockQuantity()));
                String firstUnselectedAttributeName = scroll_sku_list.getFirstUnelectedAttributeName();
                tvSkuInfo.setText("请选择：" + firstUnselectedAttributeName);
                btnSubmit.setEnabled(false);
                String quantity = et_sku_quantity_input.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }

            @Override
            public void onSelect(SkuAttribute selectAttribute) {
                String firstUnselectedAttributeName = scroll_sku_list.getFirstUnelectedAttributeName();
                tvSkuInfo.setText("请选择：" + firstUnselectedAttributeName);
            }

            @Override
            public void onSkuSelected(Sku sku) {
                selectedSku = sku;
                GlideManager.loadImg(selectedSku.getMainImage(), iv_sku_logo);
//                GImageLoader.displayUrl(context, binding.ivSkuLogo, selectedSku.getMainImage());
                List<SkuAttribute> attributeList = selectedSku.getAttributes();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < attributeList.size(); i++) {
                    if (i != 0) {
                        builder.append("　");
                    }
                    SkuAttribute attribute = attributeList.get(i);
                    builder.append("\"" + attribute.getSpec_value() + "\"");
                }
                tvSkuInfo.setText("已选：" + builder.toString());
                tvSkuQuantity.setText(String.format(stockQuantityFormat, selectedSku.getStockQuantity()));
                btnSubmit.setEnabled(true);
                String quantity = et_sku_quantity_input.getText().toString();
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
                String quantity = et_sku_quantity_input.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 0 && quantityInt <= selectedSku.getStockQuantity()) {
                    callback.onAdded(selectedSku, quantityInt);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "商品数量超出库存，请修改数量", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setData(final GoodsEntity product, Callback callback) {
        this.product = product;
        this.skuList = product.getSkuList();
        this.callback = callback;

        priceFormat = context.getString(R.string.comm_price_format);
        stockQuantityFormat = context.getString(R.string.product_detail_sku_stock);

        updateSkuData();
        updateQuantityOperator(1);
    }

    private void updateSkuData() {
        scroll_sku_list.setSkuList(product.getSkuList());
        Sku firstSku = product.getSkuList().get(0);
        if (firstSku.getStockQuantity() > 0) {
            selectedSku = firstSku;
            // 选中第一个sku
            scroll_sku_list.setSelectedSku(selectedSku);
//            GImageLoader.displayUrl(context, binding.ivSkuLogo, selectedSku.getMainImage());
            GlideManager.loadImg(selectedSku.getMainImage(), iv_sku_logo);
            tvSkuSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(selectedSku.getSellingPrice() / 100)));
//            tvSkuSellingPriceUnit.setText("/" + product.getMeasurementUnit());
            tvSkuQuantity.setText(String.format(stockQuantityFormat, selectedSku.getStockQuantity()));
            btnSubmit.setEnabled(selectedSku.getStockQuantity() > 0);
            List<SkuAttribute> attributeList = selectedSku.getAttributes();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < attributeList.size(); i++) {
                if (i != 0) {
                    builder.append("　");
                }
                SkuAttribute attribute = attributeList.get(i);
                builder.append("\"" + attribute.getSpec_value() + "\"");
            }
            tvSkuInfo.setText("已选：" + builder.toString());
        } else {
//            GImageLoader.displayUrl(context, binding.ivSkuLogo, product.getMainImage());
            GlideManager.loadImg(product.getDetail().getImage(), iv_sku_logo);
//            tvSkuSellingPrice.setText(String.format(priceFormat, NumberUtils.formatNumber(product.getSellingPrice() / 100)));
           /* tvSkuSellingPriceUnit.setText("/" + product.getMeasurementUnit());
            tvSkuQuantity.setText(String.format(stockQuantityFormat, product.getStockQuantity()));*/
            btnSubmit.setEnabled(false);
            tvSkuInfo.setText("请选择：" + skuList.get(0).getAttributes().get(0).getItem_id());
        }
    }

    private void updateQuantityOperator(int newQuantity) {
        if (selectedSku == null) {
            btn_sku_quantity_minus.setEnabled(false);
            btn_sku_quantity_plus.setEnabled(false);
            et_sku_quantity_input.setEnabled(false);
        } else {
            if (newQuantity <= 1) {
                btn_sku_quantity_minus.setEnabled(false);
                btn_sku_quantity_plus.setEnabled(true);
            } else if (newQuantity >= selectedSku.getStockQuantity()) {
                btn_sku_quantity_minus.setEnabled(true);
                btn_sku_quantity_plus.setEnabled(false);
            } else {
                btn_sku_quantity_minus.setEnabled(true);
                btn_sku_quantity_plus.setEnabled(true);
            }
            et_sku_quantity_input.setEnabled(true);
        }

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 解决键盘遮挡输入框问题
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.getDecorView().setPadding(0, 0, 0, 0);
        // KeyboardConflictCompat.assistWindow(getWindow());
        AppUtils.transparencyBar(getWindow());
    }


    public interface Callback {
        void onAdded(Sku sku, int quantity);
    }

 /*   private List<Sku> parseSkuList(GoodsEntity goodsEntity) {
        List<Sku> skuList = new ArrayList<>();
        if (goodsEntity == null || goodsEntity.getDetail() == null || goodsEntity.getDetail().getSpec() == null) {
            return
        }
    }*/


    private List<Sku> parseSkuList(GoodsEntity goodsEntity) {
        List<Sku> skuList = new ArrayList<>();
        if (goodsEntity == null || goodsEntity.getSpecData() == null) {
            return skuList;
        }
        SpecData specData = goodsEntity.getSpecData();
        List<SpecList> specListList = specData.getSpec_list();
        if (specListList == null) {
            return skuList;
        }
        List<SpecAttr> specAttrList = specData.getSpec_attr();
        HashMap<String, BaseSkuModel> baseSkuModelHashMap = new HashMap<>();
        for (SpecAttr specAttr : specAttrList) {
            List<SkuAttribute> attributeList = specAttr.getSpec_items();
//            ,
//            baseSkuModelHashMap.put(specAttr.getGroup_name(), );
            return skuList;
        }
//        SkuUtil.skuCollection();

   /* private Sku getSkuBySkuId(String skuId, List<SpecList> specListList ){
        Sku sku = new Sku();
        for (SpecList specListBean : specListList) {
                    if(skuId.equalsIgnoreCase(specListBean.getSpec_sku_id())){
                        sku =
                    }
        }
    }*/


   /* private void parseSku(BaseSkuModel skuModel,SpecList specList){
        skuModel.setGoods_price(specList.getForm().getGoods_price());
        skuModel.setStock_num(specList.getForm().getStock_num());
        skuModel.setGoods_no(specList.getForm().getGoods_no());
        skuModel.setGoods_price();
        */
   return null;
    }
}
