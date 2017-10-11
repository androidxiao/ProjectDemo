package cn.expand.layout.cn.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chawei on 2017/10/10.
 */

public class Section<T, E> {

    public boolean expanded;
    public T parent;
    public List<E> children;

    public Section() {
        children = new ArrayList<>();
        expanded = false;
    }
}
