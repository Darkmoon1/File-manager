package javaTest;

import java.util.Random;

public class javaTest {
    private static double Y;
    private static double U;
    private static double V;
    public static void main(String[] args) {
        RGBtoYUN(new Red());
        Color red = YuNtoRGB(Y,U,V);
        System.out.println(red.getRed()+" "+red.getGreen()+ " "+red.getBlue());

        RGBtoYUN(new Green());
        Color green = YuNtoRGB(Y,U,V);
        System.out.println(green.getRed()+" "+green.getGreen()+ " "+green.getBlue());

        RGBtoYUN(new Blue());
        Color blue = YuNtoRGB(Y,U,V);
        System.out.println(blue.getRed()+" "+blue.getGreen()+ " "+blue.getBlue());
    }

    private static void RGBtoYUN(Color c){
        Y = 0.257 * c.getRed() + 0.504 * c.getGreen() + 0.098 * c.getBlue() + 16;
        U = 128 - 0.148 * c.getRed() - 0.291 * c.getGreen() + 0.439 * c.getBlue();
        V = 0.439 * c.getRed() - 0.368 * c.getGreen() -0.071 * c.getBlue() + 128;
        System.out.println(c.getName()+" to Y:"+ Y + "U:"+U+"V:"+V);
    }
    private static Color YuNtoRGB(double Y,double U,double V){
        Color c = new Color();
        c.setRed(1.164 * (Y-16)+ 1.596 *(V-128));
        c.setGreen(1.164*(Y-16)-0.813*(V-128)-0.391*(U-128));
        c.setBlue(1.164*(Y-16)+2.018*(U-128));
        return c;
    }
}
