package cn.custom.widget.widget.textviewanim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/11/13.
 */

public class JiKeLikeView extends View implements View.OnClickListener {

    public static final String TAG = "ez";
    private Paint mPaint;
    private Bitmap mBitmap;
    private Bitmap mBitmapSelected;
    private Bitmap mLike;
    private float mV;
    private String[] nums;//nums[0]:不变的部分，nums[1]：原来的部分，nums[2]：更新的部分

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
//        matrix.postScale(1f, 0.9f);
//        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(),
//                matrix, true);
        mBitmapSelected = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected);
        mLike = BitmapFactory.decodeResource(getResources(), R.drawable.ic_messages_like_selected_shining);

        mV = -(mPaint.descent() + mPaint.ascent());
        setOnClickListener(this);

        nums = new String[]{String.valueOf(count), "", ""};
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {

        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int fontMetrics = (fontMetricsInt.bottom + fontMetricsInt.top) / 2;


        canvas.drawText(nums[0], 0, 0, mPaint);
        canvas.drawText(nums[1], mPaint.measureText(nums[0])+5, -fontMetrics, mPaint);
        canvas.drawText(nums[2], mPaint.measureText(nums[0])+5, fontMetrics, mPaint);
    }

    private boolean mIsLike = true;

    @Override
    public void onClick(View v) {
        if (mIsLike) {
            mIsLike=false;
            calcuteNum(1);
            count++;
        }else{
            mIsLike=true;
            calcuteNum(-1);
            count--;
        }
    }



    private int count = 99;

    private void calcuteNum(int num) {//num：取值-1或者1
        int oldLength = String.valueOf(count).length();
        int newLength = String.valueOf(count + num).length();
        if (oldLength != newLength) {
            nums[0] = "";
            nums[1] = String.valueOf(count);
            nums[2] = String.valueOf(count + num);
        } else {
            String oldCount = String.valueOf(count);
            String newCount = String.valueOf(count + num);
            for (int i = 0; i < oldLength; i++) {
                char oldChar = oldCount.charAt(i);
                char newChar = newCount.charAt(i);
                if (oldChar != newChar) {
                    if (i == 0) {
                        nums[0] = "";
                    } else {
                        nums[0] = oldCount.substring(0, i);
                    }
                    nums[1] = oldCount.substring(i);
                    nums[2] = newCount.substring(i);
                    break;
                }
            }
        }

        postInvalidate();

    }
}
