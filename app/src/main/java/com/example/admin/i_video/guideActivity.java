package com.example.admin.i_video;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class guideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private List<View> list;
    private ViewPager mViewPager;
    private ImageView[] dot;
    private int lastDot=0;//上一个点的位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mSharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        editor=mSharedPreferences.edit();
        //!!!!!!!!!!!!!!!!!!!!为了调试方便把isFirst设置为true!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        editor.putBoolean("isFirst",false);
        editor.commit();

        init();
        mViewPager.setAdapter(new myPagerAdapter());
    }

    private void init() {
        LayoutInflater mLayoutInflater = LayoutInflater.from(this);
        list = new ArrayList<>();
        list.add(mLayoutInflater.inflate(R.layout.guide_1,null));
        list.add(mLayoutInflater.inflate(R.layout.guide_2,null));
        list.add(mLayoutInflater.inflate(R.layout.guide_3,null));
        mViewPager= (ViewPager) findViewById(R.id.ViewPager);
        mViewPager.addOnPageChangeListener(this);
        dot=new ImageView[]{(ImageView) findViewById(R.id.dot1),(ImageView) findViewById(R.id.dot2),(ImageView) findViewById(R.id.dot3)};
        //让第一个点为被选中。
        dot[0].setEnabled(false);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        dot[position].setEnabled(false);
        dot[lastDot].setEnabled(true);
        lastDot=position;

        if(position==2){
            Button button_guide = (Button)list.get(position).findViewById(R.id.button_guide);
            button_guide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(guideActivity.this,homeActivity.class);
                    startActivity(i);
                }
            });
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class myPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView(list.get(position));
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }
}
