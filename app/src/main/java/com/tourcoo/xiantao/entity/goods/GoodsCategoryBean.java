package com.tourcoo.xiantao.entity.goods;

import com.tourcoo.xiantao.entity.comment.CommentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月27日14:17
 * @Email: 971613168@qq.com
 */
public class GoodsCategoryBean implements Serializable {


    /**
     * listdata : [{"goods_max_price":"323.00","goods_min_price":"323.00","goods_name":"Mate 20 华为 HUAWEI ","goods_sales":75,"id":22,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg"},{"goods_max_price":"0.01","goods_min_price":"0.01","goods_name":"小米Mix3","goods_sales":24,"id":21,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg"},{"goods_max_price":"0.01","goods_min_price":"0.01","goods_name":"车厘子智利进口","goods_sales":13,"id":24,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg"},{"goods_max_price":"12608.00","goods_min_price":"12588.00","goods_name":"MacBook Pro 13寸","goods_sales":12,"id":23,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png"},{"goods_max_price":"16.80","goods_min_price":"16.80","goods_name":"玫瑰香葡萄","goods_sales":0,"id":27,"image":"http://ahxtao.hfcoco.top/uploads/20190427/f94ffca1a5f0ced4254548dabfc53dcd.jpg"}]
     * pagedata : {"current_page":1,"data":[{"category_id":5,"coin":0,"comment_list":[],"content":"<p style=\"text-align: center; line-height: 1.6;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/3f0233e227359137bb55152c0750f8a2.png\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/509af801726984aaa359b4bf249f5716.png\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p><p><br><\/p>","createtime":1.541402364E9,"deduct_stock_type":"20","delivery_id":22,"goods_id":22,"goods_max_price":"323.00","goods_min_price":"323.00","goods_name":"Mate 20 华为 HUAWEI ","goods_sales":75,"goods_sort":22,"goods_status":"10","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/e0d6dc822cf7632c66f7718bdd0dc2bc.jpg","is_delete":"0","label":"","origin":"","sales_actual":65,"sales_initial":10,"spec":[{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":150,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_46_58","stock_num":12,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":1,"goods_spec_id":151,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_46_59","stock_num":31,"update_time":1.556332744E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":152,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_47_58","stock_num":123,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":153,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_47_59","stock_num":321,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":154,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_46_58","stock_num":11,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":155,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_46_59","stock_num":22,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":156,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_47_58","stock_num":33,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":157,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_47_59","stock_num":44,"update_time":1.556098781E9}],"spec_type":"20","tuan":false,"tuan_list":[],"tuan_rule":{"status_text":""},"updatetime":1.556332744E9},{"category_id":5,"coin":0,"comment_list":[],"content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/f5b49f703245ef61bb3faa574f32076d.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/7d0fe135394408d4332386117c928003.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/6a87fa6af95e39d7dc227f666d7b8ff6.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/32d58a08cf92282c8f28078137c970f2.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","createtime":1.541401778E9,"deduct_stock_type":"20","delivery_id":22,"goods_id":21,"goods_max_price":"0.01","goods_min_price":"0.01","goods_name":"小米Mix3","goods_sales":24,"goods_sort":21,"goods_status":"10","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/83bf8f141969a9e3e607a768407fc7e0.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c5d85254fc17b1a1b0e2254470881e59.jpg","is_delete":"0","label":"","origin":"","sales_actual":4,"sales_initial":20,"spec":[{"create_time":1.556247149E9,"goods_id":21,"goods_no":"SN001","goods_price":"0.01","goods_sales":0,"goods_spec_id":161,"goods_weight":0.5,"line_price":"0.00","spec_image":"","spec_sku_id":"40_42","stock_num":997,"update_time":1.556247149E9},{"create_time":1.556247149E9,"goods_id":21,"goods_no":"SN002","goods_price":"0.01","goods_sales":0,"goods_spec_id":162,"goods_weight":0.5,"line_price":"0.00","spec_image":"","spec_sku_id":"40_43","stock_num":999,"update_time":1.556247149E9},{"create_time":1.556247149E9,"goods_id":21,"goods_no":"SN011","goods_price":"0.01","goods_sales":0,"goods_spec_id":163,"goods_weight":0.5,"line_price":"0.00","spec_image":"","spec_sku_id":"41_42","stock_num":999,"update_time":1.556247149E9},{"create_time":1.556247149E9,"goods_id":21,"goods_no":"SN012","goods_price":"0.01","goods_sales":0,"goods_spec_id":164,"goods_weight":0.5,"line_price":"0.00","spec_image":"","spec_sku_id":"41_43","stock_num":999,"update_time":1.556247149E9}],"spec_type":"20","tuan":false,"tuan_list":[],"tuan_rule":{"status_text":""},"updatetime":1.556098763E9},{"category_id":7,"coin":12,"comment_list":[],"content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","createtime":1.541403509E9,"deduct_stock_type":"20","delivery_id":24,"goods_id":24,"goods_max_price":"0.01","goods_min_price":"0.01","goods_name":"车厘子智利进口","goods_sales":13,"goods_sort":24,"goods_status":"10","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","is_delete":"0","label":"","origin":"","sales_actual":13,"sales_initial":0,"spec":[{"create_time":1.556334606E9,"goods_id":24,"goods_no":"CHE001","goods_price":"0.01","goods_sales":0,"goods_spec_id":172,"goods_weight":1,"line_price":"0.01","spec_image":"","spec_sku_id":"","stock_num":93,"update_time":1.556334606E9}],"spec_type":"10","tuan":true,"tuan_list":[{"avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","createtime":1.556333914E9,"deadline":1.556593114E9,"goods":{"coin":0,"comment_list":[],"deduct_stock_type_text":"","goods_name":"车厘子智利进口","goods_status_text":"","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","is_delete_text":"","label":"","spec_type_text":"","tuan":false,"tuan_list":[],"tuan_rule":false},"goods_id":24,"id":1,"nickname":"测试","rule_id":1,"status":1,"surplus":1.6,"updatetime":1.556333914E9,"user_id":3}],"tuan_rule":{"status_text":""},"updatetime":1.556334606E9},{"category_id":8,"coin":0,"comment_list":[],"content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/43b7a84d68a15d9058971526068a853a.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","createtime":1.541403061E9,"deduct_stock_type":"20","delivery_id":23,"goods_id":23,"goods_max_price":"12608.00","goods_min_price":"12588.00","goods_name":"MacBook Pro 13寸","goods_sales":12,"goods_sort":20,"goods_status":"10","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/85587c2e045b71fb4c977884a19a36cb.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg","is_delete":"0","label":"","origin":"","sales_actual":12,"sales_initial":0,"spec":[{"create_time":1.556333436E9,"goods_id":23,"goods_no":"mac_0001","goods_price":"12608.00","goods_sales":0,"goods_spec_id":170,"goods_weight":1.2,"line_price":"0.00","spec_image":"","spec_sku_id":"48","stock_num":989,"update_time":1.556333436E9},{"create_time":1.556333436E9,"goods_id":23,"goods_no":"mac_0002","goods_price":"12588.00","goods_sales":0,"goods_spec_id":171,"goods_weight":1.2,"line_price":"0.00","spec_image":"","spec_sku_id":"49","stock_num":997,"update_time":1.556333436E9}],"spec_type":"20","tuan":false,"tuan_list":[],"tuan_rule":{"status_text":""},"updatetime":1.556333436E9},{"category_id":9,"coin":0,"comment_list":[],"content":"<p><img src=\"/uploads/20190427/f94ffca1a5f0ced4254548dabfc53dcd.jpg\" data-filename=\"filename\" style=\"width: 603px;\"><br><\/p>","createtime":1.556332686E9,"deduct_stock_type":"20","delivery_id":24,"goods_id":27,"goods_max_price":"16.80","goods_min_price":"16.80","goods_name":"玫瑰香葡萄","goods_sales":0,"goods_sort":27,"goods_status":"10","images":"/uploads/20190427/f94ffca1a5f0ced4254548dabfc53dcd.jpg","is_delete":"0","label":"","origin":"","sales_actual":0,"sales_initial":0,"spec":[{"create_time":1.556332868E9,"goods_id":27,"goods_no":"","goods_price":"16.80","goods_sales":0,"goods_spec_id":168,"goods_weight":1,"line_price":"30.00","spec_image":"","spec_sku_id":"","stock_num":100,"update_time":1.556332868E9}],"spec_type":"10","tuan":false,"tuan_list":[],"tuan_rule":{"status_text":""},"updatetime":1.556332868E9}],"last_page":2,"per_page":5,"total":7}
     */

