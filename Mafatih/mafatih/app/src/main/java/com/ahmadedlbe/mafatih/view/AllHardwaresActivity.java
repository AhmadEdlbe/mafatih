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
import com.ahmadedlbe.mafatih.databinding.ActivityAllHardwaresBinding;
import com.ahmadedlbe.mafatih.model.Product;
import com.ahmadedlbe.mafatih.storage.LoginUtils;
import com.ahmadedlbe.mafatih.viewmodel.ProductViewModel;

public class AllHardwaresActivity extends AppCompatActivity implements ProductAdapter.ProductAdapterOnClickHandler {

    private ActivityAllHardwaresBinding binding;
    private ProductAdapter hardwareAdapter;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_hardwares);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.all_Hardware));

        int userID = LoginUtils.getInstance(this).getUserInfo().getId();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.loadHardwares("Hardware",userID);

        setupRecyclerViews();

        getAllHardwares();
    }

    private void setupRecyclerViews() {
        // Laptops
        binding.allHardwaresRecyclerView.setHasFixedSize(true);
        binding.allHardwaresRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        hardwareAdapter = new ProductAdapter(this, this);
    }

    public void getAllHardwares() {
        productViewModel.hardwarePagedList.observe(this, products -> hardwareAdapter.submitList(products));
        binding.allHardwaresRecyclerView.setAdapter(hardwareAdapter);
    }

    @Override
    public void onClick(Product product) {
        Intent intent = new Intent(AllHardwaresActivity.this, DetailsActivity.class);
        // Pass an object of product class
        intent.putExtra(PRODUCT, (product));
        startActivity(intent);
    }
}
