package cn.behavior.custom.demo.behavior.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.behavior.custom.demo.behavior.model.MusicInfo;
import cn.custom.widget.Px2DpUtil;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/4.
 */

public class BottomSheetDialogActivity extends AppCompatActivity implements View.OnClickListener{

    private BottomSheetDialog mBottomSheetDialog;
    private BottomSheetDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_dialog_layout);

        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("bottomsheet");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.inflateMenu(R.menu.menu_share);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {


            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() ==R.id.item_share) {
                    //弹出shareDialog
                    showShareDialog();
                }
                return false;
            }
        });

        findViewById(R.id.show_bottom).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.show_bottom) {
            showBottomSheetDialog();
            //设置能被"拉"到的最大值
            mDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,Px2DpUtil.dp2px(this,300));
        }
    }

    private void showShareDialog(){
        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_share_dialog, null);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.setCanceledOnTouchOutside(true);
        mBottomSheetDialog.show();
    }

    private void showBottomSheetDialog(){
        mDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog, null);
        handleList(view);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        //自定义BottomSheetDialog弹出的高度需要如下设置
        View sheetViewChange= mDialog.getWindow().findViewById(android.support.design.R.id.design_bottom_sheet);
        //初始时，显示在界面上的高度
        BottomSheetBehavior.from(sheetViewChange).setPeekHeight(Px2DpUtil.dp2px(this,200));
        mDialog.getWindow().setGravity(Gravity.BOTTOM);


        mDialog.show();
    }
    private void setBehaviorCallback(){
        //解决下滑隐藏dialog后，再次调用show方法显示时，不能弹出dialog
        View delegateView = mDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior behavior=BottomSheetBehavior.from(delegateView);
        behavior.setHideable(false);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    mDialog.dismiss();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void handleList(View contentView){
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        MusicAdapter adapter = new MusicAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setData(mockData());
        adapter.notifyDataSetChanged();
    }

    /**
     * 模拟数据
     * @return
     */
    private List<MusicInfo> mockData(){
        List<MusicInfo> musicInfos = new ArrayList<>();
        MusicInfo musicInfo = new MusicInfo("哪里都是你","周杰伦");
        musicInfos.add(musicInfo);
        MusicInfo musicInfo1 = new MusicInfo("阳光宅男","周杰伦");
        musicInfos.add(musicInfo1);
        MusicInfo musicInfo2 = new MusicInfo("可爱女人","周杰伦");
        musicInfos.add(musicInfo2);
        MusicInfo musicInfo3 = new MusicInfo("火车叨位去","周杰伦");
        musicInfos.add(musicInfo3);
        MusicInfo musicInfo4 = new MusicInfo("我的地盘","周杰伦");
        musicInfos.add(musicInfo4);
        MusicInfo musicInfo5 = new MusicInfo("枫","周杰伦");
        musicInfos.add(musicInfo5);
        MusicInfo musicInfo6 = new MusicInfo("搁浅","周杰伦");
        musicInfos.add(musicInfo6);
        MusicInfo musicInfo7 = new MusicInfo("一路向北","周杰伦");
        musicInfos.add(musicInfo7);
        MusicInfo musicInfo8 = new MusicInfo("半岛铁盒","周杰伦");
        musicInfos.add(musicInfo8);
        MusicInfo musicInfo9 = new MusicInfo("霍元甲","周杰伦");
        musicInfos.add(musicInfo9);

        return musicInfos;
    }



    public static class MusicAdapter extends RecyclerView.Adapter{
        private List<MusicInfo> mData;

        public void setData(List<MusicInfo> data) {
            mData = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MusicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_sheet_music_item,null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final MusicViewHolder musicViewHolder = (MusicViewHolder) holder;

            final MusicInfo musicInfo = mData.get(position);

            musicViewHolder.name.setText(musicInfo.name);

            musicViewHolder.singer.setText(musicInfo.singer);
            musicViewHolder.ll_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(musicViewHolder.ll_content.getContext(),musicInfo.name,Toast.LENGTH_LONG).show();
                }
            });


        }

        @Override
        public int getItemCount() {
            return mData == null ? 0:mData.size();
        }

        public static class MusicViewHolder extends RecyclerView.ViewHolder{
            public TextView name;
            public TextView singer;
            public View ll_content;
            public MusicViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.music_name);
                singer = (TextView) itemView.findViewById(R.id.music_singer);
                ll_content=itemView.findViewById(R.id.ll_content);
            }
        }
    }
}
