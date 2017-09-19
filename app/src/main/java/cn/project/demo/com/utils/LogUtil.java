package cn.project.demo.com.utils;

import android.util.Log;

import cn.project.demo.com.tools.Constants;

public class LogUtil {

    private static final String TAG = "RetrofitDemo";

    private LogUtil() {
    }

    public static void debug(String msg) {
        if (Constants.DEBUG)
            Log.d(TAG, msg);
    }

    public static void info(String msg) {
        if (Constants.DEBUG)
            Log.i(TAG, msg);
    }

    /**
     * 在终端中使用adb logcat打印服务器json数据，如果返回数据过大超过4000字节（4K）即会截断不显示
     * <p>
     * 原因：logcat在对于message的内存分配大概是4k左右.所以超过的内容都直接被丢弃;
     * <p>
     * 解决方案：切分超过4k的message，使用多个Log.i输出
     *
     * @param str
     */
    public static void showLimitLog(String str) {
        if (Constants.DEBUG) {
            if(str.length() > 4000) {
                for(int i=0;i<str.length();i+=4000){
                    if(i+4000<str.length())
                        LogUtil.debug("超出长度限制---->"+str.substring(i, i+4000));
                    else
                        LogUtil.debug("超出长度限制---->"+str.substring(i, str.length()));
                }
            } else
                LogUtil.debug("超出长度限制----->"+str);
        }

    }

}
