package cn.swiperefreshlayout.demo.cn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.project.demo.com.R;
import cn.project.demo.com.utils.LogUtil;

/**
 * Created by chawei on 2017/9/21.
 */

public class SwipeAdapter extends LoadingMoreFooterAdapter<String>{

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private Context mContext;
//    private List<String> mList=new ArrayList<>();
    /**
     * 是否正在加载
     */
//    private boolean isLoadingAdded;
    /**
     * 是否全部加载完
     */
//    private boolean isAll;

    public SwipeAdapter(Context context){
        mContext=context;
    }

    public void appendList(List<String>list){
        mList= list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
       switch (viewType){
           case ITEM:
               TextView textView = new TextView(mContext);
               ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
               textView.setLayoutParams(params);
               textView.setGravity(Gravity.CENTER);
               viewHolder=new SwipeHolder(textView);
               break;
           case LOADING:
               viewHolder= new LoadingVH(LayoutInflater.from(mContext).inflate(R.layout.swipe_item_progress,parent, false));
               break;

       }

       return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM:
                ((SwipeHolder)holder).mTextView.setText(mList.get(position));
                break;
            case LOADING:
                LoadingVH loadingVh=(LoadingVH)holder;
                if (isAll) {
                    LogUtil.debug("加载完了");
                    loadingVh.mTvLoad.setVisibility(View.GONE);
                    loadingVh.mProgressBar.setVisibility(View.GONE);
                    loadingVh.mTvFinish.setVisibility(View.VISIBLE);
                }else{
                    LogUtil.debug("显示ProgressBar");
                    int visibility = loadingVh.mTvLoad.getVisibility();
//                    if(visibility==View.GONE) {
                        loadingVh.mTvLoad.setVisibility(View.VISIBLE);
                        loadingVh.mProgressBar.setVisibility(View.VISIBLE);
//                    }
                }
                break;
        }
    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        LogUtil.debug("  --->"+(position == mList.size() - 1 )+"  --->"+isLoadingAdded);
        return (position == mList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public class SwipeHolder extends RecyclerView.ViewHolder{
        private TextView mTextView;
        public SwipeHolder(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView;
        }
    }


//    protected class LoadingVH extends RecyclerView.ViewHolder{
//        private ProgressBar mProgressBar;
//        private TextView mTvLoad;
//        private TextView mTvFinish;
//        public LoadingVH(View itemView) {
//            super(itemView);
//
//            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
//            mTvLoad = (TextView) itemView.findViewById(R.id.id_tv_loading);
//            mTvFinish = (TextView) itemView.findViewById(R.id.id_tv_load_finish);
//        }
//    }
//
//    public void addLoadingFooter() {
//        isLoadingAdded = true;
//        //用来为ProgressBar占据一个item
//        //可以为任何内容
//        add("aaaaaa");
//    }
//
//    public void removeLoadingFooter(){
//        isLoadingAdded = false;
//       //移除占据的数据，添加ProgressBar
//        int position = mList.size() - 1;
//        String result = getItem(position);
//        if (result != null) {
//            mList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
//
//    public String getItem(int position) {
//        return mList.get(position);
//    }
//
//    public void add(String r) {
//        mList.add(r);
//        notifyItemInserted(mList.size() - 1);
//    }
//
//    public void remove(String r) {
//        int position = mList.indexOf(r);
//        if (position > -1) {
//            mList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
//
//    public void clear() {
//        isLoadingAdded = false;
//        while (getItemCount() > 0) {
//            remove(getItem(0));
//        }
//    }
//
//    public void isLoadFinishAll(boolean isAll){
//        this.isAll=isAll;
//    }
}
