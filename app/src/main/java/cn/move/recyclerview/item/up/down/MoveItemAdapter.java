package cn.move.recyclerview.item.up.down;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/4/10.
 */

public class MoveItemAdapter extends RecyclerView.Adapter<MoveItemAdapter.MyViewHolder> {

    startDragListener draglistener;
    List<String> sortedList;
    List<String> sourceList;

    //set接口回调
    public void setDraglistener(startDragListener draglistener) {
        this.draglistener = draglistener;
    }


    public MoveItemAdapter(List<String> list) {
        this.sortedList =list;
        this.sourceList=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move_up_down, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public int getItemCount() {
        return sortedList == null ? 0 : sortedList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvContent.setText(sortedList.get(position));
        holder.image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //注意：这里down和up都会回调该方法
                if (event.getAction()== MotionEvent.ACTION_DOWN) {
                    draglistener.startDragItem(holder);
                }
                return false;
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String string : sortedList) {
                    Log.d("ez", "onClick: --->"+string);
                }
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sortedPosition=holder.getAdapterPosition();
                notifyItemRemoved(sortedPosition);
                sortedList.remove(sortedPosition);

                for (String string : sortedList) {
                    Log.d("ez", "移除后的sortedList: --->"+string);
                }
            }
        });

        holder.tvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sortedPosition=holder.getAdapterPosition();

               notifyItemMoved(sortedPosition,0);
                String element = sortedList.get(sortedPosition);
                sortedList.remove(sortedPosition);
                sortedList.add(0,element);
                for (String string : sortedList) {
                    Log.d("ez", "置顶的sortedList: --->"+string);
                }
            }
        });
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvContent;
        TextView tvTop;
        TextView tvDelete;
        ImageView image;
        View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            tvContent = (TextView) itemView.findViewById(R.id.id_tv);
            tvTop = (TextView) itemView.findViewById(R.id.id_tv_top);
            tvDelete = (TextView) itemView.findViewById(R.id.id_tv_delete);
            image= (ImageView) itemView.findViewById(R.id.id_iv);
        }

    }
}
