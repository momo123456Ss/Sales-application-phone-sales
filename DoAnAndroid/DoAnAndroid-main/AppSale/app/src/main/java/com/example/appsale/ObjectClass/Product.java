package com.example.appsale.ObjectClass;

import java.io.Serializable;

public class Product  implements Serializable {
    private int id;//ma sp
    private int manufacturerId;
    private String name;
    private Long price;
    private String image;
    private String manufacturerName;
    private int manufacturer_id;
    private Double averageRating = 0.0;
    private int num = 0;
    private String size = "";
    private String weight = "";
    private String chipset = "";
    private String ram ="";
    private String storage ="";
    private String battery ="";
    private String charging ="";
    private String video ="";
    private int numComment;

    public Product(String name, Long price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
    public Product(int id, String name, Long price, String image, String manufacturerName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.manufacturerName = manufacturerName;
    }
    public Product(int id, String name, Long price, String image, String manufacturerName,Double averageRating,int numComment) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.manufacturerName = manufacturerName;
        this.averageRating = averageRating;
        this.numComment = numComment;
    }

    public Product(int id, int manufacturerId, String name
            , Long price, String image, double averageRating, int numComment, String size
            , String weight, String chipset, String ram
            , String storage, String battery, String charging, String video) {
        this.id = id;
        this.manufacturerId = manufacturerId;
        this.name = name;
        this.price = price;
        this.numComment = numComment;
        this.image = image;
        this.averageRating = averageRating;
        this.size = size;
        this.weight = weight;
        this.chipset = chipset;
        this.ram = ram;
        this.storage = storage;
        this.battery = battery;
        this.charging = charging;
        this.video = video;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getCharging() {
        return charging;
    }

    public void setCharging(String charging) {
        this.charging = charging;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    private int NumberInCart;

    public int getNumberInCart() {
        return NumberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        NumberInCart = numberInCart;
    }

    public int getNumComment() {
        return numComment;
    }

    public void setNumComment(int numComment) {
        this.numComment = numComment;
    }

    public int getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(int manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }
}
