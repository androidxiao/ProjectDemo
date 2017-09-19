package cn.project.demo.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chawei on 2017/9/12.
 */

public class CoordinatorAdapter extends RecyclerView.Adapter{

    private Context mContext;
    public CoordinatorAdapter(Context context) {
        mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView view = new TextView(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,40));
        view.setText("我用来test");
        return new CoordinatorHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 50;
    }

    class CoordinatorHolder extends RecyclerView.ViewHolder{

        public CoordinatorHolder(View itemView) {
            super(itemView);

        }
    }
}
