package com.tourcoo.xiantao.ui.account;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tourcoo.xiantao.MainActivity;
import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.common.RequestConfig;
import com.tourcoo.xiantao.core.frame.manager.GlideManager;
import com.tourcoo.xiantao.core.frame.retrofit.UploadRequestListener;
import com.tourcoo.xiantao.core.frame.util.SizeUtil;
import com.tourcoo.xiantao.core.helper.AccountInfoHelper;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;
import com.tourcoo.xiantao.core.widget.core.util.TourCooUtil;
import com.tourcoo.xiantao.core.widget.core.view.radius.RadiusEditText;
import com.tourcoo.xiantao.core.widget.core.view.titlebar.TitleBarView;
import com.tourcoo.xiantao.entity.BaseEntity;
import com.tourcoo.xiantao.entity.upload.UploadEntity;
import com.tourcoo.xiantao.entity.user.UserInfo;
import com.tourcoo.xiantao.retrofit.repository.ApiRepository;
import com.tourcoo.xiantao.retrofit.repository.UploadProgressBody;
import com.tourcoo.xiantao.ui.BaseTourCooTitleActivity;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.builder.TimePickerBuilder;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.tourcoo.xiantao.widget.bigkoo.pickerview.view.TimePickerView;
import com.tourcoo.xiantao.widget.dialog.EmiAlertDialog;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tourcoo.xiantao.core.common.RequestConfig.CODE_REQUEST_SUCCESS;

/**
 * @author :JenkinsZhou
 * @description :个人资料
 * @company :途酷科技
 * @date 2019年03月26日17:35
 * @Email: 971613168@qq.com
 */
public class PersonalDataActivity extends BaseTourCooTitleActivity implements View.OnClickListener {
    private CircleImageView crvAvatar;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();
    private KProgressHUD hud;
    private TextView tvNickName;
    private TextView tvBirthday;
    private TextView tvMobile;
    private TimePickerView pvTime;
    /**
     * 回调回来的图片ur集合
     */
    private String imageUrl = "";
    private MyHandler mHandler = new MyHandler(this);
    private Message message;

    @Override
    public int getContentLayout() {
        return R.layout.activity_personal_data;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        crvAvatar = findViewById(R.id.crvAvatar);
        tvNickName = findViewById(R.id.tvNickName);
        tvBirthday = findViewById(R.id.tvBirthday);
        tvMobile = findViewById(R.id.tvMobile);
        crvAvatar.setOnClickListener(this);
        tvBirthday.setOnClickListener(this);
        initTimePicker();
    }

    @Override
    public void loadData() {
        super.loadData();
        showCurrentInfo(AccountInfoHelper.getInstance().getUserInfo());
    }

    @Override
    public void setTitleBar(TitleBarView titleBar) {
        super.setTitleBar(titleBar);
        titleBar.setTitleMainText("个人资料");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crvAvatar:
                selectPic();
                break;
            case R.id.tvNickName:
                showNickNameAlert();
                break;
            case R.id.tvBirthday:
                pvTime.show();
                break;
            default:
                break;
        }
    }


    private void selectPic() {
        PictureSelector.create(mContext)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.PicturePickerStyle)
                // 最大图片选择数量
                .maxSelectNum(1)
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
                .enableCrop(true)
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


    /**
     * 显示当前信息
     */
    private void showCurrentInfo(UserInfo userInfo) {
        if (userInfo == null) {
            TourCooLogUtil.e(TAG, "用户信息为null");
            userInfo = new UserInfo();
        }
        if (TextUtils.isEmpty(userInfo.getNickname())) {
            tvNickName.setText("未填写");
        } else {
            tvNickName.setText(userInfo.getNickname());
        }
        if (TextUtils.isEmpty(userInfo.getMobile())) {
            tvMobile.setText("未填写");
        } else {
            String maskNumber = userInfo.getMobile();
            if (TourCooUtil.isMobileNumber(userInfo.getMobile())) {
                maskNumber = userInfo.getMobile().substring(0, 3) + "****" + maskNumber.substring(7, userInfo.getMobile().length());
            }
            tvMobile.setText(maskNumber);
        }
        String url = TourCooUtil.getUrl(userInfo.getAvatar());
        showAvatar(url);
    }


    private void showAvatar(String url) {
        GlideManager.loadImg(url, crvAvatar, TourCooUtil.getDrawable(R.mipmap.img_default_avatar));
    }


    /**
     * Dialog 模式下，在底部弹出
     */
    private void initTimePicker() {
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(PersonalDataActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                Log.i("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                //默认设置false ，内部实现将DecorView 作为它的父控件。
                .isDialog(true)
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("pvTime", "onCancelClickListener");
                    }
                })
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                //修改动画样式
                dialogWindow.setWindowAnimations(R.style.picker_view_slide_anim);
                //改成Bottom,底部显示
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setDimAmount(0.1f);
            }
        }
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private void showNickNameAlert() {
        final RadiusEditText editText = new RadiusEditText(mContext);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editText.getDelegate()
                .setTextColor(Color.GRAY)
                .setRadius(6f)
                .setBackgroundColor(TourCooUtil.getColor(R.color.colorWhite))
                .setStrokeColor(Color.GRAY)
                .setStrokeWidth(SizeUtil.dp2px(0.5f));
        editText.setMinHeight(SizeUtil.dp2px(40));
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setPadding(SizeUtil.dp2px(12), 0, SizeUtil.dp2px(12), 0);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        editText.setHint("请输入内容");
        editText.setLayoutParams(params);
        new EmiAlertDialog.DividerIosBuilder(this)
                .setTitle("输入信息")
                .setBackgroundColor(TourCooUtil.getColor(R.color.whiteCommon))
                .setTitleTextColorResource(R.color.colorAlertTitle)
                .setView(editText)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(text)) {
                            return;
                        }

                    }
                })
                .create()
