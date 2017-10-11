package cn.custom.widget.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.project.demo.com.R;
import cn.project.demo.com.databinding.AttrBind;

/**
 * Created by chawei on 2017/10/9.
 */

public class AttributeSettersActivity extends AppCompatActivity {

    private AttrBind mAttrBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAttrBind=DataBindingUtil.setContentView(this,R.layout.activity_attr_setter_layout);


        mAttrBind.idSet1.setHint("我是提示文字");

        mAttrBind.idSet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAttrBind.idSet1.setHint("我是被点击赋值的");
            }
        });

        mAttrBind.idSet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAttrBind.idSet1.setHint("我被点击后值被还原了");
            }
        });
    }
}
