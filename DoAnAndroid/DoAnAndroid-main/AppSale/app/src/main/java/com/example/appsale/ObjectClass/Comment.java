package com.example.appsale.ObjectClass;

import java.util.Date;



public class Comment {

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getProduct_Id() {
        return product_Id;
    }

    public String getUsername() {
        return username;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public Date getCreateed_data() {
        return createed_data;
    }

    public int getStar() {
        return star;
    }

    private int id;
    private String content;
    private int product_Id;
    private String username;
    private int user_Id;
    private Date createed_data;
    private int star;
    public Comment(int id, String content, int product_Id, int user_Id, Date createed_data, int star) {
        this.id = id;
        this.content = content;
        this.product_Id = product_Id;
        this.user_Id = user_Id;
        this.createed_data = createed_data;
        this.star = star;
    }


    public Comment(String content, String userName, int star) {
        this.content = content;
        this.username = userName;
        this.star = star;
    }
}