    private PagedataBean pagedata;
    private List<GoodsSimpleInfo> listdata;

    public PagedataBean getPagedata() {
        return pagedata;
    }

    public void setPagedata(PagedataBean pagedata) {
        this.pagedata = pagedata;
    }

    public List<GoodsSimpleInfo> getListdata() {
        return listdata;
    }

    public void setListdata(List<GoodsSimpleInfo> listdata) {
        this.listdata = listdata;
    }

    public static class PagedataBean implements Serializable {
        /**
         * current_page : 1
         * data : [{"category_id":5,"coin":0,"comment_list":[],"content":"<p style=\"text-align: center; line-height: 1.6;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/3f0233e227359137bb55152c0750f8a2.png\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/509af801726984aaa359b4bf249f5716.png\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p><p><br><\/p>","createtime":1.541402364E9,"deduct_stock_type":"20","delivery_id":22,"goods_id":22,"goods_max_price":"323.00","goods_min_price":"323.00","goods_name":"Mate 20 华为 HUAWEI ","goods_sales":75,"goods_sort":22,"goods_status":"10","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/e0d6dc822cf7632c66f7718bdd0dc2bc.jpg","is_delete":"0","label":"","origin":"","sales_actual":65,"sales_initial":10,"spec":[{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":150,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_46_58","stock_num":12,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":1,"goods_spec_id":151,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_46_59","stock_num":31,"update_time":1.556332744E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":152,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_47_58","stock_num":123,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":153,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_47_59","stock_num":321,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":154,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_46_58","stock_num":11,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":155,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_46_59","stock_num":22,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":156,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_47_58","stock_num":33,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":157,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_47_59","stock_num":44,"update_time":1.556098781E9}],"spec_type":"20","tuan":false,"tuan_list":[],"tuan_rule":{"status_text":""},"updatetime":1.556332744E9},{"category_id":5,"coin":0,"comment_list":[],"content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/f5b49f703245ef61bb3faa574f32076d.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/7d0fe135394408d4332386117c928003.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/6a87fa6af95e39d7dc227f666d7b8ff6.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/32d58a08cf92282c8f28078137c970f2.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","createtime":1.541401778E9,"deduct_stock_type":"20","delivery_id":22,"goods_id":21,"goods_max_price":"0.01","goods_min_price":"0.01","goods_name":"小米Mix3","goods_sales":24,"goods_sort":21,"goods_status":"10","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/83bf8f141969a9e3e607a768407fc7e0.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c5d85254fc17b1a1b0e2254470881e59.jpg","is_delete":"0","label":"","origin":"","sales_actual":4,"sales_initial":20,"spec":[{"create_time":1.556247149E9,"goods_id":21,"goods_no":"SN001","goods_price":"0.01","goods_sales":0,"goods_spec_id":161,"goods_weight":0.5,"line_price":"0.00","spec_image":"","spec_sku_id":"40_42","stock_num":997,"update_time":1.556247149E9},{"create_time":1.556247149E9,"goods_id":21,"goods_no":"SN002","goods_price":"0.01","goods_sales":0,"goods_spec_id":162,"goods_weight":0.5,"line_price":"0.00","spec_image":"","spec_sku_id":"40_43","stock_num":999,"update_time":1.556247149E9},{"create_time":1.556247149E9,"goods_id":21,"goods_no":"SN011","goods_price":"0.01","goods_sales":0,"goods_spec_id":163,"goods_weight":0.5,"line_price":"0.00","spec_image":"","spec_sku_id":"41_42","stock_num":999,"update_time":1.556247149E9},{"create_time":1.556247149E9,"goods_id":21,"goods_no":"SN012","goods_price":"0.01","goods_sales":0,"goods_spec_id":164,"goods_weight":0.5,"line_price":"0.00","spec_image":"","spec_sku_id":"41_43","stock_num":999,"update_time":1.556247149E9}],"spec_type":"20","tuan":false,"tuan_list":[],"tuan_rule":{"status_text":""},"updatetime":1.556098763E9},{"category_id":7,"coin":12,"comment_list":[],"content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","createtime":1.541403509E9,"deduct_stock_type":"20","delivery_id":24,"goods_id":24,"goods_max_price":"0.01","goods_min_price":"0.01","goods_name":"车厘子智利进口","goods_sales":13,"goods_sort":24,"goods_status":"10","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","is_delete":"0","label":"","origin":"","sales_actual":13,"sales_initial":0,"spec":[{"create_time":1.556334606E9,"goods_id":24,"goods_no":"CHE001","goods_price":"0.01","goods_sales":0,"goods_spec_id":172,"goods_weight":1,"line_price":"0.01","spec_image":"","spec_sku_id":"","stock_num":93,"update_time":1.556334606E9}],"spec_type":"10","tuan":true,"tuan_list":[{"avatar":"/uploads/20190426/324e19196a9b75b279324bb974776dbe.png","createtime":1.556333914E9,"deadline":1.556593114E9,"goods":{"coin":0,"comment_list":[],"deduct_stock_type_text":"","goods_name":"车厘子智利进口","goods_status_text":"","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","is_delete_text":"","label":"","spec_type_text":"","tuan":false,"tuan_list":[],"tuan_rule":false},"goods_id":24,"id":1,"nickname":"测试","rule_id":1,"status":1,"surplus":1.6,"updatetime":1.556333914E9,"user_id":3}],"tuan_rule":{"status_text":""},"updatetime":1.556334606E9},{"category_id":8,"coin":0,"comment_list":[],"content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/43b7a84d68a15d9058971526068a853a.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","createtime":1.541403061E9,"deduct_stock_type":"20","delivery_id":23,"goods_id":23,"goods_max_price":"12608.00","goods_min_price":"12588.00","goods_name":"MacBook Pro 13寸","goods_sales":12,"goods_sort":20,"goods_status":"10","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/85587c2e045b71fb4c977884a19a36cb.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg","is_delete":"0","label":"","origin":"","sales_actual":12,"sales_initial":0,"spec":[{"create_time":1.556333436E9,"goods_id":23,"goods_no":"mac_0001","goods_price":"12608.00","goods_sales":0,"goods_spec_id":170,"goods_weight":1.2,"line_price":"0.00","spec_image":"","spec_sku_id":"48","stock_num":989,"update_time":1.556333436E9},{"create_time":1.556333436E9,"goods_id":23,"goods_no":"mac_0002","goods_price":"12588.00","goods_sales":0,"goods_spec_id":171,"goods_weight":1.2,"line_price":"0.00","spec_image":"","spec_sku_id":"49","stock_num":997,"update_time":1.556333436E9}],"spec_type":"20","tuan":false,"tuan_list":[],"tuan_rule":{"status_text":""},"updatetime":1.556333436E9},{"category_id":9,"coin":0,"comment_list":[],"content":"<p><img src=\"/uploads/20190427/f94ffca1a5f0ced4254548dabfc53dcd.jpg\" data-filename=\"filename\" style=\"width: 603px;\"><br><\/p>","createtime":1.556332686E9,"deduct_stock_type":"20","delivery_id":24,"goods_id":27,"goods_max_price":"16.80","goods_min_price":"16.80","goods_name":"玫瑰香葡萄","goods_sales":0,"goods_sort":27,"goods_status":"10","images":"/uploads/20190427/f94ffca1a5f0ced4254548dabfc53dcd.jpg","is_delete":"0","label":"","origin":"","sales_actual":0,"sales_initial":0,"spec":[{"create_time":1.556332868E9,"goods_id":27,"goods_no":"","goods_price":"16.80","goods_sales":0,"goods_spec_id":168,"goods_weight":1,"line_price":"30.00","spec_image":"","spec_sku_id":"","stock_num":100,"update_time":1.556332868E9}],"spec_type":"10","tuan":false,"tuan_list":[],"tuan_rule":{"status_text":""},"updatetime":1.556332868E9}]
         * last_page : 2
         * per_page : 5
         * total : 7
         */

