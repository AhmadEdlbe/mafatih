package com.ahmadedlbe.mafatih.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ahmadedlbe.mafatih.repository.DeleteUserRepository;

import okhttp3.ResponseBody;

public class DeleteUserViewModel extends ViewModel {

    private final DeleteUserRepository deleteUserRepository;

    public DeleteUserViewModel() {
        deleteUserRepository = new DeleteUserRepository();
    }

    public LiveData<ResponseBody> deleteUser(String token, int userId) {
        return deleteUserRepository.deleteUser(token, userId);
    }
}

