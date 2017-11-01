package cn.custom.widget.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.custom.widget.widget.CircleRingView;
import cn.custom.widget.widget.ObjectAnimateCircleRingView;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/12.
 */

public class CustomViewActivity extends AppCompatActivity {

    public static final String TAG = "ez";
    private CircleRingView mCircleRingView;
    private ObjectAnimateCircleRingView mObjectAnimateCircleRingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_layout);

        findView();

        mCircleRingView.setProgressValue(50);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mObjectAnimateCircleRingView, "mPointAngle", 0, 50);
        animator.setDuration(3000);
        animator.start();


    }

    private void findView() {
        mCircleRingView = (CircleRingView) findViewById(R.id.id_circle_ring_view);
        mObjectAnimateCircleRingView = (ObjectAnimateCircleRingView) findViewById(R.id.id_object_animate_circle_ring_view);
    }
}
