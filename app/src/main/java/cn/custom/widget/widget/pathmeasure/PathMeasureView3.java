package cn.custom.widget.widget.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by chawei on 2017/12/16.
 */

public class PathMeasureView3 extends View {

    public static final String TAG = "gear2";

    private Paint mPaint;
    private Path mPath;

    public PathMeasureView3(Context context) {
        this(context, null);
    }

    public PathMeasureView3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);

        mPath = new Path();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth() / 2, getHeight() / 2);

        Path path = new Path();

        RectF rectF = new RectF();
        rectF.left=-100;
        rectF.top=-100;
        rectF.right=100;
        rectF.bottom=100;
        mPath.addRect(rectF, Path.Direction.CW);

        rectF = new RectF();
        rectF.left=-200;
        rectF.top=-200;
        rectF.right=200;
        rectF.bottom=200;

        mPath.addRect(rectF, Path.Direction.CW);

        PathMeasure pathMeasure = new PathMeasure(mPath,true);
        //每次只会获取一个
//        Log.d(TAG, "onDraw: ------->"+pathMeasure.getLength());
        pathMeasure.nextContour();
//        Log.d(TAG, "onDraw: ------->"+pathMeasure.getLength());

        canvas.drawPath(path, mPaint);

        /**
         * 1、曲线顺序与Path中添加的顺序有关
         * 2、getLength获取到的是当前一条曲线的长度，而不是整个Path的长度
         * 3、getLength等方法是针对当前曲线的
         */


    }
}
