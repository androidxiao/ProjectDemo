package cn.custom.widget.widget;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/11/30.
 */

public class GearMachineView extends View {

    public static final String TAG = "gear";

    private static final int X = 0;
    private static final int Y = 1;

    private float mTopCircleRadius;
    private float mBottomBigCircleRadius;
    private Paint mRedPaint, mDarkRedPaint, mStrokePaint, mBeltPaint;

    private Paint mTransitionPaint, mTransitionDarkPaint;
    private Path mBigSawtoothPath, mSmallSawtoothPath;
    private Path mLeftTopTrianglePath, mRightTopTrianglePath, mBarPath, mSmallBarPath;

    private float mCurProgress;
    private RectF mShadeRectF;
    private PorterDuffXfermode mClearXfermode;
    private ValueAnimator mValueAnimator;

    /**
     * 确定四个点
     * 1. 左上齿轮中心 宽占完整宽度的比例0.237 高占完整宽度的比例 0.102
     * 2. 右上齿轮中心 宽占完整宽度的比例0.763 高占完整宽度的比例 0.102
     * 3. 中上齿轮中心 宽占完整宽度的比例0.5   高占完整宽度的比例 0.311
     * 4. 中下齿轮中心 宽占完整宽度的比例0.5   高占完整宽度的比例 0.597
     */
    private float[] mLeftTopCentre, mRightTopCentre, mTopCentre, mBottomCentre;

    public GearMachineView(Context context) {
        this(context, null);
    }

    public GearMachineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GearMachineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GearMachineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mLeftTopCentre = new float[2];
        mRightTopCentre = new float[2];
        mTopCentre = new float[2];
        mBottomCentre = new float[2];

        mRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRedPaint.setStrokeJoin(Paint.Join.ROUND);
        mRedPaint.setStrokeCap(Paint.Cap.ROUND);
        mRedPaint.setColor(ContextCompat.getColor(getContext(), R.color.red));

        mDarkRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDarkRedPaint.setStrokeJoin(Paint.Join.ROUND);
        mDarkRedPaint.setStrokeCap(Paint.Cap.ROUND);
        mDarkRedPaint.setStyle(Paint.Style.FILL);
        mDarkRedPaint.setColor(ContextCompat.getColor(getContext(), R.color.red_dark));

        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setStrokeJoin(Paint.Join.ROUND);
        mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(ContextCompat.getColor(getContext(), R.color.red));

        mTransitionPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTransitionPaint.setStrokeJoin(Paint.Join.ROUND);
        mTransitionPaint.setStrokeCap(Paint.Cap.ROUND);
        mTransitionPaint.setStyle(Paint.Style.FILL);
        mTransitionPaint.setColor(ContextCompat.getColor(getContext(), R.color.red_transition));

        mTransitionDarkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTransitionDarkPaint.setStrokeJoin(Paint.Join.ROUND);
        mTransitionDarkPaint.setStrokeCap(Paint.Cap.ROUND);
        mTransitionDarkPaint.setStyle(Paint.Style.FILL);
        mTransitionDarkPaint.setColor(ContextCompat.getColor(getContext(), R.color.red_transition_dark));

        mBeltPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBeltPaint.setStrokeJoin(Paint.Join.ROUND);
        mBeltPaint.setStrokeCap(Paint.Cap.ROUND);
        mBeltPaint.setStyle(Paint.Style.FILL);
        mBeltPaint.setColor(ContextCompat.getColor(getContext(), R.color.red));

        mClearXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        setLayerType(LAYER_TYPE_SOFTWARE, null);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        Log.d(TAG, "paddingLeft: ---->"+paddingLeft);
        Log.d(TAG, "paddingRight: ---->"+paddingRight);
        Log.d(TAG, "paddingTop: ---->"+paddingTop);
        Log.d(TAG, "paddingBottom: ---->"+paddingBottom);
        Log.d(TAG, "getWidth(): ---->"+getWidth());
        Log.d(TAG, "getHeight(): ---->"+getHeight());

