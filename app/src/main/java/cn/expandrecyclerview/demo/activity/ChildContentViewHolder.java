package cn.expandrecyclerview.demo.activity;

import android.view.View;
import android.widget.TextView;

import cn.expandrecyclerview.demo.model.ExpandableGroup;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/7.
 */

public class ChildContentViewHolder extends cn.expandrecyclerview.demo.viewholders.ChildViewHolder {

    private TextView phoneName;

    public ChildContentViewHolder(View itemView) {
        super(itemView);

        phoneName = (TextView) itemView.findViewById(R.id.phone_name);
    }

    public void onBind(ChildText phone, ExpandableGroup group) {
        phoneName.setText(phone.getName());
        if (group.getTitle().equals("水果")) {
            phoneName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher, 0, 0, 0);
        } else if (group.getTitle().equals("球类")) {
            phoneName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher, 0, 0, 0);
        } else {
            phoneName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher, 0, 0, 0);
        }
    }
}