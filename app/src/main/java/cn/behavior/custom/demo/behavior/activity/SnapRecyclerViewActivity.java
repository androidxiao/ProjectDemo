package cn.behavior.custom.demo.behavior.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.behavior.custom.demo.behavior.custom.behavior.StartSnapHelper;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/12.
 */

public class SnapRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_snap_recycler_view_layout);

        final RecyclerView llSnapRv= (RecyclerView) findViewById(R.id.id_ll_snap_rv);
        final RecyclerView llStartSnapRv= (RecyclerView) findViewById(R.id.id_ll_start_snap_rv);

        init();

        LinearLayoutManager layoutManagerCenter
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        llSnapRv.setLayoutManager(layoutManagerCenter);
        SnapAdapter adapter = new SnapAdapter(this);
        llSnapRv.setAdapter(adapter);
        SnapHelper snapHelperCenter = new LinearSnapHelper();
        snapHelperCenter.attachToRecyclerView(llSnapRv);
        findViewById(R.id.id_center_snap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llSnapRv.scrollToPosition(6);
            }
        });


        LinearLayoutManager layoutManagerStart
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        llStartSnapRv.setLayoutManager(layoutManagerStart);
        SnapAdapter adapter1 = new SnapAdapter(this);
        llStartSnapRv.setAdapter(adapter1);
        SnapHelper snapHelperStart = new StartSnapHelper();
        snapHelperStart.attachToRecyclerView(llStartSnapRv);


    }

    List<Image> mList = new ArrayList<>();

    private void init(){
        for(int i=0;i<15;i++) {
            Image image = new Image();
            image.setResId(R.drawable.ic_launcher);
            image.setTitle("标题"+i);
            mList.add(image);
        }
    }

    class Image{
        private String title;
        private int resId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }
    }

    public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.SnapHolder>{

        private Context mContext;

        public SnapAdapter(Context context) {
            mContext=context;
        }

        @Override
        public SnapAdapter.SnapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SnapHolder(LayoutInflater.from(mContext).inflate(R.layout.snap_item_recycler_view,parent,false));
        }

        @Override
        public void onBindViewHolder(SnapAdapter.SnapHolder holder, int position) {
            holder.iv.setImageResource(mList.get(position).getResId());
            holder.tv.setText(mList.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class SnapHolder extends RecyclerView.ViewHolder{

            ImageView iv;
            TextView tv;

            public SnapHolder(View itemView) {
                super(itemView);
                iv= (ImageView) itemView.findViewById(R.id.id_iv);
                tv= (TextView) itemView.findViewById(R.id.id_tv_title);
            }
        }
    }

}
