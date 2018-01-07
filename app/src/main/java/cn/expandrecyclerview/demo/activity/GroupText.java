package cn.expandrecyclerview.demo.activity;

import android.annotation.SuppressLint;

import java.util.List;

import cn.expandrecyclerview.demo.model.ExpandableGroup;

/**
 * Created by chawei on 2018/1/7.
 */

@SuppressLint("ParcelCreator")
public class GroupText extends ExpandableGroup<ChildText> {

    public GroupText(String title, List<ChildText> items) {
        super(title, items);
    }
}
