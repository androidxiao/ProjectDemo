package cn.example.stockmarket.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cn.example.stockmarket.entity.Product;
import cn.example.stockmarket.util.NumberUtil;
import cn.project.demo.com.R;

public class RightAdapter extends ArrayAdapter<Product> {
    List<Product> objects;
    public RightAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.item_right, null);
            viewHolder.tv_item1 = (TextView)convertView.findViewById(R.id.tv_item1);
            viewHolder.tv_item2 = (TextView)convertView.findViewById(R.id.tv_item2);
            viewHolder.tv_item3 = (TextView)convertView.findViewById(R.id.tv_item3);
            viewHolder.tv_item4 = (TextView)convertView.findViewById(R.id.tv_item4);
            viewHolder.tv_item5 = (TextView)convertView.findViewById(R.id.tv_item5);
            viewHolder.tv_item6 = (TextView)convertView.findViewById(R.id.tv_item6);
            viewHolder.tv_item7 = (TextView)convertView.findViewById(R.id.tv_item7);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Product product = objects.get(position);
        viewHolder.tv_item1.setText(NumberUtil.beautifulDouble(product.getLast()));
        viewHolder.tv_item2.setText(NumberUtil.beautifulDouble(product.getSell() - product.getLastClose()));
        viewHolder.tv_item3.setText(NumberUtil.beautifulDouble(product.getUpDownRate()));//(product.getSell() - product.getLastClose()) * 100 / product.getSell() + ""
        viewHolder.tv_item4.setText(NumberUtil.beautifulDouble(product.getOpen()));
        viewHolder.tv_item5.setText(NumberUtil.beautifulDouble(product.getHigh()));
        viewHolder.tv_item6.setText(NumberUtil.beautifulDouble(product.getLow()));
        viewHolder.tv_item7.setText(NumberUtil.beautifulDouble(product.getLastClose()));
        return convertView;
    }

    @Override
    public int getCount() {
        return objects.size();

    }

    class ViewHolder {
        TextView tv_item1, tv_item2, tv_item3, tv_item4, tv_item5, tv_item6, tv_item7;
    }
}
