package cn.android.example.knowledge.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import cn.android.example.knowledge.adapter.AppsAdapter;
import cn.android.example.knowledge.utils.ApkInfos;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/24.
 */

public class ShowInstallAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_install_app_layout);
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        List<String> appInfos = new ApkInfos(this).GetAllInstalledApkInfo();
        AppsAdapter adapter=new AppsAdapter(this,appInfos);
        recyclerView.setAdapter(adapter);
    }
}
