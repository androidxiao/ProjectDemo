package cn.tablayout.demo.com.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;
import cn.tablayout.demo.com.adapter.FragmentAdapter;
import cn.tablayout.demo.com.fragment.TabFragment;
import cn.tablayout.demo.com.widget.CustomBotTabItem;

/**
 * Created by chawei on 2018/1/22.
 */

public class BottomTabLayoutActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_tab_layout);


        findView();
        initFragmentList();
        setVpAdapter();

        CustomBotTabItem item = CustomBotTabItem.create();
        item.setContext(this)
                .setViewPager(mViewPager)
                .setTabLayout(mTabLayout)
                .build();
    }

    private void findView() {
        mTabLayout = (TabLayout) findViewById(R.id.id_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
    }

    private void initFragmentList() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(TabFragment.getInstance("主页"));
        mFragmentList.add(TabFragment.getInstance("理财"));
        mFragmentList.add(TabFragment.getInstance("添加"));
        mFragmentList.add(TabFragment.getInstance("消息"));
        mFragmentList.add(TabFragment.getInstance("我的"));
    }


    private void setVpAdapter() {
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), mFragmentList, this));
    }
}
