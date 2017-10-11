package cn.expand.layout.cn.listener;

import android.view.View;

/**
 * Created by chawei on 2017/10/10.
 */

public interface ExpandCollapseListener<T> {
    interface ExpandListener<T> {
        void onExpanded(int parentIndex, T parent, View view);
    }

    interface CollapseListener<T> {
        void onCollapsed(int parentIndex, T parent, View view);
    }
}