        // 横向有效宽度
        int validWidth = getWidth() - paddingLeft - paddingRight;
        Log.d(TAG, "validWidth: ---->"+validWidth);
        // 纵向有效高度
        int validHeight = getHeight() - paddingTop - paddingBottom;
        Log.d(TAG, "validHeight: ---->"+validHeight);

        // 左上齿轮中心
        mLeftTopCentre[X] = (float) (paddingLeft + validWidth * 0.237);
        mLeftTopCentre[Y] = (float) (paddingTop + validHeight * 0.102);

        // 右上齿轮中心
        mRightTopCentre[X] = (float) (paddingLeft + validWidth * 0.763);
        mRightTopCentre[Y] = (float) (paddingTop + validHeight * 0.102);

        // 中上齿轮中心
        mTopCentre[X] = (float) (paddingLeft + validWidth * 0.5);
        mTopCentre[Y] = (float) (paddingTop + validHeight * 0.311);

        // 中下齿轮中心
        mBottomCentre[X] = (float) (paddingLeft + validWidth * 0.5);
        mBottomCentre[Y] = (float) (paddingTop + validHeight * 0.597);

        Log.d(TAG, "mBottomCentre[X]: ---->"+mBottomCentre[X]);
        Log.d(TAG, "mBottomCentre[Y]: ---->"+mBottomCentre[Y]);

        // 上部两个齿轮的半径
        mTopCircleRadius = (float) (validWidth * 0.102);

        // 最大的齿轮stroke size
        mBottomBigCircleRadius = validWidth / 2 - mTopCircleRadius / 4 * 5;
        Log.d(TAG, "mBottomBigCircleRadius: --->"+mBottomBigCircleRadius);
        Log.d(TAG, "mBottomBigStrokeWidth: ---->"+(mTopCircleRadius / 4 * 5));
        mStrokePaint.setStrokeWidth(mTopCircleRadius / 4 * 5);

        // 传送带stroke size
        mBeltPaint.setStrokeWidth((float) (validWidth * 0.0077));

        // 大齿轮Path
        mBigSawtoothPath = new Path();
        mBigSawtoothPath.moveTo((float) (mBottomCentre[X] - validWidth * 0.03), (float) (mBottomCentre[Y] - mBottomBigCircleRadius - mStrokePaint.getStrokeWidth() / 2 * 0.9)); // 此处的0.9表示未被绘制上的偏移量
        Log.d(TAG, "onSizeChanged: --->"+((float) (mBottomCentre[X] - validWidth * 0.03)));
        Log.d(TAG, "onSizeChanged: --->"+((float) (mBottomCentre[Y] - mBottomBigCircleRadius - mStrokePaint.getStrokeWidth() / 2 * 0.9)));
        mBigSawtoothPath.lineTo((float) (mBottomCentre[X] - validWidth * 0.025), (float) (mBottomCentre[Y] - (mBottomBigCircleRadius + mStrokePaint.getStrokeWidth() / 3 * 2.4))); // 此处的mStrokePaint.getStrokeWidth() / 3 * 2.4 代表最大锯齿的高度
        Log.d(TAG, "onSizeChanged: --->"+((float) (mBottomCentre[X] - validWidth * 0.025)));
        Log.d(TAG, "onSizeChanged: --->"+((float) (mBottomCentre[Y] - (mBottomBigCircleRadius + mStrokePaint.getStrokeWidth() / 3 * 2.4))));
        mBigSawtoothPath.lineTo((float) (mBottomCentre[X] + validWidth * 0.025), (float) (mBottomCentre[Y] - (mBottomBigCircleRadius + mStrokePaint.getStrokeWidth() / 3 * 2.4)));
        Log.d(TAG, "onSizeChanged: --->"+((float) (mBottomCentre[X] + validWidth * 0.025)));
        Log.d(TAG, "onSizeChanged: --->"+(float) (mBottomCentre[Y] - (mBottomBigCircleRadius + mStrokePaint.getStrokeWidth() / 3 * 2.4)));
        mBigSawtoothPath.lineTo((float) (mBottomCentre[X] + validWidth * 0.03), (float) (mBottomCentre[Y] - mBottomBigCircleRadius - mStrokePaint.getStrokeWidth() / 2 * 0.9));
        Log.d(TAG, "onSizeChanged: ---->"+((float) (mBottomCentre[X] + validWidth * 0.03)));
        Log.d(TAG, "onSizeChanged: --->"+((float) (mBottomCentre[Y] - mBottomBigCircleRadius - mStrokePaint.getStrokeWidth() / 2 * 0.9)));
        mBigSawtoothPath.close();

