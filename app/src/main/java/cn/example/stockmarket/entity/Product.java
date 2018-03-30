package cn.example.stockmarket.entity;

import java.io.Serializable;

public class Product implements Serializable {

    /**
     * 品种所需字段
     */

    private double Buy;
    private String Code;
    private String End;
    private double High;
    private double Last;
    private double LastClose;
    private double Low;
    private String Name;
    private double Open;
    private long QuoteTime;
    private double Sell;
    private double UpDown;
    private double UpDownRate;

    public double getBuy() {
        return Buy;
    }

    public void setBuy(double buy) {
        Buy = buy;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getEnd() {
        return End;
    }

    public void setEnd(String end) {
        End = end;
    }

    public double getHigh() {
        return High;
    }

    public void setHigh(double high) {
        High = high;
    }

    public double getLast() {
        return Last;
    }

    public void setLast(double last) {
        Last = last;
    }

    public double getLastClose() {
        return LastClose;
    }

    public void setLastClose(double lastClose) {
        LastClose = lastClose;
    }

    public double getLow() {
        return Low;
    }

    public void setLow(double low) {
        Low = low;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getOpen() {
        return Open;
    }

    public void setOpen(double open) {
        Open = open;
    }

    public long getQuoteTime() {
        return QuoteTime;
    }

    public void setQuoteTime(long quoteTime) {
        QuoteTime = quoteTime;
    }

    public double getSell() {
        return Sell;
    }

    public void setSell(double sell) {
        Sell = sell;
    }

    public double getUpDown() {
        return UpDown;
    }

    public void setUpDown(double upDown) {
        UpDown = upDown;
    }

    public double getUpDownRate() {
        return UpDownRate;
    }

    public void setUpDownRate(double upDownRate) {
        UpDownRate = upDownRate;
    }
}
