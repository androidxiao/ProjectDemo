package cn.custom.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import cn.custom.widget.Px2DpUtil;
import cn.custom.widget.model.LadderModel;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/11/29.
 */

public class LadderView extends View {
    public static final String TAG = "ez";
    private ArrayList<LadderModel> mLadderModels;
    private int smallRadius;
    private int smallColorId;
    private int middleRadius;
    private int middleColorId;
    private int lineColor;
    private int lineLength;
    private int bigRadius;
    private int bigMidRadius;
    private Paint mPaint;
    private int[] mNums;

    private int mRoundRadius;
    private String[] mTexts;

    public LadderView(Context context) {
        this(context,null);
    }

    public LadderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LadderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(Px2DpUtil.dp2px(context,4));
        mNums = new int[]{ContextCompat.getColor(context, R.color.c_F9A54A),ContextCompat.getColor(context,R.color.c_F35E66),ContextCompat.getColor(context,R.color.c_48A5D9),ContextCompat.getColor(context,R.color.c_48C67A),ContextCompat.getColor(context,R.color.c_817699),ContextCompat.getColor(context,R.color.c_A78B7A),ContextCompat.getColor(context,R.color.c_D36E8F),ContextCompat.getColor(context,R.color.c_8B8197)};

        mPaint.setColor(mNums[0]);
        smallRadius = Px2DpUtil.dp2px(getContext(), 15);
        middleRadius = Px2DpUtil.dp2px(getContext(), 20);
        bigRadius = Px2DpUtil.dp2px(context, 30);
        bigMidRadius = Px2DpUtil.dp2px(context, 25);
        lineLength=Px2DpUtil.dp2px(context, 20);
        mRoundRadius = Px2DpUtil.dp2px(context, 5);

        mTexts = new String[]{"最强王者","王者","超凡大师","钻石","白金","黄金","白银","青铜"};

        mLadderModels = new ArrayList<>();
        smallColorId= ContextCompat.getColor(context, R.color.white);
        lineColor= ContextCompat.getColor(context, R.color.c_898B8D);
        for (int i=0;i<mNums.length;i++) {
             RectF  mF = new RectF();
            if(i%2==0) {
                mF.left = -Px2DpUtil.dp2px(context, 90);
                mF.top = -Px2DpUtil.dp2px(context, 30);
                mF.bottom = -Px2DpUtil.dp2px(context, 5);
                mF.right = -Px2DpUtil.dp2px(context, 24);
            }else{
                mF.right=Px2DpUtil.dp2px(context,90);
                mF.top= -Px2DpUtil.dp2px(context, 30);
                mF.bottom=-Px2DpUtil.dp2px(context,5);
                mF.left=Px2DpUtil.dp2px(context,24);
            }
            LadderModel mLadderModel = new LadderModel(smallRadius, middleRadius, smallColorId, mNums[i], lineColor, lineLength, mF, bigRadius);
            mLadderModels.add(mLadderModel);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth=100;
        int desiredHeight=800;
        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthSpec == MeasureSpec.EXACTLY) {
            width=widthSize;
        }else if(widthSpec==MeasureSpec.AT_MOST){
            width = Math.min(desiredWidth, widthSize);
        }else{
            width=desiredWidth;
        }

