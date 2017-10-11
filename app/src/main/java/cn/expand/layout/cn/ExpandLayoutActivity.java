package cn.expand.layout.cn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.expand.layout.cn.listener.ExpandCollapseListener;
import cn.expand.layout.cn.model.Fruit;
import cn.expand.layout.cn.model.FruitCategory;
import cn.expand.layout.cn.model.Section;
import cn.expand.layout.cn.widget.ExpandableLayout;
import cn.project.demo.com.R;

/**
 * Created by chawei on 2017/10/10.
 */

public class ExpandLayoutActivity extends AppCompatActivity {

    public static final String TAG = "ez";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_layout);

        ExpandableLayout sectionLinearLayout = (ExpandableLayout) findViewById(R.id.id_expand_layout);

        sectionLinearLayout.setRenderer(new ExpandableLayout.Renderer<FruitCategory, Fruit>() {
            @Override
            public void renderParent(View view, FruitCategory model, boolean isExpanded, int parentPosition) {
                ((TextView) view.findViewById(R.id.tvParent)).setText(model.name);

                Log.d(TAG, "renderParent: --->"+isExpanded);
                view.findViewById(R.id.arrow).setBackgroundResource(isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down);
            }

            @Override
            public void renderChild(View view, Fruit model, int parentPosition, int childPosition) {
                ((TextView) view.findViewById(R.id.tvChild)).setText(model.name);
            }
        });

        sectionLinearLayout.addSection(getSection());
        sectionLinearLayout.addSection(getSection());

        sectionLinearLayout.setExpandListener(new ExpandCollapseListener.ExpandListener<FruitCategory>() {
            @Override
            public void onExpanded(int parentIndex, FruitCategory parent, View view) {
                view.findViewById(R.id.arrow).setBackgroundResource( R.drawable.arrow_down);

            }
        });

        sectionLinearLayout.setCollapseListener(new ExpandCollapseListener.CollapseListener<FruitCategory>() {
            @Override
            public void onCollapsed(int parentIndex, FruitCategory parent, View view) {
                view.findViewById(R.id.arrow).setBackgroundResource( R.drawable.arrow_up);
            }
        });
    }

    public Section<FruitCategory, Fruit> getSection() {
        Section<FruitCategory, Fruit> section = new Section<>();
        FruitCategory fruitCategory = new FruitCategory("Fruits");
        Fruit fruit1 = new Fruit("Apple");
        Fruit fruit2 = new Fruit("Orange");
        Fruit fruit3 = new Fruit("Banana");
        Fruit fruit4 = new Fruit("Grape");
        Fruit fruit5 = new Fruit("Strawberry");
        Fruit fruit6 = new Fruit("Cherry");

        section.parent = fruitCategory;
        section.children.add(fruit1);
        section.children.add(fruit2);
        section.children.add(fruit3);
        section.children.add(fruit4);
        section.children.add(fruit5);
        section.expanded = true;
        return section;
    }
}
