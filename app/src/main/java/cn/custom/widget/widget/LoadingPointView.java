package cn.custom.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/13.
 */

public class LoadingPointView extends View {
    public static final int MESSAGE_ID = 0;
    public static final String TAG = "ez";
    //白色圆点
    private Paint mWhitePaint;
    //绿色圆点
    private Paint mGreenPaint;
    //画笔粗细
    private int mPaintWidth;
    //半径
    private int mRadius;
    //圆心X值
    private int mCircleX;
    //圆心Y值
    private int mCircleY;
    private int mIndex;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ++mIndex;
            if (mIndex == 5) {
                mIndex = 0;
            }
            postInvalidate();
        }
    };

    public LoadingPointView(Context context) {
        this(context, null);
    }

    public LoadingPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParmas(context);
    }

    private void initParmas(Context context) {
        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setStyle(Paint.Style.FILL);
        mWhitePaint.setColor(ContextCompat.getColor(context, R.color.white));

        mGreenPaint = new Paint();
        mGreenPaint.setAntiAlias(true);
        mGreenPaint.setStyle(Paint.Style.FILL);
        mGreenPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));

        mPaintWidth = Px2DpUtil.dp2px(context, 2);
        mCircleX = Px2DpUtil.dp2px(context, 40);
        mCircleY = Px2DpUtil.dp2px(context, 40);
        mRadius = Px2DpUtil.dp2px(context, 5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 5; i++) {
            canvas.drawCircle(getHeight() / 2 + mRadius * i * 2 + 5 * i, getHeight() / 2, mRadius, mWhitePaint);
        }
        canvas.drawCircle(getHeight() / 2 + mRadius * mIndex * 2 + 5 * mIndex, getHeight() / 2, mRadius, mGreenPaint);
        mHandler.sendEmptyMessageDelayed(MESSAGE_ID, 200);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(MESSAGE_ID);
        mHandler = null;
    }
}
