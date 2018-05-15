package cn.custom.widget.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.custom.widget.model.PieData;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/5/13.
 */

public class AnimateScalePieChartView extends View {

    public static final String TAG = "ez";

    private final float DEFAULT_START_ANGLE = 180;

    /*
     * mPaint1: for sector
     * mPaint2: for center circle and center text
     * mPaint3: for center circle shadow
     */
    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;

    //get padding size
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mPaddingStart;
    private int mPaddingEnd;

    //coordinate for center of the view
    private int mCenterX;
    private int mCenterY;

    private float mStartAngle;
    private float mMaxValue;
    private float mTextSize;
    private float mShaderSize;

    //fraction for animator
    private float curtFraction = 1f;
    private float curtFractionTouch = 1f;
    private float curtFractionTouch2 = 0f;

    private int mCurrentColor;

    //mIndex for which sector touched
    private int mIndex;

    //list for pie params
    private List<Integer> mPieColorList;
    private List<Float> mPieValueList;
    private List<String> mPieStringList;

    //list for the middle angle of each sector
    private List<Float> angleList;

    //sector radius
    private int mRadius;

    //center circle radius
    private int circleRadius;

    //coordinate for touch
    private float touchX;
    private float touchY;

    //animator for init pie chart
    private ValueAnimator animator;

    //animator for touch
    private ValueAnimator animatorTouch;

    //center text
    private String text;

    //center bg
    private int centerColor;

    public AnimateScalePieChartView(Context context) {
        super(context, null);
    }

    public AnimateScalePieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimateScalePieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PieChartView);

        mRadius = a.getDimensionPixelSize(R.styleable.PieChartView_pie_radius,
                getResources().getDimensionPixelSize(R.dimen.pie_default_radius));
        circleRadius = a.getDimensionPixelSize(R.styleable.PieChartView_centerCircle_radius,
                getResources().getDimensionPixelSize(R.dimen.pie_center_radius));
        mTextSize = a.getDimension(R.styleable.PieChartView_textSize,
                getResources().getDimension(R.dimen.pie_text_size));
        mShaderSize = a.getDimension(R.styleable.PieChartView_shaderSize,
                getResources().getDimension(R.dimen.pie_shader_size));

        centerColor = getResources().getColor(R.color.color_window_background);

        a.recycle();

        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();
        mPaddingStart = getPaddingLeft();
        mPaddingEnd = getPaddingRight();

        initPaint();
        initAnimator();
    }

    /**
     * init Paint and params for pie chart
     */
    private void initPaint() {
        mPieColorList = new ArrayList<>();
        mPieValueList = new ArrayList<>();
        mPieStringList = new ArrayList<>();
        angleList = new ArrayList<>();


        mPaint1 = new Paint();
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint1.setAntiAlias(true);

        mPaint2 = new Paint();
        mPaint2.setColor(centerColor);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setAntiAlias(true);
        mPaint2.setTextSize(mTextSize);
        mPaint2.setTextAlign(Paint.Align.CENTER);

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setAntiAlias(true);
    }

    /**
     * init animators
     */
    private void initAnimator() {
        animator = ValueAnimator.ofFloat(0, 1f);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curtFraction = animation.getAnimatedFraction();
                mStartAngle = DEFAULT_START_ANGLE;
                invalidate();
            }
        });

        animatorTouch = ValueAnimator.ofFloat(1f, 1.07f);