        // 小齿轮Path
        mSmallSawtoothPath = new Path();
        mSmallSawtoothPath.moveTo((float) (mBottomCentre[X] - validWidth * 0.01), (float) (mBottomCentre[Y] - (mTopCircleRadius / 2 * 2.8))); // 此处的12 代表梯形底边的一半
        mSmallSawtoothPath.lineTo((float) (mBottomCentre[X] - validWidth * 0.013), (float) (mBottomCentre[Y] - (mTopCircleRadius / 2 * 3.2))); // 此处的10 代表梯形顶边的一半
        mSmallSawtoothPath.lineTo((float) (mBottomCentre[X] + validWidth * 0.013), (float) (mBottomCentre[Y] - (mTopCircleRadius / 2 * 3.2))); // 此处的10 代表梯形顶边的一半
        mSmallSawtoothPath.lineTo((float) (mBottomCentre[X] + validWidth * 0.01), (float) (mBottomCentre[Y] - (mTopCircleRadius / 2 * 2.8)));
        mSmallSawtoothPath.close();

        // 左上角三角齿轮
        mLeftTopTrianglePath = new Path();
        mLeftTopTrianglePath.moveTo((float) (mLeftTopCentre[X] - validWidth * 0.0082), mLeftTopCentre[Y] - (mTopCircleRadius / 4 * 2));
        mLeftTopTrianglePath.lineTo(mLeftTopCentre[X] , (float) (mLeftTopCentre[Y] - (mTopCircleRadius / 4 * 2.6)));  // 此处的10 是三角形底边的一半
        mLeftTopTrianglePath.lineTo((float) (mLeftTopCentre[X] + validWidth * 0.0082), mLeftTopCentre[Y] - (mTopCircleRadius / 4 * 2));
        mLeftTopTrianglePath.close();

        // 右上角三角齿轮
        mRightTopTrianglePath = new Path();
        mRightTopTrianglePath.moveTo((float) (mRightTopCentre[X] - validWidth * 0.0082), mRightTopCentre[Y] - (mTopCircleRadius / 4 * 2));
        mRightTopTrianglePath.lineTo(mRightTopCentre[X] , (float) (mRightTopCentre[Y] - (mTopCircleRadius / 4 * 2.6)));  // 此处的10 是三角形底边的一半
        mRightTopTrianglePath.lineTo((float) (mRightTopCentre[X] + validWidth * 0.0082), mRightTopCentre[Y] - (mTopCircleRadius / 4 * 2));
        mRightTopTrianglePath.close();

        // 上方斜杆
        mBarPath = new Path();
        mBarPath.moveTo(mTopCentre[X] - mTopCircleRadius, mTopCentre[Y]);
        mBarPath.lineTo(mRightTopCentre[X] - mTopCircleRadius, mRightTopCentre[Y]);
        mBarPath.lineTo(mRightTopCentre[X] + mTopCircleRadius, mRightTopCentre[Y]);
        mBarPath.lineTo(mTopCentre[X] + mTopCircleRadius, mTopCentre[Y]);
        mBarPath.close();

        mSmallBarPath = new Path();
        mSmallBarPath.moveTo((float) (mTopCentre[X] - mTopCircleRadius * 0.3), mTopCentre[Y]);
        mSmallBarPath.lineTo((float) (mRightTopCentre[X] - mTopCircleRadius * 0.3), mRightTopCentre[Y]);
        mSmallBarPath.lineTo((float) (mRightTopCentre[X] + mTopCircleRadius * 0.3), mRightTopCentre[Y]);
        mSmallBarPath.lineTo((float) (mTopCentre[X] + mTopCircleRadius * 0.3), mTopCentre[Y]);
        mSmallBarPath.close();

