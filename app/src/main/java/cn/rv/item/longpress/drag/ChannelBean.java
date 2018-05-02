package cn.rv.item.longpress.drag;


public class ChannelBean {

    private String name;
    private int spanSize;
    private boolean isLock;

    public ChannelBean(String name,int spanSize,  boolean isLock) {
        this.name=name;
        this.spanSize = spanSize;
        this.isLock = isLock;
    }


    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
