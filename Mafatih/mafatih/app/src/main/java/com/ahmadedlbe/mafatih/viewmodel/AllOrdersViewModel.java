package com.ahmadedlbe.mafatih.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ahmadedlbe.mafatih.model.AllOrdersApiResponse;
import com.ahmadedlbe.mafatih.repository.AllOrdersRepository;

public class AllOrdersViewModel extends ViewModel {

    private final AllOrdersRepository AllOrdersRepository;

    public AllOrdersViewModel() {
        AllOrdersRepository = new AllOrdersRepository();
    }
    public LiveData<AllOrdersApiResponse> getAllOrders() {
        return AllOrdersRepository.getAllOrders();
    }
}

