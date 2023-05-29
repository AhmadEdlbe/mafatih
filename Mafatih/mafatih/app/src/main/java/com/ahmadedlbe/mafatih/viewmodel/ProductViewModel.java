package com.ahmadedlbe.mafatih.viewmodel;

import static com.ahmadedlbe.mafatih.net.HardwareDataSourceFactory.HardwareDataSource;
import static com.ahmadedlbe.mafatih.net.ProductDataSourceFactory.productDataSource;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.ahmadedlbe.mafatih.model.Product;
import com.ahmadedlbe.mafatih.net.HardwareDataSourceFactory;
import com.ahmadedlbe.mafatih.net.ProductDataSource;
import com.ahmadedlbe.mafatih.net.ProductDataSourceFactory;

public class ProductViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Product>> productPagedList;

    public LiveData<PagedList<Product>> hardwarePagedList;

    // Get PagedList configuration
    private static final PagedList.Config  pagedListConfig =
            (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(ProductDataSource.PAGE_SIZE)
                    .build();

    public void loadDoorHandles(String category, int userId){
        // Get our database source factory
        ProductDataSourceFactory productDataSourceFactory = new ProductDataSourceFactory(category,userId);

        // Build the paged list
        productPagedList = (new LivePagedListBuilder<>(productDataSourceFactory, pagedListConfig)).build();
    }

    public void loadHardwares(String category, int userId){
        // Get our database source factory
        HardwareDataSourceFactory hardwareDataSourceFactory = new HardwareDataSourceFactory(category,userId);

        // Build the paged list
        hardwarePagedList = (new LivePagedListBuilder<>(hardwareDataSourceFactory, pagedListConfig)).build();
    }

    public void invalidate(){
        if(productDataSource != null) productDataSource.invalidate();
        if(HardwareDataSource!= null) HardwareDataSource.invalidate();
    }
}
