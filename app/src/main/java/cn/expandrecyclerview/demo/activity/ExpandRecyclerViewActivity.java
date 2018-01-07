package cn.expandrecyclerview.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/7.
 */

public class ExpandRecyclerViewActivity extends AppCompatActivity{


    private RecyclerView recyclerView;
    private ArrayList<GroupText> mobileOSes;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_recyclerview_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mobileOSes = new ArrayList<>();

        setData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(this, mobileOSes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    private void setData() {
        ArrayList<ChildText> iphones = new ArrayList<>();
        iphones.add(new ChildText("苹果"));
        iphones.add(new ChildText("橘子"));
        iphones.add(new ChildText("芒果"));
        iphones.add(new ChildText("香蕉"));
        iphones.add(new ChildText("火龙果"));
        iphones.add(new ChildText("草莓"));
        iphones.add(new ChildText("柚子"));
        iphones.add(new ChildText("哈密瓜"));

        ArrayList<ChildText> nexus = new ArrayList<>();
        nexus.add(new ChildText("足球"));
        nexus.add(new ChildText("篮球"));
        nexus.add(new ChildText("乒乓球"));
        nexus.add(new ChildText("棒球"));
        nexus.add(new ChildText("保龄球"));
        nexus.add(new ChildText("溜溜球"));
        nexus.add(new ChildText("橄榄球"));

        ArrayList<ChildText> windowPhones = new ArrayList<>();
        windowPhones.add(new ChildText("单击游戏"));
        windowPhones.add(new ChildText("主机游戏"));
        windowPhones.add(new ChildText("FPS游戏"));
        windowPhones.add(new ChildText("挂机游戏"));
        windowPhones.add(new ChildText("小游戏"));
        windowPhones.add(new ChildText("手游"));

        mobileOSes.add(new GroupText("水果", iphones));
        mobileOSes.add(new GroupText("球类", nexus));
        mobileOSes.add(new GroupText("游戏", windowPhones));
    }
}
