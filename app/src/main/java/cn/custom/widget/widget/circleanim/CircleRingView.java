package cn.custom.widget.widget.circleanim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/18.
 */

public class CircleRingView extends View {
    public static final String TAG = "ez";
    private int mArcDis;
    private Paint mRingPaint;
    private Paint mPointPaint;
    private int mPointAngle;
    private float mCircleX;
    private float mCircleY;
    private int mSwipeAngle=45;


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mSwipeAngle == mPointAngle) {
                mHandler.removeMessages(0);
            }else {
                ++mSwipeAngle;
                postInvalidate();
            }
        }
    };
    private Paint mSwipePaint;
    private Paint mTextPaint;
    private Paint mPercentPaint;

    public CircleRingView(Context context) {
        this(context, null);
    }

    public CircleRingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context);
    }

    private void initParams(Context context) {
        mRingPaint = new Paint();
        mRingPaint.setStrokeWidth(Px2DpUtil.dp2px(context, 3));
        mRingPaint.setAntiAlias(true);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setColor(ContextCompat.getColor(context, R.color.white));

        mPointPaint = new Paint();
        mPointPaint.setStrokeWidth(Px2DpUtil.dp2px(context, 1));
        mPointPaint.setAntiAlias(true);
        mPointPaint.setStyle(Paint.Style.FILL);
        mPointPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));

        mSwipePaint = new Paint();
        mSwipePaint.setStrokeWidth(Px2DpUtil.dp2px(context,3));
        mSwipePaint.setAntiAlias(true);
        mSwipePaint.setStyle(Paint.Style.STROKE);
        mSwipePaint.setColor(ContextCompat.getColor(context, R.color.bg_gradient_start));


        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(Px2DpUtil.dp2px(context, 1));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));
        mTextPaint.setTextSize(Px2DpUtil.sp2px(context,40));


        mPercentPaint = new Paint();
        mPercentPaint.setStrokeWidth(Px2DpUtil.dp2px(context, 1));
        mPercentPaint.setAntiAlias(true);
        mPercentPaint.setStyle(Paint.Style.FILL);
        mPercentPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));
        mPercentPaint.setTextSize(Px2DpUtil.sp2px(context,25));

        mArcDis = Px2DpUtil.dp2px(context, 20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        RectF rectF = new RectF();
        rectF.left = mArcDis;
        rectF.top = mArcDis;
        rectF.right = width - mArcDis;
        rectF.bottom = height - mArcDis;
        canvas.rotate(90, width / 2, height / 2);
        canvas.drawArc(rectF, 45, 270, false, mRingPaint);
        if (mSwipeAngle>=0&&mSwipeAngle <= 45) {
            mSwipeAngle = 45;
            mPointAngle=45;
        } else if (mSwipeAngle > 315 & mSwipeAngle <= 360) {
            mSwipeAngle = 315;
            mPointAngle = 315;
        }
        mCircleX = width / 2;
        mCircleY = height / 2;
        if(mSwipeAngle<=mPointAngle) {
            float radius = (float) ((width - mArcDis - mArcDis) / 2);
            float pointX = (float) (mCircleX + radius * Math.cos(mSwipeAngle * 3.14 / 180));
            float pointY = (float) (mCircleY + radius * Math.sin(mSwipeAngle * 3.14 / 180));
            canvas.drawArc(rectF, 45, mSwipeAngle-45, false, mSwipePaint);
            canvas.drawCircle(pointX, pointY, Px2DpUtil.dp2px(getContext(), 10), mPointPaint);
            canvas.rotate(-90, width / 2, height / 2);
            float v = mTextPaint.measureText(String.valueOf((int) Math.floor((float) (mSwipeAngle - 45) / 270 * 100)));
            canvas.drawText(String.valueOf((int)Math.floor((float)(mSwipeAngle-45)/270*100)),mCircleX-(int)v/2,mCircleY+Px2DpUtil.dp2px(getContext(),10),mTextPaint);
            canvas.drawText("%",mCircleX+(int)v/2,mCircleY+Px2DpUtil.dp2px(getContext(),10),mPercentPaint);
            mHandler.sendEmptyMessageDelayed(0, 10);
//            if (mSwipeAngle == mPointAngle) {
//                Log.d(TAG, "onDraw: remove");
//                mHandler.removeMessages(0);
//            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float actionX = event.getX();
        float actionY = event.getY();
        float deltaX = actionX - mCircleX;
        float deltaY = actionY - mCircleY;

        if (deltaX <= 0 & deltaY >= 0) {
            float tanX = deltaX * -1;
            float tanY = deltaY;
            double atan = Math.atan(tanX / tanY);
            mPointAngle = (int) Math.toDegrees(atan);
            mSwipeAngle=mPointAngle;
        }

        if (deltaX <= 0 & deltaY <= 0) {
            float tanX = deltaX * -1;
            float tanY = deltaY * -1;
            double atan = Math.atan(tanY / tanX);
            mPointAngle = (int) Math.toDegrees(atan) + 90;
            mSwipeAngle=mPointAngle;
        }

        if (deltaX >= 0 & deltaY <= 0) {
            float tanX = deltaX;
            float tanY = deltaY * -1;
            double atan = Math.atan(tanX / tanY);
            mPointAngle = (int) Math.toDegrees(atan) + 180;
            mSwipeAngle=mPointAngle;
        }

        if (deltaX >= 0 & deltaY >= 0) {
            float tanX = deltaX;
            float tanY = deltaY;
            double atan = Math.atan(tanY / tanX);
            mPointAngle = (int) Math.toDegrees(atan) + 270;
            mSwipeAngle=mPointAngle;
        }

        invalidate();
        return true;
    }

    public void setProgressValue(int value){
        int swipeAngle = (int) (((float)value / 100) * 360);
        mPointAngle=  swipeAngle;
        mHandler.sendEmptyMessageDelayed(0, 10);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(0);
        mHandler=null;
    }
}
