package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.RechargeEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :JenkinsZhou
 * @description :充值详情
 * @company :途酷科技
 * @date 2019年03月26日18:22
 * @Email: 971613168@qq.com
 */
public class RechargeDetailAdapter extends BaseQuickAdapter<RechargeEntity, BaseViewHolder> {

    public RechargeDetailAdapter(@Nullable List<RechargeEntity> data) {
        super(R.layout.item_recharge_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeEntity item) {
        helper.setText(R.id.tvRechargeMoney, "+" + item.rechargeMoney + "元");
        helper.setText(R.id.tvAccountBalance, item.accountBalance + "元");
        helper.setText(R.id.tvRechargeTime, item.rechargeTime);
    }
}
