package com.ahmadedlbe.mafatih.view;

import static com.ahmadedlbe.mafatih.storage.LanguageUtils.loadLocale;
import static com.ahmadedlbe.mafatih.utils.Constant.PRODUCT;
import static com.ahmadedlbe.mafatih.utils.InternetUtils.isNetworkConnected;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.adapter.CartAdapter;
import com.ahmadedlbe.mafatih.databinding.ActivityCartBinding;
import com.ahmadedlbe.mafatih.model.Product;
import com.ahmadedlbe.mafatih.storage.LoginUtils;
import com.ahmadedlbe.mafatih.viewmodel.CartViewModel;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    private CartAdapter cartAdapter;
    private List<Product> favoriteList;
    private CartViewModel cartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.cart));

        setUpRecyclerView();

        getProductsInCart();
    }

    private void setUpRecyclerView() {
        binding.productsInCart.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.productsInCart.setHasFixedSize(true);
        cartViewModel = ViewModelProviders.of(this).get(CartViewModel.class);
    }

    private void getProductsInCart() {
        if (isNetworkConnected(this)) {
            cartViewModel.getProductsInCart(LoginUtils.getInstance(this).getUserInfo().getId()).observe(this, cartApiResponse -> {
                if (cartApiResponse != null) {
                    favoriteList = cartApiResponse.getProductsInCart();
                    if (favoriteList.size() == 0) {
                        binding.noBookmarks.setVisibility(View.VISIBLE);
                        binding.emptyCart.setVisibility(View.VISIBLE);
                    } else {
                        binding.productsInCart.setVisibility(View.VISIBLE);
                    }
                    cartAdapter = new CartAdapter(getApplicationContext(), favoriteList, product -> {
                        Intent intent = new Intent(CartActivity.this, DetailsActivity.class);
                        // Pass an object of product class
                        intent.putExtra(PRODUCT, (product));
                        startActivity(intent);
                    }, this);
                }

                binding.loadingIndicator.setVisibility(View.GONE);
                binding.productsInCart.setAdapter(cartAdapter);
            });
        } else {
            binding.emptyCart.setVisibility(View.VISIBLE);
            binding.loadingIndicator.setVisibility(View.GONE);
            binding.emptyCart.setText(getString(R.string.no_internet_connection));
        }
    }
}
