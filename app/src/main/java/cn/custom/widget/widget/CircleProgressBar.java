package cn.custom.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/12.
 *
 * 仿微信拍摄视频按钮
 *
 *  这里可以添加接口回调，实现项目需求
 */

public class CircleProgressBar extends View {

    public static final String TAG = "ez";

    // 录制时的环形进度条
    private Paint mRecordPaint;
    // 录制时点击的圆形按钮
    private Paint mBgPaint;
    // 画笔宽度
    private int mStrokeWidth;
    // 圆形按钮半径
    private int mRadius;
    //控件宽度
    private int mWidth;
    //控件高度
    private int mHeight;
    // 圆的外接圆
    private RectF mRectF;
    //progress max value
    private int mMaxValue=100;
    //per progress value
    private int mProgressValue;
    //是否开始record
    private boolean mIsStartRecord=false;
    //Arc left、top value
    private int mArcValue;
    //录制 time
    private long mRecordTime;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ++mProgressValue;
            postInvalidate();
            if (mProgressValue <= mMaxValue) {
                mHandler.sendEmptyMessageDelayed(0, 100);
            }
        }
    };

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context);
    }

    private void initParams(Context context){
        mArcValue=mStrokeWidth = Px2DpUtil.dp2px(context, 3);

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(context.getResources().getColor(R.color.white));
        mBgPaint.setStrokeWidth(mStrokeWidth);
        mBgPaint.setStyle(Paint.Style.FILL);

        mRecordPaint = new Paint();
        mRecordPaint.setAntiAlias(true);
        mRecordPaint.setColor(context.getResources().getColor(R.color.c_3ec88e));
        mRecordPaint.setStrokeWidth(mStrokeWidth);
        mRecordPaint.setStyle(Paint.Style.STROKE);

        mRadius = Px2DpUtil.dp2px(context, 30);
        mRectF = new RectF();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWidth=getWidth();
        mHeight=getHeight();
        if (mWidth != mHeight) {
            int min = Math.min(mWidth, mHeight);
            mWidth=min;
            mHeight=min;
        }

        if (mIsStartRecord) {

            canvas.drawCircle(mWidth / 2, mHeight / 2, (float) (mRadius*1.2), mBgPaint);

            if(mProgressValue<=mMaxValue) {
                //left--->距Y轴的距离
                //top--->距X轴的距离
                //right--->距Y轴的距离
                //bottom--->距X轴的距离
                mRectF.left = mArcValue;
                mRectF.top = mArcValue;
                mRectF.right = mWidth - mArcValue;
                mRectF.bottom = mHeight - mArcValue;
                canvas.drawArc(mRectF, -90, ((float)mProgressValue / mMaxValue) * 360, false, mRecordPaint);

                if (mProgressValue == mMaxValue) {
                    mProgressValue = 0;
                    mHandler.removeMessages(0);
                    mIsStartRecord = false;
                    //这里可以回调出去表示已到录制时间最大值
                    //code.....
                }
            }
        }else{
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mBgPaint);
        }

//        Log.d(TAG, "onDraw: mWidth--->"+mWidth);
//        Log.d(TAG, "onDraw: mStrokeWidth--->"+mStrokeWidth);
//        Log.d(TAG, "onDraw: mRadius--->"+(mRadius*2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsStartRecord=true;
                mRecordTime=System.currentTimeMillis();
                mHandler.sendEmptyMessage(0);
                //这里可以回调出去表示已经开始录制了
                //code.....
                break;
            case MotionEvent.ACTION_UP:
                if (mRecordTime > 0) {
                    //录制的时间（单位：秒）
                    int actualRecordTime= (int) ((System.currentTimeMillis()-mRecordTime)/1000);
                    //这里回调出去表示已经取消录制了
                    //code.....
                }
                mHandler.removeMessages(0);
                mIsStartRecord=false;
                mRecordTime=0;
                mProgressValue=0;
                postInvalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                //这里可以回调出去表示已经取消录制了
                //code.....
                mHandler.removeMessages(0);
                mIsStartRecord=false;
                mRecordTime=0;
                mProgressValue=0;
                postInvalidate();
                break;
        }

        return true;
    }
}
