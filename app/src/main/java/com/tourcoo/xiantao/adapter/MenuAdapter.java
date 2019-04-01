package com.tourcoo.xiantao.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.entity.MenuItem;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author :JenkinsZhou
 * @description :我的菜单适配器
 * @company :途酷科技
 * @date 2019年03月29日10:13
 * @Email: 971613168@qq.com
 */
public class MenuAdapter extends BaseQuickAdapter<MenuItem, BaseViewHolder> {
    public MenuAdapter(@Nullable List<MenuItem> data) {
        super(R.layout.item_mine_menu, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuItem item) {
        helper.setImageResource(R.id.ivMenuImage, item.menuImageId);
        helper.setText(R.id.tvMenuLabel, item.menuLabel);

    }
}
