package com.tourcoo.xiantao.ui.goods;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.GridImageAdapter;
import com.tourcoo.xiantao.constant.WxConfig;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.module.MainTabActivity;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.util.TourCoolUtil;
import com.tourcoo.xiantao.core.widget.core.util.SizeUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.custom.SharePopupWindow;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.event.RefreshEvent;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;
import com.tourcoo.xiantao.entity.goods.Spec;
import com.tourcoo.xiantao.entity.settle.SettleEntity;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.tourcoo.xiantao.ui.base.WebViewActivity;
import com.tourcoo.xiantao.ui.comment.CommentListActivity;
import com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity;
import com.tourcoo.xiantao.util.FormatDuration;
import com.tourcoo.xiantao.widget.dialog.PinTuanDialog;
import com.tourcoo.xiantao.widget.dialog.ProductSkuDialog;
import com.tourcoo.xiantao.widget.ratingstar.RatingStarView;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import de.hdodenhof.circleimageview.CircleImageView;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.core.module.SplashActivity.EXTRA_ADV_TAG;
import static com.tourcoo.xiantao.ui.goods.HomeFragment.EXTRA_GOODS_ID;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.EXTRA_PIN_USER_ID;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.EXTRA_SETTLE_TYPE;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.SETTLE_TYPE_PIN;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.SETTLE_TYPE_SINGLE;

/**
 * @author :JenkinsZhou
 * @description :商品详情
 * @company :途酷科技
 * @date 2019年04月24日19:52
 * @Email: 971613168@qq.com
 */
public class GoodsDetailActivity extends BaseTourCooTitleMultiViewActivity implements IMultiStatusView, View.OnClickListener {
    private RelativeLayout rlContentView;
    private SharePopupWindow sharePopupWindow;
    private IWXAPI api;
    private int count;
    private BGABanner bgaBanner;
    private List<String> imageList = new ArrayList<>();
    private TextView tvComment;
    private GoodsEntity mGoodsEntity;
    /**
     * 用于退出activity,避免countdown，造成资源浪费。
     */
    private SparseArray<CountDownTimer> countDownMap;
    private String skipTag;

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
    private LinearLayout llTuanContainer;

    private LinearLayout llCommentContainer;

    /**
     * 单独购买
     */
    private TextView tvBuyNow;
    /**
     * 拼团按钮
     */
    private TextView tvPin;
    private TextView tvAddShoppingCar;
    private TextView tvTuanTag;
    private TextView tvLable;
    private TextView tvOrigin;
    private TextView tvTuanStatus;
    private TextView btnSeeTotal;
    private TextView btnSeeTotalComment;
    private TextView btnSeeComment;

    private CheckBox cbCollect;
    private WebView webView;


    private ProductSkuDialog dialog;
    private Goods currentGoods;

    private LinearLayout llDeduct;
    private LinearLayout llGiveAway;
    private TextView tvGiveAwayCoin;

    private TextView tvDeduct;

    @Override
    public int getContentLayout() {
        return R.layout.activity_goods_detail;
    }

