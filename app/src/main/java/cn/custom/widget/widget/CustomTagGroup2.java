package cn.custom.widget.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/19.
 */

public class CustomTagGroup2 extends ViewGroup {

    public static final String TAG = "ez";
    private int mScreenWidth;

    private int mMarginLeft;
    private int mMarginTop;

    private static final int DEFAULT_CHILD_SPACING = 10;
    private static final int DEFAULT_ROW_SPACING = 10;
    private int mChildSpacing = DEFAULT_CHILD_SPACING;
    private int mRowSpacing = DEFAULT_ROW_SPACING;
    private List<Integer> itemLineWidth = new ArrayList<>();
    private List<Integer> itemLineNum = new ArrayList<>();
    private ArrayMap<Integer, Integer> mRowIndexs = new ArrayMap<>();
    private int lineNum = 0;
    private int mRowCount = 0;

    public CustomTagGroup2(Context context) {
        this(context, null);
    }

    public CustomTagGroup2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTagGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = ScreenUtil.getScreenWidth();
        mMarginLeft = Px2DpUtil.dp2px(context, 10);
        mMarginTop = Px2DpUtil.dp2px(context, 10);


        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CenterFlowLayout, 0, 0);
        mChildSpacing = typedArray.getDimensionPixelSize(R.styleable.CenterFlowLayout_childSpacing, DEFAULT_CHILD_SPACING);
        mRowSpacing = mChildSpacing;
//        Log.d(TAG, "mChildSpacing: ----->"+mChildSpacing);
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
        int curLeft = childLeft
                + (childWidth - itemLineWidth.get(0)) / 2;
//        Log.d(TAG, "onLayout: ---->"+childLeft+"  ---->"+childWidth+"   --->"+itemLineWidth.get(0)+"  ---->"+((childWidth - itemLineWidth.get(0)) / 2));
        //每次重新排版时top的值。其实就是换行时左边第一个view的top初始值
        int curTop = childTop;
        //子View换行排列时需要的height，这个值是测量单行排列中最大的子View的高度
        int maxHeight = 0;
        //当前子View的具体Height
        int exactHeight;
        //当前子View的具体Width
        int exactWidth = 0;
        //获取ViewGroup中子View的count值
        int count = getChildCount();
//        Log.d(TAG, "getPaddingBottom: "+getPaddingBottom());
//        Log.d(TAG, "getMeasuredHeight(): "+getMeasuredHeight());
//        Log.d(TAG, "childLeft: ---->"+childLeft);
//        Log.d(TAG, "childTop: ---->"+childTop);
//        Log.d(TAG, "childRight: ---->"+childRight);
//        Log.d(TAG, "childBottom: ---->"+childBottom);
//        Log.d(TAG, "childWidth: ---->"+childWidth);
//        Log.d(TAG, "childHeight: ---->"+childHeight);
        int childNumInRow = 0;
