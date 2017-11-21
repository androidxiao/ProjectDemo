package cn.custom.widget.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.custom.widget.widget.CircleRingView;
import cn.custom.widget.widget.LoadingScaleAlphaView;
import cn.custom.widget.widget.ObjectAnimateCircleRingView;
import cn.custom.widget.widget.TwitterLikeView;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/12.
 */

public class CustomViewActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "ez";
    private CircleRingView mCircleRingView;
    private ObjectAnimateCircleRingView mObjectAnimateCircleRingView;
    private LoadingScaleAlphaView mScaleAlphaView;
    private AnimatorSet mAnimatorSet;
    private TwitterLikeView mTwitterLikeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_layout);

        findView();

        initListener();

        mCircleRingView.setProgressValue(50);

        final ObjectAnimator animator = ObjectAnimator.ofFloat(mObjectAnimateCircleRingView, "mPointAngle", 0, 50);
        animator.setDuration(3000);
        animator.start();

//        mScaleAlphaView.setMinRadius(5);
//        mScaleAlphaView.setMaxRadius(20);
//        mScaleAlphaView.startAnimate(mScaleAlphaView);
    }

    private void findView() {
        mCircleRingView = (CircleRingView) findViewById(R.id.id_circle_ring_view);
        mObjectAnimateCircleRingView = (ObjectAnimateCircleRingView) findViewById(R.id.id_object_animate_circle_ring_view);
        mScaleAlphaView = (LoadingScaleAlphaView) findViewById(R.id.id_loading_scale_alpha_view);
        mTwitterLikeView = (TwitterLikeView) findViewById(R.id.id_twitter_like_view);
    }

    private void initListener(){
        mTwitterLikeView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_twitter_like_view:
                mTwitterLikeView.starUnSelectAnimation();
                break;
        }
    }
}
