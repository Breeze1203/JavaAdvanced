package com.example.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Organization implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer parentId;
    private List<Organization> children;

    public Organization(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.children = new ArrayList<>();
    }

    private void addChild(Organization o){
        children.add(o);
    }

    public static Organization covertToTree(List<Organization> organizationList) {
        Map<Integer, Organization> map = new HashMap<>();
        Organization root = null;
        for (Organization o : organizationList) {
            int id = o.getId();
            int parentId = o.getParentId();
            Organization organization = new Organization(id, o.getName(), parentId);
            map.put(id, organization);
            if (parentId == -1) {
                root = organization;
            } else {
                Organization organization1 = map.get(parentId);
                if (organization1 != null) {
                    organization1.addChild(organization);
                }
            }
        }
        return root;
    }
}
