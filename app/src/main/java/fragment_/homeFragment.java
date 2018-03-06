package fragment_;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.i_video.R;
import com.example.admin.i_video.homeActivity;
import com.example.admin.i_video.infoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017/10/9.
 */

public class homeFragment extends android.support.v4.app.Fragment {

    private View v;
    private ViewPager mViewPager;
    private List<Integer> iv_id_list;
    private List<ImageView> images;
    private ImageView[] dot;
    private int last_dot;
    private boolean is_loop = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e("", "" + msg.arg1);
            mViewPager.setCurrentItem(msg.arg1);

        }
    };
    private int i = 0;//用于轮播

    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iv_id_list = new ArrayList<>();
        iv_id_list.add(R.drawable.san_francisco_supreme);
        iv_id_list.add(R.drawable.supreme_universe);
        iv_id_list.add(R.drawable.tokyo_supreme);


        images = new ArrayList<>();
        for (int i = 0; i < iv_id_list.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(iv_id_list.get(i));
            images.add(imageView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, null);

        mViewPager = (ViewPager) v.findViewById(R.id.ViewPager);
        mViewPager.setAdapter(new myPagerAdapter());

        dot = new ImageView[]{(ImageView) v.findViewById(R.id.dot_f_1),
                (ImageView) v.findViewById(R.id.dot_f_2),
                (ImageView) v.findViewById(R.id.dot_f_3)};

        dot[0].setEnabled(false);
        last_dot = 0;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dot[position].setEnabled(false);
                dot[last_dot].setEnabled(true);
                last_dot = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        initRecyclerView();

        return v;
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) v.findViewById(R.id.home_RecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder) {
                MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
                String name = (String) myViewHolder.mTextView.getText();

                Intent intent = new Intent( getActivity() , infoActivity.class);

                if(name != null){
                    intent.putExtra("name",name);
                    Toast.makeText(getActivity(),name,Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);

            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder) {

            }
        });


        recyclerView.setAdapter(new myRecyclerAdapter());
    }

    private void showNormalDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(R.drawable.b1);
        normalDialog.setTitle("我是一个普通Dialog");
        normalDialog.setMessage("你要点击哪一个按钮呢?");
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //...To-do
                Toast.makeText(getActivity(),"Love you",Toast.LENGTH_SHORT).show();
            }
        });
        normalDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-doThank
                        Toast.makeText(getActivity(),"Fuck you",Toast.LENGTH_SHORT).show();
                    }
                });
        // 显示
        normalDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();

        new Thread(new Runnable() {

            @Override
            public void run() {
                while (is_loop) {
                    try {
                        Message ms;
                        ms = handler.obtainMessage();
                        ms.arg1 = i;
                        handler.sendMessage(ms);

                        Thread.sleep(4000);
                        i++;
                        if (i > 2) {
                            i = 0;
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        }).start();
    }

    private class myPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images.get(position));
            return images.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(images.get(position));
        }

        @Override
        public int getCount() {

            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }
    }

    private class myRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_home,parent,false));
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mTextView.setText("产品"+position);
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public MyViewHolder(View view)
        {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.product_name);
        }
    }
}
