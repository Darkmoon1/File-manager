package FileManager;

import com.sun.deploy.panel.JreTableModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.reflect.generics.tree.Tree;
import tabletest.Test1;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;


public class UI extends JFrame {

    DefaultMutableTreeNode treeRoot;
    DefaultTreeModel model;

    JTree tree;
    JButton addNewFile;
    JButton deleteFile;
    JButton addNewClass;
    JButton deleteClass;
    JButton openFile;
    JTextArea outConsole;

    JTable infoTable;
    DefaultTableModel tableModel;
    private JSONObject catelog;
    Object[][] list = { {} };

    public UI(DefaultMutableTreeNode treeNode,JSONObject catelog){
        this.catelog = catelog;
        treeRoot = treeNode;

        model = new DefaultTreeModel(treeRoot);
        tree = new JTree(model);
        tree.addTreeSelectionListener(new TreeListener());

        JScrollPane scrollTreePane = new JScrollPane(tree);
        scrollTreePane.setPreferredSize(new Dimension(200, 720));


        String[] row = { "属性", "值"};
        tableModel = new DefaultTableModel(list, row);

        infoTable = new JTable(tableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        infoTable.setRowHeight(30);

        JScrollPane scrollTable = new JScrollPane(infoTable);
        scrollTable.setPreferredSize(new Dimension(520, 300));

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


        outConsole = new JTextArea();
        JScrollPane OCPane = new JScrollPane(outConsole);
        OCPane.setPreferredSize(new Dimension(520, 200));

        JPanel toolBarPane = new JPanel();
        openFile = new JButton("打开文件");
        openFile.addActionListener(new openFileListener());

        toolBarPane.add(BorderLayout.NORTH,toolBar);
        toolBarPane.add(scrollTable);
        toolBarPane.add(openFile);
        toolBarPane.add(OCPane);


        toolBarPane.setPreferredSize(new Dimension(520,720));
//        toolBarPane.setBackground(Color.DARK_GRAY);

        JFrame f = new JFrame("JTreeDemo");
        f.add(BorderLayout.WEST, scrollTreePane);
        f.add(BorderLayout.EAST,toolBarPane);

        //将容器外部特性实例化
        f.setTitle("FileManager");
        f.setSize(740,620);//设窗体的大小     宽和高
        f.setVisible(true);//设定窗体的可视化
        //设置窗体的关闭方式
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void RefreshTree(){
        treeRoot.removeAllChildren();
        try {
            catelog = new JSONObject(Const.readToString(Const.jsonPath));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Iterator<String> iterator;
        iterator = catelog.keys();
        while (iterator.hasNext()) {
            MyFile sonNodeFile = new MyFile();
            sonNodeFile.key = iterator.next();
            DefaultMutableTreeNode sonNode = new DefaultMutableTreeNode(sonNodeFile);
            try {
                JSONArray jsonArray = catelog.getJSONArray(sonNodeFile.key);
                if (jsonArray == null) {
                    continue;
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    MyFile one = new MyFile();
                    one.key = jsonArray.getJSONObject(i).keys().next().toString();
                    one.path = jsonArray.getJSONObject(i).getString(one.key);
                    sonNode.add(new DefaultMutableTreeNode(one));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            treeRoot.add(sonNode);
        }
        model.reload(treeRoot);
    }


    class TreeListener implements TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent arg0) {
            TreePath path = tree.getSelectionPath();
            if (path == null)
                return;
            DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path
                    .getLastPathComponent();
            if (!selectnode.isLeaf()) {
                // 这里加上类型判断
                tableModel.setRowCount(0);
                int countChildren = selectnode.getChildCount();
                Object re[][]={
                        {"类名",selectnode.toString()},
                        {"类中的文件数量",countChildren}
                };
                list = re;
                for (Object[] one:list){
                    tableModel.addRow(one);
                }
            }
            else {
                tableModel.setRowCount(0);
                String filePath = ((MyFile)selectnode.getUserObject()).path;
                File file_select = new File(filePath);
                if (file_select == null){
                    outConsole.append("空文件");
                    return;
                }
                Object re[][]={
                        {"文件名",file_select.getName()},
                        {"是否存在",file_select.exists()},
                        {"文件大小",file_select.length()},
                        {"最后修改日期",new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(file_select.lastModified())},
                        {"是否可读",file_select.canRead()},
                        {"是否可写",file_select.canWrite()},
                        {"是否隐藏",file_select.isHidden()}

            };
                list = re;
                for (Object[] one:list){
                    tableModel.addRow(one);
                }
            }

        }
    }

    class addNewFileListener implements ActionListener{
        String inputFilePath;
        String inputClassName;
        String inputFileName;
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog jd = new JDialog();
            jd.setBounds(320, 180, 260, 220);
            jd.setTitle("弹出文本框");
            JPanel contentPane = new JPanel();
            contentPane.setPreferredSize(new Dimension(260,80));
            contentPane.setLayout(new GridLayout(3, 2));
            jd.add(contentPane);
            JLabel filePath = new JLabel("文件路径");
            contentPane.add(filePath);
            JTextField filePathInput = new JTextField(10);
            contentPane.add(filePathInput);
            JLabel fileName = new JLabel("备注名字");
            contentPane.add(fileName);
            JTextField fileNameInput = new JTextField(10);
            contentPane.add(fileNameInput);
            JLabel className = new JLabel("文件分类");
            contentPane.add(new JLabel("分类"));
            JComboBox classNameInput = new JComboBox();
            Iterator<String> iterator;
            iterator = catelog.keys();
            while (iterator.hasNext()){
                classNameInput.addItem(iterator.next());
            }
            contentPane.add(classNameInput);
            JButton confirmButton = new JButton("确认");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    inputFilePath = filePathInput.getText().replaceAll("\\\\","/");
                    inputClassName = classNameInput.getSelectedItem().toString();
                    inputFileName = fileNameInput.getText();
                    Const.AddFileToJSON(inputClassName,inputFileName,inputFilePath);
                    RefreshTree();
                    jd.dispose();
                }
            });
            jd.add(BorderLayout.SOUTH,confirmButton);
            jd.setModal(true);//确保弹出的窗口在其他窗口前面
            jd.setVisible(true);
        }
    }

    class deleteFileListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath path = tree.getSelectionPath();
            if (path == null){
                outConsole.append("请选择文件\n");
                return;
            }
            DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path
                    .getLastPathComponent();
            if (selectnode.isLeaf()) {
                Const.ClearFile(((MyFile)selectnode.getUserObject()).path,((MyFile)selectnode.getUserObject()).key);
            } else {
                outConsole.append("请选择文件\n");
                return;
            }
            RefreshTree();

        }
    }

    class addNewClassListener implements ActionListener{
        String inputClassName;
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog jd = new JDialog();
            jd.setBounds(320, 180, 260, 120);
            jd.setTitle("弹出文本框");
            JPanel contentPane = new JPanel();
            contentPane.setPreferredSize(new Dimension(260,30));
            contentPane.setLayout(new GridLayout(1, 2));
            jd.add(contentPane);
            JLabel className = new JLabel("新类名");
            contentPane.add(className);
            JTextField classNameInput = new JTextField(10);
            contentPane.add(classNameInput);
            JButton confirmButton = new JButton("确认");
            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    inputClassName = classNameInput.getText();
                    Const.AddClassToJson(inputClassName);
                    RefreshTree();
                    jd.dispose();
                }
            });
            jd.add(BorderLayout.SOUTH,confirmButton);
            jd.setModal(true);//确保弹出的窗口在其他窗口前面
            jd.setVisible(true);
        }
    }

    class deleteClassListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath path = tree.getSelectionPath();
            if (path == null){
                outConsole.append("请选择分类\n");
                return;
            }
            DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path
                    .getLastPathComponent();
            if (!selectnode.isLeaf()) {
                Const.DeleteClass(selectnode.toString());
            } else {
                outConsole.append("请选择分类\n");
                return;
            }
            RefreshTree();
        }
    }

    class openFileListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath path = tree.getSelectionPath();
            if (path == null){
                outConsole.append("请选择文件\n");
                return;
            }
            DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path
                    .getLastPathComponent();
            if (!selectnode.isLeaf()) {
                outConsole.append("这不是一个文件\n");
                return;
            } else {
                String filePath = ((MyFile) selectnode.getUserObject()).path;
                if (path != null)
                    try {
                        Const.OpenFIle(filePath);
                        return;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                else
                    outConsole.append("路径不存在\n");
            }
        }
    }
}
