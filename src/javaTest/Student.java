package javaTest;

public class Student {
    private String name;
    private String school;
    private int age;
    private String birthday;
    private boolean sex;

    public Student(){

    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean getSex(){
        return sex;
    }
}