        if (heightSpec == MeasureSpec.EXACTLY) {
            height=heightSize;
        }else if(heightSpec==MeasureSpec.AT_MOST){
            height = Math.min(desiredHeight, heightSize);
        }else {
            height = desiredHeight;
        }
        Log.d(TAG, "onMeasure: --width-->"+width);
        Log.d(TAG, "onMeasure: --height-->"+height);
        Log.d(TAG, "onMeasure: --widthSize-->"+widthSize);
        Log.d(TAG, "onMeasure: --heightSize-->"+heightSize);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth()/2,Px2DpUtil.dp2px(getContext(),21));
        mPaint.setColor(mNums[0]);
        canvas.drawCircle(0,0,mLadderModels.get(0).getMiddleRadius(),mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(0,0,mLadderModels.get(0).getSmallRadius(),mPaint);
        mPaint.setColor(lineColor);
        canvas.drawLine(0,mLadderModels.get(0).getMiddleRadius(),0,mLadderModels.get(0).getMiddleRadius()*2+lineLength,mPaint);

        for (int i=0;i<mLadderModels.size();i++) {
            mPaint.setColor(mNums[i]);
            canvas.drawCircle(0, mLadderModels.get(i).getMiddleRadius() * 3 + lineLength, mLadderModels.get(i).getMiddleRadius(), mPaint);
            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(0, mLadderModels.get(i).getMiddleRadius() * 3 + lineLength, mLadderModels.get(i).getSmallRadius(), mPaint);
            mPaint.setColor(lineColor);
            canvas.drawLine(0,mLadderModels.get(0).getMiddleRadius(),0,mLadderModels.get(0).getMiddleRadius()*2+lineLength,mPaint);
            mPaint.setColor(mNums[i]);
            if(i%2==0){
                canvas.drawLine(-mLadderModels.get(i).getMiddleRadius(), mLadderModels.get(i).getMiddleRadius() * 3 + lineLength, -lineLength * 5, mLadderModels.get(i).getMiddleRadius() * 3 + lineLength, mPaint);
            }else{
                canvas.drawLine(mLadderModels.get(i).getMiddleRadius(), mLadderModels.get(i).getMiddleRadius() * 3 + lineLength, lineLength * 5, mLadderModels.get(i).getMiddleRadius() * 3 + lineLength, mPaint);
            }
            canvas.save();
            canvas.translate(0, (mLadderModels.get(i).getMiddleRadius() * 3 + lineLength));
            canvas.drawRoundRect(mLadderModels.get(i).getRectF(), mRoundRadius, mRoundRadius, mPaint);
            if(i%2==0) {
                mPaint.setColor(Color.WHITE);
                mPaint.setTextSize(Px2DpUtil.sp2px(getContext(),15));
                float textWidth = mPaint.measureText(mTexts[i]);
                if(textWidth>=(-mLadderModels.get(i).getRectF().left)/2){
                    canvas.drawText(mTexts[i],-(-mLadderModels.get(i).getRectF().left-10),mLadderModels.get(i).getRectF().top/3,mPaint);
                }else{
                    canvas.drawText(mTexts[i],-(-mLadderModels.get(i).getRectF().left-mPaint.measureText(mTexts[i])/2),mLadderModels.get(i).getRectF().top/3,mPaint);
                }
                mPaint.setColor(mLadderModels.get(i).getMiddleColorId());
                canvas.drawCircle(-(lineLength * 5 + bigRadius), 0, bigRadius, mPaint);
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(-(lineLength * 5 + bigRadius), 0, bigMidRadius, mPaint);
                canvas.save();
                canvas.translate(-(lineLength * 5 + bigRadius),0);
                mPaint.setColor(mLadderModels.get(i).getMiddleColorId());
                mPaint.setTextSize(Px2DpUtil.sp2px(getContext(),20));
                canvas.drawText(String.valueOf(i+1),-mPaint.measureText(String.valueOf(i+1))/2,(bigRadius-(mPaint.descent()-mPaint.ascent())/2)/2,mPaint);
                canvas.restore();
            }else{
                mPaint.setColor(Color.WHITE);
                mPaint.setTextSize(Px2DpUtil.sp2px(getContext(),15));
                float textWidth = mPaint.measureText(mTexts[i]);
                if(textWidth>=(mLadderModels.get(i).getRectF().right)/2){
                    canvas.drawText(mTexts[i],(mLadderModels.get(i).getRectF().right-10),mLadderModels.get(i).getRectF().top/3,mPaint);
                }else{
                    canvas.drawText(mTexts[i],(mLadderModels.get(i).getRectF().right-mPaint.measureText(mTexts[i])/3)/2,mLadderModels.get(i).getRectF().top/3,mPaint);
                }
                mPaint.setColor(mLadderModels.get(i).getMiddleColorId());
                canvas.drawCircle((lineLength * 5 + bigRadius), 0, bigRadius, mPaint);
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle((lineLength * 5 + bigRadius), 0, bigMidRadius, mPaint);
                canvas.save();
                canvas.translate((lineLength * 5 + bigRadius),0);
                mPaint.setColor(mLadderModels.get(i).getMiddleColorId());
                mPaint.setTextSize(Px2DpUtil.sp2px(getContext(),20));
                canvas.drawText(String.valueOf(i+1),-mPaint.measureText(String.valueOf(i+1))/2,(bigRadius-(mPaint.descent()-mPaint.ascent())/2)/2,mPaint);
                canvas.restore();
            }
            canvas.restore();
            canvas.translate(0,(mLadderModels.get(i).getMiddleRadius() * 3 + lineLength));
        }
        mPaint.setColor(lineColor);
        canvas.drawLine(0,mLadderModels.get(0).getMiddleRadius(),0,mLadderModels.get(0).getMiddleRadius()*2+lineLength,mPaint);
        mPaint.setColor(mNums[mLadderModels.size()-1]);
        canvas.drawCircle(0,mLadderModels.get(0).getMiddleRadius()*3+lineLength,mLadderModels.get(0).getMiddleRadius(),mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(0,mLadderModels.get(0).getMiddleRadius()*3+lineLength,mLadderModels.get(0).getSmallRadius(),mPaint);
    }
}
