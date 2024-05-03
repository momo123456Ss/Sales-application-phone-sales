package com.example.appsale.ObjectClass;

import java.io.Serializable;
import java.util.Date;


public class Manufacturer  implements Serializable {
    private int id = -1;
    private String name = "";
    private String image = "";

    public Manufacturer() {

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public Manufacturer(String name, String image) {
        this.name = name;
        this.image = image;
    }
    public Manufacturer(String image) {
        this.image = image;
    }

    public Manufacturer(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
