package cn.custom.widget.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.custom.widget.widget.RecyclerGridDecoration2;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/10.
 */

public class RecyclerViewGridManagerActivity extends AppCompatActivity {


    private int mColumn;
    private RecyclerView mRv;
    private RecyclerView mRv1;
    private RecyclerView mRv2;
    List<Image> lists = new ArrayList<>();
    List<Image> lists1 = new ArrayList<>();
    List<Image> lists2 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_grid_manager_layout);
        mRv = (RecyclerView) findViewById(R.id.id_rv);
        mRv1 = (RecyclerView) findViewById(R.id.id_rv1);
        mRv2 = (RecyclerView) findViewById(R.id.id_rv2);

        int count = 6;

        for (int i = 0; i < count; i++) {
            Image image = new Image();
            image.setTitle("title" + i);
            image.setResId(R.drawable.ic_launcher);
            lists.add(image);
        }

        mColumn = 1;

        noramlBottom();

        count = 7;

        for (int i = 0; i < count; i++) {
            Image image = new Image();
            image.setTitle("title" + i);
            image.setResId(R.drawable.ic_launcher);
            lists1.add(image);
        }
        remainderNum();

        count = 8;

        for (int i = 0; i < count; i++) {
            Image image = new Image();
            image.setTitle("title" + i);
            image.setResId(R.drawable.ic_launcher);
            lists2.add(image);
        }
        remainderNum1();

    }

    //正常画bottomline
    private void noramlBottom() {
        final RecyclerGridDecoration2 lineService = new RecyclerGridDecoration2(this, mColumn, lists.size());
        GridLayoutManager managerService = new GridLayoutManager(this, mColumn);
        mRv.addItemDecoration(lineService);
        mRv.setLayoutManager(managerService);
        GridAdapter mGridAdapter = new GridAdapter(this);
        mGridAdapter.append(lists);
        mRv.setAdapter(mGridAdapter);
    }

    //有余数，bottomLine全屏
    private void remainderNum() {
        final RecyclerGridDecoration2 lineService = new RecyclerGridDecoration2(this, mColumn, lists1.size());
        lineService.isAllScreen(true);
        GridLayoutManager managerService = new GridLayoutManager(this, mColumn);
        mRv1.addItemDecoration(lineService);
        mRv1.setLayoutManager(managerService);
        GridAdapter mGridAdapter = new GridAdapter(this);
        mGridAdapter.append(lists1);
        mRv1.setAdapter(mGridAdapter);
    }

    //有余数，bottomLine不全屏
    private void remainderNum1() {
        final RecyclerGridDecoration2 lineService = new RecyclerGridDecoration2(this, mColumn, lists2.size());
        GridLayoutManager managerService = new GridLayoutManager(this, mColumn);
        mRv2.addItemDecoration(lineService);
        mRv2.setLayoutManager(managerService);
        GridAdapter mGridAdapter = new GridAdapter(this);
        mGridAdapter.append(lists2);
        mRv2.setAdapter(mGridAdapter);
    }


    class Image {
        private String title;
        private int resId;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }
    }

    class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridHolder> {

        private Context mContext;
        private List<Image> lists;

        public GridAdapter(Context context) {
            mContext = context;
        }

        public void append(List<Image> list) {
            lists = list;
            notifyDataSetChanged();
        }

        @Override
        public GridAdapter.GridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GridHolder(LayoutInflater.from(mContext).inflate(R.layout.recycler_grid_item, parent, false));
        }

        @Override
        public void onBindViewHolder(GridAdapter.GridHolder holder, int position) {
            holder.ivIcon.setImageResource(lists.get(position).getResId());
            holder.tvTitle.setText(lists.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return lists.size();
        }


        class GridHolder extends RecyclerView.ViewHolder {
            private ImageView ivIcon;
            private TextView tvTitle;

            public GridHolder(View itemView) {
                super(itemView);
                ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
                params.width = LinearLayout.LayoutParams.MATCH_PARENT;
                params.height = ScreenUtil.getScreenWidth() / mColumn;
                itemView.setLayoutParams(params);
            }
        }
    }


}
