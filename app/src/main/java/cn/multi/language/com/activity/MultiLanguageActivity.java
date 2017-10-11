package cn.multi.language.com.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.multi.language.com.utils.LocaleHelper;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/1.
 * <p>
 * APP多语言支持（通过系统选择语言来查看效果）
 */

public class MultiLanguageActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_language_layout);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.id_btn_chinese).setOnClickListener(this);
        findViewById(R.id.id_btn_english).setOnClickListener(this);
        findViewById(R.id.id_btn_fr).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_chinese:
                updateLanguage("zh");
                finish();
                startActivity(getIntent());
                overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                break;
            case R.id.id_btn_english:
                break;
            case R.id.id_btn_fr:
                break;
        }
    }

    private void updateLanguage(String languageISO) {
        Context context = LocaleHelper.setLocale(this, languageISO);
        Resources resources = context.getResources();
//        recreate();
    }
}
