package cn.android.example.knowledge.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.android.example.knowledge.utils.ApkInfos;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/24.
 */

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder>{

    Context mContext;
    List<String> mStringlist;

    public AppsAdapter(Context context, List<String> list){

        mContext = context;

        mStringlist = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public ImageView imageView;
        public TextView textView_App_Name;
        public TextView textView_App_Package_Name;

        public ViewHolder (View view){

            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.imageview);
            textView_App_Name = (TextView) view.findViewById(R.id.id_tv_apk_name);
            textView_App_Package_Name = (TextView) view.findViewById(R.id.id_apk_package_name);
        }
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view = LayoutInflater.from(mContext).inflate(R.layout.show_install_app_cardview_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){

        ApkInfos apkInfos = new ApkInfos(mContext);

        final String ApplicationPackageName = (String) mStringlist.get(position);
        String ApplicationLabelName = apkInfos.GetAppName(ApplicationPackageName);
        Drawable drawable = apkInfos.getAppIconByPackageName(ApplicationPackageName);

        viewHolder.textView_App_Name.setText(ApplicationLabelName);

        viewHolder.textView_App_Package_Name.setText(ApplicationPackageName);

        viewHolder.imageView.setImageDrawable(drawable);

        //Adding click listener on CardView to open clicked application directly from here .
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                if(intent != null){

                    mContext.startActivity(intent);

                }
                else {

                    Toast.makeText(mContext,ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount(){

        return mStringlist.size();
    }

}