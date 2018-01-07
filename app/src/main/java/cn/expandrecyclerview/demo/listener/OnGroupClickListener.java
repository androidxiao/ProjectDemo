package cn.expandrecyclerview.demo.listener;

/**
 * Created by chawei on 2018/1/6.
 */

public interface OnGroupClickListener {

    /**
     * @param flatPos the flat position (raw index within the list of visible items in the
     * RecyclerView of a GroupViewHolder)
     * @return false if click expanded group, true if click collapsed group
     */
    boolean onGroupClick(int flatPos);
}