package com.test.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by min on 2015/2/25.
 */
//@Entity
//@Table(name = "users")
public class UserEntity implements Serializable {

    private int id;

    private String name;

    private String sex;

    private int age;
//
//    private String accountNo;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
