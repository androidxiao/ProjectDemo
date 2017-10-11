package cn.swipedeleterecyclerview.demo.cn.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.project.demo.com.R;
import cn.swipedeleterecyclerview.demo.cn.adapter.SwipeRecyclerAdapter;
import cn.swipedeleterecyclerview.demo.cn.model.ItemModel;
import cn.swipedeleterecyclerview.demo.cn.utils.RecyclerItemTouchHelper;

/**
 * Created by chawei on 2017/10/2.
 * <p>
 * recyclerView 滑动显示删除按钮
 */

public class SwipeRecyclerActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{


    private List<ItemModel> mItemModels;
    private RecyclerView mRecyclerView;
    private SwipeRecyclerAdapter mAdapter;
    private ConstraintLayout mConstraintLayout;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recyclerview_layout);

        findView();
        initData();
        initRecyclerView();
    }

    private void initRecyclerView(){


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new SwipeRecyclerAdapter(this);
        mAdapter.appendList(mItemModels);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback callback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(callback).attachToRecyclerView(mRecyclerView);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
//        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(mRecyclerView);
    }

    private void findView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycleview);
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.id_constraint_layout);
    }

    private void initData() {
        mItemModels = new ArrayList<>();
        for(int i=0;i<20;i++) {
            ItemModel itemModel = new ItemModel();
            itemModel.setResId(R.mipmap.ic_launcher);
            itemModel.setTitle("我是标题--->"+i);
            itemModel.setDesc("我是内容--->"+i);
            mItemModels.add(itemModel);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof SwipeRecyclerAdapter.SwipeHolder) {
            String title = mItemModels.get(viewHolder.getAdapterPosition()).getTitle();
            final ItemModel deleteItem = mItemModels.get(viewHolder.getAdapterPosition());
            final int deletePosition = viewHolder.getAdapterPosition();
            mAdapter.removeItem(deletePosition);
            Snackbar snackbar=Snackbar.make(mConstraintLayout,title+" will removed！",Snackbar.LENGTH_LONG);
            snackbar.setAction("撤销", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAdapter.restoreItem(deleteItem,deletePosition);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
