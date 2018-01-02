package cn.custom.widget.model;

import android.graphics.RectF;

/**
 * Created by chawei on 2017/11/29.
 */

public class LadderModel {

    private int smallRadius;
    private int middleRadius;
    private int smallColorId;
    private int middleColorId;
    private int lineColor;
    private int lineLength;
    private RectF mRectF;
    private int bigRadius;

    public LadderModel(int smallRadius, int middleRadius, int smallColorId, int middleColorId, int lineColor, int lineLength, RectF rectF, int bigRadius) {
        this.smallRadius = smallRadius;
        this.middleRadius = middleRadius;
        this.smallColorId = smallColorId;
        this.middleColorId = middleColorId;
        this.lineColor = lineColor;
        this.lineLength = lineLength;
        mRectF = rectF;
        this.bigRadius = bigRadius;
    }

    public int getSmallRadius() {
        return smallRadius;
    }

    public void setSmallRadius(int smallRadius) {
        this.smallRadius = smallRadius;
    }

    public int getMiddleRadius() {
        return middleRadius;
    }

    public void setMiddleRadius(int middleRadius) {
        this.middleRadius = middleRadius;
    }

    public int getSmallColorId() {
        return smallColorId;
    }

    public void setSmallColorId(int smallColorId) {
        this.smallColorId = smallColorId;
    }

    public int getMiddleColorId() {
        return middleColorId;
    }

    public void setMiddleColorId(int middleColorId) {
        this.middleColorId = middleColorId;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getLineLength() {
        return lineLength;
    }

    public void setLineLength(int lineLength) {
        this.lineLength = lineLength;
    }

    public RectF getRectF() {
        return mRectF;
    }

    public void setRectF(RectF rectF) {
        mRectF = rectF;
    }

    public int getBigRadius() {
        return bigRadius;
    }

    public void setBigRadius(int bigRadius) {
        this.bigRadius = bigRadius;
    }
}
