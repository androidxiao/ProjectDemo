package cn.realm.demo.com.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import cn.project.demo.com.R;
import cn.project.demo.com.databinding.RealmLayout;
import cn.realm.demo.com.model.Person;
import cn.realm.demo.com.util.RealmHelper;
import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by chawei on 2017/8/29.
 */

public class Realm1Activity extends AppCompatActivity {

    public static final String TAG = "ez";
    private Realm mRealm;
    private RealmLayout mRealm1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mRealm = Realm.getDefaultInstance();
        mRealm1 = DataBindingUtil.setContentView(this, R.layout.activity_realm1_layout);
//        basicCRUD(mRealm);
//        basicQuery(mRealm);
//        basicLinkQuery(mRealm);
        //More complex operations can be executed on another thread.
//        new AsyncTask<Void, Void, String>() {
//
//            @Override
//            protected String doInBackground(Void... params) {
//                String info;
//                info = complexReadWrite();
//                info += complexQuery();
//                return info;
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                showStatus(s);
//            }
//        }.execute();
        RealmHelper realmHelper=RealmHelper.getInstance();//获取Realm实例
        Person person=new Person();
        person.setAge(15);
        person.setFullName("凯特琳");
        person.setId(2);
//        realmHelper.insertRealmObject(person);
        List<? extends RealmObject> all = realmHelper.queryRealmObjects(Person.class);
//        RealmResults<Person> all = (RealmResults<Person>) realmHelper.queryRealmObjects(Person.class);
        Log.d(TAG, "onCreate:--->"+all.size());
        for (int i=0;i<all.size();i++){
            Log.d(TAG, "onCreate: "+((Person)all.get(i)).getPets().get(i).getName());
        }
    }

    private void showStatus(String txt) {
        Log.d(TAG, "showStatus: ---->" + txt);
        TextView tv = new TextView(this);
        tv.setText(txt);
        mRealm1.container.addView(tv);
    }

    private void basicCRUD(Realm realm) {
        Log.d(TAG, "Perform basic Create/REad/Update/Delete(CRUD) operations...");
        //All writes must be wrapped in a transaction to facilitate safe multi threading
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //Add a person
                Person person = realm.createObject(Person.class);
                person.setId(1);
//                person.setName("Young Person");
                person.setAge(14);
            }
        });

        //Find the first person (no query conditions) and read a field
        final Person person = realm.where(Person.class).findFirst();
//        showStatus(person.getName() + ":" + person.getAge());

        //Update person in a transaction
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
//                person.setName("Senior Person");
                person.setAge(88);
//                showStatus(person.getName() + " got older: " + person.getAge());
            }
        });

//        Delete all persons
//        mRealm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.delete(Person.class);
//            }
//        });

    }

//    private void basicQuery(Realm realm) {
//        showStatus("\nPerforming basic Query operation...");
//        showStatus("Number of persons: " + realm.where(Person.class).count());
//        RealmResults<Person> all = realm.where(Person.class).findAll();
//        for (int i = 0; i < all.size(); i++) {
//            Log.d(TAG, "basicQuery: --->" + all.get(i).getName() + " ->" + all.get(i).getAge() + " " + all.get(i).getId());
//        }
//        RealmResults<Person> results = realm.where(Person.class).equalTo("age", 88).findAll();
//        showStatus("Size of result set: " + results.size());
//    }
//
//    private void basicLinkQuery(Realm realm) {
//        showStatus("\nPerforming basic Link Query operation...");
//        showStatus("Number of persons: " + realm.where(Person.class).count());
//        RealmResults<Person> results = realm.where(Person.class).equalTo("cats.name", "Tiger").findAll();
//        showStatus("Size of result set: " + results.size());
//    }
//
//    private String complexReadWrite() {
//        String status = "\nPerforming complex Read/Write operation...";
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                Dog fido = realm.createObject(Dog.class);
//                fido.name = "fido";
//                for (int i = 0; i < 10; i++) {
//                    Person person = realm.createObject(Person.class);
//                    person.setId(i);
//                    person.setName("Person no. " + i);
//                    person.setAge(i);
//                    person.setDog(fido);
//                    for (int j = 0; j < i; j++) {
//                        Cat cat = realm.createObject(Cat.class);
//                        cat.name = "Cat_" + j;
//                        person.getCats().add(cat);
//                    }
//                }
//            }
//        });
//
//        status += "]nNumber of persons: " + realm.where(Person.class).count();
//
//        for (Person pers : realm.where(Person.class).findAll()) {
//            String dogName;
//            if (pers.getDog() == null) {
//                dogName = "None";
//            } else {
//                dogName = pers.getDog().name;
//            }
//            status += "\n" + pers.getName() + ":" + pers.getAge() + ":" + dogName + ":" + pers.getCats().size();
//        }
//        //Sorting
//        RealmResults<Person> sortedPersons = realm.where(Person.class).findAllSorted("age", Sort.DESCENDING);
//        status += "\nSorting: " + sortedPersons.last().getName() + "==" + realm.where(Person.class).findFirst().getName();
//        realm.close();
//        return status;
//    }
//
//    private String complexQuery() {
//        String status = "\n\nPerforming complex Query operation...";
//        Realm realm = Realm.getDefaultInstance();
//        status += "\nNumber of persons:" + realm.where(Person.class).count();
//        RealmResults<Person> results = realm.where(Person.class)
//                .between("age", 7, 9)
//                .beginsWith("name", "Person")
//                .findAll();
//        status += "\nSize of result set: " + results.size();
//        realm.close();
//        return status;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Remember to close Realm when down.
        mRealm.close();
    }
}
