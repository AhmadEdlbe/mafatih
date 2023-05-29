package com.ahmadedlbe.mafatih.model;

import com.google.gson.annotations.SerializedName;

public class Shipping {

    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("floor")
    private String floor;
    @SerializedName("phone")
    private String phone;
    @SerializedName("userId")
    private int userId;
    @SerializedName("productId")
    private int productId;

    public Shipping(String address, String city, String floor, String phone, int userId, int productId) {
        this.address = address;
        this.city = city;
        this.floor = floor;
        this.phone = phone;
        this.userId = userId;
        this.productId = productId;
    }
}
