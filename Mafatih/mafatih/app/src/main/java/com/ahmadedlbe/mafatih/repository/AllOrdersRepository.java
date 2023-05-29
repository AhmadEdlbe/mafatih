package com.ahmadedlbe.mafatih.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ahmadedlbe.mafatih.model.AllOrdersApiResponse;
import com.ahmadedlbe.mafatih.net.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllOrdersRepository {

    private static final String TAG = AllOrdersRepository.class.getSimpleName();

    public LiveData<AllOrdersApiResponse> getAllOrders() {
        final MutableLiveData<AllOrdersApiResponse> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().getAllOrders().enqueue(new Callback<AllOrdersApiResponse>() {
            @Override
            public void onResponse(Call<AllOrdersApiResponse> call, Response<AllOrdersApiResponse> response) {
                Log.d("onResponse", "" + response.code());

                AllOrdersApiResponse responseBody = response.body();

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<AllOrdersApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


        return mutableLiveData;
    }


}