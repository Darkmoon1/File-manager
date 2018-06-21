package FileManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Const {
    public final static String rootPath =
            "./src/FileManager";
    public final static String pool = rootPath + "/pool/";
    public final static String jsonPath = pool + "/config.json";
    public final static String content = readToString(jsonPath);
    public final static String BakeFile = rootPath+"/BakeFile/";
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

    public static void ClearFile (String fileName,Object[] path)
    {

        File file1 = new File(pool + fileName);
        try {
            JSONObject jsonObject = new JSONObject(readToString(jsonPath));
            JSONArray jsonArray = jsonObject.getJSONArray(((MyFile)path[1]).key);
            for (int i = 0;i< jsonArray.length();i++) {
                String a = jsonArray.getJSONObject(i).keys().next().toString();
                String b = ((MyFile)path[2]).key;
                if(a.equals(b)){
                    jsonArray.remove(i);
                    break;
                }
            }
            if (file1.exists())
            {
                file1.delete();
            }
            else
            {
                System.out.println("文件已经被删除");
            }
            jsonObject.remove(((MyFile)path[1]).key);
            jsonObject.put(((MyFile)path[1]).key,jsonArray);
            String newJson = jsonObject.toString();
            SaveDataToFile(newJson);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    public static String SearchFile(String query){
        String resPool = "查询内容： " + query + "\n";
        int count = 0;
        try {
            JSONObject jsonObject = new JSONObject(readToString(jsonPath));
            Iterator<String> iterator;
            iterator = jsonObject.keys();
            while (iterator.hasNext()){
                String text = iterator.next();
                if (text.contains(query)){
                    resPool += "pool-->"+text + "\n";
                    count++;
                }
                JSONArray jsonArray = jsonObject.getJSONArray(text);
                for (int i=0;i<jsonArray.length();i++){
                    String key = jsonArray.getJSONObject(i).keys().next().toString();
                    if (key.contains(query)){
                        resPool += "pool-->" + text + "-->" + key + "\n";
                        count++;
                    }
                }
            }
        resPool += "共得到搜索结果: " + count + "\n";
        return resPool;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void OpenFIle(String Path) throws IOException {
        Desktop.getDesktop().open(new File(Path));
    }

    public static void fileToZip(String sourceFilePath,String zipFilePath){
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

        if(sourceFile.exists() == false){
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");
        }else{
            try {
                File zipFile = new File(zipFilePath + "/allFiles" + sdf.format(d) + ".bake");
                if(zipFile.exists()){
                    System.out.println(zipFilePath + "目录下存在名字为:" + zipFile.getName() +"打包文件.");
                }else{
                    File[] sourceFiles = sourceFile.listFiles();
                    if(null == sourceFiles || sourceFiles.length<1){
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                    }else{
                        fos = new FileOutputStream(zipFile);
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));
                        byte[] bufs = new byte[1024*10];
                        for(int i=0;i<sourceFiles.length;i++){
                            //创建ZIP实体，并添加进压缩包
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                            zos.putNextEntry(zipEntry);
                            //读取待压缩的文件并写进压缩包里
                            fis = new FileInputStream(sourceFiles[i]);
                            bis = new BufferedInputStream(fis, 1024*10);
                            int read = 0;
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){
                                zos.write(bufs,0,read);
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally{
                //关闭流
                try {
                    if(null != bis) bis.close();
                    if(null != zos) zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }

    }


    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }

    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        File deleteFiles = new File(pool);
        if (deleteFiles.exists()){
            deleteFile(deleteFiles);
        }
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));//解决中文文件夹乱码
        String name = zip.getName().substring(zip.getName().lastIndexOf('\\')+1, zip.getName().lastIndexOf('.'));

        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir  +"/"+ zipEntryName).replaceAll("\\*", "/");

            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
//          System.out.println(outPath);

            FileOutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        System.out.println("******************解压完毕********************");
        return;
    }

    public static String[] ConsoleAnalysze(String text){
        String[] res = text.split(" ");
        return res;
    }

    public static void sortJsonArray(String key){
        class JsonComparator implements Comparator<JSONObject> {

            @Override
            public int compare(JSONObject json1, JSONObject json2){
                String key1 = json1.keys().next().toString();
                String key2 = json2.keys().next().toString();
                if(key1.compareTo(key2) < 0){
                    return 1;
                }else if(key1.compareTo(key2) >0){
                    return -1;
                }
                return 0;
            }
        }
        try {
            JSONObject jsonObject = new JSONObject(readToString(jsonPath));
            JSONArray mJSONArray = jsonObject.getJSONArray(key);
            List<JSONObject> list = new ArrayList<JSONObject> ();
            JSONObject jsonObj = null;
            for (int i = 0; i < mJSONArray.length(); i++) {
                jsonObj = mJSONArray.optJSONObject(i);
                list.add(jsonObj);
            }
            //排序操作
            JsonComparator pComparator =  new JsonComparator();
            Collections.sort(list, pComparator);

            //把数据放回去
            mJSONArray = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                jsonObj = list.get(i);
                mJSONArray.put(jsonObj);
            }

            jsonObject.remove(key);
            jsonObject.put(key,mJSONArray);
            String newJson = jsonObject.toString();
            SaveDataToFile(newJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

