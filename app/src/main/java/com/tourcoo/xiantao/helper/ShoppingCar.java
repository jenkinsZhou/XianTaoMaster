package com.tourcoo.xiantao.helper;

import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.entity.goods.GoodsDetailEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :购物车实例
 * @company :途酷科技
 * @date 2019年04月17日12:50
 * @Email: 971613168@qq.com
 */
public class ShoppingCar {
    private static final String TAG = "ShoppingCar";
    /**
     * 购物车
     */
    private List<GoodsDetailEntity> shoppingCar = new ArrayList<>();

    private ShoppingCar() {
    }

    private static class Holder {
        private static final ShoppingCar INSTANCE = new ShoppingCar();
    }

    public static ShoppingCar getInstance() {
        return ShoppingCar.Holder.INSTANCE;
    }


    /**
     * 添加商品
     *
     * @param goodsDetailEntity
     */
    public void addGoods(GoodsDetailEntity goodsDetailEntity) {
        if (goodsDetailEntity == null) {
            TourCooLogUtil.e(TAG, "商品为null");
            return;
        }
        //先判断购物车内是否有此类商品(根据商品id判断)
        int index = goodsHasExist(goodsDetailEntity);
        if (index >= 0) {
            //已存在，直接将购物车该商品的数量+1
            GoodsDetailEntity current = shoppingCar.get(index);
            //设为选中状态
        /*    current.select = true;
            current.goodsCount++;*/
//            TourCooLogUtil.d(TAG, "商品已经存在,执行++ 当前数量:" + shoppingCar.get(index).goodsCount);
        } else {
            //不存在，则直接添加,并将值数量置为1
//            goodsDetailEntity.goodsCount = 1;
            //设为选中状态
//            goodsDetailEntity.select = true;
            shoppingCar.add(goodsDetailEntity);
            TourCooLogUtil.i(TAG, "商品不存在,直接添加");
        }
    }

    /**
     * 获取购物车中商品数量
     *
     * @return
     */
    public int getGoodsCount() {
        int count = 0;
        for (GoodsDetailEntity goodsDetailEntity : shoppingCar) {
//            count += goodsDetailEntity.goodsCount;
        }
        return count;
    }

    public void clearShoppingCar() {
        if (shoppingCar != null) {
            shoppingCar.clear();
        }
    }

    /**
     * 判断购物车是否为空
     */
    public boolean isEmpty() {
        return shoppingCar == null || shoppingCar.isEmpty();
    }

    /**
     * 获取购物车
     *
     * @return
     */
    public List<GoodsDetailEntity> getShoppingCar() {
        return shoppingCar;
    }

    /**
     * 判断商品是否已存在 -1表示商品在购物车中不存在,其他代表存在返回商品所在的购物车索引
     *
     * @return
     */
    private int goodsHasExist(GoodsDetailEntity goodsDetailEntity) {
        if (goodsDetailEntity == null) {
            return -1;
        }
        for (int i = 0; i < shoppingCar.size(); i++) {
          /*  TourCooLogUtil.i(TAG, "商品id:" + goodsDetailEntity.goodsId);
            TourCooLogUtil.d(TAG, "购物车中商品id:" + shoppingCar.get(i).goodsId);
            if (goodsDetailEntity.goodsId == shoppingCar.get(i).goodsId) {

                return i;
            }*/
        }
        return -1;
    }


    private int goodsExsits(long goodsEntityId) {
        for (int i = 0; i < shoppingCar.size(); i++) {
           /* TourCooLogUtil.i(TAG, "商品id:" + goodsEntityId);
            TourCooLogUtil.d(TAG, "购物车中商品id:" + shoppingCar.get(i).goodsId);
            if (goodsEntityId == shoppingCar.get(i).goodsId) {
                return i;
            }*/
        }
        return -1;
    }

