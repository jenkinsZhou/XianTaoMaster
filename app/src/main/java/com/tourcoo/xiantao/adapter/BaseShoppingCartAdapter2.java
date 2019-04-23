package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :JenkinsZhou
 * @description :购物车适配器版本2
 * @company :途酷科技
 * @date 2019年04月18日12:34
 * @Email: 971613168@qq.com
 */
public abstract class BaseShoppingCartAdapter2 extends BaseQuickAdapter<GoodsEntity, BaseViewHolder> {

    public BaseShoppingCartAdapter2(@Nullable List<GoodsEntity> data) {
        super(R.layout.item_goods_cart_version2, data);
    }
}
