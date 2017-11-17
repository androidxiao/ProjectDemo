package cn.custom.widget.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by chawei on 2017/11/17.
 */

public class PieProgressView extends View {

    public PieProgressView(Context context) {
        this(context,null);
    }

    public PieProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

    }
}
