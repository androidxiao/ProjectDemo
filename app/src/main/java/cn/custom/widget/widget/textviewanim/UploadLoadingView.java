package cn.custom.widget.widget.textviewanim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.AttributeSet;
import android.view.View;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/19.
 */

public class UploadLoadingView extends View {

    public static final String TAG = "gear2";

    private Paint mPaint;
    private ValueAnimator mValueAnimator;
    //动画是否执行结束
    private boolean isEnd;
    private Paint mProgressPaint;
    //RectF距离X轴/Y轴的距离
    private int mRectFLength;
    //动画改变的值
    private float mAnimatedValue;
    //进度值
    private float mProgressAnimValue;
    //进度初始位置的X轴偏移量
    private int mTextOffsetX;

    private Paint mTextPaint;
    private float mTextHeight;
    private float mTextWidth;
    private String mText = "Loading...";
    //文字偏移进度
    private int translateDis;
    private PorterDuffXfermode mXfermode;
    private Paint mRectPaint;

    public UploadLoadingView(Context context) {
        this(context, null);
    }

    public UploadLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UploadLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#EBECEC"));
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(ContextCompat.getColor(getContext(), R.color.c_3ec88e));
        mProgressPaint.setStrokeWidth(1);
        mProgressPaint.setTextSize(40);

        //矩形框中文字Paint
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.parseColor("#95EBECEC"));
        mTextPaint.setTextSize(60);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        //计算出准确的文字高度
        mTextHeight = metrics.bottom - metrics.top;
        mTextWidth = mTextPaint.measureText(mText);
        //设置文字和矩形重合时，显示的方式
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);//取两层绘制交集。显示上层。

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mRectPaint.setColor(ContextCompat.getColor(getContext(), R.color.c_3ec88e));
        mRectPaint.setXfermode(mXfermode);


        mRectFLength = Px2DpUtil.dp2px(getContext(), 30);
        mTextOffsetX = Px2DpUtil.dp2px(getContext(), 10);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasRectWidthProgress(canvas);
        canvasBitmapWidthText(canvas);
    }

    /**
     * 绘制矩形框中的文字
     *
     * @param canvas
     */
    private void canvasBitmapWidthText(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() / 2 - mRectFLength, getHeight() / 2 - mRectFLength);
        //创建bitmap
        Bitmap srcBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_4444);
        //新建画布存储bitmap
        Canvas srcCanvas = new Canvas(srcBitmap);
        float v1 = (mRectFLength * 5 - mTextWidth) / 2;
        //bitmap上添加文字
        srcCanvas.drawText(mText, v1, mRectFLength + mTextHeight / 4, mTextPaint);
        RectF rectF = new RectF(0, 0, translateDis, getMeasuredHeight());
        //画矩形
        srcCanvas.drawRect(rectF, mRectPaint);
        //绘制bitmap
        canvas.drawBitmap(srcBitmap, 0, 0, null);
        canvas.restore();
    }

    /**
     * 绘制进度边框和进度值
     *
     * @param canvas
     */
    private void canvasRectWidthProgress(Canvas canvas) {

        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        RectF rectF = new RectF();
        rectF.left = -mRectFLength;
        rectF.top = -mRectFLength;
        rectF.right = mRectFLength * 4;
        rectF.bottom = mRectFLength;
        mPaint.setColor(Color.parseColor("#EBECEC"));
        canvas.drawRect(rectF, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(getWidth() / 2 - mRectFLength, getHeight() / 2 - mRectFLength);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.c_3ec88e));
        //矩形左边线
        canvas.drawLine(0, 0, 0, mRectFLength * 2, mPaint);
        //矩形上边线
        canvas.drawLine(0, 0, mAnimatedValue, 0, mPaint);
        //矩形下边线
        canvas.drawLine(0, mRectFLength * 2, mAnimatedValue, mRectFLength * 2, mPaint);

        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        float textHeight = metrics.bottom - metrics.top;
        canvas.drawText((int) mProgressAnimValue + "%", mAnimatedValue + mTextOffsetX, mRectFLength + textHeight, mProgressPaint);

        if (isEnd) {
            //动画结束，矩形右边线
            canvas.drawLine(mRectFLength * 5, 0, mRectFLength * 5, mRectFLength * 2, mPaint);
            canvas.drawText("", mAnimatedValue + mTextOffsetX, (mRectFLength + textHeight), mProgressPaint);
//            Paint paint = new Paint();
//            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//            canvas.drawPaint(paint);
//            canvas.drawColor(Color.WHITE);
        } else {
            mPaint.setColor(Color.parseColor("#EBECEC"));
            //动画重新开始，矩形右边线还原
            canvas.drawLine(mRectFLength * 5, 0, mRectFLength * 5, mRectFLength * 2, mPaint);
        }

        canvas.restore();
    }


    public void startAnim() {

        isEnd = false;

        mValueAnimator = ValueAnimator.ofFloat(1, mRectFLength * 5);
        mValueAnimator.setDuration(2000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.setInterpolator(new FastOutLinearInInterpolator());

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mRectFLength * 5);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translateDis = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new FastOutLinearInInterpolator());
        valueAnimator.setDuration(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(mValueAnimator, valueAnimator);
        animatorSet.start();

        changeProgress();
        endAnim();

    }

    private void endAnim() {
        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isEnd = true;
                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public float getProgressAnimValue() {
        return mProgressAnimValue;
    }

    public void setProgressAnimValue(float progressAnimValue) {
        mProgressAnimValue = progressAnimValue;
        invalidate();
    }


    private void changeProgress() {
        ObjectAnimator progressObAnim = ObjectAnimator.ofFloat(this, "progressAnimValue", 0, 100);
        progressObAnim.setDuration(2000);
        progressObAnim.setInterpolator(new FastOutLinearInInterpolator());
        progressObAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgressAnimValue = (float) animation.getAnimatedValue();
            }
        });
        progressObAnim.start();
    }
}
