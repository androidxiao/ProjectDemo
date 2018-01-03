package cn.behavior.custom.demo.CollapsingToolbarLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/3.
 */

public class CollapsingToolbarLayoutActivity extends AppCompatActivity {

    public static final String TAG = "ez";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing_toolbar_layout);
        final ImageView imageView= (ImageView) findViewById(R.id.id_iv_toolbar);
        final TextView textView= (TextView) findViewById(R.id.id_tv_toolbar);
        final AppBarLayout appBarLayout= (AppBarLayout) findViewById(R.id.appbar_layout);
        final Toolbar toolbar= (Toolbar) findViewById(R.id.id_tool_bar);
        final CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapse_layout);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.c_3ec88e));
        final int totalHeight = Px2DpUtil.dp2px(this, 250);
        final int titleHeight = Px2DpUtil.dp2px(this, 60);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int abs = Math.abs(verticalOffset);
                if(abs==(totalHeight-titleHeight)){
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    //折叠了
                    collapsingToolbarLayout.setTitle("");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.c_bat_man));
                }else if(abs==0){
                    imageView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    collapsingToolbarLayout.setTitle("展开的标题");
                    toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
                
            }
        });



    }
}
