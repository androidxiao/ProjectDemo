package cn.realm.demo.com.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by chawei on 2017/8/29.
 */

public class Person extends RealmObject{
    private int id;
//    private String name;
    private int age;
    private String fullName;
    private RealmList<Pet>pets;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public RealmList<Pet> getPets() {
        return pets;
    }

    public void setPets(RealmList<Pet> pets) {
        this.pets = pets;
    }
}
