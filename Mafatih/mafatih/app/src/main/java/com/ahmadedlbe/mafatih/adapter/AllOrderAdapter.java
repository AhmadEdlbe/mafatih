package com.ahmadedlbe.mafatih.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadedlbe.mafatih.R;
import com.ahmadedlbe.mafatih.databinding.AllordersListItemBinding;
import com.ahmadedlbe.mafatih.model.Order;

import java.text.DecimalFormat;
import java.util.List;

public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.OrderViewHolder>{

    private final List<Order> orderList;
    private Order currentOrder;

    private final AllOrderAdapter.OrderAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface OrderAdapterOnClickHandler {
        void onClick(Order order);
    }

    public AllOrderAdapter(List<Order> orderList, AllOrderAdapter.OrderAdapterOnClickHandler clickHandler) {
        this.orderList = orderList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        AllordersListItemBinding AllordersListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.allorders_list_item,parent,false);
        return new OrderViewHolder(AllordersListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        currentOrder = orderList.get(position);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        holder.binding.username.setText(currentOrder.getUserName());
        holder.binding.orderNumber.setText(currentOrder.getOrderNumber());
        holder.binding.orderDate.setText(currentOrder.getOrderDate());
    }

    @Override
    public int getItemCount() {
        if (orderList == null) {
            return 0;
        }
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        private final AllordersListItemBinding binding;

        private OrderViewHolder(AllordersListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getBindingAdapterPosition();
            // Get position of the product
            currentOrder = orderList.get(position);
            // Send product through click
            clickHandler.onClick(currentOrder);
        }
    }
}
