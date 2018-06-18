package javaTest;

public class GirlStudent extends Student{
    public GirlStudent(){
        super.setSex(false);
    }
    public void singAndDance(){
        System.out.println(getName()+"is singing and dancing");
    }
}
