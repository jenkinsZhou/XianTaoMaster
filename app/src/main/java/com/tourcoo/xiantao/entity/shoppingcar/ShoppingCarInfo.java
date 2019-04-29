package com.tourcoo.xiantao.entity.shoppingcar;

import com.tourcoo.xiantao.entity.address.AddressEntity;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :
 * @company :途酷科技
 * @date 2019年04月26日17:53
 * @Email: 971613168@qq.com
 */
public class ShoppingCarInfo {


    /**
     * address : {"Area":{"city":"合肥市","province":"安徽省","region":"蜀山区"},"address_id":27,"city_id":1047,"createtime":1.556091048E9,"detail":"经开区高速翡翠湖畔1号楼","isdefault":"1","name":"周健","phone":"18256070563","province_id":1046,"region_id":1050,"updatetime":1.556091048E9,"user_id":2}
     * error_msg :
     * exist_address : {"Area":{"city":"合肥市","province":"安徽省","region":"蜀山区"},"address_id":27,"city_id":1047,"createtime":1.556091048E9,"detail":"经开区高速翡翠湖畔1号楼","isdefault":"1","name":"周健","phone":"18256070563","province_id":1046,"region_id":1050,"updatetime":1.556091048E9,"user_id":2}
     * express_price : 680
     * goods_list : [{"category":{"createtime":1.541402921E9,"id":8,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg","name":"笔记本","pid":4,"updatetime":1.541403316E9,"weigh":8},"category_id":8,"content":"<p><img src=\"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/43b7a84d68a15d9058971526068a853a.jpg\" data-filename=\"filename\" style=\"width: 100%;\"><br><\/p>","createtime":1.541403061E9,"deduct_stock_type":"20","delivery_id":23,"express_price":"680.00","freight":{"createtime":1.540363605E9,"id":23,"method":"10","method_text":"Method 10","name":"电脑","rule":[{"additional":1,"additional_fee":"10.00","create_time":1.540363605E9,"first":1,"first_fee":"20.00","litestore_freight_id":23,"region":"2,20,38,61,76,84,104,124,150,168,180,197,208,221,232,244,250,264,271,278,290,304,319,337,352,362,372,376,389,398,407,422,430,442,449,462,467,481,492,500,508,515,522,530,537,545,553,558,566,574,581,586,597,607,614,619,627,634,640,646,656,675,692,702,711,720,730,748,759,764,775,782,793,802,821,833,842,853,861,871,880,887,896,906,913,920,927,934,948,960,972,980,986,993,1003,1010,1015,1025,1035,1047,1057,1066,1074,1081,1088,1093,1098,1110,1118,1127,1136,1142,1150,1155,1160,1169,1183,1190,1196,1209,1222,1234,1245,1253,1264,1274,1279,1285,1299,1302,1306,1325,1339,1350,1362,1376,1387,1399,1408,1415,1421,1434,1447,1459,1466,1471,1476,1479,1492,1504,1513,1522,1533,1546,1556,1572,1583,1593,1599,1612,1623,1630,1637,1643,1650,1664,1674,1685,1696,1707,1710,1724,1731,1740,1754,1764,1768,1774,1782,1791,1802,1809,1813,1822,1828,1838,1848,1854,1867,1880,1890,1900,1905,1912,1924,1936,1949,1955,1965,1977,1988,1999,2003,2011,2017,2025,2035,2041,2050,2056,2065,2070,2077,2082,2091,2123,2146,2150,2156,2163,2177,2189,2207,2215,2220,2225,2230,2236,2245,2258,2264,2276,2283,2292,2297,2302,2306,2324,2363,2368,2388,2395,2401,2409,2416,2426,2434,2440,2446,2458,2468,2475,2486,2493,2501,2510,2516,2521,2535,2554,2573,2584,2589,2604,2611,2620,2631,2640,2657,2671,2686,2696,2706,2712,2724,2730,2741,2750,2761,2775,2784,2788,2801,2807,2812,2817,2826,2845,2857,2870,2882,2890,2899,2913,2918,2931,2946,2958,2972,2984,2997,3008,3016,3023,3032,3036,3039,3045,3053,3058,3065,3073,3081,3090,3098,3108,3117,3127,3135,3142,3147,3152,3158,3165,3172,3179,3186,3190,3196,3202,3207,3216,3221,3225,3229,3237,3242,3252,3262,3267,3280,3289,3301,3309,3317,3326,3339,3378,3386,3416,3454,3458,3461,3491,3504,3518,3532,3551,3578,3592,3613,3632,3666,3683,3697,3704,3711,3717,3722,3728,3739,3745,3747","region_data":["2","20","38","61","76","84","104","124","150","168","180","197","208","221","232","244","250","264","271","278","290","304","319","337","352","362","372","376","389","398","407","422","430","442","449","462","467","481","492","500","508","515","522","530","537","545","553","558","566","574","581","586","597","607","614","619","627","634","640","646","656","675","692","702","711","720","730","748","759","764","775","782","793","802","821","833","842","853","861","871","880","887","896","906","913","920","927","934","948","960","972","980","986","993","1003","1010","1015","1025","1035","1047","1057","1066","1074","1081","1088","1093","1098","1110","1118","1127","1136","1142","1150","1155","1160","1169","1183","1190","1196","1209","1222","1234","1245","1253","1264","1274","1279","1285","1299","1302","1306","1325","1339","1350","1362","1376","1387","1399","1408","1415","1421","1434","1447","1459","1466","1471","1476","1479","1492","1504","1513","1522","1533","1546","1556","1572","1583","1593","1599","1612","1623","1630","1637","1643","1650","1664","1674","1685","1696","1707","1710","1724","1731","1740","1754","1764","1768","1774","1782","1791","1802","1809","1813","1822","1828","1838","1848","1854","1867","1880","1890","1900","1905","1912","1924","1936","1949","1955","1965","1977","1988","1999","2003","2011","2017","2025","2035","2041","2050","2056","2065","2070","2077","2082","2091","2123","2146","2150","2156","2163","2177","2189","2207","2215","2220","2225","2230","2236","2245","2258","2264","2276","2283","2292","2297","2302","2306","2324","2363","2368","2388","2395","2401","2409","2416","2426","2434","2440","2446","2458","2468","2475","2486","2493","2501","2510","2516","2521","2535","2554","2573","2584","2589","2604","2611","2620","2631","2640","2657","2671","2686","2696","2706","2712","2724","2730","2741","2750","2761","2775","2784","2788","2801","2807","2812","2817","2826","2845","2857","2870","2882","2890","2899","2913","2918","2931","2946","2958","2972","2984","2997","3008","3016","3023","3032","3036","3039","3045","3053","3058","3065","3073","3081","3090","3098","3108","3117","3127","3135","3142","3147","3152","3158","3165","3172","3179","3186","3190","3196","3202","3207","3216","3221","3225","3229","3237","3242","3252","3262","3267","3280","3289","3301","3309","3317","3326","3339","3378","3386","3416","3454","3458","3461","3491","3504","3518","3532","3551","3578","3592","3613","3632","3666","3683","3697","3704","3711","3717","3722","3728","3739","3745","3747"],"rule_id":18,"weigh":0}],"updatetime":1.540363605E9,"weigh":23},"goods_id":23,"goods_name":"MacBook Pro 13寸","goods_price":"12608.00","goods_sales":12,"goods_sku":{"create_time":1.556098793E9,"goods_attr":"颜色:天空灰; ","goods_id":23,"goods_no":"mac_0001","goods_price":"12608.00","goods_sales":0,"goods_spec_id":158,"goods_weight":1.2,"img_show":"","line_price":"0.00","spec_image":"","spec_sku_id":"48","stock_num":989,"update_time":1.556098793E9},"goods_sku_id":"48","goods_sort":23,"goods_status":"10","goods_total_weight":"80.4","image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png","images":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/85587c2e045b71fb4c977884a19a36cb.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg","is_delete":"0","label":"","origin":"","sales_actual":12,"sales_initial":0,"show_error":0,"spec":[{"create_time":1.556098793E9,"goods_attr":"颜色:天空灰; ","goods_id":23,"goods_no":"mac_0001","goods_price":"12608.00","goods_sales":0,"goods_spec_id":158,"goods_weight":1.2,"img_show":"","line_price":"0.00","spec_image":"","spec_sku_id":"48","stock_num":989,"update_time":1.556098793E9},{"create_time":1.556098793E9,"goods_id":23,"goods_no":"mac_0002","goods_price":"12588.00","goods_sales":0,"goods_spec_id":159,"goods_weight":1.2,"line_price":"0.00","spec_image":"","spec_sku_id":"49","stock_num":997,"update_time":1.556098793E9}],"specRel":[{"createtime":1.541403005E9,"id":48,"pivot":{"create_time":1.556098793E9,"goods_id":23,"id":140,"spec_id":20,"spec_value_id":48},"spec":{"createtime":1.541401442E9,"id":20,"spec_name":"颜色"},"spec_id":20,"spec_value":"天空灰"},{"createtime":1.541403011E9,"id":49,"pivot":{"create_time":1.556098793E9,"goods_id":23,"id":141,"spec_id":20,"spec_value_id":49},"spec_id":20,"spec_value":"银色"}],"spec_rel":[{"createtime":1.541403005E9,"id":48,"pivot":{"create_time":1.556098793E9,"goods_id":23,"id":140,"spec_id":20,"spec_value_id":48},"spec":{"createtime":1.541401442E9,"id":20,"spec_name":"颜色"},"spec_id":20,"spec_value":"天空灰"},{"createtime":1.541403011E9,"id":49,"pivot":{"create_time":1.556098793E9,"goods_id":23,"id":141,"spec_id":20,"spec_value_id":49},"spec":{"createtime":1.541401442E9,"id":20,"spec_name":"颜色"},"spec_id":20,"spec_value":"银色"}],"spec_type":"20","total_num":67,"total_price":"844736.00","updatetime":1.556098793E9}]
     * has_error : false
     * intra_region : true
     * order_pay_price : 845416.00
     * order_total_num : 67
     * order_total_price : 844736
     */

