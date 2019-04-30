package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.widget.core.util.ResourceUtil;
import com.tourcoo.xiantao.entity.recharge.RechargeEntity;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author :JenkinsZhou
 * @description :充值金额适配器
 * @company :途酷科技
 * @date 2019年03月28日14:28
 * @Email: 971613168@qq.com
 */
public class RechargeAmountAdapter extends BaseQuickAdapter<RechargeEntity, BaseViewHolder> {
    public RechargeAmountAdapter(@Nullable List<RechargeEntity> data) {
        super(R.layout.item_recharge_amount, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeEntity item) {
        helper.setText(R.id.tvRechargeMoney, (int) item.rechargeMoney + "元");
        if (item.selected) {
            helper.setBackgroundRes(R.id.tvRechargeMoney, R.drawable.selector_bg_radius_5_green_hollow);
            helper.setTextColor(R.id.tvRechargeMoney, ResourceUtil.getColor(R.color.colorPrimary));
        } else {
            helper.setBackgroundRes(R.id.tvRechargeMoney, R.drawable.selector_bg_radius_5_gray_hollow);
            helper.setTextColor(R.id.tvRechargeMoney, ResourceUtil.getColor(R.color.grayCommon));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
