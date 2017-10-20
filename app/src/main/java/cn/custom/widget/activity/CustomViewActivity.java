package cn.custom.widget.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.custom.widget.widget.CircleRingView;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/12.
 */

public class CustomViewActivity extends AppCompatActivity {

    private CircleRingView mCircleRingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_layout);

        findView();

        mCircleRingView.setProgressValue(50);
    }

    private void findView(){
        mCircleRingView = (CircleRingView) findViewById(R.id.id_circle_ring_view);
    }
}
