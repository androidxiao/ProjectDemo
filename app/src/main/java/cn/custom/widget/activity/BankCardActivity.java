package cn.custom.widget.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import cn.custom.widget.adapter.CustomListAdapter;
import cn.custom.widget.widget.customlayout.BankCardView;
import cn.custom.widget.widget.customlayout.CenterLockHorizontalScrollview;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/12/21.
 */

public class BankCardActivity extends AppCompatActivity {

    CenterLockHorizontalScrollview centerLockHorizontalScrollview;
    CustomListAdapter customListAdapter;
    int currIndex = 0;
    ArrayList<String> list = new ArrayList<String>() {

        {
            add("Manchester city");
            add("Manchester United");
            add("Chelsea");
            add("Liverpool");
            add("TottenHam");
            add("Everton");
            add("WestHam");
            add("Arsenal");
            add("West Broom");
            add("New Castle");
            add("Norich City");
            add("Swansea city");
            add("stroke city");

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bank_card_layout);

        final BankCardView bankCardView= (BankCardView) findViewById(R.id.id_bank_view);
        bankCardView.setActivity(this);


        Button btnSwitch= (Button) findViewById(R.id.id_btn_switch);
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                bankCardView.rotate180();
                if (currIndex != 0) {
                    currIndex--;
                    centerLockHorizontalScrollview.setCenter(currIndex);
                }
            }
        });

        CenterLockHorizontalScrollview horizontalScrollview= (CenterLockHorizontalScrollview) findViewById(R.id.id_clhv);
        customListAdapter = new CustomListAdapter(this,
                R.layout.item_textview_view, list);
        horizontalScrollview.setAdapter(this, customListAdapter);

    }


}
