package cn.custom.widget.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/19.
 */

public class CustomTagGroup3 extends ViewGroup {

    public static final String TAG = "ez";
    //默认item的spacing
    private static final int DEFAULT_CHILD_SPACING = 0;
    //默认item的topMargin值
    private static final int DEFAULT_ROW_SPACING = 0;
    private int mChildSpacing = DEFAULT_CHILD_SPACING;
    private int mRowSpacing = DEFAULT_ROW_SPACING;
    //用来存储每行item所占用的宽度的总和
    private List<Integer> itemLineWidth = new ArrayList<>();
    //用来存储每行item的个数
    private List<Integer> itemLineNum = new ArrayList<>();
    //用来记录item所占用的总行数
    private int mRowTotalCount = 0;

    public CustomTagGroup3(Context context) {
        this(context, null);
    }

    public CustomTagGroup3(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTagGroup3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CenterFlowLayout, 0, 0);
        mChildSpacing = typedArray.getDimensionPixelSize(R.styleable.CenterFlowLayout_childSpacing, DEFAULT_CHILD_SPACING);
        mRowSpacing = typedArray.getDimensionPixelSize(R.styleable.CenterFlowLayout_rowSpacing, DEFAULT_ROW_SPACING);
//        Log.d(TAG, "mChildSpacing: ----->"+mChildSpacing);
//        Log.d(TAG, "mRowSpacing: ----->"+mRowSpacing);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        //子View的最小left值
        int childLeft = getPaddingLeft();
        //子View的最小top值
        int childTop = getPaddingTop();
        //子View的最大right值
        int childRight = getMeasuredWidth() - getPaddingRight();
        //子View的最大bottom值--->如果没有重写onMeasure方法，那这里的bottom值就是屏幕的高度-状态栏的高度；如果重写了，那就是测量出来的高度
        int childBottom = getMeasuredHeight() - getPaddingBottom();
        //子View可用排列的最大宽度
        int childWidth = childRight - childLeft;
        //子View可用排列的最大高度
        int childHeight = childBottom - childTop;
//        Log.d(TAG, "onLayout: ---->"+itemLineWidth.size());
        //每次重新排版时left的值。其实就是换行时左边第一个view的left初始值（left、top、right、bottom）
        int curLeft;
        //每次重新排版时top的值。其实就是换行时左边第一个view的top初始值
        int curTop = childTop;
        //子View换行排列时需要的height，这个值是测量单行排列中最大的子View的高度
        int maxHeight = 0;
        //当前子View的具体Height
        int exactHeight;
        //当前子View的具体Width
        int exactWidth = 0;
        //获取ViewGroup中子View的count值
//        Log.d(TAG, "getPaddingBottom: "+getPaddingBottom());
//        Log.d(TAG, "getMeasuredHeight(): "+getMeasuredHeight());
//        Log.d(TAG, "childLeft: ---->"+childLeft);
//        Log.d(TAG, "childTop: ---->"+childTop);
//        Log.d(TAG, "childRight: ---->"+childRight);
//        Log.d(TAG, "childBottom: ---->"+childBottom);
//        Log.d(TAG, "childWidth: ---->"+childWidth);
//        Log.d(TAG, "childHeight: ---->"+childHeight);

