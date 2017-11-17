package cn.custom.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/11/16.
 */

public class QuoteView extends View{
    public static final String TAG = "ez";
    private Paint mPaint;
    private int mBigRadius;
    private int mMidRadius;
    private int mSmallRadius;
    private float mBigRotateAngle=5.5f;
    private float mMidRotateAngle=4f;
    private float mSmallRotateAngle=3f;
    private double mValidMoney=5000.00;
    public QuoteView(Context context) {
        this(context,null);
    }

    public QuoteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QuoteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(Px2DpUtil.dp2px(context,2));
        mPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));
        mBigRadius = Px2DpUtil.dp2px(context, 100);
        mMidRadius = Px2DpUtil.dp2px(context, 80);
        mSmallRadius = Px2DpUtil.dp2px(context, 60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(getContext().getResources().getColor(android.R.color.holo_purple));

        canvas.translate(ScreenUtil.getScreenWidth()/2,500);

        canvas.save();
        //外->内
        //第一层
        for(int i=0;i<65;i++) {
//            canvas.drawPoint(mBigRadius,0,mPaint);
            canvas.drawCircle(mBigRadius,0,2,mPaint);
//            canvas.drawText(mBigRotateAngle*(i+1)+"",mBigRadius,0,mPaint);
            canvas.rotate(mBigRotateAngle,0,0);
        }
        canvas.restore();

        //第二层
        canvas.save();
        for (int i=0;i<90;i++) {
            canvas.rotate(mMidRotateAngle);
            canvas.drawLine(mMidRadius,0,mMidRadius+mMidRadius/10,0,mPaint);
        }
        canvas.restore();

        //第三层
        canvas.save();
        for(int i=0;i<120;i++) {
            canvas.drawCircle(mSmallRadius,0,2,mPaint);
            canvas.rotate(mSmallRotateAngle,0,0);
        }
        canvas.restore();
        mPaint.setTextSize(40);
        mPaint.setColor(getContext().getResources().getColor(R.color.white));
        canvas.drawText(mValidMoney+"",-mPaint.measureText(String.valueOf(mValidMoney))/2,0,mPaint);
        mPaint.setTextSize(20);
        canvas.drawText("可用额度",-mPaint.measureText("可用额度")/2,Px2DpUtil.dp2px(getContext(),20),mPaint);
    }

    public void setValidMoney(int validMoney){
         mValidMoney=validMoney;
    }
}
