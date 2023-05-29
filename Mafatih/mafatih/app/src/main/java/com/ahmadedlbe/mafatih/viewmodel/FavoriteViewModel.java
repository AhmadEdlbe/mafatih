package com.ahmadedlbe.mafatih.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ahmadedlbe.mafatih.model.FavoriteApiResponse;
import com.ahmadedlbe.mafatih.repository.FavoriteRepository;

public class FavoriteViewModel extends ViewModel {

    private final FavoriteRepository favoriteRepository;

    public FavoriteViewModel() {
        favoriteRepository = new FavoriteRepository();
    }

    public LiveData<FavoriteApiResponse> getFavorites(int userId) {
        return favoriteRepository.getFavorites(userId);
    }
}
