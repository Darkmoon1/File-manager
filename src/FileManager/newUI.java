package FileManager;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class newUI {
  private JPanel ToolBar;
  private JScrollPane treeSpace;
  private JScrollPane FileInformation;
  private JTree treeInformation;
  private JTextArea ConsolePlay;
  private JTextField Console;
  private JButton addFile;
  private JButton deleteFile;
  private JButton addClass;
  private JButton deleteClass;
  private JButton save;
  private JButton load;
  private JButton openFile;
  private JButton searchButton;
  private JTextField search;
  private JTable InfoTable;
  private JScrollPane ConsoleSArea;
  private JPanel ConsoleArea;

  String hint =  "> ";
  DefaultMutableTreeNode treeRoot;
  DefaultTreeModel model;
  JTable infoTable;
  DefaultTableModel tableModel;
  private JSONObject catelog;

  Object[][] list = { {} };

  /**
   * 图标
   * @param args
   */
  private Border emptyBorder = BorderFactory.createEmptyBorder(0,0, 0, 0);
  private Icon addFileIcon = new ImageIcon("./img/addFile.png");
  private Icon addFilePressIcon = new ImageIcon("./img/addFilePress.png");
  private Icon deleteFileIcon = new ImageIcon("./img/deleteFile.png");
  private Icon deleteFilePressIcon = new ImageIcon("./img/deleteFilePress.png");
  private Icon addClassIcon = new ImageIcon("./img/addClass.png");
  private Icon addClassPressIcon = new ImageIcon("./img/addClassPress.png");
  private Icon deleteClassIcon = new ImageIcon("./img/deleteClass.png");
  private Icon deleteClassPressIcon = new ImageIcon("./img/deleteClassPress.png");

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

  private TreePath findInPath(TreePath treePath, String str) {
    Object object = treePath.getLastPathComponent();
    if (object == null) {
      return null;
    }

    String value = object.toString();
    if (str.equals(value)) {
      return treePath;
    } else {
      DefaultTreeModel model = (DefaultTreeModel)treeInformation.getModel();
      int n = model.getChildCount(object);
      for (int i = 0; i < n; i++) {
        Object child = model.getChild(object, i);
        TreePath path = treePath.pathByAddingChild(child);

        path = findInPath(path, str);
        if (path != null) {
          return path;
        }
      }
      return null;
    }
  }

  public newUI(DefaultMutableTreeNode treeNode,JSONObject catelog){

    this.catelog = catelog;

    /**
     *  生成主界面
     */

    JFrame mainFrame = new JFrame("FileManager");
    mainFrame.setLayout(new GridBagLayout());
    mainFrame.setSize(1200,700);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent){
        System.exit(0);
      }
    });


    /****
     * 添加tool相关
     */
    ToolBar = new JPanel(new GridLayout());
    ToolBar.setLayout(new GridBagLayout());
    ToolBar.setBackground(Color.WHITE);
    mainFrame.add(ToolBar,new GBC(0,0,2,1).setFill(GBC.BOTH).setIpad(200, 50).setWeight(100, 0));
    addFile = new JButton();
    deleteFile = new JButton();
    addClass = new JButton();
    deleteClass = new JButton();
    openFile = new JButton("打开文件");
    save = new JButton("备份");
    load = new JButton("还原");
    searchButton = new JButton("搜索");
    search = new JTextField();
    search.setFont(new Font("宋体", Font.PLAIN, 19) );
    ToolBar.add(addFile,new GBC(0,0).setInsets(0,20,0,5).setWeight(1,0));
    ToolBar.add(deleteFile,new GBC(1,0).setInsets(0,5,0,5).setWeight(1,0));
    ToolBar.add(openFile,new GBC(2,0).setInsets(0,5,0,5).setWeight(1,0));
    ToolBar.add(addClass,new GBC(3,0).setInsets(0,5,0,5).setWeight(1,0));
    ToolBar.add(deleteClass,new GBC(4,0).setInsets(0,5,0,5).setWeight(1,0));
    ToolBar.add(save,new GBC(5,0).setInsets(0,5,0,5).setWeight(1,0));
    ToolBar.add(load,new GBC(6,0).setInsets(0,5,0,5).setWeight(1,0));
    ToolBar.add(searchButton,new GBC(7,0).setInsets(0,5,0,5).setWeight(1,0));
    ToolBar.add(search,new GBC(8,0,3,1).setIpad(300,0).setInsets(0,20,0,20).setWeight(100,0).setFill(GBC.HORIZONTAL));

    /**
     * 按钮图标设置
     */
    addFile.setIcon(addFileIcon);
    addFile.setPressedIcon(addFilePressIcon);
    addFile.setBorder(emptyBorder);
    addFile.setBorderPainted(false);
    addFile.setContentAreaFilled(false);

    deleteFile.setIcon(deleteFileIcon);
    deleteFile.setPressedIcon(deleteFilePressIcon);
    deleteFile.setBorder(emptyBorder);
    deleteFile.setBorderPainted(false);
    deleteFile.setContentAreaFilled(false);

    addClass.setIcon(addClassIcon);
    addClass.setPressedIcon(addClassPressIcon);
    addClass.setBorder(emptyBorder);
    addClass.setBorderPainted(false);
    addClass.setContentAreaFilled(false);

    deleteClass.setIcon(deleteClassIcon);
    deleteClass.setPressedIcon(deleteClassPressIcon);
    deleteClass.setBorder(emptyBorder);
    deleteClass.setBorderPainted(false);
    deleteClass.setContentAreaFilled(false);



    /**
     * 添加树
     */
    treeRoot = treeNode;
    model = new DefaultTreeModel(treeRoot);
    treeInformation = new JTree(model);
    treeSpace = new JScrollPane(treeInformation);
    mainFrame.add(treeSpace,new GBC(0,1,1,2).setIpad(100,700).setWeight(0,100).setFill(GBC.BOTH));

    /**
     * 添加FileInformation相关内容
     */
    String[] row = { "属性", "值"};
    tableModel = new DefaultTableModel(list, row);
    infoTable = new JTable(tableModel);
    FileInformation = new JScrollPane(infoTable);
    mainFrame.add(FileInformation,new GBC(1,1,1,1).setIpad(110,400).setWeight(100,100).setFill(GBC.BOTH));
    mainFrame.setVisible(true);

    /**
     * 添加Console相关内容
     */


    ConsolePlay = new JTextArea();
    Console = new JTextField();
    ConsolePlay.setEditable(false);
    Console.setFont(new Font("宋体", Font.PLAIN, 19));
    Console.setBorder(emptyBorder);
    ConsolePlay.setFont(new Font("宋体", Font.PLAIN, 19));
    ConsoleArea = new JPanel(new GridBagLayout());
    ConsoleArea.add(ConsolePlay,new GBC(0,1).setIpad(1100,160).setWeight(100,100).setFill(GBC.BOTH));
    ConsoleArea.add(Console,new GBC(0,0).setIpad(1100,0).setWeight(100,100).setFill(GBC.BOTH));
    ConsoleSArea = new JScrollPane(ConsoleArea);
    mainFrame.add(ConsoleSArea,new GBC(1,2,1,1).setIpad(1100,300).setWeight(100,100).setFill(GBC.BOTH));


    /**
     * 监听
     */
    addFile.addActionListener(new ActionListener() {           //添加文件的监听接口
      String inputFilePath;
      String inputClassName;
      String inputFileName;
      @Override
      public void actionPerformed(ActionEvent e) {
        JDialog jd = new JDialog();
        jd.setSize(700,500);
        jd.setResizable(true);
        jd.setLocationRelativeTo(mainFrame);
        jd.setTitle("选择文件");
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        jd.add(contentPane);
        JPanel Tool = new JPanel(new FlowLayout());
        //contentPane.add(filePath);
        JTextField filePathInput = new JTextField();
        Tool.add(filePathInput);
        //contentPane.add(filePathInput);
        contentPane.add(filePathInput,new GBC(0,0,5,1).setIpad(700,0).setWeight(100,0).setFill(GBC.BOTH));
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setControlButtonsAreShown(false);

        jfc.addActionListener(new AbstractAction() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (jfc.APPROVE_SELECTION.equals(e
                    .getActionCommand())) {
              //System.out.println("按了打开");
              String filePath = jfc.getSelectedFile().getAbsolutePath();
              filePathInput.setText(filePath);
            } else if (jfc.CANCEL_SELECTION.equals(e
                    .getActionCommand())) {

              //System.out.println("按了取消");
            }
          }
        });
        contentPane.add(jfc,new GBC(0,1,5,1).setIpad(700,250).setWeight(100,100).setFill(GBC.BOTH));
        JLabel fileName = new JLabel("备注名称:");
        fileName.setFont(new Font("宋体", Font.BOLD, 15));
        contentPane.add(fileName,new GBC(0,2).setInsets(0,12,0,5));
        JTextField fileNameInput = new JTextField();
        fileNameInput.setFont(new Font("宋体", Font.PLAIN, 15));
        contentPane.add(fileNameInput,new GBC(1,2).setIpad(200,0).setWeight(100,0).setFill(GBC.NONE));
        JLabel className = new JLabel("文件分类");
        className.setFont(new Font("宋体", Font.BOLD, 15));
        contentPane.add(className,new GBC(0,3));
        JLabel space = new JLabel();
        JComboBox classNameInput = new JComboBox();
        Iterator<String> iterator;
        iterator = catelog.keys();
        while (iterator.hasNext()){
          classNameInput.addItem(iterator.next());
        }
        contentPane.add(classNameInput,new GBC(1,3).setIpad(150,0).setWeight(1,0).setFill(GBC.NONE));
        contentPane.add(space,new GBC(2,3,3,1).setIpad(400,20).setWeight(0,0).setFill(GBC.BOTH));
        JButton confirmButton = new JButton("确认");
        confirmButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            inputFilePath = filePathInput.getText().replaceAll("\\\\","/");
            inputClassName = classNameInput.getSelectedItem().toString();
            inputFileName = fileNameInput.getText();
            Const.AddFileToJSON(inputClassName,inputFileName,inputFilePath);
            ConsolePlay.append("添加文件成功\n");
            RefreshTree();
            jd.dispose();
          }
        });
        contentPane.add(confirmButton,new GBC(1,5,1,1).setIpad(0,0).setWeight(0,0).setFill(GBC.NONE));
        jd.setModal(true);//确保弹出的窗口在其他窗口前面
        jd.setVisible(true);
      }
    });

    deleteFile.addActionListener(new ActionListener() {        //删除文件的监听接口
      @Override
      public void actionPerformed(ActionEvent e) {
        TreePath path = treeInformation.getSelectionPath();
        if (path == null){
          ConsolePlay.append("请选择文件\n");
          return;
        }
        DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path
                .getLastPathComponent();
        if (selectnode.isLeaf()) {
          Const.ClearFile(((MyFile)selectnode.getUserObject()).path,selectnode
                  .getUserObjectPath());
          ConsolePlay.append("文件删除成功\n");
        } else {
          ConsolePlay.append("请选择文件\n");
          return;
        }
        RefreshTree();
      }
    });

    addClass.addActionListener(new ActionListener() {          //添加分类的监听接口
      String inputClassName;
      @Override
      public void actionPerformed(ActionEvent e) {
        JDialog jd = new JDialog();
        jd.setBounds(320, 180, 260, 120);
        jd.setTitle("新建分类");
        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setPreferredSize(new Dimension(260,30));
        jd.add(contentPane);
        JLabel className = new JLabel("新类名");
        className.setFont(new Font("宋体", Font.BOLD, 15));
        contentPane.add(className,new GBC(0,0));
        JTextField classNameInput = new JTextField(10);
        contentPane.add(classNameInput,new GBC(1,0).setInsets(0,10,0,0));
        JButton confirmButton = new JButton("确认");
        confirmButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            inputClassName = classNameInput.getText();
            Const.AddClassToJson(inputClassName);
            ConsolePlay.append("添加分类成功\n");
            RefreshTree();
            jd.dispose();
          }
        });
        contentPane.add(confirmButton,new GBC(0,1));
        jd.setModal(true);//确保弹出的窗口在其他窗口前面
        jd.setVisible(true);
      }
    });

    deleteClass.addActionListener(new ActionListener() {       //删除分类的监听接口
      @Override
      public void actionPerformed(ActionEvent e) {
        TreePath path = treeInformation.getSelectionPath();
        if (path == null){
          ConsolePlay.append("请选择分类\n");
          return;
        }
        DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path
                .getLastPathComponent();
        if (!selectnode.isLeaf()) {
          Const.DeleteClass(selectnode.toString());
          ConsolePlay.append("删除成功\n");
        } else {
          ConsolePlay.append("请选择分类\n");
          return;
        }
        RefreshTree();
      }
    });

    openFile.addActionListener(new ActionListener() {          //打开文件的监听接口
      @Override
      public void actionPerformed(ActionEvent e) {
        TreePath path = treeInformation.getSelectionPath();
        if (path == null){
          ConsolePlay.append("请选择文件\n");
          return;
        }
        DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path
                .getLastPathComponent();
        if (!selectnode.isLeaf()) {
          ConsolePlay.append("这不是一个文件\n");
          return;
        } else {
          String filePath = ((MyFile) selectnode.getUserObject()).path;
          if (path != null)
            try {
              Const.OpenFIle(filePath);
              ConsolePlay.append("打开成功\n");
              return;
            } catch (IOException e1) {
              e1.printStackTrace();
            }
          else
            ConsolePlay.append("路径不存在\n");
        }
      }
    });

    save.addActionListener(new ActionListener() {                //备份文件的监听接口
      @Override
      public void actionPerformed(ActionEvent e) {
        Const.fileToZip(Const.pool,Const.rootPath);
        ConsolePlay.append("******************压缩完毕********************\n备份成功\n");
      }
    });

    load.addActionListener(new ActionListener() {               //还原文件的监听接口
      @Override
      public void actionPerformed(ActionEvent e) {
        File bakeFile = new File(Const.rootPath + "/allFiles.zip");
        if (bakeFile.exists()){
          try {
            Const.unZipFiles(bakeFile,Const.pool);
            ConsolePlay.append("******************解压完毕********************\n恢复成功\n");
            RefreshTree();
          } catch (IOException e1) {
            e1.printStackTrace();
          }
        }else {
          ConsolePlay.append("备份不存在\n");
        }
      }
    });

    searchButton.addActionListener(new ActionListener() {      //搜索文件的监听接口
      String query;
      @Override
      public void actionPerformed(ActionEvent e) {
        query = search.getText();
        if (query.isEmpty()){
          ConsolePlay.append("请输入查询内容\n");
        }else {
          String res = Const.SearchFile(query);
          if (res.equals("查询内容： " + query + "\n")){
            ConsolePlay.append("找不到该关键字\n");
          }else {
            ConsolePlay.append(res);
          }
        }
      }
    });

    treeInformation.addTreeSelectionListener(new TreeSelectionListener() {     //树监听接口
      @Override
      public void valueChanged(TreeSelectionEvent e) {
        TreePath path = treeInformation.getSelectionPath();
        if (path == null)
          return;
        DefaultMutableTreeNode selectnode = (DefaultMutableTreeNode) path
                .getLastPathComponent();
        if (!selectnode.isLeaf()) {
          // 这里加上类型判断
          tableModel.setRowCount(0);
          int countChildren = selectnode.getChildCount();
          Object re[][] = {
                  {"类名", selectnode.toString()},
                  {"类中的文件数量", countChildren}
          };
          list = re;
          for (Object[] one : list) {
            tableModel.addRow(one);
          }
        } else {
          tableModel.setRowCount(0);
          String filePath = ((MyFile) selectnode.getUserObject()).path;
          File file_select = new File(filePath);
          if (file_select == null) {
            ConsolePlay
                    .append("空文件\n");
            return;
          }
          Object re[][] = {
                  {"文件名", file_select.getName()},
                  {"是否存在", file_select.exists()},
                  {"文件大小", file_select.length()},
                  {"最后修改日期", new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(file_select.lastModified())},
                  {"是否可读", file_select.canRead()},
                  {"是否可写", file_select.canWrite()},
                  {"是否隐藏", file_select.isHidden()}

          };
          list = re;
          for (Object[] one : list) {
            tableModel.addRow(one);
          }
        }
      }
    });

    Console.addFocusListener(new JTextFieldHintListener(hint,Console));

    Console.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {

      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER){
          if (Console.getText().isEmpty()){
            return;
          }
          ConsolePlay.append("get ENTER\n" + Console.getText() + "\n");
          String[] keys = Const.ConsoleAnalysze(Console.getText());
          Console.setText(null);
          switch (keys[0]){
            case "find": {
              if (keys.length<2){
                ConsolePlay.append("请输入路径\n");
                return;
              }
              String[] paths = keys[1].split("-->");
              if (paths.length < 2) {
                ConsolePlay.append("路径不正确\n");
                return;
              }
              int i = 1;
              TreePath path = findInPath(new TreePath(model.getRoot()), paths[i]);
              while (i < paths.length) {
                path = findInPath(path, paths[i]);
                i++;
              }
              if (path != null) {
                treeInformation.setSelectionPath(path);
                treeInformation.scrollPathToVisible(path);
              } else {
                ConsolePlay.append("该路径不存在\n");
              }
            }
            break;
            case "clear":{
              ConsolePlay.setText(null);
            }
            break;
              default:{
                ConsolePlay.append("不存在该命令: " + keys[0] + "\n");
              }
              break;
          }
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {

      }
    });








  }
}