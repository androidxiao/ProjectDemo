package cn.cn.retrofit.demo.com.tools;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.Collections;

import cn.ProjectApp;
import cn.cn.retrofit.demo.com.utils.DeviceUtil;
import cn.cn.retrofit.demo.com.utils.Md5Util;
import cn.cn.retrofit.demo.com.utils.SharePreUtil;
import cn.cn.retrofit.demo.com.utils.StringUtils;
import cn.project.demo.com.tools.Constants;
import cn.project.demo.com.utils.LogUtil;

/**
 * Created by chawei on 2017/9/14.
 *
 * Http 参数签名类
 */

public class HttpSign {

    /**
     * 请求参数和必传参数，一起加密
     * @param temp
     * @return
     */
    public static ArrayMap paramsMd5(ArrayMap<String, String> temp) {
        if (null == temp)  {
            temp = new ArrayMap<>();
        }
        //必传参数已拼接
        temp.put("client", "android");
        temp.put("version", Constants.VERSION);
        temp.put("cid", SharePreUtil.getStringValue("IMEI"));
        temp.put("channelId", SharePreUtil.getStringValue("channel_id", "anxindeli"));
        temp.put("network", DeviceUtil.getNetWorkType(ProjectApp.getApp()));
        for(int i=0;i<temp.size();i++) {
            LogUtil.debug("key-->"+temp.keyAt(i)+" |||value--->"+temp.valueAt(i));
        }
        ArrayList<String> tempList = new ArrayList<String>(temp.keySet());
        Collections.sort(tempList);

        StringBuffer buffer = new StringBuffer();
        for (String key : tempList) {
            buffer.append(temp.get(key));
        }
        temp.put("sign", Md5Util.digestMd5(buffer.toString() + Md5Util.decode()));
        return temp;
    }

    /**
     * 参数加密
     * 如果url不走接口（直接加载webview地址），需要在原url后面添加&sign=getSignStr()
     * 必传参数得到sign字符串
     * @ return
     */
    public static String getSignStr() {
        ArrayMap<String, String> temp =  new ArrayMap<String, String>();
        //必传参数已拼接
        temp.put("client", "android");
        temp.put("version", Constants.VERSION);
        temp.put("cid", SharePreUtil.getStringValue("IMEI"));
        temp.put("channelId",  SharePreUtil.getStringValue("channel_id", "anxindeli"));
        temp.put("network", DeviceUtil.getNetWorkType(ProjectApp.getApp()));
//        temp.put("uuCode", getUCode());

        ArrayList<String> tempList = new ArrayList<String>(temp.keySet());
        Collections.sort(tempList);

        StringBuffer buffer = new StringBuffer();
        for (String key : tempList) {
            buffer.append(temp.get(key));
        }
        return Md5Util.digestMd5(buffer.toString() + StringUtils.decode());
    }
}
