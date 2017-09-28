package cn.cn.retrofit.demo.com.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by chawei on 2017/9/26.
 */

public class TimeUtils {

    /**
     * 根据传入的type，获取具体的时分秒
     * @return
     */
    public static int getSingleTime(int type,String time){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse("2017-09-26 16:27:21");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);

        Log.d("ez", "onCreate: "+calendar.get(Calendar.MINUTE));//获取单独的分钟
        return calendar.get(Calendar.MINUTE);
    }
}
