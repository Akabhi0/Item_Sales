package com.example.item_sales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.item_sales.R;
import com.example.item_sales.databinding.CustomerItemAdapterBinding;
import com.example.item_sales.model.CustomerItem;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerItemAdapter extends RecyclerView.Adapter<CustomerItemAdapter.ViewHolderClass> {
    private Context context;
    private List<CustomerItem> customerItems;

    public CustomerItemAdapter(Context context, List<CustomerItem> customerItems) {
        this.context = context;
        this.customerItems = customerItems;
    }

    @Override
    public int getItemCount() {
        return customerItems.size();
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.customer_item_adapter, parent, false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        holder.getBinding().setCustomerItem(customerItems.get(position));
        holder.getBinding().actvTotal.setText(String.valueOf(Math.floor(customerItems.get(position).getTotalAmount())));
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        public CustomerItemAdapterBinding getBinding() {
            return binding;
        }

        private CustomerItemAdapterBinding binding;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

}
