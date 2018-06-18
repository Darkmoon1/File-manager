package javaTest;

public class BoyStudent extends Student{
    public BoyStudent(){
        super.setSex(true);

    }
    public  void playGames(){
        System.out.println(getName()+"is playing games");
    }
}
