package cn.custom.widget.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/9/25.
 */

public class ClearFocusEditTextActivity extends AppCompatActivity {

    private String TAG = "ez";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_focus_layout);
    }
}
