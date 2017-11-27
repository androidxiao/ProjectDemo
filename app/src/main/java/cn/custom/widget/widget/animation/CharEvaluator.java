package cn.custom.widget.widget.animation;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * Created by chawei on 2017/11/23.
 */

public class CharEvaluator implements TypeEvaluator<Character> {

    public static final String TAG = "ez";
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int  startInt=startValue;
        int endInt=endValue;
//        int  startInt=(int)startValue;
//        int endInt=(int)endValue;
        int result = (int) (startInt + fraction * (endInt - startInt));
        Log.d(TAG, "evaluate: --->"+(char)result);
        return (char)result;
    }

}