        mShadeRectF = new RectF(0, mBottomCentre[Y] - getWidth() / 2, getWidth(), mBottomCentre[Y] + getWidth() / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 中下最大的齿轮
        canvas.drawCircle(mBottomCentre[X], mBottomCentre[Y], mBottomBigCircleRadius, mStrokePaint);

        canvas.save();
        canvas.rotate(mCurProgress * 180, mBottomCentre[X], mBottomCentre[Y]);
        for (int i = 0; i < 18; i ++) {
            // 中心齿轮最外围的齿轮上的锯齿 梯形
            canvas.drawPath(mBigSawtoothPath, mRedPaint);
            canvas.rotate(20.0f, mBottomCentre[X], mBottomCentre[Y]);
        }
        canvas.restore();

        // 大齿轮扇形遮罩
        mDarkRedPaint.setXfermode(mClearXfermode);
        canvas.drawArc(mShadeRectF, 180, 130, true, mDarkRedPaint);
        mDarkRedPaint.setXfermode(null);

        // 上方两个齿轮底下的斜向连杆
        canvas.drawPath(mBarPath, mTransitionPaint);
        canvas.drawPath(mSmallBarPath, mTransitionDarkPaint);

        // 上方两个齿轮底下的横向连杆
        canvas.drawRect(mLeftTopCentre[X], mLeftTopCentre[Y] - mTopCircleRadius / 4 * 3, mRightTopCentre[X], mRightTopCentre[Y] + mTopCircleRadius / 4 * 3, mRedPaint);

        // 左上齿轮
        canvas.drawCircle(mLeftTopCentre[X], mLeftTopCentre[Y], mTopCircleRadius, mRedPaint);
        canvas.drawCircle(mLeftTopCentre[X], mLeftTopCentre[Y], mTopCircleRadius / 4 * 3, mDarkRedPaint);
        canvas.save();
        canvas.rotate(mCurProgress * 360, mLeftTopCentre[X], mLeftTopCentre[Y]);
        for (int i = 0; i < 24; i ++) {
            // 齿轮的三角 path
            canvas.drawPath(mLeftTopTrianglePath, mRedPaint);
            canvas.rotate(15.0f, mLeftTopCentre[X], mLeftTopCentre[Y]);
        }
        canvas.restore();
        canvas.drawCircle(mLeftTopCentre[X], mLeftTopCentre[Y], mTopCircleRadius / 2, mRedPaint);
        canvas.drawCircle(mLeftTopCentre[X], mLeftTopCentre[Y], mTopCircleRadius / 5 * 2, mDarkRedPaint);
        canvas.drawCircle(mLeftTopCentre[X], mLeftTopCentre[Y], mTopCircleRadius / 4, mRedPaint);

        // 右上齿轮
        canvas.drawCircle(mRightTopCentre[X], mRightTopCentre[Y], mTopCircleRadius, mRedPaint);
        canvas.drawCircle(mRightTopCentre[X], mRightTopCentre[Y], mTopCircleRadius / 4 * 3, mDarkRedPaint);
        canvas.save();
        canvas.rotate(-mCurProgress * 360, mRightTopCentre[X], mRightTopCentre[Y]);
        for (int i = 0; i < 24; i ++) {
            // 齿轮的三角 path
            canvas.drawPath(mRightTopTrianglePath, mRedPaint);
            canvas.rotate(15.0f, mRightTopCentre[X], mRightTopCentre[Y]);
        }
        canvas.restore();
        canvas.drawCircle(mRightTopCentre[X], mRightTopCentre[Y], mTopCircleRadius / 2, mRedPaint);
        canvas.drawCircle(mRightTopCentre[X], mRightTopCentre[Y], mTopCircleRadius / 5 * 2, mDarkRedPaint);
        canvas.drawCircle(mRightTopCentre[X], mRightTopCentre[Y], mTopCircleRadius / 4, mRedPaint);

        // 中上齿轮
        canvas.drawCircle(mTopCentre[X], mTopCentre[Y], mTopCircleRadius, mRedPaint);
        canvas.drawCircle(mTopCentre[X], mTopCentre[Y], mTopCircleRadius / 4 * 3, mDarkRedPaint);
        canvas.drawCircle(mTopCentre[X], mTopCentre[Y], mTopCircleRadius / 4, mRedPaint);
        canvas.save();
        canvas.rotate(mCurProgress * 225, mTopCentre[X], mTopCentre[Y]);
        for (int i = 0; i < 8; i ++) {
            // 圆盘上的小圆点
            canvas.drawCircle(mTopCentre[X], mTopCentre[Y] - (mTopCircleRadius / 4 * 2), (float) (mTopCircleRadius * 0.0625), mRedPaint); // 此处的(mTopCircleRadius * 0.125) 表示小圆球的半径
            canvas.rotate(45.0f, mTopCentre[X], mTopCentre[Y]);
        }
        canvas.restore();

        // 中下齿轮
        canvas.save();
        canvas.rotate(-mCurProgress * 216, mBottomCentre[X], mBottomCentre[Y]);
        for (int i = 0; i < 15; i ++) {
            // 中心齿轮最外围的齿轮上的锯齿 梯形
            canvas.drawPath(mSmallSawtoothPath, mDarkRedPaint);
            canvas.rotate(24.0f, mBottomCentre[X], mBottomCentre[Y]);
        }
        canvas.restore();

        canvas.drawCircle(mBottomCentre[X], mBottomCentre[Y], mTopCircleRadius / 2 * 3, mDarkRedPaint);
        canvas.drawCircle(mBottomCentre[X], mBottomCentre[Y], mTopCircleRadius / 16 * 17, mRedPaint);
        // 投影
        mRedPaint.setShadowLayer(10.0f, 0f, 6.0f, ContextCompat.getColor(getContext(), R.color.shadow_black));
        canvas.drawCircle(mBottomCentre[X], mBottomCentre[Y], mTopCircleRadius / 80 * 55, mRedPaint);
        mRedPaint.setShadowLayer(0f, 0f, 0f, ContextCompat.getColor(getContext(), R.color.shadow_black));
        canvas.drawCircle(mBottomCentre[X], mBottomCentre[Y], mTopCircleRadius / 80 * 40, mDarkRedPaint);

        canvas.save();
        canvas.rotate(mCurProgress * 180, mBottomCentre[X], mBottomCentre[Y]);
        for (int i = 0; i < 8; i ++) {
            // 圆盘上的小圆点
            canvas.drawCircle(mBottomCentre[X], (float) (mBottomCentre[Y] - (mTopCircleRadius / 4 * 1.2)), (float) (mTopCircleRadius * 0.05), mRedPaint);
            canvas.rotate(45.0f, mBottomCentre[X], mBottomCentre[Y]);
        }
        canvas.restore();
        canvas.drawCircle(mBottomCentre[X], mBottomCentre[Y], mTopCircleRadius / 8, mRedPaint);

        // 左侧传送带
        canvas.drawLine(mTopCentre[X] - mTopCircleRadius * 0.85f, mTopCentre[Y],
                mBottomCentre[X] - mTopCircleRadius * 0.6f, mBottomCentre[Y],
                mBeltPaint);

        // 右侧传送带
        canvas.drawLine(mTopCentre[X] + mTopCircleRadius * 0.85f, mTopCentre[Y],
                mBottomCentre[X] + mTopCircleRadius * 0.6f, mBottomCentre[Y],
                mBeltPaint);
    }

    public void start() {
        stop();

        mValueAnimator = ValueAnimator.ofFloat(0f, 1f);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mCurProgress = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        mValueAnimator.setDuration(5000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.start();
    }

    public void stop() {
        if (null != mValueAnimator && mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
        }
    }

}