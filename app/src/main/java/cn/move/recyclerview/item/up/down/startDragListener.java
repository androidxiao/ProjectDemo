package cn.move.recyclerview.item.up.down;

import android.support.v7.widget.RecyclerView;

/**
 * Created by chawei on 2018/4/10.
 */

public interface startDragListener {

    //触摸imageview，开启拖动的接口
    void startDragItem(RecyclerView.ViewHolder holder);
}
