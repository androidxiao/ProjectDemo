package cn.custom.widget.activity;

/**
 * Created by chawei on 2017/12/19.
 */

public class ScreenShott {

    /** c
     *
     * 重写的 xrecycleview 为了防止 recycleview 的滑动和对 recycleview 手势的 冲突
     *
     */
//    public class MyRecycleView extends XRecyclerView{
//        public MyRecycleView(Context context) {
//            super(context);
//        }
//
//
//        public MyRecycleView(Context context, AttributeSet attrs) {
//            super(context, attrs);
//        }
//
//
//        public MyRecycleView(Context context, AttributeSet attrs, int defStyle) {
//            super(context, attrs, defStyle);
//        }
//
//
//        // 滑动距离及坐标
//        private float xDistance, yDistance, xLast, yLast;
//
//
//        @Override
//        public boolean onInterceptTouchEvent(MotionEvent ev) {
//// TODO Auto-generated method stub
//            switch (ev.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    xDistance = yDistance = 0f;
//                    xLast = ev.getX();
//                    yLast = ev.getY();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    final float curX = ev.getX();
//                    final float curY = ev.getY();
//
//
//                    xDistance += Math.abs(curX - xLast);
//                    yDistance += Math.abs(curY - yLast);
//                    xLast = curX;
//                    yLast = curY;
//
//
//                    if(xDistance > yDistance){
//                        return false;
//                    }
//            }
//
//
//            return super.onInterceptTouchEvent(ev);
//        }
//    }
}
