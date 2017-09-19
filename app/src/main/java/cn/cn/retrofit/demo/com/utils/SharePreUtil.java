package cn.cn.retrofit.demo.com.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cn.ProjectApp;

/**
 * SharePreference文件管理的工具类
 */
public class SharePreUtil {

    private static final SharedPreferences mSharePre;
    private  static final SharedPreferences.Editor mEditor;

    static {
        mSharePre = ProjectApp.getApp().getSharedPreferences("Global", Context.MODE_PRIVATE);
        mEditor = mSharePre.edit();
    }

    private SharePreUtil() {}

    public static void saveString(String key, String value) {
        mEditor.putString(key,value);
        mEditor.commit();
    }

    public static String getStringValue(String key, String defaultValue) {
        return mSharePre.getString(key, defaultValue);
    }

    public static String getStringValue(String key) {
        return getStringValue(key, "");
    }

    public static void saveBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public static boolean getBooleanValue(String key) {
        return mSharePre.getBoolean(key, false);
    }

    public static void saveInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public static int getIntValue(String key) {
        return mSharePre.getInt(key, 0);
    }

    public static void saveFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public static float getFloatValue(String key) {
        return mSharePre.getFloat(key, 0f);
    }

    public static void removeValue(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public static void removeAll() {
        mEditor.clear();
        mEditor.commit();
    }

}
