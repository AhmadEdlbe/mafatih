package com.ahmadedlbe.mafatih.view;

import static com.ahmadedlbe.mafatih.storage.LanguageUtils.loadLocale;
import static com.ahmadedlbe.mafatih.utils.Constant.PRODUCT;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.adapter.ProductAdapter;
import com.ahmadedlbe.mafatih.databinding.ActivityAllDoorhandlesBinding;
import com.ahmadedlbe.mafatih.model.Product;
import com.ahmadedlbe.mafatih.storage.LoginUtils;
import com.ahmadedlbe.mafatih.viewmodel.ProductViewModel;

public class AllDoorHandlesActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler{

    private ActivityAllDoorhandlesBinding binding;

    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_doorhandles);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.all_Door_Handles));

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadDoorHandles("Door Handle", userID);

        setupRecyclerViews();

        getAllDoorHandles();
    }

    private void setupRecyclerViews() {
        // Mobiles
        binding.allDoorHandlesRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        binding.allDoorHandlesRecyclerView.setHasFixedSize(true);
        productAdapter = new ProductAdapter(this,this);
    }

    public void getAllDoorHandles() {
        productViewModel.productPagedList.observe(this, products -> productAdapter.submitList(products));
        binding.allDoorHandlesRecyclerView.setAdapter(productAdapter);
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllDoorHandlesActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
