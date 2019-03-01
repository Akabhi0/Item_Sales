package com.example.item_sales.viewmodel;

import android.app.Application;

import com.example.item_sales.model.CustomerItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AddItemDataFragmentViewModelFactroy extends ViewModelProvider.NewInstanceFactory {

    private Application context;
    private CustomerItem customerItem;

    public AddItemDataFragmentViewModelFactroy(Application context, CustomerItem customerItem) {
        this.context = context;
        this.customerItem = customerItem;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddItemDataFragmentViewModel(context, customerItem);
    }
}
