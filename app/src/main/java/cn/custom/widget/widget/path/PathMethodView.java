package cn.custom.widget.widget.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.custom.widget.Px2DpUtil;

/**
 * Created by chawei on 2017/12/14.
 */

public class PathMethodView extends View {


    private Paint mPaint;
    private Path mPath;
    private int mDis;

    public PathMethodView(Context context) {
        this(context,null);
    }

    public PathMethodView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathMethodView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);

        mDis = Px2DpUtil.dp2px(getContext(), 10);

        mPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //addArc
        RectF rectF = new RectF();
        rectF.left=mDis;
        rectF.top=mDis;
        rectF.right=mDis*3;
        rectF.bottom=mDis*3;
        mPath.addArc(rectF,0,90);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        //addCircle--->Path.Direction.CW
        mPath.addCircle(mDis*3,mDis*5,mDis, Path.Direction.CW);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        //addCircle--->Path.Direction.CCW
        mPath.addCircle(mDis*3,mDis*7,mDis, Path.Direction.CCW);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        //addOval--->Path.Direction.CW
        RectF rectF1 = new RectF();
        rectF1.top=mDis*8;
        rectF1.left=mDis;
        rectF1.right=mDis*13;
        rectF1.bottom=mDis*10;
        mPath.addOval(rectF1, Path.Direction.CW);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        //addOval--->Path.Direction.CW
        RectF rectF2 = new RectF();
        rectF2.top=mDis*11;
        rectF2.left=mDis;
        rectF2.right=mDis*3;
        rectF2.bottom=mDis*13;
        mPath.addOval(rectF2, Path.Direction.CCW);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        //addRect
        RectF rectF3 = new RectF();
        rectF3.top=mDis*14;
        rectF3.left=mDis;
        rectF3.right=mDis*4;
        rectF3.bottom=mDis*16;
        mPath.addRect(rectF3, Path.Direction.CW);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        //addRoundRect
        RectF rectF4 = new RectF();
        rectF4.top=mDis*17;
        rectF4.left=mDis;
        rectF4.right=mDis*4;
        rectF4.bottom=mDis*18;
        mPath.addRoundRect(rectF4, 10, 10, Path.Direction.CW);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        //arcTo--->true
        RectF rectF5 = new RectF();
        rectF5.top=mDis*19;
        rectF5.left=mDis;
        rectF5.right=mDis*4;
        rectF5.bottom=mDis*21;
        mPath.arcTo(rectF5,0,90,true);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        //arcTo--->true
        RectF rectF6 = new RectF();
        rectF6.top=mDis*22;
        rectF6.left=mDis;
        rectF6.right=mDis*4;
        rectF6.bottom=mDis*23;
        mPath.arcTo(rectF6,0,90,false);
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        //lineTo
//        mPath.moveTo(0,mDis*24);
//        mPath.lineTo(mDis*10,mDis*10);
        mPath.lineTo(mDis,mDis*24);
//        mPath.close();//会产生一个三角形

        canvas.drawPath(mPath,mPaint);
    }
}
