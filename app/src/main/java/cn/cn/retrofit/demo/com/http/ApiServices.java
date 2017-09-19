package cn.cn.retrofit.demo.com.http;

import android.support.v4.util.ArrayMap;

import cn.cn.retrofit.demo.com.model.SupplyModel;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by chawei on 2017/9/13.
 */

public interface ApiServices {

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> getBannerData(@Url String url, @FieldMap ArrayMap<String, String> map);//这里把ResponseBody.string，就可以获取json串
//    Observable<BannerImageModel> getBannerData(@Url String url, @FieldMap ArrayMap<String, String> map);

    @FormUrlEncoded
    @POST
    Observable<SupplyModel> getSupplyList(@Url String url,@FieldMap ArrayMap<String,String>map);
}
