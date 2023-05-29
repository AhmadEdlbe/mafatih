package com.ahmadedlbe.mafatih.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ahmadedlbe.mafatih.model.AccountsApiResponse;
import com.ahmadedlbe.mafatih.repository.ShowAccountsRepository;

public class ShowAccountsViewModel extends ViewModel {

    private final ShowAccountsRepository ShowAccountsRepository;

    public ShowAccountsViewModel() {ShowAccountsRepository = new ShowAccountsRepository();}

    public LiveData<AccountsApiResponse> getAccounts() {
        return ShowAccountsRepository.getAccounts();
    }
}

