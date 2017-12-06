package cn.custom.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.custom.widget.Px2DpUtil;
import cn.custom.widget.model.PieModel;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/11/17.
 */

public class PieProgressView extends View {

    public static final String TAG = "ez";
    private ArrayList<PieModel> mPieModels;
    private int mBigRadius;
    private int mMidRadius;
    private int mSmallRadius;
    private int mOuterRadius;
    private float mTotalPercent;
    private Paint mPaint;
    private float mStartX;
    private float mStartY;
    private float mStopX;
    private float mStopY;

    public PieProgressView(Context context) {
        this(context, null);
    }

    public PieProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mPieModels = new ArrayList<>();

        PieModel pieModel = new PieModel("玄幻", 44f, ContextCompat.getColor(context, R.color.c_3ec88e));
        mPieModels.add(pieModel);
        pieModel = new PieModel("文学", 24f, ContextCompat.getColor(context, R.color.c_a330ff));
        mPieModels.add(pieModel);
        pieModel = new PieModel("古典", 14f, ContextCompat.getColor(context, R.color.c_d71345));
        mPieModels.add(pieModel);
        pieModel = new PieModel("科幻", 34f, ContextCompat.getColor(context, R.color.bg_button_login));
        mPieModels.add(pieModel);
        pieModel = new PieModel("名著", 10f, ContextCompat.getColor(context, R.color.bg_gradient_end));
        mPieModels.add(pieModel);
        pieModel = new PieModel("武侠", 5f, ContextCompat.getColor(context, R.color.colorBack));
        mPieModels.add(pieModel);
        pieModel = new PieModel("仙侠", 15f, ContextCompat.getColor(context, R.color.placeholder_grey_20));
        mPieModels.add(pieModel);
        pieModel = new PieModel("军事", 3f, ContextCompat.getColor(context, R.color.c1));
        mPieModels.add(pieModel);
//        pieModel = new PieModel("历史", 2.5f, ContextCompat.getColor(context, R.color.blue_color));
//        mPieModels.add(pieModel);
//        pieModel = new PieModel("游戏", 8.5f, ContextCompat.getColor(context, R.color.c2));
//        mPieModels.add(pieModel);
//        pieModel = new PieModel("体育", 9.5f, ContextCompat.getColor(context, R.color.c3));
//        mPieModels.add(pieModel);
//        pieModel = new PieModel("二次元", 1.5f, ContextCompat.getColor(context, R.color.c4));
//        mPieModels.add(pieModel);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(Px2DpUtil.dp2px(context, 2));
        mPaint.setStyle(Paint.Style.FILL);

        mBigRadius = Px2DpUtil.dp2px(context, 50);
        mMidRadius = Px2DpUtil.dp2px(context, 60);
        mSmallRadius = Px2DpUtil.dp2px(context, 65);
        mOuterRadius = Px2DpUtil.dp2px(context, 20);

        for (int i = 0; i < mPieModels.size(); i++) {
            mTotalPercent += mPieModels.get(i).getPercent();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minimumWidth = getSuggestedMinimumWidth();
        int minimumHeight = getSuggestedMinimumHeight();
        int validWidth = measureWidth(minimumWidth, widthMeasureSpec);
        int validHeight = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(validWidth,validHeight);
    }

