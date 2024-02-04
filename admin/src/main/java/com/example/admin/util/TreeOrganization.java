package com.example.admin.util;

import com.example.admin.model.Organization;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ToString
@Data
public class TreeOrganization implements Serializable {
    private Organization organization;
    private List<TreeOrganization> children;

    public TreeOrganization(Organization organization) {
        this.organization = organization;
        this.children=new ArrayList<>();
    }

    public void addChild(TreeOrganization child){
        children.add(child);
    }

    public static TreeOrganization convertToTree(List<Organization> organizationList){
        Map<Integer, TreeOrganization> map = new HashMap<>();
        TreeOrganization root=null;
        for (Organization o:organizationList) {
            int id=o.getId();
            TreeOrganization treeNode = new TreeOrganization(o);
            int parentId=o.getParentId();
            map.put(id,treeNode);
            if(o.getParentId()==-1){
                root=treeNode;
            }else {
                TreeOrganization organization= map.get(parentId);
                if(organization!=null){
                    organization.addChild(treeNode);
                }
            }
        }
        return root;
    }
}
