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

        PieModel pieModel = new PieModel("玄幻", 44.5f, ContextCompat.getColor(context, R.color.c_3ec88e));
        mPieModels.add(pieModel);
        pieModel = new PieModel("文学", 24.5f, ContextCompat.getColor(context, R.color.c_a330ff));
        mPieModels.add(pieModel);
        pieModel = new PieModel("古典", 14.5f, ContextCompat.getColor(context, R.color.c_d71345));
        mPieModels.add(pieModel);
        pieModel = new PieModel("科幻", 34.5f, ContextCompat.getColor(context, R.color.bg_button_login));
//        mPieModels.add(pieModel);
        pieModel = new PieModel("名著", 10.5f, ContextCompat.getColor(context, R.color.bg_gradient_end));
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(ScreenUtil.getScreenWidth() / 2, 500);

        RectF rectF = new RectF();
        rectF.left = -mBigRadius * 2;
        rectF.top = -mBigRadius * 2;
        rectF.right = mBigRadius * 2;
        rectF.bottom = mBigRadius * 2;

        int tempAngle=-45;

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
            mStopX = (float) ((mBigRadius * 2 + 50+mOuterRadius) * Math.cos(v));
            mStopY = (float) ((mBigRadius * 2 + 50+mOuterRadius) * Math.sin(v));
            canvas.translate(mStopX,mStopY);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(20);
            canvas.drawText(mPieModels.get(i).getText(),-mPaint.measureText(mPieModels.get(i).getText())/2,0,mPaint);
            canvas.restore();


            tempAngle += percent;
        }

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(0, 0, mMidRadius, mPaint);
        mPaint.setColor(Color.parseColor("#10000000"));
        canvas.drawCircle(0, 0, mSmallRadius, mPaint);

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
