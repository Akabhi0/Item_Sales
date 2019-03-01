package com.example.item_sales.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.item_sales.R;
import com.example.item_sales.databinding.PartialPaidAdapterBinding;
import com.example.item_sales.model.CustomerDataBase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PartiatPaidAdapter extends RecyclerView.Adapter<PartiatPaidAdapter.ViewHolderClass> {
    private List<CustomerDataBase> customerDataBases;
    private Context context;
    private OnItemClicklistner onItemClicklistner;
    private CustomerDataBase customerData;

    public PartiatPaidAdapter(List<CustomerDataBase> customerDataBases, Context context) {
        this.context = context;
        this.customerDataBases = customerDataBases;
    }

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.partial_paid_adapter, parent, false);
        return new ViewHolderClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {
        if (customerDataBases != null) {
            holder.getBinding().setCustomerDataBase(customerDataBases.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return customerDataBases.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {

        private PartialPaidAdapterBinding binding;

        public PartialPaidAdapterBinding getBinding() {
            return binding;
        }

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

            binding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = customerDataBases.get(getAdapterPosition()).getId_customer();
                    onItemClicklistner.onPositionClicked(id);
                }
            });

            binding.pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = customerDataBases.get(getAdapterPosition()).getId_customer();
                    double balanceAmount = customerDataBases.get(getAdapterPosition()).getBalance_amount_customer();
                    onItemClicklistner.onPositionClickedBalanceAmount(balanceAmount, id);
                }
            });
        }
    }

    public interface OnItemClicklistner {
        void onPositionClicked(int position);

        void onPositionClickedBalanceAmount(double balanceAmount, int id);
    }

    public void setOnclickListner(OnItemClicklistner onclickListner) {
        this.onItemClicklistner = onclickListner;
    }

    public void UpdateList(List<CustomerDataBase> customerData) {
        this.customerDataBases = customerData;
        notifyDataSetChanged();
    }
}
