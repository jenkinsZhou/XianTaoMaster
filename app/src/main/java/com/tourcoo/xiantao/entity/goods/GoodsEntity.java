package com.tourcoo.xiantao.entity.goods;

import com.tourcoo.xiantao.entity.spec.SpecData;
import com.tourcoo.xiantao.widget.sku.Sku;

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
     *   "code": 1,
     *   "msg": "",
     *   "time": "1556463374",
     *   "data": {
     *     "detail": {
     *       "goods_id": 28,
     *       "goods_name": "小台农芒果",
     *       "category_id": 9,
     *       "images": "https://ahxtao.hfcoco.top/uploads/20190428/dad009181b01a253238d89d04f518ff0.jpg",
     *       "spec_type": "10",
     *       "deduct_stock_type": "10",
     *       "content": "<p><img src=\"https://ahxtao.hfcoco.top/uploads/20190428/17e5417d204848084ac6bd92045dcdc3.jpg\" data-filename=\"filename\" style=\"width: 603px;\"><br></p>",
     *       "sales_initial": 0,
     *       "sales_actual": 5,
     *       "goods_sort": 28,
     *       "delivery_id": 25,
     *       "goods_status": "10",
     *       "is_delete": "0",
     *       "createtime": 1556332856,
     *       "updatetime": 1556444936,
     *       "label": "",
     *       "origin": "",
     *       "image": "https://ahxtao.hfcoco.top/uploads/20190428/dad009181b01a253238d89d04f518ff0.jpg",
     *       "imgs_url": [
     *         "https://ahxtao.hfcoco.top/uploads/20190428/dad009181b01a253238d89d04f518ff0.jpg"
     *       ],
     *       "collect": 0,
     *       "category": {
     *         "id": 9,
     *         "pid": 6,
     *         "name": "国产水果",
     *         "image": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/753cd25e97135e874dca8ab5126ad144.jpg",
     *         "weigh": 9,
     *         "createtime": 1541403546,
     *         "updatetime": 1541403622
     *       },
     *       "spec": [
     *         {
     *           "goods_spec_id": 236,
     *           "goods_id": 28,
     *           "goods_no": "",
     *           "goods_price": "10.00",
     *           "line_price": "15.00",
     *           "stock_num": 95,
     *           "goods_sales": 0,
     *           "goods_weight": 1,
     *           "spec_sku_id": "",
     *           "spec_image": "",
     *           "create_time": 1556444936,
     *           "update_time": 1556444936
     *         }
     *       ],
     *       "spec_rel": [],
     *       "freight": {
     *         "id": 25,
     *         "name": "包邮",
     *         "method": "20",
     *         "weigh": 25,
     *         "createtime": 1555036150,
     *         "updatetime": 1555036150,
     *         "method_text": "Method 20"
     *       },
     *       "goods_sales": 5,
     *       "tuan_rule": null,
     *       "tuan": false,
     *       "tuan_list": [
     *         {
     *           "id": 1,
     *           "goods_id": 24,
     *           "user_id": 3,
     *           "rule_id": 1,
     *           "status": 1,
     *           "createtime": 1556333914,
     *           "updatetime": 1556333914,
     *           "deadline": 1556593114,
     *           "nickname": "陈舟为",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190426/324e19196a9b75b279324bb974776dbe.png",
     *           "num": 10,
     *           "surplus": 2,
     *           "goods": {
     *             "goods_name": "车厘子智利进口",
     *             "label": "",
     *             "images": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg",
     *             "image": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 1,
     *             "goods_id": 24,
     *             "price": 10,
     *             "name": "10元/200g",
     *             "weigh": 0.2,
     *             "num": 10,
     *             "status": "normal",
     *             "createtime": 1555916335,
     *             "updatetime": 1556264243,
     *             "validity": 3,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "陈舟为",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190426/324e19196a9b75b279324bb974776dbe.png"
     *           },
     *           "tuan": {
     *             "num": 10,
     *             "surplus": 2
     *           }
     *         },
     *         {
     *           "id": 2,
     *           "goods_id": 24,
     *           "user_id": 3,
     *           "rule_id": 1,
     *           "status": 1,
     *           "createtime": 1556351238,
     *           "updatetime": 1556351238,
     *           "deadline": 1556610438,
     *           "nickname": "陈舟为",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190426/324e19196a9b75b279324bb974776dbe.png",
     *           "num": 10,
     *           "surplus": 2,
     *           "goods": {
     *             "goods_name": "车厘子智利进口",
     *             "label": "",
     *             "images": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg",
     *             "image": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 1,
     *             "goods_id": 24,
     *             "price": 10,
     *             "name": "10元/200g",
     *             "weigh": 0.2,
     *             "num": 10,
     *             "status": "normal",
     *             "createtime": 1555916335,
     *             "updatetime": 1556264243,
     *             "validity": 3,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "陈舟为",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190426/324e19196a9b75b279324bb974776dbe.png"
     *           },
     *           "tuan": {
     *             "num": 10,
     *             "surplus": 2
     *           }
     *         },
     *         {
     *           "id": 3,
     *           "goods_id": 24,
     *           "user_id": 2,
     *           "rule_id": 1,
     *           "status": 1,
     *           "createtime": 1556354777,
     *           "updatetime": 1556354777,
     *           "deadline": 1556613977,
     *           "nickname": "周健",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190427/43597faeb457812746578ca99bcc893b.png",
     *           "num": 10,
     *           "surplus": 2,
     *           "goods": {
     *             "goods_name": "车厘子智利进口",
     *             "label": "",
     *             "images": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg",
     *             "image": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 1,
     *             "goods_id": 24,
     *             "price": 10,
     *             "name": "10元/200g",
     *             "weigh": 0.2,
     *             "num": 10,
     *             "status": "normal",
     *             "createtime": 1555916335,
     *             "updatetime": 1556264243,
     *             "validity": 3,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "周健",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190427/43597faeb457812746578ca99bcc893b.png"
     *           },
     *           "tuan": {
     *             "num": 10,
     *             "surplus": 2
     *           }
     *         },
     *         {
     *           "id": 4,
     *           "goods_id": 24,
     *           "user_id": 2,
     *           "rule_id": 1,
     *           "status": 1,
     *           "createtime": 1556357186,
     *           "updatetime": 1556357186,
     *           "deadline": 1556616386,
     *           "nickname": "周健",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190427/43597faeb457812746578ca99bcc893b.png",
     *           "num": 10,
     *           "surplus": 2,
     *           "goods": {
     *             "goods_name": "车厘子智利进口",
     *             "label": "",
     *             "images": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg",
     *             "image": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 1,
     *             "goods_id": 24,
     *             "price": 10,
     *             "name": "10元/200g",
     *             "weigh": 0.2,
     *             "num": 10,
     *             "status": "normal",
     *             "createtime": 1555916335,
     *             "updatetime": 1556264243,
     *             "validity": 3,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "周健",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190427/43597faeb457812746578ca99bcc893b.png"
     *           },
     *           "tuan": {
     *             "num": 10,
     *             "surplus": 2
     *           }
     *         },
     *         {
     *           "id": 5,
     *           "goods_id": 24,
     *           "user_id": 2,
     *           "rule_id": 1,
     *           "status": 1,
     *           "createtime": 1556357621,
     *           "updatetime": 1556357621,
     *           "deadline": 1556616821,
     *           "nickname": "周健",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190427/43597faeb457812746578ca99bcc893b.png",
     *           "num": 10,
     *           "surplus": 2,
     *           "goods": {
     *             "goods_name": "车厘子智利进口",
     *             "label": "",
     *             "images": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg",
     *             "image": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 1,
     *             "goods_id": 24,
     *             "price": 10,
     *             "name": "10元/200g",
     *             "weigh": 0.2,
     *             "num": 10,
     *             "status": "normal",
     *             "createtime": 1555916335,
     *             "updatetime": 1556264243,
     *             "validity": 3,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "周健",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190427/43597faeb457812746578ca99bcc893b.png"
     *           },
     *           "tuan": {
     *             "num": 10,
     *             "surplus": 2
     *           }
     *         },
     *         {
     *           "id": 6,
     *           "goods_id": 24,
     *           "user_id": 3,
     *           "rule_id": 1,
     *           "status": 1,
     *           "createtime": 1556361670,
     *           "updatetime": 1556361670,
     *           "deadline": 1556620870,
     *           "nickname": "陈舟为",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190426/324e19196a9b75b279324bb974776dbe.png",
     *           "num": 10,
     *           "surplus": 2,
     *           "goods": {
     *             "goods_name": "车厘子智利进口",
     *             "label": "",
     *             "images": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg",
     *             "image": "https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 1,
     *             "goods_id": 24,
     *             "price": 10,
     *             "name": "10元/200g",
     *             "weigh": 0.2,
     *             "num": 10,
     *             "status": "normal",
     *             "createtime": 1555916335,
     *             "updatetime": 1556264243,
     *             "validity": 3,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "陈舟为",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190426/324e19196a9b75b279324bb974776dbe.png"
     *           },
     *           "tuan": {
     *             "num": 10,
     *             "surplus": 2
     *           }
     *         },
     *         {
     *           "id": 8,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438222,
     *           "updatetime": 1556438222,
     *           "deadline": 1556524622,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 9,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438282,
     *           "updatetime": 1556438282,
     *           "deadline": 1556524682,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 10,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438294,
     *           "updatetime": 1556438294,
     *           "deadline": 1556524694,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 11,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438376,
     *           "updatetime": 1556438376,
     *           "deadline": 1556524776,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 12,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438385,
     *           "updatetime": 1556438385,
     *           "deadline": 1556524785,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 13,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438394,
     *           "updatetime": 1556438394,
     *           "deadline": 1556524794,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 14,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438403,
     *           "updatetime": 1556438403,
     *           "deadline": 1556524803,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 15,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438406,
     *           "updatetime": 1556438406,
     *           "deadline": 1556524806,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 16,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438413,
     *           "updatetime": 1556438413,
     *           "deadline": 1556524813,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 17,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438447,
     *           "updatetime": 1556438447,
     *           "deadline": 1556524847,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 18,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438456,
     *           "updatetime": 1556438456,
     *           "deadline": 1556524856,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 19,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438462,
     *           "updatetime": 1556438462,
     *           "deadline": 1556524862,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 20,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438469,
     *           "updatetime": 1556438469,
     *           "deadline": 1556524869,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 21,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556438478,
     *           "updatetime": 1556438478,
     *           "deadline": 1556524878,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 22,
     *           "goods_id": 29,
     *           "user_id": 7,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556439207,
     *           "updatetime": 1556439207,
     *           "deadline": 1556525607,
     *           "nickname": "ﾟ默｡",
     *           "avatar": "https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "ﾟ默｡",
     *             "avatar": "https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 23,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556439231,
     *           "updatetime": 1556439231,
     *           "deadline": 1556525631,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 24,
     *           "goods_id": 29,
     *           "user_id": 7,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556440492,
     *           "updatetime": 1556440492,
     *           "deadline": 1556526892,
     *           "nickname": "ﾟ默｡",
     *           "avatar": "https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "ﾟ默｡",
     *             "avatar": "https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 25,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556442728,
     *           "updatetime": 1556442728,
     *           "deadline": 1556529128,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         },
     *         {
     *           "id": 26,
     *           "goods_id": 29,
     *           "user_id": 4,
     *           "rule_id": 5,
     *           "status": 1,
     *           "createtime": 1556447494,
     *           "updatetime": 1556447494,
     *           "deadline": 1556533894,
     *           "nickname": "照明胧",
     *           "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png",
     *           "num": 2,
     *           "surplus": 5,
     *           "goods": {
     *             "goods_name": "精选砀山酥梨",
     *             "label": "",
     *             "images": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "image": "https://ahxtao.hfcoco.top/uploads/20190428/9b8fc78d2e24b8b9738e6fc2f6b22d94.jpg",
     *             "spec_type_text": "",
     *             "deduct_stock_type_text": "",
     *             "goods_status_text": "",
     *             "is_delete_text": "",
     *             "tuan_rule": false,
     *             "tuan": false,
     *             "tuan_list": [],
     *             "comment_list": [],
     *             "coin": 0
     *           },
     *           "tuan_rule": {
     *             "id": 5,
     *             "goods_id": 29,
     *             "price": 0.01,
     *             "name": "砀山酥梨拼团啦",
     *             "weigh": 2.5,
     *             "num": 2,
     *             "status": "normal",
     *             "createtime": 1556345831,
     *             "updatetime": 1556440474,
     *             "validity": 1,
     *             "admin_id": 0,
     *             "status_text": "开启"
     *           },
     *           "user": {
     *             "nickname": "照明胧",
     *             "avatar": "https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png"
     *           },
     *           "tuan": {
     *             "num": 2,
     *             "surplus": 5
     *           }
     *         }
     *       ],
     *       "comment_list": [],
     *       "coin": 0
     *     },
     *     "specData": null
     *   }
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
