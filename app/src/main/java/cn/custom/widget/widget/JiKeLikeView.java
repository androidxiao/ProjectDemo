package cn.custom.widget.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/11/13.
 */

public class JiKeLikeView extends View implements View.OnClickListener {

    public static final String TAG = "ez";
    private Paint mPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Bitmap mBitmapSelected;
    private Bitmap mLike;
    private float mV;
    private static final int mIsFirst = 0x00;
    private int mTemp = mIsFirst;
    private static final int mAddValue = 0x01;
    private static final int mIsBackValue = 0x02;
    private ValueAnimator mValueAnimator;
    private int mInitValue=123;
    private int mUpdateValue;
    public JiKeLikeView(Context context) {
        this(context, null);
    }

    public JiKeLikeView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public JiKeLikeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(ContextCompat.getColor(context, R.color.white));
        mPaint.setTextSize(Px2DpUtil.sp2px(context, 20));
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_unselected);
        Matrix matrix = new Matrix();
        matrix.postScale(1f, 0.9f);
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(),
                matrix, true);
        mBitmapSelected = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mLike = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        mV = -(mPaint.descent() + mPaint.ascent());
        mValueAnimator = new ValueAnimator();

        setOnClickListener(this);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;

        switch (mTemp) {
            case mIsFirst:
                drawFirstValue();
                Log.d(TAG, "onDraw: 111");
                break;
            case mAddValue:
                drawSelected();
                mTemp=mIsBackValue;
                Log.d(TAG, "onDraw: 222");
                break;
            case mIsBackValue:
                drawFirstValue();
                mTemp=mAddValue;
                Log.d(TAG, "onDraw: 3333");
                break;
        }

    }

    private void drawFirstValue() {
        mCanvas.drawBitmap(mBitmap, 0, 30, null);
        drawEmptyText();
        mCanvas.drawText(mInitValue+"", mBitmap.getWidth() + 10, mBitmap.getHeight() / 2 + mV / 2+30, mPaint);
    }
    private int scale;
    public void setScale(int scale){
        this.scale=scale;
    }

    public void getScale(){

    }

    private void drawSelected() {
        Matrix matrix = new Matrix();
        matrix.postScale(1f, 1f);
        mBitmapSelected = Bitmap.createBitmap(mBitmapSelected, 0, 0, mBitmapSelected.getWidth(), mBitmapSelected.getHeight(),
                matrix, true);
        mCanvas.drawBitmap(mBitmapSelected, 0, 30, null);
        mCanvas.drawBitmap(mLike, 0, 10, null);
        drawEmptyText();
        mCanvas.drawText((mInitValue+1) + "", mBitmap.getWidth() + 10, mBitmap.getHeight() / 2 + mV / 2+30, mPaint);
    }



    private void drawEmptyText(){
        mCanvas.drawText("", mBitmap.getWidth() + 10, mBitmap.getHeight() / 2 + mV / 2+30, mPaint);
    }

    @Override
    public void onClick(View v) {
         if(mTemp==mIsFirst) {
            mTemp = mAddValue;
        }
        invalidate();

    }
}
