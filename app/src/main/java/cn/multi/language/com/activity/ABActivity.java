package cn.multi.language.com.activity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.multi.language.com.fragment.AFragment;
import cn.multi.language.com.fragment.BFragment;
import cn.multi.language.com.utils.LocaleHelper;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/3.
 */

public class ABActivity extends AppCompatActivity {

    private Fragment[] mFragments;
    private Fragment mLastFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ab_layout);

        mFragments=new Fragment[]{new AFragment(),new BFragment()};

        init();
    }

    private void init(){
        RadioGroup radioGroup= (RadioGroup) findViewById(R.id.rg_btn);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (R.id.rb_home == checkedId) {
                    switchFragment(mFragments[0]);
                    updateLanguage("zh");
                    finish();
                    startActivity(getIntent());
                    overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                    Toast.makeText(ABActivity.this, "aaa", Toast.LENGTH_SHORT).show();
                }else if(R.id.rb_manage==checkedId){
                    Toast.makeText(ABActivity.this, "bbb", Toast.LENGTH_SHORT).show();
                    switchFragment(mFragments[1]);
                    updateLanguage("fr");
                    finish();
                    startActivity(getIntent());
                    overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                }
            }
        });
        switchFragment(mFragments[0]);
    }



    private void updateLanguage(String languageISO) {
        Context context = LocaleHelper.setLocale(this, languageISO);
        Resources resources = context.getResources();
//        recreate();
    }

    private void switchFragment(Fragment fragment){
        try {
            if (fragment == null) {
                fragment = mFragments[0];
            }
            if (fragment.equals(mLastFragment)) {
                return;
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (mLastFragment != null) {
                transaction.hide(mLastFragment);
            }
            if (!fragment.isAdded()) {
                transaction.add(R.id.fl_content, fragment);
            } else {
                transaction.show(fragment);
                fragment.onResume();
            }
            transaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mLastFragment=fragment;
        }

    }
}
