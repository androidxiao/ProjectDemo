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
import android.view.View;

/**
 * Created by chawei on 2017/12/16.
 */

public class PathMeasureView2 extends View {

    public static final String TAG = "gear2";

    private Paint mPaint;
    private Path mPath;

    public PathMeasureView2(Context context) {
        this(context, null);
    }

    public PathMeasureView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        PathMeasure pathMeasure = new PathMeasure(mPath,true);
        pathMeasure.getSegment(200, 400, path, true);

        canvas.drawPath(path, mPaint);

        /**
         * 1、 如果 startD、stopD 的数值不在取值范围 [0, getLength] 内，或者 startD == stopD 则返回值为 false，不会改变 dst 内容。
         * 2、如果在安卓4.4或者之前的版本，在默认开启硬件加速的情况下，更改 dst 的内容后可能绘制会出现问题，请关闭硬件加速
         *    或者给 dst 添加一个单个操作，例如: dst.rLineTo(0, 0)
         */
    }
}
