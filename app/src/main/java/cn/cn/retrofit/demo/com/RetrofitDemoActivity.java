package cn.cn.retrofit.demo.com;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import cn.cn.retrofit.demo.com.activity.CowFunctionActivity;
import cn.cn.retrofit.demo.com.http.HttpMethods;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/9/13.
 *
 * 测试retrofit
 */

public class RetrofitDemoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnHome;
    private ImageView mIvHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo_layout);
        findView();
        initListener();



        HttpMethods methods = HttpMethods.getInstance(this);
//        methods.getBannerData();

        methods.getSupplyList();


    }

    private void findView(){
        mBtnHome = (Button) findViewById(R.id.id_btn_home);

    }

    private void initListener(){
        mBtnHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_btn_home:
                startActivity(new Intent(this, CowFunctionActivity.class));
                 break;
        }
    }

}
