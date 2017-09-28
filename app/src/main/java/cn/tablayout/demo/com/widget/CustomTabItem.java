package cn.tablayout.demo.com.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import cn.cn.retrofit.demo.com.utils.DensityUtils;
import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.project.demo.com.R;

import static cn.project.demo.com.R.drawable.bg_tv_selector;

/**
 * Created by chawei on 2017/9/27.
 *
 * 自定义TabLayout中的TabItem（这只是一种形式，其他需求可依据这个修改）
 */

public class CustomTabItem {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Context mContext;

    public static CustomTabItem create(){
        return TabItemHolder.sCustomTabItem;
    }

    private static class TabItemHolder{
        private static CustomTabItem sCustomTabItem=new CustomTabItem();
    }

    public CustomTabItem setContext(Context context){
        mContext=context;
        return this;
    }

    public CustomTabItem setTabLayout(TabLayout tabLayout){
        mTabLayout=tabLayout;
        return this;
    }

    public CustomTabItem setViewPager(ViewPager viewPager){
        mViewPager=viewPager;
        return this;
    }

    /**
     * 修改滑块长度需要在build()之后
     * @param lineLength (dp)
     * @return
     */
    public CustomTabItem setIndicator(int lineLength){
        lineLength=DensityUtils.dip2px(mContext,lineLength);
        int widthDp =ScreenUtil.getScreenWidth();
        Log.d("ez", "lineLength---->" + lineLength);
        Log.d("ez", "widthDp---->" + widthDp);
        int finalWidth = (widthDp / 3 - lineLength) / 2;
//        finalWidth=DensityUtils.px2dip(mContext, finalWidth);
        setIndicator(mContext,mTabLayout,finalWidth,finalWidth);
        return this;
    }

    public CustomTabItem build(){
        initTabLayout();
        return this;
    }


    private void initTabLayout(){
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setCustomView(getTabView(0));
        mTabLayout.getTabAt(1).setCustomView(getTabView(1));
        mTabLayout.getTabAt(2).setCustomView(getTabView(2));
        tabSelectListener();
    }


    private View getTabView(final int position){
        View view = LayoutInflater.from(mContext).inflate(R.layout.tab_item_text_img_layout, null);
        TextView tvTitle= (TextView) view.findViewById(R.id.id_tv_title);
        final ImageView ivTitle = (ImageView) view.findViewById(R.id.id_iv_title);
        ivTitle.setImageResource(R.mipmap.below_arrow_icon);
        tvTitle.setText("标题");
        if (position == 0) {
            tvTitle.setTextColor(Color.parseColor("#057523"));
            view.setBackgroundResource(bg_tv_selector);
            startPropertyAnim(ivTitle);
        }else{
            view.setBackgroundDrawable(null);
            ivTitle.setVisibility(View.INVISIBLE);
            tvTitle.setTextColor(Color.parseColor("#ced0d3"));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPropertyAnim(ivTitle);
                mViewPager.setCurrentItem(position);
            }
        });
        return view;
    }

    private void tabSelectListener(){
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTAbStatus(tab,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTAbStatus(tab,false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void changeTAbStatus(TabLayout.Tab tab,boolean selected){
        View view = tab.getCustomView();
        if (view == null) {
            return;
        }
        final ImageView ivTitle = (ImageView) view.findViewById(R.id.id_iv_title);
        TextView tvTitle= (TextView) view.findViewById(R.id.id_tv_title);
        ivTitle.setVisibility(View.VISIBLE);
        if (selected) {
            startPropertyAnim(ivTitle);
            view.setBackgroundResource(bg_tv_selector);
            tvTitle.setTextColor(Color.parseColor("#0ea73c"));
        }else{
            view.setBackgroundDrawable(null);
            tvTitle.setTextColor(Color.parseColor("#7f7f7f"));
            ivTitle.setVisibility(View.INVISIBLE);
        }
    }

    private void startPropertyAnim(ImageView view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", 180f, -180f);
        animator.setDuration(500);
        animator.start();
    }

    /**
     * 设置TabLayout的滑块的左右间距
     * @param context
     * @param tabs
     * @param leftDp
     * @param rightDp
     */
    private void setIndicator(Context context, TabLayout tabs, int leftDp, int rightDp) {
        Class<?> tabLayout=tabs.getClass();
        Field tabStrip=null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout ll_tab=null;
        try {
            ll_tab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        for(int i=0;i<ll_tab.getChildCount();i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0,0,0,0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin=leftDp;
            params.rightMargin=rightDp;
            Log.d("ez", "setIndicator: "+leftDp);
            Log.d("ez", "setIndicator: "+rightDp);
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

}
