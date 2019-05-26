package com.tourcoo.xiantao.ui.mine;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ImageUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.constant.WxConfig;
import com.tourcoo.xiantao.core.frame.retrofit.BaseLoadingObserver;
import com.tourcoo.xiantao.core.frame.retrofit.BaseObserver;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.SizeUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.core.widget.custom.ShareGoodsPopupWindow;
import com.tourcoo.xiantao.core.widget.custom.SharePopupWindow;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.goods.Goods;
import com.tourcoo.xiantao.qrcode.QRCodeUtil;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.trello.rxlifecycle3.android.ActivityEvent;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :我的邀请码
 * @company :途酷科技
 * @date 2019年05月10日10:22
 * @Email: 971613168@qq.com
 */
public class MyInviteCodeActivity extends BaseTourCooTitleActivity {
    private TextView tvInviteCode;
    private LinearLayout llContentView;
    private StatusLayoutManager mStatusLayoutManager;
    private TextView tvErrorContent;
    private ImageView ivQrCode;
    private IWXAPI api;
    private SharePopupWindow sharePopupWindow;
    private String mContent;


    @Override
    public int getContentLayout() {
        return R.layout.activity_my_invite_code;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tvInviteCode = findViewById(R.id.tvInviteCode);
        llContentView = findViewById(R.id.llContentView);
        ivQrCode = findViewById(R.id.ivQrCode);
        setupStatusLayoutManager();
        api = WXAPIFactory.createWXAPI(mContext, WxConfig.APP_ID);
        sharePopupWindow = new SharePopupWindow(mContext, true, "分享邀请码");
        mStatusLayoutManager.showLoadingLayout();
        if (AccountInfoHelper.getInstance().getUserInfo() != null) {
            tvInviteCode.setText(AccountInfoHelper.getInstance().getUserInfo().getMobile());
        }
        tvInviteCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = tvInviteCode.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    return;
                }
                copyToClipboard(code);
                ToastUtil.showSuccess("已复制到粘贴板");
            }
        });
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("邀请码");
        titleBar.setRightTextDrawableWidth(SizeUtil.dp2px(20));
        titleBar.setRightTextDrawableHeight(SizeUtil.dp2px(19));
        titleBar.setRightTextDrawable(TourCooUtil.getDrawable(R.mipmap.ic_share));
        titleBar.getTextView(Gravity.RIGHT).setVisibility(View.INVISIBLE);
        titleBar.setOnRightTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        requestInvitecode();
    }

    /**
     * 复制内容到剪切板
     *
     * @param copyStr
     * @return
     */
    private boolean copyToClipboard(String copyStr) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", copyStr);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private void requestInvitecode() {
        ApiRepository.getInstance().requestInvitecode().compose(bindUntilEvent(ActivityEvent.DESTROY)).
                subscribe(new BaseObserver<BaseEntity<String>>() {
                    @Override
                    public void onRequestNext(BaseEntity<String> entity) {
                        if (entity != null) {
                            if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                                tvInviteCode.setText(entity.data);
                                mStatusLayoutManager.showSuccessLayout();
                                mTitleBar.getTextView(Gravity.RIGHT).setVisibility(View.VISIBLE);
                                mContent = "http://www.ahxtao.com/";
                                generateQrcodeAndDisplay(mContent);
                            } else {
                                mStatusLayoutManager.showErrorLayout();
                                tvErrorContent.setText(TourCooUtil.getNotNullValue(entity.msg));
                            }
                        }
                    }

                    @Override
                    public void onRequestError(Throwable e) {
                        super.onRequestError(e);
                        tvInviteCode.setText("");
                        mStatusLayoutManager.showErrorLayout();
                        tvErrorContent.setText(TourCooUtil.getNotNullValue("请求异常"));
                    }
                });
    }


    private void setupStatusLayoutManager() {
        View view = inflateLayout(R.layout.custom_no_invite_code);
        tvErrorContent = view.findViewById(R.id.tvErrorContent);
        mStatusLayoutManager = new StatusLayoutManager.Builder(llContentView)
                // 设置默认布局属性
//                .setDefaultEmptyText("空白了，哈哈哈哈")
//                .setDefaultEmptyImg(R.mipmap.ic_launcher)
//                .setDefaultEmptyClickViewText("retry")
//                .setDefaultEmptyClickViewTextColor(getResources().getColor(R.color.colorAccent))
//                .setDefaultEmptyClickViewVisible(false)
//
//                .setDefaultErrorText(R.string.app_name)
//                .setDefaultErrorImg(R.mipmap.ic_launcher)
//                .setDefaultErrorClickViewText("重试一波")
//                .setDefaultErrorClickViewTextColor(getResources().getColor(R.color.colorPrimaryDark))
//                .setDefaultErrorClickViewVisible(true)
//
//                .setDefaultLayoutsBackgroundColor(Color.WHITE)

                // 自定义布局
                .setLoadingLayout(getLoadingLayout())
//                .setEmptyLayout(inflate(R.layout.layout_empty))
                .setErrorLayout(view)
                .setErrorClickViewID(R.id.tvRefresh)
//                .setLoadingLayout(R.layout.layout_loading)
//                .setEmptyLayout(R.layout.layout_empty)
//                .setErrorLayout(R.layout.layout_error)
//
//                .setEmptyClickViewID(R.id.tv_empty)
//                .setErrorClickViewID(R.id.tv_error)

                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                    }

                    @Override
                    public void onCustomerChildClick(View view) {
                     /*   if (view.getId() == R.id.tv_customer) {
                            Toast.makeText(MainActivity.this, R.string.request_access, Toast.LENGTH_SHORT).show();
                        } else if (view.getId() == R.id.tv_customer1) {
                            Toast.makeText(MainActivity.this, R.string.switch_account, Toast.LENGTH_SHORT).show();
                        }*/

                    }
                })
                .build();
    }


    /**
     * 生成二维码并显示
     */
    private Bitmap generateQrcodeAndDisplay(String content) {
        int width = SizeUtil.dp2px(120);
        int height = SizeUtil.dp2px(120);
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        String error_correction_level = getResources().getStringArray(R.array.spinarr_error_correction)[2];
        String margin = getResources().getStringArray(R.array.spinarr_margin)[0];
        Bitmap qrcode_bitmap = QRCodeUtil.createQRCodeBitmap(content, width, height, "UTF-8",
                error_correction_level, margin, Color.BLACK, Color.WHITE, null, 0.2F, null);
        ivQrCode.setImageBitmap(qrcode_bitmap);
        return qrcode_bitmap;
    }


    private void doShare(boolean isFrend) {
        // 检查手机或者模拟器是否安装了微信
        if (!api.isWXAppInstalled()) {
            ToastUtil.showFailed("您还没有安装微信");
            return;
        }
        WXImageObject wxImageObject = new WXImageObject();
        Bitmap qrCodeBitmap = generateQrcodeAndDisplay(mContent);
        // 如果没有位图，可以传null，会显示默认的图片
        if (qrCodeBitmap != null) {
            int width = qrCodeBitmap.getWidth();
            int height = qrCodeBitmap.getHeight();
            Bitmap sendBitmap = Bitmap.createScaledBitmap(qrCodeBitmap, width, height, true);
            wxImageObject.imageData = ImageUtils.bitmap2Bytes(sendBitmap, Bitmap.CompressFormat.PNG);
        }
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wxImageObject;
        req.message = msg;
        // transaction用于唯一标识一个请求（可自定义）
//        req.transaction = "webpage";
        // 上文的WXMediaMessage对象
//        req.message = msg;
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


    public void WXsharePic(String transaction, final boolean isSession, Bitmap bitmap) {
        //初始化WXImageObject和WXMediaMessage对象
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        WXImageObject imageObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        //设置缩略图
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        msg.thumbData = ImageUtils.bitmap2Bytes(scaledBitmap, Bitmap.CompressFormat.PNG);
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = transaction +(System.currentTimeMillis());
        req.message = msg;
        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = isSession ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        //调用api接口发送数据到微信
        api.sendReq(req);
       /* bitmap.recycle();
        scaledBitmap.recycle();*/
    }

    /**
     * 显示分享
     */
    private void showShare() {
        sharePopupWindow.setISharePopupWindowClickListener(new SharePopupWindow.ISharePopupWindowClickListener() {
            @Override
            public void onWxClick() {
//                doShare(true);
                sharePopupWindow.dismiss();
                Bitmap bitmap = generateQrcodeAndDisplay(mContent);
                if(bitmap == null ){
                    return;
                }
                WXsharePic("media",true,bitmap);
            }

            @Override
            public void onWxFriendClick() {
                sharePopupWindow.dismiss();
//                doShare(false);
                Bitmap bitmap = generateQrcodeAndDisplay(mContent);
                if(bitmap == null ){
                    return;
                }
                WXsharePic("media",false,bitmap);
            }
        });
        sharePopupWindow.showAtScreenBottom(llContentView);
    }


    @Override
    protected void onDestroy() {
        if (api != null) {
            api.detach();
        }
        super.onDestroy();
    }
}
