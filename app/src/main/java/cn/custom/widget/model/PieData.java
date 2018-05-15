package cn.custom.widget.model;

/**
 * Created by wei on 2018/5/13.
 */

public class PieData {

    public int pieColor;
    public float pieValue;
    public String pieString;

    public PieData(float pieValue, String pieString, int pieColor){
        this.pieValue = pieValue;
        this.pieColor = pieColor;
        this.pieString = pieString;
    }
}
