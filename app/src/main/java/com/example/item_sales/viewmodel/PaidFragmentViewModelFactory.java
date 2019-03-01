package com.example.item_sales.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class PaidFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application context;

    public PaidFragmentViewModelFactory(Application context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PaidFragmentViewModel(context);
    }
}
