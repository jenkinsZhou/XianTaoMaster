package com.tourcoo.xiantao.entity.comment;

import java.util.List;

/**
 * @author :JenkinsZhou
 * @description :评论实体
 * @company :途酷科技
 * @date 2019年04月30日17:42
 * @Email: 971613168@qq.com
 */
public class CommentEntity {


    /**
     * total : 3
     * per_page : 10
     * current_page : 1
     * last_page : 1
     * data : [{"id":15,"goods_id":0,"user_id":4,"order_id":131,"nickname":"照明胧","avatar":"https://ahxtao.hfcoco.top/uploads/20190428/32b59995b0d5a969005d6fc93bcf8019.png","star":5,"detail":"宝贝收到了，卖家发货很快，物流也非常给力,客服服务态度极好，很有耐心、给人一种亲切感，好喜欢。还有包装精美，高端大气上档次;看得出来商家很用心。宝贝真心不错，与图片相符，没有任何差异，真的是物超所值，物美价廉啊。质量很好，下次还会再关顾!快递很给力，好评！发货快性价比高，给满分好评！我买东西习惯默默拍下，没有太大的问题是不会去询问客服，当然如果产品有问题，我是不会发表这条言论的。在此希望店主越做越好，回头客多多。很不错的购物体验，对比好多家最后选择了这家，果然很安心!客服也很负责，很耐心的给了推荐，即时回答问题!很赞!关键是东东很满意!值得推荐!还在犹豫的朋友们可以下手了!","images":"https://ahxtao.hfcoco.top/uploads/20190429/f16e6221dc715968f1cf50542fd98e23.png,https://ahxtao.hfcoco.top/uploads/20190429/6d3cf79834ad34eae5d72cb8cedc95ec.png,https://ahxtao.hfcoco.top/uploads/20190429/fb977bfad363c254ecf00812caf7ede9.png,https://ahxtao.hfcoco.top/uploads/20190429/4142c1b8ccaf092514651c41abdc5fc4.png,https://ahxtao.hfcoco.top/uploads/20190429/d1003640a2986dbee143ccb61f81f873.png,https://ahxtao.hfcoco.top/uploads/20190429/ce5b1f8993a393bb06f1f5e08f0b25ce.png,https://ahxtao.hfcoco.top/uploads/20190429/db672cf714b1c64ccf212a6448155419.png,https://ahxtao.hfcoco.top/uploads/20190429/d7593a9ab029746e0e67c152230b0225.png","createtime":1556504940,"updatetime":1556504940,"status":"normal","reply":null,"admin_id":0,"imgs_arr":["https://ahxtao.hfcoco.top/uploads/20190429/f16e6221dc715968f1cf50542fd98e23.png","https://ahxtao.hfcoco.top/uploads/20190429/6d3cf79834ad34eae5d72cb8cedc95ec.png","https://ahxtao.hfcoco.top/uploads/20190429/fb977bfad363c254ecf00812caf7ede9.png","https://ahxtao.hfcoco.top/uploads/20190429/4142c1b8ccaf092514651c41abdc5fc4.png","https://ahxtao.hfcoco.top/uploads/20190429/d1003640a2986dbee143ccb61f81f873.png","https://ahxtao.hfcoco.top/uploads/20190429/ce5b1f8993a393bb06f1f5e08f0b25ce.png","https://ahxtao.hfcoco.top/uploads/20190429/db672cf714b1c64ccf212a6448155419.png","https://ahxtao.hfcoco.top/uploads/20190429/d7593a9ab029746e0e67c152230b0225.png"]},{"id":17,"goods_id":0,"user_id":4,"order_id":256,"nickname":"ﾟ默｡","avatar":"https://wx.qlogo.cn/mmopen/vi_32/lTYDafia0gtOUD73KkMECgR3CqaqpUtRGzOsxCltfpHWIMr02yIDRxe6ED6GzAMQm1MRkdfWAG044agOxVS8Xcw/132","star":1,"detail":"还行一颗星","images":"https://ahxtao.hfcoco.top/uploads/20190430/20b62a2932cbf99679ae0c193c572118.png,https://ahxtao.hfcoco.top/uploads/20190430/1aff49d2f1f1b7a5dcb076d0b5603eaa.png,https://ahxtao.hfcoco.top/uploads/20190430/a16aee987578d50848f29fc42ab77976.png,https://ahxtao.hfcoco.top/uploads/20190430/37a31e6006fd24c9628cf40e4afbdf22.png,https://ahxtao.hfcoco.top/uploads/20190430/4142c1b8ccaf092514651c41abdc5fc4.png","createtime":1556593689,"updatetime":1556593689,"status":"normal","reply":null,"admin_id":0,"imgs_arr":["https://ahxtao.hfcoco.top/uploads/20190430/20b62a2932cbf99679ae0c193c572118.png","https://ahxtao.hfcoco.top/uploads/20190430/1aff49d2f1f1b7a5dcb076d0b5603eaa.png","https://ahxtao.hfcoco.top/uploads/20190430/a16aee987578d50848f29fc42ab77976.png","https://ahxtao.hfcoco.top/uploads/20190430/37a31e6006fd24c9628cf40e4afbdf22.png","https://ahxtao.hfcoco.top/uploads/20190430/4142c1b8ccaf092514651c41abdc5fc4.png"]},{"id":20,"goods_id":0,"user_id":2,"order_id":217,"nickname":"嘻哈江湖","avatar":"https://ahxtao.hfcoco.top/uploads/20190429/db00fae60f092ccf9bef0607cef4924f.jpg","star":5,"detail":"偷偷","images":"","createtime":1556614445,"updatetime":1556614445,"status":"normal","reply":null,"admin_id":0,"imgs_arr":[""]}]
     */

    private int total;
    private int per_page;
    private int current_page;
    private int last_page;
    private List<CommentDetail> data;

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

    public List<CommentDetail> getData() {
        return data;
    }

    public void setData(List<CommentDetail> data) {
        this.data = data;
    }


}
