package cn.rv.item.longpress.drag;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import cn.ProjectApp;
import cn.project.demo.com.R;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_DRAG;

public class ItemDragCallback extends ItemTouchHelper.Callback {
    private ChannelAdapter mAdapter;
    private boolean mEdit;

    public ItemDragCallback(ChannelAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }


    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        ChannelAdapter adapter = (ChannelAdapter) recyclerView.getAdapter();
        mEdit = adapter.isEdit;
        if (!mEdit) {
            return 0;
        }
        int position = viewHolder.getLayoutPosition();
        //第一个item不用交换
        if (position == 0) {
            return 0;
        }
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();   //拖动的position
        int toPosition = target.getAdapterPosition();     //释放的position
        int position = viewHolder.getLayoutPosition();
        //第一个item不用交换
        if (position == 0) {
            return false;
        }
        mAdapter.itemMove(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }


    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (dX != 0 && dY != 0 || isCurrentlyActive) {
            ChannelAdapter adapter = (ChannelAdapter) recyclerView.getAdapter();
            mEdit = adapter.isEdit;
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == ACTION_STATE_DRAG) {
            //长按时调用
            ChannelAdapter.ChannelHolder holder = (ChannelAdapter.ChannelHolder) viewHolder;
            holder.name.setBackgroundColor(Color.parseColor("#FDFDFE"));
            holder.name.setBackground(ContextCompat.getDrawable(ProjectApp.getApp(), R.drawable.shape_channel_bg));
            holder.delete.setVisibility(View.GONE);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        ChannelAdapter.ChannelHolder holder = (ChannelAdapter.ChannelHolder) viewHolder;
        holder.name.setBackground(ContextCompat.getDrawable(ProjectApp.getApp(), R.drawable.shape_channel_bg));
        holder.delete.setVisibility(View.VISIBLE);
    }
}
