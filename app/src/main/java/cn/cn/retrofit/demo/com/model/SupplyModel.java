package cn.cn.retrofit.demo.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by chawei on 2017/9/14.
 */

public class SupplyModel extends BaseModel {

    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {
        private int count;
        private int pSize;
        private int page;
        private int pages;
        private ArrayList<DataList> list;

        public int getCount() {
            return count;
        }

        public int getpSize() {
            return pSize;
        }

        public int getPage() {
            return page;
        }

        public int getPages() {
            return pages;
        }


        public ArrayList<DataList> getList() {
            return list;
        }
    }

    public static class DataList implements Parcelable {
        private String date;
        private String img;
        private int number;
        private String title;
        private int userId;
        private int dealId;
        private String priceDisplay;
        private String numUnit;
        private int hasVideo;

        protected DataList(Parcel in) {
            date = in.readString();
            img = in.readString();
            number = in.readInt();
            title = in.readString();
            userId = in.readInt();
            dealId = in.readInt();
            priceDisplay=in.readString();
            numUnit=in.readString();
            hasVideo = in.readInt();
        }

        public static final Creator<DataList> CREATOR = new Creator<DataList>() {
            @Override
            public DataList createFromParcel(Parcel in) {
                return new DataList(in);
            }

            @Override
            public DataList[] newArray(int size) {
                return new DataList[size];
            }
        };

        public int getDealId() {
            return dealId;
        }
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }


        public String getPriceDisplay() {
            return priceDisplay;
        }

        public void setPriceDisplay(String priceDisplay) {
            this.priceDisplay = priceDisplay;
        }

        public String getNumUnit() {
            return numUnit;
        }

        public int getHasVideo() {
            return hasVideo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(date);
            dest.writeString(img);
            dest.writeInt(number);
            dest.writeString(title);
            dest.writeInt(userId);
            dest.writeInt(dealId);
            dest.writeString(priceDisplay);
            dest.writeString(numUnit);
            dest.writeInt(hasVideo);
        }
    }

}