//        for (int i = 0; i < count; i++) {
//            //获取每个子View
//            View child = getChildAt(i);
//
//            //子View为GONE时，不进行排列，继续执行下一个子View的排列
//            if (child.getVisibility() == View.GONE) {
//                continue;
//            }
//
//            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
//            //获取子View的具体占用width值
//            exactWidth = child.getMeasuredWidth();
//            //获取子View的具体占用height值
//            exactHeight = child.getMeasuredHeight();
//
//            //已经排满一行了，此时需要换行
////            Log.e(TAG, "onLayout: curLeft + exactWidth--->"+(curLeft + exactWidth)+"   childRight--->"+childRight);
////            Log.d(TAG, "curLeft--->"+(childLeft+exactWidth+mChildSpacing)+"--->"+childRight);
//            if (curLeft + exactWidth + mChildSpacing >childRight) {
//                //重新初始化，下一行第一个子View的left值
//                ++lineNum;
////                Log.d(TAG, "lineNum: ------>"+lineNum);
////                Log.d(TAG, "iiii--->" + i);
////                curLeft = childLeft+(childWidth-itemLineWidth.get(lineNum))/2;
//                curLeft = childLeft;
//                //换行时，需要加上上一行中子View高度的最大值，这样才会另起一行进行重新排列
//                curTop += maxHeight + mRowSpacing;
//                //将maxHegith重新置为0
//                maxHeight = 0;
//                childNumInRow = 1;
//
//            } else {
//                ++childNumInRow;
//            }
//            MarginLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
////            Log.d(TAG, "onLayout: 11111----->" + params.leftMargin + "  --->" + params.rightMargin);
//            //对当前子View进行排列
//            child.layout(curLeft, curTop, curLeft + exactWidth, curTop + exactHeight);
//
//            //计算出单行中子View的最大height值
//            if (maxHeight < exactHeight) {
//                //获取最大的子View的高度
//                maxHeight = exactHeight;
//            }
//            //叠加子View占用的宽度，以用来下次进行判断是否需要换行
//            curLeft += (exactWidth + mChildSpacing);
//
//        }
        int childIndex=0;
        for (int j = 0; j < mRowCount; j++) {

            Integer childNum = itemLineNum.get(j);
             curLeft = childLeft
                    + (childWidth - itemLineWidth.get(j)) / 2;
//            Log.d(TAG, "childLeft--->"+childLeft+"-curLeft-->"+curLeft+"-itemLineWidth-->"+itemLineWidth.get(j)+"");
            for(int i=0;i<childNum&&childIndex<getChildCount();i++) {

                //获取每个子View
                View child = getChildAt(childIndex++);

                //子View为GONE时，不进行排列，继续执行下一个子View的排列
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
                //获取子View的具体占用width值
                exactWidth = child.getMeasuredWidth();
                //获取子View的具体占用height值
                exactHeight = child.getMeasuredHeight();

                    ++lineNum;
//                curLeft = childLeft+(childWidth-itemLineWidth.get(lineNum))/2;
                    //换行时，需要加上上一行中子View高度的最大值，这样才会另起一行进行重新排列

                    //将maxHegith重新置为0
                    childNumInRow = 1;

                MarginLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
//            Log.d(TAG, "onLayout: 11111----->" + params.leftMargin + "  --->" + params.rightMargin);
                //对当前子View进行排列
                child.layout(curLeft, curTop, curLeft + exactWidth, curTop + exactHeight);

                //计算出单行中子View的最大height值
                if (maxHeight < exactHeight) {
                    //获取最大的子View的高度
                    maxHeight = exactHeight;
                }
                curLeft+=exactWidth+mChildSpacing;
            }
            curTop += maxHeight + mRowSpacing;
            maxHeight = 0;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        itemLineWidth.clear();
        lineNum = 0;
        mRowCount = 0;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int rowSize = widthSize - getPaddingLeft() - getPaddingRight();
        int exactWidth =getPaddingLeft();
        int exactHeight = 0;
        int maxWidth = 0;
        int rowCount = 0;
        int maxHeight = 0;
        int measuredHeight = 0;
        int count = getChildCount();
        int lineWidth = 0;
        int childWidth=0;
        int childNumInRow = 0;
        int tempIndex=0;
        int exceptLastRowNum=0;
        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);

            if (child.getVisibility() == View.GONE) {
                continue;
            }

            CustomLayoutParams params = (CustomLayoutParams) child.getLayoutParams();
//                measureChildWithMargins(child, widthMeasureSpec, 0,heightMeasureSpec, measuredHeight);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//            Log.d(TAG, "onMeasure: 11111----->" + params.leftMargin + "  --->" + params.rightMargin);


