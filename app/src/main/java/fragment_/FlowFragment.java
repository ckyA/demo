package fragment_;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.example.admin.i_video.R;

import java.util.ArrayList;
import java.util.List;



public class FlowFragment extends Fragment {

    private RecyclerView mRecyclerView;
//    private List<String> Img_URL = new ArrayList<>();
    private String img_num;
    private int item_num = 20;
    //缓存
    private LruCache<String,Bitmap> mCache;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //分配可用内存的四分之一用于缓存
        mCache = new LruCache<String,Bitmap>((int) Runtime.getRuntime().maxMemory()/4){
            //重写该方法，否则默认返回参数的个数
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_flow_image, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(new MyAdapter());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getActivity()).inflate(R.layout.item_flow_image, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if(position%10 > 5) item_num = item_num + 20;


            img_num = "" + (position+10);

            Glide.with(FlowFragment.this)
                    .load("https://picsum.photos/500/"+(Math.pow(position,9)%300+300)+"/?image="+img_num)
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return item_num;
            //return Img_URL.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView mImageView;

        public MyViewHolder(View view)
        {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.item_img);

        }
    }
}