        int childIndex = 0;
        for (int j = 0; j < mRowTotalCount; j++) {

            Integer childNum = itemLineNum.get(j);

            curLeft = childLeft + (childWidth - itemLineWidth.get(j)) / 2;
            Log.d(TAG, "childLeft: "+childLeft+" childWidth "+childWidth+" itemLineWidth.get(j) "+itemLineWidth.get(j)+" curLeft "+curLeft);
//            curLeft =  childLeft;
            int verticalMargin = 0;
            for (int i = 0; i < childNum; i++) {

                View child = getChildAt(childIndex++);

                //子View为GONE时，不进行排列，继续执行下一个子View的排列
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                //获取子View的具体占用width值
                exactWidth = child.getMeasuredWidth();
                //获取子View的具体占用height值
                exactHeight = child.getMeasuredHeight();
                MarginLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
                int  marginRight = 0,marginTop = 0,marginBottom;
                if (params instanceof MarginLayoutParams) {
                    marginRight = params.rightMargin;
                    marginTop = params.topMargin;
                    marginBottom=params.bottomMargin;
                    if (childNum > 1 && i == 0) {
                        verticalMargin = marginTop+marginBottom;
                    }
                }

//                Log.d(TAG, "onLayout: --->" + exactHeight );

                //对当前子View进行排列
//                child.layout(curLeft + marginLeft, curTop + marginTop, curLeft + exactWidth + marginLeft, curTop + exactHeight + marginTop);
                child.layout(curLeft, curTop, curLeft + exactWidth, curTop + exactHeight);
                //计算出单行中子View的最大height值
                if (maxHeight < exactHeight) {
                    //获取最大的子View的高度
                    maxHeight = exactHeight;
                }

                curLeft += exactWidth + mChildSpacing + marginRight ;
            }
            //换行时，需要加上上一行中子View高度的最大值，这样才会另起一行进行重新排列
            curTop += maxHeight + mRowSpacing+verticalMargin;
            maxHeight = 0;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        itemLineNum.clear();
        itemLineWidth.clear();
        mRowTotalCount = 0;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int rowLength = widthSize - getPaddingLeft() - getPaddingRight();
        int exactWidth = 0;
//        int exactWidth = 0;
        int exactHeight = 0;
        int maxWidth = 0;
        int rowCount = 0;
        int childCount = getChildCount();
        int rowWidth = 0;
        int childWidth;
        int childHeight;
        int childNumInRow = 0;
        int measureWidth;
        //记录最后一行，第一个item的index（主要是用来循环计算最后一行item的宽度）
        int tempIndex = 0;
        //记录除了最后一行的item个数，用来计算出最后一行中item的个数
        int exceptLastRowNum = 0;
        int measuredHeight = 0;
        for (int i = 0; i < childCount; i++) {

            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                continue;
            }

            CustomLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
//            int horizalMargin = 0;
//            int verticalMargin = 0;
            int marginRight=0,marginTop=0,marginBottom=0;
            if (params instanceof MarginLayoutParams) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, measuredHeight);
                marginRight=params.rightMargin;
                marginTop = params.topMargin;
                marginBottom=params.bottomMargin;
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
            childWidth = child.getMeasuredWidth() + mChildSpacing + marginRight;
            childHeight = child.getMeasuredHeight() + mRowSpacing + marginBottom+marginTop;
            rowWidth += childWidth;
            maxWidth += Math.max(maxWidth, childWidth);

//            Log.d(TAG, "onMeasure: --->"+childHeight+"  --->"+child.getMeasuredHeight());

            if (exactWidth + childWidth > rowLength) {
                tempIndex = i;
//                Log.d(TAG, "aaaaaaa: ------->"+i);
                rowWidth = rowWidth - childWidth - mChildSpacing - marginRight;
                itemLineWidth.add(rowWidth);
                rowWidth = childWidth;
                ++rowCount;
//                Log.d(TAG, "rowCount-1111111--->"+rowCount);
                exactWidth = childWidth;
                exactHeight += childHeight;
                measuredHeight = exactHeight;
                itemLineNum.add(childNumInRow);
                exceptLastRowNum += childNumInRow;
                childNumInRow = 1;
            } else {
//                Log.d(TAG, "rowCount-2222222--->"+rowCount);
//                Log.d(TAG, "bbbbbbb: ------->"+i);
                exactWidth += childWidth;
                ++childNumInRow;
                exactHeight = Math.max(exactHeight, childHeight);
                measuredHeight = exactHeight;
            }

        }

        int lastRowWidth = 0;
        int singleHorizalMargin = 0;
        for (int i = tempIndex; i < childCount; i++) {
            View child = getChildAt(i);
            int horizalMargin = 0;
            CustomLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
            if (params instanceof MarginLayoutParams) {
//                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, measuredHeight);
                singleHorizalMargin = horizalMargin = params.rightMargin;
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
            lastRowWidth += child.getMeasuredWidth() + mChildSpacing + horizalMargin;
        }
        int lastChildCount = childCount - exceptLastRowNum;
        lastRowWidth -= mChildSpacing == 0 ? singleHorizalMargin : mChildSpacing;
        itemLineWidth.add(lastRowWidth);
        itemLineNum.add(lastChildCount);
        mRowTotalCount = rowCount + 1;
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth()) + getPaddingRight() + getPaddingLeft();
        exactHeight = Math.max(exactHeight, getSuggestedMinimumHeight()) + getPaddingTop() + getPaddingBottom();

        if (widthMode == MeasureSpec.EXACTLY) {
            measureWidth = widthSize;
        } else {
            measureWidth = maxWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            measuredHeight = heightSize;
        } else {
            measuredHeight = exactHeight;
        }
        setMeasuredDimension(resolveSize(measureWidth, widthMeasureSpec),
                resolveSize(measuredHeight, heightMeasureSpec));
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CustomLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CustomLayoutParams(getContext(), attrs);
    }

    /**
     * 只有在ViewGroup中直接添加子View才能获取到子View设置的margin值。这里才能为true；
     * 如果是外部addView()到ViewGroup中的话，返回的是false，是获取不到子View的margin值的，
     * 需要自己去自定义margin或者spacing值去单独处理。
     *
     * @param p
     * @return
     */
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CustomLayoutParams;
    }

    public static class CustomLayoutParams extends MarginLayoutParams {

        public CustomLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(LayoutParams source) {
            super(source);
        }

        public CustomLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomLayoutParams(int width, int height) {
            super(width, height);
        }
    }
}