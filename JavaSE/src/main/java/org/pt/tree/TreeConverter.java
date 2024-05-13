package org.pt.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeConverter {
    public static TreeNode convertToTree(List<Department> departmentList) {
        Map<Integer,TreeNode> map=new HashMap<>();
        TreeNode root=null;
        // 遍历取出来的集合
        for (Department d:departmentList){
            int departmentId = d.getId();
            TreeNode treeNode = new TreeNode(d);
            map.put(departmentId,treeNode);
            int parentId=d.getParentId();
            if(parentId==0){
                // 就是子节点
                root=treeNode;
            }else {
                TreeNode parentNode = map.get(parentId);
                if(parentNode!=null){
                    parentNode.addChild(treeNode);
                }
            }
        }
        return root;
    }
}
