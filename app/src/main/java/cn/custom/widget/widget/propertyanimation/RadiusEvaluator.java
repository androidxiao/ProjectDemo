package cn.custom.widget.widget.propertyanimation;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * Created by chawei on 2017/11/23.
 */

public class RadiusEvaluator implements TypeEvaluator<RaidusCircle> {
    public static final String TAG = "ez";
    @Override
    public RaidusCircle evaluate(float fraction, RaidusCircle startValue, RaidusCircle endValue) {
        float result = startValue.getRadius() + fraction * (endValue.getRadius() - startValue.getRadius());
        RaidusCircle raidus = new RaidusCircle();
        raidus.setRadius(result);
        Log.d(TAG, "evaluate: --->"+result);
        return raidus;
    }
}
