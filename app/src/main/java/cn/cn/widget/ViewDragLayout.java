package cn.cn.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chawei on 2018/2/6.
 */

public class ViewDragLayout extends ViewGroup {

    public static final String TAG = "gesturedemo";
    private static final int VEL_THRESHOLD = 300;
    private static final int DISTANCE_THRESHOLD = 300;
    private View mTopView;
    private View mBottomView;
    private ViewDragHelper mViewDragHelper;
    private GestureDetectorCompat mGestureDetectorCompat;
    private int mFirstHeight;

    public ViewDragLayout(Context context) {
        super(context);
        init();
    }

    public ViewDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewDragLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(), new YScrollDetector());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = getChildAt(0);
        mBottomView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (mTopView.getTop() == 0) {
            mTopView.layout(l, 0, r, b-t );
            mBottomView.layout(l, 0, r, b-t );

            mFirstHeight = mTopView.getMeasuredHeight();
            mBottomView.offsetTopAndBottom(mFirstHeight);
        }else{
            mTopView.layout(l, mTopView.getTop(), r, mTopView.getBottom());
            mBottomView.layout(l, mBottomView.getTop(), r, mBottomView.getBottom());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        /**
         * 这里的top就代表你将要移动的位置坐标。返回值就是最终确定的移动的位置
         * 我们要让view滑动的范围在我们的layout之内
         * 实际上就是判断如果这个坐标在layout之内，那我们就返回这个坐标值
         * 如果这个坐标在layout的边界处，那我们就只能返回边界的坐标给它。不能让它超出这个范围
         * 除此之外就是如果你的layout设置了padding的话，也可以让子view的活动范围在padding之内的
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            
            int finalTop=top;
            if (child == mTopView) {

                Log.d(TAG, "clampViewPositionVertical: mFirstView--->"+top);
                if (top > 0) {
                    finalTop=0;
                }
            }else if(child==mBottomView){
                Log.d(TAG, "clampViewPositionVertical: mSecondView--->"+top);
                if(top<0){
                    finalTop=0;
                }
            }
            return finalTop;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == mTopView) {
                mBottomView.offsetTopAndBottom(dy);
            }else if (changedView==mBottomView){
                mTopView.offsetTopAndBottom(dy);
            }
            ViewCompat.postInvalidateOnAnimation(ViewDragLayout.this);
        }

        /**
         *
         * @param releasedChild
         * @param xvel 水平方向的速度（向右为正）
         * @param yvel 竖直方向的速度（向下为正）
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            animTopOrBottom(releasedChild, yvel);
        }


    }

    private void animTopOrBottom(View releasedChild, float yvel) {
        int finalTop=0;
        if (releasedChild == mTopView) {
            if (yvel < -VEL_THRESHOLD || (releasedChild.getTop() < -DISTANCE_THRESHOLD)) {
                finalTop=-mFirstHeight;
            }
        } else if (releasedChild == mBottomView) {
            if (yvel > VEL_THRESHOLD || (releasedChild.getTop() > DISTANCE_THRESHOLD)) {
                finalTop=mFirstHeight;
            }
        }

        if (mViewDragHelper.smoothSlideViewTo(releasedChild, 0, finalTop)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }



    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //如果'top view'和'bottom view'在交换状态，则不处理touch事件
        if (mTopView.getTop() < 0 && mTopView.getBottom() > 0) {
            return false;
        }



        boolean isCanTouch = mGestureDetectorCompat.onTouchEvent(ev);
        boolean shouldIntercept = mViewDragHelper.shouldInterceptTouchEvent(ev);

        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mViewDragHelper.processTouchEvent(ev);
        }
        return isCanTouch&&shouldIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //ViewDragHelper.callback执行drag操作
        try {
            mViewDragHelper.processTouchEvent(event);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return true;
    }

    private class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
    }
}
