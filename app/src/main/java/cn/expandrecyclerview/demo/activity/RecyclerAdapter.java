package cn.expandrecyclerview.demo.activity;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.expandrecyclerview.demo.controller.ExpandableRecyclerViewAdapter;
import cn.expandrecyclerview.demo.model.ExpandableGroup;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/7.
 */

public class RecyclerAdapter extends ExpandableRecyclerViewAdapter<GroupContentViewHolder, ChildContentViewHolder> {

    private Activity activity;

    public RecyclerAdapter(Activity activity, List<? extends ExpandableGroup> groups) {
        super(groups);
        this.activity = activity;
    }

    @Override
    public GroupContentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.group_view_holder, parent, false);

        return new GroupContentViewHolder(view);
    }

    @Override
    public ChildContentViewHolder onCreateChildViewHolder(ViewGroup parent, final int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.child_view_holder, parent, false);

        return new ChildContentViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildContentViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final ChildText phone = ((GroupText) group).getItems().get(childIndex);
        holder.onBind(phone,group);
    }

    @Override
    public void onBindGroupViewHolder(GroupContentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupName(group);
    }
}