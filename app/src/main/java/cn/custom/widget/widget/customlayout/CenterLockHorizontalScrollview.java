package cn.custom.widget.widget.customlayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import cn.custom.widget.adapter.CustomListAdapter;

/**
 * Created by chawei on 2017/12/24.
 */

public class CenterLockHorizontalScrollview extends HorizontalScrollView {
    Context context;
    int prevIndex = 0;

    public CenterLockHorizontalScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setSmoothScrollingEnabled(true);

    }

    public void setAdapter(Context context, CustomListAdapter mAdapter) {
        try {
            fillViewWithAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillViewWithAdapter(CustomListAdapter mAdapter)
            throws Exception {
        if (getChildCount() == 0) {
            throw new Exception(
                    "CenterLockHorizontalScrollView must have one child");
        }
        if (getChildCount() == 0 || mAdapter == null)
            return;

        ViewGroup parent = (ViewGroup) getChildAt(0);

        parent.removeAllViews();

        for (int i = 0; i < mAdapter.getCount(); i++) {
            parent.addView(mAdapter.getView(i, null, parent));
        }
    }

    public void setCenter(int index) {

        ViewGroup parent = (ViewGroup) getChildAt(0);

        View preView = parent.getChildAt(prevIndex);
        preView.setBackgroundColor(Color.parseColor("#64CBD8"));
        android.widget.LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 5, 5, 5);
        preView.setLayoutParams(lp);

        View view = parent.getChildAt(index);
        view.setBackgroundColor(Color.RED);

        int screenWidth = ((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth();

        int scrollX = (view.getLeft() - (screenWidth / 2))
                + (view.getWidth() / 2);
        this.smoothScrollTo(scrollX, 0);
        prevIndex = index;
    }

}