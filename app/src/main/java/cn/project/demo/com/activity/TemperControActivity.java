package cn.project.demo.com.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.project.demo.com.R;


/**
 * Created by zjp on 2017/9/16 15:15.
 * 温度控制器
 */

public class TemperControActivity extends AppCompatActivity {

    Toolbar mToolbar;

    TextView mSaveResie;

    ImageView mRestartIv;

    ImageView mDown_iv;

    TextView mAuto_tv_work;

    ImageView mIvzhilen;

    ImageView mInline;

    TextView mTargetTemp;

    TextView mZuiditxt;

    TextView mZuigaotxt;

    AppBarLayout mAppbar;

    NestedScrollView mSetLayout_ll;

    RelativeLayout mSetShowRl;

    RelativeLayout mRestartRl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tempcontro_layout);
        findView();
        initView();
    }

    private void findView(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar1);
        mSaveResie = (TextView) findViewById(R.id.save_revise);
        mRestartIv = (ImageView) findViewById(R.id.restart_iv);
        mDown_iv = (ImageView) findViewById(R.id.down_iv);
        mAuto_tv_work = (TextView) findViewById(R.id.auto_tv_work);
        mIvzhilen = (ImageView) findViewById(R.id.iv_zhilen);
        mInline = (ImageView) findViewById(R.id.inline);
        mTargetTemp = (TextView) findViewById(R.id.target_temp);
        mZuiditxt = (TextView) findViewById(R.id.zuidi_txt);
        mZuigaotxt = (TextView) findViewById(R.id.zuigao_txt);
        mAppbar = (AppBarLayout) findViewById(R.id.appbar);
        mSetLayout_ll = (NestedScrollView) findViewById(R.id.setll);
        mSetShowRl = (RelativeLayout) findViewById(R.id.set_show_rl);
        mRestartRl = (RelativeLayout) findViewById(R.id.restart_rl);
    }
    private void initView() {
//        mToolbar.setTitle(mToolbarTitle);
        setSupportActionBar(mToolbar);

//        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mAppbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                if (state == State.EXPANDED) {
                    mSetLayout_ll.setAlpha(0f);
                    mSetShowRl.setAlpha(0f);
                    mRestartRl.setAlpha(1f);
                } else if (state == State.COLLAPSED) {
                    mSetLayout_ll.setAlpha(1.0f);
                    mSetShowRl.setAlpha(1f);
                    mRestartRl.setAlpha(0f);
                } else {
                    mSetLayout_ll.setAlpha(0.5f);
                    mSetShowRl.setAlpha(0.5f);
                    mRestartRl.setAlpha(0.5f);
                }
            }
        });
    }

    public abstract static class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

        public enum State {
            EXPANDED,
            COLLAPSED,
            IDLE
        }

        private State mCurrentState = State.IDLE;

        @Override
        public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            if (i == 0) {
                if (mCurrentState != State.EXPANDED) {
                    onStateChanged(appBarLayout, State.EXPANDED);
                }
                mCurrentState = State.EXPANDED;
            } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                if (mCurrentState != State.COLLAPSED) {
                    onStateChanged(appBarLayout, State.COLLAPSED);
                }
                mCurrentState = State.COLLAPSED;
            } else {
                if (mCurrentState != State.IDLE) {
                    onStateChanged(appBarLayout, State.IDLE);
                }
                mCurrentState = State.IDLE;
            }
        }

        public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
    }
}
