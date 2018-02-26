package cn.cn.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.cn.fragment.TopFragment;
import cn.cn.fragment.BottomFragment;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/2/6.
 */

public class ViewDragHelperDemo1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_helper1_layout);


        TopFragment firstFragment = new TopFragment();
        BottomFragment secondFragment = new BottomFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.top_fragment_view, firstFragment)
                .add(R.id.bottom_fragment_view, secondFragment).commit();
    }
}