        private int current_page;
        private int last_page;
        private int per_page;
        private int total;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable{
            /**
             * category_id : 5
             * coin : 0
             * comment_list : []
             * content : <p style="text-align: center; line-height: 1.6;"><img src="https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/3f0233e227359137bb55152c0750f8a2.png" data-filename="filename" style="width: 100%;"><img src="https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/509af801726984aaa359b4bf249f5716.png" data-filename="filename" style="width: 100%;"><br></p><p><br></p>
             * createtime : 1.541402364E9
             * deduct_stock_type : 20
             * delivery_id : 22
             * goods_id : 22
             * goods_max_price : 323.00
             * goods_min_price : 323.00
             * goods_name : Mate 20 华为 HUAWEI
             * goods_sales : 75
             * goods_sort : 22
             * goods_status : 10
             * images : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/4fffea1c27bfb8df655a39114bb05814.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/e0d6dc822cf7632c66f7718bdd0dc2bc.jpg
             * is_delete : 0
             * label :
             * origin :
             * sales_actual : 65
             * sales_initial : 10
             * spec : [{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":150,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_46_58","stock_num":12,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":1,"goods_spec_id":151,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_46_59","stock_num":31,"update_time":1.556332744E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":152,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_47_58","stock_num":123,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":153,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"44_47_59","stock_num":321,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":154,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_46_58","stock_num":11,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":155,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_46_59","stock_num":22,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":156,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_47_58","stock_num":33,"update_time":1.556098781E9},{"create_time":1.556098781E9,"goods_id":22,"goods_no":"321321","goods_price":"323.00","goods_sales":0,"goods_spec_id":157,"goods_weight":321,"line_price":"321.00","spec_image":"","spec_sku_id":"45_47_59","stock_num":44,"update_time":1.556098781E9}]
             * spec_type : 20
             * tuan : false
             * tuan_list : []
             * tuan_rule : {"status_text":""}
             * updatetime : 1.556332744E9
             */

            private int category_id;
            private int coin;
            private String content;
            private double createtime;
            private String deduct_stock_type;
            private int delivery_id;
            private int goods_id;
            private String goods_max_price;
            private String goods_min_price;
            private String goods_name;
            private int goods_sales;
            private int goods_sort;
            private String goods_status;
            private String images;
            private String is_delete;
            private String label;
            private String origin;
            private int sales_actual;
            private int sales_initial;
            private String spec_type;
            private boolean tuan;
            private TuanRuleBean tuan_rule;
            private double updatetime;
            private List<CommentEntity> comment_list;
            private List<SpecBean> spec;
            private List<?> tuan_list;

            public int getCategory_id() {
                return category_id;
            }

            public void setCategory_id(int category_id) {
                this.category_id = category_id;
            }

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public double getCreatetime() {
                return createtime;
            }

            public void setCreatetime(double createtime) {
                this.createtime = createtime;
            }

            public String getDeduct_stock_type() {
                return deduct_stock_type;
            }

            public void setDeduct_stock_type(String deduct_stock_type) {
                this.deduct_stock_type = deduct_stock_type;
            }

            public int getDelivery_id() {
                return delivery_id;
            }

            public void setDelivery_id(int delivery_id) {
                this.delivery_id = delivery_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_max_price() {
                return goods_max_price;
            }

            public void setGoods_max_price(String goods_max_price) {
                this.goods_max_price = goods_max_price;
            }

            public String getGoods_min_price() {
                return goods_min_price;
            }

            public void setGoods_min_price(String goods_min_price) {
                this.goods_min_price = goods_min_price;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getGoods_sales() {
                return goods_sales;
            }

            public void setGoods_sales(int goods_sales) {
                this.goods_sales = goods_sales;
            }

            public int getGoods_sort() {
                return goods_sort;
            }

            public void setGoods_sort(int goods_sort) {
                this.goods_sort = goods_sort;
            }

            public String getGoods_status() {
                return goods_status;
            }

            public void setGoods_status(String goods_status) {
                this.goods_status = goods_status;
            }

            public String getImages() {
                return images;
            }

            public void setImages(String images) {
                this.images = images;
            }

            public String getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(String is_delete) {
                this.is_delete = is_delete;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public int getSales_actual() {
                return sales_actual;
            }

            public void setSales_actual(int sales_actual) {
                this.sales_actual = sales_actual;
            }

            public int getSales_initial() {
                return sales_initial;
            }

            public void setSales_initial(int sales_initial) {
                this.sales_initial = sales_initial;
            }

            public String getSpec_type() {
                return spec_type;
            }

            public void setSpec_type(String spec_type) {
                this.spec_type = spec_type;
            }

            public boolean isTuan() {
                return tuan;
            }

            public void setTuan(boolean tuan) {
                this.tuan = tuan;
            }

            public TuanRuleBean getTuan_rule() {
                return tuan_rule;
            }

            public void setTuan_rule(TuanRuleBean tuan_rule) {
                this.tuan_rule = tuan_rule;
            }

            public double getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(double updatetime) {
                this.updatetime = updatetime;
            }

            public List<?> getComment_list() {
                return comment_list;
            }

            public void setComment_list(List<CommentEntity> comment_list) {
                this.comment_list = comment_list;
            }

            public List<SpecBean> getSpec() {
                return spec;
            }

            public void setSpec(List<SpecBean> spec) {
                this.spec = spec;
            }

            public List<?> getTuan_list() {
                return tuan_list;
            }

            public void setTuan_list(List<?> tuan_list) {
                this.tuan_list = tuan_list;
            }

            public static class TuanRuleBean  implements Serializable  {
                /**
                 * status_text :
                 */

                private String status_text;

                public String getStatus_text() {
                    return status_text;
                }

                public void setStatus_text(String status_text) {
                    this.status_text = status_text;
                }
            }

            public static class SpecBean implements Serializable {
                /**
                 * create_time : 1.556098781E9
                 * goods_id : 22
                 * goods_no : 321321
                 * goods_price : 323.00
                 * goods_sales : 0
                 * goods_spec_id : 150
                 * goods_weight : 321
                 * line_price : 321.00
                 * spec_image :
                 * spec_sku_id : 44_46_58
                 * stock_num : 12
                 * update_time : 1.556098781E9
                 */

                private double create_time;
                private int goods_id;
                private String goods_no;
                private String goods_price;
                private int goods_sales;
                private int goods_spec_id;
                private int goods_weight;
                private String line_price;
                private String spec_image;
                private String spec_sku_id;
                private int stock_num;
                private double update_time;

                public double getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(double create_time) {
                    this.create_time = create_time;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_no() {
                    return goods_no;
                }

                public void setGoods_no(String goods_no) {
                    this.goods_no = goods_no;
                }

                public String getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(String goods_price) {
                    this.goods_price = goods_price;
                }

                public int getGoods_sales() {
                    return goods_sales;
                }

                public void setGoods_sales(int goods_sales) {
                    this.goods_sales = goods_sales;
                }

                public int getGoods_spec_id() {
                    return goods_spec_id;
                }

                public void setGoods_spec_id(int goods_spec_id) {
                    this.goods_spec_id = goods_spec_id;
                }

                public int getGoods_weight() {
                    return goods_weight;
                }

                public void setGoods_weight(int goods_weight) {
                    this.goods_weight = goods_weight;
                }

                public String getLine_price() {
                    return line_price;
                }

                public void setLine_price(String line_price) {
                    this.line_price = line_price;
                }

                public String getSpec_image() {
                    return spec_image;
                }

                public void setSpec_image(String spec_image) {
                    this.spec_image = spec_image;
                }

                public String getSpec_sku_id() {
                    return spec_sku_id;
                }

                public void setSpec_sku_id(String spec_sku_id) {
                    this.spec_sku_id = spec_sku_id;
                }

                public int getStock_num() {
                    return stock_num;
                }

                public void setStock_num(int stock_num) {
                    this.stock_num = stock_num;
                }

                public double getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(double update_time) {
                    this.update_time = update_time;
                }
            }
        }
    }

    public static class GoodsSimpleInfo implements Serializable {
        /**
         * goods_max_price : 323.00
         * goods_min_price : 323.00
         * goods_name : Mate 20 华为 HUAWEI
         * goods_sales : 75
         * id : 22
         * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/b044b7bcd4930202fcd96d6b50453894.jpg
         */
        private String label;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        private String goods_max_price;
        private String goods_min_price;
        private String goods_name;
        private int goods_sales;
        private int id;
        private String image;

        public String getGoods_max_price() {
            return goods_max_price;
        }

        public void setGoods_max_price(String goods_max_price) {
            this.goods_max_price = goods_max_price;
        }

        public String getGoods_min_price() {
            return goods_min_price;
        }

        public void setGoods_min_price(String goods_min_price) {
            this.goods_min_price = goods_min_price;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getGoods_sales() {
            return goods_sales;
        }

        public void setGoods_sales(int goods_sales) {
            this.goods_sales = goods_sales;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
