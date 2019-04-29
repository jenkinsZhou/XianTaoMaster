package com.tourcoo.xiantao.entity.goods;


import com.tourcoo.xiantao.entity.spec.SpecData;

import java.io.Serializable;

/**
 * @author :JenkinsZhou
 * @description :商品实体
 * @company :途酷科技
 * @date 2019年03月29日15:56
 * @Email: 971613168@qq.com
 */
public class GoodsDetailEntity implements Serializable {
    /**
     * detail : {"goods_id":23,"goods_name":"MacBook Pro 13寸","category_id":8,"images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/85587c2e045b71fb4c977884a19a36cb.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg","spec_type":"20","deduct_stock_type":"20","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/43b7a84d68a15d9058971526068a853a.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","sales_initial":0,"sales_actual":12,"goods_sort":23,"delivery_id":23,"goods_status":"10","is_delete":"0","createtime":1541403061,"updatetime":1556098793,"label":"","origin":"","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png","imgs_url":["https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png","https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/85587c2e045b71fb4c977884a19a36cb.jpg","https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg"],"collect":0,"category":{"id":8,"pid":4,"name":"笔记本","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg","weigh":8,"createtime":1541402921,"updatetime":1541403316},"spec":[{"goods_spec_id":158,"goods_id":23,"goods_no":"mac_0001","goods_price":"12608.00","line_price":"0.00","stock_num":989,"goods_sales":0,"goods_weight":1.2,"spec_sku_id":"48","spec_image":"","create_time":1556098793,"update_time":1556098793},{"goods_spec_id":159,"goods_id":23,"goods_no":"mac_0002","goods_price":"12588.00","line_price":"0.00","stock_num":997,"goods_sales":0,"goods_weight":1.2,"spec_sku_id":"49","spec_image":"","create_time":1556098793,"update_time":1556098793}],"spec_rel":[{"id":48,"spec_value":"天空灰","spec_id":20,"createtime":1541403005,"pivot":{"id":140,"goods_id":23,"spec_id":20,"spec_value_id":48,"create_time":1556098793},"spec":{"id":20,"spec_name":"颜色","createtime":1541401442}},{"id":49,"spec_value":"银色","spec_id":20,"createtime":1541403011,"pivot":{"id":141,"goods_id":23,"spec_id":20,"spec_value_id":49,"create_time":1556098793}}],"freight":{"id":23,"name":"电脑","method":"10","weigh":23,"createtime":1540363605,"updatetime":1540363605,"method_text":"Method 10"},"goods_sales":12,"tuan_rule":{"status_text":""},"tuan":false,"tuan_list":[],"comment_list":[],"coin":0}
     * specData : {"spec_attr":[{"group_id":20,"group_name":"颜色","spec_items":[{"item_id":48,"spec_value":"天空灰"},{"item_id":49,"spec_value":"银色"}]}],"spec_list":[{"goods_spec_id":158,"spec_sku_id":"48","rows":[],"form":{"goods_no":"mac_0001","goods_price":"12608.00","goods_weight":1.2,"line_price":"0.00","stock_num":989,"spec_image":"","imgshow":null}},{"goods_spec_id":159,"spec_sku_id":"49","rows":[],"form":{"goods_no":"mac_0002","goods_price":"12588.00","goods_weight":1.2,"line_price":"0.00","stock_num":997,"spec_image":"","imgshow":null}}]}
     */

    private Goods detail;
    private SpecData specData;
    private int collect;

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

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
