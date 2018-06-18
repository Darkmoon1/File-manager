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
        ui = new UI(GetFileTracer());
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
            String key = iterator.next();
            DefaultMutableTreeNode sonNode = new DefaultMutableTreeNode(key);
            try {
                JSONArray jsonArray = catelog.getJSONArray(key);
                if (jsonArray==null){
                    continue;
                }
                for (int i = 0;i< jsonArray.length();i++){
                    sonNode.add(new DefaultMutableTreeNode(jsonArray.getString(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            topNode.add(sonNode);
        }

        return  topNode;

    }


//    public  DefaultMutableTreeNode getChildrenFiles(String parentNode){
//
//        JSONObject jsonObject;
//        JSONArray jsonArray;
//
//        try {
//            jsonObject = new JSONObject(Const.content);
//            jsonArray = jsonObject.getJSONArray(parentNode.toString());
//            if (jsonArray==null){
//                return parentNode;
//            }
//            for (int i = 0;i< jsonArray.length();i++){
//                parentNode.add(new DefaultMutableTreeNode(jsonArray.getString(i)));
//            }
//            scrolltree.updateUI();
//            return parentNode;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return parentNode;
//    }


}
