package cn.custom.widget.widget.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chawei on 2017/12/15.
 */

public class RadarView extends View {

    public static final String TAG = "gear2";

    //数据个数
    private int count = 6;
    private float angle = (float) ((Math.PI) / count);
    //网格最大半径
    private float radius;
    //中心X
    private int centerX;
    //中心Y
    private int centerY;
    private String[] titles = {"语文", "数学", "英语", "化学", "物理", "生物"};
    //各维度分值
    private double[] data = {100, 60, 60, 60, 100, 50, 10, 20};
    //数据最大值
    private float maxValue = 100;
    //雷达区画笔
    private Paint mainPaint;
    //数据区画笔
    private Paint valuePaint;
    //文本画笔
    private Paint textPaint;

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mainPaint = new Paint();
        mainPaint.setAntiAlias(true);
        mainPaint.setColor(Color.GRAY);
        mainPaint.setStrokeWidth(2);
        mainPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(Color.BLUE);
        valuePaint.setStrokeWidth(2);
        valuePaint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint();
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20);
        textPaint.setAntiAlias(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        radius = centerY * 0.9f;

        drawPolygon(canvas);
    }

    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        float r = radius / (count - 1);//蜘蛛丝之间的间距
        for (int i = 1; i < count; i++) {//中心点不用绘制
            float curR = r * i;//当前半径
            path.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {
//                    Log.d(TAG, "drawPolygon: (centerX+curR)---->" + (centerX + curR) + "    centerY--->" + centerY);
                    path.moveTo(centerX + curR, centerY);
                } else {
                    float x=0,y=0;
                    if(j==1) {
                         x = centerX + curR / 2;
                         y = (float) (centerY - curR * Math.cos(30));
                    }else if(j==5){
                        x = centerX + curR / 2;
                        y = (float) (centerY + curR * Math.cos(30));
                    }else if(j==2){
                        x = centerX -curR / 2;
                        y = (float) (centerY - curR * Math.cos(30));
                    }else if(j==4){
                        x = centerX -curR / 2;
                        y = (float) (centerY + curR * Math.cos(30));
                    }else if(j==3){
                        x = centerX-curR;
                        y = centerY;
                    }
//                    Log.d(TAG, "drawPolygon: x--->" + x + "    y---->" + y);
                    path.lineTo(x, y);
                }
            }
//            Log.d(TAG, "drawPolygon: ----------------------分割线-------------------------");
            path.close();
            canvas.drawPath(path, mainPaint);
        }
    }
}
