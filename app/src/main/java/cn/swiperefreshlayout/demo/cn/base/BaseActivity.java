package cn.swiperefreshlayout.demo.cn.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chawei on 2017/9/21.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化SwipeRefreshLayout
     * @param refreshLayout
     */
    protected void initSwipeRefreshLayout(SwipeRefreshLayout refreshLayout,boolean isAutoRefresh){
        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        refreshLayout.setProgressViewOffset(true, 0, 50);

        //设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        refreshLayout.setSize(SwipeRefreshLayout.LARGE);

        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //只设置一种颜色
//        refreshLayout.setColorSchemeColors(getResources()
//                .getColor(android.R.color.black));

        // 通过 setEnabled(false) 禁用下拉刷新
        refreshLayout.setEnabled(true);

        //设置手势下拉刷新的监听
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // 刷新动画开始后回调到此方法
                        onRefreshLoadData();
                    }
                }
        );

        if(isAutoRefresh) {
            autoRefresh(refreshLayout);
        }
    }

    /**
     * 自动刷新
     * @param refreshLayout
     */
    private void autoRefresh(SwipeRefreshLayout refreshLayout){
        refreshLayout.measure(0,0);
        refreshLayout.setRefreshing(true);
    }

    protected void onRefreshLoadData(){

    }
}
