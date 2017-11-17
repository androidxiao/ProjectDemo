package cn.custom.widget.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.custom.widget.Px2DpUtil;

/**
 * Created by chawei on 2017/11/2.
 */

public class LoadingScaleAlphaView extends View {
    public static final String TAG = "ez";
    private int color;
    private Paint mPaint;
    private int mRadius;
    private int radius1;
    private int radius2;
    private AnimatorSet mAnimatorSet;
    private int minRadius;
    private int maxRadius;
    public LoadingScaleAlphaView(Context context) {
        this(context, null);
    }

    public LoadingScaleAlphaView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingScaleAlphaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRadius = Px2DpUtil.dp2px(context, 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(color);
        canvas.drawCircle(getHeight() / 2 + mRadius * 0 * 2 + 5 * 0, getHeight() / 2, radius1, mPaint);
        canvas.drawCircle(getHeight() / 2 + mRadius * 1 * 2 + 5 * 1, getHeight() / 2, radius2, mPaint);
        canvas.drawCircle(getHeight() / 2 + mRadius * 2 * 2 + 5 * 2, getHeight() / 2, radius1, mPaint);
    }

    public int getColor(){
        return color;
    }

    public void setColor(int color) {
        this.color=color;
        invalidate();
    }

    public int getRadius1(){
        return radius1;
    }

    public void setRadius1(int radius){
        radius1=radius;
        invalidate();
    }

    public int getRadius2(){
        return radius2;
    }

    public void setRadius2(int radius){
        radius2=radius;
        invalidate();
    }

    public void setMinRadius(int radius){
        minRadius=radius;
    }

    public void setMaxRadius(int radius){
        maxRadius=Px2DpUtil.dp2px(getContext(), radius);
    }

    public void startAnimate(LoadingScaleAlphaView mScaleAlphaView){
        ObjectAnimator animator1 = ObjectAnimator.ofInt(mScaleAlphaView, "color", 0xff3ec88e, 0xff24b484);
        animator1.setEvaluator(new ArgbEvaluator());
        animator1.setDuration(400);

        ObjectAnimator animator2 = ObjectAnimator.ofInt(mScaleAlphaView, "radius1", minRadius, maxRadius);
        animator2.setDuration(400);


        final ObjectAnimator animator3 = ObjectAnimator.ofInt(mScaleAlphaView, "radius1",  maxRadius,minRadius);
        animator3.setDuration(400);
        animator3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, "animator3结束了。。。");
                mAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });

        final ObjectAnimator animator4 = ObjectAnimator.ofInt(mScaleAlphaView, "radius2",  maxRadius,minRadius);
        animator4.setDuration(400);
        animator4.start();

        final ObjectAnimator animator5 = ObjectAnimator.ofInt(mScaleAlphaView, "radius2", minRadius,maxRadius);
        animator5.setDuration(400);

        animator4.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animator5.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator5.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animator4.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animator1, animator2);
        mAnimatorSet.start();
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animator3.setDuration(400);
                animator3.start();

            }
        });
    }

}
