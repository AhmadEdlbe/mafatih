package com.ahmadedlbe.mafatih.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ahmadedlbe.mafatih.model.AccountsApiResponse;
import com.ahmadedlbe.mafatih.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAccountsRepository {

    private static final String TAG = ShowAccountsRepository.class.getSimpleName();

    public LiveData<AccountsApiResponse> getAccounts() {
        final MutableLiveData<AccountsApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getAccounts().enqueue(new Callback<AccountsApiResponse>() {
            @Override
            public void onResponse(Call<AccountsApiResponse> call, Response<AccountsApiResponse> response) {
                Log.d("onResponse", "" + response.code());

                AccountsApiResponse responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<AccountsApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


        return mutableLiveData;
    }


}