package cn.rv.item.longpress.drag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/5/2.
 */

public class RvItemDragActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<ChannelBean> mList;
    private ChannelAdapter mAdapter;
    private String mTitles[] = {"自选", "BTC", "OKEx", "ETH", "火币", "ZB", "USDT"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_item_drag_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_rv);
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
        animator.setMoveDuration(200);
        animator.setRemoveDuration(0);
        mRecyclerView.setItemAnimator(animator);
        for (int i = 0; i < mTitles.length; i++) {
            if (i == 0) {
                mList.add(new ChannelBean(mTitles[i], 1, true));
            } else {
                mList.add(new ChannelBean(mTitles[i], 1, false));
            }
        }
        mAdapter = new ChannelAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        ItemDragCallback callback = new ItemDragCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
    }
}
