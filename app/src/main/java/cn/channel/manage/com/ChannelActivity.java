package cn.channel.manage.com;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;

public class ChannelActivity extends AppCompatActivity implements ChannelAdapter.onItemRangeChangeListener {

    private RecyclerView mRecyclerView;
    private List<ChannelBean> mList;
    private ChannelAdapter mAdapter;
    private String select[] = {"要闻", "体育", "新时代", "汽车", "时尚", "国际", "电影", "财经", "游戏", "科技", "房产", "政务", "图片", "独家"};
    private String recommend[] = {"娱乐", "军事", "文化", "视频", "股票", "动漫", "理财", "电竞", "数码", "星座", "教育", "美容", "旅游"};
    private String city[] = {"重庆", "深圳", "汕头", "东莞", "佛山", "江门", "湛江", "惠州", "中山", "揭阳", "韶关", "茂名", "肇庆", "梅州", "汕尾", "河源", "云浮", "四川"};

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
//        for (String bean : select) {
//            mList.add(new ChannelBean(bean, 1, R.layout.adapter_channel, true));
//        }
        for (int i=0;i<select.length;i++) {
            if(i==0) {
                mList.add(new ChannelBean(select[i], 1, R.layout.adapter_channel, true,true));
            }else{
                mList.add(new ChannelBean(select[i], 1, R.layout.adapter_channel, true,false));
            }
        }
        ChannelBean tabBean = new ChannelBean();
        tabBean.setLayoutId(R.layout.adapter_tab);
        tabBean.setSpanSize(4);
        mList.add(tabBean);
        List<ChannelBean> recommendList = new ArrayList<>();
        for (String bean : recommend) {
            recommendList.add(new ChannelBean(bean, 1, R.layout.adapter_channel, true));
        }
        List<ChannelBean> cityList = new ArrayList<>();
        for (String bean : city) {
            cityList.add(new ChannelBean(bean, 1, R.layout.adapter_channel, false));
        }
        mList.addAll(recommendList);
        mAdapter = new ChannelAdapter(this, mList, recommendList);
        mAdapter.setFixSize(1);
        mAdapter.setSelectedSize(select.length);
        mAdapter.setRecommend(true);
        mAdapter.setOnItemRangeChangeListener(this);
        mRecyclerView.setAdapter(mAdapter);
        WindowManager m = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int spacing = (m.getDefaultDisplay().getWidth() - dip2px(this, 70) * 4) / 5;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,spacing,true));
        ItemDragCallback callback=new ItemDragCallback(mAdapter,2);
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
