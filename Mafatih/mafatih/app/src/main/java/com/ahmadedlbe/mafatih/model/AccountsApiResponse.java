package com.ahmadedlbe.mafatih.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccountsApiResponse {

    @SerializedName("users")
    private List<account> AccountsList;

    public List<account> getAccounts() {
        return AccountsList;
    }
}
