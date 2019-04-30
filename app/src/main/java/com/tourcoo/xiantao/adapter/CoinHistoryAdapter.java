package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.entity.coin.CoinDetail;

/**
 * @author :JenkinsZhou
 * @description :消费记录
 * @company :途酷科技
 * @date 2019年04月30日11:50
 * @Email: 971613168@qq.com
 */
public class CoinHistoryAdapter extends BaseQuickAdapter<CoinDetail, BaseViewHolder> {


    public CoinHistoryAdapter() {
        super(R.layout.item_coin_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, CoinDetail item) {
        String typeValue;
        if (item.getAttr() == 1) {
            typeValue = "金币" + item.getSymbol() + item.getAmount();
        } else {
            typeValue = "银币" + item.getSymbol() + item.getAmount();
        }
        helper.setText(R.id.spentType, item.getType_text());
        helper.setText(R.id.tvCoinRecord, typeValue);
        helper.setText(R.id.tvRechargeTime, item.getCreatetime_text());
    }
}
