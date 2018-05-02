package cn.touch.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import cn.custom.widget.Px2DpUtil;

/**
 * Created by chawei on 2018/5/1.
 */

public class TouchListenerScrollView extends LinearLayout {

    public static final String TAG = "ez";
    public static final int mTriggerDis=10;
    private float mStartY;
    private int mActionUpY;
    private int mOffSetY;
    private int mFixTopDis = Px2DpUtil.dp2px(getContext(), 150);

    public TouchListenerScrollView(Context context) {
        super(context);
    }

    public TouchListenerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchListenerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY =ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dis = Math.abs(ev.getY()- mStartY);
                if(dis>mTriggerDis) {
                    Log.d(TAG, "onInterceptTouchEvent: 拦截了。。。。。。"+dis);
                    return true;
                }else{
                    Log.d(TAG, "onInterceptTouchEvent: RecycleView执行手势。。。"+dis);
                    return false;
                }
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        View child = getChildAt(1);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                if (mActionUpY == 0) {
//                    mActionUpY= (int) mStartY;
//                }
                mStartY=ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mOffSetY = (int) (ev.getY() - Math.abs(mStartY));
                if(Math.abs(mOffSetY)<=mFixTopDis&&mOffSetY<0) {
//                    child.setTranslationY(mOffSetY);
//                    child.scrollTo(0,Math.abs(mOffSetY));
                    child.scrollBy(0,mOffSetY);
                Log.d(TAG, "onTouchEvent: ScrollView------>ACTION_MOVE--->"+mOffSetY);
                }
                break;
            case MotionEvent.ACTION_UP:

                Log.d(TAG, "onTouchEvent: ScrollView------>ACTION_UP--->"+mActionUpY);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
