package cn.custom.widget.activity;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import cn.custom.widget.model.PieData;
import cn.custom.widget.widget.AnimatePieChartView;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/5/13.
 */

public class AnimatePieViewActivity extends AppCompatActivity implements View.OnClickListener{

    private float[] mPies;
    private AnimatePieChartView mAnimatePieChartView;
    private int mIndex=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_pie_view_layout);
        findView();
        mAnimatePieChartView = (AnimatePieChartView) findViewById(R.id.id_animate_pie_view);
        setPieData(mAnimatePieChartView);
    }

    private void findView(){
        Button btnPre= (Button) findViewById(R.id.id_btn_pre);
        Button btnNext= (Button) findViewById(R.id.id_btn_next);

        btnPre.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    private void setPieData(AnimatePieChartView animatePieChartView) {
        final TypedArray typedArray = getResources().obtainTypedArray(R.array.ring_colors);
        final int size = 5;
        final int length = typedArray.length();
        List<PieData> pieEntryList = new ArrayList<>();
        mPies = new float[]{20f, 30f, 10f, 50f, 15f};
        String[] positions=new String[]{"上单","打野","中单","下路","辅助"};
        int color ;
        for(int i = 0; i < size; i++) {
            if(i >= length) {
                color = typedArray.getColor(length - 1, 0);
            } else {
                color = typedArray.getColor(i, 0);
            }
            PieData pe = new PieData(mPies[i],positions[i] , color);

            pieEntryList.add(pe);
        }
        animatePieChartView.setPie(pieEntryList);
        if(size > 1) {
            animatePieChartView.onTouchPie(0);
        } else {
            animatePieChartView.invalidate();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_pre:
                if (mIndex <= 0) {
                    mIndex = mPies.length -1;
                } else {
                    mIndex = mIndex - 1;
                }
                circulation(mIndex);
                break;
            case R.id.id_btn_next:
                if (mIndex >= mPies.length - 1) {
                    mIndex = 0;
                } else {
                    mIndex = mIndex + 1;
                }
                circulation(mIndex);
                break;
        }
    }

    private void circulation(int index) {
        if(mPies.length > 1) {
            mAnimatePieChartView.onTouchPie(index);
        }
    }
}