    private AddressEntity address;
    private String error_msg;
    private AddressEntity exist_address;
    private int express_price;
    private boolean has_error;
    private boolean intra_region;
    private String order_pay_price;
    private int order_total_num;
    private int order_total_price;
    private List<GoodsListBean> goods_list;

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public AddressEntity getExist_address() {
        return exist_address;
    }

    public void setExist_address(AddressEntity exist_address) {
        this.exist_address = exist_address;
    }

    public int getExpress_price() {
        return express_price;
    }

    public void setExpress_price(int express_price) {
        this.express_price = express_price;
    }

    public boolean isHas_error() {
        return has_error;
    }

    public void setHas_error(boolean has_error) {
        this.has_error = has_error;
    }

    public boolean isIntra_region() {
        return intra_region;
    }

    public void setIntra_region(boolean intra_region) {
        this.intra_region = intra_region;
    }

    public String getOrder_pay_price() {
        return order_pay_price;
    }

    public void setOrder_pay_price(String order_pay_price) {
        this.order_pay_price = order_pay_price;
    }

    public int getOrder_total_num() {
        return order_total_num;
    }

    public void setOrder_total_num(int order_total_num) {
        this.order_total_num = order_total_num;
    }

    public int getOrder_total_price() {
        return order_total_price;
    }

