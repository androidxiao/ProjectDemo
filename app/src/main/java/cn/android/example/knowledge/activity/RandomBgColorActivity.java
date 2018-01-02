package cn.android.example.knowledge.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Random;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/24.
 */

public class RandomBgColorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_random_bg_color_layout);
        final LinearLayout layout= (LinearLayout) findViewById(R.id.id_ll);
        Button btnChangeColor= (Button) findViewById(R.id.id_btn_change_color);
        btnChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();

                int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

                layout.setBackgroundColor(color);
            }
        });
    }
}
