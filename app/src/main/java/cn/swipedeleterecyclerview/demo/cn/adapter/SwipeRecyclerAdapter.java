package cn.swipedeleterecyclerview.demo.cn.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;
import cn.swipedeleterecyclerview.demo.cn.model.ItemModel;

/**
 * Created by chawei on 2017/10/2.
 */

public class SwipeRecyclerAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ItemModel> mItemModels = new ArrayList<>();

    public SwipeRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void appendList(List<ItemModel>list) {
        mItemModels=list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SwipeHolder(LayoutInflater.from(mContext).inflate(R.layout.swipe_recyclerview_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SwipeHolder swipeHolder = (SwipeHolder) holder;
        swipeHolder.mIvThumb.setBackgroundResource(mItemModels.get(position).getResId());
        swipeHolder.mTvTitle.setText(mItemModels.get(position).getTitle());
        swipeHolder.mTvDesc.setText(mItemModels.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mItemModels.size();
    }

    public class SwipeHolder extends RecyclerView.ViewHolder {
        ImageView mIvThumb;
        TextView mTvTitle;
        TextView mTvDesc;
        public RelativeLayout mViewBackground;
        public RelativeLayout mViewForeground;

        public SwipeHolder(View itemView) {
            super(itemView);
            mIvThumb = (ImageView) itemView.findViewById(R.id.id_iv_thumb);
            mTvTitle = (TextView) itemView.findViewById(R.id.id_tv_title);
            mTvDesc = (TextView) itemView.findViewById(R.id.id_tv_desc);
            mViewBackground = (RelativeLayout) itemView.findViewById(R.id.id_background);
            mViewForeground = (RelativeLayout) itemView.findViewById(R.id.id_forebackground);
        }
    }

    public void removeItem(int position) {
        mItemModels.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(ItemModel item, int position) {
        mItemModels.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
