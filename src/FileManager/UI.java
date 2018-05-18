package FileManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.reflect.generics.tree.Tree;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UI extends JFrame {

    DefaultMutableTreeNode treeRoot;
    Container container;

    public UI(DefaultMutableTreeNode treeNode){

        treeRoot = treeNode;
        final JTree tree = new JTree(treeRoot);
//        tree.addTreeSelenectionListener(new TreeListener());
        tree.addMouseListener(new TreeListener(tree));
        JScrollPane scrolltree = new JScrollPane(tree);
        scrolltree.setPreferredSize(new Dimension(200, 300));

        JTable jTable = new JTable();
        JScrollPane scrollTable = new JScrollPane(jTable);
        scrollTable.setPreferredSize(new Dimension(500, 500));
//        jTable.setPreferredSize(new Dimension(360,360));

        JFrame f = new JFrame("JTreeDemo");
        f.add(BorderLayout.WEST, scrolltree);
        f.add(BorderLayout.CENTER, scrollTable);

        //将容器外部特性实例化
        f.setTitle("JPanel面板的案例");
        f.setSize(720,720);//设窗体的大小     宽和高
        f.setVisible(true);//设定窗体的可视化
        //设置窗体的关闭方式
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public  DefaultMutableTreeNode getChildrenFiles(String parentName,DefaultMutableTreeNode parentNode){

        JSONObject jsonObject;
        JSONArray jsonArray;

        try {
            jsonObject = new JSONObject(Const.content);
            jsonArray = jsonObject.getJSONArray(parentName);
            if (jsonArray==null){
                return parentNode;
            }
            for (int i = 0;i< jsonArray.length();i++){
                parentNode.add(new DefaultMutableTreeNode(jsonArray.getString(i)));
            }
            return parentNode;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parentNode;
    }


    class TreeListener implements MouseListener {
        JTree f;
        public TreeListener(JTree tree){
            f = tree;
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource()==f&&e.getClickCount()==2){
                TreePath selPath = f.getPathForLocation(e.getX(),e.getY());
                if (selPath!=null){
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                    if (parentNode.getChildCount()>1)
                        parentNode  = getChildrenFiles(parentNode.toString(),parentNode);

                }

            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
