package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.util.TourcoolUtil;
import com.tourcoo.xiantao.entity.InvoiceInfomationEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :JenkinsZhou
 * @description :发票信息适配器
 * @company :途酷科技
 * @date 2019年03月29日19:43
 * @Email: 971613168@qq.com
 */
public class InvoiceInformationAdapter extends BaseQuickAdapter<InvoiceInfomationEntity, BaseViewHolder> {
    public static final int TYPE_INVOICE_PAPER = 1;
    public static final int TYPE_INVOICE_ELECTRON = 2;

    public static final int STATUS_INVOICE_WAIT = 1;
    public static final int STATUS_INVOICE_FINISH = 2;

    public InvoiceInformationAdapter(@Nullable List<InvoiceInfomationEntity> data) {
        super(R.layout.item_invoice_information, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvoiceInfomationEntity item) {
        helper.setText(R.id.tvInvoiceDescription, TourcoolUtil.getStringNotNull(item.invoiceDescription));
        switch (item.invoiceType) {
            case TYPE_INVOICE_PAPER:
                helper.setText(R.id.tvInvoiceType, TourcoolUtil.getStringNotNull("纸质发票"));
                break;
            case TYPE_INVOICE_ELECTRON:
                helper.setText(R.id.tvInvoiceType, TourcoolUtil.getStringNotNull("电子发票"));
                break;
            default:
                helper.setText(R.id.tvInvoiceType, TourcoolUtil.getStringNotNull("未知"));
                break;
        }
        helper.setText(R.id.tvInvoiceMoney, "￥" + item.invoiceMoney);
        switch (item.invoiceStatus) {
            case STATUS_INVOICE_WAIT:
                helper.setText(R.id.tvInvoiceStatus, "待开具");
                helper.setTextColor(R.id.tvInvoiceStatus, TourcoolUtil.getColor(R.color.redTextCommon));
                break;
            case STATUS_INVOICE_FINISH:
                helper.setText(R.id.tvInvoiceStatus, "已开具");
                helper.setTextColor(R.id.tvInvoiceStatus, TourcoolUtil.getColor(R.color.colorPrimary));
                break;
            default:
                helper.setText(R.id.tvInvoiceStatus, "未知");
                helper.setTextColor(R.id.tvInvoiceStatus, TourcoolUtil.getColor(R.color.redTextCommon));
                break;
        }
        helper.setText(R.id.tvInvoiceCompany, TourcoolUtil.getStringNotNull(item.invoiceCompany));

        helper.setText(R.id.tvInvoiceNumber, TourcoolUtil.getStringNotNull(item.invoiceNumber));


    }
}
