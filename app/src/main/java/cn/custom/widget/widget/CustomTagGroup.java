package cn.custom.widget.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;

/**
 * Created by chawei on 2018/1/19.
 */

public class CustomTagGroup extends ViewGroup{

    public static final String TAG = "ez";
    private int mScreenWidth;

    public CustomTagGroup(Context context) {
        this(context,null);
    }

    public CustomTagGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTagGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = ScreenUtil.getScreenWidth();
    }

    private void init(){

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int count=getChildCount();

        int curWidth,curHeight,curLeft,curTop,maxHeight;

        final int childLeft=getPaddingLeft();
        final int childTop=getPaddingTop();
        //child的最右边size
        final int childRight=getMeasuredWidth()-getPaddingRight();
        int childBottom=getMeasuredHeight()-getPaddingBottom();
        //child的width最大size
        int childWidth=childRight-childLeft;
        //child的height最大size
        int childHeight=childBottom-childTop;

//        Log.d(TAG, "childLeft: ---->"+childLeft);
//        Log.d(TAG, "childTop: ---->"+childTop);
//        Log.d(TAG, "childRight: ---->"+childRight);
//        Log.d(TAG, "childBottom: ---->"+childBottom);
//        Log.d(TAG, "childWidth: ---->"+childWidth);
//        Log.d(TAG, "childHeight: ---->"+childHeight);

        maxHeight=0;
        curLeft=childLeft;
        curTop=childTop;

        for (int i=0;i<count;i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                continue;
            }

            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth=child.getMeasuredWidth();
            curHeight=child.getMeasuredHeight();

            if (curLeft + curWidth >=childRight) {
                curLeft=childLeft;
                curTop+=maxHeight;
                maxHeight=0;
            }

            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);
            if (maxHeight < curHeight) {
//                Log.d(TAG, "maxHeight < curHeight: ---->"+i);
                maxHeight=curHeight;
            }

            curLeft+=curWidth;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count=getChildCount();

        int maxHeight=0;
        int maxWidth=0;
        int childState=0;
        int mLeftWidth=0;
        int rowCount=0;

        for (int i=0;i<count;i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                continue;
            }

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            maxWidth += Math.max(maxWidth, child.getMeasuredWidth());
            mLeftWidth+=child.getMeasuredWidth();

            if ((mLeftWidth / mScreenWidth) >rowCount) {
                maxHeight+=child.getMeasuredHeight();
                ++rowCount;
//                Log.d(TAG, "i---->"+i+"   rowCount--->"+rowCount);
            }else{
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
//                Log.d(TAG, "i---->"+i+"   rowCount--->"+rowCount);
            }


//            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        Log.d(TAG, "maxHeight---->"+maxHeight+"   resolveSize--->"+(resolveSizeAndState(maxHeight,heightMeasureSpec,childState)));
        //设置最终的size
        setMeasuredDimension(resolveSizeAndState(maxWidth,widthMeasureSpec,0),
                resolveSizeAndState(maxHeight,heightMeasureSpec,0));
    }
}
