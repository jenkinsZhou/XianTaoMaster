package com.tourcoo.xiantao.entity;

import com.tourcoo.xiantao.entity.goods.GoodsDetailEntity;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :购物车实体
 * @company :途酷科技
 * @date 2019年04月01日11:33
 * @Email: 971613168@qq.com
 */
public class ShoppingCartEntity {
    public double allPrice;
    private List<GoodsDetailEntity> mGoodsDetailEntityList;
}
