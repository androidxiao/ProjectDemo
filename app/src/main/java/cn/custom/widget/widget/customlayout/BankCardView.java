package cn.custom.widget.widget.customlayout;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.DecimalFormat;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/21.
 */

public class BankCardView extends ConstraintLayout {

    public static final String TAG = "gear2";

    private ConstraintLayout mCtltBank;
    private ConstraintLayout mBankReverse;
    private EditText mEtBankNumReverse;

    public BankCardView(Context context) {
        this(context, null);
    }

    public BankCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BankCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bank_card_layout, this, true);
        mCtltBank = (ConstraintLayout) view.findViewById(R.id.id_ctlt_bank);
        mBankReverse = (ConstraintLayout) findViewById(R.id.id_ctlt_bank_reverse);
        mEtBankNumReverse = (EditText) findViewById(R.id.id_tv_bank_num_reverse);

        mBankReverse.setRotationX(180);
        mBankReverse.setAlpha(0);

        etTextWatcher();
    }


    public void etTextWatcher(){
        mEtBankNumReverse.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEtBankNumReverse.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    mEtBankNumReverse.setText(str);
                    Editable etable = mEtBankNumReverse.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }

        });
    }

    public void rotate180() {


        ObjectAnimator rotationXReverse = ObjectAnimator.ofFloat(mBankReverse, "rotationX", 180, 0);
        ObjectAnimator alphaAnimShow = ObjectAnimator.ofFloat(mBankReverse, "alpha", 0, 1.0f);

        final AnimatorSet animatorSetReverse = new AnimatorSet();
        animatorSetReverse.playTogether(rotationXReverse, alphaAnimShow);
        animatorSetReverse.setDuration(1000);


        ObjectAnimator rotationXAnim = ObjectAnimator.ofFloat(mCtltBank, "rotationX", 360, 180);
        ObjectAnimator alphaAnimFade = ObjectAnimator.ofFloat(mCtltBank, "alpha", 1.0f, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotationXAnim, alphaAnimFade);
        animatorSet.play(animatorSetReverse);
        animatorSet.setDuration(2000);
        animatorSet.start();

        final DecimalFormat decimalFormat = new DecimalFormat("0.0");
//        final ObjectAnimator gradientBg = ObjectAnimator.ofInt(mBankReverse, "backgroundColor", R.color.c_2879D7);
//        gradientBg.setDuration(1000);
//        gradientBg.setEvaluator(new ArgbEvaluator());

        final AnimationDrawable animationDrawable= (AnimationDrawable) mBankReverse.getBackground();
        alphaAnimShow.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float format = Float.parseFloat(decimalFormat.format((float) animation.getAnimatedValue()));
                if (format == 0.5) {
                    animationDrawable.setEnterFadeDuration(1000);
                    animationDrawable.setExitFadeDuration(500);

                    animationDrawable.start();
//                    gradientBg.start();
                }else if(format==1.0){
                    animationDrawable.stop();
                }
            }
        });

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showSoftInputFromWindow(mEtBankNumReverse);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private Activity mActivity;
    public void setActivity(Activity activity){
        mActivity=activity;
    }


    /**
     * EditText获取焦点并显示软键盘
     */
    public  void showSoftInputFromWindow( EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 0);
    }

}
