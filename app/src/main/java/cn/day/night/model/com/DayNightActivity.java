package cn.day.night.model.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/9/18.
 *
 * 1、切换日间/夜间模式
 *
 * 2、包含了密码可见性切换
 */

public class DayNightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_night_layout);


        TextView title = (TextView) findViewById(R.id.textviewMain);
        int themeMode = AppCompatDelegate.getDefaultNightMode();
        if (themeMode == AppCompatDelegate.MODE_NIGHT_AUTO) {
            title.setText("Current Theme : Auto");
        } else if (themeMode == AppCompatDelegate.MODE_NIGHT_NO) {
            title.setText("Current Theme : Day");
        } else if (themeMode == AppCompatDelegate.MODE_NIGHT_YES) {
            title.setText("Current Theme : Night");
        }
    }
}
