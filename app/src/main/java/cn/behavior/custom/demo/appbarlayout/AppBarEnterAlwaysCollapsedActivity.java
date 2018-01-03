package cn.behavior.custom.demo.appbarlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/3.
 */

public class AppBarEnterAlwaysCollapsedActivity extends AppCompatActivity {

    public static final String TAG = "ez";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_bar_enter_always_collapsed_layout);

        AppBarLayout appBarLayout= (AppBarLayout) findViewById(R.id.id_app_bar_layout);
        //当AppbarLayout 的偏移发生改变的时候回调，也就是子View滑动。(这里是Toolbar滑动的时候)
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(TAG, "onOffsetChanged: --verticalOffset->"+verticalOffset);
            }
        });
        appBarLayout.setExpanded(true);
        //返回AppbarLayout 所有子View的滑动范围
        int range = appBarLayout.getTotalScrollRange();
        Log.d(TAG, "AppbarLayout 所有子View的滑动范围: ---->"+range);
    }
}