//                .setDimAmount(0.6f)
                .show();
    }

    private String getNickName() {
        return tvNickName.getText().toString();
    }


    /**
     * 上传图片
     *
     * @param imageList
     */
    private void uploadImage(List<String> imageList) {
        if (imageList == null || imageList.isEmpty()) {
            ToastUtil.show("您还没选择图片");
            return;
        }
        File file;
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        //注意，file是后台约定的参数，如果是多图，files，如果是单张图片，file就行
        for (String imagePath : imageList) {
            //这里上传的是多图
            file = new File(imagePath);
            builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        RequestBody requestBody = builder.build();

        UploadProgressBody uploadProgressBody = new UploadProgressBody(requestBody, new UploadRequestListener() {
            @Override
            public void onProgress(float progress, long current, long total) {
                message = mHandler.obtainMessage();
                message.what = 1;
                message.arg1 = (int) (progress * 100);
                mHandler.sendMessage(message);

            }

            @Override
            public void onFail(Throwable e) {
                TourCooLogUtil.e("异常：" + e.toString());
                closeHudProgressDialog();
            }
        });
        showHudProgressDialog();
        ApiRepository.getInstance().getApiService().uploadFiles(uploadProgressBody).enqueue(new Callback<BaseEntity<UploadEntity>>() {
            @Override
            public void onResponse(Call<BaseEntity<UploadEntity>> call, Response<BaseEntity<UploadEntity>> response) {
                closeHudProgressDialog();
                BaseEntity<UploadEntity> entity = response.body();
                if (entity != null) {
                    if (entity.code == CODE_REQUEST_SUCCESS && entity.data != null) {
                        //todo
                        TourCooLogUtil.i(TAG, JSON.toJSONString(entity));
                        TourCooLogUtil.i("图片URL：", entity.data.getUrl());
                    } else {
                        ToastUtil.showFailed(entity.msg);
                    }
                }

            }

            @Override
            public void onFailure(Call<BaseEntity<UploadEntity>> call, Throwable t) {
                Toast.makeText(mContext, "图片上传失败，稍后重试", Toast.LENGTH_SHORT).show();
                closeHudProgressDialog();
            }
        });
    }


    private static class MyHandler extends Handler {
        WeakReference<PersonalDataActivity> personalDataActivity;

        MyHandler(PersonalDataActivity dataActivity) {
            personalDataActivity = new WeakReference<>(dataActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    personalDataActivity.get().updateProgress(msg.arg1);
                    break;
                default:
                    break;
            }
        }
    }


    private void initProgressDialog() {
        hud = KProgressHUD.create(mContext)
                .setStyle(KProgressHUD.Style.PIE_DETERMINATE)
                .setCancellable(false)
                .setAutoDismiss(false)
                .setMaxProgress(100);
    }


    private void showHudProgressDialog() {
        if (hud != null) {
            hud.setProgress(0);
        } else {
            initProgressDialog();
        }
        hud.show();
    }

    private void updateProgress(int progress) {
        TourCooLogUtil.i("进度：" + progress);
        if (hud != null) {
            hud.setProgress(progress);
        }
    }

    private void closeHudProgressDialog() {
        if (hud != null && hud.isShowing()) {
            hud.setProgress(0);
            hud.dismiss();
        }
        hud = null;
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
                    imageList.clear();
                    for (LocalMedia localMedia : selectList) {
                        imageList.add(localMedia.getCompressPath());
                    }
                    //todo
                    for (String s : imageList) {
                        TourCooLogUtil.i(TAG, "图片:" + s);
                    }
                    uploadImage(imageList);
                    break;
                default:
                    break;
            }
        }
    }
}
