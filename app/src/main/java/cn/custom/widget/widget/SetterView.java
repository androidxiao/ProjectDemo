package cn.custom.widget.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/9.
 *
 * 利用databinding 自定义setter 创建组合控件
 */

public class SetterView extends ConstraintLayout {


    private ImageView mIvIcon;
    private TextView mTvTitle;
    private TextView mTvHint;
    private ImageView mIvArrow;
    private View mLine1;
    private View mLine2;

    public SetterView(Context context) {
        this(context, null);
    }

    public SetterView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SetterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.setting_view_layout, this);
        mIvIcon = (ImageView) view.findViewById(R.id.id_iv_icon);
        mTvTitle = (TextView) view.findViewById(R.id.id_tv_title);
        mTvHint = (TextView) view.findViewById(R.id.id_tv_hint);
        mIvArrow = (ImageView) view.findViewById(R.id.id_iv_arrow);
        mLine1 = view.findViewById(R.id.id_view_line1);
        mLine2 = view.findViewById(R.id.id_view_line2);
    }

    public void setIcon(Drawable resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mIvIcon.setBackground(resId);
        }else{
            mIvIcon.setBackgroundDrawable(resId);
        }
    }

    public void setTitle(String title){
        mTvTitle.setText(title);
    }

    public void setHint(String hint){
        Log.d("ez", "setHint: --->"+hint);
        mTvHint.setText(hint);
    }

    public void setArrowVisible(Drawable resId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mIvArrow.setBackground(resId);
        }else{
            mIvArrow.setBackgroundDrawable(resId);
        }
    }


    /**
     * 有margin的line
     * @param colorId
     */
    public void setLine2Visible(int colorId){
        mLine1.setVisibility(View.GONE);
        mLine2.setBackgroundColor(colorId);
    }

    /**
     * match_parent的line
     * @param colorId
     */
    public void setLine1Visible(int colorId){
        mLine1.setBackgroundColor(colorId);
        mLine2.setVisibility(View.GONE);
    }
}
