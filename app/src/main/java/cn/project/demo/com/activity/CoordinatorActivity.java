package cn.project.demo.com.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import cn.project.demo.com.R;
import cn.project.demo.com.adapter.CoordinatorAdapter;

/**
 * Created by chawei on 2017/9/12.
 *
 * CoordinatorLayout、AppBarLayout、CollapsingToolbarLayout、Toolbar的配合使用
 */

public class CoordinatorActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_constraint_layout);
        mRv = (RecyclerView) findViewById(R.id.id_recycleview);
        initRecycler();
        initListener();

    }

    private void initRecycler(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(manager);


        CoordinatorAdapter adapter = new CoordinatorAdapter(this);
        mRv.setAdapter(adapter);
    }

    private void initListener(){
        findViewById(R.id.id_tv_back).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_tv_back:
                Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
