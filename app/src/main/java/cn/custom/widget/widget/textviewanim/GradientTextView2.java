package cn.custom.widget.widget.textviewanim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chawei on 2017/12/20.
 */

public class GradientTextView2 extends View {

    public static final String TAG = "gear2";

    private Paint mTextPaint;
    private float mTextHeight;
    private float mTextWidth;
    private String mText="正在上传...";
    private int translateDis;
    private PorterDuffXfermode mXfermode;
    private Paint mRectPaint;

    public GradientTextView2(Context context) {
        this(context,null);
    }

    public GradientTextView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GradientTextView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

    }

    private void init(){

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(60);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextHeight=metrics.bottom-metrics.top;
        mTextWidth = mTextPaint.measureText(mText);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);



        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mRectPaint.setColor(Color.RED);
        mRectPaint.setXfermode(mXfermode);

        initXMode();
    }

    private void initXMode(){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap srcBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_4444);
        Canvas srcCanvas = new Canvas(srcBitmap);
        srcCanvas.drawText(mText, 0, mTextHeight, mTextPaint);
        RectF rectF = new RectF(0, 0, translateDis, getMeasuredHeight());
        srcCanvas.drawRect(rectF, mRectPaint);
        canvas.drawBitmap(srcBitmap,0,0,null);
    }

    private void startAnim(){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, (int) mTextWidth);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translateDis= (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }
}
