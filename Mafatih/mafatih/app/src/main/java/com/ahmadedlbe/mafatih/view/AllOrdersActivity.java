package com.ahmadedlbe.mafatih.view;

import static com.ahmadedlbe.mafatih.utils.Constant.ORDER;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.adapter.AllOrderAdapter;
import com.ahmadedlbe.mafatih.databinding.ActivityAllordersBinding;
import com.ahmadedlbe.mafatih.model.Order;
import com.ahmadedlbe.mafatih.viewmodel.AllOrdersViewModel;

public class AllOrdersActivity extends AppCompatActivity implements AllOrderAdapter.OrderAdapterOnClickHandler {

    private ActivityAllordersBinding binding;
    private AllOrdersViewModel AllOrdersViewModel;
    private AllOrderAdapter AllOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorders);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_allorders);

        AllOrdersViewModel = ViewModelProviders.of(this).get(AllOrdersViewModel.class);

        setUpRecycleView();

        getOrders();
    }

    private void setUpRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.orderList.setLayoutManager(layoutManager);
        binding.orderList.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.orderList.addItemDecoration(dividerItemDecoration);
    }

    private void getOrders() {
        AllOrdersViewModel.getAllOrders().observe(this, AllOrdersApiResponse -> {
            AllOrderAdapter = new AllOrderAdapter(AllOrdersApiResponse.getOrderList(),this);
            binding.orderList.setAdapter(AllOrderAdapter);
        });
    }

    @Override
    public void onClick(Order order) {
        Intent intent = new Intent(AllOrdersActivity.this, DetailsOrderActivity.class);
        // Pass an object of order class
        intent.putExtra(ORDER, (order));
        startActivity(intent);
    }
}
