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
import cn.tablayout.demo.com.widget.CustomTabItem;

/**
 * Created by chawei on 2017/9/26.
 *
 * TabLayout 的详细用法
 */

public class TabLayoutActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_layout);


        findView();
        initFragmentList();
        setVpAdapter();

        CustomTabItem item = CustomTabItem.create();
        item.setContext(this)
                .setViewPager(mViewPager)
                .setTabLayout(mTabLayout)
                .build()
                .setIndicator(70);
    }

    private void findView(){
        mTabLayout = (TabLayout) findViewById(R.id.id_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
    }

    private void initFragmentList(){
        mFragmentList = new ArrayList<>();
        mFragmentList.add(TabFragment.getInstance("A"));
        mFragmentList.add(TabFragment.getInstance("B"));
        mFragmentList.add(TabFragment.getInstance("C"));
    }



    private void setVpAdapter(){
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),mFragmentList,this));
    }

}
