package cn.custom.widget.widget.customlayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.custom.widget.model.MenuItem;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/21.
 */

public class MenuPop extends PopupWindow {

    private Context mContext;
    private List<MenuItem>mMenuItems;
    private final RecyclerView mRecyclerView;
    private final LinearLayout mLl;
    private final View mView;

    public MenuPop(Activity context, List<MenuItem>menuItems){
        mMenuItems=menuItems;
        mContext=context;
        mView = LayoutInflater.from(context).inflate(R.layout.pop_layout, null);
        setContentView(mView);
        mLl = (LinearLayout) mView.findViewById(R.id.ll_pop);
        mRecyclerView= (RecyclerView) mView.findViewById(R.id.id_pop_rv);
        int width = ScreenUtil.getScreenWidth();
        int height = ScreenUtil.getScreenHeight();
        setWidth(1);
        setHeight(1);
        setWindowLayoutMode(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        ColorDrawable colorDrawable = new ColorDrawable();
        setBackgroundDrawable(colorDrawable);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter=new RecyclerAdapter();
        mRecyclerView.setAdapter(adapter);


        setAnimationStyle(R.style.PopAnimLeftTop);
    }


    public LinearLayout getLl(){
        return mLl;
    }

    public PopupWindow getPop(){
        return this;
    }

    public RecyclerView getContentView(){
        return mRecyclerView;
    }

    public int getPopHeight(){
        return mRecyclerView.getMeasuredHeight();
    }

    public int getPopWidth(){
        return mRecyclerView.getMeasuredWidth();
    }



    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {


        @Override
        public RecyclerAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerAdapter.RecyclerHolder(LayoutInflater.from(mContext).inflate(R.layout.item_textview_view,parent,false));
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.RecyclerHolder holder, int position) {
            holder.tvItem.setText(mMenuItems.get(position).getContent());
        }

        @Override
        public int getItemCount() {
            return mMenuItems.size();
        }

        public class RecyclerHolder extends RecyclerView.ViewHolder {

            TextView tvItem;

            public RecyclerHolder(View itemView) {
                super(itemView);
                tvItem = (TextView) itemView.findViewById(R.id.id_tv_test);
                tvItem.setBackgroundColor(mContext.getResources().getColor(R.color.c_3ec88e));

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "点我干嘛啊", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}
