package com.ahmadedlbe.mafatih.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class account implements Serializable {

    @SerializedName("id")
    private int productId;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    @SerializedName("isAdmin")
    private String isAdmin;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("order_number")
    private String orderNumber;
    @SerializedName("order_date")
    private String orderDate;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("status")
    private String orderDateStatus;
    @SerializedName("name")
    private String userName;
    @SerializedName("address")
    private String shippingAddress;
    @SerializedName("phone")
    private String shippingPhone;

    public int getProductId() {
        return productId;
    }
    public String getIsAdmin() {
        return isAdmin;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getPassword() {
        return password;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getProductName() {
        return productName;
    }

    public String getemail() {
        return email;
    }

    public String getOrderDateStatus() {
        return orderDateStatus;
    }

    public String getUserName() {
        return userName;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getShippingPhone() {
        return shippingPhone;
    }
}
