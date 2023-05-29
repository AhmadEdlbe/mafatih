package com.ahmadedlbe.mafatih.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllOrdersApiResponse {

    @SerializedName("orders")
    private List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }
}
