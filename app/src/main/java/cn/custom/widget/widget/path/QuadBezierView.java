package cn.custom.widget.widget.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chawei on 2017/12/14.
 *
 * 二阶贝赛尔曲线
 */

public class QuadBezierView extends View {

    private Paint mPaint;
    private int centerX;
    private int centerY;
    private PointF mStartPointF;
    private PointF mCenterPointF;
    private PointF mEndPointF;

    public QuadBezierView(Context context) {
        this(context,null);
    }

    public QuadBezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QuadBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init(){

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(8);
        mPaint.setTextSize(60);
        mPaint.setStyle(Paint.Style.STROKE);

        mStartPointF = new PointF(200,200);
        mCenterPointF = new PointF(300, 200);
        centerX=300;
        centerY=200;
        mEndPointF = new PointF(400, 200);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(mStartPointF.x,mStartPointF.y,mPaint);
        canvas.drawPoint(mCenterPointF.x,mCenterPointF.y,mPaint);
        canvas.drawPoint(mEndPointF.x,mEndPointF.y,mPaint);

        canvas.drawLine(mStartPointF.x,mStartPointF.y,centerX,centerY,mPaint);

//        canvas.drawLine(centerX,centerY,mEndPointF.x,mEndPointF.y,mPaint);
        canvas.drawLine(mEndPointF.x,mEndPointF.y,centerX,centerY,mPaint);

        Path path = new Path();

        path.moveTo(mStartPointF.x,mStartPointF.y);

        path.quadTo(centerX,centerY,mEndPointF.x,mEndPointF.y);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(path,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        centerX= (int) event.getX();
        centerY= (int) event.getY();
        invalidate();
        return true;
    }
}
