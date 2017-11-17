package cn.cn.retrofit.demo.com.http;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import cn.cn.retrofit.demo.com.model.BannerImageModel;
import cn.cn.retrofit.demo.com.model.SupplyModel;
import cn.cn.retrofit.demo.com.tools.ParamsInterceptor;
import cn.project.demo.com.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static cn.cn.retrofit.demo.com.tools.HttpSign.paramsMd5;

/**
 * Created by chawei on 2017/9/13.
 */

public class HttpMethods {
    public static final String TAG="RetrofitDemo";
    public static final String Base_URL = BuildConfig.BASE_URL;
    private final ApiServices mApiServices;
    private static HttpMethods mHttpMethods;
    private static Context mContext;

    public static HttpMethods getInstance(Context context) {
        mContext = context;
        if (mHttpMethods == null) {
            synchronized (HttpMethods.class) {
                if (mHttpMethods == null) {
                    mHttpMethods = new HttpMethods();
                }
            }
        }
        return mHttpMethods;
    }

    private HttpMethods() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new ParamsInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApiServices = retrofit.create(ApiServices.class);
    }

    private   <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    public void getBannerData1(Subscriber subscriber){
        Observable<ResponseBody> observable = mApiServices.getBannerData("/v1/default/slider",paramsMd5(null));
        toSubscribe(observable,subscriber);
    }

    /**
     * banner图
     */
    public void getBannerData(){
        Observable<BannerImageModel> observable = mApiServices.getBannerData("/v1/default/slider",paramsMd5(null));
        toSubscribe(observable, new Subscriber<BannerImageModel>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage()+" "+e.getCause()+" "+e.getLocalizedMessage());
            }

            @Override
            public void onNext(BannerImageModel model) {

                Log.d(TAG, "onNext: "+model.getStatus()+"  "+model.getMsg());
            }
        });
    }

    /**
     * 供求信息/供应
     */
    public void getSupplyList(){
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("page","1");
        map.put("type","1");
        Observable<SupplyModel> observable = mApiServices.getSupplyList("/v1/deal/list", paramsMd5(map));
        toSubscribe(observable, new Subscriber<SupplyModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: "+e.getMessage()+" "+e.getCause()+" "+e.getLocalizedMessage());
            }

            @Override
            public void onNext(SupplyModel model) {
                Log.d(TAG, "onNext: "+model.getStatus()+"  --->"+model.getData().getList().size());
            }
        });
    }
}
