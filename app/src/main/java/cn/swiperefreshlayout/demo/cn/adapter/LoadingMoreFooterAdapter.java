package cn.swiperefreshlayout.demo.cn.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/9/22.
 */

public abstract class LoadingMoreFooterAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /**
     * 是否正在加载
     */
    protected boolean isLoadingAdded;
    /**
     * 是否全部加载完
     */
    protected boolean isAll;

    protected List<T> mList=new ArrayList<>();

    public class LoadingVH extends RecyclerView.ViewHolder {
        protected ProgressBar mProgressBar;
        protected TextView mTvLoad;
        protected TextView mTvFinish;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mTvLoad = (TextView) itemView.findViewById(R.id.id_tv_loading);
            mTvFinish = (TextView) itemView.findViewById(R.id.id_tv_load_finish);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        //用来为ProgressBar占据一个item
        //可以为任何内容
        add((T) "aaaaaa");
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
        //移除占据的数据，添加ProgressBar
        int position = mList.size() - 1;
        T result = getItem(position);
        if (result != null) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    public void add(T r) {
        mList.add(r);
        notifyItemInserted(mList.size() - 1);
    }

    public void remove(T r) {
        int position = mList.indexOf(r);
        if (position > -1) {
            mList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public void isLoadFinishAll(boolean isAll) {
        this.isAll = isAll;
    }

}
