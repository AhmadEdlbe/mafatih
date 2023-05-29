package com.ahmadedlbe.mafatih.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ahmadedlbe.mafatih.model.Favorite;
import com.ahmadedlbe.mafatih.net.RetrofitClient;
import com.ahmadedlbe.mafatih.utils.RequestCallback;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFavoriteRepository {

    private static final String TAG = AddFavoriteRepository.class.getSimpleName();

    public LiveData<ResponseBody> addFavorite(Favorite favorite, RequestCallback callback) {
        final MutableLiveData<ResponseBody> mutableLiveData = new MutableLiveData<>();
        RetrofitClient.getInstance().getApi().addFavorite(favorite).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse" + response.code());

                ResponseBody responseBody = response.body();

                if(response.code() == 200){
                    callback.onCallBack();
                }

                if (response.body() != null) {
                    mutableLiveData.setValue(responseBody);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure" + t.getMessage());
            }
        });
        return mutableLiveData;
    }

}
