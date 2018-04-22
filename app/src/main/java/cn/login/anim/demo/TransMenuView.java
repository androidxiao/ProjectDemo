package cn.login.anim.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/4/21.
 */

public class TransMenuView extends LinearLayout implements View.OnClickListener {
    public TransMenuView(Context context) {
        this(context, null);
    }

    public TransMenuView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.trans_menu_view, this, true);
        ImageView iv1 = (ImageView) view.findViewById(R.id.id_iv1);
        ImageView iv2 = (ImageView) view.findViewById(R.id.id_iv2);
        ImageView iv3 = (ImageView) view.findViewById(R.id.id_iv3);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_iv1:
                if (mListener != null) {
                    mListener.ivClick(0);
                }
                break;
            case R.id.id_iv2:
                if (mListener != null) {
                    mListener.ivClick(1);
                }
                break;
            case R.id.id_iv3:
                if (mListener != null) {
                    mListener.ivClick(2);
                }
                break;
        }
    }

    private OnIvClickListener mListener;

    public void setListener(OnIvClickListener listener) {
        mListener = listener;
    }

    public interface OnIvClickListener {
        void ivClick(int position);
    }

    private boolean isShow;

    public void setTranslationX(final TransMenuView transMenuView,Button btn) {
        transMenuView.setVisibility(VISIBLE);
        if (!isShow) {
            come(btn);
        } else {
            back(btn);
        }

    }

    private void come(final Button btn) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationX", ScreenUtil.getScreenWidth(), 0);
        objectAnimator.setDuration(getResources().getInteger(R.integer.anim_short));
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isShow = true;
                btn.setEnabled(true);
            }
        });
    }

    private void back(final Button btn) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationX", 0, ScreenUtil.getScreenWidth());
        objectAnimator.setDuration(getResources().getInteger(R.integer.anim_short));
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isShow = false;
                btn.setEnabled(true);
            }
        });
    }

    public void comeBtn(Button btn) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(btn, "translationX", 0, ScreenUtil.getScreenWidth());
        objectAnimator.setDuration(getResources().getInteger(R.integer.anim_short));
        objectAnimator.start();
    }

    public void backBtn(Button btn) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(btn, "translationX", ScreenUtil.getScreenWidth(), 0);
        objectAnimator.setDuration(getResources().getInteger(R.integer.anim_short));
        objectAnimator.start();

    }
}
