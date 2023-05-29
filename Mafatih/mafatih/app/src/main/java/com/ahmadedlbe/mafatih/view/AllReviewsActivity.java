package com.ahmadedlbe.mafatih.view;

import static com.ahmadedlbe.mafatih.storage.LanguageUtils.loadLocale;
import static com.ahmadedlbe.mafatih.utils.Constant.PRODUCT_ID;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.adapter.ReviewAdapter;
import com.ahmadedlbe.mafatih.databinding.ActivityAllReviewsBinding;
import com.ahmadedlbe.mafatih.model.Review;
import com.ahmadedlbe.mafatih.viewmodel.ReviewViewModel;

import java.util.List;

public class AllReviewsActivity extends AppCompatActivity {

    private ActivityAllReviewsBinding binding;
    private ReviewViewModel reviewViewModel;
    private ReviewAdapter reviewAdapter;
    private List<Review> reviewList;
    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_reviews);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.reviews));

        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);

        Intent intent = getIntent();
        productId = intent.getIntExtra(PRODUCT_ID, 0);

        setUpRecycleView();

        getReviewsOfProduct();
    }

    private void setUpRecycleView() {
        binding.allReviewsList.setHasFixedSize(true);
        binding.allReviewsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void getReviewsOfProduct() {
        reviewViewModel.getReviews(productId).observe(this, reviewApiResponse -> {
            if (reviewApiResponse != null) {
                binding.rateProduct.setRating(reviewApiResponse.getAverageReview());
                binding.rateNumber.setText(reviewApiResponse.getAverageReview() + getString(R.string.highestNumber));
                reviewList = reviewApiResponse.getReviewList();
                reviewAdapter = new ReviewAdapter(reviewList);
                binding.allReviewsList.setAdapter(reviewAdapter);
            }
        });
    }
}
