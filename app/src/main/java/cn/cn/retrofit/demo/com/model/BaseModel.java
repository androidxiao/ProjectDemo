package cn.cn.retrofit.demo.com.model;

/**
 * Created by chawei on 2017/9/13.
 */

public  class BaseModel {

    private int status;
    private String msg;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BaseModel toLzyResponse() {
        BaseModel lzyResponse = new BaseModel();
        lzyResponse.setStatus( status);
        lzyResponse.setMsg(msg);
        return lzyResponse;
    }

}

