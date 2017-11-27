package cn.custom.widget.activity;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.custom.widget.widget.animation.BoundCircleView;
import cn.custom.widget.widget.animation.CharEvaluator;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/11/23.
 */

public class CustomEvaluatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_evaluator_layout);
        Button btnStart= (Button) findViewById(R.id.id_start_animator);
        final TextView tvText = (TextView) findViewById(R.id.id_tv_text);
        final Button btnCancel = (Button) findViewById(R.id.id_cancel_animator);


        Button btnStart1= (Button) findViewById(R.id.id_start_animator1);
        final BoundCircleView boudCircle= (BoundCircleView) findViewById(R.id.id_bound_circle_view);
        final Button btnCancel1 = (Button) findViewById(R.id.id_cancel_animator1);
        final ImageView ivPhone= (ImageView) findViewById(R.id.id_iv_phone);

        final ValueAnimator valueAnimator=ValueAnimator.ofObject(new CharEvaluator(),new Character('A'),new Character('Z'));
        valueAnimator.setDuration(4000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                char cha= (char) animation.getAnimatedValue();
                tvText.setText(String.valueOf(cha));
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.start();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAnimator.cancel();
            }
        });

        btnStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boudCircle.starAnimator();
            }
        });

        btnCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneAimator(ivPhone);
            }
        });

    }

    private void phoneAimator(ImageView imageView){
        Keyframe keyframe1 = Keyframe.ofFloat(0f, 0f);
        Keyframe keyframe2 = Keyframe.ofFloat(0.1f, 20f);
        Keyframe keyframe3 = Keyframe.ofFloat(0.2f, -20f);
        Keyframe keyframe4 = Keyframe.ofFloat(0.3f, 20f);
        Keyframe keyframe5 = Keyframe.ofFloat(0.4f, -20f);
        Keyframe keyframe6 = Keyframe.ofFloat(0.5f, 20f);
        Keyframe keyframe7 = Keyframe.ofFloat(0.6f, -20f);
        Keyframe keyframe8 = Keyframe.ofFloat(0.7f, 20f);
        Keyframe keyframe9 = Keyframe.ofFloat(0.8f, -20f);
        Keyframe keyframe10 = Keyframe.ofFloat(0.9f, 20f);
        Keyframe keyframe11 = Keyframe.ofFloat(1.0f, 0f);

        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofKeyframe("rotation", keyframe1, keyframe2, keyframe3, keyframe4, keyframe5, keyframe6, keyframe7, keyframe8, keyframe9, keyframe10, keyframe11);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(imageView, propertyValuesHolder);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }
}
