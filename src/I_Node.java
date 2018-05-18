import javax.swing.*;
import java.io.File;

public interface I_Node {
    //获取指定索引节点文件的子文件节点
    public abstract Object getChild(String fileKind, int index);
    //获取指定子文件
    public abstract Object getChildFile( int index) ;
    //返回根节点
    public abstract Object getRoot();
    //获取子节点数目
    public abstract int getChildCount(String fileKind);
    //获取文件名
    public abstract String toString();
    //获取文件图标
    public abstract Icon getIcon();
    //判断是否是叶节点
    public abstract boolean isLeaf();
    //获取文件路径
    public abstract String getPath();
    //获取父节点
    public abstract Object getParent();
    //获取当前节点
    public abstract Object getCurrent();
    //添加孩子节点
    public abstract void addChild(I_Node node);
    //获取当前文件
    public abstract File getFile();
    //删除节点文件
    public abstract void deleteChild(I_Node node);
    //获取文件大小
    public abstract long getSize();
    //删除所有孩子节点
    public abstract void removeAllChildren();
}
