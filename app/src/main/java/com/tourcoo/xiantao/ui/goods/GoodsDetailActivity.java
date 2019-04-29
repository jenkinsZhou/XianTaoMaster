package com.tourcoo.xiantao.ui.goods;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;
import com.tourcoo.xiantao.entity.goods.Spec;
import com.tourcoo.xiantao.entity.settle.SettleEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.tourcoo.xiantao.ui.base.WebViewActivity;
import com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.ui.goods.HomeFragment.EXTRA_GOODS_ID;

/**
 * @author :JenkinsZhou
 * @description :商品详情
 * @company :途酷科技
 * @date 2019年04月24日19:52
 * @Email: 971613168@qq.com
 */
public class GoodsDetailActivity extends BaseTourCooTitleMultiViewActivity implements IMultiStatusView, View.OnClickListener {
    private RelativeLayout rlContentView;
    private int count;
    private BGABanner bgaBanner;
    private List<String> imageList = new ArrayList<>();
    private TextView tvComment;
    private GoodsEntity mGoodsEntity;

    /**
     * 结算明细实体
     */
    public static final String EXTRA_SETTLE = "EXTRA_SETTLE";
    /**
     * 传过来的商品id
     */
    private int mGoodsId;
    private TextView tvGoodsName;

    /**
     * 拼团布局
     */
    private LinearLayout llAssemble;

    /**
     * 单独购买
     */
    private TextView tvBuyNow;
    /**
     * 拼团按钮
     */
    private TextView tvPin;

    private TextView tvCollect;
    private ImageView ivCollect;

    private LinearLayout llCollect;

    @Override
    public int getContentLayout() {
        return R.layout.activity_goods_detail;
    }

    private void init() {
        llCollect = findViewById(R.id.llCollect);
        llCollect.setOnClickListener(this);
        tvCollect = findViewById(R.id.tvCollect);
        ivCollect = findViewById(R.id.ivCollect);
        rlContentView = findViewById(R.id.rlContentView);
        llAssemble = findViewById(R.id.llAssemble);
        tvBuyNow = findViewById(R.id.tvBuyNow);
        tvPin = findViewById(R.id.tvPin);
        tvBuyNow.setOnClickListener(this);
        findViewById(R.id.tvAddShoppingCar).setOnClickListener(this);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        bgaBanner = findViewById(R.id.bgaBanner);
        tvGoodsName = findViewById(R.id.tvGoodsName);
        tvComment = findViewById(R.id.tvComment);
        /**
         * 进入此页面必须携带商品id参数
         */
        mGoodsId = getIntent().getIntExtra(EXTRA_GOODS_ID, -1);
        init();
        TourCooLogUtil.i(TAG, TAG + ":" + "商品=" + mGoodsId);
        getGoodsDetail(mGoodsId);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("商品详情");
    }

    @Override
    protected IMultiStatusView getMultiStatusView() {
        return this;
    }

    @Override
    public View getMultiStatusContentView() {
        return rlContentView;
    }

    @Override
    public void setMultiStatusView(StatusLayoutManager.Builder statusView) {

    }

