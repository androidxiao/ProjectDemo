package cn.custom.widget.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by chawei on 2017/9/25.
 *
 * 清除光标的EditText
 */

public class ClearFocusEditText extends android.support.v7.widget.AppCompatEditText{


    public ClearFocusEditText(Context context) {
        super(context);
        init(null);
    }

    public ClearFocusEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ClearFocusEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        handleActionBtnClick();
    }

    private void handleActionBtnClick(){
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                ((InputMethodManager) v.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        v.getWindowToken(), 0);
                clearFocus();
                return false;
            }
        });
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clearFocus();
        }
        return super.onKeyPreIme(keyCode, event);
    }
}
