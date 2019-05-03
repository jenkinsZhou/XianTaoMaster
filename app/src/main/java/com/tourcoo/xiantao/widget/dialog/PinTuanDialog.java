package com.tourcoo.xiantao.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;
import com.tourcoo.xiantao.entity.goods.TuanRule;
import com.tourcoo.xiantao.entity.spec.SkuAttribute;
import com.tourcoo.xiantao.entity.spec.SpecAttr;
import com.tourcoo.xiantao.entity.spec.SpecList;
import com.tourcoo.xiantao.util.FormatDuration;
import com.tourcoo.xiantao.widget.sku.view.OnSkuListener;
import com.tourcoo.xiantao.widget.sku.view.SkuSelectScrollView;
import com.wuhenzhizao.titlebar.utils.AppUtils;

import java.util.List;

/**
 * Created by liufei on 2017/11/30.
 */
public class PinTuanDialog extends Dialog {
    private Context context;
    private Callback callback;
    private String surplus;
    private long deadline;

    public PinTuanDialog(@NonNull Context context, int currentQuantity, String surplus, long deadline, Callback callback) {
        super(context, R.style.CommonBottomDialogStyle);
        this.context = context;
        this.callback = callback;
        this.currentQuantity = currentQuantity;
        this.surplus = surplus;
        this.deadline = deadline;
    }


    private EditText etInput;
    //减少商品
    private TextView btnMinus;
    //增加商品
    private TextView btnPlus;
    private TextView tvInfo;
    private Button btnSubmit;
    private int currentQuantity = -1;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pin_tuan);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                countDownTimer.cancel();
            }
        });
        btnMinus = findViewById(R.id.btnMinus);
        etInput = findViewById(R.id.etInput);
        btnPlus = findViewById(R.id.btnPlus);
        tvInfo = findViewById(R.id.tvInfo);

        long time = deadline - System.currentTimeMillis();
        countDownTimer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                tvInfo.setText(new SpanUtils()
                        .append("商品仅剩").setForegroundColor(TourCoolUtil.getColor(R.color.colorTextBlack))
                        .append(surplus + "kg " + FormatDuration.format(new Long(millisUntilFinished).intValue()) + "后结束")
                        .setForegroundColor(TourCoolUtil.getColor(R.color.redTextCommon)).create());
            }

            public void onFinish() {

            }
        }.start();


        btnSubmit = findViewById(R.id.btn_submit);

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 1) {
                    String newQuantity = String.valueOf(quantityInt - 1);
                    etInput.setText(newQuantity);
                    etInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt - 1);
                }
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (currentQuantity != -1) {
                    if (quantityInt < currentQuantity) {
                        String newQuantity = String.valueOf(quantityInt + 1);
                        etInput.setText(newQuantity);
                        etInput.setSelection(newQuantity.length());
                        updateQuantityOperator(quantityInt + 1);
                    } else {
                        ToastUtils.showShort("已达到商品数量上限");
                    }
                } else {
                    String newQuantity = String.valueOf(quantityInt + 1);
                    etInput.setText(newQuantity);
                    etInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt + 1);
                }
            }
        });
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != EditorInfo.IME_ACTION_DONE) {
                    return false;
                }
                String quantity = etInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return false;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt <= 1) {
                    etInput.setText("1");
                    etInput.setSelection(1);
                    updateQuantityOperator(1);
                } else {
                    etInput.setSelection(quantity.length());
                    updateQuantityOperator(quantityInt);
                }
                return false;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }

                int quantityInt = Integer.parseInt(quantity);
                callback.onAdded(quantityInt);
                dismiss();

            }
        });

    }

    private void updateQuantityOperator(int newQuantity) {
        if (newQuantity <= 1) {
            btnMinus.setEnabled(false);
            btnPlus.setEnabled(true);
        } else {
            btnMinus.setEnabled(true);
            btnPlus.setEnabled(true);
        }
        etInput.setEnabled(true);
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 解决键盘遮挡输入框问题
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        AppUtils.transparencyBar(getWindow());
    }


    public interface Callback {
        void onAdded(int quantity);
    }
}