    @Override
    public View.OnClickListener getEmptyClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    @Override
    public View.OnClickListener getErrorClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    @Override
    public View.OnClickListener getCustomerClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    /**
     * 请求商品详情
     */
    private void getGoodsDetail(int goodsId) {
        ApiRepository.getInstance().getGoodsDetail(goodsId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    //显示回调
                                    mGoodsEntity = parseGoodsDetail(entity.data);
                                    showGoodsDetail(mGoodsEntity);
                                    TourCooLogUtil.i(TAG, mGoodsEntity);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private GoodsEntity parseGoodsDetail(Object object) {
        if (object == null) {
            return null;
        }
        try {
            String homeInfo = JSONObject.toJSONString(object);
            LogUtils.e(TAG, homeInfo);
            return JSON.parseObject(homeInfo, GoodsEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    private void setBanner(List<String> images) {
        bgaBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
                GlideManager.loadImg(model, (ImageView) itemView);
            }
        });
        bgaBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
                WebViewActivity.start(mContext, model.toString(), false);
            }
        });
        bgaBanner.setData(images, null);
    }

    /**
     * 显示商品详情
     *
     * @param goodsEntity
     */
    private void showGoodsDetail(GoodsEntity goodsEntity) {
        if (goodsEntity == null) {
            mStatusLayoutManager.showErrorLayout();
            return;
        }
        Goods detail = goodsEntity.getDetail();
        if (detail == null || detail.getImgs_url() == null) {
            mStatusLayoutManager.showErrorLayout();
            return;
        }
        setBanner(detail.getImgs_url());
        //显示名称
        tvGoodsName.setText(detail.getGoods_name());
        //是否显示拼团信息
        setVisible(llAssemble, detail.isTuan());
        setVisible(tvPin, detail.isTuan());
        if (goodsEntity.getCollect() == 0) {
            showNoCollect();
        } else {
            showCollect();
        }
    }


    private void setVisible(View view, boolean visible) {
        if (view == null) {
            TourCooLogUtil.e(TAG, TAG + ":" + "为null");
            return;
        }
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 显示评论
     */
    private void showComent() {
//        tvComment.setText();

    }


    /**
     * 结算后跳转到结算页面显示
     *
     * @param settleEntity
     */
    private void skipOrderDetail(SettleEntity settleEntity) {
        boolean skipEnable = settleEntity != null && settleEntity.getGoods_list() != null;
        if (!skipEnable) {
            ToastUtil.showFailed("未获取到商品信息");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SETTLE, settleEntity);
        intent.setClass(mContext, OrderSettleDetailActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBuyNow:
                //立即购买
                mGoodsEntity.setGoodsCount(1);
                settleGoods(mGoodsEntity);
                break;
            case R.id.tvAddShoppingCar:
                //添加购物车
                addGoods(count++);
                break;
            case R.id.llCollect:
                //收藏或取消收藏
                if (!isCollected(mGoodsEntity)) {
                    collectAdd(mGoodsId);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 直接购买结算页接口
     */
    private void settleGoods(GoodsEntity goodsEntity) {
        if (goodsEntity == null || goodsEntity.getDetail() == null) {
            return;
        }
        Goods goods = goodsEntity.getDetail();
        Spec spec = goods.getSpecBean().get(0);
        ApiRepository.getInstance().settleGoods(goods.getGoods_id(), goodsEntity.getGoodsCount(), spec.getSpec_sku_id()).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    //显示回调
                                    SettleEntity settleEntity = parseSettleInfo(entity.data);
                                    if (settleEntity != null) {
                                        skipOrderDetail(settleEntity);
                                    } else {
                                        ToastUtil.showFailed("失败");
                                    }
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    /**
     * 结算实体
     *
     * @param data
     * @return
     */
    private SettleEntity parseSettleInfo(Object data) {
        if (data == null) {
            return null;
        }
        try {
            String homeInfo = JSONObject.toJSONString(data);
            TourCooLogUtil.i(TAG, "准备解析:" + homeInfo);
            return JSON.parseObject(homeInfo, SettleEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    /**
     * 添加购物车
     */
    private void addShopingCar(int goodsId, int count, String skuId) {
        ApiRepository.getInstance().settleGoods(goodsId, count, skuId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    ToastUtil.showSuccess(entity.data.toString());
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }




    /**
     * 请求添加商品接口
     */
    private void addGoods(int count) {
        ApiRepository.getInstance().addGoods(23, count, "48").compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    ToastUtil.showSuccess(entity.data.toString());
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private void showCollect() {
        tvCollect.setText("已收藏");
        tvCollect.setTextColor(TourCoolUtil.getColor(R.color.greenCommon));
        ivCollect.setImageResource(R.mipmap.ic_collect_selected);
    }

    private void showNoCollect() {
        tvCollect.setText("收藏");
        tvCollect.setTextColor(TourCoolUtil.getColor(R.color.text_gray_common));
        ivCollect.setImageResource(R.mipmap.ic_collect);
    }


    private void collectAdd(int goodsId) {
        if (goodsId < 0) {
            ToastUtil.showFailed("未获取到商品id");
            return;
        }
        TourCooLogUtil.i(TAG, TAG + "操作的id:" + goodsId);
        ApiRepository.getInstance().collectAdd(goodsId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    ToastUtil.showSuccess(entity.data.toString());
                                    TourCooLogUtil.i(TAG, TAG + "回调结果:" + JSON.toJSONString(entity.data.toString()));
                                } else {
                                    ToastUtil.showFailed(entity.msg);
                                    TourCooLogUtil.i(TAG, TAG + "回调结果:" + JSON.toJSONString(entity));
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }

    private boolean isCollected(GoodsEntity goodsEntity) {
        return goodsEntity.getCollect() == 1;
    }
}
