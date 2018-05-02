package cn.custom.widget.widget.textviewanim;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chawei on 2017/12/20.
 */

public class FlashTextView extends View {

    private Context context;
    private TextPaint paint;
    private LinearGradient linearGradient;
    private Matrix matrix;
    private ObjectAnimator objectAnimator;
    private boolean isInvoke = false;
    private float gradientX;

    public float getGradientX() {
        return gradientX;
    }

    public void setGradientX(float gradientX) {
        this.gradientX = gradientX;
        System.out.println("setGradientX");
        invalidate();
    }

    public FlashTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new TextPaint();
        matrix = new Matrix();
        paint.setTextSize(20);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
    }

    // 为什么要在onSizeChanged中实现而不在onLayout中实现？因为onLayout中被调用了两次，而onSizeChanged中被调用一次，不明白为什么，但是打印日志确实是这样。
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        objectAnimator = ObjectAnimator.ofFloat(this, "gradientX", 0,
                getWidth());
        objectAnimator.setDuration(1000);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        linearGradient = new LinearGradient(-getWidth(), 0, 0, 0, new int[] {
                Color.GRAY, Color.WHITE, Color.GRAY }, new float[] { 0f,
                0.5f, 1f }, Shader.TileMode.CLAMP);

        linearGradient.setLocalMatrix(matrix);
        paint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth()/2,getHeight()/2);

        if (!isInvoke) {
            animate.run();
        }

        matrix.setTranslate(2 * gradientX, 0);
        linearGradient.setLocalMatrix(matrix);

        canvas.drawText("闪动的文字",0,0,paint);

    }

    private Runnable animate = new Runnable() {
        @Override
        public void run() {
            isInvoke = true;
            objectAnimator.start();
        }
    };


    @Override
    protected void onDetachedFromWindow() {
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        super.onDetachedFromWindow();
    }
}
