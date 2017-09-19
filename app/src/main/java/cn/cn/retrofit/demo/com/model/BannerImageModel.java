package cn.cn.retrofit.demo.com.model;

import java.util.ArrayList;

/**
 * Created by chawei on 2017/9/13.
 */
public class BannerImageModel extends BaseModel {

    private BannerImageBean data;

    public BannerImageBean getData() {
        return data;
    }

    public class BannerImageBean {

        private ArrayList<BannerImageInfo> list;

        public ArrayList<BannerImageInfo> getList() {
            return list;
        }
    }

}
