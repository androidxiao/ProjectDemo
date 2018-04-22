package cn.login.anim.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2018/1/17.
 */

public class LoginInFragment extends Fragment{


    TransMenuView mMenuView;

    Context mContext;
    private Button mBtn;

    @Override
    public void onStart() {
        super.onStart();
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_in_layout, container, false);
    }

    private boolean isRotation;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMenuView= (TransMenuView) view.findViewById(R.id.id_trans);
        mBtn = (Button) view.findViewById(R.id.id_btn_click_me);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRotation){
                    mBtn.setRotation(45);
                    isRotation=true;
                }else{
                    mBtn.setRotation(0);
                    isRotation=false;
                }
                mMenuView.setTranslationX(mMenuView,mBtn);
                mBtn.setEnabled(false);

            }
        });

        mMenuView.setListener(new TransMenuView.OnIvClickListener() {
            @Override
            public void ivClick(int position) {
                if(position==1){
                    mMenuView.backBtn(mBtn);
                }else{
                    mMenuView.comeBtn(mBtn);
                }
            }
        });

    }

}
