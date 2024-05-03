package com.example.appsale.ObjectClass;

public class OrderDetails {
    private int id;
    private int orderId;
    private int productId;
    private  int num;


    private String productName;
    private int quanity;
    private Long total;

    public OrderDetails(String productName, int quanity, Long total) {
        this.productName = productName;
        this.quanity = quanity;
        this.total = total;
    }

    public OrderDetails(int id, int orderId, int produdId, int num) {
        this.id = id;
        this.orderId = orderId;
        this.productId = produdId;
        this.num = num;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuanity() {
        return quanity;
    }

    public void setQuanity(int quanity) {
        this.quanity = quanity;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProdudId() {
        return productId;
    }

    public void setProdudId(int produdId) {
        this.productId = produdId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
