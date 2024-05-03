package com.example.appsale.ObjectClass;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id =-1;
    private String firstName = "";
    private String lastName = "";
    private String email = "";
    private String phone ="";
    private String username ="";
    private String password="";
    private Date createdDate;
    private boolean active;
    private int roleId = 0;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    private String roleName;

    public User(int id,String firstName, String lastName, String email, String phone, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.username = username;
    }

    public User(int id, String firstName, String lastName
            , String email, String phone, String username
            , String password, Date createdDate
            , boolean active, int roleId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.createdDate = createdDate;
        this.setActive(active);
        this.roleId = roleId;
    }

    public User(int id, String lastName, String email, String username, String roleName, boolean active) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.roleName = roleName;
        this.setActive(active);
    }

    public User(String lastName, String email, String username, String roleName) {
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.roleName = roleName;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
