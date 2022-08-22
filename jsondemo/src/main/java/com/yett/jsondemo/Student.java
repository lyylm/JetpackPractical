package com.yett.jsondemo;

import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("student_name")//可以自定义转换后的名称
    private String name;
    private  int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
