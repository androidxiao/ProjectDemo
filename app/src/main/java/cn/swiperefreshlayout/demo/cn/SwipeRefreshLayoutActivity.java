package cn.swiperefreshlayout.demo.cn;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;
import cn.project.demo.com.utils.LogUtil;
import cn.swiperefreshlayout.demo.cn.adapter.SwipeAdapter;
import cn.swiperefreshlayout.demo.cn.base.BaseActivity;
import cn.swiperefreshlayout.demo.cn.utils.PaginationScrollListener;

/**
 * Created by chawei on 2017/9/21.
 *
 * SwipeRefreshLayout配合RecycleView下拉刷新，和自己写的上拉加载更多
 */

public class SwipeRefreshLayoutActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private SwipeAdapter mAdapter;
    private List<String> mList;
    private List<String> mListAll=new ArrayList<>();


    private static final int PAGE_START = 1;
    private int TOTAL_PAGES = 2;
    private int currentPage = PAGE_START;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private LinearLayoutManager mManager;

    //static
    class MyHandler extends Handler{
        WeakReference<Activity>mWeakReference;
        public MyHandler(Activity activity){
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = mWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, "refresh", Toast.LENGTH_SHORT).show();
                if(mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
                loadNextPage();
//                mListAll.clear();
//                currentPage=PAGE_START;
//                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_layout);
        findView();
        initRecyclerView();
        initSwipeRefreshLayout(mRefreshLayout,false);
//        onRefreshLoadData();
        initList();
        rvScrollListener();
    }

    private void findView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.id_swipe);
    }


    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycleview);
        mManager = new LinearLayoutManager(this);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);

        mAdapter=new SwipeAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void rvScrollListener(){
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                LogUtil.debug("currentPage----->"+currentPage+"    TotalPage--->"+TOTAL_PAGES);
                new MyHandler(SwipeRefreshLayoutActivity.this).sendEmptyMessageDelayed(0, 4000);
//                loadNextPage();
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void loadNextPage() {

        mAdapter.removeLoadingFooter();
        isLoading=false;
        LogUtil.debug("loadNextPage--->"+currentPage);
        mList.clear();
        for(int i=0;i<5;i++) {
            mList.add("ITEM------>"+i+"   当前page--->"+currentPage);
        }
        mListAll.addAll(mList);
        mAdapter.appendList(mListAll);

        if(currentPage<TOTAL_PAGES){//!=
            mAdapter.addLoadingFooter();
        }else{
            isLastPage=true;
        }

        if(currentPage==TOTAL_PAGES){
            mAdapter.addLoadingFooter();
            mAdapter.isLoadFinishAll(true);
        }

    }

    private void initList(){
        mList = new ArrayList<>();
        for(int i=0;i<5;i++) {
            mList.add("ITEM------>"+i+"   当前page--->"+currentPage);
        }
        LogUtil.debug("initList--->"+currentPage);
        mListAll.addAll(mList);
        mAdapter.appendList(mListAll);
        if(currentPage<=TOTAL_PAGES){
            mAdapter.addLoadingFooter();
        }else{
            isLastPage=true;
        }
    }


    @Override
    protected void onRefreshLoadData() {
        super.onRefreshLoadData();
        new MyHandler(SwipeRefreshLayoutActivity.this).sendEmptyMessageDelayed(0,1000);
    }
}
