package cn.custom.widget.widget.reveal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import cn.custom.widget.Px2DpUtil;

/**
 * Created by chawei on 2017/12/13.
 */

public class RevealCircleView extends View {

    public static final String TAG = "gear2";

    // 画笔
    private Paint paint;
    private int mScreenWidth;
    private Path mPath1;
    private Path mPath2;
    private Path mPath3;
    private Paint mPaint2;



    public RevealCircleView(Context context) {
        this(context,null);
    }

    public RevealCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RevealCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        mScreenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = wm.getDefaultDisplay().getHeight();
        init();

    }

    /**
     * 初始化变量
     */
    private void init() {
        // 初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setDither(true);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setColor(Color.BLACK);


        mPath1 = new Path();
        mPath2 = new Path();

        mPath3 = new Path();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight() / 2;
        Log.d(TAG, "onDraw: --->"+height);
        Log.d(TAG, "onDraw: --->"+Px2DpUtil.dp2px(getContext(),100));
        mPath1.addCircle(mScreenWidth/2,height,Px2DpUtil.dp2px(getContext(),100), Path.Direction.CW);
        mPath2.addCircle(mScreenWidth / 2, 10,Px2DpUtil.dp2px(getContext(),100), Path.Direction.CW);
        canvas.drawPath(mPath1,paint);
        canvas.drawPath(mPath2,mPaint2);
        mPath3.op(mPath1, mPath2, Path.Op.REVERSE_DIFFERENCE);
//        canvas.drawCircle(mScreenWidth/2,height,Px2DpUtil.dp2px(getContext(),100),paint);
    }

}
