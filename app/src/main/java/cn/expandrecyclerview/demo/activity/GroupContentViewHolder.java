package cn.expandrecyclerview.demo.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.expandrecyclerview.demo.model.ExpandableGroup;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/7.
 */

public class GroupContentViewHolder extends cn.expandrecyclerview.demo.viewholders.GroupViewHolder {

    private TextView osName;

    public GroupContentViewHolder(View itemView) {
        super(itemView);

        osName = (TextView) itemView.findViewById(R.id.mobile_os);
    }

    @Override
    public void expand() {
        osName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.below_arrow_icon, 0);
        Log.i("Adapter", "expand");
    }

    @Override
    public void collapse() {
        Log.i("Adapter", "collapse");
        osName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.up_arrow, 0);
    }

    public void setGroupName(ExpandableGroup group) {
        osName.setText(group.getTitle());
    }
}