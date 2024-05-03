package com.example.appsale.ObjectClass;


import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private int id;
    private int user_id;
    private Long totalprice;
    private String created_date;
    private String address = "";

    public Order(int id, Long totalprice, String created_date) {
        this.id = id;
        this.totalprice = totalprice;
        this.created_date = created_date;
    }

    public Order(int id, int user_id, String address , Long totalprice, Date created_dates) {
        this.id = id;
        this.user_id = user_id;
        this.totalprice = totalprice;
        this.created_date = created_date;
        this.address = address;
    }
    public Order(int id, int user_id, Long totalprice, String created_date) {
        this.id = id;
        this.user_id = user_id;
        this.totalprice = totalprice;
        this.created_date = created_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Long getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Long totalprice) {
        this.totalprice = totalprice;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
