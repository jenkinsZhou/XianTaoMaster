package com.tourcoo.xiantao.entity.goods;

import com.tourcoo.xiantao.entity.spec.SpecData;
import com.tourcoo.xiantao.entity.sku.Sku;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :商品明细实体
 * @company :途酷科技
 * @date 2019年04月25日10:17
 * @Email: 971613168@qq.com
 */
public class GoodsEntity implements Serializable {
    private int collect;

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    private List<Sku> skuList;

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    private int goodsCount = 1;
    /**
     * {
     * "detail": {
     * "goods_id": 51,
     * "goods_name": "测试商品大图",
     * "category_id": 11,
     * "images": "https://test.ahxtao.com/uploads/20190524/4c627c18c862a7123a952918bff9e654.jpg,/uploads/20190525/7c680ab616e745f7751e7bf30b263c3d.jpg,/uploads/20190525/a4837bfec6f243b31eb53b4bd7148b96.gif,/uploads/20190525/f872e059ad06a2ce224c2bb345f34bce.jpg,/uploads/20190525/9af0f66a86f0db5c694626a94df1064e.jpg,/uploads/20190525/25f13b1cd25512c55007b9a14df98f8c.jpg",
     * "spec_type": "10",
     * "deduct_stock_type": "10",
     * "content": "<p><img src=\"https://test.ahxtao.com/uploads/20190525/2b439d0429092734dbcadf7f97efdc1c.jpg\" style=\"width: 603px;\" data-filename=\"filename\"><br></p>",
     * "sales_initial": 0,
     * "sales_actual": 11,
     * "goods_sort": 51,
     * "delivery_id": 28,
     * "goods_status": "10",
     * "is_delete": "0",
     * "createtime": 1558658783,
     * "updatetime": 1559273225,
     * "label": "新鲜采摘，安全无农药",
     * "origin": "美国夏威夷",
     * "recommend1": 0,
     * "recommend2": 0,
     * "recommend3": 0,
     * "promote": "好吃的水果",
     * "special": 1,
     * "bottom": 0,
     * "spec": [
     * {
     * "goods_spec_id": 726,
     * "goods_id": 51,
     * "goods_no": "1",
     * "goods_price": "50.00",
     * "line_price": "1.00",
     * "stock_num": 85,
     * "goods_sales": 0,
     * "goods_weight": 1,
     * "spec_sku_id": "",
     * "spec_image": "",
     * "create_time": 1559273225,
     * "update_time": 1559273282
     * }
     * ],
     * "spec_rel": [],
     * "imgs_url": [
     * "https://test.ahxtao.com/uploads/20190524/4c627c18c862a7123a952918bff9e654.jpg",
     * "https://test.ahxtao.com/uploads/20190525/7c680ab616e745f7751e7bf30b263c3d.jpg",
     * "https://test.ahxtao.com/uploads/20190525/a4837bfec6f243b31eb53b4bd7148b96.gif",
     * "https://test.ahxtao.com/uploads/20190525/f872e059ad06a2ce224c2bb345f34bce.jpg",
     * "https://test.ahxtao.com/uploads/20190525/9af0f66a86f0db5c694626a94df1064e.jpg",
     * "https://test.ahxtao.com/uploads/20190525/25f13b1cd25512c55007b9a14df98f8c.jpg"
     * ],
     * "image": "https://test.ahxtao.com/uploads/20190524/4c627c18c862a7123a952918bff9e654.jpg",
     * "collect": 1,
     * "comment_list": [
     * {
     * "id": 13,
     * "goods_id": 0,
     * "user_id": 20,
     * "order_id": 607,
     * "nickname": "182****5578",
     * "avatar": "https://test.ahxtao.com/uploads/20190529/b0202555d39d01259eb3edb8d7f0dd56.png",
     * "star": 5,
     * "detail": "121",
     * "images": null,
     * "createtime": 1559198879,
     * "updatetime": 1559198879,
     * "status": "normal",
     * "reply": null,
     * "admin_id": 0
     * }
     * ],
     * "tuan_rule": {
     * "id": 17,
     * "goods_id": 51,
     * "price": 0,
     * "name": "",
     * "weigh": 0,
     * "num": 1,
     * "status": "hidden",
     * "createtime": 1558951557,
     * "updatetime": 1558951557,
     * "validity": 0,
     * "admin_id": 0
     * },
     * "tuan": false,
     * "tuan_list": [],
     * "give": 0,
     * "deduct": 0,
     * "deduct_coin": 0,
     * "goods_min_line_price": "1.00",
     * "goods_min_price": "50.00",
     * "goods_max_price": "50.00",
     * "deduct_rule": "直接购买可用"
     * },
     * "specData": null
     * }
     */

    private Goods detail;
    private SpecData specData;

    public Goods getDetail() {
        return detail;
    }

    public void setDetail(Goods detail) {
        this.detail = detail;
    }

    public SpecData getSpecData() {
        return specData;
    }

    public void setSpecData(SpecData specData) {
        this.specData = specData;
    }



}
