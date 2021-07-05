package cn.edu.bupt.pcsauth.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author arron
 * @date 19-6-3
 * @description 树节点
 */
@Data
public class TreeNode implements Comparable<TreeNode>{
    protected int id;
    protected int parentId;
    protected int sort;
    protected List<TreeNode> children = new ArrayList<TreeNode>();
    public void add(TreeNode node) {
        children.add(node);
    }

    @Override
    public int compareTo(TreeNode o) {
        return this.sort - o.sort;
    }
}
