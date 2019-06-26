package com.tourcoo.xiantao.ui.goods;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.UserManager;
import android.text.Layout;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.melnykov.fab.ObservableScrollView;
import com.previewlibrary.GPreviewBuilder;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.XianTaoApplication;
import com.tourcoo.xiantao.adapter.GridCommentImageAdapter;
import com.tourcoo.xiantao.adapter.GridImageAdapter;
import com.tourcoo.xiantao.constant.WxConfig;
import com.tourcoo.xiantao.core.common.CommonConstant;
import com.tourcoo.xiantao.core.frame.interfaces.IMultiStatusView;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.frame.util.NetworkUtil;
import com.tourcoo.xiantao.core.frame.util.SharedPreferencesUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.log.widget.utils.DateUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.SizeUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.custom.ShareGoodsPopupWindow;
import com.tourcoo.xiantao.core.widget.custom.SharePopupWindow;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.ImageEntity;
import com.tourcoo.xiantao.entity.SystemSettingEntity;
import com.tourcoo.xiantao.entity.event.RefreshEvent;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.entity.goods.GoodsEntity;
import com.tourcoo.xiantao.entity.goods.TuanRule;
import com.tourcoo.xiantao.entity.settle.SettleEntity;
import com.tourcoo.xiantao.permission.PermissionManager;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleMultiViewActivity;
import com.tourcoo.xiantao.ui.account.LoginActivity;
import com.tourcoo.xiantao.ui.comment.CommentListActivity;
import com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity;
import com.tourcoo.xiantao.util.FormatDuration;
import com.tourcoo.xiantao.widget.custom.LabelLayout;
import com.tourcoo.xiantao.widget.dialog.PinTuanDialog;
import com.tourcoo.xiantao.widget.dialog.ProductSkuDialog;
import com.tourcoo.xiantao.widget.ratingstar.RatingStarView;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import de.hdodenhof.circleimageview.CircleImageView;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.CommonConstant.companyInfo;
import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_ADDRESS_KEY;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_TEL_PHONE_KEY;
import static com.tourcoo.xiantao.core.helper.AccountInfoHelper.PREF_TEL_REGISTER_KEY;
import static com.tourcoo.xiantao.core.module.SplashActivity.EXTRA_ADV_TAG;
import static com.tourcoo.xiantao.ui.home.HomeFragment.EXTRA_GOODS_ID;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.EXTRA_GOODS_COUNT;
import static com.tourcoo.xiantao.ui.order.OrderSettleDetailActivity.EXTRA_GOODS_SKU_ID;
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
@Route(path = "/goods/GoodsDetailActivity")
public class GoodsDetailActivity extends BaseTourCooTitleMultiViewActivity implements IMultiStatusView, View.OnClickListener {
    public static final int REQUSET_CODE_SETTLE = 1016;
    public static final int REQUSET_CODE_TUAN_LIST = 1017;
    public static final String EXTRA_SKIP_SETTLE = "EXTRA_SKIP_SETTLE";
    private boolean swiping = false;
    private String companyInfo;
    private LinearLayout llComanyInfo;
    private WebView companyWebView;
    private TitleBarView mTitleBarView;
    private RelativeLayout rlContentView;

    private ShareGoodsPopupWindow sharePopupWindow;
    private RelativeLayout rlORigin;
    private IWXAPI api;
    private int count;
    private BGABanner bgaBanner;
    private List<String> imageList = new ArrayList<>();
    /**
     * 商品价格区间
     */
    private TextView tvPriceRange;
    private TextView tvLinePrice;
    private TextView tvSaleCount;

    /**
     * 拼团价
     */
    private TextView tvPinPrice;
    private GoodsEntity mGoodsEntity;
    private TextView tvLimitInfo;
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
    private LabelLayout labelLayout;

    private LinearLayout llDeductRule;
    private TextView tvExplainDiscount;
    //    private FloatingActionButton mFloatingActionButton;
    private ObservableScrollView mObservableScrollView;
    private LinearLayout llLimitSaleInfo;
    private CircleImageView civReturnTop;
    private double limitSaleCount;

    @Override
    public int getContentLayout() {
        return R.layout.activity_goods_detail;
    }

