package cn.custom.widget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import cn.custom.widget.Px2DpUtil;

/**
 * Created by chawei on 2017/10/13.
 */

public class StateView extends View {

    private Paint mTextPaint;
    private Path mPath;

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setStrokeWidth(Px2DpUtil.dp2px(context, 10));
//        mTextPaint.setColor(ContextCompat.getColor(context, R.color.white));
        mTextPaint.setColor(ContextCompat.getColor(context, android.R.color.holo_red_light));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
