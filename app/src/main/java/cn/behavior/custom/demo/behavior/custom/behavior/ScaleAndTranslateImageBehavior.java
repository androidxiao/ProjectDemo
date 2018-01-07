package cn.behavior.custom.demo.behavior.custom.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/6.
 */

public class ScaleAndTranslateImageBehavior extends CoordinatorLayout.Behavior<View> {

    private final static float MIN_AVATAR_PERCENTAGE_SIZE   = 0.3f;
    private final static int EXTRA_FINAL_AVATAR_PADDING     = 80;

    private final static String TAG = "behavior";
    private Context mContext;

    private float mCustomFinalYPosition;
    private float mCustomStartXPosition;
    private float mCustomStartToolbarPosition;
    private float mCustomStartHeight;
    private float mCustomFinalHeight;

    private float mAvatarMaxSize;
    private float mFinalLeftAvatarPadding;
    private float mStartPosition;
    private int mStartXPosition;
    private float mStartToolbarPosition;
    private int mStartYPosition;
    private int mFinalYPosition;
    private int mStartHeight;
    private int mFinalXPosition;
    private float mChangeBehaviorPoint;

    public ScaleAndTranslateImageBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();

        mFinalLeftAvatarPadding = context.getResources().getDimension(
                R.dimen.spacing_normal);
    }

    private void init() {
        bindDimensions();
    }

    private void bindDimensions() {
        mAvatarMaxSize = mContext.getResources().getDimension(R.dimen.d_120);
    }


    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Toolbar;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        initProperties(child, dependency);
//              maybeInitProperties(child, dependency);

        final int maxScrollDistance = (int) (mStartToolbarPosition);
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

        if (expandedPercentageFactor < mChangeBehaviorPoint) {
            float heightFactor = (mChangeBehaviorPoint - expandedPercentageFactor) / mChangeBehaviorPoint;

            float distanceXToSubtract = ((mStartXPosition - mFinalXPosition)
                    * heightFactor) + (child.getHeight()/2);
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (child.getHeight()/2);

            child.setX(mStartXPosition - distanceXToSubtract);
            child.setY(mStartYPosition - distanceYToSubtract);

            float heightToSubtract = ((mStartHeight - mCustomFinalHeight) * heightFactor);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = (int) (mStartHeight - heightToSubtract);
            lp.height = (int) (mStartHeight - heightToSubtract);
            child.setLayoutParams(lp);
        } else {
            float distanceYToSubtract = ((mStartYPosition - mFinalYPosition)
                    * (1f - expandedPercentageFactor)) + (mStartHeight/2);

            child.setX(mStartXPosition - child.getWidth()/2);
            child.setY(mStartYPosition - distanceYToSubtract);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = (int) (mStartHeight);
            lp.height = (int) (mStartHeight);
            child.setLayoutParams(lp);
        }


        return true;
    }


    private void initProperties(View child, View dependency) {
        if (mStartYPosition == 0)
            mStartYPosition = (int) (dependency.getY());

        if (mFinalYPosition == 0)
            mFinalYPosition = (dependency.getHeight() /2);

        if (mStartHeight == 0)
            mStartHeight = child.getHeight();

        if (mStartXPosition == 0)
            mStartXPosition = (int) (child.getX() + (child.getWidth() / 2));

        if (mFinalXPosition == 0)
            mFinalXPosition = mContext.getResources().getDimensionPixelOffset(R.dimen.abc_action_bar_content_inset_material) + ((int) mCustomFinalHeight / 2);

        if (mStartToolbarPosition == 0)
            mStartToolbarPosition = dependency.getY();

        if (mChangeBehaviorPoint == 0) {
            mChangeBehaviorPoint = (child.getHeight() - mCustomFinalHeight) / (2f * (mStartYPosition - mFinalYPosition));
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
