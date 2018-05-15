package cn.rv.item.longpress.drag;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;


public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<ChannelBean> mSortedList;
    public boolean isEdit;//是否是编辑状态

    public ChannelAdapter(Context mContext, List<ChannelBean> list) {
        this.mContext = mContext;
        this.mSortedList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_channel, parent, false);
        return new ChannelHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setChannel((ChannelHolder) holder, mSortedList.get(position));
    }


    private void setChannel(final ChannelHolder holder, final ChannelBean bean) {
        holder.name.setText(bean.getName());
        int position = holder.getLayoutPosition();
        //点击标签删除已选标签
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bean.isLock()) {
                    if (isEdit) {
                        removeFromSelected(holder);
                    }
                } else {
                }
            }
        });
        //长按进入编辑状态
        holder.name.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    isEdit = true;
                    notifyDataSetChanged();
                //返回true 防止长按拖拽事件跟点击事件冲突
                return true;
            }
        });
        //点击X删除已选标签
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromSelected(holder);
            }
        });
        //是否是编辑状态且第一个不可编辑
        if (isEdit&&position!=0) {
            holder.delete.setVisibility(View.VISIBLE);
        } else {
            holder.delete.setVisibility(View.GONE);
        }
    }

    /**
     * 删除已选标签
     * @param holder
     */
    private void removeFromSelected(ChannelHolder holder) {
        int position = holder.getLayoutPosition();
        holder.delete.setVisibility(View.GONE);
        notifyItemRemoved(position);
        mSortedList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 对拖拽的元素进行排序
     * @param fromPosition
     * @param toPosition
     */
    void itemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mSortedList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mSortedList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public int getItemCount() {
        return mSortedList == null ? 0 : mSortedList.size();
    }

    class ChannelHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView delete;

        public ChannelHolder(View itemView) {
            super(itemView);

            int validWidth = ScreenUtil.getScreenWidth() - Px2DpUtil.dp2px(mContext, 45);
            int childWidth = validWidth / 4;
            name = (TextView) itemView.findViewById(R.id.channel_name);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(childWidth, Px2DpUtil.dp2px(mContext, 35));
            params.setMargins(0, Px2DpUtil.dp2px(mContext, 5), 0, 0);
            name.setLayoutParams(params);
            name.setGravity(Gravity.CENTER);
            delete = (ImageView) itemView.findViewById(R.id.channel_delete);
        }
    }
}
