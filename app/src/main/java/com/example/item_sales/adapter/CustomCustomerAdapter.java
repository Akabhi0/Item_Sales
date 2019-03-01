package com.example.item_sales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.item_sales.R;
import com.example.item_sales.databinding.CustomerCustomerAdapterBinding;
import com.example.item_sales.model.CustomerItem;
import com.example.item_sales.model.CustomerItemDataBase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CustomCustomerAdapter extends RecyclerView.Adapter<CustomCustomerAdapter.ViewHolderClass> {
    private List<CustomerItemDataBase> customerItems;
    private Context context;


    public CustomCustomerAdapter(List<CustomerItemDataBase> customerItems, Context context) {
        this.context = context;
        this.customerItems = customerItems;
    }

    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_customer_adapter, parent, false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.getBinding().setCustomerItem(customerItems.get(position));
        holder.getBinding().actvTotal.setText(String.valueOf(customerItems.get(position).getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return customerItems.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        private CustomerCustomerAdapterBinding binding;

        public CustomerCustomerAdapterBinding getBinding() {
            return binding;
        }

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
