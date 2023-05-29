package com.ahmadedlbe.mafatih.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.adapter.NewsFeedAdapter;
import com.ahmadedlbe.mafatih.databinding.ActivityNewsFeedBinding;
import com.ahmadedlbe.mafatih.viewmodel.NewsFeedViewModel;

public class NewsFeedActivity extends AppCompatActivity {

    private ActivityNewsFeedBinding binding;
    private NewsFeedViewModel newsFeedViewModel;
    private NewsFeedAdapter newsFeedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_feed);

        newsFeedViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);

        setUpRecyclerView();

        getPosters();
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.newsFeedList.setLayoutManager(layoutManager);
        binding.newsFeedList.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.newsFeedList.addItemDecoration(dividerItemDecoration);
    }

    private void getPosters() {
        newsFeedViewModel.getPosters().observe(this, newsFeedResponse -> {
            newsFeedAdapter = new NewsFeedAdapter(getApplicationContext(), newsFeedResponse.getPosters());
            binding.newsFeedList.setAdapter(newsFeedAdapter);
        });
    }
}
