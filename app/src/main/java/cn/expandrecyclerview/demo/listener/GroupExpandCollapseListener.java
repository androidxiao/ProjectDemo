package cn.expandrecyclerview.demo.listener;

import cn.expandrecyclerview.demo.model.ExpandableGroup;

/**
 * Created by chawei on 2018/1/6.
 */

public interface GroupExpandCollapseListener {

    /**
     * Called when a group is expanded
     * @param group the {@link ExpandableGroup} being expanded
     */
    void onGroupExpanded(ExpandableGroup group);

    /**
     * Called when a group is collapsed
     * @param group the {@link ExpandableGroup} being collapsed
     */
    void onGroupCollapsed(ExpandableGroup group);
}