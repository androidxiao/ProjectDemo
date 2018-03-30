package cn.tablayout.demo.com.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/7.
 */

public class UpdateToolbarColorActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private String[] colors = {"红", "绿", "蓝", "紫", "灰"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_toolbar_color_layout);
        tabLayout = (TabLayout) findViewById(R.id.id_tab_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        for (String color : colors) {
            tabLayout.addTab(tabLayout.newTab().setText(color));
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int colorFrom = ((ColorDrawable) toolbar.getBackground()).getColor();
                int colorTo = getColorForTab(tab.getPosition());

                ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                colorAnimation.setDuration(1000);
                colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int color = (int) animator.getAnimatedValue();

                        toolbar.setBackgroundColor(color);
                        tabLayout.setBackgroundColor(color);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(color);
                        }
                    }

                });
                colorAnimation.start();
                toolbar.setTitle(colors[tab.getPosition()].toUpperCase());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public int getColorForTab(int position) {
        if (position == 0) return ContextCompat.getColor(this, R.color.red);
        else if (position == 1) return ContextCompat.getColor(this, android.R.color.holo_green_light);
        else if (position == 2) return ContextCompat.getColor(this, R.color.blue_color);
        else if (position == 3) return ContextCompat.getColor(this, android.R.color.holo_purple);
        else return ContextCompat.getColor(this, android.R.color.darker_gray);
    }
}
