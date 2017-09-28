package cn.realm.demo.com;

import cn.project.demo.com.utils.LogUtil;
import io.realm.DynamicRealm;
import io.realm.DynamicRealmObject;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by chawei on 2017/8/30.
 *
 * realm升级/降级配置文件
 */

public class MyConfigMigration implements RealmMigration {
    public static final String TAG = "ez";

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        LogUtil.debug("oldVersion--->" + oldVersion + "  newVersion--->" + newVersion);

        realmUpdate((int) oldVersion,realm);

        //官方写法
//        if (oldVersion == 0 && newVersion == 1) {
//            version1(realm);
//            ++oldVersion;
//        }
//
//        if (oldVersion == 1 && newVersion == 2) {
//            version2(realm);
//            ++oldVersion;
//        }
//
//        if (oldVersion == 2 && newVersion == 3) {
//            version3(realm);
//            ++oldVersion;
//        }
    }

    /**
     * realm数据库迁移（升级）
     * 降级：需要使用if判断来处理
     * @param oldVersion
     * @param realm
     */
    private void realmUpdate(int oldVersion, DynamicRealm realm) {
        switch (oldVersion) {
            case 0:
                version1(realm);
            case 1:
                version2(realm);
            case 2:
                version3(realm);
            default:
                break;
        }
    }

    /**
     * version=1时，增加fullName，删除name。
     * <p>
     * 注意：添加字段时，对应的model也要对应添加（如果标记的不为空，那么model也必须注解不为空），删除字段时，对应的model也要删除，
     *
     * @param realm
     */
    private void version1(DynamicRealm realm) {
        RealmSchema schema = realm.getSchema();
        RealmObjectSchema personSchema = schema.get("Person");
        personSchema.addField("fullName", String.class, FieldAttribute.REQUIRED)
                .transform(new RealmObjectSchema.Function() {

                    @Override
                    public void apply(DynamicRealmObject obj) {
                        obj.set("fullName", obj.get("name") + "Full");
                    }
                })
                .removeField("name");
    }

    /**
     * version=2时，增加了Pet类。
     */
    private void version2(final DynamicRealm realm) {
        RealmSchema schema = realm.getSchema();
        //创建对应表和表字段
        RealmObjectSchema petSchema = schema.create("Pet")
                .addField("name", String.class, FieldAttribute.REQUIRED)
                .addField("type", String.class, FieldAttribute.REQUIRED);
        //将创建的表添加到Person表中
        schema.get("Person")
                .addRealmListField("pets", petSchema)
                .transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(DynamicRealmObject obj) {
                        if (obj.getString("fullName").equals("Senior PersonFull")) {
                            DynamicRealmObject pet = realm.createObject("Pet");
                            pet.setString("name", "Jemos");
                            pet.setString("type", "cat");
                            obj.getList("pets").add(pet);
                        }
                    }
                });
    }

    /**
     * ①将fullName置为空
     * ②将type类型变为int（通过一个中间变量的转换，然后再将model中的type删除，然后将变量rename为type(此时model中的String类型需要自己手动改为int)）
     *
     * @param realm
     */
    private void version3(DynamicRealm realm) {
        RealmSchema schema = realm.getSchema();
        RealmObjectSchema personSchema = schema.get("Person");
        personSchema.setNullable("fullName", true);//fullName 设置为null,需要把model中的  @Required注解删除，才可以

        //change type from String to int
        schema.get("Pet")
                .addField("type_tmp", int.class)
                .transform(new RealmObjectSchema.Function() {
                    @Override
                    public void apply(DynamicRealmObject obj) {
                        String oldType = obj.getString("type");
                        if (oldType.equals("dog")) {
                            obj.setLong("type_tmp", 1);
                        } else if (oldType.equals("cat")) {
                            obj.setInt("type_tmp", 2);
                        } else if (oldType.equals("hamster")) {
                            obj.setInt("type_tmp", 3);
                        }
                    }
                })
                .removeField("type")
                .renameField("type_tmp", "type");

    }
}
