package cn.custom.widget.widget.textviewanim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/5.
 */

public class LetterView extends View {

    public static final String TAG = "gear2";

    private String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o"
            , "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private Paint mLetterPaint;
    private int mValidWidth;
    private int mValidHeight;
    private Paint mPressPaint;

    public LetterView(Context context) {
        this(context, null);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public LetterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mLetterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLetterPaint.setStyle(Paint.Style.FILL);
        mLetterPaint.setColor(ContextCompat.getColor(context, R.color.c_3ec88e));


        mPressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPressPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        Log.e(TAG, "---minimumWidth = " + minimumWidth + "");
        Log.e(TAG, "---minimumHeight = " + minimumHeight + "");
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        mValidWidth = width;
        mLetterPaint.setTextSize(mValidHeight / letters.length);
        mPressPaint.setTextSize(mValidHeight / letters.length);
        setMeasuredDimension(width, height);
    }


    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e(TAG, "---speSize = " + specSize + "");


        switch (specMode) {
            case MeasureSpec.AT_MOST://wrap_content
//                defaultWidth = (int) mPaint.measureText(mText) + getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.EXACTLY://match_parent或具体值
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED://父容器不限制，想多大就多大
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }


    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e(TAG, "---speSize = " + specSize + "");

        switch (specMode) {
            case MeasureSpec.AT_MOST:
//                defaultHeight = (int) (-mPaint.ascent() + mPaint.descent()) + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                mValidHeight = specSize - getPaddingTop() - getPaddingBottom();
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
//        1.基准点是baseline
//        2.ascent：是baseline之上至字符最高处的距离
//        3.descent：是baseline之下至字符最低处的距离
//        4.leading：是上一行字符的descent到下一行的ascent之间的距离,也就是相邻行间的空白距离
//        5.top：是指的是最高字符到baseline的值,即ascent的最大值
//        6.bottom：是指最低字符到baseline的值,即descent的最大值

                break;
        }
        return defaultHeight;


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(ContextCompat.getColor(getContext(), R.color.c_ffa81f));

        canvas.translate(mValidWidth / 2, getPaddingTop());

        for (int i = 0; i < letters.length; i++) {
            if(mSelectIndex==i){
                canvas.drawText(letters[i], -mLetterPaint.measureText(letters[i]) / 2, mValidHeight / letters.length * (i + 1), mPressPaint);
            }else{
                canvas.drawText(letters[i], -mLetterPaint.measureText(letters[i]) / 2, mValidHeight / letters.length * (i + 1), mLetterPaint);
            }
        }

    }

    private int mSelectIndex=-1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                float v = (float) Math.floor(y / (mValidHeight / letters.length));
                mSelectIndex=(int) v-1;

                if(mSelectIndex>=0&&mSelectIndex<=25) {
                    if (mSelectLetterListener != null) {
                        mSelectLetterListener.onSelectLetter(letters[mSelectIndex]);
                    }
                }
                invalidate();

                break;
            case MotionEvent.ACTION_UP:

                if (mSelectLetterListener != null) {
                    mSelectLetterListener.onTouchActionUp();
                }

                mSelectIndex=-1;
                invalidate();

                break;
        }
        return true;
    }

    private ISelectLetterListener mSelectLetterListener;

    public void setSelectLetterListener(ISelectLetterListener listener){
        mSelectLetterListener=listener;
    }

    public interface ISelectLetterListener{
        void onSelectLetter(String selectLetter);
        void onTouchActionUp();
    }
}