    public void setOrder_total_price(int order_total_price) {
        this.order_total_price = order_total_price;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }







    public static class GoodsListBean {
        /**
         * category : {"createtime":1.541402921E9,"id":8,"image":"https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg","name":"笔记本","pid":4,"updatetime":1.541403316E9,"weigh":8}
         * category_id : 8
         * content : <p><img src="https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/43b7a84d68a15d9058971526068a853a.jpg" data-filename="filename" style="width: 100%;"><br></p>
         * createtime : 1.541403061E9
         * deduct_stock_type : 20
         * delivery_id : 23
         * express_price : 680.00
         * freight : {"createtime":1.540363605E9,"id":23,"method":"10","method_text":"Method 10","name":"电脑","rule":[{"additional":1,"additional_fee":"10.00","create_time":1.540363605E9,"first":1,"first_fee":"20.00","litestore_freight_id":23,"region":"2,20,38,61,76,84,104,124,150,168,180,197,208,221,232,244,250,264,271,278,290,304,319,337,352,362,372,376,389,398,407,422,430,442,449,462,467,481,492,500,508,515,522,530,537,545,553,558,566,574,581,586,597,607,614,619,627,634,640,646,656,675,692,702,711,720,730,748,759,764,775,782,793,802,821,833,842,853,861,871,880,887,896,906,913,920,927,934,948,960,972,980,986,993,1003,1010,1015,1025,1035,1047,1057,1066,1074,1081,1088,1093,1098,1110,1118,1127,1136,1142,1150,1155,1160,1169,1183,1190,1196,1209,1222,1234,1245,1253,1264,1274,1279,1285,1299,1302,1306,1325,1339,1350,1362,1376,1387,1399,1408,1415,1421,1434,1447,1459,1466,1471,1476,1479,1492,1504,1513,1522,1533,1546,1556,1572,1583,1593,1599,1612,1623,1630,1637,1643,1650,1664,1674,1685,1696,1707,1710,1724,1731,1740,1754,1764,1768,1774,1782,1791,1802,1809,1813,1822,1828,1838,1848,1854,1867,1880,1890,1900,1905,1912,1924,1936,1949,1955,1965,1977,1988,1999,2003,2011,2017,2025,2035,2041,2050,2056,2065,2070,2077,2082,2091,2123,2146,2150,2156,2163,2177,2189,2207,2215,2220,2225,2230,2236,2245,2258,2264,2276,2283,2292,2297,2302,2306,2324,2363,2368,2388,2395,2401,2409,2416,2426,2434,2440,2446,2458,2468,2475,2486,2493,2501,2510,2516,2521,2535,2554,2573,2584,2589,2604,2611,2620,2631,2640,2657,2671,2686,2696,2706,2712,2724,2730,2741,2750,2761,2775,2784,2788,2801,2807,2812,2817,2826,2845,2857,2870,2882,2890,2899,2913,2918,2931,2946,2958,2972,2984,2997,3008,3016,3023,3032,3036,3039,3045,3053,3058,3065,3073,3081,3090,3098,3108,3117,3127,3135,3142,3147,3152,3158,3165,3172,3179,3186,3190,3196,3202,3207,3216,3221,3225,3229,3237,3242,3252,3262,3267,3280,3289,3301,3309,3317,3326,3339,3378,3386,3416,3454,3458,3461,3491,3504,3518,3532,3551,3578,3592,3613,3632,3666,3683,3697,3704,3711,3717,3722,3728,3739,3745,3747","region_data":["2","20","38","61","76","84","104","124","150","168","180","197","208","221","232","244","250","264","271","278","290","304","319","337","352","362","372","376","389","398","407","422","430","442","449","462","467","481","492","500","508","515","522","530","537","545","553","558","566","574","581","586","597","607","614","619","627","634","640","646","656","675","692","702","711","720","730","748","759","764","775","782","793","802","821","833","842","853","861","871","880","887","896","906","913","920","927","934","948","960","972","980","986","993","1003","1010","1015","1025","1035","1047","1057","1066","1074","1081","1088","1093","1098","1110","1118","1127","1136","1142","1150","1155","1160","1169","1183","1190","1196","1209","1222","1234","1245","1253","1264","1274","1279","1285","1299","1302","1306","1325","1339","1350","1362","1376","1387","1399","1408","1415","1421","1434","1447","1459","1466","1471","1476","1479","1492","1504","1513","1522","1533","1546","1556","1572","1583","1593","1599","1612","1623","1630","1637","1643","1650","1664","1674","1685","1696","1707","1710","1724","1731","1740","1754","1764","1768","1774","1782","1791","1802","1809","1813","1822","1828","1838","1848","1854","1867","1880","1890","1900","1905","1912","1924","1936","1949","1955","1965","1977","1988","1999","2003","2011","2017","2025","2035","2041","2050","2056","2065","2070","2077","2082","2091","2123","2146","2150","2156","2163","2177","2189","2207","2215","2220","2225","2230","2236","2245","2258","2264","2276","2283","2292","2297","2302","2306","2324","2363","2368","2388","2395","2401","2409","2416","2426","2434","2440","2446","2458","2468","2475","2486","2493","2501","2510","2516","2521","2535","2554","2573","2584","2589","2604","2611","2620","2631","2640","2657","2671","2686","2696","2706","2712","2724","2730","2741","2750","2761","2775","2784","2788","2801","2807","2812","2817","2826","2845","2857","2870","2882","2890","2899","2913","2918","2931","2946","2958","2972","2984","2997","3008","3016","3023","3032","3036","3039","3045","3053","3058","3065","3073","3081","3090","3098","3108","3117","3127","3135","3142","3147","3152","3158","3165","3172","3179","3186","3190","3196","3202","3207","3216","3221","3225","3229","3237","3242","3252","3262","3267","3280","3289","3301","3309","3317","3326","3339","3378","3386","3416","3454","3458","3461","3491","3504","3518","3532","3551","3578","3592","3613","3632","3666","3683","3697","3704","3711","3717","3722","3728","3739","3745","3747"],"rule_id":18,"weigh":0}],"updatetime":1.540363605E9,"weigh":23}
         * goods_id : 23
         * goods_name : MacBook Pro 13寸
         * goods_price : 12608.00
         * goods_sales : 12
         * goods_sku : {"create_time":1.556098793E9,"goods_attr":"颜色:天空灰; ","goods_id":23,"goods_no":"mac_0001","goods_price":"12608.00","goods_sales":0,"goods_spec_id":158,"goods_weight":1.2,"img_show":"","line_price":"0.00","spec_image":"","spec_sku_id":"48","stock_num":989,"update_time":1.556098793E9}
         * goods_sku_id : 48
         * goods_sort : 23
         * goods_status : 10
         * goods_total_weight : 80.4
         * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png
         * images : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/eaccd76080ed9e7ece7642694e734885.png,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/85587c2e045b71fb4c977884a19a36cb.jpg,https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg
         * is_delete : 0
         * label :
         * origin :
         * sales_actual : 12
         * sales_initial : 0
         * show_error : 0
         * spec : [{"create_time":1.556098793E9,"goods_attr":"颜色:天空灰; ","goods_id":23,"goods_no":"mac_0001","goods_price":"12608.00","goods_sales":0,"goods_spec_id":158,"goods_weight":1.2,"img_show":"","line_price":"0.00","spec_image":"","spec_sku_id":"48","stock_num":989,"update_time":1.556098793E9},{"create_time":1.556098793E9,"goods_id":23,"goods_no":"mac_0002","goods_price":"12588.00","goods_sales":0,"goods_spec_id":159,"goods_weight":1.2,"line_price":"0.00","spec_image":"","spec_sku_id":"49","stock_num":997,"update_time":1.556098793E9}]
         * specRel : [{"createtime":1.541403005E9,"id":48,"pivot":{"create_time":1.556098793E9,"goods_id":23,"id":140,"spec_id":20,"spec_value_id":48},"spec":{"createtime":1.541401442E9,"id":20,"spec_name":"颜色"},"spec_id":20,"spec_value":"天空灰"},{"createtime":1.541403011E9,"id":49,"pivot":{"create_time":1.556098793E9,"goods_id":23,"id":141,"spec_id":20,"spec_value_id":49},"spec_id":20,"spec_value":"银色"}]
         * spec_rel : [{"createtime":1.541403005E9,"id":48,"pivot":{"create_time":1.556098793E9,"goods_id":23,"id":140,"spec_id":20,"spec_value_id":48},"spec":{"createtime":1.541401442E9,"id":20,"spec_name":"颜色"},"spec_id":20,"spec_value":"天空灰"},{"createtime":1.541403011E9,"id":49,"pivot":{"create_time":1.556098793E9,"goods_id":23,"id":141,"spec_id":20,"spec_value_id":49},"spec":{"createtime":1.541401442E9,"id":20,"spec_name":"颜色"},"spec_id":20,"spec_value":"银色"}]
         * spec_type : 20
         * total_num : 67
         * total_price : 844736.00
         * updatetime : 1.556098793E9
         */

