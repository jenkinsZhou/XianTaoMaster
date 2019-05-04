package com.tourcoo.xiantao.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.adapter.ReturnGoodsAdapter;
import com.tourcoo.xiantao.adapter.UploadImageAdapter;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.util.SizeUtil;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.action.ActionSheetDialog;
import com.tourcoo.xiantao.core.widget.core.action.BaseDialog;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :退货页面
 * @company :途酷科技
 * @date 2019年04月28日15:45
 * @Email: 971613168@qq.com
 */
public class ReturnGoodsActivity extends BaseTourCooTitleActivity implements View.OnClickListener {
    private List<Goods> mGoodsList;
    private UploadImageAdapter uploadImageAdapter;
    private RecyclerView mRecyclerView;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();
    private ReturnGoodsAdapter mGoodsAdapter;
    private RecyclerView goodsRecyclerView;
    private int mOrderId;
    private String images;

    private TextView tvReturnType;

    private TextView tvReturnReason;

    private String[] returnTypeArray = new String[]{"退货退款", "仅退款",};

    private String[] returnReasonArray = new String[]{"拍错/多拍/不想要", "协商一致退款", "缺货", "未按约定时间发货", "其他"};
    /**
     * 商品列表集合
     */
    public static final String EXTRA_GOODS_LIST = "EXTRA_GOODS_LIST";
    public static final String EXTRA_ORDER_ID = "EXTRA_ORDER_ID";
    private EditText etDetail;

    @Override
    public int getContentLayout() {
        return R.layout.activity_return_goods;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initView(Bundle savedInstanceState) {
        mGoodsList = (List<Goods>) getIntent().getSerializableExtra(EXTRA_GOODS_LIST);
        for (Goods goods : mGoodsList) {
            TourCooLogUtil.i(TAG, TAG + "goods id = " + "" + goods.getId());
        }
        etDetail = findViewById(R.id.etDetail);
        mOrderId = getIntent().getIntExtra(EXTRA_ORDER_ID, -1);
        mRecyclerView = findViewById(R.id.rvUploadImage);
        findViewById(R.id.btnCommit).setOnClickListener(this);
        goodsRecyclerView = findViewById(R.id.goodsRecyclerView);
        tvReturnType = findViewById(R.id.tvReturnType);
        tvReturnType.setOnClickListener(this);
        tvReturnReason = findViewById(R.id.tvReturnReason);
        tvReturnReason.setOnClickListener(this);

        //商品列表适配器
        mGoodsAdapter = new ReturnGoodsAdapter();
        goodsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mGoodsAdapter.bindToRecyclerView(goodsRecyclerView);
        mGoodsAdapter.setNewData(mGoodsList);
        TourCooLogUtil.i("退单商品列表", mGoodsList);
        init();
        initAdapterClick();
    }


    private void init() {
        GridLayoutManager manager = new GridLayoutManager(mContext, 4, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        uploadImageAdapter = new UploadImageAdapter(mContext, onAddPicClickListener);
        uploadImageAdapter.setOnEmptyCallBack(new UploadImageAdapter.OnEmptyCallBack() {
            @Override
            public void empty() {
//                showAddIcon();
            }
        });
        mRecyclerView.setAdapter(uploadImageAdapter);
        initItemClick();
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("申请退单");
    }

    private UploadImageAdapter.OnAddPictureClickListener onAddPicClickListener = new UploadImageAdapter.OnAddPictureClickListener() {
        @Override
        public void onAddPicClick() {
            selectPic();
        }
    };


    private void selectPic() {
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.PicturePickerStyle)
                // 最大图片选择数量
                .maxSelectNum(8)
                // 最小选择数量
                .minSelectNum(1)
                // 每行显示个数
                .imageSpanCount(4)
                // 多选 or 单选
                .selectionMode(PictureConfig.MULTIPLE)
                // 是否可预览图片
                .previewImage(true)
                // 是否可播放音频
                .enablePreviewAudio(false)
                // 是否显示拍照按钮
                .isCamera(true)
                // 图片列表点击 缩放效果 默认true
                .isZoomAnim(true)
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                // 是否裁剪
                .enableCrop(false)
                // 是否压缩
                .compress(true)
                //同步true或异步false 压缩 默认同步
                .synOrAsy(true)
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                // glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .glideOverride(160, 160)
                // 是否显示uCrop工具栏，默认不显示
                .hideBottomControls(false)
                // 是否显示gif图片
                .isGif(false)
                // 裁剪框是否可拖拽
                .freeStyleCropEnabled(false)
                // 是否传入已选图片
                .selectionMedia(selectList)
                // 小于100kb的图片不压缩
                .minimumCompressSize(100)
                //结果回调onActivityResult code
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    private void initItemClick() {
        uploadImageAdapter.setOnItemClickListener(new UploadImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            PictureSelector.create(mContext).themeStyle(R.style.PicturePickerStyle).openExternalPreview(position, selectList);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    uploadImageAdapter.setList(selectList);
                    imageList.clear();
                    for (LocalMedia localMedia : selectList) {
                        imageList.add(localMedia.getCompressPath());
                    }
                    uploadImageAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }


    private void initAdapterClick() {
        mGoodsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Goods goods = mGoodsAdapter.getData().get(position);
                if (goods.isSelect()) {
                    goods.setSelect(false);
                } else {
                    goods.setSelect(true);
                }
                mGoodsAdapter.notifyDataSetChanged();
            }
        });


    }


