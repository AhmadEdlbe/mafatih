package com.ahmadedlbe.mafatih.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.databinding.ListAccountsBinding;
import com.ahmadedlbe.mafatih.model.account;

import java.util.List;

public class ShowAccountsAdapter extends RecyclerView.Adapter<ShowAccountsAdapter.accountViewHolder> {

    private  final List<account> accountList;

    public ShowAccountsAdapter(List<account> accountList) {
        this.accountList = accountList;
    }
    @NonNull
    @Override
    public ShowAccountsAdapter.accountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListAccountsBinding ListAccountsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_accounts,parent,false);
        return new ShowAccountsAdapter.accountViewHolder(ListAccountsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull accountViewHolder holder, int position) {
        account currentaccount = accountList.get(position);
        holder.binding.username.setText(currentaccount.getUserName());
        holder.binding.email.setText(currentaccount.getemail());
        holder.binding.isAdmin.setText(currentaccount.getIsAdmin());

    }

    public int getItemCount() {
        if (accountList == null) {
            return 0;
        }
        return accountList.size();
    }
    class accountViewHolder extends RecyclerView.ViewHolder{

        // Create view instances
        private final ListAccountsBinding binding;

        private accountViewHolder(ListAccountsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
