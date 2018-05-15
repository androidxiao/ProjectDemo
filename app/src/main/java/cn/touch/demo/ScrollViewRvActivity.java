package cn.touch.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.example.stockmarket.widget.CommonViewHolder;
import cn.project.demo.com.R;
import cn.touch.demo.adapter.CommonRvAdapter;

/**
 * Created by chawei on 2018/5/1.
 */

public class ScrollViewRvActivity extends AppCompatActivity {

    public static final String TAG = "ez";

    private RecyclerView mRv;
    private ArrayList<String> mList;
    private TouchListenerScrollView mScrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview_rv_layout);

        findView();
        initData();

        RvAdapter adapter = new RvAdapter(this, mList, R.layout.item_textview_view);
        mRv.setAdapter(adapter);

        mScrollView.setMoveViewList(((CommonRvAdapter)adapter).getMoveViewList());
//        setRvSvListener();
    }

    private void findView(){
        mRv = (RecyclerView) findViewById(R.id.id_rv);
        mScrollView = (TouchListenerScrollView) findViewById(R.id.id_touch_ll);
    }

    private void setRvSvListener(){
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if()
//                mRv.scrollTo();
                Log.d(TAG, "onScrolled: getTop----->"+recyclerView.getTop());
                Log.d(TAG, "onScrolled: dy----->"+dy);
            }
        });
    }

    private void initData(){
        mList = new ArrayList<>();
        for(int i=0;i<30;i++){
            mList.add("ITEM--->"+i);
        }
    }

    class RvAdapter extends CommonRvAdapter<String> {

        public RvAdapter(Context context, List<String> dataList, int layoutId) {
            super(context, dataList, layoutId);
        }

        @Override
        public void bindData(CommonViewHolder holder, String data) {
            holder.setText(R.id.id_tv_test, data);
        }
    }
}