//        animatorTouch = ValueAnimator.ofFloat(1f, 1.0f);
        animatorTouch.setDuration(400);
        animatorTouch.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curtFractionTouch = (Float) animation.getAnimatedValue();
                curtFractionTouch2 = 0.02f * animation.getAnimatedFraction();
                invalidate();
            }
        });

    }

    /**
     * @param i mIndex for params list
     * @return angle to rotation
     */
    private float getRotationAngle(int i) {
        float angleR;
        float angleT = angleList.get(i);
        if (angleT <= 270f && angleT >= 90f) {
            angleR = 90f - angleT;
        } else if (angleT > 270f && angleT <= 360f) {
            angleR = 360f - angleT + 90f;
        } else if (angleT >= 0 && angleT < 90) {
            angleR = 90 - angleT;
        } else {
            angleR = 0;
        }

        for (int id = 0; id < angleList.size(); id++) {
            float temp = angleList.get(id) + angleR;
            if (temp > 360f) {
                temp -= 360f;
            } else if (temp < 0) {
                temp += 360f;
            }
            angleList.set(id, temp);
        }
        return angleR;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mWidth = mRadius * 2 + mPaddingStart + mPaddingEnd;
        int mHeight = mRadius * 2 + mPaddingTop + mPaddingBottom;

        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT &&
                getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, mHeight);
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = mPaddingStart + (w - mPaddingStart - mPaddingEnd) / 2;
        mCenterY = mPaddingTop + (h - mPaddingTop - mPaddingBottom) / 2;
        mPaint3.setShader(new RadialGradient(mCenterX, mCenterY,
                circleRadius + mShaderSize,
                Color.TRANSPARENT, Color.TRANSPARENT, Shader.TileMode.CLAMP));
    }

    /**
     * @param amount value of each sector
     */
    private void drawPie(Canvas canvas, float amount,int i) {
        mPaint1.setColor(mCurrentColor);
        float mAngle = 360 * curtFraction * amount / mMaxValue;
        RectF oval = new RectF(mCenterX - mRadius, mCenterY - mRadius,mCenterX + mRadius,mCenterY + mRadius);
        canvas.drawArc(oval, mStartAngle, mAngle, true, mPaint1);
        mStartAngle += mAngle;
    }

    /**
     * draw small sector
     */
    private void drawPieTouch(Canvas canvas, float amount,int i) {
        mPaint1.setColor(mCurrentColor);
        float mAngle = 360 * curtFraction * amount / mMaxValue;
        float mRadiusTemp = mRadius * curtFractionTouch;
        RectF oval = new RectF(mCenterX - mRadiusTemp, mCenterY - mRadiusTemp,mCenterX + mRadiusTemp,mCenterY + mRadiusTemp);
        canvas.drawArc(oval, mStartAngle + mAngle * curtFractionTouch2, mAngle - mAngle * curtFractionTouch2 * 2, true, mPaint1);
        mStartAngle += mAngle;
        canvas.drawText(mPieStringList.get(i),getMeasuredWidth()/2,getMeasuredHeight()/4,mPaint2);
    }

    /**
     * @param text center text
     */
    public void setCenterText(String text) {
        this.text = text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mPieValueList.size(); i++) {
            mCurrentColor = mPieColorList.get(i);
            if (i == mIndex) {
                canvas.save();
                //选中的那一块向上平移20
//                canvas.translate(0,-20);
                drawPieTouch(canvas, mPieValueList.get(i),i);
                canvas.restore();
            } else {
                drawPie(canvas, mPieValueList.get(i),i);
            }
        }
        canvas.drawCircle(mCenterX, mCenterY,
                circleRadius + mShaderSize, mPaint3);
        mPaint2.setColor(centerColor);
        //中心圆
//        canvas.drawCircle(mCenterX, mCenterY, circleRadius, mPaint2);
        if (text != null) {
            mPaint2.setColor(Color.BLACK);
            canvas.drawText(text, mCenterX, mCenterY + mTextSize / 2, mPaint2);
        }
    }

    /**
     * touch transfer
     * @param i mIndex for params list
     */
    public void onTouchPie(int i) {
        mIndex = i;
        curtFractionTouch = 1f;
        curtFractionTouch2 = 0f;

        float angle = getRotationAngle(i);

        ValueAnimator animatorRotation;
        animatorRotation = ValueAnimator.ofFloat(mStartAngle, mStartAngle + angle);
        animatorRotation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mStartAngle = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });

        int time = (int) (1000 * Math.abs(angle) / 360);

        animatorRotation.setDuration(time);
        animatorTouch.setStartDelay(time);

        animatorRotation.start();
        animatorTouch.start();
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                touchX = event.getX();
//                touchY = event.getY();
//                break;
//            case MotionEvent.ACTION_UP:
//
//                setDrawingCacheEnabled(true);
//                Bitmap bitmap = getDrawingCache();
//                if (bitmap == null) {
//                    break;
//                }
//                int pixel = bitmap.getPixel((int) touchX, (int) touchY);
//                setDrawingCacheEnabled(false);
//
//                int i = 0;
//                for (int color : mPieColorList) {
//                    if (pixel == color) {
//                        onTouchPie(i);
//                        break;
//                    }
//                    i++;
//                }
//                break;
//        }
//        return true;
//    }

    /**
     * set pie chart params
     *
     * @param pieList pie params list
     */
    public void setPie(List<PieData> pieList) {
        mMaxValue = 0;
        curtFractionTouch = 1f;
        curtFractionTouch2 = 0f;

        mPieColorList = new ArrayList<>();
        mPieStringList = new ArrayList<>();
        mPieValueList = new ArrayList<>();
        angleList = new ArrayList<>();

        for (PieData pie : pieList) {
            mPieColorList.add(pie.pieColor);
            mPieValueList.add(pie.pieValue);
            mMaxValue += pie.pieValue;
            mPieStringList.add(pie.pieString);
        }

        float angleTemp;
        float startAngleTemp = DEFAULT_START_ANGLE;

        for (float v : mPieValueList) {
            angleTemp = 360 * v / mMaxValue;

            angleList.add(startAngleTemp + angleTemp / 2);

            startAngleTemp += angleTemp;
        }
    }

}
