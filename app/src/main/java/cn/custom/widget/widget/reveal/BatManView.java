package cn.custom.widget.widget.reveal;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnticipateInterpolator;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

import static android.view.ViewAnimationUtils.createCircularReveal;

/**
 * Created by chawei on 2017/12/13.
 */

public class BatManView extends View implements View.OnClickListener {
    public static final String TAG = "gear2";

    // 画笔
    private Paint paint;
    private Bitmap mBitmap;
    private int mHeight;
    private int mScreenWidth;
    private int mRadius;
    private Animator mAnimator;

    public BatManView(Context context) {
        this(context, null);
    }

    public BatManView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatManView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = wm.getDefaultDisplay().getWidth();

        setOnClickListener(this);
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
        paint.setColor(ContextCompat.getColor(getContext(), R.color.c_bat_man));

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_bat_man);
        mHeight = Px2DpUtil.dp2px(getContext(), 200) / 2;


        mRadius = Px2DpUtil.dp2px(getContext(), 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mScreenWidth / 2, mHeight, mRadius, paint);
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        int leftMargin = (mScreenWidth / 2 - width / 2);
        int topMargin = (mRadius * 2 - height) / 2;
        canvas.drawBitmap(mBitmap,leftMargin,topMargin,paint);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startReveal() {
        //top
//        Animator animator = createCircularReveal(this, mScreenWidth / 2, 0, 0, 180);
        //right
//        Animator animator = createCircularReveal(this, mScreenWidth/2+mRadius, mHeight,0, 180);
        //bottom
//        Animator animator = createCircularReveal(this, mScreenWidth/2, mRadius*2,0, 180);
        //left
        mAnimator.setInterpolator(new AnticipateInterpolator());
        mAnimator.setDuration(1000);
        mAnimator.start();
    }

    public Animator getReveal(){
        mAnimator = createCircularReveal(this, mScreenWidth/2-mRadius, mRadius,0, 180);
        return mAnimator;
    }

    @Override
    public void onClick(View v) {
    }
}
