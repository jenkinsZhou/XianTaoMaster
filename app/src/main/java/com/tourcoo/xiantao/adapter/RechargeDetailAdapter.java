package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.entity.recharge.RechargeDetail;
import com.tourcoo.xiantao.entity.recharge.RechargeEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :JenkinsZhou
 * @description :充值详情
 * @company :途酷科技
 * @date 2019年03月26日18:22
 * @Email: 971613168@qq.com
 */
public class RechargeDetailAdapter extends BaseQuickAdapter<RechargeDetail, BaseViewHolder> {

    public RechargeDetailAdapter() {
        super(R.layout.item_recharge_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeDetail item) {
        String money = item.getSymbol() + item.getAmount() + "元";
        helper.setText(R.id.tvRechargeMoney, money);
        helper.setText(R.id.tvAccountBalance, item.getCash() + "元");
        helper.setText(R.id.tvRechargeTime, DateUtil.parseDate(item.getCreatetime()));
    }
}
