package FileManager;

import java.io.*;

public class Const {
    public final static String rootPath = "D:\\Documents\\GitHub\\File-manager\\src\\FileManager";
//    public final static String poolPath;
    public final static String content = readToString(rootPath + "\\config.json");
    public final static String pool = rootPath + "\\pool";

    private static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
