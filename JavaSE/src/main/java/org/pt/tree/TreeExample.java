package org.pt.tree;

import java.util.Arrays;
import java.util.List;

public class TreeExample {
    public static void main(String[] args) {
        List<Department> departmentList = Arrays.asList(
                new Department(1, "总部", 0),
                new Department(2, "财务部", 1),
                new Department(3, "人力资源部", 1),
                new Department(4, "市场部", 1),
                new Department(5, "技术部", 1),
                new Department(6, "研发部", 5),
                new Department(7, "测试部", 6)
        );
        TreeNode root = TreeConverter.convertToTree(departmentList);
        System.out.println(root);

        // 打印树状结构
        printTree(root, 0);
    }

    public static void printTree(TreeNode node, int level) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("  ");
        }

//        Department department = node.getDepartment();
//        System.out.println(indent.toString() + department.getName());
//
//        List<TreeNode> children = node.getChildren();
//        for (TreeNode child : children) {
//            printTree(child, level + 1);
//        }
    }
}
