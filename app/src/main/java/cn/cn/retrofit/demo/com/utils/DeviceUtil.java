package cn.cn.retrofit.demo.com.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 设备工具类
 */
public class DeviceUtil {

    private DeviceUtil() {}

    private static final int NETWORKTYPE_INVALID = 0;
    private static final int NETWORKTYPE_WAP = 1;
    private static final int NETWORKTYPE_2G = 2;
    private static final int NETWORKTYPE_3G = 3;
    private static final int NETWORKTYPE_WIFI = 4;
    private static int mNetWorkType;
    private static String netWorkType;

    /**
     * 判断网络是否可用
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 得到当前网络环境的类型
     * @param context
     * @return
     */
    public static String getNetWorkType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G
                        : NETWORKTYPE_2G)
                        : NETWORKTYPE_WAP;
            }
        } else {
            mNetWorkType = NETWORKTYPE_INVALID;
        }
        if (mNetWorkType == 1) {
            netWorkType = "WAP";
        } else if (mNetWorkType == 2) {
            netWorkType = "2G";
        } else if (mNetWorkType == 3) {
            netWorkType = "3G";
        } else if (mNetWorkType == 4) {
            netWorkType = "WIFI";
        } else {
            netWorkType = "INVALID";
        }

        return netWorkType;
    }

    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false;
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false;
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false;
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true;
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false;
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true;
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true;
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true;
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true;
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值,或者异常),则返回值为空
     */
    public static String getAppMetaData(Context mContext, String key) {
        String resultData = null;
        if (mContext == null || TextUtils.isEmpty(key)) {
            return resultData;
        }
        try {
            PackageManager packageManager = mContext.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager
                        .getApplicationInfo(mContext.getPackageName(),
                                PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData;
    }

    /**
     * 调用系统拨号页面
     * @param phone
     */
    public static void dial(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
        if(null != intent.resolveActivity(context.getPackageManager())) {
            context.startActivity(intent);
        }
    }

    /**
     * 　　* 获取版本号
     * 　　* @return 当前应用的版本号
     *
     */
    public static String getVersion(Context context){
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
             info = manager.getPackageInfo(context.getPackageName(), 0);
             String version = info.versionName;
             return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }

}