    /**
     * 取消订单
     *
     * @param goodIds
     */
    private void requestReturnGoods(String goodIds) {
        if (mOrderId < 0) {
            ToastUtil.showFailed("未获取到订单id");
            return;
        }
        String detail = etDetail.getText().toString();
        String reason = getTextValue(tvReturnReason);
        String type = getTextValue(tvReturnType);
        TourCooLogUtil.i(TAG, TAG + "操作的订单id:" + mOrderId);
        ApiRepository.getInstance().requestReturnGoods(mOrderId, goodIds, detail, images, reason, type).compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseLoadingObserver<BaseEntity>() {
                    @Override
                    public void onRequestNext(BaseEntity entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS) {
                                setResult(RESULT_OK);
                                ToastUtil.showSuccess(entity.msg);
                            } else {
                                ToastUtil.showFailed(entity.msg);
                            }
                        }
                    }
                });
    }


    private void showTypeDialog() {
        new ActionSheetDialog.ListIOSBuilder(this)
                .addItems(returnTypeArray)
                .setItemsTextColorResource(R.color.greenCommon)
                .setBackgroundColor(TourCooUtil.getColor(R.color.whiteCommon))
                .setCancel(R.string.cancel)
                .setCancelMarginTop(SizeUtil.dp2px(8))
                .setCancelTextColorResource(R.color.greenCommon)
                .setOnItemClickListener(mOnItemClickListener)
                .create()
//                .setDimAmount(0.6f)
//                .setAlpha(0.6f)
                .show();
    }


    private ActionSheetDialog.OnItemClickListener mOnItemClickListener = new ActionSheetDialog.OnItemClickListener() {
        @Override
        public void onClick(BaseDialog dialog, View itemView, int position) {
            setTextValue(tvReturnType, returnTypeArray[position]);
            dialog.dismiss();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvReturnType:
                showTypeDialog();
                break;
            case R.id.tvReturnReason:
                showReasonDialog();
                break;
            case R.id.btnCommit:
                doReturnGoods();
                break;
            default:
                break;
        }
    }


    private void setTextValue(TextView textView, String value) {
        textView.setText(value);
    }

    private String getTextValue(TextView textView) {
        return textView.getText().toString();
    }


    private void doReturnGoods() {
        String defaultValue = "点击选择";
        if (defaultValue.equals(getTextValue(tvReturnType))) {
            ToastUtil.show("请选择退货方式");
            return;
        }
        if (defaultValue.equals(getTextValue(tvReturnReason))) {
            ToastUtil.show("请选择退货原因");
            return;
        }
        List<String> idList = new ArrayList<>();
        for (Goods goods : mGoodsAdapter.getData()) {
            TourCooLogUtil.i(TAG, TAG + ":" + goods.isSelect());
            if (goods.isSelect()) {
                idList.add(goods.getId() + "");
            }
        }
        if (idList.isEmpty()) {
            ToastUtil.show("请选择退货商品");
            return;
        }
        String goodsIds = StringUtils.join(idList, ",");
        TourCooLogUtil.i(TAG, TAG + ":" + "退货id:" + goodsIds);
        requestReturnGoods(goodsIds);

    }


    private void showReasonDialog() {
        new ActionSheetDialog.ListIOSBuilder(this)
                .addItems(returnReasonArray)
                .setItemsTextColorResource(R.color.greenCommon)
                .setBackgroundColor(TourCooUtil.getColor(R.color.whiteCommon))
                .setCancel("关闭")
                .setCancelMarginTop(SizeUtil.dp2px(8))
                .setCancelTextColorResource(R.color.greenCommon)
                .setOnItemClickListener(mReasonOnItemClickListener)
                .create()
//                .setDimAmount(0.6f)
//                .setAlpha(0.6f)
                .show();
    }


    private ActionSheetDialog.OnItemClickListener mReasonOnItemClickListener = new ActionSheetDialog.OnItemClickListener() {
        @Override
        public void onClick(BaseDialog dialog, View itemView, int position) {
            setTextValue(tvReturnReason, returnReasonArray[position]);
            dialog.dismiss();
        }
    };


}
