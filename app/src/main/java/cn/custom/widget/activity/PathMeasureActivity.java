package cn.custom.widget.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.custom.widget.widget.pathmeasure.SearchView2;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/16.
 */

public class PathMeasureActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG = "gear2";

    private SearchView2 mSearchView2;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_path_measure_layout);

        findView();
    }

    private void findView(){
        Button btnStart = (Button) findViewById(R.id.id_btn_start);
        Button btnEnd = (Button) findViewById(R.id.id_btn_end);
        mSearchView2 = (SearchView2) findViewById(R.id.id_search_view2);

        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_start:
                mSearchView2.startAnimator();
                break;
            case R.id.id_btn_end:
                mSearchView2.cancelSearchAnimator();
                break;
        }
    }
}
