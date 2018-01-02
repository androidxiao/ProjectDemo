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
 */

public class CubicBezierView extends View {

    private Paint mPaint;
    private int centerX;
    private int centerY;
    private PointF mStartPointF;
    private PointF mCenter1PointF;
    private PointF mCenter2PointF;
    private PointF mEndPointF;
    private boolean mode;

    public CubicBezierView(Context context) {
        this(context,null);
    }

    public CubicBezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CubicBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mCenter1PointF = new PointF(300, 200);
        mCenter2PointF = new PointF(300, 200);
        mCenter1PointF.x=300;
        mCenter1PointF.y=100;

        mCenter2PointF.x=300;
        mCenter2PointF.y=300;
        mEndPointF = new PointF(400, 200);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(mStartPointF.x,mStartPointF.y,mPaint);
        canvas.drawPoint(mCenter1PointF.x,mCenter1PointF.y,mPaint);
        canvas.drawPoint(mCenter2PointF.x,mCenter2PointF.y,mPaint);
        canvas.drawPoint(mEndPointF.x,mEndPointF.y,mPaint);

        canvas.drawLine(mStartPointF.x,mStartPointF.y,mCenter1PointF.x,mCenter1PointF.y,mPaint);
        canvas.drawLine(mCenter1PointF.x,mCenter1PointF.y,mCenter2PointF.x,mCenter2PointF.y,mPaint);
//        canvas.drawLine(centerX,centerY,mEndPointF.x,mEndPointF.y,mPaint);
        canvas.drawLine(mEndPointF.x,mEndPointF.y,mCenter2PointF.x,mCenter2PointF.y,mPaint);

        Path path = new Path();

        path.moveTo(mStartPointF.x,mStartPointF.y);
        path.cubicTo(mCenter1PointF.x,mCenter1PointF.y,mCenter2PointF.x,mCenter2PointF.y,mEndPointF.x,mEndPointF.y);
        mPaint.setColor(Color.BLACK);
        canvas.drawPath(path,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y= (int) event.getY();
        if(mode) {
            mCenter1PointF.x = x;
            mCenter1PointF.y = y;
        }else {
            mCenter2PointF.x = x;
            mCenter2PointF.y = y;
        }
        invalidate();
        return true;
    }

    public void setMode(boolean mode){
        mCenter1PointF.x=300;
        mCenter1PointF.y=100;

        mCenter2PointF.x=300;
        mCenter2PointF.y=300;
        this.mode=mode;
        invalidate();
    }
}
