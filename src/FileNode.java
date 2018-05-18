import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Vector;

public class FileNode implements I_Node{
    private Vector<File> allFiles = new Vector<File>();
    private Vector<File> folder = new Vector<File>();
    private FileSystemView fileSystemView = FileSystemView.getFileSystemView();
    private File file = null;
    private long dirLenth = 0;
    public FileNode() {
        file = fileSystemView.getHomeDirectory();
        addChildren();
    }
    public FileNode(File file) {
        this.file=file;
        addChildren();
    }
    //添加单个孩子文件
    public void addChild(I_Node node) {
        allFiles.add(node.getFile());
    }
    //添加多个孩子文件
    private void addChildren() {
        File[] fileList = fileSystemView.getFiles(file, true);
        for (File file : fileList) {
            allFiles.add(file);
            if (file.isDirectory()
                    && !file.getName().toLowerCase().endsWith(".lnk")) {
                folder.add(file);
            }
        }
    }
    //根据文件类型获取孩子数
    public int getChildCount(String fileKind) {
        if (fileKind.equals("files")) {     //所有文件
            return allFiles.size();
        } else if (fileKind.equals("folder")) {  //文件夹类型
            return folder.size();
        } else {
            return 0;
        }
    }
    //根据文件类型获取指定孩子节点
    public Object getChild(String fileKind, int index) {
        if (fileKind.equals( "files")) {
            return new FileNode(allFiles.get(index));
        } else if (fileKind.equals("folder")) {
            return new FileNode(folder.get(index));
        } else {
            return null;
        }
    }
    //获取指定孩子文件
    public Object getChildFile( int index) {
        if(index<allFiles.size()){
            return allFiles.get(index);
        }
        return null;
    }
    //获取父节点
    public Object getParent() {
        return new FileNode(file.getParentFile());
    }
    //获取根目录
    public Object getRoot() {
        return this;
    }
    //获取文件名
    public String toString() {
        return fileSystemView.getSystemDisplayName(file);
    }
    //获取文件图标
    public Icon getIcon() {
        return fileSystemView.getSystemIcon(file);
    }
    //判断是否是叶节点
    public boolean isLeaf() {
        return folder.size() == 0;
    }
    //获取文件路径
    public String getPath() {
        return this.file.getPath();
    }
    //获取当前节点
    public Object getCurrent() {
        return this;
    }
    //获取当前文件
    public File getFile() {
        return this.file;
    }
    //删除指定节点
    public void deleteChild(I_Node node) {
        allFiles.remove(node.getFile());
    }
    //获取当前节点大小
    public long getSize() {
        if (this.getFile().isDirectory()) {
            return getDirSize(this.getFile().getPath());
        } else {
            return getFileSize();
        }
    }
    //获取文件大小
    private long getFileSize() {
        return this.getFile().length();
    }
    //获取文件夹大小
    private long getDirSize(String dir) {
        File[] fileList = new File(dir).listFiles();
        for (File file : fileList) {
            if (file.isDirectory()) {
                getDirSize(file.getPath());
            } else {
                dirLenth = dirLenth + file.length();
            }
        }
        return dirLenth;
    }
    //删掉所有子文件
    public void removeAllChildren() {
        this.allFiles.removeAllElements();
    }

}
