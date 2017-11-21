package cn.custom.widget.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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
 * Created by chawei on 2017/11/20.
 */

public class TwitterLikeView extends View {
    public static final String TAG = "ez";
    private Paint mPaint;
    private Bitmap mBitmap;
    private Bitmap mSelectBitmap;
    private Canvas mCanvas;
    private Matrix mMatrix;
    private int mMinRadius;
    private int mMaxRaidus;
    private int mRadius;
    private int mWhiteRadius;
    private boolean mShowBitmap = true;
    private boolean mShowSelectBitmap = false;
    private int colorId;
    private Matrix mMatrix1;
    private ObjectAnimator mObjectAnimatorX;

    public TwitterLikeView(Context context) {
        this(context, null);
    }

    public TwitterLikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwitterLikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Px2DpUtil.dp2px(context, 1));
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_five_angle_star);

        mSelectBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_five_angle_star_selected);
        mMatrix1 = new Matrix();
        mMatrix1.postScale(0.7f, 0.7f);
        mSelectBitmap = Bitmap.createBitmap(mSelectBitmap, 0, 0, mSelectBitmap.getWidth(), mSelectBitmap.getHeight(), mMatrix1, true);

        mMinRadius = Px2DpUtil.dp2px(context, 5);
        mRadius = Px2DpUtil.dp2px(context, 5);
        mWhiteRadius = Px2DpUtil.dp2px(context, 5);
        mMaxRaidus = Px2DpUtil.dp2px(context, 20);
    }

    public void setMRadius(int radius) {
        mRadius = radius;
        invalidate();
    }

    public int getMRadius() {
        return mRadius;
    }


    public void setColorId(int colorId) {
        this.colorId = colorId;
        invalidate();
    }

    public int getColorId() {
        return colorId;
    }

    public int getMWhiteRadius() {
        return mWhiteRadius;
    }

    public void setMWhiteRadius(int radius) {
        mWhiteRadius = radius;
        invalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        mCanvas = canvas;

        canvas.drawColor(getContext().getResources().getColor(android.R.color.white));

//        canvas.translate(ScreenUtil.getScreenWidth() / 2, 300);
        canvas.translate(getWidth()/2, getHeight()/2);

        if (mShowBitmap) {
            canvas.drawBitmap(mBitmap, -mBitmap.getWidth()/2, -mBitmap.getHeight()/2, mPaint);
        }else{
            mPaint.setColor(colorId);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(0, 0, mRadius, mPaint);
            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(0, 0, mWhiteRadius, mPaint);
            if(mShowSelectBitmap) {
                canvas.drawBitmap(mSelectBitmap, -mSelectBitmap.getWidth()/2, -mSelectBitmap.getHeight()/2, mPaint);


                drawOuterCircle();
            }
        }


    }

    public void starUnSelectAnimation() {

        mMatrix = new Matrix();
        mMatrix.postScale(0.9f, 0.9f);
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mMatrix, true);
        invalidate();
        mHandler.sendEmptyMessageDelayed(0, 200);
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mMatrix.postScale(0.8f, 0.8f);
                    mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mMatrix, true);
                    invalidate();
                    mHandler.sendEmptyMessageDelayed(1, 200);
                    break;
                case 1:
                    mPaint.setAlpha(0);
                    invalidate();
//                    mHandler.sendEmptyMessageDelayed(2, 200);
                    mHandler.sendEmptyMessage(2);
                    break;
                case 2:
                    mShowBitmap = false;
//                    mPaint.reset();
                    ObjectAnimator animator1 = ObjectAnimator.ofInt(TwitterLikeView.this, "colorId", 0xffff7918, 0xffffa81f);
                    animator1.setEvaluator(new ArgbEvaluator());
                    animator1.setDuration(600);

                    ObjectAnimator objectAnimator = ObjectAnimator.ofInt(TwitterLikeView.this, "mRadius", mMinRadius, mMaxRaidus);
                    objectAnimator.setDuration(600);

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(animator1, objectAnimator);
                    animatorSet.start();

                    final ObjectAnimator objectAnimatorWhite = ObjectAnimator.ofInt(TwitterLikeView.this, "mWhiteRadius", mMinRadius, mMaxRaidus);
                    objectAnimatorWhite.setDuration(600);
                    objectAnimatorWhite.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    animatorSet.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mShowSelectBitmap = true;
                            selectStarAnimation();
                            objectAnimatorWhite.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    break;
            }

        }
    };

    private float scaleX = 0.7f;

    public void setScaleX(float scaleX) {
//        Log.d(TAG, "setScaleX: ---->" + scaleX);
        this.scaleX = scaleX;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleX, scaleX);
        mSelectBitmap = Bitmap.createBitmap(mSelectBitmap, 0, 0, mSelectBitmap.getWidth(), mSelectBitmap.getHeight(), matrix, true);

        invalidate();
    }

    public float getScaleX() {
        return scaleX;
    }

    private void selectStarAnimation() {
        mObjectAnimatorX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f,1.1f);
        mObjectAnimatorX.setDuration(400);
        mObjectAnimatorX.start();

        mObjectAnimatorX.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                drawOuterCircle();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void drawOuterCircle(){
        mPaint.setColor(ContextCompat.getColor(getContext(),R.color.c_24b484));
        mCanvas.save();
//        double v = (45.0f / 2) / 180 * 3.14;
//        float mStartX = (float) (mMaxRaidus * 2 * Math.cos(v));
//        float mStartY = (float) (mMaxRaidus * 2 * Math.sin(v));
//        mCanvas.translate(mStartX,mStartY);
        mCanvas.translate(mMaxRaidus/2,mMaxRaidus-5);
        mCanvas.drawCircle(0,0,10,mPaint);
        mCanvas.restore();
    }
}
