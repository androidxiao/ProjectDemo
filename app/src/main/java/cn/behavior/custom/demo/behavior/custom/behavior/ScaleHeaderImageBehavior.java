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

public  class ScaleHeaderImageBehavior extends CoordinatorLayout.Behavior<View> {

    public static final String TAG = "value";

    private WeakReference<View>mReference;

    public ScaleHeaderImageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.id_iv_girl) {
            Log.d(TAG, "layoutDependsOn: 1111111");
            if (mReference == null) {
                mReference = new WeakReference<>(dependency);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        CoordinatorLayout.LayoutParams params= (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if(params!=null&&params.height==CoordinatorLayout.LayoutParams.MATCH_PARENT){
            View dependencyView = mReference.get();
            float scaleX =1-Math.abs((1-dependencyView.getScaleX()))-0.2f;
            Log.d(TAG, "onDependentViewChanged:   scaleX----->"+scaleX+"    ---->"+(child.getContext().getResources().getDimension(R.dimen.d_160)*scaleX));

//            child.layout(0,0,parent.getWidth(),parent.getHeight());
                child.setTranslationY(child.getContext().getResources().getDimension(R.dimen.d_160) * scaleX);
            return true;
        }
        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        CoordinatorLayout.LayoutParams params= (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if(params!=null&&params.height==CoordinatorLayout.LayoutParams.MATCH_PARENT){
            child.layout(0,0,parent.getWidth(), (int) child.getContext().getResources().getDimension(R.dimen.d_40));
            child.setTranslationY(child.getContext().getResources().getDimension(R.dimen.d_160)*0.8f);
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
        Log.d(TAG, "onNestedScrollAccepted: ");
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        Log.d(TAG, "onNestedScroll: dyConsumed---->"+dyConsumed+"--dyUnconsumed--->"+dyUnconsumed);


    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        Log.d(TAG, "onNestedPreScroll: ");
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
        Log.d(TAG, "onNestedFling: ");
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        Log.d(TAG, "onNestedPreFling: ");
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }


}
