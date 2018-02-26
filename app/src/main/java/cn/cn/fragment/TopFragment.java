package cn.cn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.cn.adapter.LocalImageAdapter;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/2/6.
 */

public class TopFragment extends Fragment {

    private ConvenientBanner mConvenientBanner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_top_layout, null);
        mConvenientBanner = (ConvenientBanner)view.findViewById(R.id.id_convenient_banner);


        initBanner();


        return view;
    }


    private void initBanner() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.banner1);
        list.add(R.drawable.banner2);
        list.add(R.drawable.banner3);
        mConvenientBanner.setPages(new CBViewHolderCreator<LocalImageAdapter>() {
            @Override
            public LocalImageAdapter createHolder() {
                return new LocalImageAdapter();
            }
        }, list)
                .setPageIndicator(new int[]{R.drawable.dot_unfocus, R.drawable.dot_focus})
                .setPointViewVisible(true)
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
                    }
                }).setCanLoop(true);
    }
}