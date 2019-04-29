package com.tourcoo.xiantao.entity.order;

import com.tourcoo.xiantao.entity.goods.Goods;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :订单实体
 * @company :途酷科技
 * @date 2019年04月27日19:23
 * @Email: 971613168@qq.com
 */
public class OrderEntity {


    /**
     * total : 20
     * per_page : 10
     * current_page : 1
     * last_page : 2
     * data : [{"id":61,"order_no":"2019042649999851","total_price":"0.01","pay_price":"10.01","pay_status":"10","pay_time":0,"express_price":"10.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556253153,"updatetime":1556253153,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":66,"goods_id":21,"goods_name":"小米Mix3","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/83bf8f141969a9e3e607a768407fc7e0.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c5d85254fc17b1a1b0e2254470881e59.jpg","deduct_stock_type":"20","spec_type":"20","spec_sku_id":"40_42","goods_spec_id":161,"goods_attr":"颜色:黑色; 版本:6+128; ","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/f5b49f703245ef61bb3faa574f32076d.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/7d0fe135394408d4332386117c928003.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/6a87fa6af95e39d7dc227f666d7b8ff6.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/32d58a08cf92282c8f28078137c970f2.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"SN001","goods_price":"0.01","line_price":"0.00","goods_weight":0.5,"total_num":1,"total_price":"0.01","order_id":61,"user_id":2,"createtime":1556253153}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-26 12:32:33"},{"id":60,"order_no":"2019042652549899","total_price":"0.01","pay_price":"10.01","pay_status":"10","pay_time":0,"express_price":"10.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556247348,"updatetime":1556247348,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":65,"goods_id":21,"goods_name":"小米Mix3","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/83bf8f141969a9e3e607a768407fc7e0.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c5d85254fc17b1a1b0e2254470881e59.jpg","deduct_stock_type":"20","spec_type":"20","spec_sku_id":"40_42","goods_spec_id":161,"goods_attr":"颜色:黑色; 版本:6+128; ","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/f5b49f703245ef61bb3faa574f32076d.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/7d0fe135394408d4332386117c928003.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/6a87fa6af95e39d7dc227f666d7b8ff6.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/32d58a08cf92282c8f28078137c970f2.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"SN001","goods_price":"0.01","line_price":"0.00","goods_weight":0.5,"total_num":1,"total_price":"0.01","order_id":60,"user_id":2,"createtime":1556247348}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-26 10:55:48"},{"id":59,"order_no":"2019042649100539","total_price":"12608.00","pay_price":"12628.00","pay_status":"10","pay_time":0,"express_price":"20.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556247041,"updatetime":1556247041,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":64,"goods_id":23,"goods_name":"MacBook Pro 13寸","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/85587c2e045b71fb4c977884a19a36cb.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg","deduct_stock_type":"20","spec_type":"20","spec_sku_id":"48","goods_spec_id":158,"goods_attr":"颜色:天空灰; ","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/43b7a84d68a15d9058971526068a853a.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"mac_0001","goods_price":"12608.00","line_price":"0.00","goods_weight":1.2,"total_num":1,"total_price":"12608.00","order_id":59,"user_id":2,"createtime":1556247041}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-26 10:50:41"},{"id":58,"order_no":"2019042648554948","total_price":"3299.00","pay_price":"3309.00","pay_status":"10","pay_time":0,"express_price":"10.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556246416,"updatetime":1556246416,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":63,"goods_id":21,"goods_name":"小米Mix3","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/83bf8f141969a9e3e607a768407fc7e0.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c5d85254fc17b1a1b0e2254470881e59.jpg","deduct_stock_type":"20","spec_type":"20","spec_sku_id":"40_42","goods_spec_id":146,"goods_attr":"颜色:黑色; 版本:6+128; ","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/f5b49f703245ef61bb3faa574f32076d.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/7d0fe135394408d4332386117c928003.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/6a87fa6af95e39d7dc227f666d7b8ff6.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/32d58a08cf92282c8f28078137c970f2.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"SN001","goods_price":"3299.00","line_price":"0.00","goods_weight":0.5,"total_num":1,"total_price":"3299.00","order_id":58,"user_id":2,"createtime":1556246416}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-26 10:40:16"},{"id":57,"order_no":"2019042697559855","total_price":"3299.00","pay_price":"3309.00","pay_status":"10","pay_time":0,"express_price":"10.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556246410,"updatetime":1556246410,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":62,"goods_id":21,"goods_name":"小米Mix3","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/83bf8f141969a9e3e607a768407fc7e0.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c5d85254fc17b1a1b0e2254470881e59.jpg","deduct_stock_type":"20","spec_type":"20","spec_sku_id":"40_42","goods_spec_id":146,"goods_attr":"颜色:黑色; 版本:6+128; ","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/f5b49f703245ef61bb3faa574f32076d.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/7d0fe135394408d4332386117c928003.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/6a87fa6af95e39d7dc227f666d7b8ff6.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/32d58a08cf92282c8f28078137c970f2.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"SN001","goods_price":"3299.00","line_price":"0.00","goods_weight":0.5,"total_num":1,"total_price":"3299.00","order_id":57,"user_id":2,"createtime":1556246410}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-26 10:40:10"},{"id":56,"order_no":"2019042698100505","total_price":"3299.00","pay_price":"3309.00","pay_status":"10","pay_time":0,"express_price":"10.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556246251,"updatetime":1556246251,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":61,"goods_id":21,"goods_name":"小米Mix3","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/83bf8f141969a9e3e607a768407fc7e0.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c5d85254fc17b1a1b0e2254470881e59.jpg","deduct_stock_type":"20","spec_type":"20","spec_sku_id":"40_42","goods_spec_id":146,"goods_attr":"颜色:黑色; 版本:6+128; ","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/f5b49f703245ef61bb3faa574f32076d.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/7d0fe135394408d4332386117c928003.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/6a87fa6af95e39d7dc227f666d7b8ff6.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/32d58a08cf92282c8f28078137c970f2.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"SN001","goods_price":"3299.00","line_price":"0.00","goods_weight":0.5,"total_num":1,"total_price":"3299.00","order_id":56,"user_id":2,"createtime":1556246251}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-26 10:37:31"},{"id":55,"order_no":"2019042654565655","total_price":"258.00","pay_price":"268.00","pay_status":"10","pay_time":0,"express_price":"10.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556246102,"updatetime":1556246102,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":60,"goods_id":24,"goods_name":"车厘子智利进口","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","deduct_stock_type":"20","spec_type":"10","spec_sku_id":"","goods_spec_id":160,"goods_attr":"","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"CHE001","goods_price":"258.00","line_price":"299.00","goods_weight":1,"total_num":1,"total_price":"258.00","order_id":55,"user_id":2,"createtime":1556246102}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-26 10:35:02"},{"id":54,"order_no":"2019042698101515","total_price":"3299.00","pay_price":"3309.00","pay_status":"10","pay_time":0,"express_price":"10.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556245323,"updatetime":1556245323,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":59,"goods_id":21,"goods_name":"小米Mix3","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/83bf8f141969a9e3e607a768407fc7e0.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c5d85254fc17b1a1b0e2254470881e59.jpg","deduct_stock_type":"20","spec_type":"20","spec_sku_id":"40_42","goods_spec_id":146,"goods_attr":"颜色:黑色; 版本:6+128; ","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/f5b49f703245ef61bb3faa574f32076d.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/7d0fe135394408d4332386117c928003.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/6a87fa6af95e39d7dc227f666d7b8ff6.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/32d58a08cf92282c8f28078137c970f2.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"SN001","goods_price":"3299.00","line_price":"0.00","goods_weight":0.5,"total_num":1,"total_price":"3299.00","order_id":54,"user_id":2,"createtime":1556245323}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-26 10:22:03"},{"id":50,"order_no":"2019042599975597","total_price":"12608.00","pay_price":"12628.00","pay_status":"10","pay_time":0,"express_price":"20.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556196780,"updatetime":1556196780,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":55,"goods_id":23,"goods_name":"MacBook Pro 13寸","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/85587c2e045b71fb4c977884a19a36cb.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg","deduct_stock_type":"20","spec_type":"20","spec_sku_id":"48","goods_spec_id":158,"goods_attr":"颜色:天空灰; ","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/43b7a84d68a15d9058971526068a853a.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"mac_0001","goods_price":"12608.00","line_price":"0.00","goods_weight":1.2,"total_num":1,"total_price":"12608.00","order_id":50,"user_id":2,"createtime":1556196780}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-25 20:53:00"},{"id":49,"order_no":"2019042510157529","total_price":"258.00","pay_price":"268.00","pay_status":"10","pay_time":0,"express_price":"10.00","express_company":"","express_no":"","freight_status":"10","freight_time":0,"receipt_status":"10","receipt_time":0,"order_status":"10","transaction_id":"","user_id":2,"createtime":1556196590,"updatetime":1556196590,"coin":0,"coin_status":0,"tuan":0,"comment_status":"10","pay_type":"cash","remark":null,"return_status":"10","goods":[{"id":54,"goods_id":24,"goods_name":"车厘子智利进口","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/a460ffdbd534b10cdf40487189ccb6b7.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c83a0019dfa7a768037e98f02b70efd5.jpg","deduct_stock_type":"20","spec_type":"10","spec_sku_id":"","goods_spec_id":160,"goods_attr":"","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/611619c7dac06511213278a469a1efea.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"CHE001","goods_price":"258.00","line_price":"299.00","goods_weight":1,"total_num":1,"total_price":"258.00","order_id":49,"user_id":2,"createtime":1556196590}],"pay_status_text":"Pay_status 10","pay_time_text":"1970-01-01 08:00:00","freight_status_text":"Freight_status 10","freight_time_text":"1970-01-01 08:00:00","receipt_status_text":"Receipt_status 10","receipt_time_text":"1970-01-01 08:00:00","order_status_text":"Order_status 10","creattime_text":"2019-04-25 20:49:50"}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<OrderInfo> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

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

    public List<OrderInfo> getData() {
        return data;
    }

    public void setData(List<OrderInfo> data) {
        this.data = data;
    }



    public static class OrderInfo {
        /**
         * id : 61
         * order_no : 2019042649999851
         * total_price : 0.01
         * pay_price : 10.01
         * pay_status : 10
         * pay_time : 0
         * express_price : 10.00
         * express_company :
         * express_no :
         * freight_status : 10
         * freight_time : 0
         * receipt_status : 10
         * receipt_time : 0
         * order_status : 10
         * transaction_id :
         * user_id : 2
         * createtime : 1556253153
         * updatetime : 1556253153
         * coin : 0
         * coin_status : 0
         * tuan : 0
         * comment_status : 10
         * pay_type : cash
         * remark : null
         * return_status : 10
         * goods : [{"id":66,"goods_id":21,"goods_name":"小米Mix3","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/ffc4440df18661948b9c2d4dd4ae419b.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/83bf8f141969a9e3e607a768407fc7e0.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/c5d85254fc17b1a1b0e2254470881e59.jpg","deduct_stock_type":"20","spec_type":"20","spec_sku_id":"40_42","goods_spec_id":161,"goods_attr":"颜色:黑色; 版本:6+128; ","content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/f5b49f703245ef61bb3faa574f32076d.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/7d0fe135394408d4332386117c928003.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/6a87fa6af95e39d7dc227f666d7b8ff6.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/32d58a08cf92282c8f28078137c970f2.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","goods_no":"SN001","goods_price":"0.01","line_price":"0.00","goods_weight":0.5,"total_num":1,"total_price":"0.01","order_id":61,"user_id":2,"createtime":1556253153}]
         * pay_status_text : Pay_status 10
         * pay_time_text : 1970-01-01 08:00:00
         * freight_status_text : Freight_status 10
         * freight_time_text : 1970-01-01 08:00:00
         * receipt_status_text : Receipt_status 10
         * receipt_time_text : 1970-01-01 08:00:00
         * order_status_text : Order_status 10
         * creattime_text : 2019-04-26 12:32:33
         */

