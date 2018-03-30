package cn.login.anim.demo;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/17.
 */

public class LoginMainActivity extends AppCompatActivity {

    private boolean isLogin = true;
    private Fragment[] mFragments;
    private Fragment mLastFragment;
    private RelativeLayout mRl;
    private Button mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main_layout);
        findView();
    }

    private void findView() {
        mRl = (RelativeLayout) findViewById(R.id.id_rl);
        mBtn = (Button) findViewById(R.id.id_btn);
        mFragments = new Fragment[]{new LoginInFragment(), new SignUpFragment()};
        switchLogin();
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogin();
            }
        });
    }

    private void btnTranslateLeft(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBtn, "translationX",-Px2DpUtil.dp2px(this,100) , Px2DpUtil.dp2px(this,20));
        objectAnimator.setDuration(getResources().getInteger(R.integer.anim_short));
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.start();
    }


    private void btnTranslateRight(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBtn, "translationX", ScreenUtil.getScreenWidth(), ScreenUtil.getScreenWidth()-Px2DpUtil.dp2px(this,120));
        objectAnimator.setDuration(getResources().getInteger(R.integer.anim_short));
        objectAnimator.setInterpolator(new BounceInterpolator());
        objectAnimator.start();
    }

    private void animColor(int colorTo) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), R.color.white, colorTo);
        colorAnimation.setDuration(getResources().getInteger(R.integer.anim_short));
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int color = (int) animator.getAnimatedValue();
                mRl.setBackgroundColor(color);
            }
        });
        colorAnimation.start();
    }


    private void switchLogin() {
        isLogin = true;
        switchFragment(mFragments[0], R.anim.rotate_fg_enter_left, R.anim.rotate_fg_exit_left);
        animColor(ContextCompat.getColor(this, R.color.c_499AF7));
        btnTranslateLeft();
        mBtn.setText("去注册");
    }

    private void switchSignUp(){
        isLogin = false;
        switchFragment(mFragments[1], R.anim.rotate_fg_enter_right, R.anim.rotate_fg_exit_right);
        animColor(ContextCompat.getColor(this, R.color.c_3ec88e));
        btnTranslateRight();
        mBtn.setText("去登录");
    }

    private void isLogin() {
        if (isLogin) {
            switchSignUp();
        } else {
            switchLogin();
        }
    }


    /**
     * Fragment切换
     *
     * @param fragment
     */
    public void switchFragment(Fragment fragment, int enter, int exit) {
        try {
            if (fragment == null) {
                fragment = mFragments[0];
            }
            if (fragment.equals(mLastFragment)) {
                return;
            }

            FragmentManager mFragmentManager = getSupportFragmentManager();
            FragmentTransaction mTransaction = mFragmentManager.beginTransaction();
            mTransaction.setCustomAnimations(enter, exit);

            if (mLastFragment != null) {
                mTransaction.hide(mLastFragment);
            }
            if (!fragment.isAdded()) {
                mTransaction.add(R.id.id_fl_login_in, fragment);
            } else {
                mTransaction.show(fragment);
            }
            mTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mLastFragment = fragment;
        }
    }
}
