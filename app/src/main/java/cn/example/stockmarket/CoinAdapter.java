package cn.example.stockmarket;

import android.content.Context;

import java.util.List;

import cn.example.stockmarket.model.CoinInfo;
import cn.example.stockmarket.widget.CommonAdapter;
import cn.example.stockmarket.widget.CommonViewHolder;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/4/29.
 */

public class CoinAdapter extends CommonAdapter<CoinInfo> {

    private CommonViewHolder.onItemCommonClickListener commonClickListener;

    public CoinAdapter(Context context, List<CoinInfo> dataList, int layoutId, CommonViewHolder.onItemCommonClickListener listener) {
        super(context, dataList, layoutId);
        commonClickListener = listener;
    }

    @Override
    public void bindData(CommonViewHolder holder, CoinInfo data) {

        holder.setText(R.id.id_name, data.name)
                .setText(R.id.id_tv_price_last, data.priceLast)
                .setText(R.id.id_tv_rise_rate24, data.riseRate24)
                .setText(R.id.id_tv_vol24, data.vol24)
                .setText(R.id.id_tv_close, data.close)
                .setText(R.id.id_tv_open, data.open)
                .setText(R.id.id_tv_bid, data.bid)
                .setText(R.id.id_tv_ask, data.ask)
                .setText(R.id.id_tv_percent, data.amountPercent)
                .setCommonClickListener(commonClickListener);
    }
}
