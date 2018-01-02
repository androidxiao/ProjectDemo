package cn.custom.widget.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import cn.custom.widget.widget.textviewanim.LetterView;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/5.
 */

public class LetterViewActivity extends AppCompatActivity {

    private TextView mTv;
    private LetterView mLetterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_letter_view_layout);

        init();

        mLetterView.setSelectLetterListener(new LetterView.ISelectLetterListener() {
            @Override
            public void onSelectLetter(String selectLetter) {
                mTv.setVisibility(View.VISIBLE);
                mTv.setText(selectLetter);
            }

            @Override
            public void onTouchActionUp() {
                mTv.setVisibility(View.GONE);
            }
        });

    }

    private void init(){
        mTv = (TextView) findViewById(R.id.id_tv_select_letter);
        mLetterView = (LetterView) findViewById(R.id.id_letter_view);
    }
}
