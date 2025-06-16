package org.pt.treeNode;

import org.pt.treeNode.Department;


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
        Department department = Department.covertTree(departmentList);
        System.out.println(department);

        printTree(department, 0);
    }

    public static void printTree(Department node, int level) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < level; i++) {
            indent.append("  ");
        }
        System.out.println(indent.toString() + node.getName());

        List<Department> children = node.getChildren();
        for (Department child : children) {
            printTree(child, level + 1);
        }
    }
}