        private int id;
        private String order_no;
        private String total_price;
        private String pay_price;
        private int pay_status;
        private int pay_time;
        private String express_price;
        private String express_company;
        private String express_no;
        private int freight_status;
        private int freight_time;
        private int receipt_status;
        private int receipt_time;
        private int order_status;
        private String transaction_id;
        private int user_id;
        private long createtime;
        private int updatetime;
        private int coin;
        private int coin_status;
        private int tuan;
        private int comment_status;
        private String pay_type;
        private Object remark;
        private int return_status;
        private String pay_status_text;
        private String pay_time_text;
        private String freight_status_text;
        private String freight_time_text;
        private String receipt_status_text;
        private String receipt_time_text;
        private String order_status_text;
        private String creattime_text;
        private List<Goods> goods;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getPay_price() {
            return pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getPay_time() {
            return pay_time;
        }

        public void setPay_time(int pay_time) {
            this.pay_time = pay_time;
        }

        public String getExpress_price() {
            return express_price;
        }

        public void setExpress_price(String express_price) {
            this.express_price = express_price;
        }

        public String getExpress_company() {
            return express_company;
        }

        public void setExpress_company(String express_company) {
            this.express_company = express_company;
        }

        public String getExpress_no() {
            return express_no;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public int getFreight_status() {
            return freight_status;
        }

        public void setFreight_status(int freight_status) {
            this.freight_status = freight_status;
        }

        public int getFreight_time() {
            return freight_time;
        }

        public void setFreight_time(int freight_time) {
            this.freight_time = freight_time;
        }

        public int getReceipt_status() {
            return receipt_status;
        }

        public void setReceipt_status(int receipt_status) {
            this.receipt_status = receipt_status;
        }

        public int getReceipt_time() {
            return receipt_time;
        }

        public void setReceipt_time(int receipt_time) {
            this.receipt_time = receipt_time;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public int getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(int updatetime) {
            this.updatetime = updatetime;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public int getCoin_status() {
            return coin_status;
        }

        public void setCoin_status(int coin_status) {
            this.coin_status = coin_status;
        }

        public int getTuan() {
            return tuan;
        }

        public void setTuan(int tuan) {
            this.tuan = tuan;
        }

        public int getComment_status() {
            return comment_status;
        }

        public void setComment_status(int comment_status) {
            this.comment_status = comment_status;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getReturn_status() {
            return return_status;
        }

        public void setReturn_status(int return_status) {
            this.return_status = return_status;
        }

        public String getPay_status_text() {
            return pay_status_text;
        }

        public void setPay_status_text(String pay_status_text) {
            this.pay_status_text = pay_status_text;
        }

        public String getPay_time_text() {
            return pay_time_text;
        }

        public void setPay_time_text(String pay_time_text) {
            this.pay_time_text = pay_time_text;
        }

        public String getFreight_status_text() {
            return freight_status_text;
        }

        public void setFreight_status_text(String freight_status_text) {
            this.freight_status_text = freight_status_text;
        }

        public String getFreight_time_text() {
            return freight_time_text;
        }

        public void setFreight_time_text(String freight_time_text) {
            this.freight_time_text = freight_time_text;
        }

        public String getReceipt_status_text() {
            return receipt_status_text;
        }

        public void setReceipt_status_text(String receipt_status_text) {
            this.receipt_status_text = receipt_status_text;
        }

        public String getReceipt_time_text() {
            return receipt_time_text;
        }

        public void setReceipt_time_text(String receipt_time_text) {
            this.receipt_time_text = receipt_time_text;
        }

        public String getOrder_status_text() {
            return order_status_text;
        }

        public void setOrder_status_text(String order_status_text) {
            this.order_status_text = order_status_text;
        }

        public String getCreattime_text() {
            return creattime_text;
        }

        public void setCreattime_text(String creattime_text) {
            this.creattime_text = creattime_text;
        }

        public List<Goods> getGoods() {
            return goods;
        }

        public void setGoods(List<Goods> goods) {
            this.goods = goods;
        }


    }
}
