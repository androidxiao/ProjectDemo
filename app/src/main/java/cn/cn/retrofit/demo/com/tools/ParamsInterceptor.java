package cn.cn.retrofit.demo.com.tools;

import java.io.IOException;

import cn.ProjectApp;
import cn.cn.retrofit.demo.com.utils.DeviceUtil;
import cn.cn.retrofit.demo.com.utils.LogUtil;
import cn.cn.retrofit.demo.com.utils.SharePreUtil;
import cn.project.demo.com.tools.Constants;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by chawei on 2017/9/13.
 */

public class ParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request;
        String method = originalRequest.method();
        Headers headers = originalRequest.headers();
        HttpUrl modifyUrl = originalRequest.url().newBuilder()
                .addQueryParameter("client","android")
                .addQueryParameter("version", Constants.VERSION)
                .addQueryParameter("cid", SharePreUtil.getStringValue("IMEI"))
                .addQueryParameter("channelId", SharePreUtil.getStringValue("channel_id", "anxindeli"))
                .addQueryParameter("network", DeviceUtil.getNetWorkType(ProjectApp.getApp()))
                .build();
        LogUtil.debug("modify----->"+modifyUrl);
        request = originalRequest.newBuilder().url(modifyUrl).build();
        return chain.proceed(request);
    }


}
