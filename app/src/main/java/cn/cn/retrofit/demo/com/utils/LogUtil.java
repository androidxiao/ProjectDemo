package cn.cn.retrofit.demo.com.utils;

import android.util.Log;


/**
 * 日志打印管理类
 */
public class LogUtil {

    private static final String TAG = "RetrofitDemo";
    private static final boolean DEBUG=true;
    private LogUtil(){}

    public static void debug(String msg) {
        if(DEBUG)
            Log.d(TAG, msg);
    }

    public static void info(String msg) {
        if(DEBUG)
            Log.i(TAG, msg);
    }

}
