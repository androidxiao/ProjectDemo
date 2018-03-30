package cn.example.stockmarket.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtil {

    /**
     * 把所有的double类型的小数保留两位有效数字
     * 大于1000的不显示小数也不四舍五入
     */
    public String formartDouble(double targer) {
        //modify by fangzhu
        try {
            if(targer >= 1000) {
                return (int)targer + "";//不要四舍五入
            }else{
                //格式化两位小数
                BigDecimal big = new BigDecimal(targer);
                double res = big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                DecimalFormat df   = new DecimalFormat("######0.00");
                return df.format(res);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //原样返回
            return "" + targer ;
        }
    }

    /**
     * 最多四位小数   120.0  120.01 120.123
     * 如果上千(>= 1000)并且是整数就显示整数部分 3224 3224.2
     * @param d
     * @return
     */
    public static String beautifulDouble (double d) {
        String str = String.valueOf(d);
        try {
            if (str.contains(".")) {
                String s = str.split("\\.")[1];
                int length = s.length();
                if (length > 3) {
                    BigDecimal big = new BigDecimal(d);
                    double res = big.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
                    DecimalFormat df   = new DecimalFormat("######0.0000");
                    return df.format(res);
                }
                if (d >= 1000) {
                    //小数部分是0
                    if (Integer.parseInt(s) == 0) {
                        return (int)d + "";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return d + "";
    }
}
