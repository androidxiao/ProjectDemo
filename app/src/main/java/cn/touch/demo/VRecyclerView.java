package cn.touch.demo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import cn.custom.widget.Px2DpUtil;

/**
 * Created by chawei on 2018/5/1.
 */

public class VRecyclerView extends RecyclerView {

    public static final String TAG = "ez";
    public static final int mTriggerDis=10;
    private float mStartY;
    private int mActionUpY;
    private int mOffSetY;
    private int mFixTopDis = Px2DpUtil.dp2px(getContext(), 150);

    public VRecyclerView(Context context) {
        super(context);
    }

    public VRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                if (mActionUpY == 0) {
//                    mActionUpY= (int) mStartY;
//                }
                mStartY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mOffSetY = (int) (ev.getY() - Math.abs(mStartY));
                if(Math.abs(mOffSetY)<=mFixTopDis) {
                }
                    setTranslationY(mOffSetY);
                    Log.d(TAG, "onTouchEvent: ScrollView------>ACTION_MOVE--->"+mOffSetY);
                break;
            case MotionEvent.ACTION_UP:

                Log.d(TAG, "onTouchEvent: ScrollView------>ACTION_UP--->"+mActionUpY);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
