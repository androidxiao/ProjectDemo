package cn.channel.manage.com;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.PathEffect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_DRAG;

public class ItemDragCallback extends ItemTouchHelper.Callback {
    private static final String TAG = "ez";
    private ChannelAdapter mAdapter;
//    private Paint mPaint;    //虚线画笔
//    private int mPadding;   //虚线框框跟按钮间的距离
    private boolean mEdit;

    public ItemDragCallback(ChannelAdapter mAdapter, int mPadding) {
        this.mAdapter = mAdapter;
//        this.mPadding = mPadding;
//        mPaint = new Paint();
//        mPaint.setColor(Color.GRAY);
//        mPaint.setAntiAlias(true);
//        mPaint.setStrokeWidth(1);
//        mPaint.setStyle(Paint.Style.STROKE);
        PathEffect pathEffect = new DashPathEffect(new float[]{5f, 5f}, 5f);    //虚线
//        mPaint.setPathEffect(pathEffect);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        ChannelAdapter adapter= (ChannelAdapter) recyclerView.getAdapter();
        mEdit=adapter.isEdit;
        if(!mEdit){
            return 0;
        }
        //固定位置及tab下面的channel不能拖动
        if (viewHolder.getLayoutPosition() < mAdapter.getFixSize() + 1 || viewHolder.getLayoutPosition() > mAdapter.getSelectedSize()) {
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
            //固定位置及tab下面的channel不能拖动
            if (toPosition < mAdapter.getFixSize() + 1 || toPosition > mAdapter.getSelectedSize())
                return false;
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

                //长按拖拽时底部绘制一个虚线矩形
//                c.drawRect(viewHolder.itemView.getLeft(), viewHolder.itemView.getTop() - mPadding, viewHolder.itemView.getRight(), viewHolder.itemView.getBottom(), mPaint);
            }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
            if (actionState == ACTION_STATE_DRAG) {
                //长按时调用
                ChannelAdapter.ChannelHolder holder = (ChannelAdapter.ChannelHolder) viewHolder;
                holder.name.setBackgroundColor(Color.parseColor("#FDFDFE"));
                holder.delete.setVisibility(View.GONE);
                holder.name.setElevation(5f);
            }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        ChannelAdapter.ChannelHolder holder = (ChannelAdapter.ChannelHolder) viewHolder;
        holder.name.setBackgroundColor(Color.parseColor("#f0f0f0"));
        holder.name.setElevation(0f);
        holder.delete.setVisibility(View.VISIBLE);
    }
}
