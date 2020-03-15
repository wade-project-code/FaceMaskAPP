package com.wade.facemaskapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.wade.facemaskapp.utils.ToolUtil;
import com.wade.facemaskapp.view.ActMain;

public class ActWelcome extends AppCompatActivity {
    private static final int GOTO_ACT_MAIN = 0;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GOTO_ACT_MAIN:
                    Intent intent = new Intent();
                    intent.setClass(ActWelcome.this, ActMain.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        if(ToolUtil.isCheckNetwork(ActWelcome.this)){
            mHandler.sendEmptyMessageDelayed(GOTO_ACT_MAIN, 3000);
        }
    }
}
