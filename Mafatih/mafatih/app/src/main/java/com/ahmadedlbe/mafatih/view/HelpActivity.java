package com.ahmadedlbe.mafatih.view;

import static com.ahmadedlbe.mafatih.storage.LanguageUtils.loadLocale;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.adapter.HelpAdapter;
import com.ahmadedlbe.mafatih.databinding.ActivityHelpBinding;
import com.ahmadedlbe.mafatih.model.Help;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    private ActivityHelpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_help);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.help_center));

        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.helpList.setLayoutManager(layoutManager);
        binding.helpList.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.helpList.addItemDecoration(dividerItemDecoration);

        HelpAdapter helpAdapter = new HelpAdapter(getHelpList());
        binding.helpList.setAdapter(helpAdapter);
    }

    private ArrayList<Help> getHelpList() {
        final ArrayList<Help> helpList = new ArrayList<>();
        helpList.add(new Help(getString(R.string.inquiryOne), getString(R.string.answerOne)));
        helpList.add(new Help(getString(R.string.inquiryTwo), getString(R.string.answerTwo)));
        helpList.add(new Help(getString(R.string.inquiryThree), getString(R.string.answerThree)));
        helpList.add(new Help(getString(R.string.inquiryFour), getString(R.string.answerFour)));
        helpList.add(new Help(getString(R.string.inquiryFive), getString(R.string.answerFive)));
        return helpList;
    }
}
