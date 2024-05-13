package org.pt.treeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department {
    private int id;
    private String name;
    private int parentId;
    private List<Department> children;

    public Department(int id, String name, int parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children=new ArrayList<>();
    }

    public void addChild(Department department){
        children.add(department);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<Department> getChildren() {
        return children;
    }

    public void setChildren(List<Department> children) {
        this.children = children;
    }

    public static Department covertTree(List<Department> departments){
        Map<Integer,Department> map=new HashMap<>();
        Department root=null;
        for (Department d:departments) {
            int id=d.getId();
            int parentId=d.getParentId();
            Department node = new Department(d.getId(), d.getName(), d.getParentId());
            map.put(id,node);
            if(parentId==0){
                root=node;
            }else {
                Department department = map.get(parentId);
                if(department!=null){
                    department.addChild(node);
                }
            }
        }
        return root;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", children=" + children +
                '}';
    }
}
