package cn.custom.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/16.
 */

public class JianshuAdapter extends RecyclerView.Adapter {
    public static final String TAG = "ez";
    private Context mContext;
    private List<String> mList = new  ArrayList<>();
    private static final int HEADER_VIEW=0x00;
    private static final int ITEM_VIEW=0x01;
    private View mHeaderView;
    public JianshuAdapter(Context context) {
        mContext=context;

    }

    public void appendList(){
        for(int i=0;i<30;i++) {
            mList.add("测试--->" + i);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        JianshuHolder holder=null;
        switch (viewType) {
            case HEADER_VIEW:
                holder= new JianshuHolder(mHeaderView);
                break;
            case ITEM_VIEW:
                holder= new JianshuHolder(LayoutInflater.from(mContext).inflate(R.layout.item_textview_view, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case HEADER_VIEW:

                break;
            case ITEM_VIEW:
                JianshuHolder jHold= (JianshuHolder) holder;
                jHold.tvTest.setText(mList.get(position-1));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        }else{
            return ITEM_VIEW;
        }

    }

    public void addHeaderView(){
        View headerView=LayoutInflater.from(mContext).inflate(R.layout.jianshu_title_layout_view, null);
        mHeaderView=headerView;
        notifyItemInserted(0);
    }


    public class JianshuHolder extends RecyclerView.ViewHolder{
        private TextView tvTest;
        public JianshuHolder(View itemView) {
            super(itemView);
            tvTest = (TextView) itemView.findViewById(R.id.id_tv_test);
        }
    }
}
