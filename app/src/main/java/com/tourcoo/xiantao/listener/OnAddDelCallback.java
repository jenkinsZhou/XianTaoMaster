package com.tourcoo.xiantao.listener;


public interface OnAddDelCallback {
    /**
     * 商品加减回调监听
     * @param goodsId
     * @param number
     */
    void showNumber( int goodsId, int number);
}