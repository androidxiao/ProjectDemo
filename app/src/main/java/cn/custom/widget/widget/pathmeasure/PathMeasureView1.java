package cn.custom.widget.widget.pathmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chawei on 2017/12/16.
 */

public class PathMeasureView1 extends View {

    public static final String TAG = "gear2";

    private Paint mPaint;
    private Path mPath;

    public PathMeasureView1(Context context) {
        this(context,null);
    }

    public PathMeasureView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PathMeasureView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private  void init(){
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

        canvas.translate(getWidth()/2,getHeight()/2);

        mPath.lineTo(0,100);
        mPath.lineTo(100,100);
        mPath.lineTo(100,0);

        PathMeasure pathMeasure1 = new PathMeasure(mPath, false);
        PathMeasure pathMeasure2 = new PathMeasure(mPath, true);
//        Log.d(TAG, "onDraw: ----->"+pathMeasure1.getLength());
//        Log.d(TAG, "onDraw: ----->"+pathMeasure2.getLength());

        canvas.drawPath(mPath, mPaint);

        /**
         * 1.我们将 Path 与两个的 PathMeasure 进行关联，并给 forceClosed 设置了不同的状态，之后绘制再绘制出来的 Path 没有任何变化，
         *   所以与 Path 与 PathMeasure进行关联并不会影响 Path 状态。
         *
         * 2.我们可以看到，设置 forceClosed 为 true 的方法比设置为 false 的方法测量出来的长度要长一点，这是由于 Path 没有闭合的缘故，
         *   多出来的距离正是 Path 最后一个点与最开始一个点之间点距离。forceClosed 为 false 测量的是当前 Path 状态的长度， forceClosed 为 true，
         *   则不论Path是否闭合测量的都是 Path 的闭合长度。
         */
    }
}
