package cn.custom.widget.model;

/**
 * Created by chawei on 2017/11/17.
 */

public class PieModel {

    private String text;
    private float percent;
    private int colorId;

    public PieModel(String text, float percent, int colorId) {
        this.text = text;
        this.percent = percent;
        this.colorId = colorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }
}
