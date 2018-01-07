package cn.behavior.custom.demo.behavior.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/4.
 */

public class BottomSheetBehaviorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_behavior_layout);

        View shareView = findViewById(R.id.share_view);
        //获取BottomSheetBehavior
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(shareView);

        //设置折叠时的高度
//        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //监听BottomSheetBehavior的状态变化
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        //下滑的时候是否可以隐藏
        bottomSheetBehavior.setHideable(true);
        /***
         * 1、STATE_EXPANDED 展开状态，显示完整布局。
         * 2、STATE_COLLAPSED 折叠状态，显示peekHeigth 的高度，如果peekHeight为0，则全部隐藏,与STATE_HIDDEN效果一样。
         * 3、STATE_DRAGGING  拖拽时的状态
         * 4、STATE_HIDDEN 隐藏时的状态
         * 5、STATE_SETTLING  释放时的状态
         */
        findViewById(R.id.btn_show_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断BottomSheet是否展开
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });
    }
}
