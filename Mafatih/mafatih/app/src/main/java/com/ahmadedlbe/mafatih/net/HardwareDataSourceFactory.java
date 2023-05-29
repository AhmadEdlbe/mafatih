package com.ahmadedlbe.mafatih.net;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.ahmadedlbe.mafatih.model.Product;

public class HardwareDataSourceFactory extends DataSource.Factory{

    // Creating the mutable live database
    private final MutableLiveData<PageKeyedDataSource<Integer, Product>> hardwareLiveDataSource = new MutableLiveData<>();

    public static ProductDataSource HardwareDataSource;

    private final String category;
    private final int userId;

    public HardwareDataSourceFactory(String category, int userId){
        this.category = category;
        this.userId = userId;
    }

    @Override
    public DataSource<Integer, Product> create() {
        // Getting our Data source object
        HardwareDataSource = new ProductDataSource(category,userId);

        // Posting the Data source to get the values
        hardwareLiveDataSource.postValue(HardwareDataSource);

        // Returning the Data source
        return HardwareDataSource;
    }
}