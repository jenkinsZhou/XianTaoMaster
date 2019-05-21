package com.tourcoo.xiantao.adapter;

import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :商品详情中的标签适配器
 * @company :途酷科技
 * @date 2019年05月21日20:59
 * @Email: 971613168@qq.com
 */
public class GoodsLableAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public GoodsLableAdapter(@Nullable List<String> data) {
        super(R.layout.item_goods_label);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tvGoodsLabel, item);
    }
}
