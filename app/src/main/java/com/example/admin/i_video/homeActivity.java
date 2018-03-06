package com.example.admin.i_video;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import MyView.RoundView;
import MyView.TouchPullView.MyTouchPUllView;
import fragment_.FlowFragment;
import fragment_.homeFragment;


public class homeActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private LinearLayout mLinearLayout;
    private MyTouchPUllView myTouchPUllView;
    private ViewPager mViewPager;
    private List<android.support.v4.app.Fragment> fragment_list;
    private List<String> fragment_title_list;
    private TabLayout mTabLayout;

    private RoundView roundView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar("Home", 0);
        mDrawerLayout = FindViewById(R.id.drawer_layout);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        initTab();

        myTouchPUllView = FindViewById(R.id.mTouchPullView);
        mLinearLayout = FindViewById(R.id.ll_home);
        InitMyTouchPUllView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initLogin();
    }

    private SharedPreferences.Editor editor;

    private void initLogin() {

        editor = getSharedPreferences("config",MODE_PRIVATE).edit();
        editor.putBoolean("isLogin",false);

        roundView = (RoundView) findViewById(R.id.roundView);
        if(roundView != null){
            roundView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!getSharedPreferences("config",MODE_PRIVATE).getBoolean("isLogin",false)){
                        startActivity(new Intent(homeActivity.this,LoginActivity.class));
                        homeActivity.this.editor.putBoolean("isLogin",true);
                    }
                }
            });
        }

    }


    private float mProgress = 0; //进度
    private float start = 0;//开始的Y
    private int maxPull = 600;//最大拖动距离
    private void InitMyTouchPUllView() {

        mLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        start = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float Y = event.getY();
                        if(Y >start){
                            mProgress =  (Y - start) / maxPull ;
                            myTouchPUllView.setProgress(mProgress);
                         }
                        return true;
                    case MotionEvent.ACTION_UP:
                        mProgress = 0;
                        myTouchPUllView.release();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }


    private void initTab() {
        mTabLayout = FindViewById(R.id.TabLayout);
        mViewPager = FindViewById(R.id.home_ViewPager);

        initFragment();

        mViewPager.setAdapter(new FragmentPagerAdapter(homeActivity.this.getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return fragment_list.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return fragment_title_list.get(position);
            }

            @Override
            public int getCount() {
                return fragment_list.size();
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initFragment() {
        fragment_list = new ArrayList<>();
        fragment_title_list = new ArrayList<>();

        fragment_list.add(new homeFragment());
        fragment_title_list.add("推荐");
        fragment_list.add(new FlowFragment());
        fragment_title_list.add("发现");
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("...")
                .setMessage("确定要退出吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        System.exit(0);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_LONG).show();
                    }
                }).create().show();
    }


}
