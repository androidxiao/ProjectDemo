package cn.project.demo.com.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/9/19.
 *
 * TextInputLayout配合EditText，使用图标显示和隐藏密码（需要使用setPasswordVisibilityToggleDrawable属性,用于切换显示效果）
 * xml中只能设置一种状态。
 *
 *
 * 无障碍权限(开启后仅仅是辅助功能)--->是可以获取页面中的全部文本（包括等重要信息），
 * 要想阻止该情况，就需要设置相关属性。如下所示：
 * ViewCompat.setImportantForAccessibility(mLayout.getEditText(),ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO);
 */

public class TextInputLayoutActivity extends AppCompatActivity {


    private TextInputLayout mLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(cn.project.demo.com.R.layout.activity_text_input_layout);
        //当设置此属性时，textinputlayout中的edittext就为不可用状态,edittext本身也具有此属性
//        ((TextInputLayout)findViewById(R.id.layout_password)).setEnabled(false);
        TextInputLayout mLayout = (TextInputLayout) findViewById(R.id.layout_password);
        mLayout.setPasswordVisibilityToggleDrawable(R.drawable.pwd_selector);
    }
}
