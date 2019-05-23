package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.shoppingcar.ShoppingCarEntity;
import com.tourcoo.xiantao.listener.OnAddDelCallback;

/**
 * @author :JenkinsZhou
 * @description :购物车适配器版本2
 * @company :途酷科技
 * @date 2019年04月18日12:34
 * @Email: 971613168@qq.com
 */
public abstract class BaseShoppingCartAdapter2 extends BaseQuickAdapter<ShoppingCarEntity.GoodsBean, BaseViewHolder> {
    private OnAddDelCallback mOnAddDelCallback;

    public OnAddDelCallback getOnAddDelCallback() {
        return mOnAddDelCallback;
    }

    public void setOnAddDelCallback(OnAddDelCallback onAddDelCallback) {
        mOnAddDelCallback = onAddDelCallback;
    }

    public BaseShoppingCartAdapter2() {
        super(R.layout.item_goods_cart_version2);
    }





}
