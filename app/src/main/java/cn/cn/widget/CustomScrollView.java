package cn.cn.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * Created by chawei on 2018/2/18.
 */

public class CustomScrollView extends ScrollView {

    public static final String TAG = "mvp";
    
    private int mTouchSlop;
    private float mRawX;
    private float mRawY;
    private boolean mCanScrollUp;
    private boolean mCanScrollDown;

    public CustomScrollView(Context context) {
        super(context);
        init();
    }

    public CustomScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mRawX = ev.getRawX();
                mRawY = ev.getRawY();
                mCanScrollUp = canScrollingUp();
                mCanScrollDown = canScrollingDown();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float xDis = Math.abs(mRawX - ev.getRawX());
                float yDis = Math.abs(mRawY - ev.getRawY());

                if (yDis > xDis && yDis > mTouchSlop) {

                    if (mRawY < ev.getRawY() && mCanScrollUp) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }

                    if (mRawY > ev.getRawY() && mCanScrollDown) {
                        getParent().requestDisallowInterceptTouchEvent(false);
                        return false;
                    }


                }

                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 手指向下滑动（内容向上滑动）
     * @return
     */
    private boolean canScrollingUp() {

        if (ViewCompat.canScrollVertically(this, -1)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 手指向上滑动（内容向下滑动）
     * @return
     */
    private boolean canScrollingDown() {

        if (ViewCompat.canScrollVertically(this, 1)) {
            return false;
        } else {
            return true;
        }
    }



}