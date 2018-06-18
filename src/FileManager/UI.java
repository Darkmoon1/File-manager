package FileManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.reflect.generics.tree.Tree;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UI extends JFrame {

    DefaultMutableTreeNode treeRoot;
    DefaultTreeModel model;

    JButton addNewFile;
    JButton deleteFile;
    JButton addNewClass;
    JButton deleteClass;


    public UI(DefaultMutableTreeNode treeNode){

        treeRoot = treeNode;
        final JTree tree = new JTree(treeRoot);
        tree.addTreeSelectionListener(new TreeListener(tree));

        model = new DefaultTreeModel(treeRoot);

        JScrollPane scrollTreePane = new JScrollPane(tree);
        scrollTreePane.setPreferredSize(new Dimension(200, 720));

        JTable jTable = new JTable();
        JScrollPane scrollTable = new JScrollPane(jTable);
        scrollTable.setPreferredSize(new Dimension(500, 500));

        JPanel toolBar = new JPanel(new GridLayout(1,4,20,10));
        addNewFile = new JButton("添加新文件");
        addNewFile.addActionListener(new addNewFileListener());
        deleteFile = new JButton("删除文件");
        deleteFile.addActionListener(new deleteFileListener());
        addNewClass = new JButton("新增分类");
        addNewClass.addActionListener(new addNewClassListener());
        deleteClass = new JButton("删除分类");
        deleteClass.addActionListener(new deleteClassListener());
        toolBar.add(addNewFile);
        toolBar.add(deleteFile);
        toolBar.add(addNewClass);
        toolBar.add(deleteClass);
        JPanel toolBarPane = new JPanel();
        toolBarPane.add(BorderLayout.NORTH,toolBar);
        toolBarPane.add(scrollTable);
        toolBarPane.setPreferredSize(new Dimension(500,720));
        toolBarPane.setBackground(Color.DARK_GRAY);

        JFrame f = new JFrame("JTreeDemo");
        f.add(BorderLayout.WEST, scrollTreePane);
        f.add(BorderLayout.EAST,toolBarPane);

        //将容器外部特性实例化
        f.setTitle("FileManager");
        f.setSize(700,720);//设窗体的大小     宽和高
        f.setVisible(true);//设定窗体的可视化
        //设置窗体的关闭方式
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }





    class TreeListener implements TreeSelectionListener {
        JTree f;
        public TreeListener(JTree tree){
            f = tree;
        }
        @Override
        public void valueChanged(TreeSelectionEvent e) {

        }
    }

    class addNewFileListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class deleteFileListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    class addNewClassListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    class deleteClassListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
