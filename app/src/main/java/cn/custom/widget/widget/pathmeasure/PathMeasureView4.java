package cn.custom.widget.widget.pathmeasure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/16.
 */

public class PathMeasureView4 extends View {

    public static final String TAG = "gear2";

    private Paint mPaint;
    private Path mPath;
    //用于记录当前的位置，取值范围[0,1]映射Path的整个长度
    private float currentValue = 0;
    //当前点的实际位置
    private float[] pos;
    //当前点的tangent(tan)值，用于计算图片所需要的旋转角度
    private float[] tan;
    //箭头图片
    private Bitmap mBitmap;
    //矩阵，用于对图片进行一些操作
    private Matrix mMatrix;

    public PathMeasureView4(Context context) {
        this(context, null);
    }

    public PathMeasureView4(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView4(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 12;//缩放图片
        mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_path_arrow, options);
        mMatrix = new Matrix();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth() / 2, getHeight() / 2);

        mPath.addCircle(0, 0, 200, Path.Direction.CW);
        PathMeasure pathMeasure = new PathMeasure(mPath, false);
        //计算当前的位置在总长度上的比例[0,1]
        currentValue += 0.005;
        if (currentValue >= 1) {
            currentValue = 0;
        }
        //用于得到路径上某一长度的位置以及该位置的正切值（运动趋势或方向）
        /**
         * 返回值(boolean)	判断获取是否成功	true表示成功，数据会存入 pos 和 tan 中，
         * false     表示失败，pos 和 tan 不会改变
         * distance	 距离 Path 起点的长度	取值范围: 0 <= distance <= getLength
         * pos	     该点的坐标值	当前点在画布上的位置，有两个数值，分别为x，y坐标。
         * tan	     该点的正切值	当前点在曲线上的方向，使用 Math.atan2(tan[1], tan[0]) 获取到正切角的弧度值。
         */

        pathMeasure.getPosTan(pathMeasure.getLength() * currentValue, pos, tan);
        //重置Matrix
        mMatrix.reset();
        //计算图片旋转的角度
        float degrees = (float) Math.atan2(tan[1], tan[0] * 180.0 / Math.PI);
        //旋转图片
        mMatrix.postRotate(degrees, mBitmap.getWidth() / 2, mBitmap.getHeight() / 2);
        //将图片绘制中心调整到与当前点重合
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);
        canvas.drawPath(mPath, mPaint);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
        invalidate();
    }
}
