package cn.custom.widget.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/1.
 */

public class GearView2 extends View implements OnClickListener{

    public static final String TAG = "gear2";

    private Paint mCirclePaint;
    private int mCircleRadius;
    private float mCircleX;
    private float mCircleY;
    private Path mTopPath;
    private Paint mLadderPaint;
    private int mSmallRadius;
    private float mCurrAngle;
    private float mBotCurrAngle;
    private ValueAnimator mValueAnimator;
    private float mBotCircleX;
    private float mBotCircleY;
    private Path mBotPath;
    private ValueAnimator mBotValueAnimator;

    public GearView2(Context context) {
        this(context,null);
    }

    public GearView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GearView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context){
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(Px2DpUtil.dp2px(context,10));
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
        mCirclePaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));


        mLadderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLadderPaint.setStyle(Paint.Style.FILL);
        mLadderPaint.setStrokeWidth(Px2DpUtil.dp2px(context,1));
        mLadderPaint.setStrokeCap(Paint.Cap.ROUND);
        mLadderPaint.setStrokeJoin(Paint.Join.ROUND);
        mLadderPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));

        mCircleRadius = Px2DpUtil.dp2px(context, 30);

        mSmallRadius=mCircleRadius/2;

        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        int width = getWidth();
        int height = getHeight();

        int validWidth=width-left-right;
        int validHeight=height-top-bottom;

        mCircleX= (float) (left+validWidth*0.5);
        mCircleY= (float) (top+validHeight*0.3);

        mTopPath = new Path();
        Log.d(TAG, "mCircleX: --->"+mCircleX);
        Log.d(TAG, "mCircleY: --->"+mCircleY);
        Log.d(TAG, "mCircleRadius: --->"+mCircleRadius);
        mTopPath.moveTo(mCircleX,mCircleY-mCircleRadius-mCirclePaint.getStrokeWidth()/2);
        mTopPath.lineTo(mCircleX,mCircleY-mCircleRadius*1.5f-mCirclePaint.getStrokeWidth()/5);
        mTopPath.lineTo(mCircleX+mCircleRadius/3,mCircleY-mCircleRadius*1.5f-mCirclePaint.getStrokeWidth()/5);
        mTopPath.lineTo(mCircleX+mCircleRadius/3,mCircleY-mCircleRadius-mCirclePaint.getStrokeWidth()/5);
        mTopPath.close();

        float point2Dis = (float) (caclute2PointDis(mCircleX, mCircleY - mCircleRadius - mCirclePaint.getStrokeWidth() / 2, mCircleX, mCircleY - mCircleRadius * 1.5f - mCirclePaint.getStrokeWidth() / 5) * 2);
        mBotCircleX=mCircleX+mCircleRadius*2+mCirclePaint.getStrokeWidth()*2;
        mBotCircleY=mCircleY+mCircleRadius+mCirclePaint.getStrokeWidth();

        mBotPath = new Path();
        mBotPath.moveTo(mBotCircleX,mBotCircleY-mCircleRadius-mCirclePaint.getStrokeWidth()/2);
        mBotPath.lineTo(mBotCircleX,mBotCircleY-mCircleRadius*1.5f-mCirclePaint.getStrokeWidth()/5);
        mBotPath.lineTo(mBotCircleX+mCircleRadius/3,mBotCircleY-mCircleRadius*1.5f-mCirclePaint.getStrokeWidth()/5);
        mBotPath.lineTo(mBotCircleX+mCircleRadius/3,mBotCircleY-mCircleRadius-mCirclePaint.getStrokeWidth()/5);
        mBotPath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.translate(getWidth()/2,Px2DpUtil.dp2px(getContext(),100));

        canvas.drawColor(Color.WHITE);
        //top大圆
        canvas.drawCircle(mCircleX,mCircleY,mCircleRadius,mCirclePaint);

        canvas.save();
//        canvas.rotate(mCurrAngle * 360,mCircleX,mCircleY);
        canvas.rotate(mCurrAngle ,mCircleX,mCircleY);
        //齿轮
        for(int i=0;i<10;i++) {
            canvas.drawPath(mTopPath,mLadderPaint);
            canvas.rotate(36,mCircleX,mCircleY);
        }
        canvas.restore();
        //top大圆包含的小圆
        canvas.drawCircle(mCircleX,mCircleY,mSmallRadius,mCirclePaint);



        //bot大圆
        canvas.drawCircle(mBotCircleX,mBotCircleY,mCircleRadius,mCirclePaint);

        canvas.save();
//        canvas.rotate(mCurrAngle * 360,mCircleX,mCircleY);
        canvas.rotate(mBotCurrAngle ,mBotCircleX,mBotCircleY);
        canvas.rotate(36,mBotCircleX,mBotCircleY);
        for(int i=0;i<10;i++) {
            canvas.rotate(36,mBotCircleX,mBotCircleY);
            canvas.drawPath(mBotPath,mLadderPaint);
//            canvas.rotate(36,mBotCircleX,mBotCircleY);
        }
        canvas.restore();
        //bot大圆包含的小圆
        canvas.drawCircle(mBotCircleX,mBotCircleY,mSmallRadius,mCirclePaint);


    }

    @Override
    public void onClick(View v) {

        stop();

//        mValueAnimator = ValueAnimator.ofFloat(0f, 1.0f);
//        mValueAnimator = ValueAnimator.ofFloat(0f, 180.0f);//顺时针
        mValueAnimator = ValueAnimator.ofFloat(0f, -180.0f);//逆时针
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrAngle= (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.start();



        mBotValueAnimator = ValueAnimator.ofFloat(0f, -180.0f);//逆时针
        mBotValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mBotValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBotCurrAngle= (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mBotValueAnimator.setDuration(1000);
        mBotValueAnimator.setInterpolator(new LinearInterpolator());
        mBotValueAnimator.start();

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();

    }

    private void stop(){
        if (mValueAnimator != null&&mValueAnimator.isRunning()) {
            mValueAnimator.cancel();
        }
    }

    private double caclute2PointDis(float startX, float startY, float stopX, float stopY) {
        PointF p1 = new PointF(startX, startY);
        PointF p2 = new PointF(stopX, stopY);
        // 两点间距离
        double dis = Math.sqrt(Math.abs((p1.x - p2.x)
                * (p1.x - p2.x) + (p1.y - p2.y)
                * (p1.y - p2.y)));

        return dis;
    }
}
