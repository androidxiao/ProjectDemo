package cn.expandrecyclerview.demo.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.expandrecyclerview.demo.listener.OnGroupClickListener;

/**
 * Created by chawei on 2018/1/6.
 */

public abstract class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnGroupClickListener listener;

    public GroupViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onGroupClick(getAdapterPosition());
        }
    }

    public void setOnGroupClickListener(OnGroupClickListener listener) {
        this.listener = listener;
    }

    public void expand() {}

    public void collapse() {}
}
