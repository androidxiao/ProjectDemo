package cn.tablayout.demo.com.widget;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/22.
 * <p>
 * 自定义TabLayout中的TabItem（这只是一种形式，其他需求可依据这个修改）
 */

public class CustomBotTabItem {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Context mContext;
    private final String[] mTitles = {"主页", "理财", "添加", "消息", "我的"};

    public static CustomBotTabItem create() {
        return TabItemHolder.sCustomTabItem;
    }

    private static class TabItemHolder {
        private static CustomBotTabItem sCustomTabItem = new CustomBotTabItem();
    }

    public CustomBotTabItem setContext(Context context) {
        mContext = context;
        return this;
    }

    public CustomBotTabItem setTabLayout(TabLayout tabLayout) {
        mTabLayout = tabLayout;
        return this;
    }

    public CustomBotTabItem setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        return this;
    }

    public CustomBotTabItem build() {
        initTabLayout();
        return this;
    }


    private void initTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setCustomView(getTabView(0, R.drawable.home_icon_selector));
        mTabLayout.getTabAt(1).setCustomView(getTabView(1, R.drawable.manage_icon_selector));
        mTabLayout.getTabAt(2).setCustomView(getTabView(2, R.drawable.add_icon_selector));
        mTabLayout.getTabAt(3).setCustomView(getTabView(3, R.drawable.find_icon_selector));
        mTabLayout.getTabAt(4).setCustomView(getTabView(4, R.drawable.money_icon_selector));
        tabSelectListener();
    }


    private View getTabView(final int position, int resId) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.bottom_tab_item, null);
        TextView tvTitle = (TextView) view.findViewById(R.id.id_tv_title);
        final ImageView ivTitle = (ImageView) view.findViewById(R.id.id_iv_title);
        ivTitle.setImageResource(resId);
        tvTitle.setText(mTitles[position]);
        if (position == 0) {
            tvTitle.setTextColor(Color.parseColor("#4192e3"));
        } else {
            tvTitle.setTextColor(Color.parseColor("#262a3b"));
        }

        view.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
//                mViewPager.setCurrentItem(position);
                scaleTab(view);
            }
        });
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scaleTab(View view) {
        StateListAnimator animator = AnimatorInflater.loadStateListAnimator(mContext, R.drawable.icon_scale);
        view.setStateListAnimator(animator);
    }

    private void tabSelectListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabStatus(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabStatus(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void changeTabStatus(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        if (view == null) {
            return;
        }
//        scaleTab(view);
        TextView tvTitle = (TextView) view.findViewById(R.id.id_tv_title);
        if (selected) {
            tvTitle.setTextColor(Color.parseColor("#4192e3"));
        } else {
            tvTitle.setTextColor(Color.parseColor("#262a3b"));
        }
    }

}
