package com.example.admin.i_video;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by Admin on 2017/10/6.
 */

public class mainActivity extends Activity {

    private SharedPreferences mSharedPreferences;
    private static final int GO_HOME =1;//跳转主页
    private static final int GO_GUIDE =2;//跳转引导页
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_HOME:
                    Intent intent1 = new Intent(mainActivity.this,homeActivity.class);
                    startActivity(intent1);
                    break;
                case GO_GUIDE:
                    Intent intent2 = new Intent(mainActivity.this,guideActivity.class);
                    startActivity(intent2);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences=getSharedPreferences("config",MODE_PRIVATE);
        mSharedPreferences.getBoolean("isFirst",true);
        boolean ifFirst = mSharedPreferences.getBoolean("isFirst",true);
        if (ifFirst){
            mHandler.sendEmptyMessageDelayed(GO_GUIDE,500);//延迟一秒
        }else{
            mHandler.sendEmptyMessageDelayed(GO_HOME,500);//延迟一秒
        }
    }

}
