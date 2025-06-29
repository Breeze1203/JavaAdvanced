package org.pt.entity;

/**
 * @ClassName User
 * @Author pt
 * @Description
 * @Date 2025/6/28 21:22
 **/

public class User {
    private Integer id;
    private String name;
    private String email;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}