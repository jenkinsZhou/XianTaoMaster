package com.tourcoo.xiantao;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.tourcoo.xiantao.core.log.TourcoolLogUtil;
import com.tourcoo.xiantao.core.util.ToastUtil;

import androidx.appcompat.app.AppCompatActivity;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_main);
        context = this;
        TourcoolLogUtil.d("测试安达市大所多啥所发生奥术大师大大所多奥术大师大所多的");
    }




    DialogInterface.OnClickListener onAlertClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String msg;
            switch (which) {
                case BUTTON_NEGATIVE:
                    msg = "否定";
                    ToastUtil.showFailed(msg);
                    break;
                case BUTTON_POSITIVE:
                    msg = "肯定";
                    ToastUtil.showSuccess(msg);
                    break;
                case BUTTON_NEUTRAL:
                    msg = "中性";
                    break;
                default:
                    break;
            }

        }
    };
}
