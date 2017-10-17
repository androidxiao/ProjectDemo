package cn.custom.widget.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.custom.widget.Px2DpUtil;
import cn.custom.widget.adapter.JianshuAdapter;
import cn.custom.widget.utils.ViewWidthWrapper;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/16.
 */

public class JianshuActivity extends AppCompatActivity {

    public static final String TAG = "ez";
    private RelativeLayout mRlTitleLayout;
    private TextView mMinTvSearchView;
    private RecyclerView mRecyclerView;
    private int mMaxWidth;
    private int mMinWidth;
    private LinearLayout mLlTitle;
    private int mHeaderHeight;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_jianshu_search_layout);
        findView();
        initRecyclerView();
        initRvAdapter();
        listenerRvScroll();
        mMaxWidth = ScreenUtil.getScreenWidth();
        int rightMargin = Px2DpUtil.dp2px(this, 17);
        mMaxWidth = mMaxWidth -rightMargin*2;
        mMinWidth = Px2DpUtil.dp2px(this, 80);
        mHeaderHeight=Px2DpUtil.dp2px(this,R.dimen.d_120);
    }

    private void findView(){
        mLlTitle = (LinearLayout) findViewById(R.id.id_ll_title_layout);
        mRlTitleLayout = (RelativeLayout) findViewById(R.id.id_title_layout);
        mMinTvSearchView = (TextView) findViewById(R.id.id_tv_search_min);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycleview);
        mLlTitle.setBackgroundColor(getResources().getColor(android.R.color.white));
    }

    private void initRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
    }

    private void initRvAdapter(){
        JianshuAdapter adapter = new JianshuAdapter(this);
        adapter.addHeaderView();
        adapter.appendList();
        mRecyclerView.setAdapter(adapter);
    }


    private void listenerRvScroll(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager l = (LinearLayoutManager)recyclerView.getLayoutManager();
                int adapterNowPos = l.findFirstVisibleItemPosition();
                int firstCompletelyVisibleItemPosition = l.findFirstCompletelyVisibleItemPosition();
                if (adapterNowPos == 0) {
                    double delta = Math.floor(((float) getScollYDistance(recyclerView) % mHeaderHeight));
                    mLlTitle.getBackground().setAlpha((int) delta);
                }

                if (adapterNowPos == 1) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(new ViewWidthWrapper(mRlTitleLayout), "width", mMaxWidth);
                    setAnimatorListener(animator,1);
                }

                if(firstCompletelyVisibleItemPosition==0){
                    ObjectAnimator animator = ObjectAnimator.ofInt(new ViewWidthWrapper(mRlTitleLayout), "width", mMinWidth);
                    setAnimatorListener(animator,0);
                }


            }

        });
    }


    public int getScollYDistance(RecyclerView rv) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) rv.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    private void setAnimatorListener(ObjectAnimator animator,final int visibity){

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (visibity == 1) {
                    mMinTvSearchView.setText("搜索简书内容和朋友");
                }

                if (visibity == 0) {
                    mMinTvSearchView.setText("搜索");
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(100).start();
    }
}
