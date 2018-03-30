package cn.custom.widget.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/19.
 */

public class CenterFlowLayout extends ViewGroup {

    public static final String TAG = "ez";
    //默认item的spacing
    private static final int DEFAULT_CHILD_SPACING = 0;
    //默认item的topMargin值
    private static final int DEFAULT_ROW_SPACING = 0;
    //子View之间的间距
    private int mChildSpacing = DEFAULT_CHILD_SPACING;
    //子View的marginTop值
    private int mRowSpacing = DEFAULT_ROW_SPACING;
    //用来存储每行item所占用的宽度的总和
    private List<Integer> itemLineWidth = new ArrayList<>();
    //用来存储每行item的个数
    private List<Integer> itemLineNum = new ArrayList<>();
    //用来记录item所占用的总行数
    private int mRowTotalCount = 0;

    public CenterFlowLayout(Context context) {
        this(context, null);
    }

    public CenterFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CenterFlowLayout, 0, 0);
        mChildSpacing = typedArray.getDimensionPixelSize(R.styleable.CenterFlowLayout_childSpacing, DEFAULT_CHILD_SPACING);
        mRowSpacing = typedArray.getDimensionPixelSize(R.styleable.CenterFlowLayout_rowSpacing, DEFAULT_ROW_SPACING);
        typedArray.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //获取父View的paddingLeft值
        int childLeft = getPaddingLeft();
        //获取父View的paddingTop值
        int childTop = getPaddingTop();
        //获取去除paddingRight之后的宽度
        int childRight = getMeasuredWidth() - getPaddingRight();
        //获取实际子View可用的宽度
        int availableWidth = childRight - childLeft;
        //子View初始时left值
        int curLeft;
        //子View初始时的top值
        int curTop = childTop;
        //单行中最大子View的高度
        int maxHeight = 0;
        //子View的高度
        int childHeight;
        //子View宽度
        int childWidth;
        //父View中子View的index
        int childIndex = 0;
        for (int j = 0; j < mRowTotalCount; j++) {
            //获取单行中子View的个数
            Integer childNum = itemLineNum.get(j);
            //初始化子View的left值
            curLeft = childLeft + (availableWidth - itemLineWidth.get(j)) / 2;
            //竖直方向上的margin值
            int verticalMargin = 0;
            for (int i = 0; i < childNum; i++) {
                //获取ViewGroup中的子View
                View child = getChildAt(childIndex++);
                //跳过可见性为GONE的子View
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                //获取子View的宽度
                childWidth = child.getMeasuredWidth();
                //获取子View的高度
                childHeight = child.getMeasuredHeight();
                //获取子View的LayoutParams
                MarginLayoutParams params = (CenterLayoutParams) child.getLayoutParams();
                int marginRight = 0, marginTop = 0, marginBottom;
                if (params instanceof MarginLayoutParams) {
                    //获取子View的marginRight值
                    marginRight = params.rightMargin;
                    //获取子View的marginTop值
                    marginTop = params.topMargin;
                    //获取子View的marginBottom值
                    marginBottom = params.bottomMargin;
                    //获取子View上下间距的和
                    if (childNum > 1 && i == 0) {
                        verticalMargin = marginTop + marginBottom;
                    }
                }
                //对子View进行布局
                child.layout(curLeft, curTop, curLeft + childWidth, curTop + childHeight);
                //找到单行中子View的最高高度
                if (maxHeight < childHeight) {
                    maxHeight = childHeight;
                }
                //叠加left值，向右一次排列
                curLeft += childWidth + mChildSpacing + marginRight;
            }
            //换行时的高度
            curTop += maxHeight + mRowSpacing + verticalMargin;
            //最大高度重置
            maxHeight = 0;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将属性值重置
        itemLineNum.clear();
        itemLineWidth.clear();
        mRowTotalCount = 0;
        //获取ViewGroup的宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        //获取ViewGroup的宽度mode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        //获取ViewGroup的高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //获取ViewGroup的高度mode
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //获取ViewGroup的可用宽度
        int rowLength = widthSize - getPaddingLeft() - getPaddingRight();
        //测量的宽度
        int measuredWidth = 0;
        //测量的高度
        int measuredHeight = 0;
        //子View最大的宽度
        int maxWidth = 0;
        //子View最大的高度
        int maxHeight = 0;
        //子View的总行数
        int rowCount = 0;
        //子View的个数
        int childCount = getChildCount();
        //单行子View的宽度
        int rowWidth = 0;
        //子View宽度
        int childWidth;
        //子View高度
        int childHeight;
        //单行中子View的个数
        int childNumInRow = 0;
        //最后一行第一个子View的index
        int tempIndex = 0;
        //除最后一行外，其他单行中子View的总和
        int exceptLastRowNum = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == View.GONE) {
                continue;
            }
            CenterLayoutParams params = (CenterLayoutParams) child.getLayoutParams();
            int marginRight = 0, marginTop = 0, marginBottom = 0;
            if (params instanceof MarginLayoutParams) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, measuredHeight);
                marginRight = params.rightMargin;
                marginTop = params.topMargin;
                marginBottom = params.bottomMargin;
            } else {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
            //子View本身的宽度+子View之间的间距+子View的marginRight值（我这么写偷懒了，mChildSpacing和marginRight不用同时设置值）
            childWidth = child.getMeasuredWidth() + mChildSpacing + marginRight;
            //子View本身的高度+子View的marginTop值+子View的marginBottom+marginTop值（我这么写偷懒了，mRowSpacing和marginBottom + marginTop不用同时设置值）
            childHeight = child.getMeasuredHeight() + mRowSpacing + marginBottom + marginTop;
            //叠加子View的宽度
            rowWidth += childWidth;
            //取出最大的宽度
            maxWidth += Math.max(maxWidth, childWidth);
            //判断是否需要换行
            if (measuredWidth + childWidth > rowLength) {
                //循环后，就可获取，最后一行中第一个子View的index
                tempIndex = i;
                //获取单行的宽度
                rowWidth = rowWidth - childWidth - mChildSpacing - marginRight;
                //存储单行的宽度
                itemLineWidth.add(rowWidth);
                //设置下一行宽度为第一个子View的宽度
                rowWidth = childWidth;
                //行数自增
                ++rowCount;
                //保存测量的宽度
                measuredWidth = childWidth;
                //叠加子View的高度
                maxHeight += childHeight;
                //存储单行中子View的个数
                itemLineNum.add(childNumInRow);
                //叠加获取除了最后一行外，其他行子View的个数的总和
                exceptLastRowNum += childNumInRow;
                //重置子View的个数，因为已经要换行了
                childNumInRow = 1;
            } else {
                //叠加宽度
                measuredWidth += childWidth;
                //叠加单行中的子View个数
                ++childNumInRow;
                //计算出最大的高度
                maxHeight = Math.max(maxHeight, childHeight);
            }

        }
        //最后一行的宽度
        int lastRowWidth = 0;
        int singleHorizalMargin = 0;
        for (int i = tempIndex; i < childCount; i++) {
            View child = getChildAt(i);
            int horizalMargin = 0;
            CenterLayoutParams params = (CenterLayoutParams) child.getLayoutParams();
            if (params instanceof MarginLayoutParams) {
                //获取子View的marginRight
                singleHorizalMargin = horizalMargin = params.rightMargin;
            }
            //叠加最后一行中所有子View的宽度
            lastRowWidth += child.getMeasuredWidth() + mChildSpacing + horizalMargin;
        }
        //获取最后一行子View的个数
        int lastChildCount = childCount - exceptLastRowNum;
        //减去多余的marginRight或mChildSpacing，得到最终的宽度
        lastRowWidth -= mChildSpacing == 0 ? singleHorizalMargin : mChildSpacing;
        //保存最后一行的宽度值
        itemLineWidth.add(lastRowWidth);
        //保存最后一行子View的个数
        itemLineNum.add(lastChildCount);
        //子View的总行数
        mRowTotalCount = rowCount + 1;
        //子View的最大宽度+ViewGroup中的paddingLeft和paddingRight值
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth()) + getPaddingRight() + getPaddingLeft();
        //子View的最大高度+ViewGroup中的paddingTop和paddingBottom值
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight()) + getPaddingTop() + getPaddingBottom();
        //根据widthMode设置width值
        measuredWidth=widthMode==MeasureSpec.EXACTLY?widthSize:maxWidth;
        //根据heightMode设置height值
        measuredHeight=heightMode==MeasureSpec.EXACTLY?heightSize:maxHeight;
        //设置ViewGroup的宽高
        setMeasuredDimension(resolveSize(measuredWidth, widthMeasureSpec),
                resolveSize(measuredHeight, heightMeasureSpec));
    }

    /**
     * 设置子View的间距
     * @param childSpacing
     */
    public void setChildSpacing(int childSpacing){
        mChildSpacing=childSpacing;
        requestLayout();
    }

    /**
     * 设置子View的marginTop值
     * @param rowSpacing
     */
    public void setRowSpacing(int rowSpacing){
        mRowSpacing=rowSpacing;
        requestLayout();
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new CenterLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new CenterLayoutParams(getContext(), attrs);
    }

    /**
     * @param p
     * @return
     */
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof CenterLayoutParams;
    }

    /**
     * 因为需要获取子View的margin值，所以这里需要重写一下该方法
     */
    public static class CenterLayoutParams extends MarginLayoutParams {

        public CenterLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CenterLayoutParams(LayoutParams source) {
            super(source);
        }

        public CenterLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CenterLayoutParams(int width, int height) {
            super(width, height);
        }
    }
}
