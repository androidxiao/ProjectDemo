package cn.channel.manage.com;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;

public class ChannelActivity extends AppCompatActivity implements ChannelAdapter.onItemRangeChangeListener {

    private RecyclerView mRecyclerView;
    private List<ChannelBean> mList;
    private ChannelAdapter mAdapter;
    private String mSelected[] = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private String mRecommend[] = {"11", "22", "33", "44", "55", "66", "77", "88"};
    private String mUnChoose[] = {"a", "b", "c", "d", "e", "f", "g", "h"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mList.get(position).getSpanSize();
            }
        });
        mRecyclerView.setLayoutManager(manager);
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setMoveDuration(300);     //设置动画时间
        animator.setRemoveDuration(0);
        mRecyclerView.setItemAnimator(animator);
        ChannelBean title = new ChannelBean();
        title.setLayoutId(R.layout.adapter_title);
        title.setSpanSize(4);
        mList.add(title);
//        for (String bean : mSelected) {
//            mList.add(new ChannelBean(bean, 1, R.layout.adapter_channel, true));
//        }
        for (int i=0;i<mSelected.length;i++) {
            if(i==0) {
                mList.add(new ChannelBean(mSelected[i], 1, R.layout.adapter_channel, true,true));
            }else{
                mList.add(new ChannelBean(mSelected[i], 1, R.layout.adapter_channel, true,false));
            }
        }
        ChannelBean tabBean = new ChannelBean();
        tabBean.setLayoutId(R.layout.adapter_tab);
        tabBean.setSpanSize(4);
        mList.add(tabBean);
        List<ChannelBean> mRecommendList = new ArrayList<>();
        for (String bean : mRecommend) {
            mRecommendList.add(new ChannelBean(bean, 1, R.layout.adapter_channel, true));
        }
        List<ChannelBean> mUnChooseList = new ArrayList<>();
        for (String bean : mUnChoose) {
            mUnChooseList.add(new ChannelBean(bean, 1, R.layout.adapter_channel, false));
        }
        mList.addAll(mRecommendList);
        mAdapter = new ChannelAdapter(this, mList, mRecommendList);
        mAdapter.setFixSize(1);
        mAdapter.setSelectedSize(mSelected.length);
        mAdapter.setRecommend(true);
        mAdapter.setOnItemRangeChangeListener(this);
        mRecyclerView.setAdapter(mAdapter);
//        WindowManager m = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        int spacing = (m.getDefaultDisplay().getWidth() - dip2px(this, 70) * 4) / 5;
//        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,dip2px(this,2.5f),true));
        ItemDragCallback callback=new ItemDragCallback(mAdapter,dip2px(this,5f));
        ItemTouchHelper helper=new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void refreshItemDecoration() {
        mRecyclerView.invalidateItemDecorations();
    }
}
