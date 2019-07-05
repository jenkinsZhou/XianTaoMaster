package com.tourcoo.xiantao.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.ui.recharge.AccountBalanceActivity;


/**
 * @author :JenkinsZhou
 * @description :自定义支付对话框
 * @company :途酷科技
 * @date 2019年03月20日20:51
 * @Email: 971613168@qq.com
 */
public class PayDialog extends Dialog implements View.OnClickListener {
    private ImageView ivAliAPayCheckBox;
    private ImageView ivWeChatPayCheckBox;
    private ImageView ivBalancePayCheckBox;
    /**
     * 余额支付
     */
    public static final int PAY_TYPE_BALANCE = 2;
    public static final int PAY_TYPE_ALI = 1;
    public static final int PAY_TYPE_WE_XIN = 0;
    private int payType = PAY_TYPE_ALI;
    private TextView tvMoney;
    private Context context;
    private double money;
    private PayListener mPayListener;
    private boolean showBalancePay = true;
    private double balanceMoney;
    private TextView tvBalanceAmount;
    private TextView btnRecharge;

   /* public PayDialog(Context context, double money, PayListener payListener) {
        super(context, R.style.PayDialogStyle);
        this.context = context;
        this.money = money;
        this.mPayListener = payListener;
    }*/


    public PayDialog(Context context, double money, double balanceMoney, PayListener payListener) {
        super(context, R.style.PayDialogStyle);
        this.context = context;
        this.money = money;
        this.balanceMoney = balanceMoney;
        this.mPayListener = payListener;
    }

    public PayDialog(Context context, double money, PayListener payListener, boolean showBalancePay) {
        super(context, R.style.PayDialogStyle);
        this.context = context;
        this.money = money;
        this.mPayListener = payListener;
        this.showBalancePay = showBalancePay;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay);
        findViewById(R.id.tvPayConfirm).setOnClickListener(this);
        findViewById(R.id.rlWeChatPay).setOnClickListener(this);
        findViewById(R.id.rlAliPay).setOnClickListener(this);
        tvBalanceAmount = findViewById(R.id.tvBalanceAmount);
        btnRecharge = findViewById(R.id.btnRecharge);
        btnRecharge.setOnClickListener(this);
        ivBalancePayCheckBox = findViewById(R.id.ivBalancePayCheckBox);
        RelativeLayout rlBalancePay = findViewById(R.id.rlBalancePay);
        rlBalancePay.setOnClickListener(this);
        ivAliAPayCheckBox = findViewById(R.id.ivAliAPayCheckBox);
        ivWeChatPayCheckBox = findViewById(R.id.ivWeChatPayCheckBox);
        if (showBalancePay) {
            //todo 暂时屏蔽余额支付
            rlBalancePay.setVisibility(View.GONE);
        } else {
            rlBalancePay.setVisibility(View.GONE);
        }
        tvMoney = findViewById(R.id.tvMoney);
        tvMoney.setText("¥" + TourCooUtil.doubleTransString(money));
        tvBalanceAmount.setText("¥" +TourCooUtil.doubleTransString(balanceMoney));
        Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.BOTTOM);
        }
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        //设置Dialog距离底部的距离
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        usePayAli();
        showBalanceAmount();
    }


    private void setSelect(boolean select, ImageView imageView) {
        if (select) {
            imageView.setImageResource(R.mipmap.ic_select_selected);
        } else {
            imageView.setImageResource(R.mipmap.ic_select_normal);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlBalancePay:
                useBalance();
                break;
            case R.id.rlWeChatPay:
                usePayWeiChat();
                break;
            case R.id.rlAliPay:
                usePayAli();
                break;
            case R.id.tvPayConfirm:
                if (mPayListener != null) {
                    mPayListener.pay(payType, this);
                }
                break;
            case R.id.btnRecharge:
                //立即充值
                TourCooUtil.startActivity(context, AccountBalanceActivity.class);
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
                dismiss();
                break;
            default:
                break;
        }
    }


    private void usePayWeiChat() {
        setSelect(true, ivWeChatPayCheckBox);
        setSelect(false, ivAliAPayCheckBox);
        setSelect(false, ivBalancePayCheckBox);
        payType = PAY_TYPE_WE_XIN;
    }

    private void usePayAli() {
        setSelect(false, ivWeChatPayCheckBox);
        setSelect(false, ivBalancePayCheckBox);
        setSelect(true, ivAliAPayCheckBox);
        payType = PAY_TYPE_ALI;
    }

    private void useBalance() {
        setSelect(false, ivWeChatPayCheckBox);
        setSelect(false, ivAliAPayCheckBox);
        setSelect(true, ivBalancePayCheckBox);
        payType = PAY_TYPE_BALANCE;
    }

    /**
     * 支付回调
     */
    public interface PayListener {

        /**
         * 支付
         *
         * @param payType
         * @param dialog
         */
        void pay(int payType, Dialog dialog);

    }


    private void showBalanceAmount() {
        if (balanceMoney < money || !showBalancePay) {
            //如果账户余额不足 则显示立即充值
            usePayAli();
            btnRecharge.setVisibility(View.VISIBLE);
        } else {
            //默认使用余额支付
            //todo 暂时默认支付宝支付
            usePayAli();
            btnRecharge.setVisibility(View.GONE);
        }
    }
}
