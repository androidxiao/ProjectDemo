package cn.cn.retrofit.demo.com.model;

import java.io.Serializable;


public class BaseModelT<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int status;
    public String msg;
    public T data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}