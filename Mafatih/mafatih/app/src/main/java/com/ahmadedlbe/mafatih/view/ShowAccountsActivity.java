package com.ahmadedlbe.mafatih.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.adapter.ShowAccountsAdapter;
import com.ahmadedlbe.mafatih.databinding.ActivityShowAccountsBinding;
import com.ahmadedlbe.mafatih.viewmodel.ShowAccountsViewModel;

public class ShowAccountsActivity extends AppCompatActivity{

    private ActivityShowAccountsBinding binding;
    private ShowAccountsViewModel ShowAccountViewModel;
    private ShowAccountsAdapter ShowAccountsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_accounts);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_accounts);

        ShowAccountViewModel = ViewModelProviders.of(this).get(ShowAccountsViewModel.class);

        setUpRecycleView();

        getAccounts();
    }

    private void setUpRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.accountList.setLayoutManager(layoutManager);
        binding.accountList.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.accountList.addItemDecoration(dividerItemDecoration);
    }

    private void getAccounts() {
        ShowAccountViewModel.getAccounts().observe(this, AccountsApiResponse -> {
            ShowAccountsAdapter = new ShowAccountsAdapter(AccountsApiResponse.getAccounts());
            binding.accountList.setAdapter(ShowAccountsAdapter);
        });
    }
}

