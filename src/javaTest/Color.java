package javaTest;

public class Color {
    private  double red;
    private  double green;
    private  double blue;
    private  String name;

    public String getName(){return  name;}

    public void setName(String name) {
        this.name = name;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getRed() {
        return red;
    }

    public double getBlue() {
        return blue;
    }

    public double getGreen() {
        return green;
    }

}
