package cn.custom.widget.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.cn.retrofit.demo.com.utils.ScreenUtil;
import cn.custom.widget.model.MenuItem;
import cn.custom.widget.widget.customlayout.MenuPop;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/21.
 */

public class RecyclerViewShowPopActivity extends AppCompatActivity {

    public static final String TAG = "gear2";

    private RecyclerView mRecyclerView;
    private GestureDetector mGestureDetector;
    private MotionEvent mEvent;
    private MenuPop mMenuPop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_show_pop_layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(new RecyclerAdapter());

    }

    public void showPopView(View anchorView, int x, int y) {
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem menuItem = new MenuItem();
        menuItem.setContent("收藏");
        menuItems.add(menuItem);
        menuItem = new MenuItem();
        menuItem.setContent("分享");
        menuItems.add(menuItem);
        menuItem = new MenuItem();
        menuItem.setContent("删除");
        menuItems.add(menuItem);
        mMenuPop = new MenuPop(this, menuItems);
        if (!mMenuPop.isShowing()) {
            calculateDisChooosePopMethod(anchorView, x, y);
        }
    }

    private void calculateDisChooosePopMethod(View anchorView, int rawX, int rawY) {
        int[] pos = calculatePopWindowPos(anchorView, mMenuPop.getContentView(), rawX, rawY);
        int popHeight = mMenuPop.getPopHeight();
        int popWidth = mMenuPop.getPopWidth();
        int screenWidth = ScreenUtil.getScreenWidth();
        int screenHeight = ScreenUtil.getScreenHeight();
        boolean isRigthTop = (screenWidth - rawX) <= popWidth;
        boolean isLeftBot = (screenHeight - rawY) <= popHeight;
        if (isLeftBot && isRigthTop) {
            int marginBot = screenHeight - rawY;
//            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMenuPop.getContentView().getLayoutParams();
            mMenuPop.setAnimationStyle(R.style.PopAnimRigthBot);
            mMenuPop.showAtLocation(anchorView, Gravity.NO_GRAVITY, pos[0], pos[1]);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mMenuPop.getLl().getLayoutParams();
            mMenuPop.getPop().setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 20, marginBot);
//            mMenuPop.getContentView().setLayoutParams(params);
            mMenuPop.getLl().setLayoutParams(params);
        } else if (isLeftBot) {
            int marginBot = screenHeight - rawY;
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mMenuPop.getContentView().getLayoutParams();
            params.setMargins(0, 0, 0, marginBot);
            mMenuPop.getContentView().setLayoutParams(params);
            mMenuPop.setAnimationStyle(R.style.PopAnimLeftBot);
            mMenuPop.showAtLocation(anchorView, Gravity.NO_GRAVITY, pos[0], pos[1]);
        } else if (isRigthTop) {
            mMenuPop.setAnimationStyle(R.style.PopAnimRigthTop);
            mMenuPop.showAtLocation(anchorView, Gravity.NO_GRAVITY, pos[0], pos[1]);
        } else {
            mMenuPop.showAtLocation(anchorView, Gravity.NO_GRAVITY, pos[0], pos[1]);
        }
    }


    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {


        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerHolder(LayoutInflater.from(RecyclerViewShowPopActivity.this).inflate(R.layout.item_textview_view, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, int position) {
            holder.tvItem.setText("我是测试用的数据哦~~~~！");
        }

        @Override
        public int getItemCount() {
            return 20;
        }


        public class RecyclerHolder extends RecyclerView.ViewHolder {

            TextView tvItem;

            public RecyclerHolder(final View itemView) {
                super(itemView);
                tvItem = (TextView) itemView.findViewById(R.id.id_tv_test);

                itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        mGestureDetector.onTouchEvent(event);
                        return true;

                    }
                });


                mGestureDetector = new GestureDetector(RecyclerViewShowPopActivity.this, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
//                        Log.d(TAG, "onLongPress: --X-->" + e.getRawX() + "   --Y--->" + e.getRawY());
                        showPopView(itemView, (int) e.getRawX(), (int) e.getRawY());

                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        Toast.makeText(RecyclerViewShowPopActivity.this, "点击了啊", Toast.LENGTH_SHORT).show();
                        return super.onSingleTapConfirmed(e);
                    }

                });

            }
        }
    }

    public int[] calculatePopWindowPos(final View anchorView, final View contentView, int rawX, int rawY) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtil.getScreenHeight();
        final int screenWidth = ScreenUtil.getScreenWidth();
        // 测量contentView
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
//        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        final boolean isNeedShowUp = (screenHeight - rawY - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            Log.d(TAG, "----->上 ");
//            windowPos[0] = screenWidth - windowWidth;
//            windowPos[1] = anchorLoc[1] - windowHeight;
            windowPos[0] = rawX;
//            windowPos[1] =rawY-windowHeight;
            windowPos[1] = rawY;
        } else {
//            windowPos[0] = screenWidth - windowWidth;
//            windowPos[1] = anchorLoc[1] + anchorHeight;
            Log.d(TAG, "----->下 ");
            windowPos[0] = rawX;
//            windowPos[1] = rawY-windowHeight;
            windowPos[1] = rawY;
        }
        return windowPos;
    }

}
