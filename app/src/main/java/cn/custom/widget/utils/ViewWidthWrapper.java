package cn.custom.widget.utils;

import android.view.View;

/**
 * Created by chawei on 2017/10/16.
 * 动态修改控件宽度
 */

public class ViewWidthWrapper {

    private View mTarget;

    public ViewWidthWrapper(View target) {
        mTarget = target;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}
