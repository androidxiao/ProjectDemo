package cn.custom.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/18.
 *
 * 利用ObjectAnimation实现动画效果
 */

public class ObjectAnimateCircleRingView extends View {
    public static final String TAG = "ez";
    private int mArcDis;
    private Paint mRingPaint;
    private Paint mPointPaint;
    private int mPointAngle;
    private float mCircleX;
    private float mCircleY;
    private int mSwipeAngle = 45;
    private Paint mSwipePaint;
    private Paint mTextPaint;
    private Paint mPercentPaint;

    public ObjectAnimateCircleRingView(Context context) {
        this(context, null);
    }

    public ObjectAnimateCircleRingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObjectAnimateCircleRingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mSwipePaint.setStrokeWidth(Px2DpUtil.dp2px(context, 3));
        mSwipePaint.setAntiAlias(true);
        mSwipePaint.setStyle(Paint.Style.STROKE);
        mSwipePaint.setColor(ContextCompat.getColor(context, R.color.bg_gradient_start));


        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(Px2DpUtil.dp2px(context, 1));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));
        mTextPaint.setTextSize(Px2DpUtil.sp2px(context, 40));


        mPercentPaint = new Paint();
        mPercentPaint.setStrokeWidth(Px2DpUtil.dp2px(context, 1));
        mPercentPaint.setAntiAlias(true);
        mPercentPaint.setStyle(Paint.Style.FILL);
        mPercentPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));
        mPercentPaint.setTextSize(Px2DpUtil.sp2px(context, 25));

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
        if (mSwipeAngle >= 0 && mSwipeAngle <= 45) {
            mSwipeAngle = 45;
            mPointAngle = 45;
        } else if (mSwipeAngle > 315 & mSwipeAngle <= 360) {
            mSwipeAngle = 315;
            mPointAngle = 315;
        }
        mCircleX = width / 2;
        mCircleY = height / 2;
        float radius = (float) ((width - mArcDis - mArcDis) / 2);
        float pointX = (float) (mCircleX + radius * Math.cos(mSwipeAngle * 3.14 / 180));
        float pointY = (float) (mCircleY + radius * Math.sin(mSwipeAngle * 3.14 / 180));
        canvas.drawArc(rectF, 45, mSwipeAngle - 45, false, mSwipePaint);
        canvas.drawCircle(pointX, pointY, Px2DpUtil.dp2px(getContext(), 10), mPointPaint);
        canvas.rotate(-90, width / 2, height / 2);
        float v = mTextPaint.measureText(String.valueOf((int) Math.floor((float) (mSwipeAngle - 45) / 270 * 100)));
        canvas.drawText(String.valueOf((int) Math.floor((float) (mSwipeAngle - 45) / 270 * 100)), mCircleX - (int) v / 2, mCircleY + Px2DpUtil.dp2px(getContext(), 10), mTextPaint);
        canvas.drawText("%", mCircleX + (int) v / 2, mCircleY + Px2DpUtil.dp2px(getContext(), 10), mPercentPaint);
    }

    public int getMPointAngle() {
        return mPointAngle;
    }

    public void setMPointAngle(float value) {
        int swipeAngle = (int) (((float) value / 100) * 360);
        mSwipeAngle = swipeAngle;
        Log.d(TAG, "setMPointAngle: --->" + value);
        postInvalidate();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
