package com.ahmadedlbe.mafatih.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ahmadedlbe.mafatih.model.NewsFeedResponse;
import com.ahmadedlbe.mafatih.repository.NewsFeedRepository;

public class NewsFeedViewModel extends ViewModel {

    private final NewsFeedRepository newsFeedRepository;

    public NewsFeedViewModel() {
        newsFeedRepository = new NewsFeedRepository();
    }

    public LiveData<NewsFeedResponse> getPosters() {
        return newsFeedRepository.getPosters();
    }
}
