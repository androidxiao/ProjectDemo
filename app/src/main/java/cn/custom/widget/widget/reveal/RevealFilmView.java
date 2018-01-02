package cn.custom.widget.widget.reveal;

import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/13.
 */

public class RevealFilmView extends FrameLayout {

    public static final String TAG = "gear2";

    private SpiderView mSpiderView;
    private LronView mLronView;
    private CaptainAmericaView mCaptainAmericaView;
    private BatManView mBatManView;
    private FrameLayout mFrameLayout;

    public RevealFilmView(@NonNull Context context) {
        this(context, null);
    }

    public RevealFilmView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealFilmView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.reveal_frame_layout, this);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.id_frame);
        mSpiderView = new SpiderView(getContext());
        mLronView = new LronView(getContext());
        mCaptainAmericaView = new CaptainAmericaView(getContext());
        mBatManView = new BatManView(getContext());
        mLronView.setVisibility(View.GONE);
        mCaptainAmericaView.setVisibility(View.GONE);
        mBatManView.setVisibility(View.GONE);
        mFrameLayout.addView(mSpiderView);
        mFrameLayout.addView(mLronView);
        mFrameLayout.addView(mCaptainAmericaView);
        mFrameLayout.addView(mBatManView);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startAnimator() {

//        mSpiderView.startReveal();
//
//        mSpiderView.getReveal().addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (mLronView.getVisibility() == View.GONE)
//                    mLronView.setVisibility(View.VISIBLE);
//                mLronView.startReveal();
//                Log.d(TAG, "onAnimationEnd: --->mSpiderView");
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//
//        mLronView.getReveal().addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (mCaptainAmericaView.getVisibility() == View.GONE)
//                    mCaptainAmericaView.setVisibility(View.VISIBLE);
//                mCaptainAmericaView.startReveal();
//                Log.d(TAG, "onAnimationEnd: --->mLronView");
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//        mCaptainAmericaView.getReveal().addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (mBatManView.getVisibility() == View.GONE)
//                    mBatManView.setVisibility(View.VISIBLE);
//                mBatManView.startReveal();
//                Log.d(TAG, "onAnimationEnd: --->mCaptainAmericaView");
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//        mBatManView.getReveal().addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                mSpiderView.startReveal();
//                Log.d(TAG, "onAnimationEnd: --->mBatManView");
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(mSpiderView.getReveal(),mLronView.getReveal(),mCaptainAmericaView.getReveal(),mBatManView.getReveal());
        animatorSet.start();

    }


}
