package cn.custom.widget.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.custom.widget.Px2DpUtil;

/**
 * Created by chawei on 2017/11/28.
 */

public class UpDownTextView extends View implements View.OnClickListener{

    public static final String TAG = "ez";
    private String[] nums;//num[0]是不变的部分，nums[1]原来的部分，nums[2]变化后的部分
    private int count=10;
    private boolean toBigger;
    private float mOldOffsetY;
    private float mNewOffsetY;
    //文本的上下移动变化值
    private float OFFSET_MIN;
    private float OFFSET_MAX;
    private long lastClickTime;
    private boolean isThumbUp;
    private Paint mPaint;

    public UpDownTextView(Context context) {
        this(context, null);
    }

    public UpDownTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UpDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(40);
        mPaint.setColor(Color.RED);

        nums = new String[]{String.valueOf(count), "", ""};
        OFFSET_MIN = 0;
        OFFSET_MAX = Px2DpUtil.sp2px(context,mPaint.getTextSize());
        Log.d(TAG, "init: textSize------>"+OFFSET_MAX);
        setOnClickListener(this);
    }


    public void setTextOffsetY(float offsetY) {
        this.mOldOffsetY = offsetY;//变大是从[0,1]，变小是[0,-1]
        if (toBigger) {//从下到上[-1,0]
            this.mNewOffsetY = offsetY - OFFSET_MAX;
            Log.d(TAG, "值增加: ----->"+mNewOffsetY);
        } else {//从上到下[1,0]
            this.mNewOffsetY = OFFSET_MAX + offsetY;
            Log.d(TAG, "值减少: ----->"+mNewOffsetY);
        }
        postInvalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(Px2DpUtil.dp2px(getContext(),50),Px2DpUtil.dp2px(getContext(),50));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        Log.d(TAG, "onSizeChanged: 尺寸变化----->");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.addCircle(0,Px2DpUtil.dp2px(getContext(),20),Px2DpUtil.dp2px(getContext(),18), Path.Direction.CW);
//        canvas.clipPath(path);
        canvas.drawColor(Color.WHITE);

        Paint.FontMetrics metrics = mPaint.getFontMetrics();
        float y= (Px2DpUtil.dp2px(getContext(),36)-metrics.bottom-metrics.top)/2;
        canvas.drawText(String.valueOf(nums[0]), 0, y, mPaint);
        String text = String.valueOf(count);
        float textWidth = mPaint.measureText(text) / text.length();
        canvas.drawText(String.valueOf(nums[1]),  textWidth * nums[0].length(),  y- mOldOffsetY, mPaint);
        canvas.drawText(String.valueOf(nums[2]),  textWidth * nums[0].length(), y- mNewOffsetY, mPaint);

    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastClickTime <  150) {
            return;
        }
        lastClickTime = System.currentTimeMillis();
        if (isThumbUp) {
            calculateChangeNum(-1);
            count--;
            textDownAnimation();
        } else {
            calculateChangeNum(1);
            count++;
            textUpAnimation();
        }
    }


    private void textUpAnimation(){
        ObjectAnimator textOffsetY = ObjectAnimator.ofFloat(this, "textOffsetY", OFFSET_MIN, OFFSET_MAX);
        textOffsetY.setDuration(150);
        textOffsetY.start();
        isThumbUp=true;
    }

    private void textDownAnimation(){
        ObjectAnimator textOffsetY = ObjectAnimator.ofFloat(this, "textOffsetY", OFFSET_MIN, -OFFSET_MAX);
        textOffsetY.setDuration(150);
        textOffsetY.start();
        isThumbUp=false;
    }

    private void calculateChangeNum(int change) {
        if (change == 0) {
            nums[0] = String.valueOf(count);
            nums[1] = "";
            nums[2] = "";
            return;
        }
        toBigger = change > 0;
        String oldNum = String.valueOf(count);
        String newNum = String.valueOf(count + change);

        int oldNumLen = oldNum.length();
        int newNumLen = newNum.length();

        if (oldNumLen != newNumLen) {
            nums[0] = "";
            nums[1] = oldNum;
            nums[2] = newNum;
        } else {
            for (int i = 0; i < oldNumLen; i++) {
                char oldC1 = oldNum.charAt(i);
                char newC1 = newNum.charAt(i);
                if (oldC1 != newC1) {
                    if (i == 0) {
                        nums[0] = "";
                    } else {
                        nums[0] = newNum.substring(0, i);
                    }
                    nums[1] = oldNum.substring(i);
                    nums[2] = newNum.substring(i);
                    break;
                }
            }
        }
    }

}
