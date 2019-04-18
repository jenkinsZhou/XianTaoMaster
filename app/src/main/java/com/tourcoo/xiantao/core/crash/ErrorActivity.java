
package com.tourcoo.xiantao.core.crash;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tourcoo.xiantao.R;
import com.tourcoo.xiantao.core.log.TourCooLogUtil;


/**
 * @author :zhoujian
 * @description : 异常页面
 * @company :途酷科技
 * @date: 2017年07月04日下午 03:41
 * @Email: 971613168@qq.com
 */
public class ErrorActivity extends Activity {
    private static final String TAG = "ErrorActivity";
    private String errorInformation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_error_activity);
        errorInformation = CrashManager.getAllErrorDetailsFromIntent(ErrorActivity.this, getIntent());
        TourCooLogUtil.e(TAG, "errorInformation:" + errorInformation);
        //查看
        Button btnCheck = findViewById(R.id.btn_check);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ErrorActivity.this)
                        .setTitle("错误详情")
                        .setCancelable(true)
                        .setMessage(errorInformation)
                        .setPositiveButton("返回", null)
                        .setNeutralButton("复制", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                copyErrorToClipboard();
                                Toast.makeText(ErrorActivity.this, "已复制到剪切板", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
        //上传
        Button moreInfoButton = findViewById(R.id.custom_activity_on_crash_error_activity_more_info_button);
        if (CrashManager.isShowErrorDetailsFromIntent(getIntent())) {
            moreInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ErrorActivity.this, "执行上传", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            moreInfoButton.setVisibility(View.GONE);
        }

        int defaultErrorActivityDrawableId = CrashManager.getDefaultErrorActivityDrawableIdFromIntent(getIntent());
        ImageView errorImageView = (findViewById(R.id.custom_activity_on_crash_error_activity_image));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            errorImageView.setImageDrawable(getResources().getDrawable(defaultErrorActivityDrawableId, getTheme()));
        } else {
            errorImageView.setImageDrawable(getResources().getDrawable(defaultErrorActivityDrawableId));
        }
        /*CrashWriter crashWriter = new CrashWriter(this);
        //保存崩溃日志到本地
        crashWriter.saveCrashLog(errorInformation);*/
    }


    /**
     * 复制到剪切板
     */
    private void copyErrorToClipboard() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(getString(R.string.custom_activity_on_crash_error_activity_error_details_clipboard_label), errorInformation);
            clipboard.setPrimaryClip(clip);
        } else {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            clipboard.setText(errorInformation);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(ErrorActivity.this)
                    .setTitle("提示")
                    .setCancelable(true)
                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNeutralButton("重新启动", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Class<? extends Activity> restartActivityClass = CrashManager.getRestartActivityClassFromIntent(getIntent());
                            if (restartActivityClass != null) {
                                Intent intent = new Intent(ErrorActivity.this, restartActivityClass);
                                CrashManager.restartApplicationWithIntent(ErrorActivity.this, intent);
                            } else {
                                CrashManager.closeApplication(ErrorActivity.this);
                            }
                        }
                    })
                    .show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
