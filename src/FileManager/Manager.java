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

    public Manager(){
        ui = new UI(getFileTracer());
    }

    public DefaultMutableTreeNode getFileTracer(){

        DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("文件池");
        JSONObject jsonObject;
        Iterator<String> iterator;

        try {
            jsonObject = new JSONObject(Const.content);
            iterator = jsonObject.keys();
            while (iterator.hasNext()){
                topNode.add(new DefaultMutableTreeNode(iterator.next()));
            }
//            jsonArray = jsonObject.getJSONArray("分类1");
//            for (int i = 0;i< jsonArray.length();i++){
//                topNode.add(new DefaultMutableTreeNode(jsonArray.getString(i)));
//            }

            return  topNode;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
