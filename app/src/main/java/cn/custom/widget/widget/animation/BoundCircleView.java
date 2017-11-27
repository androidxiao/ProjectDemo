package cn.custom.widget.widget.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by chawei on 2017/11/23.
 */

public class BoundCircleView extends View {


    private Paint mPaint;
    private float mRadius=20f;
    public BoundCircleView(Context context) {
        this(context,null);
    }

    public BoundCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BoundCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(300,300,mRadius,mPaint);
    }

    public void starAnimator(){
        RaidusCircle startValue = new RaidusCircle();
        startValue.setRadius(20f);

        RaidusCircle endValue = new RaidusCircle();
        endValue.setRadius(100f);

        ValueAnimator valueAnimator =ValueAnimator.ofObject(new RadiusEvaluator(), startValue, endValue);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RaidusCircle circle= (RaidusCircle) animation.getAnimatedValue();
                mRadius=circle.getRadius();
                invalidate();
            }
        });

        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.start();
    }

}