        private CategoryBean category;
        private int category_id;
        private String content;
        private double createtime;
        private String deduct_stock_type;
        private int delivery_id;
        private String express_price;
        private FreightBean freight;
        private int goods_id;
        private String goods_name;
        private String goods_price;
        private int goods_sales;
        private GoodsSkuBean goods_sku;
        private String goods_sku_id;
        private int goods_sort;
        private String goods_status;
        private String goods_total_weight;
        private String image;
        private String images;
        private String is_delete;
        private String label;
        private String origin;
        private int sales_actual;
        private int sales_initial;
        private int show_error;
        private String spec_type;
        private int total_num;
        private String total_price;
        private double updatetime;
        private List<SpecBean> spec;
        private List<SpecRelBean> specRel;
        private List<SpecRelBeanX> spec_rel;

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
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

        public String getExpress_price() {
            return express_price;
        }

        public void setExpress_price(String express_price) {
            this.express_price = express_price;
        }

        public FreightBean getFreight() {
            return freight;
        }

        public void setFreight(FreightBean freight) {
            this.freight = freight;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
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

        public GoodsSkuBean getGoods_sku() {
            return goods_sku;
        }

        public void setGoods_sku(GoodsSkuBean goods_sku) {
            this.goods_sku = goods_sku;
        }

        public String getGoods_sku_id() {
            return goods_sku_id;
        }

        public void setGoods_sku_id(String goods_sku_id) {
            this.goods_sku_id = goods_sku_id;
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

        public String getGoods_total_weight() {
            return goods_total_weight;
        }

        public void setGoods_total_weight(String goods_total_weight) {
            this.goods_total_weight = goods_total_weight;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public int getShow_error() {
            return show_error;
        }

        public void setShow_error(int show_error) {
            this.show_error = show_error;
        }

        public String getSpec_type() {
            return spec_type;
        }

        public void setSpec_type(String spec_type) {
            this.spec_type = spec_type;
        }

        public int getTotal_num() {
            return total_num;
        }

        public void setTotal_num(int total_num) {
            this.total_num = total_num;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public double getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(double updatetime) {
            this.updatetime = updatetime;
        }

        public List<SpecBean> getSpec() {
            return spec;
        }

        public void setSpec(List<SpecBean> spec) {
            this.spec = spec;
        }

        public List<SpecRelBean> getSpecRel() {
            return specRel;
        }

        public void setSpecRel(List<SpecRelBean> specRel) {
            this.specRel = specRel;
        }

        public List<SpecRelBeanX> getSpec_rel() {
            return spec_rel;
        }

        public void setSpec_rel(List<SpecRelBeanX> spec_rel) {
            this.spec_rel = spec_rel;
        }

        public static class CategoryBean {
            /**
             * createtime : 1.541402921E9
             * id : 8
             * image : https://her-family.oss-cn-qingdao.aliyuncs.com/addons_store_uploads/20181105/efb53c4c7814c83aa3c21e0fd408b5df.jpg
             * name : 笔记本
             * pid : 4
             * updatetime : 1.541403316E9
             * weigh : 8
             */

            private double createtime;
            private int id;
            private String image;
            private String name;
            private int pid;
            private double updatetime;
            private int weigh;

            public double getCreatetime() {
                return createtime;
            }

            public void setCreatetime(double createtime) {
                this.createtime = createtime;
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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public double getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(double updatetime) {
                this.updatetime = updatetime;
            }

            public int getWeigh() {
                return weigh;
            }

            public void setWeigh(int weigh) {
                this.weigh = weigh;
            }
        }

        public static class FreightBean {
            /**
             * createtime : 1.540363605E9
             * id : 23
             * method : 10
             * method_text : Method 10
             * name : 电脑
             * rule : [{"additional":1,"additional_fee":"10.00","create_time":1.540363605E9,"first":1,"first_fee":"20.00","litestore_freight_id":23,"region":"2,20,38,61,76,84,104,124,150,168,180,197,208,221,232,244,250,264,271,278,290,304,319,337,352,362,372,376,389,398,407,422,430,442,449,462,467,481,492,500,508,515,522,530,537,545,553,558,566,574,581,586,597,607,614,619,627,634,640,646,656,675,692,702,711,720,730,748,759,764,775,782,793,802,821,833,842,853,861,871,880,887,896,906,913,920,927,934,948,960,972,980,986,993,1003,1010,1015,1025,1035,1047,1057,1066,1074,1081,1088,1093,1098,1110,1118,1127,1136,1142,1150,1155,1160,1169,1183,1190,1196,1209,1222,1234,1245,1253,1264,1274,1279,1285,1299,1302,1306,1325,1339,1350,1362,1376,1387,1399,1408,1415,1421,1434,1447,1459,1466,1471,1476,1479,1492,1504,1513,1522,1533,1546,1556,1572,1583,1593,1599,1612,1623,1630,1637,1643,1650,1664,1674,1685,1696,1707,1710,1724,1731,1740,1754,1764,1768,1774,1782,1791,1802,1809,1813,1822,1828,1838,1848,1854,1867,1880,1890,1900,1905,1912,1924,1936,1949,1955,1965,1977,1988,1999,2003,2011,2017,2025,2035,2041,2050,2056,2065,2070,2077,2082,2091,2123,2146,2150,2156,2163,2177,2189,2207,2215,2220,2225,2230,2236,2245,2258,2264,2276,2283,2292,2297,2302,2306,2324,2363,2368,2388,2395,2401,2409,2416,2426,2434,2440,2446,2458,2468,2475,2486,2493,2501,2510,2516,2521,2535,2554,2573,2584,2589,2604,2611,2620,2631,2640,2657,2671,2686,2696,2706,2712,2724,2730,2741,2750,2761,2775,2784,2788,2801,2807,2812,2817,2826,2845,2857,2870,2882,2890,2899,2913,2918,2931,2946,2958,2972,2984,2997,3008,3016,3023,3032,3036,3039,3045,3053,3058,3065,3073,3081,3090,3098,3108,3117,3127,3135,3142,3147,3152,3158,3165,3172,3179,3186,3190,3196,3202,3207,3216,3221,3225,3229,3237,3242,3252,3262,3267,3280,3289,3301,3309,3317,3326,3339,3378,3386,3416,3454,3458,3461,3491,3504,3518,3532,3551,3578,3592,3613,3632,3666,3683,3697,3704,3711,3717,3722,3728,3739,3745,3747","region_data":["2","20","38","61","76","84","104","124","150","168","180","197","208","221","232","244","250","264","271","278","290","304","319","337","352","362","372","376","389","398","407","422","430","442","449","462","467","481","492","500","508","515","522","530","537","545","553","558","566","574","581","586","597","607","614","619","627","634","640","646","656","675","692","702","711","720","730","748","759","764","775","782","793","802","821","833","842","853","861","871","880","887","896","906","913","920","927","934","948","960","972","980","986","993","1003","1010","1015","1025","1035","1047","1057","1066","1074","1081","1088","1093","1098","1110","1118","1127","1136","1142","1150","1155","1160","1169","1183","1190","1196","1209","1222","1234","1245","1253","1264","1274","1279","1285","1299","1302","1306","1325","1339","1350","1362","1376","1387","1399","1408","1415","1421","1434","1447","1459","1466","1471","1476","1479","1492","1504","1513","1522","1533","1546","1556","1572","1583","1593","1599","1612","1623","1630","1637","1643","1650","1664","1674","1685","1696","1707","1710","1724","1731","1740","1754","1764","1768","1774","1782","1791","1802","1809","1813","1822","1828","1838","1848","1854","1867","1880","1890","1900","1905","1912","1924","1936","1949","1955","1965","1977","1988","1999","2003","2011","2017","2025","2035","2041","2050","2056","2065","2070","2077","2082","2091","2123","2146","2150","2156","2163","2177","2189","2207","2215","2220","2225","2230","2236","2245","2258","2264","2276","2283","2292","2297","2302","2306","2324","2363","2368","2388","2395","2401","2409","2416","2426","2434","2440","2446","2458","2468","2475","2486","2493","2501","2510","2516","2521","2535","2554","2573","2584","2589","2604","2611","2620","2631","2640","2657","2671","2686","2696","2706","2712","2724","2730","2741","2750","2761","2775","2784","2788","2801","2807","2812","2817","2826","2845","2857","2870","2882","2890","2899","2913","2918","2931","2946","2958","2972","2984","2997","3008","3016","3023","3032","3036","3039","3045","3053","3058","3065","3073","3081","3090","3098","3108","3117","3127","3135","3142","3147","3152","3158","3165","3172","3179","3186","3190","3196","3202","3207","3216","3221","3225","3229","3237","3242","3252","3262","3267","3280","3289","3301","3309","3317","3326","3339","3378","3386","3416","3454","3458","3461","3491","3504","3518","3532","3551","3578","3592","3613","3632","3666","3683","3697","3704","3711","3717","3722","3728","3739","3745","3747"],"rule_id":18,"weigh":0}]
             * updatetime : 1.540363605E9
             * weigh : 23
             */

            private double createtime;
            private int id;
            private String method;
            private String method_text;
            private String name;
            private double updatetime;
            private int weigh;
            private List<RuleBean> rule;

            public double getCreatetime() {
                return createtime;
            }

            public void setCreatetime(double createtime) {
                this.createtime = createtime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMethod() {
                return method;
            }

            public void setMethod(String method) {
                this.method = method;
            }

            public String getMethod_text() {
                return method_text;
            }

            public void setMethod_text(String method_text) {
                this.method_text = method_text;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(double updatetime) {
                this.updatetime = updatetime;
            }

            public int getWeigh() {
                return weigh;
            }

            public void setWeigh(int weigh) {
                this.weigh = weigh;
            }

            public List<RuleBean> getRule() {
                return rule;
            }

            public void setRule(List<RuleBean> rule) {
                this.rule = rule;
            }

            public static class RuleBean {
                /**
                 * additional : 1
                 * additional_fee : 10.00
                 * create_time : 1.540363605E9
                 * first : 1
                 * first_fee : 20.00
                 * litestore_freight_id : 23
                 * region : 2,20,38,61,76,84,104,124,150,168,180,197,208,221,232,244,250,264,271,278,290,304,319,337,352,362,372,376,389,398,407,422,430,442,449,462,467,481,492,500,508,515,522,530,537,545,553,558,566,574,581,586,597,607,614,619,627,634,640,646,656,675,692,702,711,720,730,748,759,764,775,782,793,802,821,833,842,853,861,871,880,887,896,906,913,920,927,934,948,960,972,980,986,993,1003,1010,1015,1025,1035,1047,1057,1066,1074,1081,1088,1093,1098,1110,1118,1127,1136,1142,1150,1155,1160,1169,1183,1190,1196,1209,1222,1234,1245,1253,1264,1274,1279,1285,1299,1302,1306,1325,1339,1350,1362,1376,1387,1399,1408,1415,1421,1434,1447,1459,1466,1471,1476,1479,1492,1504,1513,1522,1533,1546,1556,1572,1583,1593,1599,1612,1623,1630,1637,1643,1650,1664,1674,1685,1696,1707,1710,1724,1731,1740,1754,1764,1768,1774,1782,1791,1802,1809,1813,1822,1828,1838,1848,1854,1867,1880,1890,1900,1905,1912,1924,1936,1949,1955,1965,1977,1988,1999,2003,2011,2017,2025,2035,2041,2050,2056,2065,2070,2077,2082,2091,2123,2146,2150,2156,2163,2177,2189,2207,2215,2220,2225,2230,2236,2245,2258,2264,2276,2283,2292,2297,2302,2306,2324,2363,2368,2388,2395,2401,2409,2416,2426,2434,2440,2446,2458,2468,2475,2486,2493,2501,2510,2516,2521,2535,2554,2573,2584,2589,2604,2611,2620,2631,2640,2657,2671,2686,2696,2706,2712,2724,2730,2741,2750,2761,2775,2784,2788,2801,2807,2812,2817,2826,2845,2857,2870,2882,2890,2899,2913,2918,2931,2946,2958,2972,2984,2997,3008,3016,3023,3032,3036,3039,3045,3053,3058,3065,3073,3081,3090,3098,3108,3117,3127,3135,3142,3147,3152,3158,3165,3172,3179,3186,3190,3196,3202,3207,3216,3221,3225,3229,3237,3242,3252,3262,3267,3280,3289,3301,3309,3317,3326,3339,3378,3386,3416,3454,3458,3461,3491,3504,3518,3532,3551,3578,3592,3613,3632,3666,3683,3697,3704,3711,3717,3722,3728,3739,3745,3747
                 * region_data : ["2","20","38","61","76","84","104","124","150","168","180","197","208","221","232","244","250","264","271","278","290","304","319","337","352","362","372","376","389","398","407","422","430","442","449","462","467","481","492","500","508","515","522","530","537","545","553","558","566","574","581","586","597","607","614","619","627","634","640","646","656","675","692","702","711","720","730","748","759","764","775","782","793","802","821","833","842","853","861","871","880","887","896","906","913","920","927","934","948","960","972","980","986","993","1003","1010","1015","1025","1035","1047","1057","1066","1074","1081","1088","1093","1098","1110","1118","1127","1136","1142","1150","1155","1160","1169","1183","1190","1196","1209","1222","1234","1245","1253","1264","1274","1279","1285","1299","1302","1306","1325","1339","1350","1362","1376","1387","1399","1408","1415","1421","1434","1447","1459","1466","1471","1476","1479","1492","1504","1513","1522","1533","1546","1556","1572","1583","1593","1599","1612","1623","1630","1637","1643","1650","1664","1674","1685","1696","1707","1710","1724","1731","1740","1754","1764","1768","1774","1782","1791","1802","1809","1813","1822","1828","1838","1848","1854","1867","1880","1890","1900","1905","1912","1924","1936","1949","1955","1965","1977","1988","1999","2003","2011","2017","2025","2035","2041","2050","2056","2065","2070","2077","2082","2091","2123","2146","2150","2156","2163","2177","2189","2207","2215","2220","2225","2230","2236","2245","2258","2264","2276","2283","2292","2297","2302","2306","2324","2363","2368","2388","2395","2401","2409","2416","2426","2434","2440","2446","2458","2468","2475","2486","2493","2501","2510","2516","2521","2535","2554","2573","2584","2589","2604","2611","2620","2631","2640","2657","2671","2686","2696","2706","2712","2724","2730","2741","2750","2761","2775","2784","2788","2801","2807","2812","2817","2826","2845","2857","2870","2882","2890","2899","2913","2918","2931","2946","2958","2972","2984","2997","3008","3016","3023","3032","3036","3039","3045","3053","3058","3065","3073","3081","3090","3098","3108","3117","3127","3135","3142","3147","3152","3158","3165","3172","3179","3186","3190","3196","3202","3207","3216","3221","3225","3229","3237","3242","3252","3262","3267","3280","3289","3301","3309","3317","3326","3339","3378","3386","3416","3454","3458","3461","3491","3504","3518","3532","3551","3578","3592","3613","3632","3666","3683","3697","3704","3711","3717","3722","3728","3739","3745","3747"]
                 * rule_id : 18
                 * weigh : 0
                 */

                private int additional;
                private String additional_fee;
                private double create_time;
                private int first;
                private String first_fee;
                private int litestore_freight_id;
                private String region;
                private int rule_id;
                private int weigh;
                private List<String> region_data;

                public int getAdditional() {
                    return additional;
                }

                public void setAdditional(int additional) {
                    this.additional = additional;
                }

                public String getAdditional_fee() {
                    return additional_fee;
                }

                public void setAdditional_fee(String additional_fee) {
                    this.additional_fee = additional_fee;
                }

                public double getCreate_time() {
                    return create_time;
                }

                public void setCreate_time(double create_time) {
                    this.create_time = create_time;
                }

                public int getFirst() {
                    return first;
                }

                public void setFirst(int first) {
                    this.first = first;
                }

                public String getFirst_fee() {
                    return first_fee;
                }

                public void setFirst_fee(String first_fee) {
                    this.first_fee = first_fee;
                }

                public int getLitestore_freight_id() {
                    return litestore_freight_id;
                }

                public void setLitestore_freight_id(int litestore_freight_id) {
                    this.litestore_freight_id = litestore_freight_id;
                }

                public String getRegion() {
                    return region;
                }

                public void setRegion(String region) {
                    this.region = region;
                }

                public int getRule_id() {
                    return rule_id;
                }

                public void setRule_id(int rule_id) {
                    this.rule_id = rule_id;
                }

                public int getWeigh() {
                    return weigh;
                }

                public void setWeigh(int weigh) {
                    this.weigh = weigh;
                }

                public List<String> getRegion_data() {
                    return region_data;
                }

                public void setRegion_data(List<String> region_data) {
                    this.region_data = region_data;
                }
            }
        }

        public static class GoodsSkuBean {
            /**
             * create_time : 1.556098793E9
             * goods_attr : 颜色:天空灰;
             * goods_id : 23
             * goods_no : mac_0001
             * goods_price : 12608.00
             * goods_sales : 0
             * goods_spec_id : 158
             * goods_weight : 1.2
             * img_show :
             * line_price : 0.00
             * spec_image :
             * spec_sku_id : 48
             * stock_num : 989
             * update_time : 1.556098793E9
             */

            private double create_time;
            private String goods_attr;
            private int goods_id;
            private String goods_no;
            private String goods_price;
            private int goods_sales;
            private int goods_spec_id;
            private double goods_weight;
            private String img_show;
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

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
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

            public double getGoods_weight() {
                return goods_weight;
            }

            public void setGoods_weight(double goods_weight) {
                this.goods_weight = goods_weight;
            }

            public String getImg_show() {
                return img_show;
            }

            public void setImg_show(String img_show) {
                this.img_show = img_show;
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

        public static class SpecBean {
            /**
             * create_time : 1.556098793E9
             * goods_attr : 颜色:天空灰;
             * goods_id : 23
             * goods_no : mac_0001
             * goods_price : 12608.00
             * goods_sales : 0
             * goods_spec_id : 158
             * goods_weight : 1.2
             * img_show :
             * line_price : 0.00
             * spec_image :
             * spec_sku_id : 48
             * stock_num : 989
             * update_time : 1.556098793E9
             */

            private double create_time;
            private String goods_attr;
            private int goods_id;
            private String goods_no;
            private String goods_price;
            private int goods_sales;
            private int goods_spec_id;
            private double goods_weight;
            private String img_show;
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

            public String getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(String goods_attr) {
                this.goods_attr = goods_attr;
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

            public double getGoods_weight() {
                return goods_weight;
            }

            public void setGoods_weight(double goods_weight) {
                this.goods_weight = goods_weight;
            }

            public String getImg_show() {
                return img_show;
            }

            public void setImg_show(String img_show) {
                this.img_show = img_show;
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

        public static class SpecRelBean {
            /**
             * createtime : 1.541403005E9
             * id : 48
             * pivot : {"create_time":1.556098793E9,"goods_id":23,"id":140,"spec_id":20,"spec_value_id":48}
             * spec : {"createtime":1.541401442E9,"id":20,"spec_name":"颜色"}
             * spec_id : 20
             * spec_value : 天空灰
             */

            private double createtime;
            private int id;
            private PivotBean pivot;
            private SpecBeanX spec;
            private int spec_id;
            private String spec_value;

            public double getCreatetime() {
                return createtime;
            }

            public void setCreatetime(double createtime) {
                this.createtime = createtime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public PivotBean getPivot() {
                return pivot;
            }

            public void setPivot(PivotBean pivot) {
                this.pivot = pivot;
            }

            public SpecBeanX getSpec() {
                return spec;
            }

            public void setSpec(SpecBeanX spec) {
                this.spec = spec;
            }

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public String getSpec_value() {
                return spec_value;
            }

            public void setSpec_value(String spec_value) {
                this.spec_value = spec_value;
            }

            public static class PivotBean {
                /**
                 * create_time : 1.556098793E9
                 * goods_id : 23
                 * id : 140
                 * spec_id : 20
                 * spec_value_id : 48
                 */

                private double create_time;
                private int goods_id;
                private int id;
                private int spec_id;
                private int spec_value_id;

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

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public int getSpec_value_id() {
                    return spec_value_id;
                }

                public void setSpec_value_id(int spec_value_id) {
                    this.spec_value_id = spec_value_id;
                }
            }

            public static class SpecBeanX {
                /**
                 * createtime : 1.541401442E9
                 * id : 20
                 * spec_name : 颜色
                 */

                private double createtime;
                private int id;
                private String spec_name;

                public double getCreatetime() {
                    return createtime;
                }

                public void setCreatetime(double createtime) {
                    this.createtime = createtime;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getSpec_name() {
                    return spec_name;
                }

                public void setSpec_name(String spec_name) {
                    this.spec_name = spec_name;
                }
            }
        }

        public static class SpecRelBeanX {
            /**
             * createtime : 1.541403005E9
             * id : 48
             * pivot : {"create_time":1.556098793E9,"goods_id":23,"id":140,"spec_id":20,"spec_value_id":48}
             * spec : {"createtime":1.541401442E9,"id":20,"spec_name":"颜色"}
             * spec_id : 20
             * spec_value : 天空灰
             */

            private double createtime;
            private int id;
            private PivotBeanX pivot;
            private SpecBeanXX spec;
            private int spec_id;
            private String spec_value;

            public double getCreatetime() {
                return createtime;
            }

            public void setCreatetime(double createtime) {
                this.createtime = createtime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public PivotBeanX getPivot() {
                return pivot;
            }

            public void setPivot(PivotBeanX pivot) {
                this.pivot = pivot;
            }

            public SpecBeanXX getSpec() {
                return spec;
            }

            public void setSpec(SpecBeanXX spec) {
                this.spec = spec;
            }

            public int getSpec_id() {
                return spec_id;
            }

            public void setSpec_id(int spec_id) {
                this.spec_id = spec_id;
            }

            public String getSpec_value() {
                return spec_value;
            }

            public void setSpec_value(String spec_value) {
                this.spec_value = spec_value;
            }

            public static class PivotBeanX {
                /**
                 * create_time : 1.556098793E9
                 * goods_id : 23
                 * id : 140
                 * spec_id : 20
                 * spec_value_id : 48
                 */

                private double create_time;
                private int goods_id;
                private int id;
                private int spec_id;
                private int spec_value_id;

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

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSpec_id() {
                    return spec_id;
                }

                public void setSpec_id(int spec_id) {
                    this.spec_id = spec_id;
                }

                public int getSpec_value_id() {
                    return spec_value_id;
                }

                public void setSpec_value_id(int spec_value_id) {
                    this.spec_value_id = spec_value_id;
                }
            }

            public static class SpecBeanXX {
                /**
                 * createtime : 1.541401442E9
                 * id : 20
                 * spec_name : 颜色
                 */

                private double createtime;
                private int id;
                private String spec_name;

                public double getCreatetime() {
                    return createtime;
                }

                public void setCreatetime(double createtime) {
                    this.createtime = createtime;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getSpec_name() {
                    return spec_name;
                }

                public void setSpec_name(String spec_name) {
                    this.spec_name = spec_name;
                }
            }
        }
    }
}
