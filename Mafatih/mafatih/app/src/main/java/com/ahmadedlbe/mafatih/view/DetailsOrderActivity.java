package com.ahmadedlbe.mafatih.view;

import static com.ahmadedlbe.mafatih.utils.Constant.ORDER;
import static com.ahmadedlbe.mafatih.utils.Constant.PRODUCTID;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.databinding.ActivityDetailsOrderBinding;
import com.ahmadedlbe.mafatih.model.Order;

public class DetailsOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private int productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailsOrderBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_details_order);

        // Receive Order object
        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra(ORDER);

        productId = order.getProductId();
        binding.orderDate.setText(order.getOrderDate());
        binding.orderNumber.setText(order.getOrderNumber());
        binding.userName.setText(order.getUserName());
        binding.userAddress.setText(order.getShippingAddress());
        binding.userphone.setText(order.getShippingPhone());
        binding.txtProductName.setText(order.getProductName());
        binding.txtProductPrice.setText(String.valueOf(order.getProductPrice()+ "  $"));
        String status = getString(R.string.item, order.getOrderDateStatus());
        binding.orderStatus.setText(status);

        binding.reOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.reOrder) {
            Intent reOrderIntent = new Intent(this, OrderProductActivity.class);
            reOrderIntent.putExtra(PRODUCTID, productId);
            startActivity(reOrderIntent);
        }
    }
}
