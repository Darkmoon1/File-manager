package FileManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;

public class Const {
    public final static String rootPath = "C:\\Users\\86427\\Documents\\GitHub\\File-manager\\src\\FileManager";
    public final static String jsonPath = rootPath + "\\config.json";
    public final static String content = readToString(jsonPath);
    public final static String pool = rootPath + "\\pool\\";
    public static boolean lock = false;

    public static String readToString(String fileName) {
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

    private static String CutFile(String path)
    {
        File file1 = new File(path);
        File file2 = new File(pool + file1.getName());
        file1.deleteOnExit();
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        byte[] bytes = new byte[1024];
        int temp = 0;
        try {
            inputStream = new FileInputStream(file1);
            fileOutputStream = new FileOutputStream(file2);
            while((temp = inputStream.read(bytes)) != -1){
                fileOutputStream.write(bytes, 0, temp);
                fileOutputStream.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file2.getName();
    }

    public static void SaveDataToFile(String data) {
        BufferedWriter writer = null;
        File file = new File(jsonPath);
        //写入
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "UTF-8"));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void AddFileToJSON(String className,String fileName,String path)
    {
        String name = CutFile(path);
        try {
            JSONObject jsonObject1 = new JSONObject(readToString(jsonPath));
            if(jsonObject1.has(className))
            {
                JSONObject js = new JSONObject();
                JSONArray className1 = jsonObject1.getJSONArray(className);
                js.put(fileName,pool + name);
                className1.put(js);
                jsonObject1.remove(className);
                jsonObject1.put(className,className1);
            }
            else
            {
                JSONArray jsonArray = new JSONArray();
                JSONObject one = new JSONObject();
                one.put(fileName,pool + name);
                jsonArray.put(one);
                jsonObject1.put(className,jsonArray);
            }
            String newJson = jsonObject1.toString();
            SaveDataToFile(newJson);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static void AddClassToJson(String className){
        try {
            JSONObject jsonObject1 = new JSONObject(readToString(jsonPath));
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(new JSONObject("{\"分类说明\":\"\"}"));
            jsonObject1.put(className,jsonArray);

            String newJson = jsonObject1.toString();
            SaveDataToFile(newJson);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static void DeleteClass (String className)
    {
        try {
            JSONObject jsonObject1 = new JSONObject(readToString(jsonPath));
            if(jsonObject1.has(className))
            {
                jsonObject1.remove(className);
            }
            else
            {
                System.out.println("此类不存在");
            }
            String newJson = jsonObject1.toString();
            SaveDataToFile(newJson);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public static void ClearFile (String fileName,String keys)
    {
        File file1 = new File(pool + fileName);
        try {
            JSONObject jsonObject1 = new JSONObject(readToString(jsonPath));
            jsonObject1.remove(keys);
            if (file1.exists())
            {
                file1.delete();
            }
            else
            {
                System.out.println("文件不存在");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }


    public static void OpenFIle(String Path) throws IOException {
        Desktop.getDesktop().open(new File(Path));
    }
}

