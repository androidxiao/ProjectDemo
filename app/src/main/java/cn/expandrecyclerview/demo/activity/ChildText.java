package cn.expandrecyclerview.demo.activity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chawei on 2018/1/7.
 */

public class ChildText implements Parcelable {
    private String name;

    public ChildText(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChildText(String name) {
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ChildText> CREATOR = new Creator<ChildText>() {
        @Override
        public ChildText createFromParcel(Parcel in) {
            return new ChildText(in);
        }

        @Override
        public ChildText[] newArray(int size) {
            return new ChildText[size];
        }
    };
}
