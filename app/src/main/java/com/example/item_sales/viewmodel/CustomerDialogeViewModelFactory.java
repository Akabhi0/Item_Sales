package com.example.item_sales.viewmodel;

import android.app.Application;
import android.content.Context;

import com.example.item_sales.model.CustomerDataBase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CustomerDialogeViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private double balamceAmount;
    private int id;
    private String doubleBalanceAmount;
    private Application context;

    public CustomerDialogeViewModelFactory(double balamceAmount, int id, Application context, String doubleBalanceAmount) {
        this.balamceAmount = balamceAmount;
        this.id = id;
        this.context = context;
        this.doubleBalanceAmount = doubleBalanceAmount;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CustomDialogeViewModel(balamceAmount, id, context, doubleBalanceAmount);
    }
}
