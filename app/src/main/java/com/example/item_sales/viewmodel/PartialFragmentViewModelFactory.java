package com.example.item_sales.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PartialFragmentViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Application context;

    public PartialFragmentViewModelFactory(Application context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PartialFragmentViewModel(context);
    }
}
