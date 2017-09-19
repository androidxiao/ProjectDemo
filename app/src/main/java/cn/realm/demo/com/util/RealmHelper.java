package cn.realm.demo.com.util;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by chawei on 2017/8/30.
 */

public class RealmHelper {

    private static RealmHelper mRealmHelper;
    private RealmHelper(){

    }

    public static RealmHelper getInstance(){
        if (mRealmHelper == null) {
            synchronized (RealmHelper.class){
                mRealmHelper=new RealmHelper();
            }
        }
        return mRealmHelper;
    }

    protected Realm createRealm(){
        //一个Realm只能在同一个线程中访问，在子线程中进行数据库操作必须重新获取Realm对象
        return Realm.getDefaultInstance();
    }

    /**
     * 添加指定类到数据库
     */
    public void insertRealmObject(RealmObject info){
        Realm realm = createRealm();
        realm.beginTransaction();
        realm.copyToRealm(info);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 添加/更新指定类到数据库（model中需要有主键）
     */
    public void insertOrUpdateRealmObject(RealmObject info){
        Realm realm = createRealm();
        realm.beginTransaction();
        //要使用update这种方法，插入的对象必须是有主键的
        realm.copyToRealmOrUpdate(info);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 批量插入数据
     */
    public void insertRealmObjects(List<? extends RealmObject> infos) {
        Realm realm = createRealm();
        realm.beginTransaction();
        //要使用update这种方法，插入的对象必须是有主键的
        realm.copyToRealmOrUpdate(infos);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 查询指定类的全部存储信息
     */
    public List<? extends RealmObject> queryRealmObjects(Class<? extends RealmObject> clazz){
        Realm realm = createRealm();
        RealmResults<? extends RealmObject> realmResults = realm.where(clazz).findAll();
        List<? extends RealmObject> arrayList = realm.copyFromRealm(realmResults);
        realm.close();
        return arrayList;
    }

    /**
     * 删除指定类的全部数据库信息
     */
    public void deleteRealmObjects(Class<? extends RealmObject> clazz){
        Realm realm = createRealm();
        realm.beginTransaction();
        realm.delete(clazz);
        realm.commitTransaction();
        realm.close();
    }

    /**
     * 删除此Realm对应的全部数据库信息
     */
    public void deleteAllRealmObjects(){
        Realm realm = createRealm();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        realm.close();
    }
}