    /**
     * 移除某商品
     *
     * @param goodsDetailEntity
     */
    public void removeGoods(GoodsDetailEntity goodsDetailEntity) {
        if (goodsDetailEntity == null || shoppingCar == null || shoppingCar.isEmpty()) {
            TourCooLogUtil.e(TAG, "商品无法移除");
            return;
        }
        for (int i = shoppingCar.size() - 1; i >= 0; i--) {
            //表示该商品在购物车中的位置(小于0表示购物车中不存在该商品)index指的是商品在购物车中的索引
            int index = goodsHasExist(goodsDetailEntity);
            if (index >= 0) {
                //先拿到购物车中对应的该商品
                GoodsDetailEntity currentGoods = shoppingCar.get(index);
                //该商品在购物车中数量
            /*    int count = currentGoods.goodsCount;
                TourCooLogUtil.w(TAG, "当前商品数量:" + count);
                if (count <= 0) {
                    TourCooLogUtil.e(TAG, "商品移除失败(因为商品数量为0)");
                } else if (count == 1) {
                    shoppingCar.remove(currentGoods);
                    TourCooLogUtil.w(TAG, "商品移除成功(购物车内已经没有该商品)");
                } else {
                    currentGoods.goodsCount = currentGoods.goodsCount--;
                    TourCooLogUtil.d(TAG, "商品移除成功(数量减了，但购物车内仍然存在,当前剩余数量:" + currentGoods.goodsCount);
                }*/
                //找到商品后必须break
                break;
            }
        }
    }

    private boolean hasGoods(long goodsId) {
        for (GoodsDetailEntity goodsDetailEntity : shoppingCar) {
          /*  if (goodsId == goodsDetailEntity.goodsId) {
                return true;
            }*/
        }
        return false;
    }


    public void removeGoods(long goodsId) {
        if (shoppingCar == null || shoppingCar.isEmpty()) {
            TourCooLogUtil.e(TAG, "商品无法移除");
            return;
        }
        for (int i = shoppingCar.size() - 1; i >= 0; i--) {
            //表示该商品在购物车中的位置(小于0表示购物车中不存在该商品)index指的是商品在购物车中的索引
            int index = goodsExsits(goodsId);
            if (index > -1) {
             /*   GoodsDetailEntity goodsDetailEntity = shoppingCar.get(index);
                goodsDetailEntity.goodsCount = goodsDetailEntity.goodsCount--;*/
            }
        }
    }

    /**
     * 设置商品数量
     *
     * @param goods
     */
    public void setGoodsCount(GoodsDetailEntity goods) {
        if (goods == null || shoppingCar.isEmpty()) {
            return;
        }
        int index = goodsHasExist(goods);
        if (index < 0) {
            shoppingCar.add(goods);
        } else {
//            shoppingCar.get(index).setGoosCount(goods.goodsCount);
        }
    }


    /**
     * 计算商品总金额(不根据是否选中状态)
     *
     * @param goodsDetailEntityList
     * @return
     */
    public double getTotalMoney(List<GoodsDetailEntity> goodsDetailEntityList) {
        double totalMoney = 0.0;
        if (goodsDetailEntityList == null) {
            return totalMoney;
        }
        for (GoodsDetailEntity goodsDetailEntity : goodsDetailEntityList) {
//            totalMoney += goodsDetailEntity.goodsCurrentPrice * goodsDetailEntity.goodsCount;
        }
        return totalMoney;
    }


    public double getTotalMoneyBySelect(List<GoodsDetailEntity> goodsDetailEntityList) {
        double totalMoney = 0.0;
        if (goodsDetailEntityList == null) {
            return totalMoney;
        }
        for (GoodsDetailEntity goodsDetailEntity : goodsDetailEntityList) {
        /*    if (goodsDetailEntity.select) {
                totalMoney += goodsDetailEntity.goodsCurrentPrice * goodsDetailEntity.goodsCount;
                TourCooLogUtil.i(TAG, "计算了一次:" + totalMoney);
            }*/
        }
        BigDecimal bd = new BigDecimal(totalMoney);
        totalMoney = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return totalMoney;
    }

    /**
     * 从购物车中删除某个商品
     * @param goodsDetailEntity
     */
    public void deleteGoods(GoodsDetailEntity goodsDetailEntity) {
        if (goodsDetailEntity == null) {
            return;
        }
        int index = goodsHasExist(goodsDetailEntity);
        if (index < 0) {
            return;
        }
        shoppingCar.remove(shoppingCar.get(index));
    }







}
