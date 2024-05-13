package org.pt.tree;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private Department department;
    private List<TreeNode> children;

    public TreeNode(Department department) {
        this.department = department;
        this.children=new ArrayList<>();
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void addChild(TreeNode child){
        children.add(child);
    }
}