    private void init() {
        //判断是否是从广告页跳转过来的
        api = WXAPIFactory.createWXAPI(mContext, WxConfig.APP_ID);
        sharePopupWindow = new SharePopupWindow(mContext, false);
        skipTag = getIntent().getStringExtra(EXTRA_ADV_TAG);
        countDownMap = new SparseArray<>();
        cbCollect = findViewById(R.id.cbCollect);
        cbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        collectAdd(mGoodsId);
                    } else {
                        collectCancel(mGoodsId);
                    }
                }
            }
        });
        rlContentView = findViewById(R.id.rlContentView);
        llGiveAway = findViewById(R.id.llGiveAway);
        llDeduct = findViewById(R.id.llDeduct);
        tvDeduct = findViewById(R.id.tvDeduct);
        tvGiveAwayCoin = findViewById(R.id.tvGiveAwayCoin);
        llAssemble = findViewById(R.id.llAssemble);
        tvBuyNow = findViewById(R.id.tvBuyNow);
        tvPin = findViewById(R.id.tvPin);
        tvAddShoppingCar = findViewById(R.id.tvAddShoppingCar);
        webView = findViewById(R.id.webView);
        tvTuanTag = findViewById(R.id.tvTuanTag);
        tvLable = findViewById(R.id.tvLable);
        tvOrigin = findViewById(R.id.tvOrigin);
        tvTuanStatus = findViewById(R.id.tvTuanStatus);
        llTuanContainer = findViewById(R.id.llTuanContainer);
        llCommentContainer = findViewById(R.id.llCommentContainer);
        btnSeeTotal = findViewById(R.id.btnSeeTotal);
        btnSeeTotal.setOnClickListener(this);
        btnSeeTotalComment = findViewById(R.id.btnSeeTotalComment);
        btnSeeTotalComment.setOnClickListener(this);
        btnSeeComment = findViewById(R.id.btnSeeComment);
        btnSeeComment.setOnClickListener(this);
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
        TourCooLogUtil.i(TAG, TAG + ":" + "商品id=" + mGoodsId);
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("商品详情");
        titleBar.setRightTextDrawableWidth(SizeUtil.dp2px(18));
        titleBar.setRightTextDrawableHeight(SizeUtil.dp2px(19));
        titleBar.setRightTextDrawable(TourCooUtil.getDrawable(R.mipmap.ic_share));
        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare(currentGoods);
            }
        });
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
                getGoodsDetail(mGoodsId);
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
        mStatusLayoutManager.showLoadingLayout();
        ApiRepository.getInstance().getGoodsDetail(goodsId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    //显示回调
                                    mStatusLayoutManager.showSuccessLayout();
                                    mGoodsEntity = parseGoodsDetail(entity.data);
                                    showGoodsDetail(mGoodsEntity);
                                    TourCooLogUtil.i(TAG, mGoodsEntity);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                                mStatusLayoutManager.showErrorLayout();
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        mStatusLayoutManager.showErrorLayout();
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
        currentGoods = detail;
        if (detail.getGive() <= 0) {
            llGiveAway.setVisibility(View.GONE);
        } else {
            llGiveAway.setVisibility(View.VISIBLE);
            String value = "购买本商品每满" + detail.getGive() + "元 , 赠送1金币";
            tvGiveAwayCoin.setText(value);
        }
        if (detail.getDeduct() <= 0) {
            llDeduct.setVisibility(View.GONE);
        } else {
            llDeduct.setVisibility(View.VISIBLE);
            String value = "购买本商品每满" + detail.getDeduct() + "元 , 可使用1金币抵扣1元";
            tvDeduct.setText(value);
        }
        setBanner(detail.getImgs_url());
        //显示名称
        tvGoodsName.setText(detail.getGoods_name());

        //是否显示拼团信息
        setVisible(llAssemble, detail.isTuan() && detail.getTuan_list() != null && detail.getTuan_list().

                size() > 0);

        setVisible(tvPin, detail.isTuan());

        setVisible(tvTuanTag, detail.isTuan());

        tvLable.setText(detail.getLabel());
        if (!StringUtils.isEmpty(detail.getOrigin())) {
            tvOrigin.setText(new SpanUtils()
                    .append("产地: ").setForegroundColor(Color.parseColor("#9B9B9B"))
                    .append(detail.getOrigin()).setForegroundColor(TourCoolUtil.getColor(R.color.colorTextBlack))
                    .create());
        }

        if (detail.isTuan() && detail.getTuan_list() != null && detail.getTuan_list().

                size() > 0) {
            tvTuanStatus.setText(detail.getTuan_list().size() + "人正在拼团 可直接参与");
            int size = detail.getTuan_list().size() == 1 ? 1 : 2;

            for (int i = 0; i < size; i++) {
                Goods.TuanListBean tuanListBean = detail.getTuan_list().get(i);
                View view = View.inflate(this, R.layout.item_goods_details_tuan_person_layout, null);
                ImageView ivAvatar = view.findViewById(R.id.ivAvatar);
                TextView tvNickName = view.findViewById(R.id.tvNickName);
                TextView tvSurplus = view.findViewById(R.id.tvSurplus);
                TextView tvEndTime = view.findViewById(R.id.tvEndTime);
                TextView btnJoinTuan = view.findViewById(R.id.btnJoinTuan);

                GlideManager.loadCircleImg(tuanListBean.getAvatar(), ivAvatar);
                tvNickName.setText(tuanListBean.getNickname());
                tvSurplus.setText(tuanListBean.getSurplus() + "kg");

                long totalTime = tuanListBean.getDeadline() * 1000L - System.currentTimeMillis();

                CountDownTimer timer = new CountDownTimer(totalTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tvEndTime.setText("剩余" + FormatDuration.format(new Long(millisUntilFinished).intValue()));
                    }

                    @Override
                    public void onFinish() {
                        btnJoinTuan.setText("拼团已截止");
                        btnJoinTuan.setEnabled(false);
                    }
                }.start();

                btnJoinTuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.e(tuanListBean.getNum(), tuanListBean.getSurplus(), tuanListBean.getDeadline() * 1000L);
                        PinTuanDialog pinTuanDialog = new PinTuanDialog(GoodsDetailActivity.this, tuanListBean.getNum(),
                                tuanListBean.getSurplus(), tuanListBean.getDeadline() * 1000L, new PinTuanDialog.Callback() {
                            @Override
                            public void onAdded(int quantity) {
                                joinTuan(tuanListBean.getId(), quantity);
                            }
                        });
                        pinTuanDialog.show();
                    }
                });

                countDownMap.put(view.hashCode(), timer);

                llTuanContainer.addView(view);
            }
        }

        if (detail.getComment_list() != null && detail.getComment_list().

                size() > 0) {
            int size = detail.getComment_list().size() == 1 ? 1 : 2;
            for (int i = 0; i < size; i++) {
                Goods.CommentListBean item = detail.getComment_list().get(i);
                View view = View.inflate(this, R.layout.item_comment, null);
                RatingStarView ratingStarView = view.findViewById(R.id.rsvRating);
                RecyclerView commentImageRecyclerView = view.findViewById(R.id.commentImageRecyclerView);

                TextView tvNickName = view.findViewById(R.id.tvNickName);
                TextView tvTime = view.findViewById(R.id.tvTime);
                TextView tvComment = view.findViewById(R.id.tvComment);
                CircleImageView circleImageView = view.findViewById(R.id.civAvatar);

                tvNickName.setText(TourCooUtil.getNotNullValue(item.getNickname()));
                tvTime.setText(DateUtil.parseDate(item.getCreatetime()));
                tvComment.setText(TourCooUtil.getNotNullValue(item.getDetail()));
                if (!TextUtils.isEmpty(item.getImages())) {
                    List<String> imageUrlList = new ArrayList<>();
                    if (item.getImages() != null) {
                        String[] imageArray = item.getImages().split(",");
                        for (String image : imageArray) {
                            if (!TextUtils.isEmpty(image)) {
                                imageUrlList.add(TourCooUtil.getUrl(image));
                            }
                        }
                    }
                    commentImageRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                    GridImageAdapter gridImageAdapter = new GridImageAdapter(imageUrlList);
                    gridImageAdapter.bindToRecyclerView(commentImageRecyclerView);
                    gridImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            onThumbnailClick(imageUrlList.get(position));
                        }
                    });
                }

                ratingStarView.setEnabled(false);
                ratingStarView.setRating(item.getStar());
                String imageUrl = TourCooUtil.getUrl(item.getAvatar());
                GlideManager.loadImg(imageUrl, circleImageView, TourCooUtil.getDrawable(R.mipmap.img_default_avatar));

                llCommentContainer.addView(view);
            }

        } else {
            llCommentContainer.setVisibility(View.GONE);
            btnSeeTotalComment.setText("暂无评论");
            btnSeeTotalComment.setEnabled(false);
            btnSeeComment.setEnabled(false);
        }

        if (goodsEntity.getDetail().

                getCollect() == 0) {
            showNoCollect();
        } else {
            showCollect();
        }

        imageFillWidth(webView, goodsEntity.getDetail().

                getContent());

        tvPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProductSkuDialog(GoodsDetailActivity.this, goodsEntity, new ProductSkuDialog.Callback() {
                    @Override
                    public void onAdded(String specSkuId, int quantity) {
                        startNewTuan(goodsEntity.getDetail().getGoods_id(), quantity);
                    }
                }, ProductSkuDialog.PING_TUAN);

                dialog.show();
            }
        });

        tvBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProductSkuDialog(GoodsDetailActivity.this, goodsEntity, new ProductSkuDialog.Callback() {
                    @Override
                    public void onAdded(String specSkuId, int quantity) {
                        addGoods(goodsEntity.getDetail().getGoods_id(), quantity, specSkuId);
                    }
                }, ProductSkuDialog.BUY_NOW);

                dialog.show();
            }
        });

        tvAddShoppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProductSkuDialog(GoodsDetailActivity.this, goodsEntity, new ProductSkuDialog.Callback() {
                    @Override
                    public void onAdded(String specSkuId, int quantity) {
                        //添加购物车
                        addShopingCar(goodsEntity.getDetail().getGoods_id(), quantity, specSkuId);
                    }
                }, ProductSkuDialog.SHOPPING_CART);

                dialog.show();
            }
        });
        mStatusLayoutManager.showSuccessLayout();
    }

    /**
     * 加入拼团
     */
    private void joinTuan(int tuan_id, int num) {
        ApiRepository.getInstance().joinTuan(tuan_id, num).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    String info = JSON.toJSONString(entity.data);
                                    JSONObject jsonObject = JSONObject.parseObject(info);
                                    int pinId = jsonObject.getInteger("tuanuser_id");
                                    skipOrderSettleByPin(pinId);
                                } else {
                                    ToastUtil.showFailed(entity.msg);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        TourCooLogUtil.e(TAG, TAG + "异常:" + e.toString());
                    }
                });
    }

    private void onThumbnailClick(String imageUrl) {
// 全屏显示的方法
        /* android.R.style.Theme_Black_NoTitleBar_Fullscreen*/
        final Dialog dialog = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ImageView imgView = getView();
        dialog.setContentView(imgView);
        dialog.show();
        GlideManager.loadImg(imageUrl, imgView);
// 点击图片消失
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private ImageView getView() {
        ImageView imgView = new ImageView(mContext);
        imgView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imgView.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        return imgView;
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
     * 单独购买结算后跳转到结算页面显示
     *
     * @param settleEntity
     */
    private void skipOrderSettleDetail(SettleEntity settleEntity) {
        boolean skipEnable = settleEntity != null && settleEntity.getGoods_list() != null;
        if (!skipEnable) {
            ToastUtil.showFailed("未获取到商品信息");
            return;
        }
        Intent intent = new Intent();
        //单独购买结算类型
        intent.putExtra(EXTRA_SETTLE_TYPE, SETTLE_TYPE_SINGLE);
        intent.putExtra(EXTRA_SETTLE, settleEntity);
        intent.setClass(mContext, OrderSettleDetailActivity.class);
        startActivity(intent);
    }


    /**
     * 发起拼团的结算跳转
     *
     * @param pinId
     */
    private void skipOrderSettleByPin(int pinId) {
        Intent intent = new Intent();
        //单独购买结算类型
        intent.putExtra(EXTRA_SETTLE_TYPE, SETTLE_TYPE_PIN);
        intent.putExtra(EXTRA_PIN_USER_ID, pinId);
        intent.setClass(mContext, OrderSettleDetailActivity.class);
        TourCooLogUtil.i(TAG, "value:" + pinId);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSeeTotal:
                Intent intent = new Intent(GoodsDetailActivity.this, TuanListActivity.class);
                intent.putExtra("goods_id", mGoodsId);
                startActivity(intent);
                break;
            case R.id.btnSeeComment:
            case R.id.btnSeeTotalComment:
                intent = new Intent(GoodsDetailActivity.this, CommentListActivity.class);
                intent.putExtra(EXTRA_GOODS_ID, mGoodsId);
                startActivity(intent);
                break;
            default:
                break;
        }
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
        ApiRepository.getInstance().addGoods(goodsId, count, skuId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            ToastUtil.showSuccess(entity.msg);
                            EventBus.getDefault().postSticky(new RefreshEvent());
                        }
                    }
                });

    }


    /**
     * 请求添加商品接口
     */
    private void addGoods(int goodsId, int count, String skuId) {
        ApiRepository.getInstance().settleGoods(goodsId, count, skuId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    //跳转到结算
                                    SettleEntity settleEntity = parseSettleInfo(entity.data);
                                    if (settleEntity != null) {
                                        skipOrderSettleDetail(settleEntity);
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
     * 发起拼团接口
     */
    private void startNewTuan(int goodsId, int count) {
        ApiRepository.getInstance().startNewTuan(goodsId, count).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                if (entity.data != null) {
                                    String info = JSON.toJSONString(entity.data);
                                    JSONObject jsonObject = JSONObject.parseObject(info);
                                    int pinId = jsonObject.getInteger("tuanuser_id");
                                    skipOrderSettleByPin(pinId);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private void showCollect() {
        cbCollect.setChecked(true);
        cbCollect.setText("已收藏");
    }

    private void showNoCollect() {
        cbCollect.setChecked(false);
        cbCollect.setText("收藏");
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
                            ToastUtil.showSuccess(entity.msg);
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                showCollect();
                            } else {
                                showNoCollect();
                            }
                        }
                    }
                });
    }

    private void collectCancel(int goodsId) {
        if (goodsId < 0) {
            ToastUtil.showFailed("未获取到商品id");
            return;
        }
        ApiRepository.getInstance().collectCancel(goodsId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            ToastUtil.showSuccess(entity.msg);
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                showNoCollect();
                            } else {
                                showCollect();
                            }
                        }
                    }
                });
    }


    /**
     * 处理图片视频填充手机宽度
     */
    private void imageFillWidth(WebView webView, String content) {
        Document doc = Jsoup.parse(content);

        //修改视频标签
        Elements embeds = doc.getElementsByTag("embed");
        for (Element element : embeds) {
            //宽度填充手机，高度自适应
            element.attr("width", "100%").attr("height", "auto");
        }
        //webview 无法正确识别 embed 为视频，所以这里把这个标签改成 video 手机就可以识别了
        doc.select("embed").tagName("video");

        //控制图片的大小
        Elements imgs = doc.getElementsByTag("img");
        for (int i = 0; i < imgs.size(); i++) {
            //宽度填充手机，高度自适应
            imgs.get(i).attr("style", "width: 100%; height: auto;");
        }

        //对数据进行包装,除去WebView默认存在的一定像素的边距问题
        String data = "<html><head><style>img{width:100% !important;}</style></head><body style='margin:0;padding:0'>" + doc + "</body></html>";

        //加载使用 jsoup 处理过的 html 文本
        webView.loadData(data, "text/html; charset=UTF-8", null);
    }

    @Override
    public void loadData() {
        super.loadData();
        getGoodsDetail(mGoodsId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAllTimers();
    }

    /**
     * 清空资源
     */
    private void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        for (int i = 0, length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }


    @Override
    public void onBackPressed() {
        //如果是从广告页跳转的说明主页面没有创建
        if (EXTRA_ADV_TAG.equals(skipTag)) {
            TourCooUtil.startActivity(mContext, MainTabActivity.class);
        }
        super.onBackPressed();
    }


    /**
     * 显示分享
     *
     * @param goods
     */
    private void showShare(Goods goods) {
        sharePopupWindow.setISharePopupWindowClickListener(new SharePopupWindow.ISharePopupWindowClickListener() {
            @Override
            public void onWxClick() {
                doShare(goods, true);
                sharePopupWindow.dismiss();
            }

            @Override
            public void onWxFriendClick() {
                doShare(goods, false);
                sharePopupWindow.dismiss();
            }
        });
        sharePopupWindow.showAtScreenBottom(rlContentView);
    }


    private void doShare(Goods goods, boolean isFrend) {
        // 检查手机或者模拟器是否安装了微信
        if (goods == null) {
            ToastUtil.showFailed("未获取到商品信息");
            return;
        }
        if (!api.isWXAppInstalled()) {
            ToastUtil.showFailed("您还没有安装微信");
            return;
        }
        // 初始化一个WXWebpageObject对象
        WXWebpageObject webpageObject = new WXWebpageObject();
        // 填写网页的url
        webpageObject.webpageUrl = "http://www.ahxtao.com/";
        // 用WXWebpageObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        // 填写网页标题、描述、位图
        msg.title = goods.getGoods_name();
        msg.description = "濡江铺子,您的生活管家";
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_share_weixin);
        // 如果没有位图，可以传null，会显示默认的图片
        if (bitmap != null) {
            Bitmap sendBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
            msg.setThumbImage(sendBitmap);
        }
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        // transaction用于唯一标识一个请求（可自定义）
        req.transaction = "webpage";
        // 上文的WXMediaMessage对象
        req.message = msg;
        // SendMessageToWX.Req.WXSceneSession是分享到好友会话
        // SendMessageToWX.Req.WXSceneTimeline是分享到朋友圈
        if (isFrend) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        // 向微信发送请求
        api.sendReq(req);
    }


    private Drawable zoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(null, newbmp);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
}