//            maxWidth += Math.max(maxWidth, child.getMeasuredWidth() + mChildSpacing);
//            exactWidth += (child.getMeasuredWidth() + mChildSpacing);
//            lineWidth += child.getMeasuredWidth() + mChildSpacing;
//            Log.d(TAG, "rowCount->" + rowCount + "-单个Child的width->" + (child.getMeasuredWidth() + mChildSpacing) + "-lineWidth->" + lineWidth + "-exactWidth->" + (exactWidth) + "-屏幕可用宽度->" + (mScreenWidth - getPaddingRight() - getPaddingLeft()));
//            if (((exactWidth) / (mScreenWidth - getPaddingRight() - getPaddingLeft())) > rowCount) {
//                Log.d(TAG, "onMeasure: iiiii----->" + i);
//                lineWidth = lineWidth - child.getMeasuredWidth() - mChildSpacing;
//                itemLineWidth.add(lineWidth);
//                lineWidth = 0;
//                ++rowCount;
//                exactHeight += child.getMeasuredHeight() + mRowSpacing;
//                childNumInRow=1;
//                Log.d(TAG, "aaaaaaaaa---->" + childNumInRow);
//            } else {
//                ++childNumInRow;
//                Log.d(TAG, "bbbbbbbbb---->" + childNumInRow);
//                exactHeight = Math.max(exactHeight, child.getMeasuredHeight() + mRowSpacing);
////                Log.d(TAG, "onMeasure: 2222222----->"+exactHeight+"  ------>"+i);
//            }

            maxWidth += Math.max(maxWidth, child.getMeasuredWidth() + mChildSpacing);
            childWidth = (child.getMeasuredWidth() + mChildSpacing);
            lineWidth += child.getMeasuredWidth() + mChildSpacing;

            if (exactWidth+childWidth> rowSize ) {
//                Log.d(TAG, "onMeasure: iiiii----->" + i);
                tempIndex=i;
                lineWidth = lineWidth - childWidth - mChildSpacing;
                itemLineWidth.add(lineWidth);
                lineWidth = childWidth - mChildSpacing;
                ++rowCount;
                exactWidth =getPaddingLeft()+(child.getMeasuredWidth() + mChildSpacing);
                exactHeight += child.getMeasuredHeight() + mRowSpacing;
                itemLineNum.add(childNumInRow);
                exceptLastRowNum+=childNumInRow;
                childNumInRow=1;
//                Log.d(TAG, "aaaaaaaaa---->" + childNumInRow);
            } else {
                exactWidth += (child.getMeasuredWidth() + mChildSpacing);
                ++childNumInRow;
//                Log.d(TAG, "bbbbbbbbb---->" + childNumInRow);
                exactHeight = Math.max(exactHeight, child.getMeasuredHeight() + mRowSpacing);
//                Log.d(TAG, "onMeasure: 2222222----->"+exactHeight+"  ------>"+i);
            }

        }

        int lastRowWidth=0;
        for(int i=tempIndex;i<count;i++) {
            lastRowWidth+=getChildAt(i).getMeasuredWidth()+mChildSpacing;
        }

        itemLineWidth.add(lastRowWidth);
        itemLineNum.add(count-exceptLastRowNum);

        Log.d(TAG, "exceptLastRowNum--->"+exceptLastRowNum);

        Log.d(TAG, "onMeasure: ----------------------分割线---------------------------");
        mRowCount =rowCount+1;
//        Log.d(TAG, "mRowCount: --->"+mRowCount);
//        itemLineWidth.add(100);
//        itemLineWidth.add(100);
//        itemLineWidth.add(lineWidth + mChildSpacing);
//        Log.d(TAG, "itemLineWidth.size(): ----->" + itemLineWidth.size());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth()) + getPaddingRight() + getPaddingLeft();
        exactHeight = Math.max(exactHeight, getSuggestedMinimumHeight()) + getPaddingTop() + getPaddingBottom();
//        curTop = Math.max(curTop, getSuggestedMinimumHeight()) + getPaddingTop() + getPaddingBottom();
        for (Integer i : itemLineWidth) {
//            Log.d(TAG, "lineWidth----aaaaa---->: "+(i));
        }
//        Log.d(TAG, "exactHeight: ---->" + exactHeight);
        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec),
                resolveSize(exactHeight, heightMeasureSpec));
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

        public CustomLayoutParams(android.view.ViewGroup.LayoutParams source) {
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
