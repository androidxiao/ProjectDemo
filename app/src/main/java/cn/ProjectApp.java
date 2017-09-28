package cn;

import android.app.Application;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import cn.cn.retrofit.demo.com.utils.SharePreUtil;
import cn.project.demo.com.tools.Constants;
import cn.realm.demo.com.MyConfigMigration;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by chawei on 2017/8/29.
 */

public class ProjectApp extends Application {


    private static Application mApplication;
    /**
     * Realm 数据库版本
     */
    private static final int REALM_VERSION=3;
    @Override
    public void onCreate() {
        super.onCreate();

        mApplication=this;

        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
        RealmConfiguration myConfig=new RealmConfiguration.Builder()
                .schemaVersion(REALM_VERSION)
                .migration(new MyConfigMigration())
                .name("myrealm.realm")
                .build();
        Realm.setDefaultConfiguration(myConfig);

        isUseStetho();

//        writeIMEI();
    }


    public static Application getApp(){
        return mApplication;
    }

    /**
     * 使用Stetho
     */
    private void isUseStetho() {
        if (Constants.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build()).
                            build());
        }
    }


    /**
     * IMEI写入本地
     */
    private void writeIMEI() {
        if (TextUtils.isEmpty(SharePreUtil.getStringValue("IMEI"))) {
            TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
            SharePreUtil.saveString("IMEI", tm.getDeviceId());
        }
    }


}