    private int measureWidth(int defaultWidth,int measureSpec){
        int widthMode = MeasureSpec.getMode(measureSpec);
        int widthSize = MeasureSpec.getSize(measureSpec);
        switch (widthMode) {
            case MeasureSpec.AT_MOST://wrap_content
//                Log.d(TAG, "measureWidth: --AT_MOST----->"+defaultWidth);
                break;
            case MeasureSpec.EXACTLY://match_parent,具体值
                defaultWidth=widthSize;
//                Log.d(TAG, "measureWidth: --1111-->"+defaultWidth);
                break;
            case MeasureSpec.UNSPECIFIED://无限大，子view想多大就多大
                defaultWidth = Math.max(defaultWidth, widthSize);
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight,int measureSpec){
        int heightMode = MeasureSpec.getMode(measureSpec);
        int heightSize = MeasureSpec.getSize(measureSpec);
        switch (heightMode) {
            //当外部是ScrollView时，xml为wrap_content，仍不会走AT_MOST,而是走UNSPECIFIED，
            // 只有当外部不是ScrollView时，xml为wrap_content，才会走AT_MOST
            case MeasureSpec.AT_MOST://wrap_content
                double calLineLength = caclute2PointDis(0, mOuterRadius, 0, mOuterRadius + 50);
                defaultHeight = (mBigRadius * 2 + mOuterRadius * 2 + Px2DpUtil.dp2px(getContext(), (float) calLineLength) * 6);
//                Log.d(TAG, "measureHeight: --AT_MOST-->"+defaultHeight);
                break;
            case MeasureSpec.EXACTLY://match_parent,具体值
                defaultHeight=heightSize;
//                Log.d(TAG, "measureHeight: --EXACTLY-->"+defaultHeight);
                break;
            case MeasureSpec.UNSPECIFIED://无限大，子view想多大就多大
//                defaultHeight= Math.max(defaultHeight, heightSize);
                double calLineLength1 = caclute2PointDis(0, mOuterRadius, 0, mOuterRadius + 50);
                defaultHeight =(mBigRadius * 3 + mOuterRadius * 2 + Px2DpUtil.dp2px(getContext(), (float) calLineLength1) * 6);
//                Log.d(TAG, "measureHeight: --UNSPECIFIED-->"+defaultHeight);
                break;
        }
        return defaultHeight;
    }


    /**
     * 圆点坐标：(x0,y0)
     * 半径：r
     * 角度：a0
     * <p>
     * 则圆上任一点为：（x1,y1）
     * x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
     * y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
     *
     * @param canvas
     */

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(ScreenUtil.getScreenWidth() / 2, 500);
        canvas.drawColor(Color.YELLOW);
        RectF rectF = new RectF();
        rectF.left = -mBigRadius * 2;
        rectF.top = -mBigRadius * 2;
        rectF.right = mBigRadius * 2;
        rectF.bottom = mBigRadius * 2;

        float tempAngle = -45.0f;
        float rotateAngle=45;
        for (int i = 0; i < mPieModels.size(); i++) {

            float percent = mPieModels.get(i).getPercent() / mTotalPercent * 360;
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mPieModels.get(i).getColorId());
            canvas.drawArc(rectF, tempAngle, percent, true, mPaint);
            double v = (tempAngle + percent / 2) / 180 * 3.14;
            mStartX = (float) (mBigRadius * 2 * Math.cos(v));
            mStartY = (float) (mBigRadius * 2 * Math.sin(v));
            mStopX = (float) ((mBigRadius * 2 + 50) * Math.cos(v));
            mStopY = (float) ((mBigRadius * 2 + 50) * Math.sin(v));
            canvas.drawLine(mStartX, mStartY, mStopX, mStopY, mPaint);

            canvas.save();
            canvas.rotate(tempAngle + percent / 2);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.WHITE);
            double dis = caclute2PointDis(mStartX, mStartY, mStopX, mStopY);
            canvas.drawCircle((float) (mBigRadius * 2 + dis + mOuterRadius), 0, mOuterRadius, mPaint);
            canvas.restore();

            canvas.save();
            mStopX = (float) ((mBigRadius * 2 + 50 + mOuterRadius) * Math.cos(v));
            mStopY = (float) ((mBigRadius * 2 + 50 + mOuterRadius) * Math.sin(v));
            canvas.translate(mStopX, mStopY);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(20);
            canvas.drawText(mPieModels.get(i).getText(), -mPaint.measureText(mPieModels.get(i).getText()) / 2, 0, mPaint);
            mPaint.setTextSize(30);
            mPaint.setColor(mPieModels.get(i).getColorId());
            if(rotateAngle>=120&&rotateAngle<=270) {
                canvas.drawLine(0, mOuterRadius,  0, (mOuterRadius+50),mPaint);
                canvas.drawText((int)mPieModels.get(i).getPercent() + "%", -mPaint.measureText((int)mPieModels.get(i).getPercent() + "%") / 2, (mOuterRadius + 80), mPaint);
            }else{
                canvas.drawLine(0, -mOuterRadius,  0, -(mOuterRadius+50),mPaint);
                canvas.drawText((int)mPieModels.get(i).getPercent() + "%", -mPaint.measureText((int)mPieModels.get(i).getPercent() + "%") / 2, -(mOuterRadius + 60), mPaint);
            }
            canvas.restore();


            tempAngle += percent;
            rotateAngle+=Math.abs(percent);//计算到哪个临界值时，百分比该显示在下方
        }

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, mMidRadius, mPaint);
        mPaint.setColor(Color.parseColor("#10000000"));
        canvas.drawCircle(0, 0, mSmallRadius, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                Log.d(TAG, "onTouchEvent: --->"+x+"---->"+y);
                break;
        }
        return super.onTouchEvent(event);
    }

    private double caclute2PointDis(float startX, float startY, float stopX, float stopY) {
        PointF p1 = new PointF(startX, startY);
        PointF p2 = new PointF(stopX, stopY);
        // 两点间距离
        double dis = Math.sqrt(Math.abs((p1.x - p2.x)
                * (p1.x - p2.x) + (p1.y - p2.y)
                * (p1.y - p2.y)));

        return dis;
    }

}
