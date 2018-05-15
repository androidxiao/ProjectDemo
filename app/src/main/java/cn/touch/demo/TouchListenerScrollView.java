package cn.touch.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import cn.custom.widget.Px2DpUtil;

/**
 * Created by chawei on 2018/5/1.
 */

public class TouchListenerScrollView extends LinearLayout {

    public static final String TAG = "ez";
    public static final int mTriggerDis = 10;
    private float mStartY;
    private int mActionUpY;
    private int mOffSetY;
    private int mFixTopDis = Px2DpUtil.dp2px(getContext(), 150);
    private ArrayList<View> mMoveViewList = new ArrayList();

    public TouchListenerScrollView(Context context) {
        super(context);
    }

    public TouchListenerScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchListenerScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMoveViewList(ArrayList<View> moveViewList) {
        mMoveViewList = moveViewList;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dis = Math.abs(ev.getY() - mStartY);
                if (dis > mTriggerDis) {
                    Log.d(TAG, "onInterceptTouchEvent: 拦截了。。。。。。" + dis);
                    return true;
                } else {
                    Log.d(TAG, "onInterceptTouchEvent: RecycleView执行手势。。。" + dis);
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

    private int mMoveOffsetY;
    private int mFixY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                if (mActionUpY == 0) {
//                    mActionUpY= (int) mStartY;
//                }
                break;
            case MotionEvent.ACTION_MOVE:
                mOffSetY = (int) Math.abs(ev.getY() - mStartY);
//                if(Math.abs(mOffSetY)<=mFixTopDis) {
//                    child.setTranslationY(mOffSetY);
//                    child.scrollTo(0,Math.abs(mOffSetY));
                if (null != mMoveViewList) {
                    for (int i = 0; i < mMoveViewList.size(); i++) {
                        mMoveViewList.get(i).scrollTo(0, Math.abs(mOffSetY));
                    }
                }
                Log.d(TAG, "onTouchEvent: ScrollView------>ACTION_MOVE--->" + mOffSetY);
//                }


//                int offsetY = (int) Math.abs(ev.getX() - mStartY);
//                if (offsetY > 30) {
//                    mMoveOffsetY = (int) (mStartY - ev.getY() + mFixY);
//                    if (0 > mMoveOffsetY) {
//                        mMoveOffsetY = 0;
//                    } else {
//                        //当滑动大于最大宽度时，不在滑动（右边到头了）
////                        if ((mRightTitleLayout.getWidth() + mMoveOffsetX) > rightTitleTotalWidth()) {
////                            mMoveOffsetX = rightTitleTotalWidth() - mRightTitleLayout.getWidth();
////                        }
//                    }
//                    //跟随手指向右滚动
//                    if (null != mMoveViewList) {
//                        for (int i = 0; i < mMoveViewList.size(); i++) {
//                            //使每个item随着手指向右滚动
//                            mMoveViewList.get(i).scrollTo(0, mMoveOffsetY);
//                        }
//                    }
//                }
                break;
            case MotionEvent.ACTION_UP:
                mFixY = mMoveOffsetY;
                Log.d(TAG, "onTouchEvent: ScrollView------>ACTION_UP--->" + mActionUpY);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
