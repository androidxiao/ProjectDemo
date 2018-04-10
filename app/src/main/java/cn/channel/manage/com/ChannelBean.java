package cn.channel.manage.com;


public class ChannelBean {
    private String name;
    private int spanSize;
    private int layoutId;
    private boolean isRecommend;
    private boolean isLock;

    public ChannelBean() {
    }

    public ChannelBean(String name, int spanSize, int layoutId, boolean isRecommend) {
        this.name = name;
        this.spanSize = spanSize;
        this.layoutId = layoutId;
        this.isRecommend = isRecommend;
    }

    public ChannelBean(String name, int spanSize, int layoutId, boolean isRecommend,boolean isLock) {
        this.name = name;
        this.spanSize = spanSize;
        this.layoutId = layoutId;
        this.isRecommend = isRecommend;
        this.isLock=isLock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpanSize() {
        return spanSize;
    }

    public void setSpanSize(int spanSize) {
        this.spanSize = spanSize;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public boolean isRecommend() {
        return isRecommend;
    }

    public void setRecommend(boolean recommend) {
        isRecommend = recommend;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }
}
