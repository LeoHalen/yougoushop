package com.yougou.freemarker;

/**
 * 学生pojo(用于freemarker测试)
 *
 * @ProjectName: yougoushop
 * @Package: com.yougou.freemarker
 * @Description: java类作用描述
 * @Author: HALEN(lizhigang)
 * @CreateDate: 2018/4/18 10:00
 * <p>Copyright: Copyright (c) 2018</p>
 */
public class Student {

    private int id;
    private String name;
    private int age;
    private String address;

    public Student(int id, String name, int age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public Student() {
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
