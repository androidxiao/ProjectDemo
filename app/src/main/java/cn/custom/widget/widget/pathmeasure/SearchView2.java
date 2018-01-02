package cn.custom.widget.widget.pathmeasure;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by chawei on 2017/12/17.
 */

public class SearchView2 extends View {

    public static final String TAG = "gear2";

    private Paint mPaint;
    private Path mPCircle;
    private Path mPSearch;
    private PathMeasure mPathMeasure;
    private ValueAnimator mStartValueAnimator;
    private ValueAnimator mSearchValueAnimator;
    private ValueAnimator mEndValueAnimator;
    private int mBigRadius;
    private int mSmallRadius;

    enum State {
        NONE,
        START,
        SEARCH,
        END
    }

    private State mCurrState = State.NONE;

    public SearchView2(Context context) {
        this(context, null);
    }

    public SearchView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15);
        mPaint.setStyle(Paint.Style.STROKE);

        mPathMeasure = new PathMeasure();

        mPSearch = new Path();
        mPCircle = new Path();

        mBigRadius = 100;
        mSmallRadius = 30;

        //放大镜圆环
        RectF rectFSearch = new RectF(-mSmallRadius, -mSmallRadius, mSmallRadius, mSmallRadius);
        mPSearch.addArc(rectFSearch, 45, 359.9f);

        //外部圆环
        RectF rectFCircle = new RectF(-mBigRadius, -mBigRadius, mBigRadius, mBigRadius);
        mPCircle.addArc(rectFCircle, 45, 359.9f);
        float[] pos = new float[2];
        mPathMeasure.setPath(mPCircle, false);
        //用于得到路径上某一长度的位置以及该位置的正切值
        /**
         * 返回值(boolean)	判断获取是否成功	true表示成功，数据会存入 pos 和 tan 中，
         * false     表示失败，pos 和 tan 不会改变
         * distance	 距离 Path 起点的长度	取值范围: 0 <= distance <= getLength
         * pos	     该点的坐标值	当前点在画布上的位置，有两个数值，分别为x，y坐标。
         * tan	     该点的正切值	当前点在曲线上的方向，使用 Math.atan2(tan[1], tan[0]) 获取到正切角的弧度值。
         */
        mPathMeasure.getPosTan(0, pos, null);
        mPSearch.lineTo(pos[0], pos[1]);

        initAnimatorCircle();

//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mStartValueAnimator.isRunning() || mSearchValueAnimator.isRunning() || mEndValueAnimator.isRunning()) {
//                    return;
//                }
//                mCurrState = State.START;
//                mStartValueAnimator.start();
//                mAnimatorValue = 0;
//            }
//        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int widthMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int actWidth = 0;
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                actWidth = mBigRadius * 2 + getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.EXACTLY:
                actWidth = widthSize;
                break;
        }
        return actWidth;
    }

    private int measureHeight(int heightMeasureSpec) {
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int actHeight = 0;
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                actHeight = mBigRadius * 2 + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                actHeight = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED://当最外层为ScrollView时，height设为wrap_content,仍然是会走MeasureSpec.UNSPECIFIED。
                actHeight = mBigRadius * 2 + getPaddingTop() + getPaddingBottom();
                break;
        }
        return actHeight;
    }

    private float mAnimatorValue;

    private void initAnimatorCircle() {
        mStartValueAnimator = ValueAnimator.ofFloat(0, 1);
        mStartValueAnimator.setDuration(1000);
        mStartValueAnimator.setInterpolator(new LinearInterpolator());


        mSearchValueAnimator = ValueAnimator.ofFloat(1, 0);
        mSearchValueAnimator.setDuration(1500);
        mSearchValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mSearchValueAnimator.setInterpolator(new LinearInterpolator());

        mEndValueAnimator = ValueAnimator.ofFloat(1, 0);
        mEndValueAnimator.setDuration(1000);
        mEndValueAnimator.setInterpolator(new LinearInterpolator());


        ValueAnimator.AnimatorUpdateListener valueAnimator = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                mAnimatorValue = (float) animation.getAnimatedValue();

                invalidate();
            }
        };


        Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {


                switch (mCurrState) {
                    case START:
                        mCurrState = State.SEARCH;
                        mSearchValueAnimator.start();
                        break;
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        };


        mStartValueAnimator.addUpdateListener(valueAnimator);
        mStartValueAnimator.addListener(animatorListener);

        mSearchValueAnimator.addUpdateListener(valueAnimator);
        mSearchValueAnimator.addListener(animatorListener);

        mEndValueAnimator.addUpdateListener(valueAnimator);
        mEndValueAnimator.addListener(animatorListener);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        switch (mCurrState) {
            case NONE:
                canvas.drawPath(mPSearch, mPaint);
                break;
            case START:
                Path dstSearch = new Path();
                PathMeasure pmSearch = new PathMeasure(mPSearch, false);
                pmSearch.getSegment(pmSearch.getLength() * mAnimatorValue, pmSearch.getLength(), dstSearch, true);
                canvas.drawPath(dstSearch, mPaint);
                break;
            case SEARCH:
                Path dst = new Path();
                PathMeasure pathMeasure = new PathMeasure(mPCircle, false);
                float v1 = (float) ((pathMeasure.getLength() * mAnimatorValue) - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * 100f));
                pathMeasure.getSegment(v1, pathMeasure.getLength() * mAnimatorValue, dst, true);
                canvas.drawPath(dst, mPaint);
                break;
            case END:
                Path dstEnd = new Path();
                PathMeasure pmEnd = new PathMeasure(mPSearch, false);
                pmEnd.getSegment(pmEnd.getLength() * mAnimatorValue, pmEnd.getLength(), dstEnd, true);
                mPaint.setColor(Color.WHITE);
                canvas.drawPath(dstEnd, mPaint);
                break;
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (mStartValueAnimator != null && mStartValueAnimator.isRunning()) {
            mStartValueAnimator.cancel();
            mStartValueAnimator.removeAllListeners();
        }

        if (mSearchValueAnimator != null && mSearchValueAnimator.isRunning()) {
            mSearchValueAnimator.cancel();
            mSearchValueAnimator.removeAllListeners();
        }

        if (mEndValueAnimator != null && mEndValueAnimator.isRunning()) {
            mEndValueAnimator.cancel();
            mEndValueAnimator.removeAllListeners();
        }

    }

    public void startAnimator() {
        if (mStartValueAnimator.isRunning() || mSearchValueAnimator.isRunning() || mEndValueAnimator.isRunning()) {
            return;
        }
        mCurrState = State.START;
        mStartValueAnimator.start();
        mAnimatorValue = 0;
    }

    public void cancelSearchAnimator() {
        if ((mSearchValueAnimator != null && mSearchValueAnimator.isRunning())||(mStartValueAnimator != null && mStartValueAnimator.isRunning())) {
            mStartValueAnimator.cancel();
            mSearchValueAnimator.cancel();
            mCurrState = State.END;
            mEndValueAnimator.start();
        }
    }
}