    private void init() {
        //判断是否是从广告页跳转过来的
        api = WXAPIFactory.createWXAPI(mContext, WxConfig.APP_ID);
        rlORigin = findViewById(R.id.rlORigin);
        tvSaleCount = findViewById(R.id.tvSaleCount);
        llComanyInfo = findViewById(R.id.llComanyInfo);
        companyWebView = findViewById(R.id.companyWebView);
        mObservableScrollView = findViewById(R.id.mObservableScrollView);
        civReturnTop = findViewById(R.id.civReturnTop);
        civReturnTop.setVisibility(View.INVISIBLE);
        llDeductRule = findViewById(R.id.llDeductRule);
        tvExplainDiscount = findViewById(R.id.tvExplainDiscount);
        tvPriceRange = findViewById(R.id.tvPriceRange);
        tvLinePrice = findViewById(R.id.tvLinePrice);
        tvPinPrice = findViewById(R.id.tvPinPrice);
        sharePopupWindow = new ShareGoodsPopupWindow(mContext, false);
        skipTag = getIntent().getStringExtra(EXTRA_ADV_TAG);
        countDownMap = new SparseArray<>();
        cbCollect = findViewById(R.id.cbCollect);
        cbCollect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (!AccountInfoHelper.getInstance().isLogin()) {
                        cbCollect.setChecked(false);
                        skipToLoginActivity();
                        return;
                    }
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
        llLimitSaleInfo = findViewById(R.id.llLimitSaleInfo);
        labelLayout = findViewById(R.id.labelLayout);
        llDeduct = findViewById(R.id.llDeduct);
        tvDeduct = findViewById(R.id.tvDeduct);
        tvGiveAwayCoin = findViewById(R.id.tvGiveAwayCoin);
        tvLimitInfo = findViewById(R.id.tvLimitInfo);
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
//        tvComment = findViewById(R.id.tvComment);
        /**
         * 进入此页面必须携带商品id参数
         */
        mGoodsId = getIntent().getIntExtra(EXTRA_GOODS_ID, -1);
        companyInfo = CommonConstant.companyInfo;
        init();
        initFloatButton();
        initFloateButtonListener();
        if (!checkPermission()) {
            PermissionManager.requestAllNeedPermission(this);
        }
        TourCooLogUtil.i(TAG, TAG + ":" + "商品id=" + mGoodsId);

    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        mTitleBarView = titleBar;
        titleBar.setTitleMainText("商品详情");
        titleBar.setRightTextDrawableWidth(SizeUtil.dp2px(20));
        titleBar.setRightTextDrawableHeight(SizeUtil.dp2px(19));
        titleBar.setRightTextDrawable(TourCooUtil.getDrawable(R.mipmap.ic_share));
        titleBar.getTextView(Gravity.RIGHT).setVisibility(View.INVISIBLE);
        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare(currentGoods);
            }
        });
        titleBar.setOnLeftTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                getGoodsDetail(mGoodsId);
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
                                showErrorLayoutMsg(entity.msg);
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
            return JSON.parseObject(homeInfo, GoodsEntity.class);
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, "解析异常:" + e.toString());
            return null;
        }
    }


    private void setBanner(List<String> images) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) bgaBanner.getLayoutParams();
        params.height = XianTaoApplication.getImageHeight();
        TourCooLogUtil.i(TAG, "banner:高度" + params.height + ";width:" + com.tourcoo.xiantao.core.frame.util.SizeUtil.getScreenWidth());
        bgaBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, Object model, int position) {
                GlideManager.loadImg(model, (ImageView) itemView);
            }
        });
        List<ImageEntity> imageEntityList = parseImageEntityList(images);
        computeBoundsBackward(bgaBanner, imageEntityList);
        bgaBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onBannerItemClick(BGABanner banner, View itemView, Object model, int position) {
//                WebViewActivity.start(mContext, model.toString(), false);
                GPreviewBuilder.from(GoodsDetailActivity.this)
                        .setData(imageEntityList)
                        .setCurrentIndex(position)
                        .setSingleFling(true)
                        .setType(GPreviewBuilder.IndicatorType.Number)
                        .start();
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
        countDownMap.clear();
        llTuanContainer.removeAllViews();
        llCommentContainer.removeAllViews();
        if (goodsEntity == null) {
            mStatusLayoutManager.showErrorLayout();
            return;
        }
        Goods detail = goodsEntity.getDetail();
        if (detail == null || detail.getImgs_url() == null) {
            mStatusLayoutManager.showErrorLayout();
            return;
        }
        mTitleBarView.getTextView(Gravity.RIGHT).setVisibility(View.VISIBLE);
        currentGoods = detail;
        loadLimitSaleCount(detail);
        if (detail.getGive() <= 0) {
            llGiveAway.setVisibility(View.GONE);
        } else {
            llGiveAway.setVisibility(View.VISIBLE);
            String value = "购买本商品每满" + TourCooUtil.doubleTransStringZhen(detail.getGive()) + "元 , 赠送1金币";
            tvGiveAwayCoin.setText(value);
        }
        //显示折扣信息
        showSaleCountByCondition(detail);
        //显示限购数量
        if (detail.getQuota() <= 0) {
            llLimitSaleInfo.setVisibility(View.GONE);
        } else {
            llLimitSaleInfo.setVisibility(View.VISIBLE);
            String value = "本商品每人限购" + TourCooUtil.doubleTransStringZhen(detail.getQuota()) + "份";
            tvLimitInfo.setText(value);
        }
        if (TextUtils.isEmpty(detail.getPromote())) {
            setVisible(llDeductRule, false);
        } else {
            setVisible(llDeductRule, true);
            tvExplainDiscount.setText(detail.getPromote());
        }
        if (detail.getDeduct() <= 0) {
            llDeduct.setVisibility(View.GONE);
        } else {
            llDeduct.setVisibility(View.VISIBLE);
            String value = "金币每满" + TourCooUtil.doubleTransStringZhen(detail.getDeduct()) + "用" + TourCooUtil.doubleTransStringZhen(detail.getDeduct_coin());
            if (!TextUtils.isEmpty(detail.getDeduct_rule())) {
                value += "(" + detail.getDeduct_rule() + ")";
            }
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
        boolean isTuan = detail.isTuan();
        try {
            String priceRange;
            if (detail.getGoods_min_price() == detail.getGoods_max_price()) {
                priceRange = TourCooUtil.doubleTransString(detail.getGoods_min_price());
            } else {
                priceRange = TourCooUtil.doubleTransString(detail.getGoods_min_price()) + "-" + TourCooUtil.doubleTransString(detail.getGoods_max_price());
            }
            if (detail.getGoods_min_line_price() > 0) {
                //中划线
                tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvLinePrice.getPaint().setAntiAlias(true);
                String linePrice = "¥" + TourCooUtil.doubleTransString(detail.getGoods_min_line_price());
                tvLinePrice.setText(linePrice);
            } else {
                setVisible(tvLinePrice, false);
            }
            tvPriceRange.setText(priceRange);
            if (isTuan && detail.getTuan_rule() != null) {
                TuanRule tuanRule = new Gson().fromJson(detail.getTuan_rule().toString(), TuanRule.class);
                String pinPrice = "拼团价¥" + TourCooUtil.doubleTrans(Double.parseDouble(tuanRule.getPrice()));
                setVisible(tvPinPrice, true);
                tvPinPrice.setText(pinPrice);
            } else {
                setVisible(tvPinPrice, false);
            }
        } catch (Exception e) {
            TourCooLogUtil.e(TAG, TAG + ":" + "显示异常:" + e.toString());
        }
        setVisible(tvLable, false);
        tvLable.setText(detail.getLabel());
        if (!StringUtils.isEmpty(detail.getOrigin())) {
         /*   tvOrigin.setText(new SpanUtils()
                    .append("产地: ").setForegroundColor(Color.parseColor("#9B9B9B"))
                    .append(detail.getOrigin()).setForegroundColor(TourCoolUtil.getColor(R.color.colorTextBlack))
                    .create());*/
            String originInfo = "产地:" + detail.getOrigin();
            tvOrigin.setText(originInfo);
        } else {
            tvOrigin.setText("未知");
            setVisible(rlORigin, false);
        }

        if (detail.isTuan() && detail.getTuan_list() != null && detail.getTuan_list().size() > 0) {
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

                GlideManager.loadCircleImg(tuanListBean.getAvatar(), ivAvatar, TourCooUtil.getDrawable(R.mipmap.img_default_avatar));
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
                        btnJoinTuan.setText("已截止");
                        btnJoinTuan.setEnabled(false);
                    }
                }.start();

                btnJoinTuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!AccountInfoHelper.getInstance().isLogin()) {
                            skipToLoginActivity();
                            return;
                        }
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

        if (detail.getComment_list() != null && detail.getComment_list().size() > 0) {
            int size = detail.getComment_list().size() == 1 ? 1 : 2;
            for (int i = 0; i < size; i++) {
                Goods.CommentListBean item = detail.getComment_list().get(i);
                View view = View.inflate(this, R.layout.item_comment_match_content, null);
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
                    GridCommentImageAdapter gridImageAdapter = new GridCommentImageAdapter(imageUrlList);
                    gridImageAdapter.bindToRecyclerView(commentImageRecyclerView);
                    List<ImageEntity> imageEntityList = parseImageEntityList(gridImageAdapter.getData());
                    gridImageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                            onThumbnailClick(imageUrlList.get(position));
                            computeBoundsBackward(commentImageRecyclerView, imageEntityList);
                            GPreviewBuilder.from(GoodsDetailActivity.this)
                                    .setData(imageEntityList)
                                    .setCurrentIndex(position)
                                    .setSingleFling(true)
                                    .setType(GPreviewBuilder.IndicatorType.Number)
                                    .start();
                        }
                    });
                }
                ratingStarView.setEnabled(false);
                ratingStarView.setRating(item.getStar());
                String imageUrl = TourCooUtil.getUrl(item.getAvatar());
                GlideManager.loadImg(imageUrl, circleImageView, TourCooUtil.getDrawable(R.mipmap.img_zwt));
                llCommentContainer.addView(view);
            }

        } else {
            llCommentContainer.setVisibility(View.GONE);
            btnSeeTotalComment.setText("暂无评论");
            btnSeeTotalComment.setEnabled(true);
            btnSeeComment.setEnabled(true);
        }

        if (goodsEntity.getDetail().getCollect() == 0) {
            showNoCollect();
        } else {
            showCollect();
        }

        imageFillWidth(webView, goodsEntity.getDetail().
                getContent());

        tvPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    skipToLoginActivity();
                    return;
                }
                dialog = new ProductSkuDialog(GoodsDetailActivity.this, goodsEntity, new ProductSkuDialog.Callback() {
                    @Override
                    public void onAdded(String specSkuId, int quantity) {
                        if (!AccountInfoHelper.getInstance().isLogin()) {
                            skipToLoginActivity();
                            return;
                        }
                        startNewTuan(goodsEntity.getDetail().getGoods_id(), quantity);
                    }
                }, ProductSkuDialog.PING_TUAN, Integer.MAX_VALUE);

                dialog.show();
            }
        });

        tvBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    skipToLoginActivity();
                    return;
                }
                dialog = new ProductSkuDialog(GoodsDetailActivity.this, goodsEntity, new ProductSkuDialog.Callback() {
                    @Override
                    public void onAdded(String specSkuId, int quantity) {
                        buyNow(goodsEntity.getDetail().getGoods_id(), quantity, specSkuId);
                    }
                }, ProductSkuDialog.BUY_NOW, limitSaleCount);

                dialog.show();
            }
        });

        tvAddShoppingCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    skipToLoginActivity();
                    return;
                }
                dialog = new ProductSkuDialog(GoodsDetailActivity.this, goodsEntity, new ProductSkuDialog.Callback() {
                    @Override
                    public void onAdded(String specSkuId, int quantity) {
                        //添加购物车
                        addShopingCar(goodsEntity.getDetail().getGoods_id(), quantity, specSkuId);
                    }
                }, ProductSkuDialog.SHOPPING_CART, limitSaleCount);

                dialog.show();
            }
        });
        showGoodsLabel(loadGoodsLabel(goodsEntity));
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
    private void skipOrderSettleDetail(SettleEntity settleEntity, int goodsId, int goodsCount, String skuId) {
        boolean skipEnable = settleEntity != null && settleEntity.getGoods_list() != null;
        if (!skipEnable) {
            ToastUtil.showFailed("未获取到商品信息");
            return;
        }
        Intent intent = new Intent();
        //单独购买结算类型
        intent.putExtra(EXTRA_SETTLE_TYPE, SETTLE_TYPE_SINGLE);
        intent.putExtra(EXTRA_SETTLE, settleEntity);
        intent.putExtra(EXTRA_GOODS_ID, goodsId);
        intent.putExtra(EXTRA_GOODS_COUNT, goodsCount);
        intent.putExtra(EXTRA_GOODS_SKU_ID, skuId);
        intent.setClass(mContext, OrderSettleDetailActivity.class);
        startActivity(intent);
//        startActivityForResult(intent, REQUSET_CODE_SETTLE);
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
        startActivityForResult(intent, REQUSET_CODE_SETTLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSeeTotal:
                if (!AccountInfoHelper.getInstance().isLogin()) {
                    skipToLoginActivity();
                    return;
                }
                Intent intent = new Intent(GoodsDetailActivity.this, TuanListActivity.class);
                intent.putExtra("goods_id", mGoodsId);
                startActivityForResult(intent, REQUSET_CODE_TUAN_LIST);
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
                            EventBus.getDefault().post(new RefreshEvent());
                        }
                    }
                });

    }


    /**
     * 请求添加商品接口
     */
    private void buyNow(int goodsId, int count, String skuId) {
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
                                        skipOrderSettleDetail(settleEntity, goodsId, count, skuId);
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
                                setResult(RESULT_OK);
                            } else {
                                setResult(RESULT_OK);
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
                                setResult(RESULT_OK);
                            } else {
                                showCollect();
                                setResult(RESULT_OK);
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
        requestSystemConfig();
    }

    @Override
    protected void onDestroy() {
        cancelAllTimers();
        if (api != null) {
            api.detach();
        }
        super.onDestroy();
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
                cdt = null;
            }
        }
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
        msg.title = "特大喜讯！濡江铺子试运营今日开启！";
        msg.description = "精挑细选的新鲜水果，欢迎选购，优惠多多，奖励多多，快把绿色健康带回家吧！";
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


    private void skipToLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(mContext, LoginActivity.class);
        startActivityAfterLogin(intent);
    }


    /**
     * 加载商品标签
     */
    private void showGoodsLabel(List<String> labelList) {
        if (labelList == null || labelList.isEmpty()) {
            setVisible(labelLayout, false);
            return;
        }
        labelLayout.removeAllViews();
        setVisible(labelLayout, true);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (int i = 0; i < labelList.size(); i++) {
            TextView textView = (TextView) layoutInflater.inflate(R.layout.item_goods_label, labelLayout, false);
            final String label = labelList.get(i);
            textView.setText(label);
            labelLayout.addView(textView);
        }
    }


    private List<String> loadGoodsLabel(GoodsEntity goodsEntity) {
        List<String> stringList = new ArrayList<>();
        if (goodsEntity == null || goodsEntity.getDetail() == null || goodsEntity.getDetail().getLabel() == null) {
            return stringList;
        }
        String label = goodsEntity.getDetail().getLabel();
        String result = label.replaceAll("，", ",");
        String[] labelArray = result.split(",");
        for (String s : labelArray) {
            stringList.add(s);
        }
        return stringList;
    }


    /**
     * 查找信息
     * 从第一个完整可见item逆序遍历，如果初始位置为0，则不执行方法内循环
     */
    private void computeBoundsBackward(RecyclerView imageRecyclerView, List<ImageEntity> imageEntityList) {
        if (imageRecyclerView == null || !(imageRecyclerView.getLayoutManager() instanceof GridLayoutManager)) {
            return;
        }
        GridLayoutManager gridLayoutManager = (GridLayoutManager) imageRecyclerView.getLayoutManager();
        int firstCompletelyVisiblePos = gridLayoutManager.findFirstVisibleItemPosition();
        for (int i = firstCompletelyVisiblePos; i < imageEntityList.size(); i++) {
            View itemView = gridLayoutManager.findViewByPosition(i);
            Rect bounds = new Rect();
            if (itemView != null) {
                ImageView thumbView = itemView.findViewById(R.id.additionalRoundedImageView);
                thumbView.getGlobalVisibleRect(bounds);
            }
            imageEntityList.get(i).setBounds(bounds);
        }
    }

    private void computeBoundsBackward(BGABanner bgaBanner, List<ImageEntity> imageEntityList) {
        if (bgaBanner == null) {
            return;
        }
        for (int i = 0; i < imageEntityList.size(); i++) {
            Rect bounds = new Rect();
            bounds.left = bgaBanner.getLeft();
            bounds.right = bgaBanner.getWidth();
            bounds.top = bgaBanner.getTop();
            bounds.bottom = bgaBanner.getHeight();
            imageEntityList.get(i).setBounds(bounds);
        }
    }

    private List<ImageEntity> parseImageEntityList(List<String> imageUrlList) {
        List<ImageEntity> imageEntityList = new ArrayList<>();
        if (imageUrlList == null || imageUrlList.isEmpty()) {
            return imageEntityList;
        }
        ImageEntity imageEntity;
        for (String url : imageUrlList) {
            imageEntity = new ImageEntity();
            imageEntity.setImageUrl(url);
            imageEntityList.add(imageEntity);
        }
        return imageEntityList;
    }


    private void initFloatButton() {
//        mFloatingActionButton.hide(false);
        civReturnTop.setVisibility(View.INVISIBLE);
        mObservableScrollView.setOnScrollChangedListener(new ObservableScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                if (t > bgaBanner.getHeight()) {
                    civReturnTop.setVisibility(View.VISIBLE);
                } else {
                    civReturnTop.setVisibility(View.INVISIBLE);
                }
            }
        });
     /*   mFloatingActionButton.attachToScrollView(mObservableScrollView, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                mFloatingActionButton.hide();
                swiping = true;
            }

            @Override
            public void onScrollUp() {
                swiping = true;
            }
        }, new ObservableScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                if (t > bgaBanner.getHeight()) {
                    mFloatingActionButton.show();
                } else {
                    mFloatingActionButton.hide();
                }
            }
        });*/
    }

    private void initFloateButtonListener() {
        civReturnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mObservableScrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }


    private void showCompanyInfo(String info) {
        if (TextUtils.isEmpty(info)) {
            llComanyInfo.setVisibility(View.GONE);
            companyWebView.setVisibility(View.GONE);
            return;
        }
        llComanyInfo.setVisibility(View.VISIBLE);
        companyWebView.setVisibility(View.VISIBLE);
        imageFillWidth(companyWebView, info);
    }


    /**
     * 获取系统相关信息
     */
    private void requestSystemConfig() {
        ApiRepository.getInstance().requestSystemConfig().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<SystemSettingEntity>>() {
                    @Override
                    public void onRequestNext(BaseEntity<SystemSettingEntity> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                SystemSettingEntity settingEntity = entity.data;
                                if (settingEntity != null) {
                                    String register = settingEntity.getRegister();
                                    String phone = settingEntity.getKefu();
                                    if (TextUtils.isEmpty(settingEntity.getAddress())) {
                                        settingEntity.setAddress("");
                                    }
                                    if (TextUtils.isEmpty(settingEntity.getCompany())) {
                                        settingEntity.setCompany("");
                                    }
                                    //保存注册条例
                                    SharedPreferencesUtil.put(PREF_TEL_REGISTER_KEY, register);
                                    SharedPreferencesUtil.put(PREF_TEL_PHONE_KEY, phone);
                                    SharedPreferencesUtil.put(PREF_ADDRESS_KEY, settingEntity.getAddress());
                                    companyInfo = settingEntity.getCompany();
                                    //显示公司信息
                                    showCompanyInfo(companyInfo);
                                }
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUSET_CODE_SETTLE:
                if (NetworkUtil.isConnected(mContext)) {
                    if (mGoodsId < 0) {
                        return;
                    }
                    refreshGoodsDetail(mGoodsId);
                }
                break;
            case REQUSET_CODE_TUAN_LIST:
                if (NetworkUtil.isConnected(mContext)) {
                    if (mGoodsId < 0) {
                        return;
                    }
                    refreshGoodsDetail(mGoodsId);
                }
                break;
            default:

                break;
        }
    }


    /**
     * 刷新商品详情(重新请求)
     */
    private void refreshGoodsDetail(int goodsId) {
        ApiRepository.getInstance().getGoodsDetail(goodsId).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
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
                                showErrorLayoutMsg(entity.msg);
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


    public void startActivityAfterLogin(Intent intent) {
        //未登录（这里用自己的登录逻辑去判断是否未登录）
        if (!AccountInfoHelper.getInstance().isLogin()) {
            ComponentName componentName = new ComponentName(mContext, LoginActivity.class);
            intent.putExtra("className", intent.getComponent().getClassName());
            intent.setComponent(componentName);
            super.startActivity(intent);
        } else {
            super.startActivity(intent);
        }
    }


    private void loadLimitSaleCount(Goods goods) {
        if (goods.getQuota() > 0) {
            limitSaleCount = goods.getQuota_surplus();
        } else {
            //说明商品不是限购商品 无需限制
            limitSaleCount = Integer.MAX_VALUE;
        }
    }


    private void showSaleCountByCondition(Goods detail) {
        if (detail == null) {
            return;
        }
        double minLinePrice = detail.getGoods_min_line_price();
        if (minLinePrice <= 0) {
            tvSaleCount.setVisibility(View.GONE);
            return;
        }
        double sale = detail.getGoods_min_price() / minLinePrice;
        sale = sale * 10;
        double limitMin = 0.1;
        double limitMax = 10;
        if (sale <= 0 || sale < limitMin || sale >= limitMax) {
            tvSaleCount.setVisibility(View.GONE);
            return;
        }
        String onSaleValue = TourCooUtil.formatNumber(sale, 1, true) + "折";
        tvSaleCount.setText(onSaleValue);
        tvSaleCount.setVisibility(View.VISIBLE);
    }

}

