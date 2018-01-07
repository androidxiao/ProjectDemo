package cn.behavior.custom.demo.behavior.custom.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/4.
 */

public class ScaleHeaderBehavior extends CoordinatorLayout.Behavior<View> {

    public static final String TAG = "ez";

    private WeakReference<View> mReference;

    public ScaleHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.id_iv_girl) {
            if (mReference == null) {
                mReference = new WeakReference<>(dependency);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        //初始化时，将nestedScrollView向下平移到dependencyView（我这里是ImageView）的下方，让图片可以全部显示出来
        if (params != null && params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, 0, parent.getWidth(), parent.getHeight());
            child.setTranslationY(child.getContext().getResources().getDimension(R.dimen.d_200));
            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        super.onNestedScrollAccepted(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        // 在这个方法里只处理向下滑动
        if (dyUnconsumed > 0) {
            return;
        }

        float transY = child.getTranslationY() - dyUnconsumed;
        Log.i(TAG, "onNestedScroll:   --transY--->" + transY + "      child.getTranslationY():---->" + child.getTranslationY() + "   dyUnconsumed---->" + dxUnconsumed);

        headerScaleWithAlph(child);

        //这里是为了让图片全部显示出来，将nestedScrollView向下平移图片的height
        if (transY >= 0 && transY <= child.getContext().getResources().getDimension(R.dimen.d_200)) {
            child.setTranslationY(transY);
        }

    }

    private void headerScaleWithAlph(View child) {
        View dependencyView = mReference.get();

        float progress = child.getTranslationY() / dependencyView.getContext().getResources().getDimension(R.dimen.d_200);
        float scale = 1+ (1-progress);
        dependencyView.setScaleX(scale);
        dependencyView.setScaleY(scale);
        dependencyView.setAlpha(progress);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //这里只处理向上滑（上滑：类似上拉加载的方向 ，下滑：类似下拉刷新的方向）
        //上滑dy>0，下滑dy<0
        if (dy < 0) {
            return;
        }


        //原来的translationY-dy（用户滑动的距离）=child需要平移到的位置
        //比如原来的translationY为400，用户滑动了2，所以child需要平移到的位置为398，意味着，child向上平移了。
        // 如此循环下去,直到<=0（这里可以根据需求来），就不在平移，就会覆盖图片了
        float transY = child.getTranslationY() - dy;
        Log.i(TAG, "onNestedPreScroll:------>transY:-->" + transY + "++++child.getTranslationY():---->" + child.getTranslationY() + "---->dy:" + dy);

        headerScaleWithAlph(child);

        //这里是为了将child开始时的setTranslationY还原到0
        if (transY >= 0) {
            child.setTranslationY(transY);
            //注释也没什么影响，后续再看看
//            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }


}
