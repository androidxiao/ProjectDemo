package cn.day.night.model.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/9/18.
 */

public class DayNightMainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night_main_layout);


        findViewById(R.id.id_btn_auto_mode).setOnClickListener(this);
        findViewById(R.id.id_btn_day_mode).setOnClickListener(this);
        findViewById(R.id.id_btn_night_mode).setOnClickListener(this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_auto_mode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            case R.id.id_btn_day_mode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case R.id.id_btn_night_mode:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
        Intent intent = new Intent(this, DayNightActivity.class);
        startActivity(intent);
    }
}
