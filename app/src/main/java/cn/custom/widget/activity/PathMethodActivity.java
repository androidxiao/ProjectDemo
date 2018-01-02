package cn.custom.widget.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import cn.custom.widget.widget.path.CubicBezierView;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/14.
 */

public class PathMethodActivity extends AppCompatActivity {


    private RadioButton mCbTop;
    private RadioButton mCbBot;
    private CubicBezierView mCubicBezierView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_method_layout);

        findView();

        mCbBot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCubicBezierView.setMode(isChecked);
            }
        });

        mCbTop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCubicBezierView.setMode(isChecked);
            }
        });


    }


    private void findView(){
        mCbTop = (RadioButton) findViewById(R.id.id_cb_top);
        mCbBot = (RadioButton) findViewById(R.id.id_cb_bot);
        mCubicBezierView = (CubicBezierView) findViewById(R.id.id_cubic_view);
    }
}
