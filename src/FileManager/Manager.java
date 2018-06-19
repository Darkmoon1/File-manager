package FileManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Iterator;

public class Manager extends JFrame {
    private UI ui;
    private JSONObject catelog;

    public Manager(){
        ui = new UI(GetFileTracer(),catelog);
    }

    public DefaultMutableTreeNode GetFileTracer(){

        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("文件池");
        try {
            catelog = new JSONObject(Const.content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iterator;
        iterator = catelog.keys();
        while (iterator.hasNext()){
            MyFile sonNodeFile = new MyFile();
            sonNodeFile.key = iterator.next();
            DefaultMutableTreeNode sonNode = new DefaultMutableTreeNode(sonNodeFile);
            try {
                JSONArray jsonArray = catelog.getJSONArray(sonNodeFile.key);
                if (jsonArray==null){
                    continue;
                }
                for (int i = 0;i< jsonArray.length();i++){
                    MyFile one = new MyFile();
                    one.key = jsonArray.getJSONObject(i).keys().next().toString();
                    one.path = jsonArray.getJSONObject(i).getString(one.key);
                    sonNode.add(new DefaultMutableTreeNode(one));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            topNode.add(sonNode);
        }

        return  topNode;

    }




}
