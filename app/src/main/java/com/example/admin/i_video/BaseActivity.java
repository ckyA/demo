package com.example.admin.i_video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * Created by Admin on 2017/10/6.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        toolbar=FindViewById(R.id.toolbar);
    }

    protected abstract int getLayout();

    protected <T extends android.view.View > T FindViewById(int id){
        return (T)findViewById(id);
    }

    protected void setToolbar(String title,int Logo_ID){

        if(title!=null) toolbar.setTitle(title);
        if(Logo_ID!=0)toolbar.setLogo(Logo_ID);
    }

}
