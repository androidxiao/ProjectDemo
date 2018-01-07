package cn.behavior.custom.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.behavior.custom.demo.CollapsingToolbarLayout.CollapsingToolbarLayoutActivity;
import cn.behavior.custom.demo.CollapsingToolbarLayout.CollapsingToolbarLayoutActivity2;
import cn.behavior.custom.demo.CollapsingToolbarLayout.CollapsingToolbarLayoutActivity3;
import cn.behavior.custom.demo.appbarlayout.AppBarEnterAlwaysActivity;
import cn.behavior.custom.demo.appbarlayout.AppBarEnterAlwaysCollapsedActivity;
import cn.behavior.custom.demo.appbarlayout.AppBarExitUntilCollapsedActivity;
import cn.behavior.custom.demo.appbarlayout.AppBarScrollActivity;
import cn.behavior.custom.demo.appbarlayout.AppBarSnapActivity;
import cn.behavior.custom.demo.behavior.activity.BottomSheetBehaviorActivity;
import cn.behavior.custom.demo.behavior.activity.BottomSheetDialogActivity;
import cn.behavior.custom.demo.behavior.activity.ScaleHeaderBehaviorActivity;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/3.
 */

public class AppbarLayoutMainActivity extends AppCompatActivity {

    private static final String TAG = "demo";
    private RecyclerView recyclerView;
    private List<Class> mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_bar_main_layout);

        findView();
        initData();
        setAdapter();

    }

    private void findView(){
        recyclerView = (RecyclerView) findViewById(R.id.id_recycleview);

    }

    private void initData(){
        mActivity = new ArrayList<>();
//        AppbarLayout详细用法  start
        mActivity.add(AppBarScrollActivity.class);
        mActivity.add(AppBarEnterAlwaysActivity.class);
        mActivity.add(AppBarEnterAlwaysCollapsedActivity.class);
        mActivity.add(AppBarExitUntilCollapsedActivity.class);
        mActivity.add(AppBarSnapActivity.class);
        mActivity.add(CollapsingToolbarLayoutActivity.class);
        mActivity.add(CollapsingToolbarLayoutActivity2.class);
        mActivity.add(CollapsingToolbarLayoutActivity3.class);
//        AppbarLayout详细用法  end

//        Behavior使用方法以及如何自定义Behavior  start
        mActivity.add(BottomSheetBehaviorActivity.class);
        mActivity.add(BottomSheetDialogActivity.class);
        mActivity.add(ScaleHeaderBehaviorActivity.class);

//        Behavior使用方法以及如何自定义Behavior  end

    }

    private void intentTo(Class clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    private void setAdapter(){
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHolder(getLayoutInflater().inflate(R.layout.item_textview_view, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                ViewHolder vh = (ViewHolder) holder;
                vh.text.setText(mActivity.get(position).getSimpleName());
                vh.text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intentTo(mActivity.get(position));
                    }
                });
            }

            @Override
            public int getItemCount() {
                return mActivity.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                TextView text;

                public ViewHolder(View itemView) {
                    super(itemView);

                    text = (TextView) itemView.findViewById(R.id.id_tv_test);
                }

            }
        });
    }
}
