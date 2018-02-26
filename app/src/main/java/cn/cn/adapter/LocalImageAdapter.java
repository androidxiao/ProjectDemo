package cn.cn.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by chawei on 2018/2/17.
 */

public class LocalImageAdapter implements Holder<Integer> {

    private ImageView imageView;
    private Context mContext;
    private RequestOptions myOptions = new RequestOptions()
            .centerCrop();

    @Override
    public View createView(Context context) {
        mContext=context;
        imageView = new ImageView(context);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        Glide.with(mContext).load(data).apply(myOptions).into(imageView);
    }
}
